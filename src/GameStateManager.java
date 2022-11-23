public class GameStateManager {
    
    private GameState[] gameStates;
    private int currentState;
    
    public static final int NUMGAMESTATES = 9;
    
    public static final int MENUSTATE = 0;
    public static final int CHOOSINGSPOTS = 1;
    public static final int PLAYINGGAME = 2;
    public static final int PAUSEDSTATE = 3;
    public static final int OPTIONSSTATE = 4;
    public static final int ABOUTSTATE = 5;
    public static final int LOADINGSTATE = 6;
    public static final int WINSTATE = 7;
    public static final int LOSESTATE = 8;
    
    public GameStateManager(){
        gameStates = new GameState[NUMGAMESTATES];
        
        currentState = MENUSTATE;
        loadState(currentState);
    }
    
    private void loadState(int state){
        if(state == MENUSTATE){
            gameStates[state] = new MenuState(this);
        }else if(state == CHOOSINGSPOTS){
            gameStates[state] = new SetUpBoard(this);
        }else if(state == LOADINGSTATE){
            gameStates[state] = new LoadingState(this);
        }else if(state == PLAYINGGAME){
            gameStates[state] = new PlayingGame(this);
        }else if(state == WINSTATE){
            gameStates[state] = new WinState(this);
        }else if(state == LOSESTATE){
            gameStates[state] = new LoseState(this);
        }
    }
    
    private void unloadState(int state){
        gameStates[state] = null;
    }
    
    public void setState(int state){
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }
    
    public void update(){
        if(gameStates[currentState] != null) gameStates[currentState].update();
    }
    
    public void draw(java.awt.Graphics2D g){
        if(gameStates[currentState] != null){
            gameStates[currentState].draw(g);
        }else{
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
     }
}