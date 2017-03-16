/**
 * Created by christine on 3/15/2017.
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sun.security.util.PendingException;

public class stepDefinition {

    @Given("^I am a player$")
    public void iAmAPlayer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        System.out.print("test1");
//        throw new cucumber.api.PendingException();
    }


    @And("^I am in the tile placement phase of my turn$")
    public void iAmInTheTilePlacementPhaseOfMyTurn() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print("test2");
        //   throw new cucumber.api.PendingException();
    }

    @And("^I have drawn a tile$")
    public void iHaveDrawnATile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print("test3");
        // throw new cucumber.api.PendingException();
    }

    @And("^I am placing a tile on the board at a certain level$")
    public void iAmPlacingATileOnTheBoardAtACertainLevel() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print("test4");
        // throw new cucumber.api.PendingException();
    }

    @Given("^the board is empty$")
    public void theBoardIsEmpty() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print("test5");
        // throw new cucumber.api.PendingException();
    }

    @When("^I try to place a tile$")
    public void iTryToPlaceATile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print("test6");
        // throw new cucumber.api.PendingException();
    }

    @Then("^my tile is placed on the center of the board$")
    public void myTileIsPlacedOnTheCenterOfTheBoard() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print("test7");
        // throw new cucumber.api.PendingException();
    }
}