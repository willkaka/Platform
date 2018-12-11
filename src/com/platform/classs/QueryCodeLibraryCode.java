package com.platform.classs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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
 * 查询Code_Library参数信息
 * @author huangyuanwei
 *
 */
public class QueryCodeLibraryCode {
	private final String tabName = "查询CL参数信息";
	
	private Connection sqliteConn = null;
	private Connection connection = null;
	private MainFrame frame = null;
	
	private JPanel mainPanel = new JPanel();
	private LayoutByRow mainPanelLayout = new LayoutByRow(mainPanel);
	
	private TablePanel tablePanel = null;
	
	private JComboBox EnvListBox = null;
	private JTextField codeNameField = new JTextField();
	
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
		
		EnvListBox = new JComboBox();
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
				String sEnv = ((JComboBox)e.getSource()).getSelectedItem().toString();
				DatabaseInfo databaseInfo = DatabaseInfo.getDatabaseInfo(sqliteConn, sEnv);
				connection = databaseInfo.getDBConnection(databaseInfo);
				
				//tableInfoJTable.removeAll();
				//tableInfoTableLayout.removeAllComp();
				resetCompPos();
			}
		});
		
		JLabel inputPromptLabel1 = new JLabel("    参数名称：");
		mainPanelLayout.add(inputPromptLabel1, 1, 80, 'N', 0, 0, 'L');
		
		mainPanelLayout.add(codeNameField, 1, 200, 'N', 0, 0, 'L');
		codeNameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTableInfo(codeNameField.getText());				
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
	
	public void showTableInfo(String sCodeName){
		Vector cols = null;
		Vector rows = null;
		try{
			String sEnv = EnvListBox.getSelectedItem().toString();
			DatabaseInfo databaseInfo = DatabaseInfo.getDatabaseInfo(sqliteConn, sEnv);
			
			HashMap<String,Object> keyAndValues = new HashMap<>();
			if(databaseInfo.getDbtype().equals("Oracle")){
				keyAndValues.put("codeno", sCodeName);
				keyAndValues.put("order", "order by itemno");
			}else if(databaseInfo.getDbtype().equals("MySql")){
				keyAndValues.put("code_no", sCodeName);
				keyAndValues.put("order", "order by item_no");
			}
			
			keyAndValues.put("select", "*");
			
			cols = Table.geTableFields("Code_Library",keyAndValues, connection);
			rows = Table.getTableRecords("Code_Library", keyAndValues, connection);			
		}catch(SQLException e){
			e.printStackTrace();
			//System.out.println("ErrorCode:"+e.getErrorCode());
			if(e.getErrorCode() == 942){
				JOptionPane.showMessageDialog(null, "参数"+sCodeName+"不存在！");
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
