import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Christine Chierico on 4/1/2017.
 */
public class stepDefDisallowBuildingSettlement {
    @Given("^I am the player$")
    public void i_AmThePlayer() throws Throwable {
       Player player = new Player();
    }

    @And("^Im in the build phase of my turn$")
    public void imInTheBuildPhaseOfMyTurn() throws Throwable {
        Player player = new Player();
        GameBoard gameboard = new GameBoard();

        if(player.getTurnPhase() == turnPhase.BUILD)
        {
            gameboard.placeFirstTileAndUpdateValidPlacementList();
        }
    }

    @And("^I chose to settle$")
    public void iChoseToSettle() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                                  terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.buildSettlement(103, 103, player);
        }
    }

    @Given("^the hex is an uninhabitable terrain hex$")
    public void theHexIsAnUninhabitableTerrainHex() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.isValidSettlementLocation(103, 103);
        }
    }

    @When("^I try to place my piece on a hex$")
    public void iTryToPlaceMyPieceOnAHex() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.buildSettlement(103, 103, player);
        }
    }

    @Given("^the hex already has a piece on it$")
    public void theHexAlreadyHasAPieceOnIt() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.buildSettlement(103, 102, player);
            gameboard.buildSettlement(103, 102, player);
        }
    }

    @Given("^the hex level is greater than one$")
    public void theHexLevelIsGreaterThanOne() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        Tile placeTileTwo = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.nukeTiles(102, 103, placeTileTwo);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.buildSettlement(103, 102, player);
        }
    }

    @Then("^my piece is prevented from being placed$")
    public void myPieceIsPreventedFromBeingPlaced() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.buildSettlement(103, 103, player);
        }
    }
}