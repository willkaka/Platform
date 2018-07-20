package com.platform.compEvent;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;

import com.platform.view.MainFrame;

public class FrameMotionListener extends MouseMotionAdapter{
	MainFrame frame = null;
	
	public FrameMotionListener(MainFrame frame) {
		// TODO Auto-generated constructor stub
		this.frame = frame;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("0");
		//System.out.println(this.frame.getRightPanel().getX() - this.frame.getLeftPanel().getX() - this.frame.getLeftPanel().getWidth());
		if(e.getX() > this.frame.getLeftPanel().getX() + this.frame.getLeftPanel().getWidth() + 2 &&
		   e.getX() <= this.frame.getRightPanel().getX()  &&
		   e.getY() > this.frame.getLeftPanel().getY()  &&
		   e.getY() <= this.frame.getLeftPanel().getY() + this.frame.getLeftPanel().getHeight() ){
			this.frame.getFrame().setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));//×óÓÒ¼ýÍ·¹â±ê
			//System.out.println("1");
		}else{
			this.frame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			//System.out.println("2");
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int startx = e.getX();
		int starty = e.getY();
		int frameWidth = frame.getFrame().getWidth();
		float leftRate = (float)startx / (float)frameWidth;
		
		
		BigDecimal bg = new BigDecimal(leftRate);
        float f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		float rightRate = 1 - f1;
		this.frame.getFrameLayout().resetCompInfo(this.frame.getLeftPanel(), 1, 100, 'B', 1, (float)leftRate, 'L');
		this.frame.getFrameLayout().resetCompInfo(this.frame.getRightPanel(), 1, 420, 'B', 1, rightRate, 'L');
		this.frame.getFrameLayout().setRowPos(this.frame.getFrame().getWidth(), this.frame.getFrame().getHeight());
		this.frame.getLeftPanel().getPanelLayout().setRowPos();
	}
}
