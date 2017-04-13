package unitTests;

import enums.terrainTypes;
import gameRules.GameBoard;
import gameRules.Tile;
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

        testTile.rotateTileClockwise(0);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.LAKE);

        testTile.rotateTileClockwise(1);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.JUNGLE);

        testTile.rotateTileClockwise(2);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.LAKE);

        testTile.rotateTileClockwise(5);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.VOLCANO);

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

        testTile.rotateTileClockwise(0);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.JUNGLE);

        testTile.rotateTileClockwise(1);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.VOLCANO);

        testTile.rotateTileClockwise(2);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.JUNGLE);

        testTile.rotateTileClockwise(5);
        Assert.assertEquals(testTile.getHexA().getTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(testTile.getHexB().getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(testTile.getHexC().getTerrainType(), terrainTypes.LAKE);

    }

}