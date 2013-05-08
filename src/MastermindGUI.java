/* File: MastermindGUI.java
* Purpose: A Graphical User Interface for a mastermind game.
*          This class implements the user interface (view and controller),
*          and the logic (model) is implemented in a separate class that
*          knows nothing about the user interface.
**/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MastermindGUI extends JApplet{

	//================================================================ constants
	private static final int CELL_SIZE = 30; // Pixels
    private static final int BOARDWIDTH = 8*CELL_SIZE;
    private static final Color[] COLORS = {Color.RED, Color.PINK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
        
    private static final int tshft = 6;
    private static final int lshft = 1;
    
    //======================================================= instance variables
    private GameColors  _availableColors;
    private GameResults _resultDisplay;
    private MastermindModel  _gameLogic   = new MastermindModel();
    
    //============================================================== method main
    // If used as an applet, main will never be called.
    // If used as an application, this main will be called and it will use 
    //         the applet for the content pane.
    public static void main(String[] args) {
        JFrame window = new JFrame("MasterMind-Genetic-Algorithm");
        window.setContentPane(new MastermindGUI());  // Make applet content pane.
        window.pack();                      // Do layout
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null); // Center window.
        window.setResizable(false);
        window.setVisible(true);            // Make window visible
    }
    
    //============================================================== constructor
    public MastermindGUI() {
        //--- Create some buttons
        JButton newGameButton = new JButton("New Game");
        JButton undoButton    = new JButton("Undo");
        JButton checkButton = new JButton("Check");
        JButton demoButton = new JButton("Demo");
        
        //--- Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(newGameButton);
        controlPanel.add(undoButton);
        controlPanel.add(checkButton);
        controlPanel.add(demoButton);
        
        //--- Create component to display available colors
        _availableColors = new GameColors();
        //--- Create component to display results.
        _resultDisplay = new GameResults();
        
        //--- Set the layout and add the components
        this.setLayout(new BorderLayout());
        this.add(controlPanel , BorderLayout.SOUTH); 
        this.add(_availableColors, BorderLayout.EAST);
        this.add(_resultDisplay, BorderLayout.CENTER);
        
        
        //-- Add action listener to New Game button.
        newGameButton.addActionListener(new NewGameAction());
        
        //-- Add action listener to Undo button.
        undoButton.addActionListener(new UndoAction());
        
        //-- Add action listener to Undo button.
        checkButton.addActionListener(new CheckAction());
        
        //-- Add action listener to Demo button.
        demoButton.addActionListener(new DemoAction());
    }
    
    ////////////////////////////////////////////////// inner class NewGameAction
    private class NewGameAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            _gameLogic.reset();
            _resultDisplay.repaint();
        }
    }
    
    ////////////////////////////////////////////////// inner class UndoAction
    private class UndoAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	_gameLogic.undo();
        	
        	if (_gameLogic.getGameStatus() == MastermindModel.CONTINUE) {
        		_resultDisplay.repaint();
        	}                     
        }
    }
    
    ////////////////////////////////////////////////// inner class CheckAction
    private class CheckAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	_gameLogic.checkGame();
        	
        	if (_gameLogic.getGameStatus() == MastermindModel.CHECK) {
        		_gameLogic.setNextGuess();
        	}  
        	
        	_resultDisplay.repaint();  // Show updated results
        }
    }
    
    //////////////////////////////////////////////////inner class CheckAction
    private class DemoAction implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    	// To be done.
    		
    	}
    }

    ////////////////////////////////////////////////////// inner class GameResults
    // This is defined inside outer class to use things from the outer class:
    //    * The logic (could be passed to the constructor).
    
    class GameResults extends JComponent{
         private static final int HEIGHT = 22 * CELL_SIZE;
         private final Font font_eval = new Font("Arial", Font.PLAIN, 15);
         private final Font font_info = new Font("Book Antiqua", Font.PLAIN, 25);
         
         //========================================================== constructor
         public GameResults() {
             this.setPreferredSize(new Dimension(BOARDWIDTH, HEIGHT));
         }
         
         //======================================================= paintComponent
         @Override public void paintComponent(Graphics g) {
             Graphics2D g2 = (Graphics2D)g;
             g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                     RenderingHints.VALUE_ANTIALIAS_ON);
             
             //... Paint background
             g2.setColor(Color.LIGHT_GRAY);
             g2.fillRect(0, 0, BOARDWIDTH, HEIGHT);
             
             //... Paint grid (could be done once and saved).
             g2.setColor(Color.BLACK); // Horizontal lines
                         
             for (int r=0; r<2*MastermindModel.TIMESTOGUESS; r++){
            	 g2.drawLine(1*CELL_SIZE, (2+r)*CELL_SIZE, 5*CELL_SIZE, (2+r)*CELL_SIZE);
             }
             
             for (int r=0; r<MastermindModel.TIMESTOGUESS; r++){
            	 for  (int c=0; c<=MastermindModel.GUESSCOLORS; c++) {
                     g2.drawLine((c+1)*CELL_SIZE, (2+2*r)*CELL_SIZE, (c+1)*CELL_SIZE, (3+2*r)*CELL_SIZE);
                 }
             }
             
             _gameLogic.setPickedColors();
             
             int cind = _gameLogic.getNextColor();
             
             int eind;
             
             int x = 1*CELL_SIZE;
             int y = (2+2*(MastermindModel.TIMESTOGUESS - 1))*CELL_SIZE;
             
             g2.setFont(font_eval);
             
             while (cind >= 0){
            	 g2.setColor(COLORS[cind]);                 
                 g2.fillRect(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
                 
                 x = x + CELL_SIZE;
                 if (x > MastermindModel.GUESSCOLORS*CELL_SIZE){
                	 g2.setColor(Color.BLACK);
                	 
                	 eind = _gameLogic.getNextEval();
                	 if (eind >= 0)
                		 g2.drawString(Integer.toString(eind),x+CELL_SIZE/4,y+CELL_SIZE/2);

                	 eind = _gameLogic.getNextEval();
                	 if (eind >= 0)
                		 g2.drawString(Integer.toString(eind),x+5*CELL_SIZE/4,y+CELL_SIZE/2);
                	 
                	 x = 1*CELL_SIZE;
                	 y = y - 2*CELL_SIZE;
                 }
                 
                 cind = _gameLogic.getNextColor();
             }
            
             g2.setFont(font_info);
             switch (_gameLogic.getGameStatus()){
            	 case MastermindModel.WIN:
            		 g2.drawString("YOU WIN.",1*CELL_SIZE,1*CELL_SIZE);
            		 break;
            	 case MastermindModel.LOSE:
            		 g2.drawString("YOU LOSE.",1*CELL_SIZE,1*CELL_SIZE);
            		 break;
            	 case MastermindModel.CHECK:
            		 g2.drawString("Click check button.",1*CELL_SIZE,1*CELL_SIZE);
            		 break;
            		 
             }
             
         }
    	
    }    
    
    ////////////////////////////////////////////////////// inner class GameColors
    // This is defined inside outer class to use things from the outer class:
    //    * The logic (could be passed to the constructor).
    
    class GameColors extends JComponent implements MouseListener {
        //============================================================ constants       
        private static final int HEIGHT = 8 * CELL_SIZE;
        private static final int WIDTH = 3 * CELL_SIZE;
                
        
        //========================================================== constructor
        public GameColors() {
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.addMouseListener(this);  // Listen to own mouse events.
        }
        
        //======================================================= paintComponent
        @Override public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                                 
            //... Paint grid (could be done once and saved).
            g2.setColor(Color.BLACK); // Horizontal lines
            
            g2.drawLine(lshft*CELL_SIZE, tshft*CELL_SIZE, lshft*CELL_SIZE, (tshft+6)*CELL_SIZE);   
            g2.drawLine((lshft+1)*CELL_SIZE, tshft*CELL_SIZE, (lshft+1)*CELL_SIZE, (tshft+6)*CELL_SIZE);
            
            for  (int c=0; c<=MastermindModel.NUMOFCOLORS; c++) {
                g2.drawLine(lshft*CELL_SIZE, (tshft+c)*CELL_SIZE, (lshft+1)*CELL_SIZE, (tshft+c)*CELL_SIZE);
            }
                       
            int x = lshft*CELL_SIZE;
            int y;
            //... Draw player pieces.
            for (int r = 0; r < MastermindModel.NUMOFCOLORS; r++) {            	
            	y = (r + tshft) * CELL_SIZE;
                
            	g2.setColor(COLORS[r]);
        		g2.fillRect(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);                
            }
        }
        
        //================================================ listener mousePressed
        // When the mouse is pressed,
        //       the coordinates are translated into a row and column.
        public void mousePressed(MouseEvent e) {
            int x = e.getX() / CELL_SIZE;
            int y = e.getY() / CELL_SIZE;
            
            int cindex = getColor(x,y);
            
            if ( cindex >= 0 && _gameLogic.getGameStatus() == MastermindModel.CONTINUE) {
                //... Add color to array.
                _gameLogic.add(cindex);
                
            } else {  // Not legal - clicked non-empty location or game over.
                Toolkit.getDefaultToolkit().beep();
            }
            
            _resultDisplay.repaint();  // Show updated results
        }
        
        //================================================== ignore these events
        public void mouseClicked(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}  
        
        
        //================================================ select the color
        // Get the color where the mouse is clicked.
        private int getColor(int x, int y){
        	if (x == lshft && y >= tshft && y <= tshft+5){
        		return (y-tshft);        		
        	}        	
        	return -1;  
        }
        
    }   
}
