import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Christine Chierico on 4/2/2017.
 */
public class stepDefAllowExpansion {


    private Player playerOne = new Player(1);
    private GameBoard gameboard = new GameBoard();

    private Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
    private Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
    private Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);

    @Given("^I am an player$")
    public void i_AmAnPlayer() throws Throwable {

        Player player = new Player();
        int score = playerOne.getScore();

        firstTile.flip();
        gameboard.setTileAtPosition(99,98,firstTile);

        secondTile.flip();
        gameboard.setTileAtPosition(99,100,secondTile);
        gameboard.buildSettlement(99,98,playerOne);

        int score1 = playerOne.getScore();


    }


    @And("^I am in build phase of my turn$")
    public void iAmInBuildPhaseOfMyTurn() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.BUILD)
        {
            gameboard.placeFirstTileAndUpdateValidPlacementList();
        }

    }


    @And("^I still have villagers$")
    public void iStillHaveVillagers() throws Throwable {

        int villagerCount =  playerOne.getVillagerCount();


    }

    @And("^I have chosen to expand$")
    public void iHaveChosenToExpand() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.EXPAND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }

    }

    @Given("^the hex is a habitable terrain$")
    public void theHexIsAHabitableTerrain() throws Throwable {


        gameboard.isHabitable(99,98);


    }

    @And("^the hex is currently unoccupied$")
    public void theHexIsCurrentlyUnoccupied() throws Throwable {

        Hex testHex1 = new Hex(0,0,terrainTypes.GRASSLANDS);
        testHex1.isNotBuiltOn();

        int settlerCount = gameboard.getGameBoardPositionArray()[99][98].getSettlerCount();


    }

    @And("^the hex is adjacent to my settlement$")
    public void theHexIsAdjacentToMySettlement() throws Throwable {

        GameBoard game = new GameBoard();

    }

    @And("^I have enough villagers to expand fully$")
    public void iHaveEnoughVillagersToExpandFully() throws Throwable {

        gameboard.buildSettlement(99,98,playerOne);
        int villagersNeeded = gameboard.calculateVillagersForExpansion(99,98,terrainTypes.GRASSLANDS);


    }

    @When("^I try to expand to a hex$")
    public void iTryToExpandToAHex() throws Throwable {

        int one = gameboard.getGameBoardPositionArray()[99][99].getSettlerCount();
        int two = gameboard.getGameBoardPositionArray()[99][100].getSettlerCount();
        int three = gameboard.getGameBoardPositionArray()[98][101].getSettlerCount();


        gameboard.buildSettlement(99,98,playerOne);
        gameboard.expandSettlement(99,98,terrainTypes.GRASSLANDS,playerOne);
    }

    @Then("^I should see that each hex I can expand to has acquired the number of villagers equal to the hexes’ level$")
    public void iShouldSeeThatEachHexICanExpandToHasAcquiredTheNumberOfVillagersEqualToTheHexesLevel() throws Throwable {

        int one = gameboard.getGameBoardPositionArray()[99][99].getSettlerCount();
        int two = gameboard.getGameBoardPositionArray()[99][100].getSettlerCount();
        int three = gameboard.getGameBoardPositionArray()[98][101].getSettlerCount();


    }

    @And("^for every empty hex adjacent to the settlement of the specified terrain, add as many villagers as the hex's level$")
    public void forEveryEmptyHexAdjacentToTheSettlementOfTheSpecifiedTerrainAddAsManyVillagersAsTheHexSLevel() throws Throwable {



    }

    @And("^for each villager placed due to the expansion, I should see my villager count decrease by one$")
    public void forEachVillagerPlacedDueToTheExpansionIShouldSeeMyVillagerCountDecreaseByOne() throws Throwable {

        int villagerCount =  playerOne.getVillagerCount();


    }

    @And("^for each villagers placed due to the expansion, I should see my score increase by the total villagers occupying the hex multiplied by the hexes’ level$")
    public void forEachVillagersPlacedDueToTheExpansionIShouldSeeMyScoreIncreaseByTheTotalVillagersOccupyingTheHexMultipliedByTheHexesLevel() throws Throwable {

        int score = playerOne.getScore();


    }

    @And("^for each hex expanded to, increase the settlement size by one and merge those tiles into the original settlement expanded from$")
    public void forEachHexExpandedToIncreaseTheSettlementSizeByOneAndMergeThoseTilesIntoTheOriginalSettlementExpandedFrom() throws Throwable {


        int score = playerOne.getScore();
    }
}
