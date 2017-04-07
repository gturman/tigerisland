import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Christine Chierico on 4/1/2017.
 */
public class stepDefAllowBuildingSettlement {

    @Given("^I am player$")
    public void i_AmPlayer() throws Throwable {
        Player player = new Player(1);
    }


    @And("^I am in the build phase of my turn$")
    public void iAmInTheBuildPhaseOfMyTurn() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.BUILD)
        {
            gameboard.placeFirstTileAndUpdateValidPlacementList();
        }
    }

    @And("^I have chosen to settle$")
    public void iHaveChosenToSettle() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, placeTile);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            gameboard.buildSettlement(103, 102, player);
        }
    }

    @And("^I still have villagers$")
    public void iStillHaveVillagers() throws Throwable {
        Player player = new Player(1);
        int villagerCount =  player.getVillagerCount();
    }

    @Given("^the hex is habitable$")
    public void theHexIsHabitable() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.isValidSettlementLocation(103, 102);
        }
    }

    @And("^the hex is currently empty$")
    public void theHexIsCurrentlyEmpty() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.isValidSettlementLocation(103, 102);
        }
    }

    @And("^the hex is level one$")
    public void theHexIsLevelOne() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.isValidSettlementLocation(103, 102);
        }
    }

    @When("^I try to place a villager on a hex$")
    public void iTryToPlaceAVillagerOnAHex() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.buildSettlement(103, 102, player);
        }
    }

    @Then("^I should see that my settlement was placed$")
    public void iShouldSeeThatMySettlementWasPlaced() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.buildSettlement(103, 102, player);
        }
    }

    @And("^my total villager count has decreased by one$")
    public void myTotalVillagerCountHasDecreasedByOne() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.buildSettlement(103, 102, player);

            int villageCount = player.getVillagerCount();
        }

    }

    @And("^my point total has increased by one$")
    public void myPointTotalHasIncreasedByOne() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.buildSettlement(103, 102, player);
        }

        int score = player.getScore();
    }

    @And("^if my villager is adjacent to one of my settlements, that villager merges into that settlement and its size increases by one, otherwise it becomes it’s own settlement$")
    public void ifMyVillagerIsAdjacentToOneOfMySettlementsThatVillagerMergesIntoThatSettlementAndItsSizeIncreasesByOtherwiseItBecomesItSOwnSettlement() throws Throwable {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        if(player.getTurnPhase() == turnPhase.FOUND_SETTLEMENT)
        {
            Tile placeTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
            gameboard.setTileAtPosition(103, 103, placeTile);

            gameboard.buildSettlement(103, 102, player);
            gameboard.buildSettlement(103, 101, player);

            gameboard.mergeSettlements();
        }
    }
}
