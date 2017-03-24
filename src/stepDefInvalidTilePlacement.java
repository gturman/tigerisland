/**
 * Created by christine on 3/24/2017.
 */


import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefInvalidTilePlacement {

    @Given("^I am the current player$")
    public void i_AmTheCurrentPlayer() throws Throwable {
        Player player = new Player();
    }


    @And("^I am in the tile placements phase of my turn$")
    public void iAmInTheTilePlacementsPhaseOfMyTurn() throws Throwable {
        Player player = new Player();
        GameBoard gameboard = new GameBoard();

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @And("^I have just drawn a tile$")
    public void iHaveJustDrawnATile() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @And("^I am placing the tile on the board at a certain level$")
    public void iAmPlacingTheTileOnTheBoardAtACertainLevel() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }


    @Given("^the board is empty$")
    public void theBoardIsEmpty() throws Throwable {
        GameBoard gameboard = new GameBoard();

        boolean testIfEmpty = (gameboard.getGameboardTileID() == 1);
    }

    @When("^I try to place a tile$")
    public void iTryToPlaceATile() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102, 102, placeTile);
    }

    @Then("^my tile is placed on the center of the board$")
    public void myTileIsPlacedOnTheCenterOfTheBoard() throws Throwable {
        Player player = new Player();
        GameBoard gameboard = new GameBoard();
        turnPhase nextTurnDesired = turnPhase.FOUND_SETTLEMENT;

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT && gameboard.getGameboardTileID() == 1)
        {
            Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(102, 102, placeTile);
            if(nextTurnDesired == turnPhase.FOUND_SETTLEMENT)
            {
                player.setTurnPhase(turnPhase.FOUND_SETTLEMENT);
            }
            else if(nextTurnDesired == turnPhase.EXPAND_SETTLEMENT)
            {
                player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
            }
            else if(nextTurnDesired == turnPhase.PLACE_TOTORO)
            {
                player.setTurnPhase(turnPhase.PLACE_TOTORO);
            }
        }
    }

    @Given("^one or more edge of my tiles touches one or more of another tile's edge$")
    public void oneOrMoreEdgeSOfMyTilesTouchesOneOrMoreOfAnotherTileSEdgeS() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.JUNGLE, terrainTypes.VOLCANO);

        gameboard.setTileAtPosition(102,102,placeTile);
        gameboard.checkIfTileCanBePlacedAtPosition(103, 103, secondTile);
    }

    @Given("^no edges of my tile touches another hexes’ edge$")
    public void noEdgesOfMyTileTouchesAnotherHexesEdge() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.checkIfTileBeingPlacedWillBeAdjacent(105, 105, secondTile);
    }

    @Given("^one or more hex is not of the same level$")
    public void oneOrMoreHexIsNotOfTheSameLevel() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        gameboard.getGameBoardPositionArray()[103][102].setHexLevel(2);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.nukeTiles(102, 101, fourthTile);
        gameboard.nukeTiles(103, 102, fifthTile);
    }

    @Given("^my tile entirely overlaps another tile one level below it$")
    public void myTileEntirelyOverlapsAnotherTileOneLevelBelowIt() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.nukeTiles(102, 102, secondTile);
    }

    @Given("^my tile’s volcano was not placed over a volcano$")
    public void myTileSVolcanoWasNotPlacedOverAVolcano() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.ROCKY, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.JUNGLE, terrainTypes.JUNGLE, terrainTypes.VOLCANO);

        gameboard.nukeTiles(102, 101, fourthTile);
        gameboard.nukeTiles(103, 102, fifthTile);

    }

    @When("^I try to place my tile$")
    public void iTryToPlaceMyTile() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.nukeTiles(102, 102, secondTile);
    }

    @Then("^my tile is placed correctly$")
    public void myTileIsPlacedCorrectly(){
        Player player = new Player();
        turnPhase currentTurn = turnPhase.FOUND_SETTLEMENT;

        if(currentTurn == turnPhase.FOUND_SETTLEMENT)
        {
            player.setTurnPhase(turnPhase.FOUND_SETTLEMENT);
        }
        else if(currentTurn == turnPhase.EXPAND_SETTLEMENT)
        {
            player.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
        }
        else if(currentTurn == turnPhase.PLACE_TOTORO)
        {
            player.setTurnPhase(turnPhase.PLACE_TOTORO);
        }
    }
}