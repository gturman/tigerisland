package tournament;

import enums.BuildType;
import enums.terrainTypes;
import gameRules.GameBoard;
import gameRules.Tile;
import gameRules.Hex;
import gameRules.Player;

/**
 * Created by William on 4/4/2017.
 */
public class AI {
    //Game vars
    Player playerOne;
    Player playerTwo;
    GameBoard gameBoard;

    //tournament.AI vars
    int colPos=103;
    int rowPos=104;
    int lastColBuilt;
    int lastRowBuilt;
    int lastColPlace;
    int lastRowPlace;
    int settlementsBuiltInARow = 0;


    public AI(){
        playerOne = new Player(1);
        playerTwo = new Player(2);
        gameBoard = new GameBoard();
        placeFirstTile();
    }

    //input from server is parsed, and the appropriate methods called with the given input

    public void placeFirstTile(){
        gameBoard.placeFirstTileAndUpdateValidPlacementList();
    }

    public void placeForOtherPlayer(terrainTypes terrainA, terrainTypes terrainB, terrainTypes terrainC, int colPos, int rowPos, boolean isFlipped){

        Tile tile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainA, terrainB, terrainC);

        if(isFlipped){
            tile.flip();
        }

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

    public void buildForOtherPlayer(BuildType buildType, int colPos, int rowPos, terrainTypes type){
        if(buildType == BuildType.FOUND_SETTLEMENT){
            gameBoard.buildSettlement(colPos,rowPos,playerTwo);
        }

        if(buildType == BuildType.EXPAND_SETTLMENT){
            gameBoard.expandSettlement(colPos,rowPos,type,playerTwo);
        }
        if(buildType == BuildType.PLACE_TOTORO){
            int settlementID = gameBoard.findAdjacentSettlementWithoutTotoro(colPos,rowPos,playerTwo);
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

    public String placeForOurPlayer(terrainTypes terrainA, terrainTypes terrainB, terrainTypes terrainC){
        Tile tile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainA, terrainB, terrainC);
        tile.flip();

        String returnString = "PLACE " + tileToString(tile) + " AT";
        while(true) {//until we place a tile, in which case we will break to exit
            if (!gameBoard.hexesToPlaceTileOnAreAlreadyOccupied(colPos, rowPos, tile)) {
                gameBoard.setTileAtPosition(colPos,rowPos,tile);
                returnString += oddRToCubicString(colPos,rowPos);
                returnString += " 4";
                break;//we have placed tile
            }else{
                colPos++;//we move over one, until we find a space we can place
                //System.out.println(rowPos);
            }
        }
        System.out.println(returnString);
        return returnString;
    }

    public String buildForOurPlayer(){
        String returnString = "";

        while(true) {

            if(playerOne.getSettlerCount() == 0) {
                returnString += "UNABLE TO BUILD";
                break;
            }

     /*      //Totoro
            try{
                if(settlementsBuiltInARow==5) {
                    int setID = gameBoard.getGameBoardPositionArray()[lastColBuilt][lastRowBuilt].getSettlementID();
                    if(gameBoard.isValidTotoroPlacement(lastColBuilt+1,lastRowBuilt,setID,playerOne) && gameBoard.playerOwnsSettlementWithID(setID,playerOne.getPlayerID())){
                        returnString += "BUILD TOTORO SANCTUARY AT " + oddRToCubicString(lastColBuilt+1,lastRowBuilt);
                        gameBoard.placeTotoroSanctuary(lastColBuilt+1,lastRowBuilt,setID,playerOne);
                        lastColBuilt += 1;
                    }
                    break;
                }
            }catch (Exception e){
                //happens on first placement
            }*/

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

    public Hex[][] getCurrentAIGameBoardPositionArray() {
        return gameBoard.getGameBoardPositionArray();
    }

    public int getPlayerOneScore(){
        return playerOne.getScore();
    }

    public int getPlayerOneVillagerCount(){
        return playerOne.getSettlerCount();
    }

    public int getPlayerOneTotoroCount(){
        return playerOne.getTotoroCount();
    }

    public int getPlayerOneTigerCount(){
        return playerOne.getTigerCount();
    }

    public int getPlayerTwoScore(){
        return playerTwo.getScore();
    }

    public int getPlayerTwoVillagerCount(){
        return playerTwo.getSettlerCount();
    }

    public int getPlayerTwoTotoroCount(){
        return playerTwo.getTotoroCount();
    }

    public int getPlayerTwoTigerCount(){
        return playerTwo.getTigerCount();
    }

    public int getAIGameboardSettlementListSize(int settlementID) {
        return gameBoard.getGameBoardSettlementListSettlementSize(settlementID);
    }

    public int getAIGameboardSettlementListTotoroCount(int settlementID) {
        return gameBoard.getGameBoardSettlementListTotoroCount(settlementID);
    }

    public int getAIGameboardSettlementListTigerCount(int settlementID) {
        return gameBoard.getGameBoardSettlementListTigerCount(settlementID);
    }
}