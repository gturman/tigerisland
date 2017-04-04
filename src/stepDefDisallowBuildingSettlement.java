/**
 * Created by Christine Chierico on 4/1/2017.
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefDisallowBuildingSettlement {

    private Player playerOne = new Player(1);
    private GameBoard gameBoard = new GameBoard();

    @Given("^I am the player$")
    public void i_AmThePlayer() throws Throwable {

       Player player = new Player();

    }

    @And("^Im in the build phase of my turn$")
    public void imInTheBuildPhaseOfMyTurn() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.BUILD)
        {
            gameBoard.placeFirstTileAndUpdateValidPlacementList();
        }
    }

    @And("^I chose to settle$")
    public void iChoseToSettle() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                    terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        }
    }

    @Given("^the hex is an uninhabitable terrain hex$")
    public void theHexIsAnUninhabitableTerrainHex() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

       boolean habitable = gameBoard.isHabitable(101,101);
       //System.out.print("habitable: " + habitable + "\n");
    }

    @When("^I try to place my piece on a hex$")
    public void iTryToPlaceMyPieceOnAHex() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

        boolean valid = gameBoard.isValidSettlementLocation(101,101);

        gameBoard.buildSettlement(101,101,playerOne);

        int settlerCount = gameBoard.getGameBoardPositionArray()[101][101].getSettlerCount();

        //System.out.print("valid hex: " + valid + "\n");
       // System.out.print("settlement Count: " + settlerCount + "\n");
    }

    @Then("^my piece is prevented from being placed$")
    public void myPieceIsPreventedFromBeingPlaced() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

        int settlerCount = gameBoard.getGameBoardPositionArray()[101][101].getSettlerCount();
        //System.out.print("settlement Count: " + settlerCount + "\n");
    }

    @Given("^the hex already has a piece on it$")
    public void theHexAlreadyHasAPieceOnIt() throws Throwable {

        Hex testHex1 = new Hex(0,0,terrainTypes.LAKE);
        boolean builtOn = testHex1.isNotBuiltOn();
        //System.out.print("builtOn: " + builtOn + "\n");

    }


    @Given("^the hex level is greater than one$")
    public void theHexLevelIsGreaterThanOne() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

        boolean level = gameBoard.isOnLevelOne(101,101);
       // System.out.print("builtOn: " + level + "\n");

    }

}



