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
        int edgeOne = 4;
        int edgeTwo = 5;

        firstHex.mergeHexes(firstHex, secondHex, edgeOne, edgeTwo);

        Assert.assertTrue(firstHex.getHexEdgeList()[edgeOne].getHexID() == secondHex.getHexID());
        Assert.assertTrue(firstHex.getHexEdgeList()[edgeOne].getConnectedEdgeID() == edgeTwo);

        Assert.assertTrue(secondHex.getHexEdgeList()[edgeTwo].getHexID() == firstHex.getHexID());
        Assert.assertTrue(secondHex.getHexEdgeList()[edgeTwo].getConnectedEdgeID() == edgeOne);
    }
}