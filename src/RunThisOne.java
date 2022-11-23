import javax.swing.JFrame;

import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class RunThisOne{
    public static JFrame window;
    public static void main(String[] args){
        window = new JFrame("Stratego");
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawIcon = classloader.getResourceAsStream("StrategoIcon.png");	
            Image icon = ImageIO.read(rawIcon);
            window.setIconImage(icon);
        }catch(Exception e){
            e.printStackTrace();
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        //window.setResizable(false);
        window.add(new GamePanel());
        window.setMinimumSize(new Dimension(720, 480));
        //window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
        
    }
}