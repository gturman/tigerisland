/*
 * Created by William on 3/14/2017.
 */

public class GameBoard {
    private int boardHeight = 202;
    private int boardWidth = 202;
    private int GameboardTileID;
    private int GameboardHexID;

    public int[][] validPlacementArray = new Hex[boardHeight][boardWidth];
    public Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];

    GameBoard(){
        this.GameboardTileID = 1;
        this.GameboardHexID = 1;
    }

    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped()){
            if(checkIfEven(rowPos)) {
                if(checkIfHexOccupiesPosition(colPos, rowPos) && checkIfHexOccupiesPosition(colPos-1, rowPos+1) && checkIfHexOccupiesPosition(colPos, rowPos+1)){
                    tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
                    tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos-1, rowPos+1);
                    tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos, rowPos+1);

                    this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    this.gameBoardPositionArray[colPos-1][rowPos+1] = tileToBePlaced.getHexB();
                    this.gameBoardPositionArray[colPos][rowPos+1] = tileToBePlaced.getHexC();
                }
            } else {
                if (checkIfHexOccupiesPosition(colPos, rowPos) && checkIfHexOccupiesPosition(colPos, rowPos +1) && checkIfHexOccupiesPosition(colPos + 1, rowPos + 1)) {
                    tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
                    tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos, rowPos + 1);
                    tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos + 1, rowPos + 1);

                    this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    this.gameBoardPositionArray[colPos][rowPos + 1] = tileToBePlaced.getHexB();
                    this.gameBoardPositionArray[colPos + 1][rowPos + 1] = tileToBePlaced.getHexC();
                }
            }
        }else {
            if(checkIfEven(rowPos)) {
                if(checkIfHexOccupiesPosition(colPos, rowPos) && checkIfHexOccupiesPosition(colPos-1, rowPos-1) && checkIfHexOccupiesPosition(colPos, rowPos-1)){
                    tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
                    tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos-1, rowPos-1);
                    tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos, rowPos-1);

                    this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    this.gameBoardPositionArray[colPos-1][rowPos-1] = tileToBePlaced.getHexB();
                    this.gameBoardPositionArray[colPos][rowPos-1] = tileToBePlaced.getHexC();
                }
            } else {
                if (checkIfHexOccupiesPosition(colPos, rowPos) && checkIfHexOccupiesPosition(colPos, rowPos - 1) && checkIfHexOccupiesPosition(colPos + 1, rowPos - 1)) {
                    tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
                    tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos, rowPos - 1);
                    tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos + 1, rowPos - 1);

                    this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    this.gameBoardPositionArray[colPos][rowPos - 1] = tileToBePlaced.getHexB();
                    this.gameBoardPositionArray[colPos + 1][rowPos - 1] = tileToBePlaced.getHexC();
                }
            }
        }

        incrementGameboardTileID();
        incrementGameboardHexID();
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
    // todo: odd home hex calculations
    void validTilePlacementList(Tile tileThatWasPlaced) {
        if(tileThatWasPlaced.isFlipped()) {
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-2][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
        }
        else {
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-2][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
            validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
        }

        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 0;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexB())][accessGameboardYValue(tileThatWasPlaced.getHexB())] = 0;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexC())][accessGameboardYValue(tileThatWasPlaced.getHexC())] = 0;
    }

    int accessGameboardXValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getColumnPosition();
    }
    int accessGameboardYValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getRowPosition();
    }



    int getGameboardTileID() {
        return GameboardTileID;
    }

    int getGameBoardHexID() {
        return GameboardHexID;
    }

    void incrementGameboardTileID() {
        this.GameboardTileID += 1;
    }

    void incrementGameboardHexID() {
        this.GameboardHexID += 3;
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }
}