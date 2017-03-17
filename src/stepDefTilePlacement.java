/**
 * Created by christine on 3/17/2017.
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import sun.security.util.PendingException;

public class stepDefTilePlacement {

    @Given("^I am the player$")
    public void i_AmthePlayer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Player P1 = new Player();
        int P1ID = P1.getPlayerID();
        //Assert.assertEquals(P1.getPlayerID(),1);
        System.out.print("player: " + P1ID + "\n");
//        throw new cucumber.api.PendingException();
    }


    @And("^I am in the tile placement phase of my turn$")
    public void iAmInTheTilePlacementPhaseOfMyTurn() throws Throwable {
        // Write code here that turns the phrase above into concrete actions


        System.out.print("test2" + "\n");
        //   throw new cucumber.api.PendingException();
    }

    @And("^I have drawn a tile$")
    public void iHaveDrawnATile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.print("test3" + "\n");
        // throw new cucumber.api.PendingException();
    }

    @And("^I am placing a tile on the board at a certain level$")
    public void iAmPlacingATileOnTheBoardAtACertainLevel() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.print("test4" + "\n");
        // throw new cucumber.api.PendingException();
    }

    @Given("^the board is empty$")
    public void theBoardIsEmpty() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.print("test5" + "\n");
        // throw new cucumber.api.PendingException();
    }

    @When("^I try to place a tile$")
    public void iTryToPlaceATile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.print("test6" + "\n");
        // throw new cucumber.api.PendingException();
    }

    @Then("^my tile is placed on the center of the board$")
    public void myTileIsPlacedOnTheCenterOfTheBoard() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.print("test7" + "\n");
        // throw new cucumber.api.PendingException();
    }


}