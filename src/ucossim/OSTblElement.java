/************************************************************************
 * Copyright (C) 2012  Jonah Murphy
 *
 *	This program is free software; you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License
 *	as published by the Free Software Foundation; either version 2
 *	of the License, or (at your option) any later version.
 *	
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with this program; if not, write to the Free Software
 *	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * @author Jonah Murphy
 * @date 01/02/2013
 * @brief
 ************************************************************************/
package ucossim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class OSTblElement extends JPanel {
	private static final long serialVersionUID = 9130511600101147975L;
	
	private static final Color TASK_ENABLED_COLOR = Color.GREEN; 
	private static final Color TASK_DISABLED_COLOR = Color.RED;
	private static final Color TASK_RUNNING_COLOR = Color.BLUE;
	private static final int RECT_WIDTH = 30;
	private static final char ENABLED_CHAR = '1';
	private static final char DISABLED_CHAR = '0';
	
	private int location;
	private boolean running;
	private boolean enabled;

	public  OSTblElement(boolean set, int elementLocation) {
		running = false;
		clearElement();
		if (set)
			setElement();
		location = elementLocation;
	}

	public boolean isSet() {
		return enabled;
	}
	
	public void toggleElement(){
		if(isSet()) {
			clearElement();
		}else {
			setElement();
		}
	}

	public void setElement() {
		enabled = true;
		if(!running)
			setBackground(TASK_ENABLED_COLOR);
		repaint();
	}

	public void clearElement() {
		enabled = false;
		if(!running)
			setBackground(TASK_DISABLED_COLOR);
		repaint();
	}

	public int getElementLocation() {
		return location;
	}
	
	public void setStopped() {
		running = false;
		if(isSet()) {
			setBackground(TASK_ENABLED_COLOR);
		} else {
			setBackground(TASK_DISABLED_COLOR);
		}
	}

	public void setRunning() {
		running = true;
		setBackground(TASK_RUNNING_COLOR);	
	}
	
	public boolean isRunning(){
		return running;
	}
	
	// draw Square
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, RECT_WIDTH, RECT_WIDTH);
		
		if(enabled)
			g.drawString(String.valueOf(ENABLED_CHAR), 11, 20);
		else
			g.drawString(String.valueOf(DISABLED_CHAR), 11, 20);
			
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(RECT_WIDTH+1, RECT_WIDTH+1);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
