import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Lieutenant extends Unit{
    public Lieutenant(int row, int col){
        super(row, col, Unit.LIEUTENANT_VALUE, "Lieutenant");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawLieutenant = classloader.getResourceAsStream("Lieutenant_Label_Overlay.png");
            label = ImageIO.read(rawLieutenant);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
