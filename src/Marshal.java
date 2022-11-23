import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Marshal extends Unit {
    public Marshal(int row, int col){
        super(row, col, Unit.MARSHAL_VALUE, "Marshal");
        BufferedImage label;
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawMarshal = classloader.getResourceAsStream("Marshal_Label_Overlay.png");
            label = ImageIO.read(rawMarshal);
            setLabelImage(label);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
