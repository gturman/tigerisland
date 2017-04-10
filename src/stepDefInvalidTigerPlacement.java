import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by KJ on 4/6/2017.
 */
public class stepDefInvalidTigerPlacement {

    @Given("^I choose a hex that is level (\\d+) or lower$")
    public void iChooseAHexThatIsLevelOrLower(int arg0) throws Throwable {
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

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(2);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(1);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);
    }

    @Given("^the settlement already has a Tiger Playground$")
    public void theSettlementAlreadyHasATigerPlayground() throws Throwable {
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

        gameBoard.getGameBoardPositionArray()[104][102].setLevel(3);
        gameBoard.placeTigerPen(104, 102, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);
    }

    @Then("^my Tiger is prevented from being placed$")
    public void myTigerIsPreventedFromBeingPlaced() throws Throwable {
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

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(2);
        gameBoard.placeTigerPen(101, 105, gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), player);
    }
}
