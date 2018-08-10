package com.platform.classs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.SecureClassLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.sqlite.SQLiteConnection;

import com.base.database.DatabaseInfo;
import com.base.database.OracleDB;
import com.base.function.StringUtil;
import com.base.function.SystemOpr;
import com.base.layout.LayoutByRow;
import com.platform.classs.LineTransInfo.CloseXIcon;
import com.platform.view.MainFrame;

/**
 * Git仓库提交信息查询
 * @author huangyuanwei
 *
 */
public class GitCommitFileList {
	private final String tabName = "Git提交清单查询";
	
	private Connection sqliteConn = null;
	//private Connection connection = null;

	private MainFrame frame = null;
	
	private JPanel mainPanel = new JPanel();
	private LayoutByRow mainPanelLayout = new LayoutByRow(mainPanel);
	
	private JPanel commitListPanel = new JPanel();
	private JPanel fileListPanel = new JPanel();
	private LayoutByRow commitListPanelLayout = new LayoutByRow(commitListPanel);
	private LayoutByRow fileListPanelLayout = new LayoutByRow(fileListPanel);
	
	private JScrollPane commitListScroll = new JScrollPane(commitListPanel);
	private JScrollPane fileListScroll = new JScrollPane(fileListPanel);
	private LayoutByRow commitListSrcollLayout = new LayoutByRow(commitListScroll);
	private LayoutByRow fileListSrcollLayout = new LayoutByRow(fileListScroll);
	
	private JTextField localPathTextField = new JTextField("D:/Java/DaShuSource/DSPM_20180720");
	private String localPath = "D:/Java/DaShuSource/DSPM_20180720";
	
	private JFileChooser fileChooser = new JFileChooser();

	//private JTextField localPathTextField = new JTextField();
	private JComboBox commitUserBox = new JComboBox<>();
	private String commitUser = "ALL";
	
	private int entryCount = 0;
	private String actionflag = "";
	
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
		JLabel inputPromptLabel0 = new JLabel("本地项目文件位置：");
		mainPanelLayout.add(inputPromptLabel0, 1, 130, 'N', 0, 0, 'L');
		
		mainPanelLayout.add(localPathTextField, 1, 250, 'N', 0, 0, 'L');
		localPathTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionflag = "localPathTextField";
				JTextField tempfield = (JTextField) e.getSource();
				setLocalPath(tempfield.getText());
				
				if(getLocalPath() != null && !"".equals(getLocalPath())){
					showCommitList(getLocalPath(),getCommitUser());
					resetCompPos();
				} else{
					//本地项目文件位置不允许为空。
				}
				actionflag = "";
			}
		});
		
		JButton selectFileButton = new JButton("选择Git仓库");
		selectFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("------on---------"+fileChooser.getSelectedFile());
				fileChooser.setCurrentDirectory(new File("D:/Java/DaShuSource"));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(null);
				setLocalPath(fileChooser.getSelectedFile().getPath());
				localPathTextField.setText(fileChooser.getSelectedFile().getPath());
			}
		}); 
		mainPanelLayout.add(selectFileButton, 1, 110, 'N', 0, 0, 'L');
		
		JLabel inputPromptLabel1 = new JLabel("提交用户：");
		mainPanelLayout.add(inputPromptLabel1, 1, 70, 'N', 0, 0, 'L');
		
		mainPanelLayout.add(commitUserBox, 1, 150, 'N', 0, 0, 'L');
		commitUserBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!"localPathTextField".equals(actionflag)){
					JComboBox tempbox = (JComboBox) e.getSource();
					setCommitUser(tempbox.getSelectedItem().toString());
					
					if(getLocalPath() != null && !"".equals(getLocalPath())){
						showCommitList(getLocalPath(),getCommitUser());
						resetCompPos();
					} else{
						//本地项目文件位置不允许为空。
					}
				}else{
					actionflag="";
				}
			}
		});
		
		JButton showUserAllFileBut = new JButton("用户提交程序清单");
		showUserAllFileBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getCommitUser() != null && !"".equals(getCommitUser()))
					showUserAllFileList(getLocalPath(),getCommitUser());
			}
		});
		mainPanelLayout.add(showUserAllFileBut, 1, 150, 'N', 0, 0, 'L');
		
		mainPanelLayout.setRowInfo(2, 100, 10, 10);
		mainPanelLayout.setRowGap(2, 0, 0, 0);
		mainPanelLayout.add(commitListScroll, 2, 300, 'V', 1, 0, 'L');
		mainPanelLayout.add(fileListScroll, 2, 500, 'B', 1, 1, 'L');
		
		mainPanelLayout.setCompLayout(commitListScroll, commitListSrcollLayout);
		mainPanelLayout.setCompLayout(fileListScroll, fileListSrcollLayout);
		
		commitListSrcollLayout.setRowInfo(1, 100, 10, 10);
		commitListSrcollLayout.add(commitListPanel, 1, 100, 'B', 1, 1, 'L');
		commitListSrcollLayout.setCompLayout(commitListPanel, commitListPanelLayout);
		
		commitListScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		fileListSrcollLayout.setRowInfo(1, 100, 10, 10);
		fileListSrcollLayout.add(fileListPanel, 1, 100, 'B', 1, 1, 'L');
		fileListSrcollLayout.setCompLayout(fileListPanel, fileListPanelLayout);
		
		fileListScroll.getVerticalScrollBar().setUnitIncrement(20); //设置滚动条滚动量
		
		frame.getRightPanel().addTab(tabName, mainPanel);
		frame.getRightPanelLayout().setRowInfo(1, 200, 5, 5);
		frame.getRightPanelLayout().add(mainPanel, 1, 100, 'B', 1, 1, 'L');
		frame.getRightPanelLayout().setCompLayout(mainPanel, mainPanelLayout);
		frame.getRightPanelLayout().setCompOthInfo(mainPanel, tabName);
		
		resetCompPos();
	}
	
	public void showCommitList(String gitLocalDir, String commitUser){
		List<String> commitList = getCommitList(gitLocalDir,"ALL");
		List<String> commitUsers = new ArrayList<>();
		
		commitListPanel.removeAll();
		commitListPanelLayout.removeAllComp();
		commitUserBox.removeAllItems();
		commitUserBox.addItem("ALL");
		
		int lineNum = 0;
		for(String commitInfo:commitList){
			String[] infos = commitInfo.split("#");
			
			if(!commitUsers.contains(infos[1])){
				commitUsers.add(infos[1]);
				commitUserBox.addItem(infos[1]);
			}
			
			if(commitUser != null && !"".equals(commitUser) && !"ALL".equals(commitUser) && !infos[1].equals(commitUser))
				continue;
			
			JLabel commitInfoLabel = new JLabel();
			//System.out.println(commitInfo);
			commitInfoLabel.setText("<html><body>"+infos[1] + " " +infos[2] +"<p>"+ infos[3]+"<body></html>");  //1-name  2-time 3-comment
			//commitInfoLabel.setText(infos[1] + " " +infos[2] +System.getProperty("line.separator")+ infos[3]);  //1-name  2-time 3-comment
			commitInfoLabel.setName(infos[0]); //commitid
			commitInfoLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						String commitID = ((JLabel)e.getSource()).getName();//0-40
						showFileList(commitID,infos[1],infos[2]);
					}
				}
			});
			
			lineNum++;
			commitListPanelLayout.setRowInfo(lineNum, 40, 5, 0);
			commitListPanelLayout.setRowGap(lineNum, 5, 0, 5);
			commitListPanelLayout.add(commitInfoLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		commitUserBox.setSelectedItem(commitUser);
		
		commitListPanelLayout.setRowPos();
		
		commitListPanel.setPreferredSize(new Dimension(commitListScroll.getWidth()-40, lineNum*25+10));
		commitListPanel.revalidate(); // 告诉其他部件,我的宽高变了
		
		this.frame.getRightPanel().repaint();
		this.frame.repaint();
 	}
	
	public void showFileList(String commitID,String user,String time){
		
		List<DiffEntry> filelist = null;
		try {
			filelist = getFileList(localPathTextField.getText(),commitID);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Component[] comps = fileListPanel.getComponents();
		for(Component comp:comps){
			//System.out.println(comp.getName());
			if(comp.getName().toLowerCase().equals(commitID.toLowerCase())) return;
		}
		
		JPanel filesPanel = new JPanel();
		filesPanel.setName(commitID);
		TitledBorder border = BorderFactory.createTitledBorder(user +" "+ time);
		border.setTitleJustification(TitledBorder.CENTER);
		filesPanel.setBorder(border);
		LayoutByRow entryPanelLayout = new LayoutByRow(filesPanel);
		entryPanelLayout.setTopGap(15);
		entryPanelLayout.setBotGap(5);
		
		CloseXIcon xIcon = new CloseXIcon(null);
		JLabel xIconLabel = new JLabel(xIcon);
		xIconLabel.setName(commitID);
		xIconLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String commitid = ((JLabel)e.getSource()).getName();
				Component[] comps = fileListPanel.getComponents();
				for(Component comp:comps){
					if(comp.getName().toLowerCase().equals(commitid.toLowerCase())) {
						fileListPanel.remove(comp);
						fileListPanelLayout.removeComp((JComponent) comp);
						fileListPanel.repaint();
						fileListPanel.updateUI();
						entryCount--;
						resetCompPos();
						break;
					}
				}
			}
		});
		entryPanelLayout.setRowInfo(1, 10, 0, 0);
		entryPanelLayout.add(xIconLabel, 1, 10, 'N', 0, 0, 'L');

		JLabel copyLabel = new JLabel("CopyToClip");
		copyLabel.setName(commitID);
		copyLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String commitid = ((JLabel)e.getSource()).getName();
				Component[] comps = fileListPanel.getComponents();
				for(Component comp:comps){
					if(comp.getName().toLowerCase().equals(commitid.toLowerCase())) {
						//copy
						Component[] labels = ((JPanel)comp).getComponents();
						String ss = "";
						for(Component label:labels ){
							if(label.getClass().getName().equals("javax.swing.JLabel")){
								if(((JLabel) label).getText() != null && !"CopyToClip".equals(((JLabel) label).getText()))
									ss = ss + ((JLabel) label).getText() + "\n";
							}
						}
						SystemOpr.setSysClipboardText(ss);
						break;
					}
				}
			}
		});
		entryPanelLayout.setRowGap(1, 5, 0, 10);
		entryPanelLayout.add(copyLabel, 1, 100, 'N', 0, 0, 'L');
		
		int lineNum = 1;
		for(DiffEntry entry:filelist){
			JLabel entryLabel = new JLabel(entry.getOldPath());
			
			lineNum++;
			entryPanelLayout.setRowInfo(lineNum, 20, 1, 0);
			entryPanelLayout.add(entryLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		fileListPanelLayout.setRowInfo(++entryCount, lineNum * 21, 5, 0);
		fileListPanelLayout.add(filesPanel, entryCount, 200, 'H', 0, 1, 'L');
		fileListPanelLayout.setCompLayout(filesPanel, entryPanelLayout);
		
		fileListPanelLayout.setRowPos();
		int w = fileListScroll.getWidth()-40;
		int h = fileListPanelLayout.getLayoutHeight() + lineNum * 21 + 10;
		
		fileListPanel.setPreferredSize(new Dimension(w, h));
		fileListPanel.revalidate(); // 告诉其他部件,我的宽高变了
		
		this.frame.getRightPanel().repaint();
		this.frame.repaint();
 	}
	
	public void showUserAllFileList(String gitLocalDir, String commitUser){
		
		Component[] comps = fileListPanel.getComponents();
		for(Component comp:comps){
			if(comp.getName().toLowerCase().equals(commitUser.toLowerCase())) return;
		}
		
		JPanel filesPanel = new JPanel();
		filesPanel.setName(commitUser);
		TitledBorder border = BorderFactory.createTitledBorder(commitUser);
		border.setTitleJustification(TitledBorder.CENTER);
		filesPanel.setBorder(border);
		LayoutByRow entryPanelLayout = new LayoutByRow(filesPanel);
		entryPanelLayout.setTopGap(15);
		entryPanelLayout.setBotGap(5);
		
		CloseXIcon xIcon = new CloseXIcon(null);
		JLabel xIconLabel = new JLabel(xIcon);
		xIconLabel.setName(commitUser);
		xIconLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String commitid = ((JLabel)e.getSource()).getName();
				Component[] comps = fileListPanel.getComponents();
				for(Component comp:comps){
					if(comp.getName().toLowerCase().equals(commitid.toLowerCase())) {
						fileListPanel.remove(comp);
						fileListPanelLayout.removeComp((JComponent) comp);
						fileListPanel.repaint();
						fileListPanel.updateUI();
						entryCount--;
						resetCompPos();
						break;
					}
				}
			}
		});
		entryPanelLayout.setRowInfo(1, 10, 0, 0);
		entryPanelLayout.add(xIconLabel, 1, 10, 'N', 0, 0, 'L');

		JLabel copyLabel = new JLabel("CopyToClip");
		copyLabel.setName(commitUser);
		copyLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String commitid = ((JLabel)e.getSource()).getName();
				Component[] comps = fileListPanel.getComponents();
				for(Component comp:comps){
					if(comp.getName().toLowerCase().equals(commitid.toLowerCase())) {
						//copy
						Component[] labels = ((JPanel)comp).getComponents();
						String ss = "";
						for(Component label:labels ){
							if(label.getClass().getName().equals("javax.swing.JLabel")){
								if(((JLabel) label).getText() != null && !"CopyToClip".equals(((JLabel) label).getText()))
									ss = ss + ((JLabel) label).getText() + "\n";
							}
						}
						SystemOpr.setSysClipboardText(ss);
						break;
					}
				}
			}
		});
		entryPanelLayout.setRowGap(1, 5, 0, 10);
		entryPanelLayout.add(copyLabel, 1, 100, 'N', 0, 0, 'L');
		
		ArrayList<String> filelist = new ArrayList<>();
		ArrayList<String> files = new ArrayList<>(); //去重
		
		//取所有提交信息
		List<String> commitList = getCommitList(gitLocalDir,"1");
		for(String commitInfo:commitList){
			String[] infos = commitInfo.split("#");
			
			if("ALL".equals(commitUser) || infos[1].equals(commitUser)){
				String commitID = infos[0];
				List<DiffEntry> diffEntries = null;
				try {
					//System.out.println("---01--"+commitID);
					diffEntries = getFileList(localPathTextField.getText(),commitID);
					if(diffEntries != null){
						for(DiffEntry entry:diffEntries){
							if(!filelist.contains(entry.getOldPath()) ) filelist.add(entry.getOldPath());
						}
					}else{
						System.out.println("error:commitid("+commitID+")未取到提交文件信息！");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		filelist.sort(null);
		
		int lineNum = 1;
		for(String filepath:filelist){
			JLabel entryLabel = new JLabel(filepath);
			
			lineNum++;
			entryPanelLayout.setRowInfo(lineNum, 20, 1, 0);
			entryPanelLayout.setRowGap(lineNum, 5, 0, 5);
			entryPanelLayout.add(entryLabel, lineNum, 50, 'H', 0, 1, 'L');
		}
		
		fileListPanelLayout.setRowInfo(++entryCount, lineNum * 21, 5, 0);
		fileListPanelLayout.add(filesPanel, entryCount, 200, 'H', 0, 1, 'L');
		fileListPanelLayout.setCompLayout(filesPanel, entryPanelLayout);
		
		fileListPanelLayout.setRowPos();
		int w = fileListScroll.getWidth()-40;
		int h = fileListPanelLayout.getLayoutHeight() + lineNum * 21 + 10;
		
		fileListPanel.setPreferredSize(new Dimension(w, h));
		fileListPanel.revalidate(); // 告诉其他部件,我的宽高变了
		
		this.frame.getRightPanel().repaint();
		this.frame.repaint();
 	}
	
	/**
	 * 历史记录  
	 * @param gitLocalDir git本地文件夹位置
	 * @return
	 */
	public List<String> getCommitList(String gitLocalDir, String commitType){
        ArrayList<String> commitList = new ArrayList<String>();
		
		File gitDir = new File(gitLocalDir); 
        Git git = null;
        
        if(gitDir.exists()){
        	//System.out.println("exist!");
        }
        
            try {  
                if (git == null) {  
                    git = Git.open(gitDir);  
                }

                Iterable<RevCommit> gitlog= git.log().call();  
                for (RevCommit revCommit : gitlog) {  
                    String version = revCommit.getName();//版本号  (commit id)
                    String name = revCommit.getAuthorIdent().getName();  
                    String email = revCommit.getAuthorIdent().getEmailAddress();  
                    String comment = revCommit.getShortMessage();
                    if(comment == null || "".equals(comment)) comment = "null";
                    if(StringUtil.length(comment)>30) comment = StringUtil.subStringByByte(comment, 0, 30);
                    
                    Calendar c=Calendar.getInstance();
                    int seconds = revCommit.getCommitTime();//数据库中提取的数据
                    long millions=new Long(seconds).longValue()*1000;
                    c.setTimeInMillis(millions);
                    SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = sdft.format(c.getTime());
                    
                    String ss = version + "#" + name + "#" + dateString +"#"+comment;
                    //System.out.println("--ss:"+ss + "  type:"+revCommit.getType());
                    if("ALL".equals(commitType) || (commitType.equals("1") && comment.length()>5 && !"Merge".equals(comment.substring(0, 5))))
                    	commitList.add(ss);
                }  
                
            }catch (NoHeadException e) {  
                e.printStackTrace();  
            } catch (GitAPIException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return commitList;
    }  
	
    /**
     * 查询本次提交的日志
     * @param gitRoot git仓库
     * @param revision  版本号
     * @return 
     * @throws Exception
     */
    public static List<DiffEntry> getFileList(String gitRoot, String revision) throws Exception {
        Git git = Git.open(new File(gitRoot));
        Repository repository = git.getRepository();

        ObjectId objId = repository.resolve(revision);
        Iterable<RevCommit> allCommitsLater = git.log().add(objId).call();
        Iterator<RevCommit> iter = allCommitsLater.iterator();
        RevCommit commit = iter.next();
        TreeWalk tw = new TreeWalk(repository);
        RevTree tree = commit.getTree();
        int x = tw.addTree(tree);

        commit = iter.next();
        if (commit != null)
            tw.addTree(commit.getTree());
        else
            return null;

        tw.setRecursive(true);
        RenameDetector rd = new RenameDetector(repository);
        rd.addAll(DiffEntry.scan(tw));

        return rd.compute();
    }
	
	public void resetCompPos(){
		this.frame.getFrameLayout().setRowPos();
	}
	
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getCommitUser() {
		return commitUser;
	}

	public void setCommitUser(String commitUser) {
		this.commitUser = commitUser;
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
	    public int getIconWidth() {
	        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
	    }
	    public int getIconHeight() {
	        return height;
	    }
	    public Rectangle getBounds() {
	        return new Rectangle(x_pos, y_pos, width, height);
	    }
	}
}
