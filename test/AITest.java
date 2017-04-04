import org.junit.*;
/**
 * Created by William on 4/4/2017.
 */
public class AITest {

    @Test
    public void testAbilityToPlaceOpponentTile(){
        AI game1 = new AI();
        game1.gameBoard.placeFirstTileAndUpdateValidPlacementList();

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
    public void testAbilityToBuildAsOpponent(){
        AI game1 = new AI();
        game1.gameBoard.placeFirstTileAndUpdateValidPlacementList();

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
    public void testAbiltyForUsToPlaceFirstTileWithNoObstructions(){
        AI game1 = new AI();
        game1.gameBoard.placeFirstTileAndUpdateValidPlacementList();

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
        game1.gameBoard.placeFirstTileAndUpdateValidPlacementList();

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
        game1.gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile1);

        //build one settlement
        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[103][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),1);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),19);

        //pretend we placed another tile
        //build another tile

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[102][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),2);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),18);
    }

    @Test
    public void testAbilityForUsToBuildSettlementWithAnObstruction(){
        AI game1 = new AI();
        game1.gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tile1 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);
        tile1.flip();
        game1.placeForOtherPlayer(tile1,103,104);

        Tile tile2 = new Tile(game1.gameBoard.getGameBoardTileID(),game1.gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.ROCKY,terrainTypes.LAKE);

        game1.placeForOurPlayer(tile2);
        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[105][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),1);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),19);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getPlayerID(),1);
        Assert.assertEquals(game1.gameBoard.getGameBoardPositionArray()[104][105].getSettlerCount(),1);
        Assert.assertEquals(game1.playerOne.getScore(),2);
        Assert.assertEquals(game1.playerOne.getVillagerCount(),18);
    }
}
