import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Miner extends Unit{
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
