/**
 * Created by christine on 3/17/2017.
 */

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
        int tileID = 0;
        int hexID = 0;

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(tileID, hexID, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @And("^I have drawn a tile$")
    public void iHaveDrawnATile() throws Throwable {
        int tileID = 0;
        int hexID = 0;

        Tile placeTile = new Tile(tileID, hexID, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @And("^I am placing a tile on the board at a certain level$")
    public void iAmPlacingATileOnTheBoardAtACertainLevel() throws Throwable {
        int tileID = 0;
        int hexID = 0;

        Tile placeTile = new Tile(tileID, hexID, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @Given("^the board is empty$")
    public void theBoardIsEmpty() throws Throwable {
        int tileID = 0;
    }

    @When("^I try to place a tile$")
    public void iTryToPlaceATile() throws Throwable {
        int tileID = 0;
        int hexID = 0;

        Tile placeTile = new Tile(tileID, hexID, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
    }

    @Then("^my tile is placed on the center of the board$")
    public void myTileIsPlacedOnTheCenterOfTheBoard() throws Throwable {
        Player player = new Player();
        int tileID = 0;
        int hexID = 0;

        turnPhase nextTurnDesired = turnPhase.FOUND_SETTLEMENT;

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT && tileID == 0)
        {
            Tile placeTile = new Tile(tileID, hexID, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
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
}