import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by christine on 4/4/2017.
 */
public class stepDefDisallowExpansion {

    private Player playerOne = new Player(1);
    private GameBoard gameBoard = new GameBoard();
    private int playerID = playerOne.getPlayerID();


    private Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
    private Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
    private Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);

    @Given("^Im the player$")
    public void imThePlayer() throws Throwable {

        Player player = new Player();
        int score = playerOne.getScore();
        System.out.print("Initial Score: " + score + "\n");
        int settlementsInit = playerOne.getSettlementCount();
        System.out.print("Initial settlement count: " + settlementsInit + "\n");

        firstTile.flip();
        gameBoard.setTileAtPosition(99,98,firstTile);

        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);
        gameBoard.buildSettlement(99,98, playerOne);
        gameBoard.buildSettlement(99,100,playerOne);

        int score1 = playerOne.getScore();
        System.out.print("Score: " + score1 + "\n");
        int settlements = playerOne.getSettlementCount();
        System.out.print("settlement count: " + settlements + "\n");
    }

    @And("^I am build phase of my turn$")
    public void iAmBuildPhaseOfMyTurn() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.BUILD)
        {
            gameBoard.placeFirstTileAndUpdateValidPlacementList();
        }
    }

    @And("^I have chosen expand$")
    public void iHaveChosenExpand() throws Throwable {

        if(playerOne.getTurnPhase() == turnPhase.EXPAND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        }
    }

    @Given("^the hex I want to expand to is occupied$")
    public void theHexIWantToExpandToIsOccupied() throws Throwable {

        boolean notBuiltOn = gameBoard.isNotBuiltOn(99,98);
        System.out.print("notBuiltOn : " + notBuiltOn + "\n");

    }

    @When("^I try to pick that terrain to expand to$")
    public void iTryToPickThatTerrainToExpandTo() throws Throwable {

        gameBoard.expandSettlement(98,99,terrainTypes.VOLCANO,playerOne);


    }


    @Given("^the hex I want to expand to is on an uninhabitable terrain type$")
    public void theHexIWantToExpandToIsOnAnUninhabitableTerrainType() throws Throwable {



    }

    @Given("^the hex I want to expand is not adjacent to any of my settlements$")
    public void theHexIWantToExpandIsNotAdjacentToAnyOfMySettlements() throws Throwable {



    }

    @When("^I try to pick that hex to expand to$")
    public void iTryToPickThatHexToExpandTo() throws Throwable {



    }

    @Given("^the chosen expansion requires more villagers than I currently have$")
    public void theChosenExpansionRequiresMoreVillagersThanICurrentlyHave() throws Throwable {



    }

    @When("^I choose to expand$")
    public void iChooseToExpand() throws Throwable {



    }

    @Then("^my expansion is prevented$")
    public void myExpansionIsPrevented() throws Throwable {



    }


}
