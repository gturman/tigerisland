/**
 * Created by christine on 3/17/2017.
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefInvalidTilePlacement {

    @Given("^I am a player about to do an invalid placement$")
    public void i_AmAPlayerAboutToDoAnInvalidPlacement() throws Throwable {
        Player player = new Player();
    }

    @And("^I am in the tile placement phase of my turn for an invalid placement$")
    public void iAmInTheTilePlacementPhaseOfMyTurnForAnInvalidPlacement() throws Throwable {
        Player player = new Player();
        GameBoard gameBoard = new GameBoard();

        if(player.getTurnPhase() == turnPhase.TILE_PLACEMENT)
        {
            Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @And("^I have drawn a tile for an invalid placement$")
    public void iHaveDrawnATileForAnInvalidPlacement() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @And("^I am placing a tile on the board at a certain level$")
    public void iAmPlacingATileOnTheBoardAtACertainLevel() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @Given("^no edges of my tile touches another hexes’ edge$")
    public void noEdgesOfMyTileTouchesAnotherHexesEdge() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(55, 32, tileToPlace);
    }

    @When("^I try to place a tile$")
    public void iTryToPlaceATile() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(55, 32, tileToPlace);
    }

    @Then("^my tile is prevented from being placed$")
    public void myTileIsPreventedFromBeingPlaced() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(55, 32, tileToPlace);
    }

    @Given("^my tile entirely overlaps another tile one level below it$")
    public void myTileEntirelyOverlapsAnotherTileOneLevelBelowIt() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @Given("^my tile overlaps a size one settlement$")
    public void myTileOverlapsASizeOneSettlement() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        gameBoard.buildSettlement(102, 100, player);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @Given("^one or more hex is not of the same level$")
    public void oneOrMoreHexIsNotOfTheSameLevel() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);

        Tile nextToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.nukeTiles(102, 102, nextToNuke);
    }

    @Given("^at least once hex below my tile has a Totoro$")
    public void atLeastOnceHexBelowMyTileHasATotoro() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        gameBoard.getGameBoardPositionArray()[101][101].setTotoroCount(1);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @Given("^at least one hex below m tile has a Tiger Playground$")
    public void atLeastOneHexBelowMTileHasATigerPlayground() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        gameBoard.getGameBoardPositionArray()[101][101].setTigerCount(1);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @Given("^my tile’s volcano was not placed over a volcano$")
    public void myTileSVolcanoWasNotPlacedOverAVolcano() throws Throwable {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();
        tileToNuke.tileRotationClockwise(1);

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }
}