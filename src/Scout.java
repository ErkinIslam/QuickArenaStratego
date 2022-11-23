import java.awt.image.*;
import javax.imageio.*;
import java.io.*;


public class Scout extends Unit {
    public Scout(int row, int col)
    {
        super(row, col, Unit.SCOUT_VALUE, "Scout");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawScout = classloader.getResourceAsStream("Scout_Label_Overlay.png");
            label = ImageIO.read(rawScout);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
