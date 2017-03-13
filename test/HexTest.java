import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by KJ on 3/13/2017.
 */
public class HexTest {

    @Test
    public void testAbilityToCreateHex() {
        Hex testHex = new Hex(0 , 0, terrainTypes.GRASSLANDS);
        Assert.assertTrue(testHex instanceof Hex);
    }

    @Test
    public void testAbilityToMergeHexes() {
        Hex firstHex = new Hex(0, 1, terrainTypes.GRASSLANDS);
        Hex secondHex = new Hex(59, 4, terrainTypes.VOLCANO);

        mergeHexes(firstHex, secondHex);

        Assert.assertTrue();
    }
}