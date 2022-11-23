import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Colonel extends Unit{
    public Colonel(int row, int col)
    {
        super(row, col, Unit.COLONEL_VALUE, "Colonel");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawColonel = classloader.getResourceAsStream("Colonel_Label_Overlay.png");
            label = ImageIO.read(rawColonel);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
