package ucossim;
/************************************************************************
 * @file USCOSSIISchedulerSimulatorApp.java
 * @author Jonah Murphy
 * @date 01/02/2013
 * @brief
 ************************************************************************/
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


public class UCOSIISchedulerSimulatorApp extends JApplet implements Runnable {
	
   private JPanel OSRdyTblPanel, OSRdyGrpPanel, arrowsPanel, mainPanel;
   private Square OSRdyGrpDisplay[];
   private Square OSRdyTblDisplay[][];
   private JTextField taskTextField;
   private JButton addTaskBtn;
   private JButton deleteTaskBtn;
   private JLabel timeTickLbl;
   
   private Timer tickIncrementTimer;               //A timer to trigger an event every time a tick increment expires
   private static int MAX_TICK_INCREMENTS = 80;    //The number of increments that make up a Tick
   private static int TICK_INCREMENT_TIME_MS = 50; // The length of a tick increment in ms
   private int nIncrementCurrent = 0;              //Keeps track of what tick increment were at

   private int osReadyGrpBitWidth = 8;             //The number of bits in the the OSReadyGrp

   private static Logger logger = Logger.getLogger(UCOSIISchedulerSimulatorApp.class.getName());
      

   // Set up the UI
   public void init()
   {
      Container container = getContentPane();
      setSize(new Dimension(550,320));
     
      taskTextField = new JTextField();
      addTaskBtn = new JButton("  Add Task  ");
      deleteTaskBtn = new JButton("  Delete Task  ");
 
      // set up diagram..
      OSRdyGrpPanel = new JPanel( new GridLayout( osReadyGrpBitWidth, 0, 0, 0 ));
      arrowsPanel = new JPanel(new GridLayout( osReadyGrpBitWidth,  osReadyGrpBitWidth, 0, 0 ));
      OSRdyTblPanel = new JPanel(new GridLayout( osReadyGrpBitWidth, osReadyGrpBitWidth, 0, 0 ));

      // create matrices to display..
      OSRdyTblDisplay = new Square[osReadyGrpBitWidth][osReadyGrpBitWidth];
      OSRdyGrpDisplay = new Square[osReadyGrpBitWidth];
      
      timeTickLbl = new JLabel("");

      //Create OSRdyGrp Matrix 
      for ( int column = 0; column <  OSRdyGrpDisplay.length; column++ ) {
    	// System.out.println("col:"+column + "length"+OSRdyGrpDisplay[row].length);

        // create matrix element
    	OSRdyGrpDisplay[column] = new Square(false, column );
    	
    	OSRdyGrpPanel.add(OSRdyGrpDisplay[ column ] );
    	arrowsPanel.add(new ImagePanel("arrow.jpg"));
      }

	 //Create OSRdyTbl Matrix 
	 for ( int row = 0; row <  OSRdyTblDisplay.length; row++ ) {
	    for ( int column = 0; column <  OSRdyTblDisplay[row].length; column++ ) {
	    	 System.out.println("col:"+column + "length"+OSRdyTblDisplay[row].length);
	    	// create matrix element
	    	OSRdyTblDisplay[ row ][ column ] = new Square(false, row * osReadyGrpBitWidth + column );
	        OSRdyTblPanel.add(OSRdyTblDisplay[ row ][ column ] );        
	     }
	  }


      
      // set up panel to contain boardPanel (for layout purposes)
      mainPanel = new JPanel(new MigLayout("", "[grow][grow][grow][grow]"));
      mainPanel.setBackground(Color.WHITE);
      
      mainPanel.add(new JLabel("OSRdyGrp"), "span 2");
      mainPanel.add(new JLabel("OSRdyTbl"), "span 2, wrap");
      
      mainPanel.add(OSRdyGrpPanel);
      mainPanel.add(arrowsPanel);
      mainPanel.add( OSRdyTblPanel, "span 2,wrap");
      
      JLabel runningTaskLbl = new JLabel("HIGHEST PRIORITY TASK RUNNING: 1");
      runningTaskLbl.setForeground(new Color(0,0,255));
      mainPanel.add(runningTaskLbl, "cell 1 2");
      mainPanel.add(timeTickLbl, "span 2, wrap");
   
      mainPanel.add(new JLabel("Task #"));
      mainPanel.add(taskTextField, "grow");
      mainPanel.add(addTaskBtn, "grow");
      mainPanel.add(deleteTaskBtn, "grow, wrap");
 
      container.add( mainPanel);
      
      tickIncrementTimer = new Timer();
      tickIncrementTimer.schedule(new TimeoutHandler(), TICK_INCREMENT_TIME_MS);
   } 


   public void start(){

   } 

   public void run() {
    

   }


   public void clear(Square[][] matrix) {
	   for(int i = 0; i <  matrix.length; i++) {
		   for(int j = 0; j < matrix[i].length; j++) {
			   matrix[i][j].clearElement();		   
		   } 
	   }   
   }
   
   public void clear(Square[] matrix) {
	   for(int i = 0; i <  matrix.length; i++) {
		   	matrix[i].clearElement();		   
	   }   
   }
   
   // private inner class to display a square...
   private class Square extends JPanel {
      private char mark;
      private int location;
   
      public Square(boolean set, int squareLocation )
      {
         mark = '0';
    	 if(set)mark ='1';
         location = squareLocation;

         addMouseListener( 
            new MouseAdapter() {
               public void mouseReleased( MouseEvent e ){
                  getSquareLocation();
                  toggleElement();
               } 
            }); 
      } 

      public Dimension getPreferredSize()  { 
         return new Dimension( 30, 30 );
      }

      public Dimension getMinimumSize() {
         return getPreferredSize();
      }
      
      public void toggleElement() {
    	  if(mark == '1') {
    		  clearElement();
    	  }else {
    		  setElement();
    	  }
      }

      public void setElement() { 
         mark = '1'; 
         repaint(); 
      }
      
      public void clearElement() {
    	  mark = '0';
    	  repaint();
      }
   
      public int getSquareLocation() {
         return location; 
      }
   
      // draw Square
      public void paintComponent( Graphics g ) {
         super.paintComponent( g );

         g.drawRect( 0, 0, 29, 29 );
         g.drawString( String.valueOf( mark ), 11, 20 );   
      }
   } 
   
   
   public class ImagePanel extends JPanel{

	    private BufferedImage image;

	    public ImagePanel(String pathToImage) {
	       try {                
	          image = ImageIO.read(new File(pathToImage));
	       } catch (IOException ex) {
	           logger.info("Could not load image!");
	       }
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, null);    
	    }
	    
	    public Dimension getPreferredSize() { 
		     return new Dimension( image.getWidth(), 30 );
		}

	}
   
   
   private class TimeoutHandler extends TimerTask {
		@Override
		public void run() {
				tickIncrementTimer.schedule(new TimeoutHandler(), TICK_INCREMENT_TIME_MS);
				nIncrementCurrent++;
				if(nIncrementCurrent < MAX_TICK_INCREMENTS) {
					timeTickLbl.setText(timeTickLbl.getText() + "|");
				} else {
					nIncrementCurrent = 0;
					timeTickLbl.setText("");
				}
		}
	}
}

