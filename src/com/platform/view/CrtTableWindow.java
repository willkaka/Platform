package com.platform.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.sqlite.SQLiteConnection;

import com.base.database.*;
import com.base.layout.LayoutByRow;

public class CrtTableWindow {
	private Connection connection = null;
	
	private JFrame frame = new JFrame();
	private JPanel titlePanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	private JScrollPane fieldScrollPane = new JScrollPane(fieldPanel);
	
	private LayoutByRow frameLayout = new LayoutByRow(frame);
	private LayoutByRow titlePanelLayout = new LayoutByRow(titlePanel);
	private LayoutByRow fieldScrollLayout = new LayoutByRow(fieldScrollPane);
	private LayoutByRow fieldPanelLayout = new LayoutByRow(fieldPanel);

	private JTextField tableNameTextField = new JTextField();

	private int curLineCount = 0; 
	
	//public CrtTableWindow(SQLiteConnection connection) {
	public void execute(MainFrame frame1, SQLiteConnection connection){
		this.connection = connection;
		
		frame.setTitle("CreateTable");
		frame.setBounds(450, 200, 530, 625);
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {    //���ڴ�С�ı��¼�
				frameLayout.setRowPos();
			}
		});
		
		titlePanelLayout.setRowInfo(1, 25, 10, 10);
		titlePanelLayout.setRowGap(1, 0, 0, 5);
		JLabel tabelNameLabel = new JLabel("��������Ҫ�����ı�����");
		titlePanelLayout.add(tabelNameLabel, 1, 150, 'N', 0, 0, 'L');
		tableNameTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getTableInfo();
			}
		});
		titlePanelLayout.add(tableNameTextField, 1, 80, 'N', 0, 0, 'L');
		
		JButton deleteButton = new JButton("ɾ�����");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteTable();
			}
		});
		titlePanelLayout.add(deleteButton, 1, 90, 'N', 0, 0, 'R');
		
		JButton createButton = new JButton("�������");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createTable();
			}
		});
		titlePanelLayout.add(createButton, 1, 90, 'N', 0, 0, 'R');
		
		titlePanelLayout.setRowInfo(2, 20, 10, 10);
		titlePanelLayout.setRowGap(2, 0, 0, 5);
		JLabel titleLabel = new JLabel("seq    fieldName            type                            len       dec        comment");
		titlePanelLayout.add(titleLabel, 2, 400, 'N', 0, 0, 'L');
		JButton addFieldButton = new JButton("+");
		addFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addOneFieldDef(null);
			}
		});
		titlePanelLayout.add(addFieldButton, 2, 40, 'N', 0, 0, 'R');
		
		//��ʼҳ����ʾһ���ֶζ���
		//addOneFieldDef();
		
		titlePanelLayout.setRowPos();
		fieldPanelLayout.setRowPos();
		
		frameLayout.setRowInfo(1, 80, 10, 10);
		frameLayout.add(titlePanel, 1, 10, 'H', 0, 1, 'L');
		frameLayout.setCompLayout(titlePanel, titlePanelLayout);
		
		frameLayout.setRowInfo(2, 400, 10, 10);
		frameLayout.setBotGap(40);
		
		//������
		fieldScrollPane.getVerticalScrollBar().setUnitIncrement(20); //���ù�����������
		
		frameLayout.add(fieldScrollPane, 2, 10, 'B', 1, 1, 'L');
		frameLayout.setCompLayout(fieldScrollPane, fieldScrollLayout);
		//frameLayout.setRowPos();
		
		fieldPanel.setPreferredSize(new Dimension(fieldScrollPane.getWidth(), fieldScrollPane.getHeight()));
		fieldPanel.revalidate(); // ������������,�ҵĿ�߱���
		fieldScrollLayout.setRowInfo(1, 200, 10, 10);
		fieldScrollLayout.add(fieldPanel, 1, 200, 'B', 1, 1, 'L');
		fieldScrollLayout.setCompLayout(fieldPanel, fieldPanelLayout);
		
		titlePanelLayout.setRowPos();
		fieldPanelLayout.setRowPos();
		
		frameLayout.setRowPos();
		frame.setVisible(true);
	}
	
	public void getTableInfo(){
		String tableName = getTableNameTextField().getText();
		if(tableName==null || tableName.equals("")){
			return;
		}
		try {
			if(!SqliteDB.isExistTable(tableName)){
				JOptionPane.showMessageDialog(null, tableName+"������");
			}else{
				Vector<TableField> fields = Table.geTableFields(tableName, null, connection);
				for(TableField field:fields){
					addOneFieldDef(field);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * ��ȡҳ���ֶζ�����Ϣ�������ݿ��д������ݱ�
	 */
	public void deleteTable(){
		String delTableName = getTableNameTextField().getText();
		if(delTableName==null || delTableName.equals("")){
			JOptionPane.showMessageDialog(null, "ɾ���������Ʋ�����Ϊ��.");
			return;
		}
		Table.deleteTable(delTableName, connection);
		JOptionPane.showMessageDialog(null, delTableName+"ɾ���ɹ���");
	}
	
	/**
	 * ��ȡҳ���ֶζ�����Ϣ�������ݿ��д������ݱ�
	 */
	public void createTable(){
		String delTableName = getTableNameTextField().getText();
		if(delTableName==null || delTableName.equals("")){
			JOptionPane.showMessageDialog(null, "������񣬱����Ʋ�����Ϊ��.");
			return;
		}
		Vector lines = getFieldPanelLayout().getCompValuesByLine();
		Vector<TableField> fields = new Vector<TableField>();
		for(int i=0; i< lines.size() ;i++){
			Vector<String> record = (Vector<String>) lines.get(i);
			if(record != null && record.get(1) != null && !record.get(1).equals("")){
				TableField field = new TableField();
				field.setFieldName(record.get(0));
				field.setFieldType(record.get(1));
				
				//����
				if (record.get(2)== null || record.get(2).equals("") || record.get(2).equals("0")){
					//����
					JOptionPane.showMessageDialog(null, "�ֶΣ�"+record.get(0)+",���ݳ��Ȳ�����Ϊ��.");
					return;
				}else{
					field.setFieldLen(Integer.parseInt(record.get(2)));
				}
				
				//С��λ
				if(	record.get(1).equals("NUMBER") ||
					record.get(1).equals("DOUBLE") ||
					record.get(1).equals("FLOAT") ){
					if (record.get(3)== null || record.get(3).equals("")){
						//����
						JOptionPane.showMessageDialog(null, "�ֶΣ�"+record.get(0)+",����Ϊ��"+ record.get(1)+",С��λ������Ϊ��.");
						return;
					}else{
						field.setFieldDec(Integer.parseInt(record.get(3)));
						
					}
				}
				field.setFieldDsc(record.get(4));				
				
				fields.add(field);
			}
		}
		
		Table.createTable(delTableName, fields, connection);
		JOptionPane.showMessageDialog(null, delTableName+"�����ɹ���");
	}
	
	public void addOneFieldDef(TableField field){
		int layoutRow = getFieldPanelLayout().getRowCount() + 1;
		
		getFieldPanelLayout().setRowInfo(layoutRow, 15, 15, 10);
		JLabel fieldSeqLabel = new JLabel((layoutRow) + ".");
		getFieldPanelLayout().add(fieldSeqLabel, layoutRow, 25, 'N', 0, 0, 'L');
		JTextField fieldNameTextField = new JTextField();
		getFieldPanelLayout().add(fieldNameTextField, layoutRow, 80, 'N', 0, 0, 'L');
		JComboBox fieldTypeComboBox = new JComboBox();
		fieldTypeComboBox.addItem("VARCHAR");
		fieldTypeComboBox.addItem("VARCHAR2");
		fieldTypeComboBox.addItem("NUMBER");
		fieldTypeComboBox.addItem("DOUBLE");
		fieldTypeComboBox.addItem("FLOAT");
		fieldTypeComboBox.addItem("CHAR");
		getFieldPanelLayout().add(fieldTypeComboBox, layoutRow, 100, 'N', 0, 0, 'L');
		JTextField fieldLenTextField = new JTextField();
		getFieldPanelLayout().add(fieldLenTextField, layoutRow, 30, 'N', 0, 0, 'L');
		JTextField fieldDecTextField = new JTextField();
		getFieldPanelLayout().add(fieldDecTextField, layoutRow, 30, 'N', 0, 0, 'L');
		JTextField fieldCommTextField = new JTextField();
		getFieldPanelLayout().add(fieldCommTextField, layoutRow, 100, 'N', 0, 0, 'L');
		
		JButton delFieldButton = new JButton("del");
		delFieldButton.setActionCommand(Integer.toString(layoutRow));
		delFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton tempButton = (JButton) e.getSource();
				int linenum = Integer.parseInt(tempButton.getActionCommand());
				Vector fieldvalues = getFieldPanelLayout().getCompValuesAtLine(linenum);
				try {
					Vector<TableField> fields = Table.geTableFields(getTableNameTextField().getText(), null, connection);
					TableField field = fields.get(linenum);
					//�ֶ��������
					if(!field.getFieldName().toLowerCase().equals(fieldvalues.get(1).toString().toLowerCase())){
						
					}else if(!field.getFieldType().toLowerCase().equals(fieldvalues.get(2).toString().toLowerCase())){
						
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		getFieldPanelLayout().add(delFieldButton, layoutRow, 20, 'N', 0, 0, 'L');
		
		if(field != null){
			fieldNameTextField.setText(field.getFieldName());
			fieldTypeComboBox.setSelectedItem(field.getFieldType().toUpperCase());
			fieldLenTextField.setText(Integer.toString(field.getFieldLen()));
			fieldDecTextField.setText(Integer.toString(field.getFieldDec()));
			fieldCommTextField.setText(field.getFieldDsc());
		}
		
		getFieldPanelLayout().setRowPos();
		
		fieldPanel.setPreferredSize(new Dimension(fieldScrollPane.getWidth()-40, layoutRow*30+30));
		fieldPanel.revalidate(); // ������������,�ҵĿ�߱���
		
		//frame.repaint();
		frameLayout.setRowPos();
	}
	
	public LayoutByRow getFrameLayout() {
		return frameLayout;
	}

	public void setFrameLayout(LayoutByRow frameLayout) {
		this.frameLayout = frameLayout;
	}
	
	
	public LayoutByRow getTitlePanelLayout() {
		return titlePanelLayout;
	}
	
	
	public LayoutByRow getFieldPanelLayout() {
		return fieldPanelLayout;
	}
	
	
	public JTextField getTableNameTextField() {
		return tableNameTextField;
	}

	public void setTableNameTextField(JTextField tableNameTextField) {
		this.tableNameTextField = tableNameTextField;
	}
	
/*	public static void main(String[] args){
		Connection conn_Sqlite = SqliteDB.getConnection("./data/Database.db");
		CrtTableWindow mntTableWindow = new CrtTableWindow((SQLiteConnection)conn_Sqlite);
	}*/
}
