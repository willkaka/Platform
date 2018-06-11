package com.platform.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.base.bean.Menuconfig;
import com.base.database.*;
import oracle.jdbc.util.SQLStateMapping;
import com.base.layout.LayoutByRow;

public class MntTableWindow2 {
	private Connection connection = null;
	private String table = "menuconfig";
	private JFrame frame= new JFrame();
	private MainFrame frame_org= null;
	private JPanel tableInfoPanel = new JPanel();
	private JPanel fieldListPanel = new JPanel();
	private JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 1000);

	private Vector<Object> valueList = new Vector<Object>();
	private char oprCode = 'U'; //U-更新记录/I-写记录；
	
	private LayoutByRow titleBoundLayout = null;
	private LayoutByRow detailBoundLayout = null;
	private LayoutByRow frameLayout = null;
	
	public MntTableWindow2(Connection connection,String tableName) {
		// 
		do{
			if(tableName == null || tableName.equals("")){
				tableName = JOptionPane.showInputDialog(null, "请输入需要维护的数据表名称：");
			}
		}while(tableName == null || tableName.equals(""));
		
		System.out.println("数据表：" + tableName);
		DatabaseMetaData md;
		try {
			md = connection.getMetaData();
			md.getDriverName();
			System.out.println("数据库：" + md.getDriverName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		frame.setTitle("MaintainTable");
		frame.setBounds(450, 200, 530, 625);
		// setResizable(false); //不允许改变窗口大小
		// 设置退出
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		// 取消框架格式
		frame.setLayout(null);
		
		titleBoundLayout = new LayoutByRow(frame);
		titleBoundLayout.setRowInfo(1, 15, 10, 10);
		JLabel tabelNameLabel = new JLabel(tableName);
		titleBoundLayout.add(tabelNameLabel, 1, 50, 'N', 0, 0, 'L');
		
		detailBoundLayout = new LayoutByRow(frame);
		detailBoundLayout.setRowInfo(1, 400, 10, 10);
		JTable detailTable = new JTable();
		detailTable.setAutoscrolls(true);
		detailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		detailTable.setFont(new Font("SimSun", 0, 12));
		
		try {
			Vector<TableField> fields = Table.geTableFields(tableName, connection);
			Vector<String> fieldComments = Table.geTableFieldsComment(tableName, connection);
			Table.createTableClass(tableName, connection);
			Menuconfig menuConfig = new Menuconfig();
			System.out.println(menuConfig.getClass().getName());
			Vector records = Table.getTableRecords(tableName, null, connection);
			DefaultTableModel model = new DefaultTableModel(records, fieldComments);
			detailTable.setModel(model);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		Connection conn_Sqlite = SqliteDB.getConnection("./data/Database.db");
		MntTableWindow2 mntTableWindow = new MntTableWindow2(conn_Sqlite, "menuconfig");
	}

}
