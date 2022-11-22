import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class General here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class General extends Unit{
    /**
     * Constructor for objects of class Marshal
     */
    public General(int row, int col)
    {
        super(row, col, Unit.GENERAL_VALUE, "General");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawGeneral = classloader.getResourceAsStream("General_Label_Overlay.png");
            label = ImageIO.read(rawGeneral);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
