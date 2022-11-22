import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

//import java.
public class WinState extends GameState {

    BufferedImage backgroundImage;
    BufferedImage logoImage;

    private String message;

    public WinState(GameStateManager gsm){
        super(gsm);
        init();
    }

    public void init(){
        try{
        	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        	InputStream rawBackground = classloader.getResourceAsStream(GamePanel.getGameBackground());
            backgroundImage = ImageIO.read(rawBackground);
            
            InputStream rawLogo = classloader.getResourceAsStream(GamePanel.getGameLogo());
            logoImage = ImageIO.read(rawLogo);
        }catch(Exception e){
            e.printStackTrace();
        }

        message = "You Win!";
    }

    public void update(){
        handleInput();
    }

    public void draw(Graphics2D g){
        g.setColor(GamePanel.defaultColor);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.drawImage(backgroundImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        g.drawImage(logoImage, GamePanel.WIDTH / 2 - logoImage.getWidth() / 2, GamePanel.HEIGHT / 2 - logoImage.getHeight() / 2 - GamePanel.HEIGHT / 4, null);

        //set quote size
        int quoteTextSize = (int) ((GamePanel.HEIGHT - logoImage.getHeight() - 15) / 20);
        g.setFont(new Font("Arial", Font.ITALIC, quoteTextSize));

        //Write our quote
        g.drawString(message, GamePanel.WIDTH / 2 - message.length() * (quoteTextSize / 2), GamePanel.HEIGHT / 2);
    }

    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)){
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }
}