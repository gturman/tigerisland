import org.junit.Assert;
import org.junit.Test;

/**
 * Created by KJ on 3/13/2017.
 */
public class HexTest {

    @Test
    public void testAbilityToCreateHex() {
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertTrue(testHex instanceof Hex);
    }

    @Test
    public void testAbilityToDetermineIfBuiltOn(){
        Hex testHex1 = new Hex(0,0,terrainTypes.LAKE);
        Assert.assertEquals(testHex1.isNotBuiltOn(),true);

        Hex testHex2 = new Hex(0,0,terrainTypes.LAKE);
        testHex2.setSettlerCount(1);
        Assert.assertEquals(testHex2.isNotBuiltOn(),false);

        Hex testHex3 = new Hex(0,0,terrainTypes.LAKE);
        testHex3.setTotoroCount(1);
        Assert.assertEquals(testHex3.isNotBuiltOn(),false);

        Hex testHex4 = new Hex(0,0,terrainTypes.LAKE);
        testHex4.setTigerCount(1);
        Assert.assertEquals(testHex4.isNotBuiltOn(),false);

    }
}
