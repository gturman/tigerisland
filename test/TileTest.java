/**
 * Created by William on 3/14/2017.
 */
import org.junit.*;

public class TileTest {

    @Test
    public void createTileTest(){
        Hex one = new Hex(0,0, terrainTypes.JUNGLE);
        Hex two = new Hex(1, 0, terrainTypes.LAKE);
        Hex three = new Hex(2,0,terrainTypes.VOLCANO);
        Tile tile = new Tile(one,two,three);

        Assert.assertTrue(tile instanceof Tile);
    }

    @Test
    public void returnHexTest() {
        Hex one = new Hex(0,0, terrainTypes.JUNGLE);
        Hex two = new Hex(1, 0, terrainTypes.LAKE);
        Hex three = new Hex(2,0,terrainTypes.VOLCANO);
        Tile tile = new Tile(one, two, three);

        Hex hexTest = tile.getHex(0);
        Assert.assertTrue(hexTest instanceof Hex);
    }

    @Test
    public void returnHexesTest(){
        Hex one = new Hex(0,0, terrainTypes.JUNGLE);
        Hex two = new Hex(1, 0, terrainTypes.LAKE);
        Hex three = new Hex(2,0,terrainTypes.VOLCANO);
        Tile tile = new Tile(one, two, three);

        Hex[] hexesTest = tile.getHexes();
        Assert.assertTrue(hexesTest instanceof Hex[]);
    }


}
