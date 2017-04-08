/**
 * Created by William on 4/4/2017.
 */
public class AI {
    //Game vars
    Player playerOne;
    Player playerTwo;
    GameBoard gameBoard;

    //AI vars
    int colPos=103;
    int rowPos=104;
    int lastColBuilt;
    int lastRowBuilt;
    int lastColPlace;
    int lastRowPlace;
    int settlementsBuiltInARow = 0;


    AI(){
        playerOne = new Player(1);
        playerTwo = new Player(2);
        gameBoard = new GameBoard();
    }

    //input from server is parsed, and the appropriate methods called with the given input

    void placeFirstTile(){
        gameBoard.placeFirstTileAndUpdateValidPlacementList();
    }

    void placeForOtherPlayer(Tile tile, int colPos, int rowPos){
        if(gameBoard.getGameBoardPositionArray()[colPos][rowPos]!=null) {
            if (gameBoard.getGameBoardPositionArray()[colPos][rowPos].getLevel() >= 1) {
                gameBoard.nukeTiles(colPos, rowPos, tile);
            } else {
                gameBoard.setTileAtPosition(colPos, rowPos, tile);
            }
        }else{
            gameBoard.setTileAtPosition(colPos,rowPos,tile);
        }
    }

    void buildForOtherPlayer(BuildType buildType, int colPos, int rowPos, terrainTypes type){
        if(buildType == BuildType.FOUND_SETTLEMENT){
            gameBoard.buildSettlement(colPos,rowPos,playerTwo);
        }

        if(buildType == BuildType.EXPAND_SETTLMENT){
            gameBoard.expandSettlement(colPos,rowPos,type,playerTwo);
        }
        if(buildType == BuildType.PLACE_TOTORO){
            int settlementID = gameBoard.findAdjacentSettlementWithoutTotoro(colPos,rowPos);
            if(settlementID != -1) {
                gameBoard.placeTotoroSanctuary(colPos, rowPos, settlementID, playerTwo);
            }
        }
        if(buildType == BuildType.PLACE_TIGER){
            int settlementID = gameBoard.findAdjacentSettlementWithoutTiger(colPos,rowPos);
            if(settlementID != -1) {
                gameBoard.placeTigerPen(colPos, rowPos, settlementID, playerTwo);
            }
        }
    }

    //returns strings to send to server, describing our actions

    String placeForOurPlayer(Tile tile){
        tile.flip();
        String returnString = "PLACED " + tileToString(tile) + " AT";
        while(true) {//until we place a tile, in which case we will break to exit
            if (!gameBoard.hexesToPlaceTileOnAreAlreadyOccupied(colPos, rowPos, tile)) {
                gameBoard.setTileAtPosition(colPos,rowPos,tile);
                returnString += oddRToCubicString(colPos,rowPos);
                break;//we have placed tile
            }else{
                colPos++;//we move over one, until we find a space we can place
                //System.out.println(rowPos);
            }
        }
        System.out.println(returnString);
        return returnString;
    }

    String buildForOurPlayer(){
        String returnString = "";

        while(true) {

            //Totoro
            try{
                if(settlementsBuiltInARow==5) {
                    int setID = gameBoard.getGameBoardPositionArray()[lastColBuilt][lastRowBuilt].getSettlementID();
                    if(gameBoard.getGameBoardSettlementListSettlementSize(setID)>=5 && gameBoard.getGameBoardSettlementListTotoroCount(setID)==0){

                        returnString += "BUILD TOTORO SANCTUARY AT " + oddRToCubicString(lastColBuilt+1,lastRowBuilt);

                        gameBoard.getGameBoardPositionArray()[lastColBuilt+1][lastRowBuilt].setPlayerID(playerOne.getPlayerID());
                        gameBoard.getGameBoardPositionArray()[lastColBuilt+1][lastRowBuilt].setTotoroCount(1);
                        playerOne.decreaseTotoroCount();
                        playerOne.increaseScore(200);

                        lastColBuilt += 2;
                        settlementsBuiltInARow = 0;
                    }
                    break;
                }
            }catch (Exception e){
                //happens on first placement
            }



            //build on adjacent tile
            try {
                if (gameBoard.isValidSettlementLocation(lastColBuilt+1, lastRowBuilt) && playerOne.getSettlerCount() >= 1) {
                    gameBoard.buildSettlement(lastColBuilt+1, lastRowBuilt, playerOne);
                    returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(lastColBuilt+1,lastRowBuilt);
                    lastColBuilt++;
                    settlementsBuiltInARow++;
                    // System.out.println("yay we placed in a line");
                    break;
                }
            }catch (Exception e){
                //happens on first placement;
            }

            settlementsBuiltInARow=1;



            //build on new tile (conflict)
            if(rowPos%2==0){
                if (gameBoard.isValidSettlementLocation(colPos-1, rowPos+1) && playerOne.getSettlerCount() >= 1) {
                    gameBoard.buildSettlement(colPos-1,rowPos+1,playerOne);
                    returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(colPos-1,rowPos+1);
                    lastColBuilt = colPos-1;
                    lastRowBuilt = rowPos+1;
                    break;
                }
                if(gameBoard.isValidSettlementLocation(colPos,rowPos+1) && playerOne.getSettlerCount() >= 1) {
                    gameBoard.buildSettlement(colPos, rowPos + 1, playerOne);
                    returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(colPos,rowPos+1);
                    lastColBuilt = colPos;
                    lastRowBuilt = rowPos+1;
                    break;
                }
            }else {
                if (gameBoard.isValidSettlementLocation(colPos, rowPos + 1) && playerOne.getSettlerCount() >= 1) {
                    gameBoard.buildSettlement(colPos, rowPos + 1, playerOne);
                    returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(colPos,rowPos+1);
                    lastColBuilt = colPos;
                    lastRowBuilt = rowPos+1;
                    break;
                }
                if (gameBoard.isValidSettlementLocation(colPos+1, rowPos + 1) && playerOne.getSettlerCount() >= 1) {
                    gameBoard.buildSettlement(colPos+1, rowPos + 1, playerOne);
                    returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(colPos+1,rowPos+1);
                    lastColBuilt = colPos+1;
                    lastRowBuilt = rowPos+1;
                    break;
                }
            }
        }
        System.out.println(returnString);
        return returnString;
    }

    String tileToString(Tile tile){
        String returnString = "";

        if(tile.isFlipped()){
            if (tile.getHexC().getTerrainType() == terrainTypes.LAKE) {
                returnString += "LAKE";
            }
            if (tile.getHexC().getTerrainType() == terrainTypes.ROCKY) {
                returnString += "ROCK";
            }
            if (tile.getHexC().getTerrainType() == terrainTypes.GRASSLANDS) {
                returnString += "GRASS";
            }
            if (tile.getHexC().getTerrainType() == terrainTypes.JUNGLE) {
                returnString += "JUNGLE";
            }

            returnString += "+";

            if (tile.getHexB().getTerrainType() == terrainTypes.LAKE) {
                returnString += "LAKE";
            }
            if (tile.getHexB().getTerrainType() == terrainTypes.ROCKY) {
                returnString += "ROCK";
            }
            if (tile.getHexB().getTerrainType() == terrainTypes.GRASSLANDS) {
                returnString += "GRASS";
            }
            if (tile.getHexB().getTerrainType() == terrainTypes.JUNGLE) {
                returnString += "JUNGLE";
            }

        }else {
            if (tile.getHexB().getTerrainType() == terrainTypes.LAKE) {
                returnString += "LAKE";
            }
            if (tile.getHexB().getTerrainType() == terrainTypes.ROCKY) {
                returnString += "ROCK";
            }
            if (tile.getHexB().getTerrainType() == terrainTypes.GRASSLANDS) {
                returnString += "GRASS";
            }
            if (tile.getHexB().getTerrainType() == terrainTypes.JUNGLE) {
                returnString += "JUNGLE";
            }

            returnString += "+";

            if (tile.getHexC().getTerrainType() == terrainTypes.LAKE) {
                returnString += "LAKE";
            }
            if (tile.getHexC().getTerrainType() == terrainTypes.ROCKY) {
                returnString += "ROCK";
            }
            if (tile.getHexC().getTerrainType() == terrainTypes.GRASSLANDS) {
                returnString += "GRASS";
            }
            if (tile.getHexC().getTerrainType() == terrainTypes.JUNGLE) {
                returnString += "JUNGLE";
            }
        }
        return returnString;
    }

    String oddRToCubicString(int col, int row){
        String returnString = "";
        col -= 102;
        row -= 102;

        int x = col - (row - (row&1)) / 2;
        int z = row;
        int y = -x-z;

        returnString += " " + x + " " + y + " " + z;
        return returnString;
    }
}
