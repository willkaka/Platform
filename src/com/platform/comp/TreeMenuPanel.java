package com.platform.comp;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.soap.Node;

import com.base.bean.MenuConfig;
import com.base.bean.Menuconfig2;
import com.base.comp.JTablePanel;
import com.base.database.ClassUtil;
import com.base.database.Table;
import com.base.function.StringUtil;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

public class TreeMenuPanel extends JPanel{
	
	private MainFrame mainFrame = null;
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	private Connection connection = null;
	
	public Connection getConnection() {
		return connection;
	}

	private LayoutByRow panelLayout =  new LayoutByRow(this);

	private JComboBox menuBox = new JComboBox();
	
	//表格及滚动条
	private JPanel tablePanel = new JPanel();
	private LayoutByRow tablePaneLayout =  new LayoutByRow(tablePanel);
	private JScrollPane tableScrollPane = new JScrollPane(tablePanel);
	private LayoutByRow tableScrollPaneLayout =  new LayoutByRow(tableScrollPane);
	
	public TreeMenuPanel(MainFrame mainFrame, Connection connection){
		this.mainFrame = mainFrame;
		this.connection = connection;
		
/*		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e) {
				getMainFrame().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});*/
		
		loadLeftMenuComp();
	}
	
	public void loadLeftMenuComp2(){
		
	}
	
	public void loadLeftMenuComp(){
		panelLayout.setBotGap(10);
		panelLayout.setRowInfo(1, 20, 20, 0);
		panelLayout.setRowGap(1, 0, 0, 0);
	    panelLayout.add(menuBox, 1, 100, 'H', 0, 1.0f, 'L');
	    
	    DefaultMutableTreeNode root = new DefaultMutableTreeNode("000000 菜单树");
	    JTree tree = new JTree(root);
		
		Vector<MenuConfig> menuConfigs = MenuConfig.getMenuConfigByLevel("leftmenu",0, connection);
		int rowNum = 0;
		for(MenuConfig menu: menuConfigs) {
			menuBox.addItem(menu.getMenuid() + " " + menu.getText());
			menuBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox tempMenuBox = (JComboBox) e.getSource(); 
					invokeMethod(tempMenuBox.getSelectedItem().toString().substring(0,7));
				}
			});
			
			DefaultMutableTreeNode node = getAllChildNode(menu);
			root.add(node);			
		}
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent evt) {
		    	// 获取被选中的相关节点
		        TreePath path = evt.getPath();
		        TreePath[] paths = evt.getPaths();
		        TreePath newLeadPath = evt.getNewLeadSelectionPath();
		        TreePath oldLeadPath = evt.getOldLeadSelectionPath();
		        
		        DefaultMutableTreeNode oSelectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
		        System.out.println(oSelectedNode.getUserObject().toString());
		        String sMenuInfo = oSelectedNode.getUserObject().toString();
		        if(sMenuInfo != null && sMenuInfo.length() >= 7){
		        	String sMenuID = sMenuInfo.substring(0,7);
		        	invokeMethod(sMenuID);
		        }
		    }
		});
		tablePaneLayout.setRowInfo(++rowNum, 500, 10, 10);
		tablePaneLayout.add(tree, rowNum, 300, 'N', 0, 1, 'L');
		

        panelLayout.setRowInfo(2, 200, 10, 0);
        panelLayout.setRowGap(2, 0, 0, 0);
        panelLayout.add(tableScrollPane, 2, 170, 'B', 1, 1, 'L');
        panelLayout.setCompLayout(tableScrollPane, tableScrollPaneLayout);
        //tableScrollPaneLayout.setResetPos(false);
        tableScrollPane.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e) {
				getMainFrame().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
        
        tableScrollPane.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
        
        tablePanel.setPreferredSize(new Dimension(tableScrollPane.getWidth(), rowNum * 28 + 10));
        
        tableScrollPaneLayout.setRowInfo(1, 200, 5, 0);
        tableScrollPaneLayout.setRowGap(1, 0, 0, 0);
        tableScrollPaneLayout.add(tablePanel, 1, 200, 'B', 1, 1, 'L');
        tableScrollPaneLayout.setCompLayout(tablePanel, tablePaneLayout);
        
        mainFrame.getFrameLayout().setRowPos();
		this.repaint();
	}
	
	public DefaultMutableTreeNode getAllChildNode(MenuConfig menuconfig){
		DefaultMutableTreeNode node = null; 
		if(menuconfig != null){
			node = new DefaultMutableTreeNode(menuconfig.getMenuid() + " " + menuconfig.getText());
			Vector<MenuConfig> subNodes = MenuConfig.getSubMenuConfig(menuconfig.getMenuCode(),connection);
			if(subNodes != null){
				for(MenuConfig menu:subNodes){
					DefaultMutableTreeNode subnode = getAllChildNode(menu);
					
					if(subnode != null) node.add(subnode);
				}
			}
		}
		return node;
	}
	
	public void invokeMethod(String menucode){
		MenuConfig menuConfig = MenuConfig.getMenuConfig(menucode, connection);
		if(menuConfig == null || menuConfig.getClassName() == null || StringUtil.isNull(menuConfig.getClassName())){
			System.out.println("菜单码："+menucode+"不存在！");
			return;
		}
		System.out.println("..............."+menuConfig.getClassName()+"....................");
		
		System.out.println("目前支持的参数：");
		System.out.println(getMainFrame().getClass().getName());
		System.out.println(getConnection().getClass().getName());
		System.out.println(this.getClass().getName());
		System.out.println("其它暂不支持！");
		
		Vector<String> parms = ClassUtil.getMethodParms(menuConfig.getClassName(), menuConfig.getClassMethod(), connection);
		Class<?>[] parmclasss = new Class<?>[parms.size()];
		Object[] parmobjects = new Object[parms.size()];
		int index = 0;
		for(String classname:parms){
			if(classname.equals(getMainFrame().getClass().getName())){
				parmclasss[index] = getMainFrame().getClass();
				parmobjects[index] = getMainFrame();
			}else if(classname.equals(getConnection().getClass().getName())){
				parmclasss[index] = getConnection().getClass();
				parmobjects[index] = getConnection();
			}else if(classname.equals(this.getClass().getName())){
				parmclasss[index] = this.getClass();
				parmobjects[index] = this;
			}else{
				System.out.println("配置的参数："+classname+",调度程序没有。");
				return;
			}
			index++;
		}
		//执行测试方法
		try {
			ClassLoader classLoader = ClassUtil.class.getClassLoader();
			Class<?> cls = classLoader.loadClass(menuConfig.getClassName());
			Object result = ClassUtil.invoke(cls, menuConfig.getClassMethod(), parmclasss, parmobjects);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
	}
	
	public LayoutByRow getPanelLayout() {
		return panelLayout;
	}
	
}
