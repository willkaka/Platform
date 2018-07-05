package com.platform.view;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.sqlite.SQLiteConnection;

import com.base.database.*;
import com.base.layout.LayoutByRow;

public class CrtTableWindow2 {
	private Connection connection = null;
	
	private JFrame frame = new JFrame();
	
	private JPanel fieldPanel = new JPanel();
	private JScrollPane fieldScrollPane = new JScrollPane(fieldPanel);
	private LayoutByRow frameLayout = null;
	
	private LayoutByRow fieldScrollLayout = null;
	private LayoutByRow fieldPanelLayout = null;

	private int curLineCount = 0; 
	
	//public CrtTableWindow(SQLiteConnection connection) {
	public void execute(JFrame frame1, Connection connection){
		this.connection = connection;
		frame.setTitle("CreateTable");
		frame.setBounds(450, 200, 530, 625);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setLayout(null);
		
		frameLayout = new LayoutByRow(frame);
		frameLayout.setRowInfo(1, 200, 10, 10);
		frameLayout.add(fieldScrollPane, 1, 10, 'B', 1, 1, 'L');
		frameLayout.setRowPos();
		
		fieldScrollLayout = new LayoutByRow(fieldScrollPane);
		frameLayout.setCompLayout(fieldScrollPane, fieldScrollLayout);
		fieldScrollLayout.setResetPos(false);
		
		fieldPanelLayout = new LayoutByRow(fieldPanel);
		addOneFieldDef();
		
		fieldPanel.setPreferredSize(new Dimension(fieldScrollPane.getWidth(), fieldScrollPane.getHeight()));
		
		fieldScrollLayout.setRowInfo(1, 200, 10, 10);
		fieldScrollLayout.add(fieldPanel, 1, 200, 'B', 1, 1, 'L');
		fieldScrollLayout.setCompLayout(fieldPanel, fieldPanelLayout);
		
		frameLayout.setRowPos();
		frame.setVisible(true);
	}
	
	public void addOneFieldDef(){
		int layoutRow = fieldPanelLayout.getRowCount() + 1;
		
		fieldPanelLayout.setRowInfo(layoutRow, 15, 15, 10);
		JLabel fieldSeqLabel = new JLabel((layoutRow) + ".");
		fieldPanelLayout.add(fieldSeqLabel, layoutRow, 25, 'N', 0, 0, 'L');
				
		fieldPanelLayout.setRowPos();
		
		frame.repaint();
	}
	
	
	public static void main(String[] args){
		Connection conn_Sqlite = SqliteDB.getConnection("./data/Database.db");
		CrtTableWindow2 mntTableWindow = new CrtTableWindow2();
		mntTableWindow.execute(new JFrame(), conn_Sqlite);
	}
}
