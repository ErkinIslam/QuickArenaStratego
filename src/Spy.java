import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Spy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spy extends Unit {
    /**
     * Constructor for objects of class Marshal
     */
    public Spy(int row, int col)
    {
        super(row, col, Unit.SPY_VALUE, "Spy");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawSpy = classloader.getResourceAsStream("Spy_Label_Overlay.png");
            label = ImageIO.read(rawSpy);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}