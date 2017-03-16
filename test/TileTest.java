import org.junit.Assert;
import org.junit.Test;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 * Created by KJ on 3/14/2017.
 */
@RunWith(Cucumber.class)
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
