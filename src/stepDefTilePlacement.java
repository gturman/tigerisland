/**
 * Created by christine on 3/17/2017.
 */

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefTilePlacement {

    @Given("^I am the player$")
    public void i_AmthePlayer() throws Throwable {
        Player player = new Player();
    }


    @And("^I am in the tile placement phase of my turn$")
    public void iAmInTheTilePlacementPhaseOfMyTurn() throws Throwable {
        Player player = new Player();
        GameBoard gameboard = new GameBoard();

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                                      terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @And("^I have drawn a tile$")
    public void iHaveDrawnATile() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                                  terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @And("^I am placing a tile on the board at a certain level$")
    public void iAmPlacingATileOnTheBoardAtACertainLevel() throws Throwable {
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

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT && gameboard.getGameboardTileID() == 0)
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



    @Then("^my tile is placed correctly$")
    public void myTileIsPlacedCorrectly(){
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.JUNGLE, terrainTypes.VOLCANO);
        secondTile.flip();

        gameboard.setTileAtPosition(102,102,placeTile);
        gameboard.setTileAtPosition(103,103,secondTile);
    }

}