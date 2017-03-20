/*
 * Created by William on 3/14/2017.
 */

public class GameBoard {
    private int boardHeight = 202;
    private int boardWidth = 202;
    private static int tileID;
    private static int hexID;
    public Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];

    GameBoard(){
        this.tileID = 1;
        this.hexID = 1;
    }

    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if(checkIfEven(rowPos)) {
            if(checkIfHexOccupiesPosition(colPos, rowPos) && checkIfHexOccupiesPosition(colPos-1, rowPos-1) && checkIfHexOccupiesPosition(colPos, rowPos-1)){
                tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
                tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos-1, rowPos-1);
                tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos, rowPos-1);
            }
        } else {
            if(checkIfHexOccupiesPosition(colPos, rowPos) && checkIfHexOccupiesPosition(colPos, rowPos-1) && checkIfHexOccupiesPosition(colPos+1, rowPos-1)){
                tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
                tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos, rowPos-1);
                tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos+1, rowPos-1);
            }
        }
    }

    boolean checkIfHexOccupiesPosition(int colPos, int rowPos) {
        if(gameBoardPositionArray[colPos][rowPos] == null) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean checkIfEven(int rowPos) {
        if (rowPos % 2 == 0) {
            return true;
        } else{
            return false;
        }

    }

}