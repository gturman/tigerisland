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
        Player playerOne = new Player(1);
        Assert.assertTrue(playerOne instanceof Player);
    }

    @Test
    public void testGetVillagerCount(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getSettlerCount(),20);
    }

    @Test
    public void testGetTotoroCount(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getTotoroCount(),3);
    }

    @Test
    public void testSettlementCount(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getSettlementCount(),0);
    }

    @Test
    public void testInitialScore(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getScore(), 0 );
    }

    @Test
    public void testModifiedScore(){
        Player playerOne = new Player(1);
        playerOne.increaseScore(30);
        Assert.assertEquals(playerOne.getScore(),30);
    }

    @Test
    public void testModifiedMeepleCount(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getSettlerCount(),20);
        playerOne.setSettlerCount(15);
        Assert.assertEquals(playerOne.getSettlerCount(),15);
    }

    @Test
    public void testModifiedTotoroCount(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getTotoroCount(), 3);
        playerOne.decreaseTotoroCount();
        Assert.assertEquals(playerOne.getTotoroCount(),2);
    }

    @Test
    public void testSetSettlementCount(){
        Player playerOne = new Player(1);
        playerOne.setSettlementCount(3);
        Assert.assertEquals(playerOne.getSettlementCount(),3);
    }

    @Test
    public void testSetPlayerOver2(){
        Player playerOne = new Player(2);
        Assert.assertEquals(playerOne.getPlayerID(),2);
    }

    @Test
    public void testSetPlayer1(){
        Player playerOne = new Player(1);
        playerOne.setPlayerID(1);
        Assert.assertEquals(playerOne.getPlayerID(),1);
    }

    @Test
    public void testGetTigerCount(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getTigerCount(),2);

    }


    @Test
    public void testGetTurnPhase(){
        Player playerOne = new Player(1);
        Assert.assertEquals(playerOne.getTurnPhase(),turnPhase.TILE_PLACEMENT);
    }

    @Test
   public void testSetTurnPhase(){
        Player playerOne = new Player(1);
        playerOne.setTurnPhase(turnPhase.FOUND_SETTLEMENT);
        Assert.assertEquals(playerOne.getTurnPhase(),turnPhase.FOUND_SETTLEMENT);

         playerOne.setTurnPhase(turnPhase.BUILD);
         Assert.assertEquals(playerOne.getTurnPhase(),turnPhase.BUILD);

        playerOne.setTurnPhase(turnPhase.EXPAND_SETTLEMENT);
        Assert.assertEquals(playerOne.getTurnPhase(),turnPhase.EXPAND_SETTLEMENT);

        playerOne.setTurnPhase(turnPhase.PLACE_TOTORO);
        Assert.assertEquals(playerOne.getTurnPhase(),turnPhase.PLACE_TOTORO);

        playerOne.setTurnPhase(turnPhase.PLACE_TIGER);
        Assert.assertEquals(playerOne.getTurnPhase(),turnPhase.PLACE_TIGER);

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
        Assert.assertEquals(dc.pid2,20145);
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

        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED JUNGLE+ROCK AT 10 11 12 5 FOUNDED SETTLEMENT AT 100 101 102");

        Assert.assertEquals(dc.gid, 10);
        Assert.assertEquals(dc.moveNum, 3);
        Assert.assertEquals(dc.pid, 1);
        Assert.assertEquals(dc.tile, "JUNGLE+ROCK");
        Assert.assertEquals(dc.x, 10);
        Assert.assertEquals(dc.y, 11);
        Assert.assertEquals(dc.z, 12);
        Assert.assertEquals(dc.orientation, 5);
        Assert.assertEquals(dc.sx, 100);
        Assert.assertEquals(dc.sy, 101);
        Assert.assertEquals(dc.sz, 102);
        Assert.assertEquals(dc.builtSettlement, true);


    }

    @Test
    public void testAnotherWaitMessage(){
        Decoder dc = new Decoder();
        dc.decodeString("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
        //testing for an exception, no exception = test passes
    }

    @Test
    public void mockMessageStreamTest(){

        Decoder dc = new Decoder();
        dc.decodeString("WELCOME TO ANOTHER EDITION OF THE THUNDERDOME");
        dc.decodeString("TWO SHALL ENTER, ONE SHALL LEAVE");
        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN 1");

        Assert.assertEquals(dc.pid,1);

        dc.decodeString("NEW CHALLENGE 10 YOU WILL PLAY 50 MATCHES");

        Assert.assertEquals(dc.cid,10);
        Assert.assertEquals(dc.rounds,50);

        dc.decodeString("BEGIN ROUND 12 OF 20");

        Assert.assertEquals(dc.rid,12);
        Assert.assertEquals(dc.rounds,20);

        dc.decodeString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 9");

        Assert.assertEquals(dc.pid2,9);

        dc.decodeString("MAKE YOUR MOVE IN GAME 1 WITHIN 30 SECONDS: MOVE 10 PLACE JUNGLE+LAKE");

        Assert.assertEquals(dc.gid,1);
        Assert.assertEquals(dc.time, 30);
        Assert.assertEquals(dc.moveNum, 10);
        Assert.assertEquals(dc.tile,"JUNGLE+LAKE");
        //Assert.assertEquals(dc.tileTerrain1,"JUNGLE");
        //Assert.assertEquals(dc.tileTerrain2,"LAKE");

        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED ROCK+GRASS AT 10 11 12 5 FOUNDED SETTLEMENT AT 100 101 102");

        Assert.assertEquals(dc.gid, 10);
        Assert.assertEquals(dc.moveNum, 3);
        Assert.assertEquals(dc.pid, 1);
        Assert.assertEquals(dc.tile, "ROCK+GRASS");
        Assert.assertEquals(dc.tileTerrain1,"ROCK");
        Assert.assertEquals(dc.tileTerrain2,"GRASS");
        Assert.assertEquals(dc.x, 10);
        Assert.assertEquals(dc.y, 11);
        Assert.assertEquals(dc.z, 12);
        Assert.assertEquals(dc.orientation, 5);
        Assert.assertEquals(dc.sx, 100);
        Assert.assertEquals(dc.sy, 101);
        Assert.assertEquals(dc.sz, 102);
        Assert.assertEquals(dc.builtSettlement, true);

        dc.decodeString("GAME 100 OVER PLAYER 12 200 PLAYER 5 900");

        Assert.assertEquals(dc.gid,100);
        Assert.assertEquals(dc.pid, 12);
        Assert.assertEquals(dc.score,200);
        Assert.assertEquals(dc.pid2, 5);
        Assert.assertEquals(dc.score2,900);

        dc.decodeString("END OF ROUND 12 OF 20");

        Assert.assertEquals(dc.rid,12);
        Assert.assertEquals(dc.rounds,20);


    }

    @Test
    public void testTileStringToObjectConversion(){
        Decoder dc = new Decoder();
        // dc.convertTileStringToTileObject("ROCKY+GRASSLANDS");
        // dc.convertTileStringToTileObject("LAKE+JUNGLE");
    }

    @Test
    public void testCoordinateConversionMethod(){

        int x = 0;
        int y = 0;
        int z = 0;

        Decoder dc = new Decoder();

        dc.convertCoordinatesFromCubicToROffset(x,y,z);

        Assert.assertEquals(102, dc.xCoordinate);
        Assert.assertEquals(102, dc.yCoordinate);
    }

    @Test
    public void testVariousCoordinateConversions(){

        //3,-1,-2
        //col+2, row-2
        int x = 3;
        int y = -1;
        int z = -2;

        Decoder dc = new Decoder();

        dc.convertCoordinatesFromCubicToROffset(x,y,z);

        Assert.assertEquals(104,dc.xCoordinate);
        Assert.assertEquals(100,dc.yCoordinate);

        //-1,-2,3
        //col+0 row+3
        x = -1;
        y = -2;
        z = 3;

        dc.convertCoordinatesFromCubicToROffset(x,y,z);

        Assert.assertEquals(102,dc.xCoordinate);
        Assert.assertEquals(105,dc.yCoordinate);


    }

    @Test
    public void testCoordinateConversionBasedOnOrientationEven(){

        Decoder dc = new Decoder();
        Pair pair = new Pair(101,103);
        Assert.assertEquals(pair.getColumnPosition(), dc.convertCoordinatesBasedOnOrientation(102,102,5).getColumnPosition());
        Assert.assertEquals(pair.getRowPosition(), dc.convertCoordinatesBasedOnOrientation(102,102,5).getRowPosition());

    }

    @Test
    public void testCoordinateConversionBasedOnOrientationOdd(){

        Decoder dc = new Decoder();
        Pair pair = new Pair(103,102);
        Assert.assertEquals(pair.getColumnPosition(),dc.convertCoordinatesBasedOnOrientation(102,101,3).getColumnPosition());
        Assert.assertEquals(pair.getRowPosition(),dc.convertCoordinatesBasedOnOrientation(102,101,3).getRowPosition());

    }

    @Test
    public void testCoordinateConversionFromGameMessage(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED ROCK+GRASS AT 0 0 0 3 FOUNDED SETTLEMENT AT 100 101 102");
        //102,103
        Assert.assertEquals(102,dc.theirColumnPosition);
        Assert.assertEquals(103,dc.theirRowPosition);

    }

    @Test
    public void testCoordinateConversionFromGameMessage2(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED ROCK+GRASS AT 0 0 0 3 FOUNDED SETTLEMENT AT 100 101 102");

    }

}
