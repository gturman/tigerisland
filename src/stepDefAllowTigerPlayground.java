import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by KJ on 4/6/2017.
 */
public class stepDefAllowTigerPlayground {
    @And("^I still have tigers$")
    public void iStillHaveTigers() throws Throwable {
        Player player = new Player(1);

        player.setTigerCount(2);
    }

    @And("^I choose to build a Tiger Playground$")
    public void iChooseToBuildATigerPlayground() throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);

        player.setTurnPhase(turnPhase.PLACE_TIGER);
    }

    @And("^the hex is of level (\\d+) or greater$")
    public void theHexIsOfLevelOrGreater(int arg0) throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
    }

    @And("^the settlement does not already have a Tiger Playground$")
    public void theSettlementDoesNotAlreadyHaveATigerPlayground() throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
    }

    @When("^I try to place my Tiger$")
    public void iTryToPlaceMyTiger() throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);
    }

    @Then("^I should see that my Tiger was placed$")
    public void iShouldSeeThatMyTigerWasPlaced() throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);
    }

    @And("^I should see my Tiger count decrease by (\\d+)$")
    public void iShouldSeeMyTigerCountDecreaseBy(int arg0) throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);

        int tigerCount = player.getTigerCount();
    }

    @And("^I should see my Tiger point count increase by (\\d+)$")
    public void iShouldSeeMyTigerPointCountIncreaseBy(int arg0) throws Throwable {
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);

        int playerScore = player.getScore();
    }

    @And("^Increase settlement size by (\\d+)$")
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
        gameBoard.buildSettlement(102, 104, player);
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);

        int settlementSize = gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID());
    }
}
