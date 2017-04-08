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

    @Test
    public void testGetHexLevel(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getLevel(),1);

        Hex testHex1 = new Hex(0, 0, terrainTypes.VOLCANO);
        Assert.assertEquals(testHex1.getLevel(),1);
    }

    @Test
    public void testSetHexLevel(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getLevel(),1);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex1.setLevel(2);
        Assert.assertEquals(testHex1.getLevel(),2);

        Hex testHex2 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex2.setLevel(5);
        Assert.assertEquals(testHex2.getLevel(),5);

    }

    @Test
    public void testGetHexID(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getHexID(),0);

        Hex testHex1 = new Hex(3, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex1.getHexID(),3);

        Hex testHex2 = new Hex(5, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex2.getHexID(),5);

    }

    @Test
    public void testGetSettlerCount(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getSettlerCount(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.JUNGLE);
        Assert.assertEquals(testHex1.getSettlerCount(),0);

    }

    @Test
    public void testSetSettlerCount(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getSettlerCount(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex1.setSettlerCount(1);
        Assert.assertEquals(testHex1.getSettlerCount(),1);

        Hex testHex2 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex2.setSettlerCount(5);
        Assert.assertEquals(testHex2.getSettlerCount(),5);

    }

    @Test
    public void testGetTotoroCount(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getTotoroCount(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.VOLCANO);
        Assert.assertEquals(testHex1.getTotoroCount(),0);

    }

    @Test
    public void testSetTotoroCount(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getTotoroCount(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex1.setTotoroCount(1);
        Assert.assertEquals(testHex1.getTotoroCount(),1);

        Hex testHex2 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex2.setTotoroCount(5);
        Assert.assertEquals(testHex2.getTotoroCount(),5);

    }

    @Test
    public void testGetTigerCount(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getTigerCount(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex1.getTigerCount(),0);

    }

    @Test
    public void testSetTigerCount(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getTigerCount(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex1.setTigerCount(1);
        Assert.assertEquals(testHex1.getTigerCount(),1);

        Hex testHex2 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex2.setTigerCount(5);
        Assert.assertEquals(testHex2.getTigerCount(),5);

    }

    @Test
    public void testGetPlayerID(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getPlayerID(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex1.getPlayerID(),0);

    }

    @Test
    public void testSetPlayerID(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getPlayerID(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex1.setPlayerID(1);
        Assert.assertEquals(testHex1.getPlayerID(),1);

        Hex testHex2 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex2.setPlayerID(5);
        Assert.assertEquals(testHex2.getPlayerID(),5);

    }

    @Test
    public void testGetIfTraversed(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getIfAlreadyTraversed(),false);

        Hex testHex1 = new Hex(1, 0, terrainTypes.VOLCANO);
        Assert.assertEquals(testHex1.getIfAlreadyTraversed(),false);

    }

    @Test
    public void testSetIfTraversed(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getIfAlreadyTraversed(),false);

        Hex testHex1 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex1.setIfAlreadyTraversed(true);
        Assert.assertEquals(testHex1.getIfAlreadyTraversed(),true);

        Hex testHex2 = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex2.setIfAlreadyTraversed(true);
        Assert.assertEquals(testHex2.getIfAlreadyTraversed(),true);

    }

    @Test
    public void testParentTileID(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getParentTileID(),0);

        Hex testHex1 = new Hex(0, 1, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex1.getParentTileID(),1);

        Hex testHex2 = new Hex(0, 6, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex2.getParentTileID(),6);

    }

    @Test
    public void testGetTerrainType(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getTerrainType(),terrainTypes.GRASSLANDS);

        Hex testHex1 = new Hex(0, 0, terrainTypes.VOLCANO);
        Assert.assertEquals(testHex1.getTerrainType(),terrainTypes.VOLCANO);

        Hex testHex2 = new Hex(0, 0, terrainTypes.ROCKY);
        Assert.assertEquals(testHex2.getTerrainType(),terrainTypes.ROCKY);

        Hex testHex3 = new Hex(0, 0, terrainTypes.JUNGLE);
        Assert.assertEquals(testHex3.getTerrainType(),terrainTypes.JUNGLE);


    }

    @Test
    public void testSetTerrainType(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex.setTerrainType(terrainTypes.VOLCANO);
        Assert.assertEquals(testHex.getTerrainType(),terrainTypes.VOLCANO);

        Hex testHex1 = new Hex(0, 0, terrainTypes.VOLCANO);
        testHex1.setTerrainType(terrainTypes.ROCKY);
        Assert.assertEquals(testHex1.getTerrainType(),terrainTypes.ROCKY);

        Hex testHex2 = new Hex(0, 0, terrainTypes.ROCKY);
        testHex2.setTerrainType(terrainTypes.JUNGLE);
        Assert.assertEquals(testHex2.getTerrainType(),terrainTypes.JUNGLE);

        Hex testHex3 = new Hex(0, 0, terrainTypes.ROCKY);
        testHex3.setTerrainType(terrainTypes.JUNGLE);

        Assert.assertEquals(testHex3.getTerrainType(),terrainTypes.JUNGLE);
    }

    @Test
    public void testGetSettlementID(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        Assert.assertEquals(testHex.getSettlementID(),0);

        Hex testHex1 = new Hex(0, 0, terrainTypes.VOLCANO);
        Assert.assertEquals(testHex1.getSettlementID(),0);

    }

    @Test
    public void testSetSettlementID(){
        Hex testHex = new Hex(0, 0, terrainTypes.GRASSLANDS);
        testHex.setSettlementID(1);
        Assert.assertEquals(testHex.getSettlementID(),1);

        Hex testHex1 = new Hex(0, 0, terrainTypes.VOLCANO);
        testHex1.setSettlementID(12);
        Assert.assertEquals(testHex1.getSettlementID(),12);

        Hex testHex2 = new Hex(0, 0, terrainTypes.VOLCANO);
        testHex2.setSettlementID(24);
        Assert.assertEquals(testHex2.getSettlementID(),24);
    }


}
