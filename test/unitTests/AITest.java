import enums.BuildType;
import enums.terrainTypes;
import gameRules.Tile;
import org.junit.*;
import tournament.AI;

/**
 * Created by William on 4/4/2017.
 */
public class AITest {

    @Test
    public void testAbilityToPlaceOpponentTile() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE, 103, 100, false);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][100].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][99].getTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][99].getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][100].getLevel(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][99].getLevel(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][99].getLevel(), 1);
    }

    @Test
    public void testAbilityForOpponentToPlace() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE, 102, 100, false);
        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE, 102, 100, true);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][100].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][101].getTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[101][101].getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][100].getLevel(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][101].getLevel(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[101][101].getLevel(), 2);
    }

    @Test
    public void testAbilityToBuildSettlementAsOpponent() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE, 103, 100, false);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 102, 99, null);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][99].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][99].getPlayerID(), 2);
        Assert.assertEquals(game1.getPlayerTwoScore(), 1);
        Assert.assertEquals(game1.getPlayerTwoVillagerCount(), 19);
    }

    @Test
    public void testAbilityForOpponentToExpandOffSizeOneSettlement() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE, 103, 103, false);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 104, 100, true);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 103, 102, null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103, 102, terrainTypes.GRASSLANDS);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][101].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][101].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][103].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][103].getPlayerID(), 2);
        Assert.assertEquals(game1.getPlayerTwoScore(), 4);
        Assert.assertEquals(game1.getPlayerTwoVillagerCount(), 16);

    }

    @Test
    public void testAbilityForOpponentToExpandOffSizeOnePlusSettlement() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE, 103, 103, false);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 104, 100, true);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 103, 102, null);
        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 103, 101, null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103, 102, terrainTypes.GRASSLANDS);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][101].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][103].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][103].getPlayerID(), 2);
        Assert.assertEquals(game1.getPlayerTwoScore(), 4);
        Assert.assertEquals(game1.getPlayerTwoVillagerCount(), 16);
    }

    @Test
    public void testAbilityForOpponentExpandMultipleTimes() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE, 103, 103, false);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 104, 100, true);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 103, 102, null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103, 102, terrainTypes.GRASSLANDS);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103, 102, terrainTypes.LAKE);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][101].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][103].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][103].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][101].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][102].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][102].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][101].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getPlayerTwoScore(), 6);
        Assert.assertEquals(game1.getPlayerTwoVillagerCount(), 14);

    }

    @Test
    public void testAbilityForOpponentToPlaceTotoro() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE, 103, 103, false);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 104, 100, true);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 103, 102, null);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103, 102, terrainTypes.GRASSLANDS);

        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 103, 102, terrainTypes.LAKE);

        game1.buildForOtherPlayer(BuildType.PLACE_TOTORO, 101, 101, null);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[101][101].getTotoroCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[101][101].getPlayerID(), 2);
        Assert.assertEquals(game1.getPlayerTwoScore(), 206);
        Assert.assertEquals(game1.getPlayerTwoTotoroCount(), 2);

        game1.buildForOtherPlayer(BuildType.PLACE_TOTORO, 101, 103, null);

        //should not place, already has totoro
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[101][103].getTotoroCount(), 0);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[101][103].getPlayerID(), 0);
        Assert.assertEquals(game1.getPlayerTwoScore(), 206);
        Assert.assertEquals(game1.getPlayerTwoTotoroCount(), 2);
    }

    @Test
    public void testAbilityForOpponentToPlaceTiger() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 103, 103, false);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 105, 102, true);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, 106, 103, false);

        game1.placeForOtherPlayer(terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, 103, 102, true);

        game1.placeForOtherPlayer(terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, 104, 103, false);

        game1.placeForOtherPlayer(terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, 106, 102, true);

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.ROCKY, 105, 102, true);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][102].getLevel(), 3);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][103].getLevel(), 3);

        game1.buildForOtherPlayer(BuildType.FOUND_SETTLEMENT, 107, 102, null);
        game1.buildForOtherPlayer(BuildType.EXPAND_SETTLMENT, 107, 102, terrainTypes.GRASSLANDS);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[106][102].getSettlementID(), 1);
        Assert.assertEquals(game1.getAIGameboardSettlementListTigerCount(1), 0);

        game1.buildForOtherPlayer(BuildType.PLACE_TIGER, 105, 103, null);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[107][102].getSettlerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[107][102].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[106][102].getSettlerCount(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[106][102].getPlayerID(), 2);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][103].getTigerCount(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][103].getPlayerID(), 2);
        Assert.assertEquals(game1.getPlayerTwoScore(), 80);
        Assert.assertEquals(game1.getPlayerTwoTigerCount(), 1);
        Assert.assertEquals(game1.getPlayerTwoVillagerCount(), 17);

    }

    @Test
    public void testAbilityForUsToPlaceFirstTileWithNoObstructions() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][104].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][105].getTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][105].getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][104].getLevel(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][105].getLevel(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][105].getLevel(), 1);

    }

    @Test
    public void testAbilityForUsToPlaceFirstTileWithAnObstructions() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE, 103, 104, true);

        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][104].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][105].getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][105].getTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][104].getLevel(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][105].getLevel(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][105].getLevel(), 1);
    }

    @Test
    public void testAbilityForUsToBuildSettlementWithNoObstruction() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);

        //build one settlement
        game1.buildForOurPlayer();

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][105].getPlayerID(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[102][105].getSettlerCount(), 1);
        Assert.assertEquals(game1.getPlayerOneScore(), 1);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(), 19);

        //we placed another tile
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);

        //build again
        game1.buildForOurPlayer();

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][105].getPlayerID(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[103][105].getSettlerCount(), 1);
        Assert.assertEquals(game1.getPlayerOneScore(), 2);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(), 18);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][105].getPlayerID(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][105].getSettlerCount(), 1);
        Assert.assertEquals(game1.getPlayerOneScore(), 3);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(), 17);

        game1.buildForOurPlayer();

        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][105].getPlayerID(), 1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][105].getSettlerCount(), 1);
        Assert.assertEquals(game1.getPlayerOneScore(), 4);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(), 16);
    }

    @Test
    public void testAbilityForUsToBuildSettlementWithAnObstruction() {
        AI game1 = new AI();
        game1.placeFirstTile();

        game1.placeForOtherPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE, 103, 104, true);

        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);
        game1.buildForOurPlayer();

        // Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][105].getPlayerID(),1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[104][105].getSettlerCount(), 1);
        Assert.assertEquals(game1.getPlayerOneScore(), 1);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(), 19);

        game1.buildForOurPlayer();

        // Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][105].getPlayerID(),1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[105][105].getSettlerCount(), 1);
        Assert.assertEquals(game1.getPlayerOneScore(), 2);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(), 18);
    }

  /*  @Test
    public void testAbilityToPlaceTotoroAfterFiveSettlementsInARow(){
        tournament.AI game1 = new tournament.AI();
        game1.placeFirstTile();

        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);

        //build one settlement to set first row/col positions
        game1.buildForOurPlayer();

        //place more to test totoro building
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);
        game1.placeForOurPlayer(terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.LAKE);

        //build again

        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();//should be totoro;
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[107][105].getTotoroCount(),1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[107][105].getPlayerID(),1);
        Assert.assertEquals(game1.getPlayerOneScore(),205);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(),15);
        Assert.assertEquals(game1.getPlayerOneTotoroCount(),2);

        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();
        game1.buildForOurPlayer();//should be totoro
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[114][105].getTotoroCount(),1);
        Assert.assertEquals(game1.getCurrentAIGameBoardPositionArray()[114][105].getPlayerID(),1);
        Assert.assertEquals(game1.getPlayerOneScore(),410);
        Assert.assertEquals(game1.getPlayerOneVillagerCount(),10);
        Assert.assertEquals(game1.getPlayerOneTotoroCount(),1);
    }*/
}