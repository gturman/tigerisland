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

        terrainTypes hexTerrainA = terrainTypes.GRASSLANDS;
        terrainTypes hexTerrainB = terrainTypes.JUNGLE;
        terrainTypes hexTerrainC = terrainTypes.VOLCANO;

        Tile testTile = new Tile(tileID, hexID, hexTerrainA, hexTerrainB, hexTerrainC);
        Assert.assertTrue(testTile instanceof Tile);
    }
}