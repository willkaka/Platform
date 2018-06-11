package com.platform.compEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.platform.view.MainFrame;

public class FrameResizedListener extends ComponentAdapter {
	
	private MainFrame mainFrame = null;
	public FrameResizedListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	@Override
    public void componentResized(ComponentEvent e) {    //窗口大小改变事件
        JFrame frame = mainFrame.getFrame();
        
        mainFrame.getFrameLayout().setRowPos(frame.getWidth(), frame.getHeight());
        
        frame.repaint();
    }
}
