import java.util.ArrayList;
import java.lang.Math;

/**
 * Created by William on 3/14/2017.
 */
public class GameBoard {

    //starting size is 20x20, can change if we find better values
    private int boardHeight = 20;
    private int boardWidth = 20;
    public Hex[][] board = new Hex[boardHeight][boardWidth];

    GameBoard(){

    }

    //Place first tile in center of game board array, using hex grid  to 2D array conversion
    public void receiveFirstTile(Tile tile){

        int width = (int)Math.floor(boardWidth/2);
        int height = (int)Math.floor(boardHeight/2);

        addHex(tile.getHex(0), width, height);
        addHex(tile.getHex(1), width+2, height);
        addHex(tile.getHex(2), width+1, height-1);

    }

    //Given a hex to be added and a location to add, places a hex at the location
    public void addHex(Hex addedHex, int xLocation, int yLocation){
        board[xLocation][yLocation] = addedHex;
    }

    //returns 2D array representing the current game board
    public Hex[][] getBoard() {
        return board;
    }


}
