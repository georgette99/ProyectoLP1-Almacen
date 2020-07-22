
package Clases;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoPaneles extends JPanel {
    private Image fondoInventario = null;
    
    @Override
    protected void paintComponent(Graphics g){
       super.paintComponent(g);
       g.drawImage(fondoInventario, 0, 0, getWidth(), getHeight(), null);
    }
    
    public void setImage(String image){
        if(image != null){
            fondoInventario = new ImageIcon(getClass().getResource(image)).getImage();
        }
    }
}
