package ucossim;

public class UCOSIIKernel {
	private int OSPrioHighRdy = 0;
	
	//Priority Bit Mask lookup table
	private static final int[] OSMapTbl  = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
	
	//Priority Index lookup table
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
	//Start with no tasks ready
	private int[] OSRdyTbl = {0,0,0,0,0,0,0,0};
	
	//cast this to a byte when using..
	private int OSRdyGrp = 0;
		
	public UCOSIIKernel() {  }
	
	//Returns the lowest priority ready..
	public int OS_Sched() {
		int y   = OSUnMapTbl[OSRdyGrp];              //Get the lowest row byte with a bit set 
		int x   = OSUnMapTbl[OSRdyTbl[y]];           //Get the col i.e the lowest bit set in the row byte..
		OSPrioHighRdy = (byte)((y << 3) + x);       // Convert from (row,col) to unique place value
		return OSPrioHighRdy;
	}
	
	public int getCurrentRunningTaskPrio() {
		return OSPrioHighRdy;
	}
	
	public void enableTask(int prio) {

		OSRdyGrp |= OSMapTbl[prio >> 3];   
		OSRdyTbl[prio >> 3] |= OSMapTbl[prio & 0x07];
	}

	public void disableTask(int prio) {
		if((OSRdyTbl[prio >> 3] &= ~OSMapTbl[prio & 0x07]) == 0) { // if there is no other priority set in that row
			OSRdyGrp &= ~OSMapTbl[prio >> 3];                      // clear the OSRdyGrp bit for that row..
		}		
	}
}
