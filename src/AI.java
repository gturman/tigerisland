/**
 * Created by William on 4/4/2017.
 */
public class AI {
    //Game vars
    Player playerOne;
    Player playerTwo;
    GameBoard gameBoard;

    //AI vars
    boolean isFirstNonFirstTilePlacement = true;
    int rowPos=103;
    int colPos=104;


    AI(){
        playerOne = new Player(1);
        playerTwo = new Player(2);
        gameBoard = new GameBoard();
    }

    //input from server is parsed, and the appropriate methods called with the given input

    void placeFirstTile(){
        gameBoard.placeFirstTileAndUpdateValidPlacementList();
    }

    void placeForOtherPlayer(Tile tile, int rowPos, int colPos){
        gameBoard.setTileAtPosition(rowPos,colPos,tile);
    }

    void buildForOtherPlayer(BuildType buildType, int rowPos, int colPos, terrainTypes type){
        if(buildType == BuildType.FOUND_SETTLEMENT){
            gameBoard.buildSettlement(rowPos,colPos,playerTwo);
        }

        if(buildType == BuildType.EXPAND_SETTLMENT){
            gameBoard.expandSettlement(rowPos,colPos,type,playerTwo);
        }
        if(buildType == BuildType.PLACE_TOTORO){
            int settlementID = gameBoard.findAdjacentSettlementWithoutTotoro(rowPos,colPos);
            gameBoard.placeTotoroSanctuary(rowPos,colPos,settlementID,playerTwo);
        }
        if(buildType == BuildType.PLACE_TIGER){
            int settlementID = gameBoard.findAdjacentSettlementWithoutTiger(rowPos,colPos);
            gameBoard.placeTigerPen(rowPos,colPos,settlementID,playerTwo);
        }
    }

    //returns strings to send to server, describing our actions

    String placeForOurPlayer(Tile tile){
        tile.flip();
        while(true) {//until we place a tile, in which case we will break to exit
            if (gameBoard.checkIfTileCanBePlacedAtPosition(rowPos, colPos, tile)) {
                gameBoard.setTileAtPosition(rowPos,colPos,tile);
                break;//we have placed tile
            }else{
                rowPos++;//we move over one, until we find a space we can place
                //System.out.println(colPos);
            }
        }
        return "";
    }

    String buildForOurPlayer(){
        while(true) {
            if(rowPos%2==0){
                if (gameBoard.isValidSettlementLocation(rowPos-1, colPos+1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(rowPos-1,colPos+1,playerOne);
                    break;
                }
                if(gameBoard.isValidSettlementLocation(rowPos,colPos+1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(rowPos, colPos + 1, playerOne);
                    break;
                }
            }else {
                if (gameBoard.isValidSettlementLocation(rowPos, colPos + 1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(rowPos, colPos + 1, playerOne);
                    break;
                }
                if (gameBoard.isValidSettlementLocation(rowPos - 1, colPos + 1) && playerOne.getVillagerCount() >= 1) {
                    gameBoard.buildSettlement(rowPos - 1, colPos + 1, playerOne);
                    break;
                }
            }
        }
        return "";
    }

}
