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
        if(gameBoard.gameBoardPositionArray[colPos][rowPos]!=null) {
            if (gameBoard.gameBoardPositionArray[colPos][rowPos].getHexLevel() >= 1) {
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
            gameBoard.placeTotoroSanctuary(colPos,rowPos,settlementID,playerTwo);
        }
        if(buildType == BuildType.PLACE_TIGER){
            int settlementID = gameBoard.findAdjacentSettlementWithoutTiger(colPos,rowPos);
            gameBoard.placeTigerPen(colPos,rowPos,settlementID,playerTwo);
        }
    }

    //returns strings to send to server, describing our actions

    String placeForOurPlayer(Tile tile){
        tile.flip();
        while(true) {//until we place a tile, in which case we will break to exit
            if (gameBoard.checkIfTileCanBePlacedAtPosition(colPos, rowPos, tile)) {
                gameBoard.setTileAtPosition(colPos,rowPos,tile);
                break;//we have placed tile
            }else{
                colPos++;//we move over one, until we find a space we can place
                //System.out.println(rowPos);
            }
        }
        return "";
    }

    String buildForOurPlayer(){
        while(true) {
            //we anticipate this spot to be available

            try{
                if(settlementsBuiltInARow==5) {
                    if (gameBoard.areFiveTotorosInALine(lastColBuilt,lastRowBuilt,playerOne.getPlayerID()) && playerOne.getTotoroCount() >= 1) {

                        gameBoard.gameBoardPositionArray[lastColBuilt+1][lastRowBuilt].setPlayerID(playerOne.getPlayerID());
                        gameBoard.gameBoardPositionArray[lastColBuilt+1][lastRowBuilt].setTotoroCount(1);
                        playerOne.decreaseTotoroCount();
                        playerOne.increaseScore(200);

                        //System.out.println(lastColBuilt+1 + " " + lastRowBuilt);
                        lastColBuilt += 2;
                        settlementsBuiltInARow = 0;
                      //  System.out.println("yay we placed a totoro");
                    }
                    break;
                }
            }catch (Exception e){
                //happens on first placement
            }

            try {
                if (gameBoard.isValidSettlementLocation(lastColBuilt+1, lastRowBuilt) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(lastColBuilt+1, lastRowBuilt, playerOne);
                    lastColBuilt++;
                    settlementsBuiltInARow++;
                   // System.out.println("yay we placed in a line");
                    break;
                }
            }catch (Exception e){
                //happens on first placement;
            }

            settlementsBuiltInARow=1;



            //Next spot we anticipated we could build on was not valid
            if(rowPos%2==0){
                if (gameBoard.isValidSettlementLocation(colPos-1, rowPos+1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(colPos-1,rowPos+1,playerOne);
                    lastColBuilt = colPos-1;
                    lastRowBuilt = rowPos+1;
                    break;
                }
                if(gameBoard.isValidSettlementLocation(colPos,rowPos+1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(colPos, rowPos + 1, playerOne);
                    lastColBuilt = colPos;
                    lastRowBuilt = rowPos+1;
                    break;
                }
            }else {
                if (gameBoard.isValidSettlementLocation(colPos, rowPos + 1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(colPos, rowPos + 1, playerOne);
                    lastColBuilt = colPos;
                    lastRowBuilt = rowPos+1;
                    break;
                }
                if (gameBoard.isValidSettlementLocation(colPos+1, rowPos + 1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(colPos+1, rowPos + 1, playerOne);
                    lastColBuilt = colPos+1;
                    lastRowBuilt = rowPos+1;
                    break;
                }
            }
        }
        return "";
    }

}
