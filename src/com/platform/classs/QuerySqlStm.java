package com.platform.classs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sqlite.SQLiteConnection;

import com.base.comp.TablePanel;
import com.base.database.DatabaseInfo;
import com.base.database.ExeSqlStm;
import com.base.database.MysqlDB;
import com.base.database.OracleDB;
import com.base.database.Table;
import com.base.database.TableField;
import com.base.layout.LayoutByRow;
import com.platform.view.MainFrame;

/**
 * 执行SQL语句查数
 * @author huangyuanwei
 *
 */
public class QuerySqlStm {
	private final String tabName = "执行SQL语句查数";
	
	private Connection sqliteConn = null;
	private Connection connection = null;
	private MainFrame frame = null;
	
	private JPanel mainPanel = new JPanel();
	private LayoutByRow mainPanelLayout = new LayoutByRow(mainPanel);
	
	private TablePanel tablePanel = null;
	
	private JTextArea sqlTextArea = new JTextArea();                 //SQL语句输入框
	private JScrollPane sqlTextAreaSrcoll = new JScrollPane();     //Table垂直滚动条
    
	private JButton queryButton = null;                     //查询按钮
	
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
		mainPanelLayout.setRowInfo(1, 20, 10, 10);
		mainPanelLayout.setRowGap(1, 10, 20, 10);
		JLabel inputPromptLabel0 = new JLabel("数据环境：");
		mainPanelLayout.add(inputPromptLabel0, 1, 65, 'N', 0, 0, 'L');
		
		JComboBox EnvListBox = new JComboBox();
		List<DatabaseInfo> envList = DatabaseInfo.getEnvList(this.sqliteConn); 
		for(DatabaseInfo env:envList){
			EnvListBox.addItem(env.getEnvname());
		}
		OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, "DEV"));
		connection = db.getConnection(); //初始
		mainPanelLayout.add(EnvListBox, 1, 100, 'N', 0, 0, 'L');
		EnvListBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				//OracleDB db = new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, ((JComboBox)e.getSource()).getSelectedItem().toString()));
				//connection = db.getConnection();
				String sEnv = ((JComboBox)e.getSource()).getSelectedItem().toString();
				DatabaseInfo databaseInfo = DatabaseInfo.getDatabaseInfo(sqliteConn, sEnv);
				
				connection = databaseInfo.getDBConnection(databaseInfo);
				
				//tableInfoJTable.removeAll();
				//tableInfoTableLayout.removeAllComp();
				resetCompPos();
			}
		});
		
		JLabel inputPromptLabel1 = new JLabel("请输入SQL语句后点击查询");
		mainPanelLayout.add(inputPromptLabel1, 1, 178, 'N', 0, 0, 'L');
		queryButton = new JButton("查询");
		queryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeSqlStm();
			}
		});
		mainPanelLayout.add(queryButton, 1, 100,'N',0,0,'R');
		//SQL语句输入框
		mainPanelLayout.setRowInfo(2, 100, 10, 10);
		mainPanelLayout.setRowGap(2, 10, 30, 0);
    	sqlTextArea.setFont(new Font("宋体", 0, 15));
    	sqlTextArea.setEditable(true);
    	sqlTextArea.setLineWrap(true);
    	sqlTextAreaSrcoll.setViewportView(sqlTextArea);
    	mainPanelLayout.add(sqlTextAreaSrcoll, 2, 300,'B',0.1f,1,'L');
		
		mainPanelLayout.setRowInfo(3, 100, 10, 10);
		mainPanelLayout.setRowGap(3, 0, 0, 0);
		tablePanel = new TablePanel(null, null);
		mainPanelLayout.add(tablePanel, 3, 300, 'B', 0.9f, 1, 'L');
		
		mainPanelLayout.setCompLayout(tablePanel, tablePanel.getTablePanelLayout());
		
		//tableInfoJTableSrcollLayout.setRowInfo(1, 100, 10, 10);
		//tableInfoJTableSrcollLayout.add(tableInfoJTable, 1, 100, 'B', 1, 1, 'L');
		//tableInfoJTableSrcollLayout.setCompLayout(tableInfoJTable, tableInfoTableLayout);
		
		//tableInfoJTableScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		frame.getRightPanel().addTab(tabName, mainPanel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(mainPanel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(mainPanel, mainPanelLayout);
		frame.getRightPanelLayout().setCompOthInfo(mainPanel, tabName);
		
		resetCompPos();
	}
	
	public void executeSqlStm(){
		String sSqlStm = sqlTextArea.getText();
		Vector<TableField> cols = new Vector<>();
		Vector rows = new Vector<>();
		
		if(sSqlStm == null || sSqlStm.isEmpty()){
			JOptionPane.showMessageDialog(null, "请输入SQL查询语句！");
			return;
		}
		try{
			ResultSet rstSqlStm = ExeSqlStm.getSqlResultSet(sSqlStm, connection);
			
			//取列名
			ResultSetMetaData metaData1 = rstSqlStm.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData1.getColumnCount(); fieldNum++){
				if(metaData1.getColumnName(fieldNum) != null && !"".equals(metaData1.getColumnName(fieldNum))){
					String fieldName = metaData1.getColumnName(fieldNum);
					TableField tableField = new TableField();
					tableField.setFieldName(fieldName);
					tableField.setFieldType(metaData1.getColumnTypeName(fieldNum));
					//tableField.setFieldDsc(metaData1.getCatalogName(fieldNum));//数据库名称
					//tableField.setFieldDsc(metaData1.getColumnClassName(fieldNum));//字段类型的className
					tableField.setTable(metaData1.getTableName(fieldNum));
					//System.out.println(metaData1.getTableName(fieldNum));
					tableField.setFieldDsc(Table.getTableFieldCommentsMysql(metaData1.getTableName(fieldNum), tableField.getFieldName(), connection));
					
					//System.out.println(metaData1.getSchemaName(fieldNum));
					
					cols.add(tableField);
				}
			}
			
			//取记录
			while(rstSqlStm.next()){
				Vector<String> tableRecord = new Vector<String>();
				ResultSetMetaData metaData = rstSqlStm.getMetaData();
				for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
					if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
						String fieldName = metaData.getColumnName(fieldNum);
						Object fieldValue = rstSqlStm.getObject(fieldName);
						
						//找到字段set方法，并调用赋值。
						if(fieldValue == null){
							tableRecord.add("");
						}else{
							tableRecord.add(fieldValue.toString());
						}
					}
				}
				rows.add(tableRecord);
			}
			tablePanel.setTablePanelData(cols, rows);
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"执行出错："+e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"执行出错："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void resetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
}
