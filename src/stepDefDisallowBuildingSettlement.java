import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Christine Chierico on 4/1/2017.
 */
public class stepDefDisallowBuildingSettlement {

    private Player playerOne = new Player(1);
    private GameBoard gameboard = new GameBoard();



    @Given("^I am the player$")
    public void i_AmThePlayer() throws Throwable {

       Player player = new Player();

    }

    @And("^Im in the build phase of my turn$")
    public void imInTheBuildPhaseOfMyTurn() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.BUILD)
        {
            gameboard.placeFirstTileAndUpdateValidPlacementList();
        }

    }

    @And("^I chose to settle$")
    public void iChoseToSettle() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        }

    }

    @Given("^the hex is an uninhabitable terrain hex$")
    public void theHexIsAnUninhabitableTerrainHex() throws Throwable {

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameboard.setTileAtPosition(101,101,placeTile);

       boolean habitable = gameboard.isHabitable(101,101);
       //System.out.print("habitable: " + habitable + "\n");

    }

    @When("^I try to place my piece on a hex$")
    public void iTryToPlaceMyPieceOnAHex() throws Throwable {

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameboard.setTileAtPosition(101,101,placeTile);

        boolean valid = gameboard.isValidSettlementLocation(101,101);

        gameboard.buildSettlement(101,101,playerOne);

        int settlerCount = gameboard.getGameBoardPositionArray()[101][101].getSettlerCount();

        //System.out.print("valid hex: " + valid + "\n");
       // System.out.print("settlement Count: " + settlerCount + "\n");

    }

    @Then("^my piece is prevented from being placed$")
    public void myPieceIsPreventedFromBeingPlaced() throws Throwable {

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameboard.setTileAtPosition(101,101,placeTile);

        int settlerCount = gameboard.getGameBoardPositionArray()[101][101].getSettlerCount();
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

        Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        gameboard.setTileAtPosition(101,101,placeTile);

       boolean level = gameboard.isOnLevelOne(101,101);
      // System.out.print("builtOn: " + level + "\n");

    }


}



