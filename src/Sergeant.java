import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Sergent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sergeant extends Unit{
    /**
     * Constructor for objects of class Marshal
     */
    public Sergeant(int row, int col){
        super(row, col, Unit.SERGEANT_VALUE, "Sergeant");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawSergeant = classloader.getResourceAsStream("Sergeant_Label_Overlay.png");
            label = ImageIO.read(rawSergeant);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
