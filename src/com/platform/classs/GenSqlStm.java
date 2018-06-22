package com.platform.classs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.database.OracleDB;
import com.base.database.Table;
import com.base.database.TableField;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class GenSqlStm {
	private final String tabName = "GenSqlStm";
	
	private MainFrame frame = null;
	private Connection sqliteConn = null;
	
	private Connection connection = null;
	
	JTextField inputTableName = new JTextField();
	JTextArea outputSqlStm = new JTextArea();
	
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
		JPanel panel = new JPanel();
		LayoutByRow panelLayout = new LayoutByRow(panel);
		panelLayout.setRowInfo(1, 20, 10, 10);
		JLabel inputPromptLabel = new JLabel("请输入表格名称：");
		panelLayout.add(inputPromptLabel, 1, 120, 'N', 0, 0, 'L');
		
		panelLayout.add(inputTableName, 1, 80, 'N', 0, 0, 'L');
		JButton GenSqlStmButton = new JButton("生成SQL创建表格语句");
		panelLayout.add(GenSqlStmButton, 1, 180, 'N', 0, 0, 'L');
		GenSqlStmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				genSqlCrtStm();
			}
		});
		
		panelLayout.setRowInfo(2, 200, 10, 10);
		outputSqlStm.setLineWrap(true);        //激活自动换行功能 
		outputSqlStm.setWrapStyleWord(true);            // 激活断行不断字功能
		panelLayout.add(outputSqlStm, 2, 400, 'N', 0, 1, 'L');
		
		frame.getRightPanel().addTab(tabName, panel);
		
		this.frame.getFrameLayout().setRowPos();
		panelLayout.setRowPos();
	}
	
	public void genSqlCrtStm(){
		String tableName = getTableName();
		String sqlCrtStm = "CREATE TABLE " + tableName + " (";
		
/*		SqliteDB.exeSql("CREATE TABLE menuconfig (menuid varchar2(10),menucode varchar2(15),"
				+ "menutype varchar2(10),level int(3),pearentmenucode varchar2(15),text varchar2(100),"
				+ "classname varchar2(100),classmethod varchar2(20))");*/
		
		OracleDB db = new OracleDB("DEV");
		this.connection = db.getConnection();
		try {
			Vector<TableField> fields = Table.geTableFields(tableName, this.connection);
			int fieldCount = 0;
			for(TableField field:fields){
				if(fieldCount++ != 0) sqlCrtStm += ",";
				sqlCrtStm += field.getFieldName().toLowerCase() + " " + field.getFieldType()
						   + "(" + field.getFieldLen();
				if(field.getFieldType().equals("NUMBER") || 
				   field.getFieldType().equals("FLOAT") || 
				   field.getFieldType().equals("DOUBLE")){
					sqlCrtStm += "," + field.getFieldDec();
				}
				sqlCrtStm += ")";
			}
			sqlCrtStm += ")";
			
			//System.out.println("[sql]"+sqlCrtStm);
			outputSqlStm(sqlCrtStm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTableName(){
		return inputTableName.getText();
	}
	public void outputSqlStm(String sqlstm){
		outputSqlStm.setText(sqlstm);
	}
}
