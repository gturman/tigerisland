import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by KJ on 4/6/2017.
 */
public class stepDefAllowPlacingOfTotoroSanctuary {
    @And("^I still have Totoros$")
    public void iStillHaveTotoroS() throws Throwable {
        Player player = new Player(1);

        player.setTotoroCount(3);
    }

    @And("^I choose to place a Totoro$")
    public void iChooseToPlaceATotoro() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        player.setTurnPhase(turnPhase.PLACE_TOTORO);
    }

    @Given("^the hex is inhabitable$")
    public void theHexIsInhabitable() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @And("^the hex is unoccupied$")
    public void theHexIsUnoccupied() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @And("^the hex is adjacent to one of my settlements$")
    public void theHexIsAdjacentToOneOfMySettlements() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @And("^the settlement is of size (\\d+) or greater$")
    public void theSettlementIsOfSizeOrGreater(int arg0) throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @And("^the settlement does not already have a Totoro$")
    public void theSettlementDoesNotAlreadyHaveATotoro() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @When("^I try to place my Totoro$")
    public void iTryToPlaceMyTotoro() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @Then("^I should see that my Totoro was placed$")
    public void iShouldSeeThatMyTotoroWasPlaced() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);

        gameBoard.getGameBoardSettlementListTotoroCount(gameBoard.getGameBoardPositionArray()[102][102].getSettlementID());
    }

    @And("^I should see my Totoro count decrease by (\\d+)$")
    public void iShouldSeeMyTotoroCountDecreaseBy(int arg0) throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);

        int totoroCount = player.getTotoroCount();
    }

    @And("^I should see my point count increase by (\\d+)$")
    public void iShouldSeeMyPointCountIncreaseBy(int arg0) throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);

        int score = player.getScore();
    }

    @And("^increase settlement size by (\\d+)$")
    public void increaseSettlementSizeBy(int arg0) throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, player);
        gameBoard.buildSettlement(102, 103, player);
        gameBoard.buildSettlement(103, 102, player);
        gameBoard.buildSettlement(103, 103, player);
        gameBoard.buildSettlement(102, 105, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(101, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);

        gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID());
    }
}
