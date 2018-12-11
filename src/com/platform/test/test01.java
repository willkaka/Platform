package com.platform.test;

import java.sql.Connection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.base.comp.TablePanel;
import com.base.database.SqliteDB;
import com.base.database.Table;

public class test01 {
	public static void main(String[] args){
		JFrame frame = new JFrame("test");
		Connection conn = SqliteDB.getConnection();
				
		String tableName = "menuconfig";
		Vector cols = null;
		Vector rows = null;
		try{
			cols = Table.getTableFieldsComment(tableName, null, conn);
			rows = Table.getTableRecords(tableName, null, conn);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		TablePanel tPanel = new TablePanel(rows, cols);
		frame.setBounds(450, 200, 530, 625);
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		frame.setVisible(true);
		
	}

}
