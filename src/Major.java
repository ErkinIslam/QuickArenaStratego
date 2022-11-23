import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Major extends Unit{
    public Major(int row, int col)
    {
        super(row, col, Unit.MAJOR_VALUE, "Major");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawMajor = classloader.getResourceAsStream("Major_Label_Overlay.png");
            label = ImageIO.read(rawMajor);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
