
/**
 * Write a description of class Computer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Computer{
    public void setInitialState(Board board){
        board.setAtLocation(new Flag(0, 0));//(x, y)
        board.setAtLocation(new Bomb(1, 0));
        board.setAtLocation(new Marshal(2, 0));
        board.setAtLocation(new Spy(3, 0));
        board.setAtLocation(new General(4, 0));
        board.setAtLocation(new Scout(5, 0));
        board.setAtLocation(new Colonel(6, 0));
        board.setAtLocation(new Colonel(7, 0));
        board.setAtLocation(new Bomb(8, 0));
        board.setAtLocation(new Scout(9, 0));
        board.setAtLocation(new Bomb(0, 1));
        board.setAtLocation(new Bomb(1, 1));
        board.setAtLocation(new Major(2, 1));
        board.setAtLocation(new Major(3, 1));
        board.setAtLocation(new Major(4, 1));
        board.setAtLocation(new Captain(5, 1));
        board.setAtLocation(new Captain(6, 1));
        board.setAtLocation(new Captain(7, 1));
        board.setAtLocation(new Bomb(8, 1));
        board.setAtLocation(new Bomb(9, 1));
        board.setAtLocation(new Captain(0, 2));
        board.setAtLocation(new Lieutenant(1, 2));
        board.setAtLocation(new Lieutenant(2, 2));
        board.setAtLocation(new Lieutenant(3, 2));
        board.setAtLocation(new Lieutenant(4, 2));
        board.setAtLocation(new Scout(5, 2));
        board.setAtLocation(new Sergeant(6, 2));
        board.setAtLocation(new Sergeant(7, 2));
        board.setAtLocation(new Sergeant(8, 2));
        board.setAtLocation(new Sergeant(9, 2));
        board.setAtLocation(new Miner(0, 3));
        board.setAtLocation(new Miner(1, 3));
        board.setAtLocation(new Miner(2, 3));
        board.setAtLocation(new Scout(3, 3));
        board.setAtLocation(new Scout(4, 3));
        board.setAtLocation(new Scout(5, 3));
        board.setAtLocation(new Scout(6, 3));
        board.setAtLocation(new Scout(7, 3));
        board.setAtLocation(new Miner(8, 3));
        board.setAtLocation(new Miner(9, 3));

        for(int r = 0; r < 4; r++){
            for(int c = 0; c < board.getNumCols(); c++){
                if(board.getAtLocation(c, r) != null){
                    board.getAtLocation(c, r).setEnemy();
                    board.getAtLocation(c, r).switchHiddenState();
                }
            }
        }
    }

    public void getNextMove(Board board){
        //TODO make this code better later after it works
        Unit unit = getNearestUnitToActor(findPlayerFlag());

        double downDist = 999;
        double rightDist = 1000;
        double leftDist = 1001;

        Actor actor = findPlayerFlag();
        int actorY = actor.getCol();
        int actorX = actor.getRow();

        if(canMoveDown(unit)){
            downDist = Math.sqrt(Math.pow((actorX - unit.getRow()), 2) + Math.pow((actorY - unit.getCol() + 1), 2));
            downDist -= 1.5;
        }

        if(canMoveRight(unit)){
            rightDist = Math.sqrt(Math.pow((actorX - unit.getRow() + 1), 2) + Math.pow((actorY - unit.getCol()), 2));
        }

        if(canMoveLeft(unit)){
            leftDist = Math.sqrt(Math.pow((actorX - unit.getRow() - 1), 2) + Math.pow((actorY - unit.getCol()), 2));
        }

        //System.out.println("down: " + downDist + " right: " + rightDist + " left: " + leftDist);

        if(Math.min(downDist, Math.min(rightDist, leftDist)) == downDist){
            if(GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() + 1) == null){
                GamePanel.board.swapLocations(unit.getRow(), unit.getCol(), unit.getRow(), unit.getCol() + 1);
            }else if(GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() + 1) instanceof Bomb){
                GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                GamePanel.board.setLocationNull(unit.getRow(), unit.getCol() + 1);
            }else if(GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() + 1) instanceof Flag){
                //System.out.println("Computer won!");
                PlayingGame.computerReachedFlag = true;
            }else{
                //dealing player piece
                int playerPieceClass = ((Unit) GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() + 1)).getUnitClass();
                if(playerPieceClass == unit.getUnitClass()){
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol() + 1);
                }else if(playerPieceClass > unit.getUnitClass()){
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol() + 1);
                    GamePanel.board.swapLocations(unit.getRow(), unit.getCol(), unit.getRow(), unit.getCol() + 1);
                }else{
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                }
            }
        }else if(Math.min(downDist, Math.min(rightDist, leftDist)) == rightDist){
            if(GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol()) == null){
                GamePanel.board.swapLocations(unit.getRow(), unit.getCol(), unit.getRow() + 1, unit.getCol());
            }else if(GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol()) instanceof Bomb){
                GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                GamePanel.board.setLocationNull(unit.getRow() + 1, unit.getCol());
            }else if(GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol()) instanceof Flag){
                //System.out.println("Computer won!");
                PlayingGame.computerReachedFlag = true;
            }else{
                //dealing player piece
                int playerPieceClass = ((Unit) GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol())).getUnitClass();
                if(playerPieceClass == unit.getUnitClass()){
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                    GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol());
                }else if(playerPieceClass > unit.getUnitClass()){
                    GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol());
                    GamePanel.board.swapLocations(unit.getRow(), unit.getCol(), unit.getRow() + 1, unit.getCol());
                }else{
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                }
            }
        }else{
            if(GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol()) == null){
                GamePanel.board.swapLocations(unit.getRow(), unit.getCol(), unit.getRow() - 1, unit.getCol());
            }else if(GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol()) instanceof Bomb){
                GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                GamePanel.board.setLocationNull(unit.getRow() - 1, unit.getCol());
            }else if(GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol()) instanceof Flag){
                //System.out.println("Computer won!");
                PlayingGame.computerReachedFlag = true;
            }else{
                //dealing player piece
                int playerPieceClass = ((Unit) GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol())).getUnitClass();
                if(playerPieceClass == unit.getUnitClass()){
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                    GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol());
                }else if(playerPieceClass > unit.getUnitClass()){
                    GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol());
                    GamePanel.board.swapLocations(unit.getRow(), unit.getCol(), unit.getRow() - 1, unit.getCol());
                }else{
                    GamePanel.board.setLocationNull(unit.getRow(), unit.getCol());
                }
            }
        }
    }

    private Unit getNearestUnitToActor(Actor actor){
        int actorY = actor.getCol();
        int actorX = actor.getRow();

        double closest = 1000;
        Unit closestUnit = null;

        Unit compUnit;
        for(int r = 0; r < GamePanel.board.getNumRows(); r++){
            for(int c = 0; c < GamePanel.board.getNumCols(); c++){
                Actor boardSpot = GamePanel.board.getAtLocation(r, c);
                if(boardSpot != null && boardSpot.isEnemy() && boardSpot instanceof Unit){
                    compUnit = (Unit) boardSpot;
                    if(canMovePiece(compUnit)){
                        double downDist = 1000;
                        double rightDist = 1000;
                        double leftDist = 1000;
                        
                        if(canMoveDown(compUnit)){
                            downDist = Math.sqrt(Math.pow((actorX - compUnit.getRow()), 2) + Math.pow((actorY - compUnit.getCol() + 1), 2));
                        }

                        if(canMoveRight(compUnit)){
                            rightDist = Math.sqrt(Math.pow((actorX - compUnit.getRow() + 1), 2) + Math.pow((actorY - compUnit.getCol()), 2));
                        }

                        if(canMoveLeft(compUnit)){
                            leftDist = Math.sqrt(Math.pow((actorX - compUnit.getRow() - 1), 2) + Math.pow((actorY - compUnit.getCol()), 2));
                        }
                        
                        
                        
                        //double dist = Math.sqrt(Math.pow((actorX - compUnit.getRow()), 2) + Math.pow((actorY - compUnit.getCol()), 2));
                        //System.out.println("Flag X " + actorX + " Y " + actorY + " Piece X " + compUnit.getRow() + " Y " + compUnit.getCol() + " Dist " + dist);
                        //System.out.println("Distance at X " + compUnit.getRow() + " Y " + compUnit.getCol() + " Dist " + dist);
                        if(Math.min(downDist, Math.min(rightDist, leftDist)) < closest){
                            closestUnit = compUnit;
                            closest = Math.min(downDist, Math.min(rightDist, leftDist));
                        }
                    }
                }
            }
        }
        //System.out.println("Nearest unit found at: X" + closestUnit.getRow() + " Y" + closestUnit.getCol());
        return closestUnit;
    }

    private Actor findPlayerFlag(){
        for(int r = 0; r < GamePanel.getBoardSize(); r++){
            for(int c = 0; c < GamePanel.getBoardSize(); c++){
                Actor spotPiece = GamePanel.board.getAtLocation(r, c);
                if(spotPiece instanceof Flag && !spotPiece.isEnemy()){
                    Flag flag = (Flag) spotPiece;
                    //System.out.println("Flag found at: X" + flag.getRow() + " Y" + flag.getCol());
                    return flag;
                }
            }
        }
        return null;//this shouldn't happen
    }

    private boolean canMovePiece(Unit unit){
        return canMoveDown(unit) || canMoveRight(unit) || canMoveLeft(unit);
    }

    private boolean canMoveDown(Unit unit){
        Actor actor = GamePanel.board.getAtLocation(unit.getRow(), unit.getCol() + 1);
        return actor == null || !(actor instanceof Rock) && !actor.isEnemy();
    }

    private boolean canMoveRight(Unit unit){
        Actor actor = GamePanel.board.getAtLocation(unit.getRow() + 1, unit.getCol());
        return actor == null || !(actor instanceof Rock) && !actor.isEnemy();
    }

    private boolean canMoveLeft(Unit unit){
        Actor actor = GamePanel.board.getAtLocation(unit.getRow() - 1, unit.getCol());
        return actor == null || !(actor instanceof Rock) && !actor.isEnemy();
    }
}