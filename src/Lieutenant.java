import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Lieutenant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lieutenant extends Unit{
    /**
     * Constructor for objects of class Marshal
     */
    public Lieutenant(int row, int col){
        super(row, col, Unit.LIEUTENANT_VALUE, "Lieutenant");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawLieutenant = classloader.getResourceAsStream("Lieutenant_Label_Overlay.png");
            label = ImageIO.read(rawLieutenant);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
