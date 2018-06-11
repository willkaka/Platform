package com.platform.menuEvent;

import java.sql.Connection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.sqlite.SQLiteConnection;

import com.base.bean.Menuconfig;
import com.base.comp.JTablePanel;
import com.base.database.SqliteDB;
import com.base.database.Table;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class ConfigMenuEvent {
	public void execute(MainFrame frame, SQLiteConnection connection){
		System.out.println("configMenuEvent");
		//public void com.platform.menuEvent.ConfigMenuEvent.execute()
		
		JFrame configFrame = new JFrame("配置信息");
		configFrame.setLayout(null);
		LayoutByRow frameLayout = new LayoutByRow(configFrame);
		frameLayout.setRowInfo(1, 625, 0, 10);
		frameLayout.setBotGap(20);
		
		configFrame.setBounds(450, 200, 530, 625);
		
		Vector tableColTitles = new Vector();
		Vector tableRecords = new Vector();
		try{
			tableColTitles = Table.geTableFieldsComment("menuconfig", connection);
			tableRecords = Table.getTableRecords("menuconfig", null, connection);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		//填充数据
		JTablePanel tablePanel = new JTablePanel("menuconfig",tableColTitles, tableRecords, connection);
		frameLayout.add(tablePanel, 1, 600, 'B', 1, 1, 'L');
		
		frameLayout.setRowPos();
		
		
		configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		configFrame.setVisible(true);
		configFrame.repaint();
		
		//frame.getMenuBar().removeAll();
		//frame.getMenuBar().setMenuBar();
	}
	
/*	public static void main(String[] args){
		ConfigMenuEvent cme = new ConfigMenuEvent();
		cme.execute();
	}*/
}
