import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Colonel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Colonel extends Unit{

    /**
     * Constructor for objects of class Marshal
     */
    public Colonel(int row, int col)
    {
        super(row, col, Unit.COLONEL_VALUE, "Colonel");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawColonel = classloader.getResourceAsStream("Colonel_Label_Overlay.png");
            label = ImageIO.read(rawColonel);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
