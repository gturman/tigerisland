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
        Assert.assertEquals("21",dc.getCurrentRoundID());
    }

    @Test
    public void testEndMessageFunctionality(){
        Decoder dc = new Decoder();

        dc.decodeString("END ROUND 12 OF 24");
        Assert.assertEquals(true, dc.getEndOfRoundFlag());

        dc.decodeString("END ROUND 50 OF 50 WAIT FOR THE NEXT MATCH");
        Assert.assertEquals(true,dc.getEndOfRoundFlag());
        Assert.assertEquals(true,dc.getWaitingForNextMatchFlag());

        dc.decodeString("END OF CHALLENGES");
        Assert.assertEquals(true,dc.getEndOfChallenges());
    }

    @Test
    public void testNewMessageFunctionality(){
        Decoder dc = new Decoder();

        dc.decodeString("NEW CHALLENGE Q12 YOU WILL PLAY 60 ROUND MATCHES");
        Assert.assertEquals("Q12",dc.getChallengeID());
        Assert.assertEquals(60,dc.getNumberOfRounds());

        dc.decodeString("NEW ROUND BEGINNING NOW YOUR OPPONENT IS PLAYER Q90LKE2");
        Assert.assertEquals("Q90LKE2",dc.getPlayerID2());
    }

    @Test
    public void testMakeMessageFunctionality(){
        Decoder dc = new Decoder();
        dc.decodeString("MAKE YOUR MOVE IN GAME P WITHIN 45.4 SECONDS: MOVE 20 PLACE ROCK+GRASS");
        Assert.assertEquals("P", dc.getGameID());
        Assert.assertEquals(45.4,dc.getTimeToCompleteTurn(),.000001);
        Assert.assertEquals(20,dc.getCurrentMoveNum());
        Assert.assertEquals("ROCK+GRASS",dc.getTileTerrainStringOfFormatAandB());

        Assert.assertEquals(terrainTypes.VOLCANO, dc.getOurTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.ROCKY, dc.getOurTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.GRASSLANDS, dc.getOurTerrainTypeAtHexC());

    }

    @Test
    public void testMakeMessageSecondFunctionality(){
        Decoder dc = new Decoder();
        dc.decodeString("MAKE YOUR MOVE IN GAME P WITHIN 123.45 SECOND: MOVE 20 PLACE LAKE+JUNGLE");
        Assert.assertEquals("P", dc.getGameID());
        Assert.assertEquals(123.45,dc.getTimeToCompleteTurn(),.000001);
        Assert.assertEquals(20,dc.getCurrentMoveNum());
        Assert.assertEquals("LAKE+JUNGLE",dc.getTileTerrainStringOfFormatAandB());

        Assert.assertEquals(terrainTypes.VOLCANO, dc.getOurTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.LAKE, dc.getOurTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.JUNGLE, dc.getOurTerrainTypeAtHexC());
    }

    @Test
    public void testConvertTileStringToTileObjectMethod(){
        Decoder dc = new Decoder();
        dc.convertTileStringToTileObject("GRASS+JUNGLE", 2, false);

        Assert.assertEquals(terrainTypes.GRASSLANDS, dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.VOLCANO, dc.getTheirTerrainTypeAtHexC());
        Assert.assertEquals(terrainTypes.JUNGLE, dc.getTheirTerrainTypeAtHexB());

    }

    @Test
    public void testCoordinateConversion(){
        Decoder dc = new Decoder();
        dc.convertTileCoordinatesFromCubicToOddROffset(1,-4,3);

        Assert.assertEquals(104,dc.getColOddRTileCoordinate());
        Assert.assertEquals(105,dc.getRowOddRTileCoordinate());
    }

    @Test
    public void testCoordinateConversionForTileWithDifferentOrientation(){
        Decoder dc = new Decoder();
        dc.convertTileCoordinatesFromCubicToOddROffset(1,-4,3);

        Assert.assertEquals(104,dc.getColOddRTileCoordinate());
        Assert.assertEquals(105,dc.getRowOddRTileCoordinate());

        Pair pair = dc.convertCoordinatesBasedOnOrientation(104,105, 5);
        Assert.assertEquals(104, pair.getColumnPosition());
        Assert.assertEquals(106, pair.getRowPosition());
        Assert.assertEquals(false, dc.getTheirTileIsFlipped());

    }

    @Test
    public void testGameMessageFoundSettlement(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME POL23 MOVE 12 PLAYER AB PLACED ROCK+GRASS AT 0 0 0 4 FOUNDED SETTLEMENT AT 0 0 0");

        Assert.assertEquals("POL23",dc.getGameID());
        Assert.assertEquals(12, dc.getCurrentMoveNum());
        Assert.assertEquals("AB", dc.getCurrentMovePlayerID());
        Assert.assertEquals("ROCK+GRASS", dc.getTileTerrainStringOfFormatAandB());

        Assert.assertEquals(terrainTypes.VOLCANO, dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.ROCKY, dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.GRASSLANDS, dc.getTheirTerrainTypeAtHexC());

        Assert.assertEquals(102,dc.getColOddRTileCoordinate());
        Assert.assertEquals(102,dc.getRowOddRTileCoordinate());
        Assert.assertEquals(4,dc.getOrientation());
        Assert.assertEquals(true,dc.getTheirTileIsFlipped());

        Assert.assertEquals(BuildType.FOUND_SETTLEMENT,dc.getTheirMoveType());
        Assert.assertEquals(102,dc.getTheirColOddRBuildCoordinate());
        Assert.assertEquals(102,dc.getTheirRowOddRBuildCoordinate());
    }

    @Test
    public void testGameMessageExpandSettlement(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME BRENDAN MOVE 111 PLAYER LO20*IL PLACED JUNGLE+LAKE AT 1 -4 3 4 EXPANDED SETTLEMENT AT 1 -4 3 ROCK");

        Assert.assertEquals("BRENDAN",dc.getGameID());
        Assert.assertEquals(111, dc.getCurrentMoveNum());
        Assert.assertEquals("LO20*IL", dc.getCurrentMovePlayerID());
        Assert.assertEquals("JUNGLE+LAKE", dc.getTileTerrainStringOfFormatAandB());

        Assert.assertEquals(terrainTypes.VOLCANO, dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.JUNGLE, dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.LAKE, dc.getTheirTerrainTypeAtHexC());

        Assert.assertEquals(104,dc.getColOddRTileCoordinate());
        Assert.assertEquals(105,dc.getRowOddRTileCoordinate());
        Assert.assertEquals(4,dc.getOrientation());
        Assert.assertEquals(true,dc.getTheirTileIsFlipped());

        Assert.assertEquals(BuildType.EXPAND_SETTLMENT,dc.getTheirMoveType());
        Assert.assertEquals(104,dc.getTheirColOddRBuildCoordinate());
        Assert.assertEquals(105,dc.getTheirRowOddRBuildCoordinate());
        Assert.assertEquals(terrainTypes.ROCKY, dc.getTheirExpandTerrainTypeIfExpansion());
    }

    @Test
    public void testGameMessageTotoroSanctuary(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME BRENDAN MOVE 111 PLAYER LO20*IL PLACED JUNGLE+LAKE AT 1 -4 3 4 BUILT TOTORO SANCTUARY AT 1 -4 3");

        Assert.assertEquals("BRENDAN",dc.getGameID());
        Assert.assertEquals(111, dc.getCurrentMoveNum());
        Assert.assertEquals("LO20*IL", dc.getCurrentMovePlayerID());
        Assert.assertEquals("JUNGLE+LAKE", dc.getTileTerrainStringOfFormatAandB());

        Assert.assertEquals(terrainTypes.VOLCANO, dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.JUNGLE, dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.LAKE, dc.getTheirTerrainTypeAtHexC());

        Assert.assertEquals(104,dc.getColOddRTileCoordinate());
        Assert.assertEquals(105,dc.getRowOddRTileCoordinate());
        Assert.assertEquals(4,dc.getOrientation());
        Assert.assertEquals(true,dc.getTheirTileIsFlipped());

        Assert.assertEquals(BuildType.PLACE_TOTORO,dc.getTheirMoveType());
        Assert.assertEquals(104,dc.getTheirColOddRBuildCoordinate());
        Assert.assertEquals(105,dc.getTheirRowOddRBuildCoordinate());

    }

    @Test
    public void testGameMessageTigerPlayground(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME BRENDAN MOVE 111 PLAYER LO20*IL PLACED JUNGLE+LAKE AT 1 -4 3 4 BUILT TIGER PLAYGROUND AT 1 -4 3");

        Assert.assertEquals("BRENDAN",dc.getGameID());
        Assert.assertEquals(111, dc.getCurrentMoveNum());
        Assert.assertEquals("LO20*IL", dc.getCurrentMovePlayerID());
        Assert.assertEquals("JUNGLE+LAKE", dc.getTileTerrainStringOfFormatAandB());

        Assert.assertEquals(terrainTypes.VOLCANO, dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.JUNGLE, dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.LAKE, dc.getTheirTerrainTypeAtHexC());

        Assert.assertEquals(104,dc.getColOddRTileCoordinate());
        Assert.assertEquals(105,dc.getRowOddRTileCoordinate());
        Assert.assertEquals(4,dc.getOrientation());
        Assert.assertEquals(true,dc.getTheirTileIsFlipped());

        Assert.assertEquals(BuildType.PLACE_TIGER,dc.getTheirMoveType());
        Assert.assertEquals(104,dc.getTheirColOddRBuildCoordinate());
        Assert.assertEquals(105,dc.getTheirRowOddRBuildCoordinate());

    }

    @Test
    public void testPassingMessagesThatAreNotImportant(){
        Decoder dc = new Decoder();
        dc.decodeString("THANKS FOR PLAYING!");
        dc.decodeString("WELCOME TO ANOTHER EDITION OF THUNDERDOME");
        dc.decodeString("TWO SHALL ENTER, ONE SHALL LEAVE");
        dc.decodeString("THANK YOU FOR PLAYING! GOODBYE");
    }


}
