import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Flag extends Actor{
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
