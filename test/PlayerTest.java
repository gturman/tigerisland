/**
 * Created by Brendan on 3/15/2017.
 */
import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testPlayerCreation(){
        Player P1 = new Player(1);
        Assert.assertTrue(P1 instanceof Player);
    }

    @Test
    public void testGetVillagerCount(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getSettlerCount(),20);
    }

    @Test
    public void testGetTotoroCount(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getTotoroCount(),3);
    }

    @Test
    public void testSettlementCount(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getSettlementCount(),0);
    }

    @Test
    public void testInitialScore(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getScore(), 0 );
    }

    @Test
    public void testModifiedScore(){
        Player P1 = new Player(1);
        P1.increaseScore(30);
        Assert.assertEquals(P1.getScore(),30);
    }

    @Test
    public void testModifiedMeepleCount(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getSettlerCount(),20);
        P1.setSettlerCount(15);
        Assert.assertEquals(P1.getSettlerCount(),15);
    }

    @Test
    public void testModifiedTotoroCount(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getTotoroCount(), 3);
        P1.decreaseTotoroCount();
        Assert.assertEquals(P1.getTotoroCount(),2);
    }

    @Test
    public void testSetSettlementCount(){
        Player P1 = new Player(1);
        P1.setSettlementCount(3);
        Assert.assertEquals(P1.getSettlementCount(),3);
    }

    @Test
    public void testSetPlayerOver2(){
        Player P1 = new Player(2);
        Assert.assertEquals(P1.getPlayerID(),2);
    }

    @Test
    public void testSetPlayer1(){
        Player P1 = new Player(1);
        P1.setPlayerID(1);
        Assert.assertEquals(P1.getPlayerID(),1);
    }

    @Test
    public void testGetTigerCount(){
        Player P1 = new Player(1);
        Assert.assertEquals(P1.getTigerCount(),2);

    }

}
