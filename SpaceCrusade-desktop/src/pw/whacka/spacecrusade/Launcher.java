package pw.whacka.spacecrusade;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
 
/*
 * Launcher.java requires:
 *   MyInternalFrame.java
 */
public class Launcher extends JFrame
                               implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CustomDesktopPane desktop;

    MyInternalFrame backgroundFrame;
    ClientGUI clientGUI;
    ServerGUI serverGUI;
    LwjglAWTCanvas game;
    MyInternalFrame gameFrame;
    MyInternalFrame dados;
    
    Rectangle clientGUIShape;
    Rectangle gameShape;
    Rectangle serverGUIShape;

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Launcher() {
        super("Launcher");
        
        desktop = new CustomDesktopPane(); //a specialized layered pane
        //desktop.paintComponent(desktop.getGraphics());
          //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 0;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
        
        //Set up the GUI.
        //desktop = new JDesktopPane(); //a specialized layered pane

        backgroundFrame = new MyInternalFrame("Background", true, true, true, true);
        backgroundFrame.setBounds(0,0, screenSize.width, screenSize.height);
        
        //createFrame(); //create first "window"
        //createFrame(backgroundFrame);
        
        //game = new LwjglAWTCanvas(new Application(), true);
        game = new LwjglAWTCanvas(new IsoMap(), true);
        
        
        gameFrame = new MyInternalFrame("Game Screen", true, true, true, true);
        gameFrame.add(game.getCanvas());
       
        
        //gameFrame.setSize(800, 480);
        //desktop.add(backgroundImage, BorderLayout.CENTER);
        gameFrame.setBounds(0, 0, 800, 480);
        createFrame(gameFrame);
        
        
        clientGUI = new ClientGUI("127.0.0.1", 1500);
        clientGUI.setBounds(0, 480,screenSize.width, screenSize.height - 580);
        createFrame(clientGUI);
        
        
        serverGUI = new ServerGUI(1500);
        serverGUI.setBounds(800, 0, screenSize.width - 800, 240);
        createFrame(serverGUI);
        
        
       dados = new MyInternalFrame("Dice Screen", true, true, true, true);
       dados.setBounds(800, 240, screenSize.width - 800, 240);
       createFrame(dados);
        
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
    	
        MyInternalFrame frame = new MyInternalFrame("Empty");
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    
    //Create a new internal frame.
    protected void createFrame(MyInternalFrame frame) {
        //MyInternalFrame frame = frame;
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    
    //Create a new internal frame.
    protected void createFrame(LwjglAWTCanvas frame) {
        //MyInternalFrame frame = frame;
        //frame.setVisible(true); //necessary as of 1.3
    	setTitle("Game Screen");
    	setVisible(true);
    	setResizable(true);
    	setLocation(5*5, 5*5);
    	setSize(100, 100);
        desktop.add(frame.getCanvas());
        frame.getCanvas().setVisible(true);//setSelected(true);
       
    }
    
    //Create a new internal frame.
    protected void createFrame(JInternalFrame frame) {
        //MyInternalFrame frame = frame;
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
    }
 
    //Quit the application.
    protected void quit() {
        System.exit(0);
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
        Launcher frame = new Launcher();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
