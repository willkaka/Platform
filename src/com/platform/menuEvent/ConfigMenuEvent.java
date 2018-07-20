package com.platform.menuEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.sqlite.SQLiteConnection;

import com.base.bean.MenuConfig;
import com.base.comp.JTablePanel;
import com.base.database.SqliteDB;
import com.base.database.Table;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class ConfigMenuEvent {
	
	JFrame configFrame = new JFrame("配置信息");
	LayoutByRow frameLayout = new LayoutByRow(configFrame);
	
	public void execute(MainFrame frame, SQLiteConnection connection){
		System.out.println("configMenuEvent");
		
		configFrame.setBounds(450, 200, 530, 625);
		configFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		configFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {    //窗口大小改变事件
				frameLayout.setRowPos();
			}
		});
		
		frameLayout.setRowInfo(1, 625, 0, 10);
		frameLayout.setBotGap(20);
		frameLayout.setRowGap(1, 10, 5, 5);
		
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
		frameLayout.setCompLayout(tablePanel, tablePanel.getPanelLayout());
		
		frameLayout.setRowPos();
		
		configFrame.setVisible(true);
		configFrame.repaint();
	}
	
/*	public static void main(String[] args){
		ConfigMenuEvent cme = new ConfigMenuEvent();
		cme.execute();
	}*/
}
