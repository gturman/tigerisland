import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by KJ on 4/6/2017.
 */
public class stepDefInvalidTotoroPlacement {

    @Given("^I choose a hex that is uninhabitable$")
    public void iChooseAHexThatIsUninhabitable() throws Throwable {
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

        gameBoard.placeTotoroSanctuary(102, 102, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @Given("^I choose a hex that is occupied$")
    public void iChooseAHexThatIsOccupied() throws Throwable {
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

        gameBoard.placeTotoroSanctuary(102, 101, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @Given("^I choose a hex not adjacent to one of my settlements$")
    public void iChooseAHexNotAdjacentToOneOfMySettlements() throws Throwable {
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
        gameBoard.buildSettlement(102, 101, player);

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(102, 105, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @Given("^I choose a hex adjacent to a settlement of size (\\d+) or less$")
    public void iChooseAHexAdjacentToASettlementOfSizeOrLess(int arg0) throws Throwable {
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

        gameBoard.mergeSettlements();

        gameBoard.placeTotoroSanctuary(104, 102, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @Given("^the settlement already has a Totoro$")
    public void theSettlementAlreadyHasATotoro() throws Throwable {
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
        gameBoard.placeTotoroSanctuary(104, 102, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }

    @Then("^my Totoro is prevented from being placed$")
    public void myTotoroIsPreventedFromBeingPlaced() throws Throwable {
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
        gameBoard.placeTotoroSanctuary(104, 102, gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), player);
    }
}
