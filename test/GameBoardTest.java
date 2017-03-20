import org.junit.*;

/**
 * Created by geoff on 3/8/17.
 */

public class GameBoardTest {
    //some testing stuff should happen in here, for sure

    @Test
    public void createGameBoardTest(){
        GameBoard board = new GameBoard();

        Assert.assertTrue(board instanceof GameBoard);
    }

    @Test
    public void placingFirstTileTest() {
        GameBoard board = new GameBoard();
        Tile initialTile = new Tile(1, 1, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.JUNGLE);
        board.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getRowPosition(), 102);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getColumnPosition(), 101);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getRowPosition(), 101);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getRowPosition(), 101);
    }
}