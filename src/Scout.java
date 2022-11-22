import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Scout here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scout extends Unit {
    /**
     * Constructor for objects of class Marshal
     */
    public Scout(int row, int col)
    {
        super(row, col, Unit.SCOUT_VALUE, "Scout");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawScout = classloader.getResourceAsStream("Scout_Label_Overlay.png");
            label = ImageIO.read(rawScout);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
