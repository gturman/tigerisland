import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Christine Chierico on 4/1/2017.
 */
public class stepDefAllowBuildingSettlement {


    private Player playerOne = new Player(1);
    private GameBoard gameBoard = new GameBoard();



    @Given("^I am player$")
    public void i_AmPlayer() throws Throwable {

        Player player = new Player();

        int score = playerOne.getScore();

    }


    @And("^I am in the build phase of my turn$")
    public void iAmInTheBuildPhaseOfMyTurn() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.BUILD)
        {
            gameBoard.placeFirstTileAndUpdateValidPlacementList();
        }

    }

    @And("^I have chosen to settle$")
    public void iHaveChosenToSettle() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @And("^I still have villager$")
    public void iStillHaveVillagerS() throws Throwable {

        int villagerCount =  playerOne.getVillagerCount();

    }

    @Given("^the hex is habitable$")
    public void theHexIsHabitable() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

        gameBoard.isHabitable(101,101);

    }

    @And("^the hex is currently empty$")
    public void theHexIsCurrentlyEmpty() throws Throwable {

        Hex testHex1 = new Hex(0,0,terrainTypes.LAKE);
        testHex1.isNotBuiltOn();

        int settlerCount = gameBoard.getGameBoardPositionArray()[101][101].getSettlerCount();

    }

    @And("^the hex is level one$")
    public void theHexIsLevelOne() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

        gameBoard.isOnLevelOne(101,101);

    }

    @When("^I try to place a villager on a hex$")
    public void iTryToPlaceAVillagerOnAHex() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);

        gameBoard.buildSettlement(101,101,playerOne);


    }

    @Then("^I should see that my settlement was placed$")
    public void iShouldSeeThatMySettlementWasPlaced() throws Throwable {

        Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(101,101,placeTile);



    }

    @And("^my total villager count has decreased by one$")
    public void myTotalVillagerCountHasDecreasedByOne() throws Throwable {

        int villagerCount= playerOne.getVillagerCount();

    }

    @And("^my point total has increased by one$")
    public void myPointTotalHasIncreasedByOne() throws Throwable {

        int score = playerOne.getScore();

       // System.out.print("Score increased by: " + score + "\n");

    }

    //TODO: Check for two adjacent settlements merging
    @And("^if my villager is adjacent to one of my settlements, that villager merges into that settlement and its size increases by one, otherwise it becomes it’s own settlement$")
    public void ifMyVillagerIsAdjacentToOneOfMySettlementsThatVillagerMergesIntoThatSettlementAndItsSizeIncreasesByOtherwiseItBecomesItSOwnSettlement() throws Throwable {

        int score = playerOne.getScore();


        int settlerCount = gameBoard.getGameBoardPositionArray()[101][101].getSettlerCount();


    }
}
