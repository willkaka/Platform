package com.base.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.base.database.SqliteDB;

public class LeftMenuConfig {
	private String menuid = null;
	private String menuCode = null;
	private String text = null;
	private String className = null;
	private String classMethod = null;
	private int level = 0;
	private String pearentMenuCode = null;
	private Connection connection = null;
	
	public LeftMenuConfig(){
		
	}
	
	public LeftMenuConfig(String pearentMenuCode, Connection connection){
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
	
	public static LeftMenuConfig getMenuConfig(String menuId, Connection connection){
		LeftMenuConfig menu = null;
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig where menuid = '" + menuId + "'");
			ResultSet set = pSql.executeQuery();
			
			if (set.next()) {
				menu = new LeftMenuConfig();
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentmenucode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menu;		
	}
	
	public static Vector<LeftMenuConfig> getMenuConfigs(Connection connection){
		Vector<LeftMenuConfig> menus = new Vector<LeftMenuConfig>();
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig");
			ResultSet set = pSql.executeQuery();
			
			while (set.next()) {
				LeftMenuConfig menu = new LeftMenuConfig();
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentmenucode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
				menus.addElement(menu);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;		
	}
	
	public static Vector<LeftMenuConfig> getMenuConfigByLevel(int level, Connection connection){
		Vector<LeftMenuConfig> menus = new Vector<LeftMenuConfig>();
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig where level = " + level +" order by menuid");
			ResultSet set = pSql.executeQuery();
			
			while (set.next()) {
				LeftMenuConfig menu = new LeftMenuConfig();
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentmenucode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
				menus.addElement(menu);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;		
	}
	
	public static Vector<LeftMenuConfig> getSubMenuConfig(String pearentMenuCode, Connection connection){
		Vector<LeftMenuConfig> menus = new Vector<LeftMenuConfig>();
		
		try{
			PreparedStatement pSql = connection.prepareStatement("select * from menuconfig where pearentMenuCode = '" + pearentMenuCode.trim() + "' order by menuid");
			ResultSet set = pSql.executeQuery();
			
			while (set.next()) {
				LeftMenuConfig menu = new LeftMenuConfig(pearentMenuCode, connection);
				menu.setMenuid(set.getString("menuid"));
				menu.setMenuCode(set.getString("menuCode"));
				menu.setLevel(set.getInt("level"));
				menu.setPearentMenuCode(set.getString("pearentMenuCode"));
				menu.setText(set.getString("text"));
				menu.setClassName(set.getString("className"));
				menu.setClassMethod(set.getString("classMethod"));
				menus.addElement(menu);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;		
	}
	
	public static void createMenuConfigTable() throws Exception{
		if(SqliteDB.isExistTable("menuconfig")) return;
		
		SqliteDB.exeSql("CREATE TABLE menuconfig (menuid varchar(10),menucode varchar(10),level number(6,0),pearentMenuCode varchar(10),text varchar(50),classname varchar(100),classMethod varchar(100))");
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
