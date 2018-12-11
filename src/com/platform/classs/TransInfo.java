package com.platform.classs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
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
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.sqlite.SQLiteConnection;

import com.base.database.DatabaseInfo;
import com.base.database.OracleDB;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

/**
 * 由TransNo查询TransID信息
 * @author huangyuanwei
 *
 */
public class TransInfo {
	private final String tabName = "交易信息查询";
	
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
	
	//private JComboBox lineListBox = new JComboBox();
	private JTextField transNoField = new JTextField();
	//private JComboBox finListBox = new JComboBox();
	
	private int entryCount = 0;
	
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
		List<DatabaseInfo> envList = DatabaseInfo.getEnvList(this.sqliteConn); 
		for(DatabaseInfo env:envList){
			EnvListBox.addItem(env.getEnvname());
		}
		OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, "DEV"));
		connection = db.getConnection(); //初始
		panelLayout.add(EnvListBox, 1, 100, 'N', 0, 0, 'L');
		EnvListBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, ((JComboBox)e.getSource()).getSelectedItem().toString()));
				connection = db.getConnection();
				
				transConfigPanel.removeAll();
				transConfigPanelLayout.removeAllComp();
				transEntryPanel.removeAll();
				transEntryPanelLayout.removeAllComp();
				resetCompPos();
			}
		});
		
		JLabel inputPromptLabel1 = new JLabel("    TransNo：");
		panelLayout.add(inputPromptLabel1, 1, 78, 'N', 0, 0, 'L');
		
		panelLayout.add(transNoField, 1, 100, 'N', 0, 0, 'L');
		transNoField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTransConfig( getInputTransNo() );				
				resetCompPos();
			}
		});
		
		panelLayout.setRowInfo(2, 100, 10, 10);
		panelLayout.setRowGap(2, 0, 0, 0);
		panelLayout.add(transConfigScroll, 2, 300, 'V', 1, 0, 'L');
		panelLayout.add(transEntryScroll, 2, 500, 'B', 1, 1, 'L');
		
		panelLayout.setCompLayout(transConfigScroll, transConfigSrcollLayout);
		panelLayout.setCompLayout(transEntryScroll, transEntrySrcollLayout);
		
		transConfigSrcollLayout.setRowInfo(1, 100, 10, 10);
		transConfigSrcollLayout.add(transConfigPanel, 1, 100, 'B', 1, 1, 'L');
		transConfigSrcollLayout.setCompLayout(transConfigPanel, transConfigPanelLayout);
		
		transConfigScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
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
	
	public void showTransConfig(String transNo ){
		List<String> TransActionList = getTransActionList(transNo );
		transConfigPanelLayout.removeAllComp();
		int lineNum = 0;
		for(String transAction:TransActionList){
			JLabel transActionLabel = new JLabel(transAction);
			transActionLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						String transid = ((JLabel)e.getSource()).getText().substring(0, 6);
						//System.out.println("transid="+transid);
						showTransEntry(transid);
					}
				}
			});
			
			lineNum++;
			transConfigPanelLayout.setRowInfo(lineNum, 20, 5, 0);
			transConfigPanelLayout.add(transActionLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		transConfigPanelLayout.setRowPos();
		
		transConfigPanel.setPreferredSize(new Dimension(transConfigScroll.getWidth()-40, lineNum*25+10));
		transConfigPanel.revalidate(); // 告诉其他部件,我的宽高变了
		
		this.frame.getRightPanel().repaint();
		this.frame.repaint();
 	}
	
	public void showTransEntry(String transid){
		
		Component[] comps = transEntryPanel.getComponents();
		for(Component comp:comps){
			//System.out.println(comp.getName());
			if(comp.getName().toLowerCase().equals(transid.toLowerCase())) return;
		}
		
		JPanel entryPanel = new JPanel();
		entryPanel.setName(transid);
		TitledBorder border = BorderFactory.createTitledBorder(transid);
		border.setTitleJustification(TitledBorder.CENTER);
		entryPanel.setBorder(border);
		LayoutByRow entryPanelLayout = new LayoutByRow(entryPanel);
		entryPanelLayout.setTopGap(15);
		entryPanelLayout.setBotGap(5);
		
		List<String> TransEntryList = getTransEntryList(transid);
		//transEntryPanelLayout.removeAllComp();
		
		CloseXIcon xIcon = new CloseXIcon(null);
		JLabel xIconLabel = new JLabel(xIcon);
		xIconLabel.setName(transid);
		xIconLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String transid = ((JLabel)e.getSource()).getName();
				Component[] comps = transEntryPanel.getComponents();
				for(Component comp:comps){
					//System.out.println(comp.getName());
					if(comp.getName().toLowerCase().equals(transid.toLowerCase())) {
						transEntryPanel.remove(comp);
						transEntryPanelLayout.removeComp((JComponent) comp);
						transEntryPanel.repaint();
						transEntryPanel.updateUI();
						entryCount--;
						resetCompPos();
						break;
					}
				}
			}
		});
		entryPanelLayout.setRowInfo(1, 10, 0, 0);
		entryPanelLayout.add(xIconLabel, 1, 10, 'N', 0, 0, 'L');
		
		int lineNum = 1;
		for(String transEntry:TransEntryList){
			JLabel transEntryLabel = new JLabel(transEntry);
			
			lineNum++;
			entryPanelLayout.setRowInfo(lineNum, 20, 1, 0);
			entryPanelLayout.add(transEntryLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		transEntryPanelLayout.setRowInfo(++entryCount, lineNum * 25+10, 5, 0);
		transEntryPanelLayout.add(entryPanel, entryCount, 200, 'H', 0, 1, 'L');
		transEntryPanelLayout.setCompLayout(entryPanel, entryPanelLayout);
		
		transEntryPanelLayout.setRowPos();
		int w = transEntryScroll.getWidth()-40;
		int h = transEntryPanelLayout.getLayoutHeight() + lineNum * 25 + 10 + 10;
		
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
	
	public List<String> getTransActionList(String transNo ){
		ArrayList<String> TransActionList = new ArrayList<String>();
		
		try {
			String sqlStm1 = "SELECT * FROM trans_action ta WHERE ta.id =? order by ta.transid";
			PreparedStatement preparedStatement = null;
			preparedStatement = this.connection.prepareStatement(sqlStm1);
			preparedStatement.setString(1, transNo);
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
	
	public void resetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
	
	public String getInputTransNo(){
		return transNoField.getText();
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
	
	class CloseXIcon implements Icon {
	    private int x_pos;
	    private int y_pos;
	    private int width;
	    private int height;
	    private Icon fileIcon;
	    public CloseXIcon(Icon fileIcon) {
	        this.fileIcon = fileIcon;
	        width = 16;
	        height = 16;
	    }
	    @Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
	        this.x_pos = x;
	        this.y_pos = y;
	        Color col = g.getColor();
	        g.setColor(Color.black);
	        int y_p = y + 2;
	        //g.drawLine(x + 1, y_p, x + 12, y_p);
	        //g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
	        //g.drawLine(x, y_p + 1, x, y_p + 12);
	        //g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
	        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
	        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
	        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
	        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
	        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
	        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
	        g.setColor(col);
	        if (fileIcon != null) {
	            fileIcon.paintIcon(c, g, x + width, y_p);
	        }
	    }
	    @Override
		public int getIconWidth() {
	        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
	    }
	    @Override
		public int getIconHeight() {
	        return height;
	    }
	    public Rectangle getBounds() {
	        return new Rectangle(x_pos, y_pos, width, height);
	    }
	}
}
