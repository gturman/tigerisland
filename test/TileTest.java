import org.junit.Assert;
import org.junit.Test;

/**
 * Created by KJ on 3/14/2017.
 */
public class TileTest {

    @Test
    public void testAbilityToCreateTile() {
        GameBoard gameboard = new GameBoard();

        Tile testTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                                 terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        Assert.assertTrue(testTile instanceof Tile);
    }

    @Test
    public void testAbilityToRotateATile(){
        GameBoard gameboard = new GameBoard();

        terrainTypes hexTerrainA = terrainTypes.VOLCANO;
        terrainTypes hexTerrainB = terrainTypes.JUNGLE;
        terrainTypes hexTerrainC = terrainTypes.LAKE;

        Tile testTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                                hexTerrainA, hexTerrainB, hexTerrainC);

        testTile.tileRotationClockwise(0);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.LAKE);

        testTile.tileRotationClockwise(1);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.JUNGLE);

        testTile.tileRotationClockwise(2);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.LAKE);

        testTile.tileRotationClockwise(5);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.VOLCANO);

    }

    @Test
    public void testAbilityToRotateAFlippedTile(){
        GameBoard gameboard = new GameBoard();

        terrainTypes hexTerrainA = terrainTypes.VOLCANO;
        terrainTypes hexTerrainB = terrainTypes.JUNGLE;
        terrainTypes hexTerrainC = terrainTypes.LAKE;

        Tile testTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(),
                hexTerrainA, hexTerrainB, hexTerrainC);
        testTile.flip();

        testTile.tileRotationClockwise(0);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.JUNGLE);

        testTile.tileRotationClockwise(1);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.VOLCANO);

        testTile.tileRotationClockwise(2);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.JUNGLE);

        testTile.tileRotationClockwise(5);
        Assert.assertEquals(testTile.getHexA().getHexTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexB().getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexC().getHexTerrainType(), terrainTypes.LAKE);

    }

}