import org.junit.Assert;
import org.junit.Test;

/**
 * Created by James on 4/8/2017.
 * and Brendan. I feel left out.
 */
public class DecoderTest {


    @Test
    public void testWaitMessageForPlayerID(){
        Decoder dc = new Decoder();

        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN 1");
        Assert.assertEquals("1",dc.getPlayerID1());

        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN 19304");
        Assert.assertEquals("19304",dc.getPlayerID1());
    }

    @Test
    public void testBeginMessageFunctionality(){
        Decoder dc = new Decoder();

        dc.decodeString("BEGIN ROUND 21 OF 30");
        Assert.assertEquals("21",dc.currentRoundID);
    }

    @Test
    public void testEndMessageFunctionality(){
        Decoder dc = new Decoder();

        dc.decodeString("END ROUND 12 OF 24");
        Assert.assertEquals(true, dc.endOfRoundFlag);

        dc.decodeString("END ROUND 50 OF 50 WAIT FOR THE NEXT MATCH");
        Assert.assertEquals(true,dc.endOfRoundFlag);
        Assert.assertEquals(true,dc.waitingForNextMatchFlag);

        dc.decodeString("END OF CHALLENGES");
        Assert.assertEquals(true,dc.endOfChallenges);
    }

    @Test
    public void testNewMessageFunctionality(){
    Decoder dc = new Decoder();

    dc.decodeString("NEW CHALLENGE Q12 YOU WILL PLAY 60 ROUND MATCHES");
    Assert.assertEquals("Q12",dc.getChallengeID());
    Assert.assertEquals(60,dc.numberOfRounds);

    dc.decodeString("NEW ROUND BEGINNING NOW YOUR OPPONENT IS PLAYER Q90LKE2");
    Assert.assertEquals("Q90LKE2",dc.getPlayerID2());
    }

    @Test
    public void testMakeMessageFunctionality(){
        Decoder dc = new Decoder();
        dc.decodeString("MAKE YOUR MOVE IN GAME P WITHIN 45 SECONDS: MOVE 20 TILE ROCK+GRASS");
        Assert.assertEquals("P", dc.gameID);

    }
/*
    @Test
    public void testNewMessageForChallengeIDandOpponentID(){

        Decoder dc = new Decoder();

        dc.decodeString("NEW CHALLENGE 10 YOU WILL PLAY 10 MATCHES");
        Assert.assertEquals(dc.getChallengeID(),"10");
        Assert.assertEquals(dc.numberOfRounds, 10);

        dc.decodeString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 20145");
        Assert.assertEquals(dc.playerID2,20145);
    }

    @Test
    public void testNewMessageForBeginRound(){

        Decoder dc = new Decoder();

        dc.decodeString("BEGIN ROUND 10 OF 2147000");
        Assert.assertEquals(dc.currentRoundID,10);
        Assert.assertEquals(dc.numberOfRounds,2147000);
    }

    @Test
    public void testNewMessageForMakeMoves(){

        Decoder dc = new Decoder();

        dc.decodeString("MAKE YOUR MOVE IN GAME 123 WITHIN 125 SECONDS: MOVE 12 PLACE 12345");
        Assert.assertEquals(dc.gameID,123);
        Assert.assertEquals(dc.timeToCompleteTurn,125);
        Assert.assertEquals(dc.currentMoveNum,12);
        Assert.assertEquals(dc.tileTerrainStringOfFormatAandB, "12345");
    }

    @Test
    public void testNewMessageForEndOfRound(){
        Decoder dc = new Decoder();

        dc.decodeString("END OF ROUND 300 OF 5000");
        Assert.assertEquals(dc.currentRoundID,300);
        Assert.assertEquals(dc.numberOfRounds,5000);
    }

    @Test
    public void testGameMessages() {

        Decoder dc = new Decoder();

        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED JUNGLE+ROCK AT 10 11 12 5 FOUNDED SETTLEMENT AT 100 101 102");

        Assert.assertEquals(dc.gameID, 10);
        Assert.assertEquals(dc.currentMoveNum, 3);
        Assert.assertEquals(dc.playerID1, 1);
        Assert.assertEquals(dc.tileTerrainStringOfFormatAandB, "JUNGLE+ROCK");
        Assert.assertEquals(dc.xCubicTileCoordinate, 10);
        Assert.assertEquals(dc.yCubicTileCoordinate, 11);
        Assert.assertEquals(dc.zCubicTileCoordinate, 12);
        Assert.assertEquals(dc.orientation, 5);
        Assert.assertEquals(dc.xCubicBuildCoordinate, 100);
        Assert.assertEquals(dc.yCubicBuildCoordinate, 101);
        Assert.assertEquals(dc.zCubicBuildCoordinate, 102);
        Assert.assertEquals(dc.builtSettlementFlag, true);


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

        Assert.assertEquals(dc.playerID1,1);

        dc.decodeString("NEW CHALLENGE 10 YOU WILL PLAY 50 MATCHES");

        Assert.assertEquals(dc.challengeID,10);
        Assert.assertEquals(dc.numberOfRounds,50);

        dc.decodeString("BEGIN ROUND 12 OF 20");

        Assert.assertEquals(dc.currentRoundID,12);
        Assert.assertEquals(dc.numberOfRounds,20);

        dc.decodeString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 9");

        Assert.assertEquals(dc.playerID2,9);

        dc.decodeString("MAKE YOUR MOVE IN GAME 1 WITHIN 30 SECONDS: MOVE 10 PLACE JUNGLE+LAKE");

        Assert.assertEquals(dc.gameID,1);
        Assert.assertEquals(dc.timeToCompleteTurn, 30);
        Assert.assertEquals(dc.currentMoveNum, 10);
        Assert.assertEquals(dc.tileTerrainStringOfFormatAandB,"JUNGLE+LAKE");
        //Assert.assertEquals(dc.tileTerrain1,"JUNGLE");
        //Assert.assertEquals(dc.tileTerrain2,"LAKE");

        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED ROCK+GRASS AT 10 11 12 5 FOUNDED SETTLEMENT AT 100 101 102");

        Assert.assertEquals(dc.gameID, 10);
        Assert.assertEquals(dc.currentMoveNum, 3);
        Assert.assertEquals(dc.playerID1, 1);
        Assert.assertEquals(dc.tileTerrainStringOfFormatAandB, "ROCK+GRASS");
        Assert.assertEquals(dc.tileTerrain1,"ROCK");
        Assert.assertEquals(dc.tileTerrain2,"GRASS");
        Assert.assertEquals(dc.xCubicTileCoordinate, 10);
        Assert.assertEquals(dc.yCubicTileCoordinate, 11);
        Assert.assertEquals(dc.zCubicTileCoordinate, 12);
        Assert.assertEquals(dc.orientation, 5);
        Assert.assertEquals(dc.xCubicBuildCoordinate, 100);
        Assert.assertEquals(dc.yCubicBuildCoordinate, 101);
        Assert.assertEquals(dc.zCubicBuildCoordinate, 102);
        Assert.assertEquals(dc.builtSettlementFlag, true);

        dc.decodeString("GAME 100 OVER PLAYER 12 200 PLAYER 5 900");

        Assert.assertEquals(dc.gameID,100);
        Assert.assertEquals(dc.playerID1, 12);
        Assert.assertEquals(dc.scoreOfPlayer1,200);
        Assert.assertEquals(dc.playerID2, 5);
        Assert.assertEquals(dc.scoreOfPlayer2,900);

        dc.decodeString("END OF ROUND 12 OF 20");

        Assert.assertEquals(dc.currentRoundID,12);
        Assert.assertEquals(dc.numberOfRounds,20);


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

        dc.convertTileCoordinatesFromCubicToOddROffset(x,y,z);

        Assert.assertEquals(102, dc.xOddRTileCoordinate);
        Assert.assertEquals(102, dc.yOddRTileCoordinate);
    }

    @Test
    public void testVariousCoordinateConversions(){

        //3,-1,-2
        //col+2, row-2
        int x = 3;
        int y = -1;
        int z = -2;

        Decoder dc = new Decoder();

        dc.convertTileCoordinatesFromCubicToOddROffset(x,y,z);

        Assert.assertEquals(104,dc.xOddRTileCoordinate);
        Assert.assertEquals(100,dc.yOddRTileCoordinate);

        //-1,-2,3
        //col+0 row+3
        x = -1;
        y = -2;
        z = 3;

        dc.convertTileCoordinatesFromCubicToOddROffset(x,y,z);

        Assert.assertEquals(102,dc.xOddRTileCoordinate);
        Assert.assertEquals(105,dc.yOddRTileCoordinate);


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
        Assert.assertEquals(102,dc.theirTileColumnPosition);
        Assert.assertEquals(103,dc.theirTileRowPosition);

    }

    @Test
    public void testCoordinateConversionFromGameMessage2(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME 10 MOVE 3 PLAYER 1 PLACED ROCK+GRASS AT 0 0 0 3 FOUNDED SETTLEMENT AT 100 101 102");

    }*/
}
