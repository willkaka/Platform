package com.platform.classs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.database.DatabaseInfo;
import com.base.database.OracleDB;
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
	
	private JTable tableInfoJTable = new JTable();
	private LayoutByRow tableInfoTableLayout = new LayoutByRow(tableInfoJTable);
	private JScrollPane tableInfoJTableScroll = new JScrollPane(tableInfoJTable);
	private LayoutByRow tableInfoJTableSrcollLayout = new LayoutByRow(tableInfoJTableScroll);
	
	private JTextField tableNameField = new JTextField();
	
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
		mainPanelLayout.add(EnvListBox, 1, 60, 'N', 0, 0, 'L');
		EnvListBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, ((JComboBox)e.getSource()).getSelectedItem().toString()));
				connection = db.getConnection();
				
				tableInfoJTable.removeAll();
				tableInfoTableLayout.removeAllComp();
				resetCompPos();
			}
		});
		
		JLabel inputPromptLabel1 = new JLabel("    表名：");
		mainPanelLayout.add(inputPromptLabel1, 1, 78, 'N', 0, 0, 'L');
		
		mainPanelLayout.add(tableNameField, 1, 100, 'N', 0, 0, 'L');
		tableNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//showTransConfig("");				
				resetCompPos();
			}
		});
		
		mainPanelLayout.setRowInfo(2, 100, 10, 10);
		mainPanelLayout.setRowGap(2, 0, 0, 0);
		mainPanelLayout.add(tableInfoJTableScroll, 2, 300, 'B', 1, 1, 'L');
		
		mainPanelLayout.setCompLayout(tableInfoJTableScroll, tableInfoJTableSrcollLayout);
		
		tableInfoJTableSrcollLayout.setRowInfo(1, 100, 10, 10);
		tableInfoJTableSrcollLayout.add(tableInfoJTable, 1, 100, 'B', 1, 1, 'L');
		tableInfoJTableSrcollLayout.setCompLayout(tableInfoJTable, tableInfoTableLayout);
		
		tableInfoJTableScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		frame.getRightPanel().addTab(tabName, mainPanel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(mainPanel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(mainPanel, mainPanelLayout);
		frame.getRightPanelLayout().setCompOthInfo(mainPanel, tabName);
		
		resetCompPos();
	}
	
	public void resetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
}
