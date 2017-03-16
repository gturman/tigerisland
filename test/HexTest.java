import org.junit.Assert;
import org.junit.Test;

/**
 * Created by KJ on 3/13/2017.
 */
public class HexTest {

    @Test
    public void testAbilityToCreateHex() {
        Hex testHex = new Hex(0 , terrainTypes.GRASSLANDS);
        Assert.assertTrue(testHex instanceof Hex);
    }

    @Test
    public void testAbilityToMergeHexes() {
        Hex firstHex = new Hex(0, terrainTypes.GRASSLANDS);
        Hex secondHex = new Hex(59, terrainTypes.VOLCANO);
        int firstEdge = 4;
        int secondEdge = 5;

        firstHex.mergeHexes(secondHex, firstEdge, secondEdge);

        Assert.assertTrue(firstHex.getHexEdgeList()[firstEdge].getPairHexID() == secondHex.getHexID());
        Assert.assertTrue(firstHex.getHexEdgeList()[firstEdge].getPairEdge() == secondEdge);

        Assert.assertTrue(secondHex.getHexEdgeList()[secondEdge].getPairHexID() == firstHex.getHexID());
        Assert.assertTrue(secondHex.getHexEdgeList()[secondEdge].getPairEdge() == firstEdge);
    }
}

