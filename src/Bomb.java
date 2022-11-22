import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Bomb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bomb extends Actor{
    /**
     * Constructor for objects of class Bomb
     */
    public Bomb(int row, int col){
        super(row, col);
        
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawBomb = classloader.getResourceAsStream("Bomb_Label_Overlay.png");
            label = ImageIO.read(rawBomb);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
