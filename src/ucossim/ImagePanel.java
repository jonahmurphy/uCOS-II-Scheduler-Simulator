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