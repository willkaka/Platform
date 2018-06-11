package com.platform.menuEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.sqlite.SQLiteConnection;

import com.base.bean.MenuConfig;
import com.base.comp.JTablePanel;
import com.base.database.SqliteDB;
import com.base.database.Table;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class OpenTableEvent {
	
	Connection connection= null;
	LayoutByRow frameLayout = null;
	JTextField inputTableNameTextField = new JTextField();

	public void execute(MainFrame frame, SQLiteConnection connection){
		System.out.println("configMenuEvent");
		//public void com.platform.menuEvent.ConfigMenuEvent.execute()
		this.connection = connection;
		JFrame configFrame = new JFrame("配置信息");
		configFrame.setLayout(null);
		frameLayout = new LayoutByRow(configFrame);
		frameLayout.setRowInfo(1, 625, 0, 10);
		frameLayout.setBotGap(20);
		
		configFrame.setBounds(450, 200, 530, 625);
		
		frameLayout.setRowInfo(1, 20, 10, 10);
		JLabel inputTableNameLabel = new JLabel("请输入表名：");
		frameLayout.add(inputTableNameLabel, 1, 100, 'N', 0, 0, 'L');
		//JTextField inputTableNameTextField = new JTextField();
		inputTableNameTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTableInfo();
			}
		});
		frameLayout.add(inputTableNameTextField, 1, 100, 'N', 0, 0, 'L');
		
		
		/*frameLayout.setRowInfo(2, 625, 0, 10);
		Vector tableColTitles = new Vector();
		Vector tableRecords = new Vector();
		try{
			tableColTitles = Table.geTableFieldsComment("menuconfig", connection);
			tableRecords = Table.getTableRecords("menuconfig", null, connection);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JTablePanel tablePanel = new JTablePanel("menuconfig",tableColTitles, tableRecords, connection);
		frameLayout.add(tablePanel, 2, 600, 'B', 1, 1, 'L');*/
		
		frameLayout.setRowPos();
		
		
		configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		configFrame.setVisible(true);
		configFrame.repaint();
	}
	
	public void showTableInfo(){
		String tableName = getInputTableNameTextField().getText();
		
		if(tableName == null || tableName.equals("")) return;
		
		newTablePanel(tableName);
	}
	
	public void newTablePanel(String tableName){
		
		frameLayout.setRowInfo(2, 625, 0, 10);
		Vector tableColTitles = new Vector();
		Vector tableRecords = new Vector();
		try{
			tableColTitles = Table.geTableFieldsComment(tableName, connection);
			tableRecords = Table.getTableRecords(tableName, null, connection);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JTablePanel tablePanel = new JTablePanel(tableName,tableColTitles, tableRecords, connection);
		
		tablePanel.repaint();
		frameLayout.add(tablePanel, 2, 600, 'B', 1, 1, 'L');
		
		frameLayout.setRowPos();
	}
	
	public JTextField getInputTableNameTextField() {
		return inputTableNameTextField;
	}

	public void setInputTableNameTextField(JTextField inputTableNameTextField) {
		this.inputTableNameTextField = inputTableNameTextField;
	}
	
/*	public static void main(String[] args){
		ConfigMenuEvent cme = new ConfigMenuEvent();
		cme.execute();
	}*/
}
