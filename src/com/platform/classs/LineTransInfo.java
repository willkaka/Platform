package com.platform.classs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.database.OracleDB;
import com.base.database.Table;
import com.base.database.TableField;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

import oracle.jdbc.util.RepConversion;

public class LineTransInfo {
	private final String tabName = "合作机构交易配置查询";
	
	private Connection sqliteConn = null;
	private Connection connection = null;

	private MainFrame frame = null;
//	private JPanel transConfigPanel = new JPanel();
//	private JPanel transActionPanel = new JPanel();
//	private JPanel transEntryPanel = new JPanel();	
/*	private JScrollPane transConfigScroll = new JScrollPane(transConfigPanel);
	private JScrollPane transActionScroll = new JScrollPane(transActionPanel);
	private JScrollPane transEntryScroll = new JScrollPane(transEntryPanel);*/
	private JScrollPane transConfigScroll = new JScrollPane();
	private JScrollPane transActionScroll = new JScrollPane();
	private JScrollPane transEntryScroll = new JScrollPane();
	private LayoutByRow transConfigLayout = new LayoutByRow(transConfigScroll);
	private LayoutByRow transActionLayout = new LayoutByRow(transActionScroll);
	private LayoutByRow transEntryLayout = new LayoutByRow(transEntryScroll);
	
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
		JLabel inputPromptLabel = new JLabel("合作机构：");
		panelLayout.add(inputPromptLabel, 1, 80, 'N', 0, 0, 'L');
		
		JComboBox lineListBox = new JComboBox();
		for(String line:genLineList()){
			lineListBox.addItem(line);
		}
		panelLayout.add(lineListBox, 1, 250, 'N', 0, 0, 'L');
		lineListBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTransConfig(((String) ( (JComboBox) e.getSource()).getSelectedItem()).substring(0, 16));
				reSetCompPos();
			}
		});
		
		panelLayout.setRowInfo(2, 100, 10, 10);
		panelLayout.add(transConfigScroll, 2, 300, 'V', 1, 0, 'L');
		panelLayout.add(transActionScroll, 2, 80, 'V', 1, 0, 'L');
		panelLayout.add(transEntryScroll, 2, 80, 'V', 1, 0, 'L');
		
		panelLayout.setCompLayout(transConfigScroll, transConfigLayout);
		panelLayout.setCompLayout(transActionScroll, transActionLayout);
		panelLayout.setCompLayout(transEntryScroll, transEntryLayout);
		
		frame.getRightPanel().addTab(tabName, panel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(panel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(panel, panelLayout);
		frame.getRightPanelLayout().setCompOthInfo(panel, tabName);
		
		reSetCompPos();
	}
	
	public void showTransConfig(String lineid){
		List<String> TransActionList = getTransActionList(lineid);
		JPanel panel = new JPanel();
		LayoutByRow panelLayout = new LayoutByRow(panel);
		
		transConfigLayout.setRowInfo(1, 200, 10, 10);
		transConfigLayout.add(panel, 1, 400, 'V', 1, 0, 'L');
		transConfigLayout.setCompLayout(panel, panelLayout);
		
		//frame.getRightPanelLayout().setCompLayout(transActionScroll, panelLayout);
		
		int lineNum = 0;
		for(String transAction:TransActionList){
			JLabel transActionLabel = new JLabel(transAction);
			lineNum++;
			panelLayout.setRowInfo(lineNum, 20, 5, 0);
			panelLayout.add(transActionLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		
		panel.repaint();
		transActionScroll.repaint();
		this.frame.repaint();
	}
	
	public List<String> genLineList(){
		ArrayList<String> lineList = new ArrayList<String>();
		
		OracleDB db = new OracleDB("DEV");
		this.connection = db.getConnection();
		try {
			String sqlStm = "SELECT lineid,cltypename FROM cl_info_p";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				lineList.add(rs.getString(1) + ":" + rs.getString(2));
			}
			rs.close();
			preparedStatement.close();
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lineList;
	}
	
	public List<String> getTransActionList(String lineid){
		ArrayList<String> TransActionList = new ArrayList<String>();
		
		OracleDB db = new OracleDB("DEV");
		this.connection = db.getConnection();
		try {
			String sqlStm = "SELECT tc.id,tc.transid,ta.name "
					+ "FROM trans_config tc,trans_action ta "
					+ "WHERE tc.bankcodeno=? AND tc.transid=ta.transid";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStm);
			preparedStatement.setString(1, lineid);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				TransActionList.add(rs.getString(1) + ":" + rs.getString(2) + ":" + rs.getString(3));
			}
			rs.close();
			preparedStatement.close();
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TransActionList;
	}
	
	public void reSetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
	
	public void getTest(){
		ArrayList<String> TransActionList = new ArrayList<String>();
		
		OracleDB db = new OracleDB("T10");
		this.connection = db.getConnection();
		try {
			double dPayFeeInte=0d,dPayFeeCorp=0d;
			String sqlStm = "select payfeeinte,payfeecorp from business_putout  WHERE serialno='RL20150817000010'";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				dPayFeeInte = rs.getDouble("payfeeinte");
				dPayFeeCorp = rs.getDouble("payfeecorp");
			}
			rs.close();
			preparedStatement.close();
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
