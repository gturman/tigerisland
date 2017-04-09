import java.io.*;

/**
 * Created by geoff on 4/6/17.
 */

public class Main {

    public static void main(String[] args) throws IOException {

        String hostName, portNumber, tournamentPassword, username, password;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Preparing to enter Thunder Dome...");
        System.out.print("Enter IP: ");
        hostName = stdIn.readLine();
        System.out.print("Port: ");
        portNumber = stdIn.readLine();
        System.out.print("TournamentClient password: ");
        tournamentPassword = stdIn.readLine();
        System.out.print("Username: ");
        username = stdIn.readLine();
        System.out.print("Password: ");
        password = stdIn.readLine();

        TournamentClient ThunderDome = new TournamentClient(hostName, portNumber, tournamentPassword, username, password);

        if (ThunderDome.authentication() == "OK"){

         //   System.out.println("Made it past authentication...");

            boolean challengeDone = false;
            while (!challengeDone){

                //wait for "NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCHES"
                ThunderDome.waitReceiveAndDecode();

                int moveNumber;
                int roundsLeft = ThunderDome.mainDecoder.getNumberOfRounds();
                while (roundsLeft != 0){

                    //wait for "BEGIN ROUND <rid> OF <rounds> "
                    ThunderDome.waitReceiveAndDecode();
                    //wait for "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>"
                    ThunderDome.waitReceiveAndDecode();

                    //gameA associated with AiGameA, and so on
                    boolean gameAon = false;
                    boolean gameBon = false;
                    String gameA = " ";
                    String gameB = " ";
                    AI AiGameA = new AI();
                    AI AiGameB = new AI();
                    Decoder decoderGameA = new Decoder();
                    Decoder decoderGameB = new Decoder();

                    //for constructing a tile for AI to use
                    int tileID, hexID;
                    //for constructing a string to send back to server via client
                    String AiPlaceTile, AiBuild;

                    ////////////////////////////////////////////////////////////////////////////////////
                    //wait for "MAKE YOUR MOVE IN GAME <gid=GameA> WITHIN 1.5 SECONDS: MOVE 1 PLACE <tile>"
                    //ThunderDome.waitReceiveAndDecode();
                    //ThunderDome.client.waitAndReceive();
                    String message = ThunderDome.client.waitAndReceive();
                    decoderGameA.decodeString(message);
                    //construct the tile
                    tileID = AiGameA.gameBoard.getGameBoardTileID();
                    hexID = AiGameA.gameBoard.getGameBoardHexID();
                    terrainTypes hexA = decoderGameA.getOurTerrainTypeAtHexA();
                    terrainTypes hexB = decoderGameA.getOurTerrainTypeAtHexB();
                    terrainTypes hexC = decoderGameA.getOurTerrainTypeAtHexC();
                    Tile givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);

                    //AI makes its placement move with the tile
                    AiPlaceTile = AiGameA.placeForOurPlayer(givenTile);
                    //AI decides what to do for its build move
                    AiBuild = AiGameA.buildForOurPlayer();

                    //construct response message
                    gameAon = true;
                    gameA = decoderGameA.getGameID();
                    moveNumber = decoderGameA.getCurrentMoveNum();

                    // send  GAME gameA MOVE 1 ((PLACE ROCK+JUNGLE AT 0 0 0 1)) ((FOUND SETTLEMENT AT 0 0 0))
                    ThunderDome.send("GAME " + gameA + " MOVE " + moveNumber + " " + AiPlaceTile + " " + AiBuild);

                    //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                    ThunderDome.waitReceiveAndDecode();
                    if (!ThunderDome.mainDecoder.getGameID().equals(gameA)){
                        decoderGameB.decodeString(ThunderDome.currentMessage);

                        //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                        if (!decoderGameB.getTheyForfeitedFlag()) {
                            if (!decoderGameB.getTheyLostFlag()) {

                                gameBon = true;
                                gameB = decoderGameB.getGameID();

                                //build tile
                                hexA = decoderGameB.getTheirTerrainTypeAtHexA();
                                hexB = decoderGameB.getTheirTerrainTypeAtHexB();
                                hexC = decoderGameB.getTheirTerrainTypeAtHexC();
                                givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                            //    System.out.println("decoder B terrain B "+givenTile.getHexB().getTerrainType());
                             //   System.out.println("decoder B terrain C "+givenTile.getHexC().getTerrainType());
                                //build message components
                                int colPos = decoderGameB.getTheirTileColumnPosition();
                                int rowPos = decoderGameB.getTheirTileRowPosition();

                                //AI makes its placement move with the tile
                                AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos, decoderGameB.getTheirTileIsFlipped());

                                //build message components
                                BuildType moveType = decoderGameB.getTheirMoveType();
                                int xCoor = decoderGameB.getTheirColOddRBuildCoordinate();
                                int yCoor = decoderGameB.getTheirRowOddRBuildCoordinate();
                                terrainTypes expandType = decoderGameB.getTheirExpandTerrainTypeIfExpansion();

                                //AI decides what to do for its build move
                                AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                            }
                        }

                        ThunderDome.waitReceiveAndDecode();
                        if (ThunderDome.mainDecoder.getGameOverFlag()){
                            gameBon = false;
                        }

                    } //else we ignore it

                    //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                    ThunderDome.waitReceiveAndDecode();
                    if (!ThunderDome.mainDecoder.getGameID().equals(gameA)){
                        decoderGameB.decodeString(ThunderDome.currentMessage);

                        //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                        if (!decoderGameB.getTheyForfeitedFlag()) {
                            if (!decoderGameB.getTheyLostFlag()) {

                                gameBon = true;
                                gameB = decoderGameB.getGameID();

                                //build tile
                                hexA = decoderGameB.getTheirTerrainTypeAtHexA();
                                hexB = decoderGameB.getTheirTerrainTypeAtHexB();
                                hexC = decoderGameB.getTheirTerrainTypeAtHexC();
                                givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                       //         System.out.println("decoder B terrain B "+givenTile.getHexB().getTerrainType());
                        //        System.out.println("decoder B terrain C "+givenTile.getHexC().getTerrainType());
                                //build message components
                                int colPos = decoderGameB.getTheirTileColumnPosition();
                                int rowPos = decoderGameB.getTheirTileRowPosition();

                                //AI makes its placement move with the tile
                                AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos, decoderGameB.getTheirTileIsFlipped());

                                //build message components
                                BuildType moveType = decoderGameB.getTheirMoveType();
                                int xCoor = decoderGameB.getTheirColOddRBuildCoordinate();
                                int yCoor = decoderGameB.getTheirRowOddRBuildCoordinate();
                                terrainTypes expandType = decoderGameB.getTheirExpandTerrainTypeIfExpansion();

                                //AI decides what to do for its build move
                                AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                            }
                        }

                        ThunderDome.waitReceiveAndDecode();
                        if (ThunderDome.mainDecoder.getGameOverFlag()){
                            gameBon = false;
                        }

                    } //else we ignore it


                    boolean matchDone = false;
                    while (!matchDone){

                        //wait for "MAKE YOUR MOVE IN GAME <gid=GameA> WITHIN 1.5 SECONDS: MOVE 1 PLACE <tile>"
                        ThunderDome.waitReceiveAndDecode();

                        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                        if (gameAon && ThunderDome.mainDecoder.getGameID().equals(gameA)){

                            decoderGameA.decodeString(ThunderDome.currentMessage);

                            //construct the tile
                            tileID = AiGameA.gameBoard.getGameBoardTileID();
                            hexID = AiGameA.gameBoard.getGameBoardHexID();
                            hexA = decoderGameA.getOurTerrainTypeAtHexA();
                            hexB = decoderGameA.getOurTerrainTypeAtHexB();
                            hexC = decoderGameA.getOurTerrainTypeAtHexC();
                            givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);

                            //AI makes its placement move with the tile
                            AiPlaceTile = AiGameA.placeForOurPlayer(givenTile);
                            //AI decides what to do for its build move
                            AiBuild = AiGameA.buildForOurPlayer();

                            //construct response message
                            moveNumber = decoderGameA.getCurrentMoveNum();
                            gameA = ThunderDome.mainDecoder.getGameID();

                            // send  GAME gameA MOVE 1 ((PLACE ROCK+JUNGLE AT 0 0 0 1)) ((FOUND SETTLEMENT AT 0 0 0))
                            ThunderDome.send("GAME " + gameA + " MOVE " + moveNumber + " " + AiPlaceTile + " " + AiBuild);

                            //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.mainDecoder.getGameID().equals(gameA)) {
                                decoderGameB.decodeString(ThunderDome.currentMessage);

                                //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!decoderGameB.getTheyForfeitedFlag()) {
                                    if (!decoderGameB.getTheyLostFlag()) {

                                        gameBon = true;
                                        gameB = decoderGameB.getGameID();

                                        //build tile
                                        hexA = decoderGameB.getTheirTerrainTypeAtHexA();
                                        hexB = decoderGameB.getTheirTerrainTypeAtHexB();
                                        hexC = decoderGameB.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = decoderGameB.getTheirTileColumnPosition();
                                        int rowPos = decoderGameB.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos, decoderGameB.getTheirTileIsFlipped());

                                        //build message components
                                        BuildType moveType = decoderGameB.getTheirMoveType();
                                        int xCoor = decoderGameB.getTheirColOddRBuildCoordinate();
                                        int yCoor = decoderGameB.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = decoderGameB.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                                ThunderDome.waitReceiveAndDecode();
                                if (ThunderDome.mainDecoder.getGameOverFlag()) {
                                    gameBon = false;
                                }
                            }

                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.mainDecoder.getGameID().equals(gameA)) {
                                decoderGameB.decodeString(ThunderDome.currentMessage);

                                //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!decoderGameB.getTheyForfeitedFlag()) {
                                    if (!decoderGameB.getTheyLostFlag()) {

                                        gameBon = true;
                                        gameB = decoderGameB.getGameID();

                                        //build tile
                                        hexA = decoderGameB.getTheirTerrainTypeAtHexA();
                                        hexB = decoderGameB.getTheirTerrainTypeAtHexB();
                                        hexC = decoderGameB.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = decoderGameB.getTheirTileColumnPosition();
                                        int rowPos = decoderGameB.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos,decoderGameB.getTheirTileIsFlipped());

                                        //build message components
                                        BuildType moveType = decoderGameB.getTheirMoveType();
                                        int xCoor = decoderGameB.getTheirColOddRBuildCoordinate();
                                        int yCoor = decoderGameB.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = decoderGameB.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                                ThunderDome.waitReceiveAndDecode();
                                if (ThunderDome.mainDecoder.getGameOverFlag()) {
                                    gameBon = false;
                                }
                            }

                        }//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

                        //BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
                        if (gameBon && ThunderDome.mainDecoder.getGameID().equals(gameB)){

                            decoderGameB.decodeString(ThunderDome.currentMessage);

                            //construct the tile
                            tileID = AiGameB.gameBoard.getGameBoardTileID();
                            hexID = AiGameB.gameBoard.getGameBoardHexID();
                            hexA = decoderGameB.getOurTerrainTypeAtHexA();
                            hexB = decoderGameB.getOurTerrainTypeAtHexB();
                            hexC = decoderGameB.getOurTerrainTypeAtHexC();
                            givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);

                            //AI makes its placement move with the tile
                            AiPlaceTile = AiGameB.placeForOurPlayer(givenTile);
                            //AI decides what to do for its build move
                            AiBuild = AiGameB.buildForOurPlayer();

                            //construct response message
                            moveNumber = decoderGameB.getCurrentMoveNum();
                            gameB = ThunderDome.mainDecoder.getGameID();

                            // send  GAME gameA MOVE 1 ((PLACE ROCK+JUNGLE AT 0 0 0 1)) ((FOUND SETTLEMENT AT 0 0 0))
                            ThunderDome.send("GAME " + gameB + " MOVE " + moveNumber + " " + AiPlaceTile + " " + AiBuild);

                            //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.mainDecoder.getGameID().equals(gameB)) {
                                decoderGameA.decodeString(ThunderDome.currentMessage);

                                //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!decoderGameA.getTheyForfeitedFlag()) {
                                    if (!decoderGameA.getTheyLostFlag()) {

                                        gameAon = true;
                                        gameA = decoderGameA.getGameID();

                                        //build tile
                                        hexA = decoderGameA.getTheirTerrainTypeAtHexA();
                                        hexB = decoderGameA.getTheirTerrainTypeAtHexB();
                                        hexC = decoderGameA.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = decoderGameA.getTheirTileColumnPosition();
                                        int rowPos = decoderGameA.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameA.placeForOtherPlayer(givenTile, colPos, rowPos,decoderGameB.getTheirTileIsFlipped());

                                        //build message components
                                        BuildType moveType = decoderGameA.getTheirMoveType();
                                        int xCoor = decoderGameA.getTheirColOddRBuildCoordinate();
                                        int yCoor = decoderGameA.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = decoderGameA.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameA.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                                ThunderDome.waitReceiveAndDecode();
                                if (ThunderDome.mainDecoder.getGameOverFlag()) {
                                    gameAon = false;
                                }
                            }

                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.mainDecoder.getGameID().equals(gameB)) {
                                decoderGameA.decodeString(ThunderDome.currentMessage);

                                //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!decoderGameA.getTheyForfeitedFlag()) {
                                    if (!decoderGameA.getTheyLostFlag()) {

                                        gameAon = true;
                                        gameA = decoderGameA.getGameID();

                                        //build tile
                                        hexA = decoderGameA.getTheirTerrainTypeAtHexA();
                                        hexB = decoderGameA.getTheirTerrainTypeAtHexB();
                                        hexC = decoderGameA.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = decoderGameA.getTheirTileColumnPosition();
                                        int rowPos = decoderGameA.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameA.placeForOtherPlayer(givenTile, colPos, rowPos,decoderGameB.getTheirTileIsFlipped());

                                        //build message components
                                        BuildType moveType = decoderGameA.getTheirMoveType();
                                        int xCoor = decoderGameA.getTheirColOddRBuildCoordinate();
                                        int yCoor = decoderGameA.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = decoderGameA.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameA.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                                ThunderDome.waitReceiveAndDecode();
                                if (ThunderDome.mainDecoder.getGameOverFlag()) {
                                    gameAon = false;
                                }
                            }

                        }//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB

                        if (gameAon == false && gameBon == false){
                            break;
                        }

                    }

                    //if wait for next match
                    //dont break
                    //if end of matchs, end of round,
                    //break

                }

                //wait for a message from the server
                ThunderDome.waitReceiveAndDecode();
                //if the message is END OF CHALLENGES
                if (ThunderDome.mainDecoder.getEndOfChallenges()) {
                    challengeDone = true;
                    //break;
                }
                //if the message is WAIT FOR THE NEXT CHALLENGE TO BEGIN
                if (ThunderDome.mainDecoder.getWaitingForNextChallengeFlag()) {
                    challengeDone = false;
                }
            }
            // wait for THANK YOU FOR PLAYING! GOODBYE

        }


        System.exit(0);
    }

}
