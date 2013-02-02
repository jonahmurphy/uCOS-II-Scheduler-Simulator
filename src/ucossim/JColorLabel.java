package ucossim;

import java.awt.Color;

import javax.swing.JLabel;



public class JColorLabel extends JLabel {
	public JColorLabel(Color bgcolor, String text) {
		super(text);
		setBackground(bgcolor);
		setOpaque(true);
	}
}
