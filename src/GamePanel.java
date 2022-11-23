import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{

    public static int WIDTH;
    public static int HEIGHT;
    
    public static int boardSize;
    public static double squareSize;
    public static int lowerBoardBuffer;

    public static Board board = new Board(10, 10);
    public static Computer comp = new Computer();

    public static Color defaultColor;

    private Thread thread;
    private boolean running;

    private static Color playerColor;
    private static Color computerColor;

    private static String backgroundImage;
    private static String logoImage;

    private static String map;

    private static int computerDifficulty;

    private static boolean showGrid;
    private static boolean showAvaliableMoves;

    private GameStateManager gsm;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS = 30;
    private double averageFPS; 

    public GamePanel(){
        super(); //because we are extending JPanel
        WIDTH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        HEIGHT = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        WIDTH = 680;
        HEIGHT = 480;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }

        addKeyListener(this);
    }
    
    public void run() {
        running = true;     
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        playerColor = new Color(50, 50, 200);
        computerColor = new Color(200, 50, 50);

        map = "SnowflakeMap.png";
        backgroundImage = "SandBackground.png";
        logoImage = "StrategoLogo.png";

        computerDifficulty = 1;
        boardSize = 10;

        showGrid = true;
        showAvaliableMoves = true;

        gsm = new GameStateManager();

        defaultColor = Color.BLACK;

        long startTime;
        long URDTimeMills;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = 30;

        long targetTime = 1000 / FPS; //amount of time for one loop to run to maintain the FPS

        while(running){
            //System.out.println(java.lang.Thread.activeCount());
            
            startTime = System.nanoTime();

            WIDTH = RunThisOne.window.getWidth();
            HEIGHT = RunThisOne.window.getHeight();
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) image.getGraphics();

            gameUpdate();
            gameRender();
            gameDraw();

            URDTimeMills = (System.nanoTime() - startTime) / 1000000; //Gets the number of milliseconds that the step was running
            waitTime = targetTime - URDTimeMills;

            try{
                if(waitTime > 0){
                    Thread.sleep(waitTime);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == maxFrameCount){
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    private void gameDraw() {
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.drawString(String.format("%.0f", averageFPS), 1, 15);
        g2.dispose();

    }

    private void gameRender() {
        gsm.draw(g);
    }

    private void gameUpdate() {
        gsm.update();
        Keys.update();
    }

    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE){
            //System.exit(1);
            gsm.setState(GameStateManager.MENUSTATE);
        }

        Keys.keySet(key.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent key) {
        //Do nothing
    }

    public static Color getPlayerColor(){
        return playerColor;
    }

    public static Color getComputerColor(){
        return computerColor;
    }

    public static void setPlayerColor(Color color){
        playerColor = color;
    }

    public static void setComputerColor(Color color){
        computerColor = color;
    }

    public static void setMap(String m){
        map = m;
    }

    public static String getMap(){
        return map;
    }

    public static boolean getShowGrid(){
        return showGrid;
    }

    public static void setShowGrid(boolean b){
        showGrid = b;
    }
    
    public static boolean getShowAvaliableMoves(){
        return showAvaliableMoves;
    }
    
    public static void setShowAvaliableMoves(boolean b){
        showAvaliableMoves = b;
    }

    public static String getGameBackground(){
        return backgroundImage;
    }

    public static String getGameLogo(){
        return logoImage;
    }

    public static void setGameBackground(String background){
        backgroundImage = background;
    }

    public static void setGameLogo(String logo){
        logoImage = logo;
    }

    public static int getComputerDifficulty(){
        return computerDifficulty;
    }

    public static void setComputerDifficulty(int diff){
        computerDifficulty = diff;
    }

    public static void setDefaultColor(Color color){
        defaultColor = color;
    }

    public static Color getDefaultColor(){
        return defaultColor;
    }
    
    public static int getBoardSize(){
        return boardSize;
    }
}
