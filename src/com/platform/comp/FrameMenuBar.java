package com.platform.comp;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.ws.AsyncHandler;

import com.base.bean.MenuConfig;
import com.base.database.ClassUtil;
import com.base.database.Table;
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

	public void setMenuBar(){
		// ��ȡ���õĲ˵���Ϣ
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
				menu.add(menuItem);
			}
		}
	}
	
	public void itemAction(String actionMenuId){
		MenuConfig menuConfig = MenuConfig.getMenuConfig(actionMenuId, getConn_Sqlite());
		System.out.println("actionMenuId="+actionMenuId +"  classname:" +menuConfig.getClassName());
		ClassLoader classLoader = ClassUtil.class.getClassLoader();
		try {
			// Class<?> clazz = Class.forName(className);
			Class<?> cls = classLoader.loadClass(menuConfig.getClassName());

			// ȡ���õ��෽��
			System.out.println("methodName:" + menuConfig.getClassMethod());
			Method method3 = cls.getDeclaredMethod(menuConfig.getClassMethod(), getFrame().getClass(), getConn_Sqlite().getClass());// ȡ����ʱ����Ҫ��������������

			// ���������
			Object clazzObject = cls.newInstance();

			// �������еķ���
			Object obj1 = method3.invoke(clazzObject, getFrame(), getConn_Sqlite()); // �в����������á�invoke���з���ֵ��
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
