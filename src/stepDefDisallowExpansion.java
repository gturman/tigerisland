import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by christine on 4/4/2017.
 */
public class stepDefDisallowExpansion {

    @Given("^Im the player$")
    public void imThePlayer() throws Throwable {
        Player player = new Player(1);
    }

    @And("^I am build phase of my turn$")
    public void iAmBuildPhaseOfMyTurn() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        player.setTurnPhase(turnPhase.BUILD);
    }

    @When("^I choose to expand$")
    public void iChooseToExpand() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        player.setVillagerCount(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
    }

    @And("^I have chosen expand$")
    public void iHaveChosenExpand() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
    }

    @Given("^the hex I want to expand to is occupied$")
    public void theHexIWantToExpandToIsOccupied() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(104, 102, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(103, 101, player);
        gameboard.buildSettlement(104, 101, player);
        gameboard.buildSettlement(105, 101, player);
        gameboard.buildSettlement(104, 100, player);

        gameboard.mergeSettlements();

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(103, 102, terrainTypes.GRASSLANDS, player);
    }

    @When("^I try to pick that terrain to expand to$")
    public void iTryToPickThatTerrainToExpandTo() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player playerOne = new Player(1);
        Player playerTwo = new Player(2);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, playerOne);
        gameboard.buildSettlement(103, 102, playerOne);
        gameboard.buildSettlement(103, 101, playerTwo);
        gameboard.buildSettlement(104, 102, playerTwo);

        gameboard.mergeSettlements();

        playerOne.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
        gameboard.expandSettlement(103, 102, terrainTypes.GRASSLANDS, playerOne);
    }


    @Given("^the hex I want to expand to is on an uninhabitable terrain type$")
    public void theHexIWantToExpandToIsOnAnUninhabitableTerrainType() throws Throwable {
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

    @Given("^the hex I want to expand is not adjacent to any of my settlements$")
    public void theHexIWantToExpandIsNotAdjacentToAnyOfMySettlements() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(104, 102, terrainTypes.GRASSLANDS, player);
    }

    @When("^I try to pick that hex to expand to$")
    public void iTryToPickThatHexToExpandTo() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(104, 102, terrainTypes.GRASSLANDS, player);
    }

    @Given("^the chosen expansion requires more villagers than I currently have$")
    public void theChosenExpansionRequiresMoreVillagersThanICurrentlyHave() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        player.setVillagerCount(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.GRASSLANDS, player);
    }

    @Then("^my expansion is prevented$")
    public void myExpansionIsPrevented() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        player.setVillagerCount(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        gameboard.buildSettlement(102, 103, player);

        player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);

        gameboard.expandSettlement(102, 103, terrainTypes.GRASSLANDS, player);
    }
}
