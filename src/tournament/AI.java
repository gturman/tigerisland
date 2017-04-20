package tournament;

import dataStructures.Pair;
import enums.BuildType;
import enums.terrainTypes;
import gameRules.GameBoard;
import gameRules.Tile;
import gameRules.Hex;
import gameRules.Player;


public class AI {
    Player playerOne;
    Player playerTwo;
    GameBoard gameBoard;

    int colPos=103;
    int rowPos=104;
    int lastColBuilt;
    int lastRowBuilt;
    int lastColPlace;
    int lastRowPlace;
    String tileOrientation = "";

    int settlementsBuiltInARow = 0;

    int moveNumber = 0;

    boolean tigerPlacementMooreContinuumStrat = true;
    boolean totoroPlacementShadowRealmStrat = false;
    int initialColPosTigerStrat = 103;
    int initialRowPosTigerStrat = 99;
    int colOffsetTigerStrat = 0;
    int rowOffsetTigerStrat = 0;
    boolean tryingToNuke = false;

    int initialColPosTotoroStrat = 101;
    int initialRowPosTotoroStrat = 104;
    int colOffsetTotoroStrat = 0;
    int rowOffsetTotoroStrat = 0;

    int initialBuildColPosTigerStrat = 103;
    int initialBuildRowPosTigerStrat = 100;
    int buildOffsetColPosTigerStrat = 0;
    int buildOffsetRowPosTigerStrat = 0;

    int initialBuildColPosTotoroStrat = 101;
    int initialBuildRowPosTotoroStrat = 105;


    boolean settingInitialSettlement = true;
    boolean settingExpansion = false;



    public AI(){
        playerOne = new Player(1);
        playerTwo = new Player(2);
        gameBoard = new GameBoard();
        placeFirstTile();
    }

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
            int settlementID = gameBoard.findAdjacentSettlementWithoutTiger(colPos,rowPos,playerTwo);
            if(settlementID != -1) {
                gameBoard.placeTigerPen(colPos, rowPos, settlementID, playerTwo);
            }
        }
    }


    public String placeForOurPlayer(terrainTypes terrainA, terrainTypes terrainB, terrainTypes terrainC){


        Tile tile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainA, terrainB, terrainC);
        String returnString = "";

        flipAndRotationForTigerStrat(tile);

        if(tryingToNuke){
                if(gameBoard.nukeAtPositionIsValid(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTotoroStrat,tile)){
                    tigerPlacementMooreContinuumStrat = true;
                    totoroPlacementShadowRealmStrat = false;

                }else{
                    tigerPlacementMooreContinuumStrat = false;
                    totoroPlacementShadowRealmStrat = true;
                    tryingToNuke = false;

                }
        }

        if(gameBoard.hexesToPlaceTileOnAreAlreadyOccupied(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat, tile)){ // failing tiger strat
            tigerPlacementMooreContinuumStrat = false;
            totoroPlacementShadowRealmStrat = true;

        }

        if(tigerPlacementMooreContinuumStrat){
            if(colOffsetTigerStrat == 0 && rowOffsetTigerStrat == 0){
                gameBoard.setTileAtPosition(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat, tile);
                tileOrientation = " 4";
                returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                colOffsetTigerStrat = 1;
                rowOffsetTigerStrat = 0;
                return returnString;
            }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == 0){
                gameBoard.setTileAtPosition(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat,tile);
                tileOrientation = " 1";
                returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                colOffsetTigerStrat = 2;
                rowOffsetTigerStrat = 0;
                return returnString;
            }else if(colOffsetTigerStrat == 2 && rowOffsetTigerStrat == 0){
                gameBoard.setTileAtPosition(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat,tile);
                tileOrientation = " 4";
                returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                colOffsetTigerStrat = 1;
                rowOffsetTigerStrat = -2;
                return returnString;
            }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == -2){
                if(tryingToNuke){
                    gameBoard.nukeTiles(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat, tile);
                    tileOrientation = " 4";
                    returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                    colOffsetTigerStrat = 1;
                    rowOffsetTigerStrat = -1;

                    tryingToNuke = true;
                    return returnString;
                }else {
                    gameBoard.setTileAtPosition(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat, tile);
                    tileOrientation = " 1";
                    returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                    colOffsetTigerStrat = 1;
                    rowOffsetTigerStrat = 1;
                    tryingToNuke = true;
                    return returnString;
                }
            }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == 1){
                if(tryingToNuke) {
                    gameBoard.nukeTiles(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat, tile);
                    tileOrientation = " 3";
                    returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                    colOffsetTigerStrat = 1;
                    rowOffsetTigerStrat = -2;
                    tryingToNuke = true;
                    return returnString;
                }
            }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == -1){
                if(tryingToNuke) {
                    gameBoard.nukeTiles(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat, tile);
                    tileOrientation = " 2";
                    returnString = "PLACE " + tileToString(tile) + " AT" + oddRToCubicString(initialColPosTigerStrat + colOffsetTigerStrat, initialRowPosTigerStrat + rowOffsetTigerStrat) + tileOrientation;
                    initialColPosTigerStrat = 107;
                    initialRowPosTigerStrat = 99;
                    colOffsetTigerStrat = 0;
                    rowOffsetTigerStrat = 0;
                    tryingToNuke = false;
                    return returnString;
                }
            }




        }else if(totoroPlacementShadowRealmStrat){
            Tile tile2 = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainA, terrainB, terrainC);
            tile2.flip();

            while(gameBoard.hexesToPlaceTileOnAreAlreadyOccupied(initialColPosTotoroStrat + colOffsetTotoroStrat, initialRowPosTotoroStrat + rowOffsetTotoroStrat, tile2)){
                colOffsetTotoroStrat--;
            }

            gameBoard.setTileAtPosition(initialColPosTotoroStrat + colOffsetTotoroStrat, initialRowPosTotoroStrat + rowOffsetTotoroStrat, tile2);
            returnString = "PLACE " + tileToString(tile2) + " AT" + oddRToCubicString(initialColPosTotoroStrat + colOffsetTotoroStrat, initialRowPosTotoroStrat + rowOffsetTotoroStrat) + " 4";
            colOffsetTotoroStrat-=2;

        }



       
        return returnString;
    }

    void flipAndRotationForTigerStrat(Tile tile) {
        if(colOffsetTigerStrat == 0 && rowOffsetTigerStrat == 0){
            tile.flip();
        }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == 0){

        }else if(colOffsetTigerStrat == 2 && rowOffsetTigerStrat == 0){
            tile.flip();
        }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == -2){
            if(tryingToNuke){
                tile.flip();
            }else {

            }
        }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == 1){
            if(tryingToNuke) {
                tile.rotateTileClockwise(1);
            }
        }else if(colOffsetTigerStrat == 1 && rowOffsetTigerStrat == -1){
            if(tryingToNuke) {
                tile.flip();
                tile.rotateTileClockwise(2);
            }
        }
    }

    public String buildForOurPlayer(){
        String returnString = "";

        if(playerOne.getVillagerCount() == 0) {
            returnString += "UNABLE TO BUILD";
        }

        if(buildOffsetColPosTigerStrat == 1 && buildOffsetRowPosTigerStrat == -1){
            if(!gameBoard.checkIfValidTigerPlacement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, gameBoard.getGameBoardPositionSettlementID(new Pair(initialBuildColPosTigerStrat + 2, initialBuildRowPosTigerStrat + 0)) ,  playerOne)){
                tigerPlacementMooreContinuumStrat = false;
                totoroPlacementShadowRealmStrat = true;
            }
        }else if(!gameBoard.isValidSettlementLocation(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat)){
            tigerPlacementMooreContinuumStrat = false;
            totoroPlacementShadowRealmStrat = true;
        }



        if(tigerPlacementMooreContinuumStrat){
            if(buildOffsetColPosTigerStrat == 0 && buildOffsetRowPosTigerStrat == 0){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 1;
                buildOffsetRowPosTigerStrat = 0;

            }else if(buildOffsetColPosTigerStrat == 1 && buildOffsetRowPosTigerStrat == 0){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 2;
                buildOffsetRowPosTigerStrat = 0;

            }else if(buildOffsetColPosTigerStrat == 2 && buildOffsetRowPosTigerStrat == 0){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 3;
                buildOffsetRowPosTigerStrat = 0;

            } else if(buildOffsetColPosTigerStrat == 3 && buildOffsetRowPosTigerStrat == 0){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 4;
                buildOffsetRowPosTigerStrat = 0;

            } else if(buildOffsetColPosTigerStrat == 4 && buildOffsetRowPosTigerStrat == 0){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 2;
                buildOffsetRowPosTigerStrat = -4;

            } else if(buildOffsetColPosTigerStrat == 2 && buildOffsetRowPosTigerStrat == -4){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 1;
                buildOffsetRowPosTigerStrat = -4;

            }else if(buildOffsetColPosTigerStrat == 1 && buildOffsetRowPosTigerStrat == -4){
                gameBoard.buildSettlement(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat, playerOne);
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                buildOffsetColPosTigerStrat = 1;
                buildOffsetRowPosTigerStrat = -1;

            }else if(buildOffsetColPosTigerStrat == 1 && buildOffsetRowPosTigerStrat == -1){
                gameBoard.placeTigerPen(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat, initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat,gameBoard.getGameBoardPositionSettlementID(new Pair(initialBuildColPosTigerStrat + 2, initialBuildRowPosTigerStrat + 0)) ,  playerOne);
                returnString += "BUILD TIGER PLAYGROUND AT" + oddRToCubicString(initialBuildColPosTigerStrat + buildOffsetColPosTigerStrat,initialBuildRowPosTigerStrat + buildOffsetRowPosTigerStrat);
                initialBuildColPosTigerStrat = 107;
                initialBuildRowPosTigerStrat = 100;
                buildOffsetColPosTigerStrat = 0;
                buildOffsetRowPosTigerStrat = 0;
            }
        }else if(totoroPlacementShadowRealmStrat){
            if(settingInitialSettlement){
                gameBoard.buildSettlement(initialBuildColPosTotoroStrat, initialBuildRowPosTotoroStrat, playerOne);
                settingInitialSettlement = false;
                returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTotoroStrat,initialBuildRowPosTotoroStrat);
            }else {
                try {
                    while (!(gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat][initialBuildRowPosTotoroStrat].isNotBuiltOn())) { // find first free hex
                        initialBuildColPosTotoroStrat--;
                        System.out.println(initialBuildColPosTotoroStrat);
                    }
                }catch(NullPointerException e){}

                if (gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat+1][initialBuildRowPosTotoroStrat].getOwningPlayerID() == playerOne.getPlayerID()) { // previous spot has settlement
                    if (gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionSettlementID(new Pair(initialBuildColPosTotoroStrat+1, initialBuildRowPosTotoroStrat))) >= 5 && playerOne.getTotoroCount() >= 1) {
                        // build totoro
                        gameBoard.placeTotoroSanctuary(initialBuildColPosTotoroStrat,initialBuildRowPosTotoroStrat,gameBoard.getGameBoardPositionSettlementID(new Pair(initialBuildColPosTotoroStrat+1, initialBuildRowPosTotoroStrat)) ,playerOne);
                        returnString += "BUILD TOTORO SANCTUARY AT" + oddRToCubicString(initialBuildColPosTotoroStrat,initialBuildRowPosTotoroStrat);
                        initialBuildColPosTotoroStrat-=2;
                    } else { // expand
                        /*if(gameBoard.calculateVillagersForExpansion(initialBuildColPosTotoroStrat+1, initialBuildRowPosTotoroStrat, gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat][initialBuildRowPosTotoroStrat].getTerrainType(), playerOne) < playerOne.getVillagerCount()) {
                            System.out.println("Before: " +  playerOne.getVillagerCount());
                            gameBoard.expandSettlement(initialBuildColPosTotoroStrat + 1, initialBuildRowPosTotoroStrat, gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat][initialBuildRowPosTotoroStrat].getTerrainType(), playerOne);
                            returnString += "EXPAND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTotoroStrat + 1, initialBuildRowPosTotoroStrat) + " " + terrainToStringTerrain(gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat][initialBuildRowPosTotoroStrat].getTerrainType());
                            System.out.println("AFTER vllgr: " + gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat][initialBuildRowPosTotoroStrat].getVillagerCount());
                            System.out.println("AFTER: " + playerOne.getVillagerCount());
                            System.out.println((gameBoard.getGameBoardPositionArray()[initialBuildColPosTotoroStrat][initialBuildRowPosTotoroStrat].isNotBuiltOn()));
                        }else{// found settlement
                          */
                            gameBoard.buildSettlement(initialBuildColPosTotoroStrat, initialBuildRowPosTotoroStrat, playerOne);
                            returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTotoroStrat,initialBuildRowPosTotoroStrat);

                        //}
                    }
                } else { // found settlement
                    gameBoard.buildSettlement(initialBuildColPosTotoroStrat, initialBuildRowPosTotoroStrat, playerOne);
                    returnString += "FOUND SETTLEMENT AT" + oddRToCubicString(initialBuildColPosTotoroStrat,initialBuildRowPosTotoroStrat);
                }
            }
        }else{
            returnString += "UNABLE TO BUILD";
        }

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
    String terrainToStringTerrain(terrainTypes X){
        String returnString = "";

        if (X == terrainTypes.LAKE) {
            returnString += "LAKE";
        }
        if (X == terrainTypes.ROCKY) {
            returnString += "ROCK";
        }
        if (X == terrainTypes.GRASSLANDS) {
            returnString += "GRASS";
        }
        if (X == terrainTypes.JUNGLE) {
            returnString += "JUNGLE";
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
        return playerOne.getVillagerCount();
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
        return playerTwo.getVillagerCount();
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
