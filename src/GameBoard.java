/*
 * Created by William on 3/14/2017.
 */

public class GameBoard {
    private int boardHeight = 202;
    private int boardWidth = 202;
    private int GameboardTileID;
    private int GameboardHexID;

    public int[][] validPlacementArray = new int[boardHeight][boardWidth];
    public Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];

    GameBoard(){
        this.GameboardTileID = 1;
        this.GameboardHexID = 1;
    }

    // TODO: REFACTOR ME PLEASE OH GOD
    void nukeTiles(int colPos, int rowPos, Tile tileToBePlaced) {
        if(checkIfValidNuke(colPos, rowPos, tileToBePlaced)) {
            if(tileToBePlaced.isFlipped()) {
                if(checkIfEven(rowPos)) {
                    gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    gameBoardPositionArray[colPos][rowPos+1] = tileToBePlaced.getHexB();
                    gameBoardPositionArray[colPos-1][rowPos+1] = tileToBePlaced.getHexC();
                }
                else {
                    gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    gameBoardPositionArray[colPos+1][rowPos+1] = tileToBePlaced.getHexB();
                    gameBoardPositionArray[colPos][rowPos+1] = tileToBePlaced.getHexC();
                }
            } else {
                if(checkIfEven(rowPos)) {
                    gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    gameBoardPositionArray[colPos-1][rowPos-1] = tileToBePlaced.getHexB();
                    gameBoardPositionArray[colPos][rowPos-1] = tileToBePlaced.getHexC();
                }
                else {
                    gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
                    gameBoardPositionArray[colPos][rowPos-1] = tileToBePlaced.getHexB();
                    gameBoardPositionArray[colPos+1][rowPos-1] = tileToBePlaced.getHexC();
                }
            }
        }
    }

    boolean checkIfValidNuke(int colPos, int rowPos, Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped()) {
            if(checkIfEven(rowPos)) { // FLIPPED, EVEN ROW
                if((gameBoardPositionArray[colPos][rowPos] != null
                   && gameBoardPositionArray[colPos][rowPos+1] != null
                   && gameBoardPositionArray[colPos-1][rowPos+1] != null)
                   &&
                   (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos+1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos-1][rowPos+1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos+1].getHexLevel() == gameBoardPositionArray[colPos-1][rowPos+1].getHexLevel())
                   &&
                   !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos+1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos-1][rowPos+1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos+1].getParentTileID() == gameBoardPositionArray[colPos-1][rowPos+1].getParentTileID())) {
                    if(tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos+1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos-1][rowPos+1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                }
            }
            else { // FLIPPED, ODD ROW
                if((gameBoardPositionArray[colPos][rowPos] != null
                   && gameBoardPositionArray[colPos+1][rowPos+1] != null
                   && gameBoardPositionArray[colPos][rowPos+1] != null)
                   &&
                   (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos+1][rowPos+1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos+1].getHexLevel()
                   && gameBoardPositionArray[colPos+1][rowPos+1].getHexLevel() == gameBoardPositionArray[colPos][rowPos+1].getHexLevel())
                   &&
                   !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos+1][rowPos+1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos+1].getParentTileID()
                   && gameBoardPositionArray[colPos+1][rowPos+1].getParentTileID() == gameBoardPositionArray[colPos][rowPos+1].getParentTileID())) {
                    if(tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos+1][rowPos+1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos+1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                }
            }
        }
        else {
            if(checkIfEven(rowPos)) { // NOT FLIPPED, EVEN ROW
                if((gameBoardPositionArray[colPos][rowPos] != null
                    && gameBoardPositionArray[colPos-1][rowPos-1] != null
                    && gameBoardPositionArray[colPos][rowPos-1] != null)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos-1][rowPos-1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos-1].getHexLevel()
                   && gameBoardPositionArray[colPos-1][rowPos-1].getHexLevel() == gameBoardPositionArray[colPos][rowPos-1].getHexLevel())
                   &&
                   !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos-1][rowPos-1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos-1].getParentTileID()
                   && gameBoardPositionArray[colPos-1][rowPos-1].getParentTileID() == gameBoardPositionArray[colPos][rowPos-1].getParentTileID())) {
                    if(tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos-1][rowPos-1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos-1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                }
            }
            else { // NOT FLIPPED, ODD ROW
                if((gameBoardPositionArray[colPos][rowPos] != null
                   && gameBoardPositionArray[colPos][rowPos-1] != null
                   && gameBoardPositionArray[colPos+1][rowPos-1] != null)
                   &&
                   (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos-1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos+1][rowPos-1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos-1].getHexLevel() == gameBoardPositionArray[colPos+1][rowPos-1].getHexLevel())
                   &&
                   !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos-1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos+1][rowPos-1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos-1].getParentTileID() == gameBoardPositionArray[colPos+1][rowPos-1].getParentTileID())) {
                    if(tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos-1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                    else if(tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos+1][rowPos-1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if(!checkIfTileCanBePlacedAtPosition(colPos,rowPos,tileToBePlaced)) {
            return;
        }
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

        updateValidTilePlacementList(tileToBePlaced);
        incrementGameboardTileID();
        incrementGameboardHexID();
    }

    boolean checkIfTileCanBePlacedAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped()) {
            if(checkIfEven(rowPos)) {
                if(validPlacementArray[colPos][rowPos] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos-1][rowPos+1] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos][rowPos+1] == -1) {
                    return false;
                }
            }
            else {
                if(validPlacementArray[colPos][rowPos] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos-1][rowPos+1] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos+1][rowPos+1] == -1) {
                    return false;
                }
            }
        }
        else {
            if(checkIfEven(rowPos)) {
                if(validPlacementArray[colPos][rowPos] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos-1][rowPos-1] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos][rowPos-1] == -1) {
                    return false;
                }
            }
            else {
                if(validPlacementArray[colPos][rowPos] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos][rowPos-1] == -1) {
                    return false;
                }
                if(validPlacementArray[colPos+1][rowPos-1] == -1) {
                    return false;
                }
            }
        }

        return true;
    }

    boolean checkIfTileBeingPlacedWillBeAdjacent(int colPos, int rowPos, Tile tileBeingPlaced){
        if(tileBeingPlaced.isFlipped()){
            if(validPlacementArray[colPos+1][rowPos+2] == 1){
                return true;
            }else if( validPlacementArray[colPos][rowPos+2] == 1 ){
                return true;
            }else if( validPlacementArray[colPos-1][rowPos+2] == 1){
                return true;
            }else if( validPlacementArray[colPos-2][rowPos+1] == 1) {
                return true;
            }else if( validPlacementArray[colPos-1][rowPos] == 1){
                return true;
            }else if(validPlacementArray[colPos-1][rowPos-1] == 1){
                return true;
            }else if(validPlacementArray[colPos][rowPos-1] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos+1] == 1) {
                return true;
            }
        }else{
            if(validPlacementArray[colPos][rowPos+1] == 1){
                return true;
            }else if( validPlacementArray[colPos-1][rowPos+1] == 1 ){
                return true;
            }else if( validPlacementArray[colPos-1][rowPos] == 1){
                return true;
            }else if( validPlacementArray[colPos-2][rowPos-1] == 1) {
                return true;
            }else if( validPlacementArray[colPos-1][rowPos-2] == 1){
                return true;
            }else if(validPlacementArray[colPos][rowPos-2] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos-2] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos-1] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos] == 1){
                return true;
            }
        }
        return false;
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

    void updateValidTilePlacementList(Tile tileThatWasPlaced) {
        if(tileThatWasPlaced.isFlipped()) {
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] == 0);
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())+2] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-2][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-2][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
        } else {
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())+1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-2][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-2][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())-1][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())-2] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())-1] = 1;
            if(validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())] == 0)
                validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+1][accessGameboardYValue(tileThatWasPlaced.getHexA())] = 1;
        }

        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexB())][accessGameboardYValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexC())][accessGameboardYValue(tileThatWasPlaced.getHexC())] = -1;
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

    int[][] getValidPlacementArray(){
        return validPlacementArray;
    }
}