/**
 * Created by christine on 3/24/2017.
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefTilePlacement {

    private Player player = new Player(1);
    private GameBoard gameBoard = new GameBoard();


    @Given("^I am a player$")
    public void iAmAPlayer() throws Throwable {
        Player player = new Player();
    }


    @And("^I am in the tile placement phase of my turn$")
    public void iAmInTheTilePlacementPhaseOfMyTurn() throws Throwable {

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @And("^I have drawn a tile$")
    public void iHaveJustDrawnATile() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

    }

    @And("^I am placing the tile on the board at a certain level$")
    public void iAmPlacingTheTileOnTheBoardAtACertainLevel() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @Given("^the board is empty$")
    public void theBoardIsEmpty() throws Throwable {

        boolean boardIsEmpty = gameBoard.getGameBoardTileID() == 1;
    }

    @Then("^my tile is placed on the center of the board$")
    public void myTileIsPlacedOnTheCenterOfTheBoard() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();
    }

    @Given("^one or more edges of my tile touches one or more of another tile’s edges$")
    public void oneOrMoreEdgeSOfMyTileTouchesOneOrMoreOfAnotherTileSEdgeS() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);
    }

    @Given("^my tile’s volcano is aligned with the bottom tile’s volcano$")
    public void myTileSVolcanoIsAlignedWithTheBottomTileSVolcano() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @And("^my tile does not completely overlap a single tile$")
    public void myTileDoesNotCompletelyOverlapASingleTile() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @And("^my tile does not completely overlap a settlement or Totoro$")
    public void myTileDoesNotCompletelyOverlapASettlementOrTotoro() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @And("^all of the tiles I am trying to cover are of the same level$")
    public void allOfTheTilesIAmTryingToCoverAreOfTheSameLevel() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);

        Tile tileToNuke = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        tileToNuke.flip();

        gameBoard.nukeTiles(102, 100, tileToNuke);
    }

    @When("^I place the tile$")
    public void iPlaceTheTile() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);
    }

    @Then("^I should see that my tile was placed$")
    public void iShouldSeeThatMyTileWasPlaced() throws Throwable {

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileToPlace = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);

        gameBoard.setTileAtPosition(102, 100, tileToPlace);
    }
}