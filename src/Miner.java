import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * Write a description of class Miner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Miner extends Unit{
    /**
     * Constructor for objects of class Marshal
     */
    public Miner(int row, int col)
    {
        super(row, col, Unit.MINER_VALUE, "Miner");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawMiner = classloader.getResourceAsStream("Miner_Label_Overlay.png");
            label = ImageIO.read(rawMiner);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
