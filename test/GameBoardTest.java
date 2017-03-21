import org.junit.*;

/**
 * Created by geoff on 3/8/17.
 */

public class GameBoardTest {
    //some testing stuff should happen in here, for sure

    @Test
    public void createGameBoardTest(){
        GameBoard gameboard = new GameBoard();

        Assert.assertTrue(gameboard instanceof GameBoard);
    }

    @Test
    public void placingFirstTileTest() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getRowPosition(), 102);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getColumnPosition(), 101);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getRowPosition(), 101);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getRowPosition(), 101);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102], initialTile.getHexA());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101], initialTile.getHexB());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101], initialTile.getHexC());
    }

    @Test
    public void testAbilityToIncrementHexAndTileID() {
        GameBoard gameboard = new GameBoard();

        Assert.assertEquals(gameboard.getGameBoardHexID(), 1);
        Assert.assertEquals(gameboard.getGameboardTileID(), 1);

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                                    terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameboard.getGameBoardHexID(), 4);
        Assert.assertEquals(gameboard.getGameboardTileID(), 2);
    }
}