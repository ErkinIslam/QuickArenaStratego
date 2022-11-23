import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Rock extends Actor{
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
