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

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][100].getHexTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][99].getHexTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][99].getHexTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][100].getHexLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][99].getHexLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][99].getHexLevel(),1);
    }

    @Test
    public void testAbilityForOpponentToNuke(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        game1.placeForOtherPlayer(tile1,102,100);

        Tile tile2 = tile1;
        tile2.flip();
        game1.placeForOtherPlayer(tile2,102,100);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][100].getHexTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][101].getHexTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][101].getHexTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][100].getHexLevel(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][101].getHexLevel(),2);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[101][101].getHexLevel(),2);

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
        Assert.assertEquals(game1.playerTwo.getVillagerCount(),19);
    }

    @Test
    public void testAbilityForOpponentToExpand(){

    }

    @Test
    public void testAbiltyForUsToPlaceFirstTileWithNoObstructions(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile1);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][104].getHexTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getHexTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getHexTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][104].getHexLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getHexLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getHexLevel(),1);

    }

    @Test
    public void testAbiltyForUsToPlaceFirstTileWithAnObstructions(){
        AI game1 = new AI();
        game1.placeFirstTile();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        tile1.flip();
        game1.placeForOtherPlayer(tile1,103,104);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][104].getHexTerrainType(),terrainTypes.VOLCANO);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getHexTerrainType(),terrainTypes.LAKE);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getHexTerrainType(),terrainTypes.ROCKY);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][104].getHexLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getHexLevel(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getHexLevel(),1);

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
        Assert.assertEquals(game1.playerOne.getVillagerCount(),19);

        //we placed another tile
        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);
        //build again

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),2);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),18);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),3);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),17);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),4);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),16);
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
        Assert.assertEquals(game1.playerOne.getVillagerCount(),19);

        game1.buildForOurPlayer();

       // Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),2);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),18);
    }

    @Test
    public void testAbiltyToPlaceTotoroAfterFiveSettlementsInARow(){
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
        Assert.assertEquals(game1.playerOne.getVillagerCount(),15);
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
        Assert.assertEquals(game1.playerOne.getVillagerCount(),10);
        Assert.assertEquals(game1.playerOne.getTotoroCount(),1);

    }
}
