package pw.whacka.spacecrusade;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class myDesktopPane extends javax.swing.JDesktopPane
{
   private Image backImage = null; //member variable
 
   public myDesktopPane()
   {     
      try
      {
         backImage = new javax.swing.ImageIcon(this.getClass().getResource("backImage.jpg")).getImage();
      }
      catch(Exception e)
      {
         System.out.println("Could not find file in folder: " + this.getClass().getResource("."));
      }
   }
 
   public void paintComponent( Graphics g )
   {
      if(backImage == null)
         super.paintComponent(g);
      else
      {
         Graphics2D g2d = (Graphics2D)g;
 
         //scale the image to fit the size of the Panel
         double mw = backImage.getWidth(null);
         double mh = backImage.getHeight(null);
 
         double sw = getWidth() / mw;
         double sh = getHeight() / mh;
 
         g2d.scale(sw, sh);
         g2d.drawImage(backImage, 0, 0, this);
      }
   }
 }