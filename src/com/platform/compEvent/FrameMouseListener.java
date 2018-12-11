package com.platform.compEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameMouseListener extends MouseAdapter{
	/* 取得鼠标拖曳的结束坐标 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int endx = e.getX();
        int endy = e.getY();
        
        
	}
	
	/* 取得鼠标拖曳的开始坐标 */
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
