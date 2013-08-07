package pw.whacka.spacecrusade;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class CustomDesktopPane extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon backgroundIcon;
	private Image background;
	private Image newBackground;
	
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public CustomDesktopPane () {
		super();
        File file = new File("windowmanager/background.png");
		try {
	        String path = file.getCanonicalPath();
			System.out.println("Intentando abrir el archivo en" + path);

			background = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo abrir el archivo en");
			e.printStackTrace();
			
		}

        newBackground = background.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        this.paintComponents(background.getGraphics());
	}
	@Override
	protected void  paintComponent(Graphics g)
	{		
    	super.paintComponent(g);
    	g.drawImage(newBackground, 0, 0, this);
	}
      
}
