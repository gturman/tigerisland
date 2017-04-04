/**
 * Created by Christine Chierico on 4/2/2017.
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDefAllowExpansion {

    private Player playerOne = new Player(1);
    private GameBoard gameBoard = new GameBoard();
    private int playerID = playerOne.getPlayerID();


    private Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
    private Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
    private Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);

    @Given("^I am an player$")
    public void i_AmAnPlayer() throws Throwable {

        Player player = new Player();
        int score = playerOne.getScore();

        int settlementsInit = playerOne.getSettlementCount();
        System.out.print("Initial settlement count: " + settlementsInit + "\n");


        firstTile.flip();
        gameBoard.setTileAtPosition(99,98,firstTile);

        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);
        gameBoard.buildSettlement(99,98,playerOne);

        int score1 = playerOne.getScore();
        //System.out.print("Initial Score: " + score1 + "\n");

        int settlements = playerOne.getSettlementCount();
        System.out.print("settlement count: " + settlements + "\n");
    }

    @And("^I am in build phase of my turn$")
    public void iAmInBuildPhaseOfMyTurn() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.BUILD)
        {
            gameBoard.placeFirstTileAndUpdateValidPlacementList();
        }
    }


    @And("^I still have villagers$")
    public void iStillHaveVillagers() throws Throwable {

        int villagerCount =  playerOne.getVillagerCount();
        //System.out.print("vilalgerCount: " + villagerCount + "\n");

    }

    @And("^I have chosen to expand$")
    public void iHaveChosenToExpand() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.EXPAND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @Given("^the hex belongs to a settlement I own$")
    public void theHexBelongsToASettlementIOwn() throws Throwable {

        boolean bool = gameBoard.isMySettlement(99,98,playerOne);
        //System.out.print("isMySettlement: " + bool + "\n");

        boolean bool2 = gameBoard.isMySettlement(99,100,playerOne);
        //System.out.print("isMySettlement: " + bool2 + "\n");

    }


    @And("^I have enough villagers to expand fully$")
    public void iHaveEnoughVillagersToExpandFully() throws Throwable {

        gameBoard.buildSettlement(99,98,playerOne);
        int villagersNeeded = gameBoard.calculateVillagersForExpansion(99,98,terrainTypes.GRASSLANDS);
        //System.out.print("Villagers Needed to Expand: " + villagersNeeded + "\n");
    }

    @When("^I try to expand to a hex$")
    public void iTryToExpandToAHex() throws Throwable {

        int one = gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount();
        int two = gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount();
        int three = gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount();

        gameBoard.buildSettlement(99,98,playerOne);
        gameBoard.expandSettlement(99,98,terrainTypes.GRASSLANDS,playerOne);
    }

    @Then("^I should see that each hex I can expand to has acquired the number of villagers equal to the hexes’ level$")
    public void iShouldSeeThatEachHexICanExpandToHasAcquiredTheNumberOfVillagersEqualToTheHexesLevel() throws Throwable {

        int one = gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount();
        int two = gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount();
        int three = gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount();

        //System.out.print("Villagers placed on hex1: " + one + "\n");
        //System.out.print("Villagers placed on hex1: " + two + "\n");
        //System.out.print("Villagers placed on hex1: " + three + "\n");
    }

    @And("^for each villager placed due to the expansion, I should see my villager count decrease by one$")
    public void forEachVillagerPlacedDueToTheExpansionIShouldSeeMyVillagerCountDecreaseByOne() throws Throwable {

        int villagerCount =  playerOne.getVillagerCount();
        //System.out.print("villagerCount: " + villagerCount + "\n");

    }

    @And("^for each villagers placed due to the expansion, I should see my score increase by the total villagers occupying the hex multiplied by the hexes’ level$")
    public void forEachVillagersPlacedDueToTheExpansionIShouldSeeMyScoreIncreaseByTheTotalVillagersOccupyingTheHexMultipliedByTheHexesLevel() throws Throwable {

         int score = playerOne.getScore();
        //System.out.print("score: " + score + "\n");

    }

    @And("^for each hex expanded to, increase the settlement size by one and merge those tiles into the original settlement expanded from$")
    public void forEachHexExpandedToIncreaseTheSettlementSizeByOneAndMergeThoseTilesIntoTheOriginalSettlementExpandedFrom() throws Throwable {

        int score = playerOne.getScore();
        //System.out.print("score: " + score + "\n");

    }
}
