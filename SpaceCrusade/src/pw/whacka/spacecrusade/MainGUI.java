package pw.whacka.spacecrusade;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

public class MainGUI extends JFrame
implements ActionListener {
	
	/**
	 * 
	 */

	
	private ClientGUI clientGUI;
	private MyInternalFrame serverGUI;
	private LwjglAWTCanvas game;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JDesktopPane desktop;

    public MainGUI() {
        super("Space Crusade Extended");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        serverGUI = new ServerGUI(1500);
        clientGUI = new ClientGUI("127.0.0.1", 1500);
        //game = new SpaceCrusade(); 
        game = new LwjglAWTCanvas(new SpaceCrusade(), true);
        		
        createFrame(serverGUI, BorderLayout.PAGE_START);
        createFrame(clientGUI, BorderLayout.CENTER);
        createFrame(game.getCanvas(), BorderLayout.PAGE_END);
        
        createFrame(); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());


        
        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);


    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
            createFrame();
        } else { //quit
            quit();
        }
    }

    //Create a new internal frame.
    protected void createFrame() {
        MyInternalFrame frame = new MyInternalFrame();
        
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }

    protected void createFrame(MyInternalFrame frame) {
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    protected void createFrame(MyInternalFrame frame, Object constraints) {
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame, constraints);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    protected void createFrame(Canvas frame, Object constraints) {
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame, constraints);
        //try {
            //frame.setSelected(true);
        //} catch (java.beans.PropertyVetoException e) {}
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

  
  //Note: Typically the main method will be in a
  //separate class. As this is a simple one class
  //example it's all in the one class.
	  public static void main(String[] args) {
	      //Schedule a job for the event-dispatching thread:
	      //creating and showing this application's GUI.
	      javax.swing.SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	              createAndShowGUI();
	          }
	      });
	  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  private static void createAndShowGUI() {
      //Make sure we have nice window decorations.
      JFrame.setDefaultLookAndFeelDecorated(true);

      //Create and set up the window.
      MainGUI frame = new MainGUI();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Display the window.
      frame.setVisible(true);
  }
}
