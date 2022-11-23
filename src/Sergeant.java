import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Sergeant extends Unit{
    public Sergeant(int row, int col){
        super(row, col, Unit.SERGEANT_VALUE, "Sergeant");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawSergeant = classloader.getResourceAsStream("Sergeant_Label_Overlay.png");
            label = ImageIO.read(rawSergeant);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
