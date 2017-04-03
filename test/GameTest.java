import org.junit.*;
/**
 * Created by William on 3/27/2017.
 */
public class GameTest {
    Game game;

    @Before
    public void simulateMain(){
        //initialize main
        game = new Game();
    }

    @Test
    public void testAbilityToBuildSettlementAndUpdatePlayer(){

        //create tile
        Tile tile = new Tile(game.gameBoard.getGameBoardTileID(),game.gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);

        //place tile
        game.gameBoard.setTileAtPosition(101,101,tile);

        //valid build, should update hex [101][101] to have 1 villager, contain playerID 1, decrease
        //villager count of P1 by 1, and increase score of P1 by 1
        game.buildSettlement(101,101,game.playerOne,game.gameBoard);

        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[101][101].getSettlerCount(),1);
        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[101][101].getPlayerID(),1);
        Assert.assertEquals(game.playerOne.getVillagerCount(),19);
        Assert.assertEquals(game.playerOne.getScore(),1);

        //invalid build (volcano), should do nothing
        game.buildSettlement(102,100,game.playerTwo,game.gameBoard);
        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[102][100].getSettlerCount(),0);
        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[102][100].getPlayerID(),0);
        Assert.assertEquals(game.playerTwo.getVillagerCount(),20);
        Assert.assertEquals(game.playerTwo.getScore(),0);

        //valid build, same as above
        game.buildSettlement(101,100,game.playerOne,game.gameBoard);

        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[101][100].getSettlerCount(),1);
        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[101][100].getPlayerID(),1);
        Assert.assertEquals(game.playerOne.getVillagerCount(),18);
        Assert.assertEquals(game.playerOne.getScore(),2);

        //invalid build(already built on), do nothing

        game.buildSettlement(101,100,game.playerOne,game.gameBoard);

        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[101][100].getSettlerCount(),1);
        Assert.assertEquals(game.gameBoard.getGameBoardPositionArray()[101][100].getPlayerID(),1);
        Assert.assertEquals(game.playerOne.getVillagerCount(),18);
        Assert.assertEquals(game.playerOne.getScore(),2);
    }

}
