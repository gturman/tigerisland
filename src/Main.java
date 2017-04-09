
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

            boolean challengeDone = false;
            while (!challengeDone){

                //wait for "NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCHES"
                ThunderDome.waitReceiveAndDecode();

                int moveNumber;
                int roundsLeft = ThunderDome.decode.getNumberOfRounds();
                while (roundsLeft != 0){

                    //wait for "BEGIN ROUND <rid> OF <rounds> "
                    ThunderDome.waitReceiveAndDecode();
                    //wait for "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>"
                    ThunderDome.waitReceiveAndDecode();

                    //gameA associated with AiGameA, and so on
                    String gameA, gameB;
                    AI AiGameA = new AI();
                    AI AiGameB = new AI();

                    //for constructing a tile for AI to use
                    int tileID, hexID;
                    terrainTypes hexA, hexB, hexC;
                    Tile givenTile;

                    //for constructing a string to send back to server via client
                    String AiPlaceTile, AiBuild;

                    ////////////////////////////////////////////////////////////////////////////////////
                    //wait for "MAKE YOUR MOVE IN GAME <gid=GameA> WITHIN 1.5 SECONDS: MOVE 1 PLACE <tile>"
                    ThunderDome.waitReceiveAndDecode();

                    //construct the tile
                    tileID = AiGameA.gameBoard.getGameBoardTileID();
                    hexID = AiGameA.gameBoard.getGameBoardHexID();
                    hexA = ThunderDome.decode.getOurTerrainTypeAtHexA();
                    hexB = ThunderDome.decode.getOurTerrainTypeAtHexB();
                    hexC = ThunderDome.decode.getOurTerrainTypeAtHexC();
                    givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);

                    //AI makes its placement move with the tile
                    AiPlaceTile = AiGameA.placeForOurPlayer(givenTile);
                    //AI decides what to do for its build move
                    AiBuild = AiGameA.buildForOurPlayer();

                    //construct response message
                    moveNumber = 1;
                    gameA = ThunderDome.decode.getGameID();

                    // send  GAME gameA MOVE 1 ((PLACE ROCK+JUNGLE AT 0 0 0 1)) ((FOUND SETTLEMENT AT 0 0 0))
                    ThunderDome.send("GAME " + gameA + " MOVE " + moveNumber + AiPlaceTile + " " + AiBuild);

                    //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                    ThunderDome.waitReceiveAndDecode();
                    if (!ThunderDome.decode.getGameID().equals(gameA)){
                        //if not forfit or lost message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                        if (!ThunderDome.decode.getTheyForfeitedFlag()) {
                            if (!ThunderDome.decode.getTheyLostFlag()) {
                                gameB = ThunderDome.decode.getGameID();

                                //build tile
                                hexA = ThunderDome.decode.getTheirTerrainTypeAtHexA();
                                hexB = ThunderDome.decode.getTheirTerrainTypeAtHexB();
                                hexC = ThunderDome.decode.getTheirTerrainTypeAtHexC();
                                givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                //build message components
                                int colPos = ThunderDome.decode.getTheirTileColumnPosition();
                                int rowPos = ThunderDome.decode.getTheirTileRowPosition();

                                //AI makes its placement move with the tile
                                AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos);

                                //build message components
                                BuildType moveType = ThunderDome.decode.getTheirMoveType();
                                int xCoor = ThunderDome.decode.getTheirColOddRBuildCoordinate();
                                int yCoor = ThunderDome.decode.getTheirRowOddRBuildCoordinate();
                                terrainTypes expandType = ThunderDome.decode.getTheirExpandTerrainTypeIfExpansion();

                                //AI decides what to do for its build move
                                AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                            }
                        }

                    } //else we ignore it

                    //wait for second GAME <gameid> MOVE 1 PLAYER <pid> ...
                    ThunderDome.waitReceiveAndDecode();
                    if (!ThunderDome.decode.getGameID().equals(gameA)){
                        //if not forfit message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                        if (!ThunderDome.decode.getTheyForfeitedFlag()) {
                            if (!ThunderDome.decode.getTheyLostFlag()) {
                                gameB = ThunderDome.decode.getGameID();

                                //build tile
                                hexA = ThunderDome.decode.getTheirTerrainTypeAtHexA();
                                hexB = ThunderDome.decode.getTheirTerrainTypeAtHexB();
                                hexC = ThunderDome.decode.getTheirTerrainTypeAtHexC();
                                givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                //build message components
                                int colPos = ThunderDome.decode.getTheirTileColumnPosition();
                                int rowPos = ThunderDome.decode.getTheirTileRowPosition();

                                //AI makes its placement move with the tile
                                AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos);

                                //build message components
                                BuildType moveType = ThunderDome.decode.getTheirMoveType();
                                int xCoor = ThunderDome.decode.getTheirColOddRBuildCoordinate();
                                int yCoor = ThunderDome.decode.getTheirRowOddRBuildCoordinate();
                                terrainTypes expandType = ThunderDome.decode.getTheirExpandTerrainTypeIfExpansion();

                                //AI decides what to do for its build move
                                AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                            }
                        }

                    } //else we ignore it


                    boolean matchDone = false;
                    while (!matchDone){

                        //wait for "MAKE YOUR MOVE IN GAME <gid=GameA> WITHIN 1.5 SECONDS: MOVE 1 PLACE <tile>"
                        ThunderDome.waitReceiveAndDecode();

                        //if the message is GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
                        if (ThunderDome.decode.getGameOverFlag()) {
                            matchDone = true;
                            break;
                        }

                        if (ThunderDome.decode.getGameID().equals(gameA)){
                            //construct the tile
                            tileID = AiGameA.gameBoard.getGameBoardTileID();
                            hexID = AiGameA.gameBoard.getGameBoardHexID();
                            hexA = ThunderDome.decode.getOurTerrainTypeAtHexA();
                            hexB = ThunderDome.decode.getOurTerrainTypeAtHexB();
                            hexC = ThunderDome.decode.getOurTerrainTypeAtHexC();
                            givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);

                            //AI makes its placement move with the tile
                            AiPlaceTile = AiGameA.placeForOurPlayer(givenTile);
                            //AI decides what to do for its build move
                            AiBuild = AiGameA.buildForOurPlayer();

                            //construct response message
                            moveNumber++;
                            gameA = ThunderDome.decode.getGameID();

                            // send  GAME gameA MOVE 1 ((PLACE ROCK+JUNGLE AT 0 0 0 1)) ((FOUND SETTLEMENT AT 0 0 0))
                            ThunderDome.send("GAME " + gameA + " MOVE " + moveNumber + AiPlaceTile + " " + AiBuild);

                            //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.decode.getGameID().equals(gameA)){
                                //if not forfit message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!ThunderDome.decode.getTheyForfeitedFlag()) {
                                    if (!ThunderDome.decode.getTheyLostFlag()) {
                                        gameB = ThunderDome.decode.getGameID();

                                        //build tile
                                        hexA = ThunderDome.decode.getTheirTerrainTypeAtHexA();
                                        hexB = ThunderDome.decode.getTheirTerrainTypeAtHexB();
                                        hexC = ThunderDome.decode.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = ThunderDome.decode.getTheirTileColumnPosition();
                                        int rowPos = ThunderDome.decode.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos);

                                        //build message components
                                        BuildType moveType = ThunderDome.decode.getTheirMoveType();
                                        int xCoor = ThunderDome.decode.getTheirColOddRBuildCoordinate();
                                        int yCoor = ThunderDome.decode.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = ThunderDome.decode.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                            } //else we ignore it

                            //wait for second GAME <gameid> MOVE 1 PLAYER <pid> ...
                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.decode.getGameID().equals(gameA)){
                                //if not forfit message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!ThunderDome.decode.getTheyForfeitedFlag()) {
                                    if (!ThunderDome.decode.getTheyLostFlag()) {
                                        gameB = ThunderDome.decode.getGameID();

                                        //build tile
                                        hexA = ThunderDome.decode.getTheirTerrainTypeAtHexA();
                                        hexB = ThunderDome.decode.getTheirTerrainTypeAtHexB();
                                        hexC = ThunderDome.decode.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = ThunderDome.decode.getTheirTileColumnPosition();
                                        int rowPos = ThunderDome.decode.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameB.placeForOtherPlayer(givenTile, colPos, rowPos);

                                        //build message components
                                        BuildType moveType = ThunderDome.decode.getTheirMoveType();
                                        int xCoor = ThunderDome.decode.getTheirColOddRBuildCoordinate();
                                        int yCoor = ThunderDome.decode.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = ThunderDome.decode.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameB.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                            } //else we ignore it

                        }

                        if (ThunderDome.decode.getGameID().equals(gameB)){

                            //construct the tile
                            tileID = AiGameB.gameBoard.getGameBoardTileID();
                            hexID = AiGameB.gameBoard.getGameBoardHexID();
                            hexA = ThunderDome.decode.getOurTerrainTypeAtHexA();
                            hexB = ThunderDome.decode.getOurTerrainTypeAtHexB();
                            hexC = ThunderDome.decode.getOurTerrainTypeAtHexC();
                            givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);

                            //AI makes its placement move with the tile
                            AiPlaceTile = AiGameB.placeForOurPlayer(givenTile);
                            //AI decides what to do for its build move
                            AiBuild = AiGameB.buildForOurPlayer();

                            //construct response message
                            moveNumber++;

                            // send  GAME gameA MOVE 1 ((PLACE ROCK+JUNGLE AT 0 0 0 1)) ((FOUND SETTLEMENT AT 0 0 0))
                            ThunderDome.send("GAME " + gameB + " MOVE " + moveNumber + AiPlaceTile + " " + AiBuild);

                            //wait for first GAME <gameid> MOVE 1 PLAYER <pid> ...
                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.decode.getGameID().equals(gameB)){
                                //if not forfit message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0

                                //build tile
                                hexA = ThunderDome.decode.getTheirTerrainTypeAtHexA();
                                hexB = ThunderDome.decode.getTheirTerrainTypeAtHexB();
                                hexC = ThunderDome.decode.getTheirTerrainTypeAtHexC();
                                givenTile = new Tile(tileID,hexID,hexA,hexB,hexC);
                                //build message components
                                int colPos = ThunderDome.decode.getTheirTileColumnPosition();
                                int rowPos = ThunderDome.decode.getTheirTileRowPosition();

                                //AI makes its placement move with the tile
                                AiGameA.placeForOtherPlayer(givenTile,colPos,rowPos);

                                //build message components
                                BuildType moveType = ThunderDome.decode.getTheirMoveType();
                                int xCoor = ThunderDome.decode.getTheirColOddRBuildCoordinate();
                                int yCoor = ThunderDome.decode.getTheirRowOddRBuildCoordinate();
                                terrainTypes expandType = ThunderDome.decode.getTheirExpandTerrainTypeIfExpansion();

                                //AI decides what to do for its build move
                                AiGameA.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);

                            } //else we ignore it

                            //wait for second GAME <gameid> MOVE 1 PLAYER <pid> ...
                            ThunderDome.waitReceiveAndDecode();
                            if (!ThunderDome.decode.getGameID().equals(gameB)){
                                //if not forfit message, PLACED ROCK+JUNGLE AT 0 0 0 1 FOUNDED SETTLEMENT AT 0 0 0
                                if (!ThunderDome.decode.getTheyForfeitedFlag()) {
                                    if (!ThunderDome.decode.getTheyLostFlag()) {

                                        //build tile
                                        hexA = ThunderDome.decode.getTheirTerrainTypeAtHexA();
                                        hexB = ThunderDome.decode.getTheirTerrainTypeAtHexB();
                                        hexC = ThunderDome.decode.getTheirTerrainTypeAtHexC();
                                        givenTile = new Tile(tileID, hexID, hexA, hexB, hexC);
                                        //build message components
                                        int colPos = ThunderDome.decode.getTheirTileColumnPosition();
                                        int rowPos = ThunderDome.decode.getTheirTileRowPosition();

                                        //AI makes its placement move with the tile
                                        AiGameA.placeForOtherPlayer(givenTile, colPos, rowPos);

                                        //build message components
                                        BuildType moveType = ThunderDome.decode.getTheirMoveType();
                                        int xCoor = ThunderDome.decode.getTheirColOddRBuildCoordinate();
                                        int yCoor = ThunderDome.decode.getTheirRowOddRBuildCoordinate();
                                        terrainTypes expandType = ThunderDome.decode.getTheirExpandTerrainTypeIfExpansion();

                                        //AI decides what to do for its build move
                                        AiGameA.buildForOtherPlayer(moveType, xCoor, yCoor, expandType);
                                    }
                                }

                            } //else we ignore it

                        }



                    }

                    //end of match

                }

                //wait for a message from the server
                ThunderDome.waitReceiveAndDecode();
                //if the message is END OF CHALLENGES
                if (ThunderDome.decode.getEndOfChallenges())
                    challengeDone == true;
                    break;
                //if the message is WAIT FOR THE NEXT CHALLENGE TO BEGIN
                if (ThunderDome.decode.getwa
                    challengeDone == false;

            }
            // wait for THANK YOU FOR PLAYING! GOODBYE

        }


        System.exit(0);
    }

}
