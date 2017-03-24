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
    public void i_AmThePlayer() throws Throwable {
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

    @When("^I try to place the tile$")
    public void iTryToPlaceTheTile() throws Throwable {
        GameBoard gameboard = new GameBoard();

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102, 102, placeTile);
    }

    @Then("^my tile is prevented from being placed$")
    public void myTileIsPreventedFromBeingPlaced() throws Throwable {
        Player player = new Player();
        turnPhase currentTurn = turnPhase.FOUND_SETTLEMENT;
        player.setTurnPhase(currentTurn);
    }

}