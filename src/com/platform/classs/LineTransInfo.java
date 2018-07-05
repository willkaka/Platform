package com.platform.classs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private JPanel transConfigPanel = new JPanel();
	private JPanel transEntryPanel = new JPanel();	
	private JScrollPane transConfigScroll = new JScrollPane(transConfigPanel);
	private JScrollPane transEntryScroll = new JScrollPane(transEntryPanel);
	
	private LayoutByRow transConfigSrcollLayout = new LayoutByRow(transConfigScroll);
	private LayoutByRow transEntrySrcollLayout = new LayoutByRow(transEntryScroll);
	
	private LayoutByRow transConfigPanelLayout = new LayoutByRow(transConfigPanel);
	private LayoutByRow transEntryPanelLayout = new LayoutByRow(transEntryPanel);
	
	private JPanel panel = new JPanel();
	private LayoutByRow panelLayout = new LayoutByRow(panel);
	
	private JComboBox lineListBox = new JComboBox();
	private JComboBox finListBox = new JComboBox();
	
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
		panelLayout.setRowInfo(1, 20, 10, 10);
		JLabel inputPromptLabel0 = new JLabel("数据环境：");
		panelLayout.add(inputPromptLabel0, 1, 65, 'N', 0, 0, 'L');
		
		JComboBox EnvListBox = new JComboBox();
		String[] envList = {"DEV","T10","ST41","ST84","ST106","ST192","UAT","84-232"};
		for(String line:envList){
			EnvListBox.addItem(line);
		}
		OracleDB db = new OracleDB("DEV");
		connection = db.getConnection(); //初始
		panelLayout.add(EnvListBox, 1, 60, 'N', 0, 0, 'L');
		EnvListBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				OracleDB db = new OracleDB(((JComboBox)e.getSource()).getSelectedItem().toString());
				connection = db.getConnection();
			}
		});
		
		JLabel inputPromptLabel1 = new JLabel("    合作机构：");
		panelLayout.add(inputPromptLabel1, 1, 78, 'N', 0, 0, 'L');
		
		
		for(String line:getLineList()){
			lineListBox.addItem(line);
		}
		panelLayout.add(lineListBox, 1, 250, 'N', 0, 0, 'L');
		lineListBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTransConfig(getSelectedLineID(), getSelectedFinType());
				resetCompPos();
			}
		});
		
		JLabel inputPromptLabel2 = new JLabel("    帐套类型：");
		panelLayout.add(inputPromptLabel2, 1, 78, 'N', 0, 0, 'L');
		
		String[] finList = {"LAS","IRR"};
		for(String fin:finList){
			finListBox.addItem(fin);
		}
		panelLayout.add(finListBox, 1, 60, 'N', 0, 0, 'L');
		finListBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTransConfig(getSelectedLineID(), getSelectedFinType());
				resetCompPos();
			}
		});
		
		
		panelLayout.setRowInfo(2, 100, 10, 10);
		panelLayout.add(transConfigScroll, 2, 300, 'V', 1, 0, 'L');
		panelLayout.add(transEntryScroll, 2, 500, 'V', 1, 0, 'L');
		
		panelLayout.setCompLayout(transConfigScroll, transConfigSrcollLayout);
		panelLayout.setCompLayout(transEntryScroll, transEntrySrcollLayout);
		
		transConfigSrcollLayout.setResetPos(false);
		transConfigSrcollLayout.setRowInfo(1, 100, 10, 10);
		transConfigSrcollLayout.add(transConfigPanel, 1, 100, 'B', 1, 1, 'L');
		transConfigSrcollLayout.setCompLayout(transConfigPanel, transConfigPanelLayout);
		
		transConfigScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		transEntrySrcollLayout.setResetPos(false);
		transEntrySrcollLayout.setRowInfo(1, 100, 10, 10);
		transEntrySrcollLayout.add(transEntryPanel, 1, 100, 'B', 1, 1, 'L');
		transEntrySrcollLayout.setCompLayout(transEntryPanel, transEntryPanelLayout);
		
		transEntryScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		frame.getRightPanel().addTab(tabName, panel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(panel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(panel, panelLayout);
		frame.getRightPanelLayout().setCompOthInfo(panel, tabName);
		
		resetCompPos();
	}
	
	public void showTransConfig(String lineid, String finType){
		List<String> TransActionList = getTransActionList(lineid,finType);
		transConfigPanel.removeAll();
		transConfigPanelLayout.removeAllComp();
		int lineNum = 0;
		for(String transAction:TransActionList){
			JLabel transActionLabel = new JLabel(transAction);
			transActionLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						String transid = ((JLabel)e.getSource()).getText().substring(5, 11);
						System.out.println("transid="+transid);
						showTransEntry(transid);
					}
				}
			});
			
			lineNum++;
			transConfigPanelLayout.setRowInfo(lineNum, 20, 5, 0);
			transConfigPanelLayout.add(transActionLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		transConfigPanelLayout.setRowPos();
		int w = transConfigScroll.getWidth()-40;
		int h = lineNum*25+10;
		System.out.println("");
		transConfigPanel.setPreferredSize(new Dimension(w, h));
		transConfigPanel.revalidate(); // 告诉其他部件,我的宽高变了
		
		this.frame.getRightPanel().repaint();
		this.frame.repaint();
 	}
	
	public void showTransEntry(String transid){
		List<String> TransEntryList = getTransEntryList(transid);
		transEntryPanel.removeAll();
		transEntryPanelLayout.removeAllComp();
		int lineNum = 0;
		for(String transEntry:TransEntryList){
			JLabel transEntryLabel = new JLabel(transEntry);
			
			lineNum++;
			transEntryPanelLayout.setRowInfo(lineNum, 20, 5, 0);
			transEntryPanelLayout.add(transEntryLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		transEntryPanelLayout.setRowPos();
		int w = transEntryScroll.getWidth()-40;
		int h = lineNum*25+10;
		
		transEntryPanel.setPreferredSize(new Dimension(w, h));
		transEntryPanel.revalidate(); // 告诉其他部件,我的宽高变了
		
		this.frame.getRightPanel().repaint();
		this.frame.repaint();
 	}
	
	public List<String> getLineList(){
		ArrayList<String> lineList = new ArrayList<String>();
		
		try {
			String sqlStm = "SELECT lineid,cltypename FROM cl_info_p";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				lineList.add(rs.getString(1) + ":" + rs.getString(2));
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lineList;
	}
	
	public List<String> getTransActionList(String lineid, String finType){
		ArrayList<String> TransActionList = new ArrayList<String>();
		
		try {
			String sqlStm = "SELECT tc.id,tc.transid,ta.name "
					+ "FROM trans_config tc,trans_action ta "
					+ "WHERE tc.bankcodeno=? AND tc.transid=ta.transid AND ta.accountset=? "
					+ "ORDER BY ta.transid";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStm);
			preparedStatement.setString(1, lineid);
			preparedStatement.setString(2, finType);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				TransActionList.add(rs.getString(1) + ":" + rs.getString(2) + ":" + rs.getString(3));
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TransActionList;
	}
	
	public List<String> getTransEntryList(String transid){
		ArrayList<String> TransEntryList = new ArrayList<String>();
		
		try {
			String sqlStm = "SELECT te.transid,te.sortid,te.direction,te.subjectno,te.digest,te.amount,te.validexpression "
					+ "FROM trans_entry te "
					+ "WHERE te.transid='" + transid +"' "
					+ "ORDER BY te.transid,te.sortid";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				TransEntryList.add(rs.getString(1) + "  " 
						          + rs.getString(2) + "  "
						          + rs.getString(3) + "  "
						          + rs.getString(4) + "  " 
						          + rs.getString(5) + "  " 
						          + rs.getString(6) + "  " 
						          + rs.getString(7));
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TransEntryList;
	}
	
	public String getSelectedLineID(){
		return lineListBox.getSelectedItem().toString().substring(0, 16);
	}
	
	public String getSelectedFinType(){
		return finListBox.getSelectedItem().toString();
	}
	
	public void resetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
	
	public void getTest(){
		ArrayList<String> TransActionList = new ArrayList<String>();
		
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
