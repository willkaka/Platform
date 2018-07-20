package com.platform.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.security.PrivilegedActionException;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.base.comp.JClosableTabbedPane;
import com.base.database.OracleDB;
import com.base.database.SqliteDB;
import com.base.layout.LayoutByRow;
import com.platform.comp.FrameMenuBar;
import com.platform.comp.TreeMenuPanel;
import com.platform.compEvent.FrameMotionListener;
import com.platform.compEvent.FrameMouseListener;
import com.platform.compEvent.FrameResizedListener;

public class MainFrame {
	
	private Connection conn_Sqlite = null;
	private Connection conn_Oracle = null;
	
	private JFrame frame = new JFrame();
	private LayoutByRow frameLayout = null;
	
	//private JPanel leftPanel = new JPanel();
	private TreeMenuPanel leftPanel = null;
	//private JTabbedPane rightPanel = new JTabbedPane();
	private JClosableTabbedPane rightPanel = new JClosableTabbedPane();
	private LayoutByRow rightPanelLayout = new LayoutByRow(rightPanel);

	private FrameMenuBar menuBar = null;

	public MainFrame(){
		conn_Sqlite = SqliteDB.getConnection("./data/Database.db");
		conn_Oracle = OracleDB.getConnection();
		
		frame.setTitle("工具操作平台");
		frame.setLayout(null);
		frameLayout = new LayoutByRow(frame);
		frameLayout.setRowInfo(1, 625, 0, 10);
		frameLayout.setBotGap(80);
		
		//建立菜单栏
		menuBar = new FrameMenuBar(this, conn_Sqlite);
		frame.setJMenuBar(menuBar);
		
		//左面板
		leftPanel = new TreeMenuPanel(this, conn_Sqlite);
		TitledBorder leftPanelBorder = BorderFactory.createTitledBorder("");
		leftPanel.setBorder(leftPanelBorder); //设置面板边框，实现分组框的效果，此句代码为关键代码  
		frameLayout.add(leftPanel, 1, 190, 'N', 1, 1, 'L');
		frameLayout.setCompLayout(leftPanel, leftPanel.getPanelLayout());
		
		//右面板
		rightPanel.setBorder(BorderFactory.createTitledBorder(""));
		frameLayout.add(rightPanel, 1, 420, 'B', 1, 1, 'L');
		frameLayout.setCompLayout(rightPanel, rightPanelLayout);
		
		frame.setBounds(200, 200, 1200, 700);
		//frameLayout.setRowPos(frame.getWidth(), frame.getHeight());
		reSetCompPos();
		
		//设置鼠标事件
		frame.addMouseListener(new FrameMouseListener());
		//设置鼠标移动事件
		frame.addMouseMotionListener(new FrameMotionListener(this));
		//设置窗口大小改变事件
		frame.addComponentListener(new FrameResizedListener(this));
		
		//frame.setResizable(false);  //不允许改变窗口大小
		//设置退出
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.repaint();
	}
	
	public void repaint(){
		frame.repaint();
	}
	
	public void reSetCompPos() {
		frameLayout.setRowPos();
		getLeftPanel().getPanelLayout().setRowPos();
		
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public LayoutByRow getFrameLayout() {
		return frameLayout;
	}


	public TreeMenuPanel getLeftPanel() {
		return leftPanel;
	}

	public JClosableTabbedPane getRightPanel() {
		return rightPanel;
	}
	
	public FrameMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(FrameMenuBar menuBar) {
		this.menuBar = menuBar;
	}
	
	public LayoutByRow getRightPanelLayout() {
		return rightPanelLayout;
	}
	
	public static void main(String[] args){
		MainFrame mainFrame=new MainFrame();
	}
}
