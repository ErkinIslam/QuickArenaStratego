import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
/**
 * Write a description of class Flag here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flag extends Actor{

    /**
     * Constructor for objects of class Flag
     */
    public Flag(int row, int col){
        super(row, col);
        
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawFlag = classloader.getResourceAsStream("Flag_Label_Overlay.png");
            label = ImageIO.read(rawFlag);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }         
    }
}
