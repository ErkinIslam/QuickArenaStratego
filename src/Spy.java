import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Spy extends Unit {
    public Spy(int row, int col)
    {
        super(row, col, Unit.SPY_VALUE, "Spy");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawSpy = classloader.getResourceAsStream("Spy_Label_Overlay.png");
            label = ImageIO.read(rawSpy);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}