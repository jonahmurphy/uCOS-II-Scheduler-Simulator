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

public class UCOSIIKernel {
	private int OSPrioHighRdy = 0;
	
	
	//Priority Index lookup table
	//Maps a number between 0-255 to position of the lowest bit set in that number
	//or in other words if finds the lowest bit set in a byte
	private static final int[] OSUnMapTbl = {
		0, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		6, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		7, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		6, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
		4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0
	};
	
	//Priority Bit Mask lookup table
	//Maps / converts a number between 0 - 8 to a bit place e.g 3 = 0x08 = 00001000
	//Used as a bitmask for setting / clearing bits in OSRdyGrp and OSRdyTbl element
	private static final int[] OSMapTbl  = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
	
	//Start with no tasks ready
	private int[] OSRdyTbl = {0,0,0,0,0,0,0,0};
	
	//Each of the first 8 bits represent a row/element in OSRdyTbl, 
	//a set bit indicates that at least one of the bits is set in corresponding element in the OSRdyTbl
	//Therefore OSRdyGrp is used  to find the the lowest element (row) with a bit set in the OSRdyTbl
	//using the OSUnMapTbl e.g OSRdyTbl[OSUnMapTbl[OSRdyGrp]]
	private int OSRdyGrp = 0;
		
	public UCOSIIKernel() {  }
	
	//Returns the lowest priority ready..
	public int OS_Sched() {
		int y   = OSUnMapTbl[OSRdyGrp];				//Get the lowest row byte with a bit set 
		int x   = OSUnMapTbl[OSRdyTbl[y]];			//Get the col i.e the lowest bit set in the row byte..
		OSPrioHighRdy = (byte)((y << 3) + x);		//Convert from (row,col) to a unique place value that corresponds to a priority
		return OSPrioHighRdy;
	}
	
	/**
	 * Returns the priority of the task currently scheduled
	 * @return
	 */
	public int getCurrentRunningTaskPrio() {
		return OSPrioHighRdy;
	}
	
	public void enableTask(int prio) {

		OSRdyGrp |= OSMapTbl[prio >> 3];   
		OSRdyTbl[prio >> 3] |= OSMapTbl[prio & 0x07];
	}

	public void disableTask(int prio) {
		if((OSRdyTbl[prio >> 3] &= ~OSMapTbl[prio & 0x07]) == 0) {	//if there is no other priority set in that row
			OSRdyGrp &= ~OSMapTbl[prio >> 3];						//clear the OSRdyGrp bit for that row..
		}
	}
}
