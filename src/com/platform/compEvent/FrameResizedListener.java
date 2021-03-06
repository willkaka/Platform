package com.platform.compEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.platform.view.MainFrame;

public class FrameResizedListener extends ComponentAdapter {
	
	private MainFrame mainFrame = null;
	public FrameResizedListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	@Override
    public void componentResized(ComponentEvent e) {    //窗口大小改变事件
        
        mainFrame.getFrameLayout().setRowPos();
        //frame.repaint();
        //mainFrame.getLeftPanel().repaint();
        //mainFrame.getLeftPanel().updateUI();
    }
}
