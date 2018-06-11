package com.platform.comp;

import java.awt.Font;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.base.bean.MenuConfig;
import com.base.comp.JTablePanel;
import com.base.database.Table;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class TreeMenuPanel extends JPanel{
	
	private MainFrame mainFrame = null;
	private Connection connection = null;
	
	private LayoutByRow panelLayout =  new LayoutByRow(this);

	private JComboBox menuBox = new JComboBox();
	//表格及滚动条
	public JScrollPane tableScrollPane = new JScrollPane();
	public JTable table = new JTable();
	
	public TreeMenuPanel(MainFrame mainFrame, Connection connection){
		this.mainFrame = mainFrame;
		this.connection = connection;
		
		loadLeftMenuComp();
	}
	
	public void loadLeftMenuComp(){
		panelLayout.setRowInfo(1, 20, 20, 10);
	    panelLayout.add(menuBox, 1, 100, 'H', 0, 1.0f, 'L');
		
		Vector<MenuConfig> menuConfigs = MenuConfig.getMenuConfigByLevel("leftmenu",0, connection);
		Vector menulist = new Vector();
		for(MenuConfig menu: menuConfigs) {
			Vector<String> m = new Vector<String>();
			m.add(menu.getMenuid() + " " + menu.getText());
			//m.add(menu.getMenuid() );
			
			menulist.add(m);
		}
		
		Vector<String> tableHeader = new Vector<String>();
		tableHeader.add("123 ");
		
		
		//表格属性
		table.setAutoscrolls(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("SimSun", 0, 12));
                
        //将数据放进table中
        DefaultTableModel model = new DefaultTableModel(menulist, tableHeader);
        table.setModel(model);
        table.getTableHeader().setVisible(false);  
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        
        //设置列宽
        //JTablePanel.fitTableColumns(table);
        //设置表格颜色
        JTablePanel.setTableColor(table);
        
        table.repaint();
        table.updateUI(); 
        
        //表格垂直滚动条
        tableScrollPane.setViewportView(table);
        panelLayout.setRowInfo(2, 100, 10, 10);
        panelLayout.add(tableScrollPane, 2, 100, 'B', 1, 1, 'L');
        
        panelLayout.setRowPos();
		this.repaint();
	}
	
	public LayoutByRow getPanelLayout() {
		return panelLayout;
	}
	
}
