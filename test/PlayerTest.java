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
        Player P1 = new Player();
        Assert.assertTrue(P1 instanceof Player);
    }

    @Test
    public void testGetVillagerCount(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getVillagerCount(),20);
    }

    @Test
    public void testGetTotoroCount(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getTotoroCount(),3);
    }

    @Test
    public void testSettlementCount(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getSettlementCount(),0);
    }

    @Test
    public void testInitialScore(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getScore(), 0 );
    }

    @Test
    public void testModifiedScore(){
        Player P1 = new Player();
        P1.increaseScore(30);
        Assert.assertEquals(P1.getScore(),30);
    }

    @Test
    public void testModifiedMeepleCount(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getVillagerCount(),20);
        P1.setVillagerCount(15);
        Assert.assertEquals(P1.getVillagerCount(),15);
    }

    @Test
    public void testModifiedTotoroCount(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getTotoroCount(), 3);
        P1.decreaseTotoroCount();
        Assert.assertEquals(P1.getTotoroCount(),2);
    }

    @Test
    public void testSetSettlementCount(){
        Player P1 = new Player();
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
        Player P1 = new Player();
        P1.setPlayerID(1);
        Assert.assertEquals(P1.getPlayerID(),1);
    }

    @Test
    public void testTileIncrease(){
        Player P1 = new Player();
        P1.increaseTileCount();
        Assert.assertEquals(P1.getTileCount(),1);

    }

    @Test
    public void testGetTigerCount(){
        Player P1 = new Player();
        Assert.assertEquals(P1.getTigerCount(),2);

    }


    @Test
    public void testWaitMessageForPlayerID(){
        Decoder dc = new Decoder();

        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN 1");
        Assert.assertEquals(dc.pid, 1);

        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN 19304");
        Assert.assertEquals(dc.pid, 19304);

    }

    @Test
    public void testNewMessageForChallengeIDandOpponentID(){

        Decoder dc = new Decoder();

        dc.decodeString("NEW CHALLENGE 10 YOU WILL PLAY 10 MATCHES");
        Assert.assertEquals(dc.cid,10);
        Assert.assertEquals(dc.rounds, 10);

        dc.decodeString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 20145");
        Assert.assertEquals(dc.opponentPid,20145);

    }

    @Test
    public void testNewMessageForBeginRound(){

        Decoder dc = new Decoder();

        dc.decodeString("BEGIN ROUND 10 OF 2147000");
        Assert.assertEquals(dc.rid,10);
        Assert.assertEquals(dc.rounds,2147000);

    }

    @Test
    public void testNewMessageForMakeMoves(){

        Decoder dc = new Decoder();

        dc.decodeString("MAKE YOUR MOVE IN GAME 123 WITHIN 125 SECONDS: MOVE 12 PLACE 12345");
        Assert.assertEquals(dc.gid,123);
        Assert.assertEquals(dc.time,125);
        Assert.assertEquals(dc.moveNum,12);
        Assert.assertEquals(dc.tile, "12345");

    }

    @Test
    public void testNewMessageForEndOfRound(){
        Decoder dc = new Decoder();

        dc.decodeString("END OF ROUND 300 OF 5000");
        Assert.assertEquals(dc.rid,300);
        Assert.assertEquals(dc.rounds,5000);
    }

    @Test
    public void testGameMessages() {

        Decoder dc = new Decoder();

        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED 1 AT 10 11 12 5 FOUNDED SETTLEMENT AT 100 101 102");

        Assert.assertEquals(dc.gid, 10);
        Assert.assertEquals(dc.moveNum, 3);
        Assert.assertEquals(dc.pid, 1);
        Assert.assertEquals(dc.tile, "1");
        Assert.assertEquals(dc.x, 10);
        Assert.assertEquals(dc.y, 11);
        Assert.assertEquals(dc.z, 12);
        Assert.assertEquals(dc.orientation, 5);
        Assert.assertEquals(dc.sx, 100);
        Assert.assertEquals(dc.sy, 101);
        Assert.assertEquals(dc.sz, 102);
        Assert.assertEquals(dc.builtSettlement, true);


    }

}
