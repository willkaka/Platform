package com.platform.comp;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.base.bean.MenuConfig;
import com.base.comp.JTablePanel;
import com.base.database.ClassUtil;
import com.base.database.Table;
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
	private JScrollPane tableScrollPane = new JScrollPane();
	private LayoutByRow tableScrollPaneLayout =  new LayoutByRow(tableScrollPane);
	/*private JTable table = new JTable();*/
	
	
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
		int rowNum = 0;
		for(MenuConfig menu: menuConfigs) {
			Vector<String> m = new Vector<String>();
			m.add(menu.getMenuid() + " " + menu.getText());
			//m.add(menu.getMenuid() );
			JLabel oprCodeLabel = new JLabel();
			oprCodeLabel.setText(menu.getMenuid() + " " + menu.getText());
			oprCodeLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//点击事件
					JLabel tempJLabel = (JLabel) e.getSource(); 
					invokeMethod(tempJLabel.getText().substring(0,7));
				}
			});
			tableScrollPaneLayout.setRowInfo(++rowNum, 25, 3, 0);
			tableScrollPaneLayout.add(oprCodeLabel, rowNum, 100, 'H', 0, 1, 'L');
			menulist.add(m);
		}
		Vector<String> tableHeader = new Vector<String>();
		tableHeader.add("123 ");

        panelLayout.setRowInfo(2, 100, 10, 10);
        panelLayout.add(tableScrollPane, 2, 100, 'B', 1, 1, 'L');
        panelLayout.setCompLayout(tableScrollPane, tableScrollPaneLayout);
        
        //panelLayout.setRowPos();
		this.repaint();
	}
	
	public void invokeMethod(String menucode){
		MenuConfig menuConfig = MenuConfig.getMenuConfig(menucode, connection);
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
