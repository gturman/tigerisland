import org.junit.Assert;
import org.junit.Test;

/**
 * Created by KJ on 3/14/2017.
 */
public class TileTest {

    @Test
    public void testAbilityToCreateTile() {

        int tileID = 0;
        int hexID = 0;

        terrainTypes hexTerrainA = terrainTypes.VOLCANO;
        terrainTypes hexTerrainB = terrainTypes.JUNGLE;
        terrainTypes hexTerrainC = terrainTypes.GRASSLANDS;

        Tile testTile = new Tile(tileID, hexID, hexTerrainA, hexTerrainB, hexTerrainC);
        Assert.assertTrue(testTile instanceof Tile);
    }

    @Test
    public void testAbilityToRotateATile(){
        int tileID = 0;
        int hexID = 0;

        terrainTypes hexTerrainA = terrainTypes.VOLCANO;
        terrainTypes hexTerrainB = terrainTypes.JUNGLE;
        terrainTypes hexTerrainC = terrainTypes.LAKE;

        Tile testTile = new Tile(tileID, hexID, hexTerrainA, hexTerrainB, hexTerrainC);

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
}