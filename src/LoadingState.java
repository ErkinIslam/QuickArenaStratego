import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

//import java.
public class LoadingState extends GameState {
    
    BufferedImage backgroundImage;
    BufferedImage logoImage;
    
    private String[] quotes = {"\"May the forces of evil become confused.\"", "\"Sacred cows make the best hamburger.\"", 
        "\"Time's fun when you're having flies.\"", "\"A big French Fry is big\"", "01001001 01101011 10001111 01010111, 10011110 !"};
    private String[] authors = {"Some Guy", "Mark Twain", "Kermit the Frog", "Anonymous", "Watson"};
    
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
        //g.drawString(quotes[randQuote], GamePanel.WIDTH / 2 - quotes[randQuote].length() * (quoteTextSize / 4), GamePanel.HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        //g.drawString(authors[randQuote], GamePanel.WIDTH / 2 + GamePanel.WIDTH / 12, GamePanel.HEIGHT / 2 + GamePanel.HEIGHT / 16);
        
        //Draw the loading screen
        
        g.setColor(GamePanel.getPlayerColor());
        int rectangleLength = GamePanel.WIDTH / 5;
        
        g.fillRect(GamePanel.WIDTH / 2 - rectangleLength / 2, GamePanel.HEIGHT / 2 + GamePanel.HEIGHT / 6, (int) ((percent / 1000.0) * rectangleLength), 
            rectangleLength / 10);
            //System.out.println("" + (GamePanel.HEIGHT / 2 + GamePanel.HEIGHT / 8));//514 480
            //System.out.println(rectangleLength);//341
            
        g.setColor(GamePanel.getPlayerColor().darker());
        g.setStroke(new BasicStroke(3));
        g.drawRect(GamePanel.WIDTH / 2 - rectangleLength / 2, GamePanel.HEIGHT / 2 + GamePanel.HEIGHT / 6, rectangleLength, 
            rectangleLength / 10);
         
    }
    
    public void handleInput(){
        //if(Keys.isPressed(Keys.ENTER)){
        //    percent += 500;
        //}
    }
}