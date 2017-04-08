import org.junit.*;
/**
 * Created by William on 4/4/2017.
 */
public class AITest {

    @Test
    public void testAbilityToPlaceOpponentTile(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,103,100);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][100].getTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][99].getTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][99].getTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][100].getLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][99].getLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][99].getLevel(),1);
    }

    @Test
    public void testAbilityForOpponentToNuke(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,102,100);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        tile2.flip();
        game1.placeForOtherPlayer(tile2,102,100);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][100].getTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][101].getTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][101].getTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][100].getLevel(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][101].getLevel(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][101].getLevel(),2);

    }

    @Test
    public void testAbilityToBuildSettlementAsOpponent(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,103,100);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,102,99,null);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][99].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][99].getPlayerID(),2);
        Assert.assertEquals(game1.playerTwo.getScore(),1);
        Assert.assertEquals(game1.playerTwo.getSettlerCount(),19);
    }

    @Test
    public void testAbilityForOpponentToExpandOffSizeOneSettlement(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,103,103);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        tile2.flip();
        game1.placeForOtherPlayer(tile2,104,100);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,103,102,null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT,103,102,terrainTypes.GRASSLANDS);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][101].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][101].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][101].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][101].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][103].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][103].getPlayerID(),2);
        Assert.assertEquals(game1.playerTwo.getScore(),4);
        Assert.assertEquals(game1.playerTwo.getSettlerCount(),16);

    }

    @Test
    public void testAbilityForOpponentToExpandOffSizeOnePlusSettlement(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,103,103);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        tile2.flip();
        game1.placeForOtherPlayer(tile2,104,100);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,103,102,null);
        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,103,101,null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT,103,102,terrainTypes.GRASSLANDS);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][101].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][101].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][103].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][103].getPlayerID(),2);
        Assert.assertEquals(game1.playerTwo.getScore(),4);
        Assert.assertEquals(game1.playerTwo.getSettlerCount(),16);
    }

    @Test
    public void testAbilityForOpponentExpandMultipleTimes(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,103,103);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        tile2.flip();
        game1.placeForOtherPlayer(tile2,104,100);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,103,102,null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT,103,102,terrainTypes.GRASSLANDS);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103,102,terrainTypes.LAKE);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][101].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][101].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][103].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][103].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][101].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][101].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][102].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][102].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][101].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][101].getPlayerID(),2);
        Assert.assertEquals(game1.playerTwo.getScore(),6);
        Assert.assertEquals(game1.playerTwo.getSettlerCount(),14);

    }

    @Test
    public void testAbilityForOpponentToPlaceTotoro(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,103,103);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        tile2.flip();
        game1.placeForOtherPlayer(tile2,104,100);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,103,102,null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT,103,102,terrainTypes.GRASSLANDS);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103,102,terrainTypes.LAKE);

        game1.buildForOtherPlayer(BuildType.PLACE_TOTORO,101,101,null);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][101].getTotoroCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][101].getPlayerID(),2);
        Assert.assertEquals(game1.playerTwo.getScore(),206);
        Assert.assertEquals(game1.playerTwo.getTotoroCount(),2);

        game1.buildForOtherPlayer(BuildType.PLACE_TOTORO,101,103,null);

        //should not place, already has totoro
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][103].getTotoroCount(),0);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][103].getPlayerID(),0);
        Assert.assertEquals(game1.playerTwo.getScore(),206);
        Assert.assertEquals(game1.playerTwo.getTotoroCount(),2);
    }

    @Test
    public void testAbilityForOpponentToPlaceTiger(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        game1.placeForOtherPlayer(tile1,103,103);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        tile2.flip();
        game1.placeForOtherPlayer(tile2,105,102);

        Tile tile3 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        game1.placeForOtherPlayer(tile3,106,103);

        Tile tile4 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        tile4.flip();
        game1.placeForOtherPlayer(tile4,103,102);

        Tile tile5 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        game1.placeForOtherPlayer(tile5,104,103);

        Tile tile6 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        tile6.flip();
        game1.placeForOtherPlayer(tile6,106,102);

        Tile tile7 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.ROCKY);
        tile7.flip();
        game1.placeForOtherPlayer(tile7,105,102);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][102].getLevel(),3);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][103].getLevel(),3);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT,107,102,null);
        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT,107,102,terrainTypes.GRASSLANDS);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[106][102].getSettlementID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardSettlementListTigerCount(1),0);

        game1.buildForOtherPlayer(BuildType.PLACE_TIGER,105,103,null);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[107][102].getSettlerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[107][102].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[106][102].getSettlerCount(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[106][102].getPlayerID(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][103].getTigerCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][103].getPlayerID(),2);
        Assert.assertEquals(game1.playerTwo.getScore(),80);
        Assert.assertEquals(game1.playerTwo.getTigerCount(),1);
        Assert.assertEquals(game1.playerTwo.getSettlerCount(),17);

    }
    
    @Test
    public void testAbilityForUsToPlaceFirstTileWithNoObstructions(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile1);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][104].getTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][104].getLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getLevel(),1);

    }

    @Test
    public void testAbilityForUsToPlaceFirstTileWithAnObstructions(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        tile1.flip();
        game1.placeForOtherPlayer(tile1,103,104);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][104].getTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][104].getLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getLevel(),1);

    }

    @Test
    public void testAbilityForUsToBuildSettlementWithNoObstruction(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile1);

        //build one settlement
        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),1);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),19);

        //we placed another tile
        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);
        //build again

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),2);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),18);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),3);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),17);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),4);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),16);
    }

    @Test
    public void testAbilityForUsToBuildSettlementWithAnObstruction(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        tile1.flip();
        game1.placeForOtherPlayer(tile1,103,104);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);
        game1.buildForOurPlayer();

        // Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),1);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),19);

        game1.buildForOurPlayer();

        // Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),2);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),18);
    }

    @Test
    public void testAbilityToPlaceTotoroAfterFiveSettlementsInARow(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile1);

        //build one settlement to set first row/col positions
        game1.buildForOurPlayer();

        //place more to test totoro building
        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);

        Tile tile3 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile3);

        Tile tile4 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile4);

        Tile tile5 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile5);

        Tile tile6 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile6);

        Tile tile7 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile7);



        //build again

        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();//should be totoro;
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[107][105].getTotoroCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[107][105].getPlayerID(),1);
        Assert.assertEquals(game1.playerOne.getScore(),205);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),15);
        Assert.assertEquals(game1.playerOne.getTotoroCount(),2);

        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();//should be totoro
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[114][105].getTotoroCount(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[114][105].getPlayerID(),1);
        Assert.assertEquals(game1.playerOne.getScore(),410);
        Assert.assertEquals(game1.playerOne.getSettlerCount(),10);
        Assert.assertEquals(game1.playerOne.getTotoroCount(),1);

    }
}