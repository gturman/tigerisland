import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Christine Chierico on 4/2/2017.
 */
public class stepDefAllowExpansion {

    @Given("^I am an player$")
    public void i_AmAnPlayer() throws Throwable {
        Player player = new Player(1);
    }

    @And("^I am in build phase of my turn$")
    public void iAmInBuildPhaseOfMyTurn() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.BUILD)
        {
            gameboard.placeFirstTileAndUpdateValidPlacementList();
        }
    }

    @And("^I still have some villagers$")
    public void iStillHaveSomeVillagers() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        player.setSettlerCount(20);

        int villagerCount = player.getSettlerCount();
    }

    @And("^I have chosen to expand$")
    public void iHaveChosenToExpand() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
    }

    @Given("^the hex is a habitable terrain$")
    public void theHexIsAHabitableTerrain() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.GRASSLANDS, player);
    }

    @And("^the hex is currently unoccupied$")
    public void theHexIsCurrentlyUnoccupied() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @And("^the hex is adjacent to my settlement$")
    public void theHexIsAdjacentToMySettlement() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @And("^I have enough villagers to expand fully$")
    public void iHaveEnoughVillagersToExpandFully() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @When("^I try to expand to a hex$")
    public void iTryToExpandToAHex() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @Then("^I should see that each hex I can expand to has acquired the number of villagers equal to the hexes’ level$")
    public void iShouldSeeThatEachHexICanExpandToHasAcquiredTheNumberOfVillagersEqualToTheHexesLevel() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @And("^for every empty hex adjacent to the settlement of the specified terrain, add as many villagers as the hex's level$")
    public void forEveryEmptyHexAdjacentToTheSettlementOfTheSpecifiedTerrainAddAsManyVillagersAsTheHexSLevel() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @And("^for each villager placed due to the expansion, I should see my villager count decrease by one$")
    public void forEachVillagerPlacedDueToTheExpansionIShouldSeeMyVillagerCountDecreaseByOne() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @And("^for each villagers placed due to the expansion, I should see my score increase by the total villagers occupying the hex multiplied by the hexes’ level$")
    public void forEachVillagersPlacedDueToTheExpansionIShouldSeeMyScoreIncreaseByTheTotalVillagersOccupyingTheHexMultipliedByTheHexesLevel() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
    }

    @And("^for each hex expanded to, increase the settlement size by one and merge those tiles into the original settlement expanded from$")
    public void forEachHexExpandedToIncreaseTheSettlementSizeByOneAndMergeThoseTilesIntoTheOriginalSettlementExpandedFrom() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(104, 100, player);
        gameboard.mergeSettlements();

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.VOLCANO, player);
        gameboard.mergeSettlements();
    }
}
