import org.junit.Assert;
import org.junit.Test;


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
    
     @Test
    public void testGameForfeitMessages(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME BOB MOVE 1 PLAYER JAMES FORFEITED: ILLEGAL TILE PLACEMENT");
        Assert.assertEquals(dc.getTheyForfeitedFlag(), true);
        dc.decodeString("END OF ROUND 2 OF 24");
        Assert.assertEquals(dc.getTheyForfeitedFlag(), false);
        dc.decodeString("GAME BOB MOVE 2 PLAYER JAMES FORFEITED: ILLEGAL BUILD");
        Assert.assertEquals(dc.getTheyForfeitedFlag(), true);
        dc.decodeString("END OF ROUND 3 OF 24");
        Assert.assertEquals(dc.getTheyForfeitedFlag(), false);
        dc.decodeString("GAME BOB MOVE 3 PLAYER JAMES FORFEITED: TIMEOUT");
        Assert.assertEquals(dc.getTheyForfeitedFlag(), true);
        dc.decodeString("END OF ROUND 14 OF 24");
        Assert.assertEquals(dc.getTheyForfeitedFlag(), false);
    }

    @Test
    public void testGameLostMessage(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME BOB MOVE 1 PLAYER JAMES LOST: UNABLE TO BUILD");
        Assert.assertEquals(dc.getTheyLostFlag(), true);
        dc.decodeString("END OF ROUND 14 OF 24");
        Assert.assertEquals(dc.getTheyLostFlag(), false);
    }

    @Test
    public void testGameOverMessage(){

        Decoder dc = new Decoder();
        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN SPONGEBOB2");
        dc.decodeString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER SQUIDWARD2");

        dc.decodeString("GAME KEVIN OVER PLAYER SPONGEBOB2 269 PLAYER SQUIDWARD2 2");

        Assert.assertEquals(dc.getGameID(), "KEVIN");
        Assert.assertEquals(dc.getPlayerID1(), "SPONGEBOB2");
        Assert.assertEquals(dc.getScoreOfPlayer1(), "269");
        Assert.assertEquals(dc.getPlayerID2(), "SQUIDWARD2");
        Assert.assertEquals(dc.getScoreOfPlayer2(), "2");
        
        dc.decodeString("WAIT FOR THE TOURNAMENT TO BEGIN SQUIDWARD2");
        dc.decodeString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER SPONGEBOB2");

        dc.decodeString("GAME KEVIN OVER PLAYER SPONGEBOB2 29 PLAYER SQUIDWARD2 25");

        Assert.assertEquals(dc.getGameID(), "KEVIN");
        Assert.assertEquals(dc.getPlayerID2(), "SPONGEBOB2");
        Assert.assertEquals(dc.getScoreOfPlayer2(), "29");
        Assert.assertEquals(dc.getPlayerID1(), "SQUIDWARD2");
        Assert.assertEquals(dc.getScoreOfPlayer1(), "25");
    }

    @Test
    public void testSetTheirExpansionTerrainType(){

        Decoder dc = new Decoder();

        dc.setTheirExpansionTerrainType("VOLCANO");
        Assert.assertEquals(terrainTypes.VOLCANO,dc.getTheirExpandTerrainTypeIfExpansion());

        dc.setTheirExpansionTerrainType("GRASS");
        Assert.assertEquals(terrainTypes.GRASSLANDS,dc.getTheirExpandTerrainTypeIfExpansion());

        dc.setTheirExpansionTerrainType("LAKE");
        Assert.assertEquals(terrainTypes.LAKE,dc.getTheirExpandTerrainTypeIfExpansion());

        dc.setTheirExpansionTerrainType("JUNGLE");
        Assert.assertEquals(terrainTypes.JUNGLE,dc.getTheirExpandTerrainTypeIfExpansion());

        dc.setTheirExpansionTerrainType("ROCK");
        Assert.assertEquals(terrainTypes.ROCKY,dc.getTheirExpandTerrainTypeIfExpansion());

    }

    @Test
    public void testConvertTileStringToTileObjectOrientationThree(){
        Decoder dc = new Decoder();

        String temp = dc.convertTileStringToTileObject("ROCK+LAKE",6,true);

        Assert.assertEquals("LAKE VOLCANO ROCK",temp);
    }

    @Test
    public void testThreeHexStrings(){

        Decoder dc = new Decoder();

        String one = "JUNGLE VOLCANO ROCK";
        dc.setTheirTileTerrainTypes(one);

        Assert.assertEquals(terrainTypes.JUNGLE,dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.VOLCANO,dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.ROCKY,dc.getTheirTerrainTypeAtHexC());

        String two = "ROCK LAKE JUNGLE";
        dc.setTheirTileTerrainTypes(two);

        Assert.assertEquals(terrainTypes.ROCKY,dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.LAKE,dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.JUNGLE,dc.getTheirTerrainTypeAtHexC());

        String three = "LAKE GRASS VOLCANO";
        dc.setTheirTileTerrainTypes(three);

        Assert.assertEquals(terrainTypes.LAKE,dc.getTheirTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.GRASSLANDS,dc.getTheirTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.VOLCANO,dc.getTheirTerrainTypeAtHexC());

    }
    
    @Test
    public void testForEvenConvertCoordinatesBasedOnOrientation(){
        Decoder dc = new Decoder();
        Pair p1 = dc.convertCoordinatesBasedOnOrientation(102,102,1);
        Assert.assertEquals(p1.getColumnPosition(),102);
        Assert.assertEquals(p1.getRowPosition(),102);

        p1 = dc.convertCoordinatesBasedOnOrientation(102,102,2);
        Assert.assertEquals(p1.getColumnPosition(),102);
        Assert.assertEquals(p1.getRowPosition(),101);

        p1 = dc.convertCoordinatesBasedOnOrientation(102,102,3);
        Assert.assertEquals(p1.getColumnPosition(),102);
        Assert.assertEquals(p1.getRowPosition(),103);

        p1 = dc.convertCoordinatesBasedOnOrientation(102,102,4);
        Assert.assertEquals(p1.getColumnPosition(),102);
        Assert.assertEquals(p1.getRowPosition(),102);

        p1 = dc.convertCoordinatesBasedOnOrientation(102,102,5);
        Assert.assertEquals(p1.getColumnPosition(),101);
        Assert.assertEquals(p1.getRowPosition(),103);

        p1 =  dc.convertCoordinatesBasedOnOrientation(102,102,6);
        Assert.assertEquals(p1.getColumnPosition(),101);
        Assert.assertEquals(p1.getRowPosition(),101);

    }
    
     @Test
    public void testOddTileOrientation(){

        Decoder dc = new Decoder();

        Pair tester = dc.convertCoordinatesBasedOnOrientation(102,101,1);
        Assert.assertEquals(102,tester.getColumnPosition());
        Assert.assertEquals(101,tester.getRowPosition());

        tester = dc.convertCoordinatesBasedOnOrientation(102,101,2);
        Assert.assertEquals(103,tester.getColumnPosition());
        Assert.assertEquals(100,tester.getRowPosition());

        tester = dc.convertCoordinatesBasedOnOrientation(102,101,3);
        Assert.assertEquals(103,tester.getColumnPosition());
        Assert.assertEquals(102,tester.getRowPosition());

        tester = dc.convertCoordinatesBasedOnOrientation(102,101,6);
        Assert.assertEquals(102,tester.getColumnPosition());
        Assert.assertEquals(100,tester.getRowPosition());

    }

    @Test
    public void testUsSettingOurTerrainTypes(){
        Decoder dc = new Decoder();

        String one = "JUNGLE GRASS VOLCANO";
        dc.setOurTileTerrainTypes(one);
        Assert.assertEquals(terrainTypes.JUNGLE,dc.getOurTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.GRASSLANDS,dc.getOurTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.VOLCANO,dc.getOurTerrainTypeAtHexC());

        one = "ROCK JUNGLE LAKE";
        dc.setOurTileTerrainTypes(one);
        Assert.assertEquals(terrainTypes.ROCKY,dc.getOurTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.JUNGLE,dc.getOurTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.LAKE,dc.getOurTerrainTypeAtHexC());

        one = "GRASS VOLCANO ROCK";
        dc.setOurTileTerrainTypes(one);
        Assert.assertEquals(terrainTypes.GRASSLANDS,dc.getOurTerrainTypeAtHexA());
        Assert.assertEquals(terrainTypes.VOLCANO,dc.getOurTerrainTypeAtHexB());
        Assert.assertEquals(terrainTypes.ROCKY,dc.getOurTerrainTypeAtHexC());
    }

    @Test

    public void testGameTileFlipping(){
        Decoder dc = new Decoder();
        dc.decodeString("GAME POL23 MOVE 12 PLAYER AB PLACED ROCK+GRASS AT 0 0 0 4 FOUNDED SETTLEMENT AT 0 0 0");
        Assert.assertEquals(dc.getTheirTileIsFlipped(), true);

        dc.decodeString("GAME POL23 MOVE 12 PLAYER AB PLACED ROCK+GRASS AT 0 0 0 5 FOUNDED SETTLEMENT AT 0 0 0");
        Assert.assertEquals(dc.getTheirTileIsFlipped(), false);
    }


}
