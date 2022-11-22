import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

//import java.
public class PlayingGame extends GameState {

    BufferedImage backgroundImage;
    BufferedImage logoImage;

    private long flashTick;
    private boolean flashSelectedPiece;
    
    protected static boolean computerReachedFlag = false;

    private int choiceY = 9;
    private int choiceX = 9;

    private int textSize = 18;
    private double squareSize = 50; 

    private boolean isPlayersTurn = true;
    private boolean stillPlaying = true;

    private String[] playerInstructions = {"It's your turn, select a piece", "with enter, then choose a", "spot for it to go.", "", "Press Z to deselect a piece",
    "", "If turned on, you can see squares", "indicating where the piece can go.", "", "Remember that you may not go in the gray squares."};
    private String[] computerInstructions = {"The computer is making it's turn."};

    private BufferedImage mapBackground;

    private Unit playerSelectedPiece = null;

    private int computerPercent = 0;

    public PlayingGame(GameStateManager gsm){
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
        GamePanel.comp.setInitialState(GamePanel.board);
    }

    public void update(){
        GamePanel.board.recalibrateBoard();

        flashTick++;

        if(flashTick > 10000000){
            flashTick = 0;
        }

        //flashes
        if(flashTick % 10 == 0){
            flashSelectedPiece = !flashSelectedPiece;
        }

        if(!stillPlaying){
            gsm.setState(GameStateManager.MENUSTATE);
        }

        if(!isPlayersTurn){
            if(computerPercent == 0){
                //System.out.println("Computer Went");
                GamePanel.comp.getNextMove(GamePanel.board);
            }
            computerPercent += 250 + (int) (Math.random() * 15);
            if(computerPercent >= 250){
                isPlayersTurn = true;
                computerPercent = 0;
            }
        }

        //piece decision testing
        /*if(GamePanel.board.getAtLocation(choiceY, choiceX) instanceof Unit){
        Unit testingUnit = (Unit) GamePanel.board.getAtLocation(choiceY, choiceX);

        System.out.println("-----------------------\nX " + choiceY + " Y " + choiceX);
        System.out.println("getRow(): " + testingUnit.getRow() + " getCol():" + testingUnit.getCol());
        System.out.println("Front: " + canMoveFront(testingUnit));
        //System.out.println("Bottom: " + testingUnit.bottomClear());
        //.out.println("Right: " + testingUnit.rightClear());
        //System.out.println("Left: " + testingUnit.leftClear());
        }*/

        handleInput();
        textSize = (int) ((GamePanel.HEIGHT - logoImage.getHeight() - 15) / 35);

        if(choiceX < 0){
            choiceX = 9;
        }
        if(choiceY < 0){
            choiceY = 9;
        }
        if(choiceX >= 10){
            choiceX = 0;
        }
        if(choiceY >= 10){
            choiceY = 0;
        }
        
        if(computerReachedFlag){
            gsm.setState(GameStateManager.LOSESTATE);
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
                }else{
                    if(GamePanel.board.getAtLocation(choiceY, choiceX) instanceof Unit){

                        Unit unit = (Unit) GamePanel.board.getAtLocation(choiceY, choiceX);
                        if(!unit.isEnemy()){
                            g.setColor(new Color(125, 125, 23, 200));
                            //Testing for bottom
                            /*if(r == unit.getRow() && c == unit.getCol() + 1){
                            g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                            (int) squareSize, (int) squareSize);
                            }*/

                            int red = (int) ((GamePanel.getComputerColor().getRed() + GamePanel.getPlayerColor().getRed()) / 2.0);
                            int blue = (int) ((GamePanel.getComputerColor().getBlue() + GamePanel.getPlayerColor().getBlue()) / 2.0);
                            int green = (int) ((GamePanel.getComputerColor().getGreen() + GamePanel.getPlayerColor().getGreen()) / 2.0);

                            g.setColor(new Color(red, blue, green, 200));
                            //Unit unit = (Unit) GamePanel.board.getAtLocation(choiceY, choiceX);
                            if(GamePanel.getShowAvaliableMoves()){
                                if(r == choiceY - 1 && c == choiceX && canMoveLeft(unit)){
                                    //eft
                                    g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                                        (int) squareSize, (int) squareSize);
                                }
                                if(r == choiceY + 1 && c == choiceX && canMoveRight(unit)){
                                    //right
                                    g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                                        (int) squareSize, (int) squareSize);
                                }
                                if(r == choiceY && c == choiceX - 1 && canMoveFront(unit)){
                                    //front
                                    g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                                        (int) squareSize, (int) squareSize);
                                }
                            }
                            //if(r == choiceY && c == choiceX + 1 && unit.bottomClear()){
                            //bottom
                            // g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                            //    (int) squareSize, (int) squareSize);
                            //}
                        }
                    }
                }
                if(GamePanel.board.getAtLocation(r, c) != null){
                    //Code to get correct color
                    Color pieceColor;
                    if(GamePanel.board.getAtLocation(r, c).isEnemy()){
                        pieceColor = new Color(GamePanel.getComputerColor().getRed(), GamePanel.getComputerColor().getGreen(), GamePanel.getComputerColor().getBlue(), 225);
                    }else{
                        pieceColor = new Color(GamePanel.getPlayerColor().getRed(), GamePanel.getPlayerColor().getGreen(), GamePanel.getPlayerColor().getBlue(), 225);
                    }
                    //if the piece is selected
                    if(playerSelectedPiece != null && r == playerSelectedPiece.getRow() && c == playerSelectedPiece.getCol() && flashSelectedPiece){
                        //Color selectorColor = new Color(GamePanel.getPlayerColor().getRed(), GamePanel.getPlayerColor().getGreen(),
                        //        GamePanel.getPlayerColor().getBlue(), 100);
                        Color selectorColor = new Color(GamePanel.getComputerColor().getRed(), GamePanel.getComputerColor().getGreen(),
                                GamePanel.getComputerColor().getBlue(), 100);
                        g.setColor(selectorColor);
                        g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                            (int) squareSize, (int) squareSize);
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
                    if(GamePanel.board.getAtLocation(r, c) instanceof Rock && isPlayersTurn){
                        Color rockColor = new Color(100, 100, 100, 125);
                        g.setColor(rockColor);
                        g.fillRect((int) (GamePanel.WIDTH / 2 - squareSize * 5 + r * squareSize - GamePanel.WIDTH / 6) + 1, (int) (logoImage.getHeight() + c * squareSize + 1),
                            (int) squareSize, (int) squareSize);
                    }

                }
            }
        }
        /*
         * DEVELOPERS NOTE::
         * GamePanel.board.setAtLocation(X, Y);
         * GamePanel.board.getAtLocation(row, colomn);
         * 
         * This should be fixed ASAP
         */
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

        g.setColor(GamePanel.defaultColor);

        int startX = (int) (GamePanel.WIDTH / 2 + squareSize * 5 - (GamePanel.WIDTH / 7));
        int wordBuffer = textSize + textSize / 3;

        if(isPlayersTurn){
            for(int i = 0; i < playerInstructions.length; i++){
                g.drawString(playerInstructions[i], startX, logoImage.getHeight() + GamePanel.HEIGHT / 10 + i * wordBuffer);
            }
        }else{
            for(int i = 0; i < computerInstructions.length; i++){
                g.drawString(computerInstructions[i], startX, logoImage.getHeight() + GamePanel.HEIGHT / 10 + i * wordBuffer);
            }
        }
    }

    public void handleInput(){
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
        if(Keys.isPressed(Keys.ENTER)){
            if(isPlayersTurn){
                if(playerSelectedPiece == null){
                    if(GamePanel.board.getAtLocation(choiceY, choiceX) instanceof Unit && !GamePanel.board.getAtLocation(choiceY, choiceX).isEnemy()){
                        Unit testUnit = (Unit) GamePanel.board.getAtLocation(choiceY, choiceX);
                        if(canPlayerMove(testUnit)){
                            playerSelectedPiece = testUnit;
                        }
                    }
                }else if(GamePanel.board.getAtLocation(choiceY, choiceX) == null){
                    //User has already selected piece chooses to move piece
                    //System.out.println("choiceX " + choiceX + " choiceY " + choiceY + " .getCol() " + playerSelectedPiece.getCol() + " .getRow() " + playerSelectedPiece.getRow());
                    if(canMoveFront(playerSelectedPiece) && choiceX == playerSelectedPiece.getCol() - 1 && choiceY == playerSelectedPiece.getRow()){
                        GamePanel.board.swapLocations(playerSelectedPiece.getRow(), playerSelectedPiece.getCol(), choiceY, choiceX);
                        playerSelectedPiece = null;
                        isPlayersTurn = false;
                    }else if(canMoveLeft(playerSelectedPiece) && choiceY == playerSelectedPiece.getRow() - 1 && choiceX == playerSelectedPiece.getCol()){
                        GamePanel.board.swapLocations(playerSelectedPiece.getRow(), playerSelectedPiece.getCol(), choiceY, choiceX);
                        playerSelectedPiece = null;
                        isPlayersTurn = false;
                    }else if(canMoveRight(playerSelectedPiece) && choiceY == playerSelectedPiece.getRow() + 1 && choiceX == playerSelectedPiece.getCol()){
                        GamePanel.board.swapLocations(playerSelectedPiece.getRow(), playerSelectedPiece.getCol(), choiceY, choiceX);
                        playerSelectedPiece = null;
                        isPlayersTurn = false;
                    }else{
                        //something to indicate that player cannot move here
                    }
                }else if(GamePanel.board.getAtLocation(choiceY, choiceX).isEnemy()){
                    //playerSelectedPiece has already been chosen
                    //attack pieces
                    if(GamePanel.board.getAtLocation(choiceY, choiceX) instanceof Unit){
                        //if user is attacking a unit

                        //Add in fancy animation and show player enemy value
                        Unit attackedEnemyUnit = (Unit) GamePanel.board.getAtLocation(choiceY, choiceX);
                        attackedEnemyUnit.switchHiddenState();

                        if(playerSelectedPiece.getUnitClass() == attackedEnemyUnit.getUnitClass()){
                            GamePanel.board.setLocationNull(choiceY, choiceX);
                            GamePanel.board.setLocationNull(playerSelectedPiece.getRow(), playerSelectedPiece.getCol());
                        }else if(playerSelectedPiece.getUnitClass() > attackedEnemyUnit.getUnitClass()){
                            GamePanel.board.setLocationNull(playerSelectedPiece.getRow(), playerSelectedPiece.getCol());
                        }else if(playerSelectedPiece.getUnitClass() < attackedEnemyUnit.getUnitClass()){
                            GamePanel.board.setLocationNull(choiceY, choiceX);
                            GamePanel.board.swapLocations(choiceY, choiceX, playerSelectedPiece.getRow(), playerSelectedPiece.getCol());
                        }
                    }else{
                        //attacking not a unit
                        if(GamePanel.board.getAtLocation(choiceY, choiceX) instanceof Bomb){
                            GamePanel.board.setLocationNull(choiceY, choiceX);
                            GamePanel.board.setLocationNull(playerSelectedPiece.getRow(), playerSelectedPiece.getCol());
                        }else if(GamePanel.board.getAtLocation(choiceY, choiceX) instanceof Flag){
                            //System.out.println("You win");
                            gsm.setState(GameStateManager.WINSTATE);
                            stillPlaying = false;
                        }
                    }
                    playerSelectedPiece = null;
                    isPlayersTurn = false;
                }
            }
        }
        if(Keys.isPressed(Keys.Z)){
            if(isPlayersTurn){
                playerSelectedPiece = null;
            }
        }
    }

    private boolean canPlayerMove(Unit unit){
        return canMoveFront(unit) || canMoveRight(unit) || canMoveLeft(unit); 
    }

    private boolean canMoveFront(Unit unit){
        return GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() - 1) == null || GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() - 1).isEnemy();
    }

    private boolean canMoveRight(Unit unit){
        return GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol()) == null || GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol()).isEnemy();
    }

    private boolean canMoveLeft(Unit unit){
        return GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol()) == null || GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol()).isEnemy();
    }
}