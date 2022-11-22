import java.awt.image.*;
/**
 * Abstract class Actor - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Actor{
    // instance variables - replace the example below with your own
    private int locRows;
    private int locCols;

    private BufferedImage image;
    private BufferedImage labelImage;

    private boolean isHidden;
    private boolean isEnemy;

    public Actor(int rows, int cols){
        locRows = rows;
        locCols = cols;
        isHidden = false;
    }

    public int getRow(){
        return locRows;
    }

    public int getCol(){
        return locCols;
    }

    public boolean isHidden(){
        return isHidden;
    }

    public boolean switchHiddenState(){
        isHidden = !isHidden;
        return isHidden;
    }

    public boolean isEnemy(){
        return isEnemy;
    }

    public void setEnemy(){
        isEnemy = true;
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }

    public BufferedImage getImage(){
        return image;
    }

    public void setLabelImage(BufferedImage image){
        labelImage = image;
    }

    public BufferedImage getLabelImage(){
        return labelImage;
    }
    
    public void setRow(int row){
        locRows = row;
    }
    
    public void setCol(int col){
        locCols = col;
    }
    
    public String toString(){
        return "Row " + locRows + " Col " + locCols + " isEnemy " + isEnemy;
    }
}
