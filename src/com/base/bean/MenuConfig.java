package com.base.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.base.database.SqliteDB;
import com.base.database.Table;

/**
    * Menuconfig()实体类
    * Sat Jun 09 14:27:46 CST 2018
    * @author huangyuanwei
    */ 
public class MenuConfig {
	private String menuid = null;
	private String menuCode = null;
	private String text = null;
	private String className = null;
	private String classMethod = null;
	private int level = 0;
	private String pearentMenuCode = null;
	private Connection connection = null;
	
	public MenuConfig(){
		
	}
	
	public MenuConfig(String pearentMenuCode, Connection connection){
		try{
			this.connection = connection;
			
			PreparedStatement pSql = connection.prepareStatement("select Max(menuid) as menuid from menuconfig where pearentMenuCode = '" 
																	+ pearentMenuCode + "' group by pearentMenuCode");
			ResultSet set = pSql.executeQuery();
			String menuid = "";
			if (set.next()) {
				menuid = set.getString("menuid");
			}
			
			setMenuid(menuid.substring(0, 2) + (Integer.parseInt(menuid.substring(2,menuid.length()-1))+1));
			setPearentMenuCode(pearentMenuCode);
			
			PreparedStatement pSql0 = connection.prepareStatement("select level from menuconfig where MenuCode = '" + pearentMenuCode +"' ");
			ResultSet set0 = pSql0.executeQuery();
			int level = 0;
			if (set0.next()) {
				level = set0.getInt("level");
			}
			setLevel(level + 1);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MenuConfig getMenuConfig(String menuId, Connection connection){
		MenuConfig menu = new MenuConfig();
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig where menuid = '" + menuId + "'");
			ResultSet set = pSql.executeQuery();
			
			if (set.next()) {
				menu = Table.parseResultSet(set, menu);
			}
			/*if (set.next()) {
				menu = new MenuConfig();
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentmenucode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menu;		
	}
	
	public static Vector<MenuConfig> getMenuConfigs(Connection connection){
		Vector<MenuConfig> menus = new Vector<MenuConfig>();
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig");
			ResultSet set = pSql.executeQuery();
			
			while (set.next()) {
				MenuConfig menu = new MenuConfig();
				menu = Table.parseResultSet(set, menu);
				menus.addElement(menu);
			}
			
			/*while (set.next()) {
				MenuConfig menu = new MenuConfig();
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentmenucode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
				menus.addElement(menu);
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;		
	}
	
	public static Vector<MenuConfig> getMenuConfigByLevel(String menutype, int level, Connection connection){
		Vector<MenuConfig> menus = new Vector<MenuConfig>();
		if(!Table.isExist("menuconfig", connection)){
			try {
				createMenuConfigTable();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig where menutype = '" 
										+ menutype + "' and level = " + level +" order by menuid");
			ResultSet set = pSql.executeQuery();
			
			while (set.next()) {
				MenuConfig menu = new MenuConfig();
				menu = Table.parseResultSet(set, menu);
				menus.addElement(menu);
			}
			
			/*while (set.next()) {
				MenuConfig menu = new MenuConfig();
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentmenucode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
				menus.addElement(menu);
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;		
	}
	
	public static Vector<MenuConfig> getSubMenuConfig(String pearentMenuCode, Connection connection){
		Vector<MenuConfig> menus = new Vector<MenuConfig>();
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig where pearentMenuCode = '" + pearentMenuCode.trim() + "' order by menuid");
			ResultSet set = pSql.executeQuery();
			
			while (set.next()) {
				MenuConfig menu = new MenuConfig(pearentMenuCode, connection);
				menu = Table.parseResultSet(set, menu);
				menus.addElement(menu);
			}
			
			/*while (set.next()) {
				MenuConfig menu = new MenuConfig(pearentMenuCode, connection);
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentMenuCode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
				menus.addElement(menu);
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;		
	}
	
	public static void createMenuConfigTable() throws Exception{
		if(SqliteDB.isExistTable("menuconfig")) return;
		
		SqliteDB.exeSql("CREATE TABLE menuconfig (menuid varchar2(10),menucode varchar2(15),"
				+ "menutype varchar2(10),level int(3),pearentmenucode varchar2(15),text varchar2(100),"
				+ "classname varchar2(100),classmethod varchar2(20))");
		SqliteDB.exeSql("insert into menuconfig (menuid,menucode,menutype,level,pearentmenucode,text,classname,classmethod)" + 
				" values('MU01000','Config','topmenu',0,'','配置','','')");
		SqliteDB.exeSql("insert into menuconfig (menuid,menucode,menutype,level,pearentmenucode,text,classname,classmethod)" + 
				" values('MU01001','MenuConfig','topmenu',1,'Config','配置菜单文件','com.platform.menuEvent.ConfigMenuEvent','execute')");
		SqliteDB.exeSql("insert into menuconfig (menuid,menucode,menutype,level,pearentmenucode,text,classname,classmethod)" + 
				" values('MU01002','MntConfigFile','topmenu',1,'','维护配置文件','com.platform.menuEvent.OpenTableEvent','execute')");
		SqliteDB.exeSql("insert into menuconfig (menuid,menucode,menutype,level,pearentmenucode,text,classname,classmethod)" + 
				" values('MU01003','CrtConfigFile','topmenu',1,'','创建配置文件','com.platform.view.CrtTableWindow','execute')");
		//CREATE TABLE classmethodparms(classname VARCHAR2(100),methodname VARCHAR2(50),parmseq VARCHAR(10),parmclass VARCHAR(100) )
	}
	
	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPearentMenuCode() {
		return pearentMenuCode;
	}
	public void setPearentMenuCode(String pearentMenuCode) {
		this.pearentMenuCode = pearentMenuCode;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassMethod() {
		return classMethod;
	}

	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}
	
}

