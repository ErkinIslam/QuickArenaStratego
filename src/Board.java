
/**
 * Write a description of class Board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Board{
    private Actor[][] board;
    public Board(int rows, int cols){
        board = new Actor[rows][cols];
    }

    public Actor getAtLocation(int row, int col){
        if(row >= board[0].length || col >= board[1].length || row < 0 || col < 0){
            Rock rock = new Rock(0, 0);
            return rock;
        }else{
            return board[row][col];
        }
    }

    public boolean setAtLocation(Actor actor){
        if(actor.getRow() < board[0].length && actor.getCol() < board[1].length && actor.getRow() >= 0 && actor.getCol() >= 0){
            //Actor temp = board[actor.getRow()][actor.getCol()];
            board[actor.getRow()][actor.getCol()] = actor;
            return true;
        }
        return false;
    }

    public void setLocationNull(int row, int col){
        if(row < board[0].length && col < board[1].length && row >= 0 && col >= 0){
            board[row][col] = null;
        }
    }

    /**
     * Swaps the actor at r1 and c1 with the actor at r2 and c2
     */
    public void swapLocations(int r1, int c1, int r2, int c2){
        if(r1 < board[0].length && r1 >= 0 & c1 < board[1].length && c1 >= 0 && r2 < board[0].length && r2 >= 0 & c2 < board[1].length && c2 >= 0){
            Actor subActor = board[r1][c1];
        board[r1][c1] = board[r2][c2];
        board[r2][c2] = subActor;
        }else{
            System.out.println("Board: void swapLocations(int, int, int, int)\nr1 = " + r1 + " c1 = " + c1 + " r2 = " + r2 + " c2 = " + c2);
        }
        
    }

    public void resetBoard(){
        board = new Actor[board[0].length][board[1].length];
    }

    public int getNumRows(){
        return board[0].length;
    }

    public int getNumCols(){
        return board[1].length;
    }

    public void recalibrateBoard(){
        for(int r = 0; r < board[0].length; r++){
            for(int c = 0; c < board[1].length; c++){
                if(board[r][c] != null){
                    board[r][c].setRow(r);
                    board[r][c].setCol(c);
                }
            }
        }
    }
}
