package com.platform.classs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.comp.TablePanel;
import com.base.database.DatabaseInfo;
import com.base.database.OracleDB;
import com.base.database.Table;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

/**
 * 查询数据表信息
 * @author huangyuanwei
 *
 */
public class QueryTableInfo {
	private final String tabName = "查询数据表信息";
	
	private Connection sqliteConn = null;
	private Connection connection = null;
	private MainFrame frame = null;
	
	private JPanel mainPanel = new JPanel();
	private LayoutByRow mainPanelLayout = new LayoutByRow(mainPanel);
	
	private TablePanel tablePanel = null;
	
	private JComboBox tableNameComboBox = new JComboBox();
	
	public void execute(MainFrame frame, SQLiteConnection sqliteConnection){
		System.out.println("---execute "+tabName+"-----");
		this.frame = frame;
		this.sqliteConn = sqliteConnection;
		
		int index = this.frame.getRightPanel().indexOfTab(tabName);
		if(index >= 0){
			this.frame.getRightPanel().setSelectedComponent(this.frame.getRightPanel().getComponentAt(index));
		} else{
			showPageComp();
			this.frame.getRightPanel().setSelectedComponent(this.frame.getRightPanel().getComponentAt(this.frame.getRightPanel().indexOfTab(tabName)));
		}
	}
	
	public void showPageComp(){
		mainPanelLayout.setRowInfo(1, 20, 10, 10);
		JLabel inputPromptLabel0 = new JLabel("数据环境：");
		mainPanelLayout.add(inputPromptLabel0, 1, 65, 'N', 0, 0, 'L');
		
		JComboBox EnvListBox = new JComboBox();
		List<DatabaseInfo> envList = DatabaseInfo.getEnvList(this.sqliteConn); 
		for(DatabaseInfo env:envList){
			EnvListBox.addItem(env.getEnvname());
		}
		OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, "DEV"));
		connection = db.getConnection(); //初始
		mainPanelLayout.add(EnvListBox, 1, 100, 'N', 0, 0, 'L');
		EnvListBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				//OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, ((JComboBox)e.getSource()).getSelectedItem().toString()));
				//connection = DatabaseInfo.getDBConnection(DatabaseInfo.getDatabaseInfo(sqliteConn, ((JComboBox)e.getSource()).getSelectedItem().toString()));
				//connection = OracleDB.getConnection();
				String sEnv = ((JComboBox)e.getSource()).getSelectedItem().toString();
				DatabaseInfo databaseInfo = DatabaseInfo.getDatabaseInfo(sqliteConn, sEnv);
				connection = databaseInfo.getDBConnection(databaseInfo);
				if(databaseInfo.getDbtype().equals("MySql")){
					try {
						tableNameComboBox.removeAllItems();
						List<Table> tableList = Table.getMysqlTableList(connection);
						for(Table table:tableList){
							tableNameComboBox.addItem(table.getTableName());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
					
				}
				
				
				//tableInfoJTable.removeAll();
				//tableInfoTableLayout.removeAllComp();
				resetCompPos();
			}
		});
		
		JLabel inputPromptLabel1 = new JLabel("    表名：");
		mainPanelLayout.add(inputPromptLabel1, 1, 78, 'N', 0, 0, 'L');
		
		mainPanelLayout.add(tableNameComboBox, 1, 200, 'N', 0, 0, 'L');
		tableNameComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTableInfo(tableNameComboBox.getSelectedItem().toString());				
				resetCompPos();
			}
		});
		
		mainPanelLayout.setRowInfo(2, 100, 10, 10);
		mainPanelLayout.setRowGap(2, 0, 0, 0);
		tablePanel = new TablePanel(null, null);
		mainPanelLayout.add(tablePanel, 2, 300, 'B', 1, 1, 'L');
		
		mainPanelLayout.setCompLayout(tablePanel, tablePanel.getTablePanelLayout());
		
		//tableInfoJTableSrcollLayout.setRowInfo(1, 100, 10, 10);
		//tableInfoJTableSrcollLayout.add(tableInfoJTable, 1, 100, 'B', 1, 1, 'L');
		//tableInfoJTableSrcollLayout.setCompLayout(tableInfoJTable, tableInfoTableLayout);
		
		//tableInfoJTableScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		frame.getRightPanel().addTab(tabName, mainPanel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(mainPanel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(mainPanel, mainPanelLayout);
		frame.getRightPanelLayout().setCompOthInfo(mainPanel, tabName);
		
		resetCompPos();
	}
	
	public void showTableInfo(String tableName){
		Vector cols = null;
		Vector rows = null;
		try{
			cols = Table.geTableFields(tableName, null, connection);
			rows = Table.getTableRecords(tableName, null, connection);			
		}catch(SQLException e){
			e.printStackTrace();
			//System.out.println("ErrorCode:"+e.getErrorCode());
			if(e.getErrorCode() == 942){
				JOptionPane.showMessageDialog(null, "数据表："+tableName+"不存在！");
				return;
			}
			//System.out.println(""+);
			//System.out.println(""+);
		}catch (Exception e) {
			e.printStackTrace();
		}
		tablePanel.setTablePanelData(cols, rows);
	}
	
	public void resetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
}
