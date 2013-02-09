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

import java.awt.event.MouseEvent;
import javax.swing.ToolTipManager;

//Class to represent an element of the  OSRdyTblElement on a swing ui.
//simply extends OSTblElement to add relevant tool tips
public class OSRdyTblElement extends OSTblElement {
	private static final long serialVersionUID = 264984211990601594L;

	public  OSRdyTblElement(boolean set, int elementLocation) {
		super(set, elementLocation);
		ToolTipManager.sharedInstance().registerComponent(this);
	}
	
	@Override
	public String getToolTipText(MouseEvent e) {
		String basicTaskInfo = "Task Priority#"+ getElementLocation();
		
        if(isRunning()) {
        	return basicTaskInfo + " is running";
        }
    	if(isSet()) {
    		return basicTaskInfo + " is enabled but not running";
    	}
    	return  basicTaskInfo +  " is disabled";
    }
}
