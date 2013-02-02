/**
 * @author Jonah Murphy
 */
package ucossim;

import java.awt.event.MouseEvent;
import javax.swing.ToolTipManager;

//Class to represent an element of the  OSRdyTblElement on a swing ui.
//simply adds some tool tips relevant to the OSRdyTblElement
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
