import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

//import java.
public class SetUpBoard extends GameState {

    BufferedImage backgroundImage;
    BufferedImage logoImage;
    BufferedImage mapBackground;

    private long flashTick;

    private double squareSize = 50;

    private boolean flashSelectedPiece;

    private int textSize = 20;

    private String[] gameRules = {"Welcome to Stratego!", "", "Stratego is a game of strategy and wits!", "To your left is the board.",
            "You will place your pieces on", "the bottom of the board.", "", "Continue to place all of your pieces,", "at the end, you may switch pieces", "if needed.", "", 
            "Your goal is to get to the other enemys' flag.", "", "Each team has 40 pieces,", "which they will use to fight the other.", "",
            "The pieces can not go diagonally and may not","go into the ponds(highlighted grey)", "", "Press enter to continue."};
    private String[] placingFlag = {"The Flag","",  "The Flag is the most important piece on the board.", "You can not move it, and it can not attack.",
            "You need to keep it safe or you lose.", "", "Usually the Flag is placed at the bottom","of the board, farthest from the enemy.","",
            "The flag is denoted by a large F", "", "Press enter to place your Flag.", "", "Remember that you may swap pieces later."};
    private String[] placingBomb = {"Bombs!", "", "You get 6 Bombs to place on the board.", "", "Bombs are immovable objects whose soul", "purpose is to kill an enemy unit.",
            "", "Usually Bombs are placed around or near the", "Flag since neither can move.", "", "Bombs are denoted by a B", "",
            "The only peice that can remove a Bomb is", "the Miner.", "", "Press enter to place a Bomb"};
    private String[] placingSpy = {"The Spy", "", "The Spy can be killed by any piece on the board.", "The only piece it can kill is the Marshal,",
            "the strongest piece on the board.", "", "But use caution, the Spy can only kill", "a Marshal if the Spy attacks first!", "", "The Spy is denoted by a S", 
            "", "Press enter to place the Spy"};
    private String[] placingMarshal = {"Marshal", "", "The Marshal is the strongest piece of the board.", "It can only be killed by a Spy if the",
            "Spy attacks first, and by Bombs.", "", "The Marshal is denoted by a 1", "", "Press enter to place the Marshal."};
    private String[] placingSet = {"Now place these pieces:", "(2) One General", "(3) Two Colonels", "(4) Three Majors", "(5) Four Captains",
            "(6) Four Lieutenants", "(7) Four Sergeants", "", "The number in the parenthesis refers", "to the strength of the piece and",
            "it's corrasponding label.", "", "Bare in mind that the lower the number,", "the stronger.", "", "Press enter to place the next piece."};
    private String[] placingMiner = {"The Miner", "", "You will get 5 Miners", "The miner is the only piece that can", "remove a bomb without killing",
            "itself in the process.", "", "Miners are denoted by 8", "", "Press enter to place a Miner"};
    private String[] placingScout = {"The Scout", "", "You will get 9 Scouts.", "The Scout is the weakest unit,", "and is purly used to scout out the",
            "enemy pieces.", "", "Scouts are denoted by 9", "", "Press enter to place a Scout."};
    private String[] endInstructions = {"Now all of your pieces have been placed,", "if you would like to swap two pieces", "select the first piece, then the",
            "second piece. Then they will be swapped.", "", "Press Z to deselect.","", "When you are ready to play",
            "press the space bar", "", "Piece values and names:", "F : Flag", "B : Bomb", "S : Spy", "1 : Marshal", "2 : General", "3 : Colonel", "4 : Major",
            "5 : Captain", "6 : Lieutenant", "7 : Sergeant", "8 : Miner", "9 : Scout"};
    private int choiceY = 0;
    private int choiceX = 0;

    private int placingPiece;

    private boolean isFirstSelectedPiece;
    private Actor firstPiece;

    public SetUpBoard(GameStateManager gsm){
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
            
            InputStream rawMap = classloader.getResourceAsStream(GamePanel.getMap());
            mapBackground = ImageIO.read(rawMap);
        }catch(Exception e){
            e.printStackTrace();
        }

        textSize = (int) ((GamePanel.HEIGHT - logoImage.getHeight() - 15) / 35);
        flashTick = 0;

        flashSelectedPiece = true;

        GamePanel.board.resetBoard();
        /*
         * DEVELOPERS NOTE::
         * GamePanel.board.setAtLocation(X, Y);
         * GamePanel.board.getAtLocation(row, colomn);
         * 
         * This should be fixed ASAP
         */
        //THESE ARE IN (X, Y) COORS!! DONT FORGET 
        GamePanel.board.setAtLocation(new Rock(2, 4));
        GamePanel.board.setAtLocation(new Rock(3, 4));
        GamePanel.board.setAtLocation(new Rock(2, 5));
        GamePanel.board.setAtLocation(new Rock(3, 5));
        GamePanel.board.setAtLocation(new Rock(6, 4));
        GamePanel.board.setAtLocation(new Rock(7, 4));
        GamePanel.board.setAtLocation(new Rock(6, 5));
        GamePanel.board.setAtLocation(new Rock(7, 5));
        placingPiece = 0;
        isFirstSelectedPiece = true;
    }

    public void update(){
        handleInput();

        GamePanel.board.recalibrateBoard();

        flashTick++;

        if(flashTick > 10000000){
            flashTick = 0;
        }

        //flashes
        if(flashTick % 10 == 0){
            flashSelectedPiece = !flashSelectedPiece;
        }

        textSize = (int) ((GamePanel.HEIGHT - logoImage.getHeight() - 15) / 35);
        //because when setting up, we dont want the player to go wSpyhere he can not place
        //TODO
        /*
         * choiceX seems to be refering to the Y
         * and
         * choiceY seems to be refering to the X
         * 
         * Thats kind of a problem....
         */
        if(choiceX < 6){
            choiceX = 9;
        }
        if(choiceY < 0){
            choiceY = 9;
        }
        if(choiceX >= 10){
            choiceX = 6;
        }
        if(choiceY >= 10){
            choiceY = 0;
        }
        if(placingPiece == 42){
            isFirstSelectedPiece = true;
            firstPiece = null;
        }
    }

    public void draw(Graphics2D g){
        g.setColor(GamePanel.defaultColor);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setFont(new Font("Arial", Font.PLAIN, textSize));
        g.drawImage(backgroundImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        g.drawImage(logoImage, GamePanel.WIDTH / 2 - logoImage.getWidth() / 2, 3, null);

        int buffer = 45;
        int boardSize = GamePanel.HEIGHT - logoImage.getHeight() - buffer;
        squareSize =  (boardSize / 10.0);

        g.drawImage(mapBackground,(int) (GamePanel.WIDTH / 2 - squareSize * 5 - GamePanel.WIDTH / 6), logoImage.getHeight(), boardSize, boardSize, null);        
        g.setStroke(new BasicStroke(1));
        for(int r = 0; r < 10; r++){
            if(GamePanel.getShowGrid()){
                g.setColor(GamePanel.defaultColor);
                g.drawLine((int) (GamePanel.WIDTH / 2 - squareSize * 5 - GamePanel.WIDTH / 6), (int) (logoImage.getHeight() + r * squareSize),
                    (int) (GamePanel.WIDTH / 2 + squareSize * 5 - GamePanel.WIDTH / 6), (int) (logoImage.getHeight() + r * squareSize));

                g.drawLine((int) (GamePanel.WIDTH / 2 - squareSize * 5 - (GamePanel.WIDTH / 6) + r * squareSize), logoImage.getHeight(),
                    (int) (GamePanel.WIDTH / 2 - squareSize * 5 - (GamePanel.WIDTH / 6) + r * squareSize), logoImage.getHeight() + boardSize);
            }
            for(int c = 0; c < 10; c++){
                //draws the selecter
                if(r == choiceY && c == choiceX){
                    Color selectorColor = new Color(GamePanel.getPlayerColor().getRed(), GamePanel.getPlayerColor().getGreen(),
                            GamePanel.getPlayerColor().getBlue(), 175);
                    g.setColor(selectorColor);
                    g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                        (int) squareSize, (int) squareSize);
                }
                //when swapping pieces, draws the selected piece
                if(!isFirstSelectedPiece && r == firstPiece.getRow() && c == firstPiece.getCol() && flashSelectedPiece){
                    //Color selectorColor = new Color(GamePanel.getPlayerColor().getRed(), GamePanel.getPlayerColor().getGreen(),
                    //        GamePanel.getPlayerColor().getBlue(), 100);
                    Color selectorColor = new Color(GamePanel.getComputerColor().getRed(), GamePanel.getComputerColor().getGreen(),
                            GamePanel.getComputerColor().getBlue(), 100);
                    g.setColor(selectorColor);
                    g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                        (int) squareSize, (int) squareSize);
                }
                if(GamePanel.board.getAtLocation(r, c) != null){
                    //Code to get correct color
                    Color pieceColor;
                    if(GamePanel.board.getAtLocation(r, c).isEnemy()){
                        pieceColor = new Color(GamePanel.getComputerColor().getRed(), GamePanel.getComputerColor().getGreen(), GamePanel.getComputerColor().getBlue(), 225);
                    }else{
                        pieceColor = new Color(GamePanel.getPlayerColor().getRed(), GamePanel.getPlayerColor().getGreen(), GamePanel.getPlayerColor().getBlue(), 225);
                    }
                    g.setColor(pieceColor);
                    if(!(GamePanel.board.getAtLocation(r, c) instanceof Rock) && GamePanel.board.getAtLocation(r, c) != null){
                        //draws piece background
                        g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6 + 1 + squareSize / 5),
                            (int) (logoImage.getHeight() + c * squareSize + 1 + squareSize / 5),
                            (int) (3 *(squareSize / 5)),
                            (int) (3 * (squareSize / 5)));

                        //draws labels if the piece is not hidden
                        if(!(GamePanel.board.getAtLocation(r, c).isHidden())){
                            g.drawImage(GamePanel.board.getAtLocation(r, c).getLabelImage(), (int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1,
                                (int) (logoImage.getHeight() + c * squareSize + 1), (int) squareSize, (int) squareSize, null); 
                        }
                    }
                    //Light grey the rock areas
                    if(GamePanel.board.getAtLocation(r, c) instanceof Rock){
                        Color rockColor = new Color(100, 100, 100, 125);
                        g.setColor(rockColor);
                        g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                            (int) squareSize, (int) squareSize);
                    }
                }
            }
        }

        //Draws the last grid lines if the grid is turned on
        if(GamePanel.getShowGrid()){
            g.setColor(GamePanel.defaultColor);
            //last horizantal line:
            g.drawLine((int) (GamePanel.WIDTH / 2 - squareSize * 5 - GamePanel.WIDTH / 6), logoImage.getHeight() + boardSize,
                (int) (GamePanel.WIDTH / 2 + squareSize * 5 - GamePanel.WIDTH / 6), logoImage.getHeight() + boardSize);

            //last vertical line:
            g.drawLine((int) (GamePanel.WIDTH / 2 + squareSize * 5 - (GamePanel.WIDTH / 6)), logoImage.getHeight(),
                (int) (GamePanel.WIDTH / 2 + squareSize * 5 - (GamePanel.WIDTH / 6)), logoImage.getHeight() + boardSize);
        }

        //draw the notes on the right side of the board.
        int introXStart = GamePanel.WIDTH / 2 + boardSize / 2 - GamePanel.WIDTH / 8;
        int introYStart = logoImage.getHeight() + GamePanel.HEIGHT / 10;
        int lineSpacing = textSize + textSize / 3;
        g.setFont(new Font("Arial", Font.PLAIN, textSize));
        if(placingPiece == 0){
            for(int i = 0; i < gameRules.length; i++){
                g.drawString(gameRules[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece == 1){
            for(int i = 0; i < placingFlag.length; i++){
                g.drawString(placingFlag[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece >= 2 && placingPiece <= 7){
            for(int i = 0; i < placingBomb.length; i++){
                g.drawString(placingBomb[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece == 8){
            for(int i = 0; i < placingSpy.length; i++){
                g.drawString(placingSpy[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece == 9){
            for(int i = 0; i < placingMarshal.length; i++){
                g.drawString(placingMarshal[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece >= 10 && placingPiece <= 27){
            for(int i = 0; i < placingSet.length; i++){
                g.drawString(placingSet[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece <= 32){
            for(int i = 0; i < placingMiner.length; i++){
                g.drawString(placingMiner[i], introXStart, introYStart + lineSpacing * i);
            }
        }else if(placingPiece <= 40){
            for(int i = 0; i < placingScout.length; i++){
                g.drawString(placingScout[i], introXStart, introYStart + lineSpacing * i);
            }
        }else{
            for(int i = 0; i < endInstructions.length; i++){
                g.drawString(endInstructions[i], introXStart, introYStart + lineSpacing * i);
            }
        }
    }

    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)){
            if(GamePanel.board.getAtLocation(choiceY, choiceX) == null && placingPiece > 0){
                if(placingPiece == 1){
                    GamePanel.board.setAtLocation(new Flag(choiceY, choiceX));
                }else if(placingPiece >= 2 && placingPiece <= 7){
                    GamePanel.board.setAtLocation(new Bomb(choiceY, choiceX));
                }else if(placingPiece == 8){
                    GamePanel.board.setAtLocation(new Spy(choiceY, choiceX));
                }else if(placingPiece == 9){
                    GamePanel.board.setAtLocation(new Marshal(choiceY, choiceX));
                }else if(placingPiece == 10){
                    GamePanel.board.setAtLocation(new General(choiceY, choiceX));
                }else if(placingPiece <= 12){
                    GamePanel.board.setAtLocation(new Colonel(choiceY, choiceX));
                }else if(placingPiece <= 15){
                    GamePanel.board.setAtLocation(new Major(choiceY, choiceX));
                }else if(placingPiece <= 19){
                    GamePanel.board.setAtLocation(new Captain(choiceY, choiceX));
                }else if(placingPiece <= 23){
                    GamePanel.board.setAtLocation(new Lieutenant(choiceY, choiceX));
                }else if(placingPiece <= 27){
                    GamePanel.board.setAtLocation(new Sergeant(choiceY, choiceX));
                }else if(placingPiece <= 32){
                    GamePanel.board.setAtLocation(new Miner(choiceY, choiceX));
                }else if(placingPiece <= 40){
                    GamePanel.board.setAtLocation(new Scout(choiceY, choiceX));
                }
                placingPiece++;
            }
            if(placingPiece == 0 || placingPiece > 40){
                placingPiece++;
            }
            if(placingPiece > 40){
                if(isFirstSelectedPiece){
                    firstPiece = GamePanel.board.getAtLocation(choiceY, choiceX);
                    isFirstSelectedPiece = false;
                }else{
                    GamePanel.board.swapLocations(choiceY, choiceX, firstPiece.getRow(), firstPiece.getCol());
                    isFirstSelectedPiece = true;
                }
            }
        }
        if(Keys.isPressed(Keys.RIGHT)){
            choiceY++;
        }
        if(Keys.isPressed(Keys.LEFT)){
            choiceY--;
        }
        if(Keys.isPressed(Keys.UP)){
            choiceX--;
        }
        if(Keys.isPressed(Keys.DOWN)){
            choiceX++;
        }
        if(Keys.isPressed(Keys.SPACE)){
            if(placingPiece > 40){
                gsm.setState(GameStateManager.PLAYINGGAME);
            }
        }
        if(Keys.isPressed(Keys.Z)){
            isFirstSelectedPiece = true;
            firstPiece = null;
        }
    }
}