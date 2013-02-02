package ucossim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImagePanel extends JPanel {

	private BufferedImage image;
	private static Logger logger = Logger.getLogger(ImagePanel.class.getName());

	public ImagePanel(String pathToImage) {
		try {
			image = ImageIO.read(new File(pathToImage));
		} catch (IOException ex) {
			logger.info("Could not load image: "+pathToImage);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}
}