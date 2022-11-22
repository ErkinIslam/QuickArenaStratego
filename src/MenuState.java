import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

//import java.
public class MenuState extends GameState {
    
    BufferedImage backgroundImage;
    BufferedImage logoImage;
    
    private int currentChoice = 1;
    private String[] options = { "","New Game", "Quit" };
    
    private boolean canContinue = false;
    
    public MenuState(GameStateManager gsm){
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
    }
    
    public void update(){
        handleInput();
        if(currentChoice >= options.length){
            if(canContinue){
                currentChoice = 0;
            }else{
                currentChoice = 1;
            }
        }
        if(canContinue && currentChoice < 0){
            currentChoice = options.length - 1;
        }
        if(!canContinue && currentChoice < 1){
            currentChoice = options.length - 1;
        }
    }
    
    public void draw(Graphics2D g){
        g.setColor(GamePanel.defaultColor);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        g.drawImage(backgroundImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        g.drawImage(logoImage, GamePanel.WIDTH / 2 - logoImage.getWidth() / 2, GamePanel.HEIGHT / 2 - logoImage.getHeight() / 2 - GamePanel.HEIGHT / 4, null);
        
        int startPrint = 0;
        if(!canContinue){
            g.setColor(new Color(100, 100, 100, 95));
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(options[0], GamePanel.WIDTH / 3 + GamePanel.WIDTH / 8, GamePanel.HEIGHT / 2);
            startPrint = 1;
        }
        for(int i = startPrint; i < options.length; i++){
            if(currentChoice == i){
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.ITALIC, 24));
            }else{
                g.setColor(GamePanel.defaultColor);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
            }
            g.drawString(options[i], GamePanel.WIDTH / 3 + GamePanel.WIDTH / 8, GamePanel.HEIGHT / 2 + i * 25);
        }
    }
    
    private void select(){
         if(currentChoice == 1){
            //gsm.setState(GameStateManager.CHOOSINGSPOTS);
            gsm.setState(GameStateManager.LOADINGSTATE);
        }else if(currentChoice == 2){
            System.exit(1);
        }
    }
    
    public void handleInput(){
        if(Keys.isPressed(Keys.DOWN) || Keys.isPressed(Keys.RIGHT)){
            currentChoice++;
        }
        if(Keys.isPressed(Keys.UP) || Keys.isPressed(Keys.LEFT)){
            currentChoice--;
        }
        if(Keys.isPressed(Keys.ENTER)){
            select();
        }
    }
}