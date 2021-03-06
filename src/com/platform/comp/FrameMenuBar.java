package com.platform.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.base.bean.MenuConfig;
import com.base.database.ClassUtil;
import com.platform.view.MainFrame;

public class FrameMenuBar extends JMenuBar {

	public MainFrame frame = null;
	public Connection conn_Sqlite = null;
	public Class<?> cls = null;
	public String className = null;
	public String methodName = null;

	public FrameMenuBar(MainFrame frame, Connection conn_Sqlite) {
		this.frame = frame;
		this.conn_Sqlite = conn_Sqlite;

		setMenuBar();
	}

	/**
	 * 读取Sqlite中菜单栏配置信息，加载菜单项
	 */
	public void setMenuBar(){
		// 读取配置的菜单信息
		Vector<MenuConfig> menuConfigs = MenuConfig.getMenuConfigByLevel("topmenu",0, conn_Sqlite);
		for (MenuConfig mc : menuConfigs) {
			JMenu menu = new JMenu(mc.getText());
			this.add(menu);
			Vector<MenuConfig> subMenuConfigs = MenuConfig.getSubMenuConfig(mc.getMenuCode(), conn_Sqlite);
			for (MenuConfig submc : subMenuConfigs) {
				JMenuItem menuItem = new JMenuItem(submc.getText());
				className = submc.getClassName();
				methodName = submc.getClassMethod();
				menuItem.setActionCommand(submc.getMenuid());
				
				//菜单项增加事件监听
				menuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						// System.out.println("selected.");
						String actionMenuId = e.getActionCommand();
						itemAction(actionMenuId);
						//e.
					}
				});
				//将菜单项加载到菜单栏中
				menu.add(menuItem);
			}
		}
	}
	
	/**
	 * 菜单事件，调用配置的类方法
	 * @param actionMenuId菜单项ID
	 */
	public void itemAction(String actionMenuId){
		//取该菜单项的配置信息
		MenuConfig menuConfig = MenuConfig.getMenuConfig(actionMenuId, getConn_Sqlite());
		System.out.println("actionMenuId="+actionMenuId +"  classname:" +menuConfig.getClassName());
		
		//加载要调用的类
		ClassLoader classLoader = ClassUtil.class.getClassLoader();
		try {
			// Class<?> clazz = Class.forName(className);
			Class<?> cls = classLoader.loadClass(menuConfig.getClassName());

			// 取配置的类方法
			System.out.println("methodName:" + menuConfig.getClassMethod());
			Method method3 = cls.getDeclaredMethod(menuConfig.getClassMethod(), getFrame().getClass(), getConn_Sqlite().getClass());// 取方法时，需要传方法参数类名

			// 创建类对象
			Object clazzObject = cls.newInstance();

			// 调用类中的方法
			Object obj1 = method3.invoke(clazzObject, getFrame(), getConn_Sqlite()); // 有参数方法调用。invoke都有返回值。
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		//this.removeAll();
		//setMenuBar();
	}
	
	public Connection getConn_Sqlite() {
		return conn_Sqlite;
	}

	public void setConn_Sqlite(Connection conn_Sqlite) {
		this.conn_Sqlite = conn_Sqlite;
	}
	
	public MainFrame getFrame() {
		return frame;
	}
}
