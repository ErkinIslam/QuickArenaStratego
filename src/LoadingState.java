import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

public class LoadingState extends GameState {
    
    BufferedImage backgroundImage;
    BufferedImage logoImage;
    
    private String[] quotes = {"Good Luck!"};
    
    private int randQuote;
    private int percent;
    
    public LoadingState(GameStateManager gsm){
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
        
        randQuote = (int) (Math.random() * quotes.length);
    }
    
    public void update(){
        handleInput();
        percent += (int) (Math.random() * 15 - Math.random() * 5);
        if(percent >= 1000){
            gsm.setState(GameStateManager.CHOOSINGSPOTS);
        }
    }
    
    public void draw(Graphics2D g){
        g.setColor(GamePanel.defaultColor);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        g.drawImage(backgroundImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        g.drawImage(logoImage, GamePanel.WIDTH / 2 - logoImage.getWidth() / 2, GamePanel.HEIGHT / 2 - logoImage.getHeight() / 2 - GamePanel.HEIGHT / 4, null);
        
        g.setFont(new Font("Arial", Font.ITALIC, 24));
        
        //Write our quote
        int quoteTextSize = (int) ((GamePanel.HEIGHT - logoImage.getHeight() - 15) / 20);
        g.drawString(quotes[randQuote], GamePanel.WIDTH / 2 - quotes[randQuote].length() * (quoteTextSize / 6), GamePanel.HEIGHT / 2);
    }
    
    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)){
            percent += 500;
        }
    }
}