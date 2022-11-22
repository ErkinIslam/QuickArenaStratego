import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
/**
 * Write a description of class Captain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Captain extends Unit{
    /**
     * Constructor for objects of class Marshal
     */
    public Captain(int row, int col){
        super(row, col, Unit.CAPTAIN_VALUE, "Captain");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawCaptain = classloader.getResourceAsStream("Captain_Label_Overlay.png");
            label = ImageIO.read(rawCaptain);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
