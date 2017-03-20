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
}
