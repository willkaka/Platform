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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.database.DatabaseInfo;
import com.base.database.OracleDB;
import com.base.database.Table;
import com.base.database.TableField;
import com.base.function.GenDatabaseTableEntity;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class GenTableClass {
	private final String tabName = "生成数据表类java代码";
	
	private MainFrame frame = null;
	private Connection sqliteConn = null;
	
	private Connection connection = null;
	
	JTextField inputTableName = new JTextField();
	JTextArea outputJavaStm = new JTextArea();
	
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
		JButton GenJavaSrcButton = new JButton("生成Java类代码");
		panelLayout.add(GenJavaSrcButton, 1, 180, 'N', 0, 0, 'L');
		GenJavaSrcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenJavaSrc();
			}
		});
		
		panelLayout.setRowInfo(2, 200, 10, 10);
		
		JScrollPane TextArea_scrollPane = new JScrollPane();
		TextArea_scrollPane.setBounds(0, 187, 591, 98);
        TextArea_scrollPane.setViewportView(outputJavaStm);
        
		outputJavaStm.setLineWrap(true);        //激活自动换行功能 
		outputJavaStm.setWrapStyleWord(true);            // 激活断行不断字功能
		panelLayout.add(TextArea_scrollPane, 2, 400, 'B', 1, 1, 'L');
		
		frame.getRightPanel().addTab(tabName, panel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(panel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(panel, panelLayout);
		frame.getRightPanelLayout().setCompOthInfo(panel, tabName);
		
		this.frame.getFrameLayout().setRowPos();
	}
	
	public void GenJavaSrc(){
		String tableName = getTableName();
		String sqlCrtStm = "CREATE TABLE " + tableName + " (";
		
		OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, "DEV"));
		this.connection = db.getConnection();
		try {
			GenDatabaseTableEntity tableEntry = new GenDatabaseTableEntity(connection,tableName);
			
			outputJavaSrc(tableEntry.getTableInfo2());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTableName(){
		return inputTableName.getText();
	}
	public void outputJavaSrc(StringBuffer sqlstm){
		outputJavaStm.setText(new String(sqlstm));
	}
}
