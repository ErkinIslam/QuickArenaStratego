import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
/**
 * Write a description of class Major here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Major extends Unit{
    /**
     * Constructor for objects of class Marshal
     */
    public Major(int row, int col)
    {
        super(row, col, Unit.MAJOR_VALUE, "Major");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawMajor = classloader.getResourceAsStream("Major_Label_Overlay.png");
            label = ImageIO.read(rawMajor);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
