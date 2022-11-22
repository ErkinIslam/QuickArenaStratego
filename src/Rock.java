import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
/**
 * Write a description of class Rock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rock extends Actor{
    /**
     * Constructor for objects of class Rock
     */
    public Rock(int row, int col){
        super(row, col);
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawRock = classloader.getResourceAsStream("Rock_Label_Overlay.png");
            
            label = ImageIO.read(rawRock);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
