import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class General extends Unit{
    public General(int row, int col)
    {
        super(row, col, Unit.GENERAL_VALUE, "General");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawGeneral = classloader.getResourceAsStream("General_Label_Overlay.png");
            label = ImageIO.read(rawGeneral);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
