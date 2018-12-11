package com.platform.classs;

import java.sql.Connection;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class LoanInfoQuery {
	private final String tabName = "借据信息查询";
	
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
			//this.frame.getRightPanel().setSelectedComponent(this.frame.getRightPanel().getComponentAt(this.frame.getRightPanel().indexOfTab(tabName)));
		}
	}
	
	public void showPageComp(){
		JPanel panel = new JPanel();
		LayoutByRow panelLayout = new LayoutByRow(panel);
		panelLayout.setRowInfo(1, 20, 10, 10);
		JLabel inputPromptLabel = new JLabel("请输入借据编号：");
		panelLayout.add(inputPromptLabel, 1, 120, 'N', 0, 0, 'L');
		
		panelLayout.add(inputTableName, 1, 80, 'N', 0, 0, 'L');

		
		panelLayout.setRowInfo(2, 200, 10, 10);
		outputSqlStm.setLineWrap(true);        //激活自动换行功能 
		outputSqlStm.setWrapStyleWord(true);            // 激活断行不断字功能
		panelLayout.add(outputSqlStm, 2, 400, 'N', 0, 1, 'L');
		
		frame.getRightPanel().addTab(tabName, panel);
		frame.getRightPanelLayout().setCompOthInfo(panel, tabName);
		frame.getRightPanel().setSelectedComponent(this.frame.getRightPanel().getComponentAt(this.frame.getRightPanel().indexOfTab(tabName)));
		
		this.frame.getFrameLayout().setRowPos();
		panelLayout.setRowPos();
	}
	
	public void genSqlCrtStm(){

	}

}
