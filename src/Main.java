import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException{

        String hostName, portNumber, tournamentPassword, username, password;
        boolean challengeIsDone = false;
        String overallRoundID;
        String currentRoundID = "";
        String playerID1;
        String playerID2;
        AI aiForGame1;
        AI aiForGame2;
        String gameID1;
        String gameID2;
        Decoder masterDecoder;
        Decoder decoderForGame1;
        Decoder decoderForGame2;
        boolean game1IsOver = false;
        boolean game2IsOver = false;
        terrainTypes tempTerrainTypeHexA;
        terrainTypes tempTerrainTypeHexB;
        terrainTypes tempTerrainTypeHexC;
        int tempTileID;
        int tempHexID;
        Tile tempTileToPlace;
        String ourPlacementMessage;
        String ourBuildMessage;
        int currentMoveNumberForGame1;
        int currentMoveNumberForGame2;
        int theirTileColPos;
        int theirTileRowPos;
        BuildType theirBuildType;
        terrainTypes theirTerrainType;
        boolean isTheirTileFlipped;
        int theirBuildColPos;
        int theirBuildRowPos;
        String lastGameWePlayedOn;

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

        TournamentClient clientForTournament = new TournamentClient(hostName, portNumber, tournamentPassword, username, password);

        //Creates main decoder and runs through first three messages
        if (clientForTournament.isAuthenticated()){
            playerID1 = clientForTournament.mainDecoder.getPlayerID1();

            //while we still have challenges to play
            while(!challengeIsDone){
                //"NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCHES" is message 4
                clientForTournament.waitReceiveAndDecode();

                overallRoundID = clientForTournament.mainDecoder.getOverallRoundID();
                //while we still have rounds to play
                while(!currentRoundID.equals(overallRoundID)){
                    //create new AI each round for each game
                    aiForGame1 = new AI();
                    aiForGame2 = new AI();
                    //"BEGIN ROUND <rid> OF <rounds>" is message 5
                    clientForTournament.waitReceiveAndDecode();
                    currentRoundID = clientForTournament.mainDecoder.getCurrentRoundID();
                    //"NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>" is message 6
                    clientForTournament.waitReceiveAndDecode();
                    playerID2 = clientForTournament.mainDecoder.getPlayerID2();

                    //create new decoder each round for each game
                    masterDecoder = new Decoder();
                    decoderForGame1 = new Decoder();
                    decoderForGame2 = new Decoder();

                    masterDecoder.setPlayerID1(playerID1);
                    masterDecoder.setPlayerID2(playerID2);
                    decoderForGame1.setPlayerID1(playerID1);
                    decoderForGame1.setPlayerID2(playerID2);
                    decoderForGame2.setPlayerID1(playerID1);
                    decoderForGame2.setPlayerID2(playerID2);

                    //"MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE <tile>" is message 7
                    //Here we do our first move of every round for 1 singular game
                    clientForTournament.waitReceiveAndDecode();

                    gameID1 = clientForTournament.mainDecoder.getGameID();
                    lastGameWePlayedOn = gameID1;
                    currentMoveNumberForGame1 = clientForTournament.mainDecoder.getCurrentMoveNum();

                    tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                    tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                    tempTerrainTypeHexA = clientForTournament.mainDecoder.getOurTerrainTypeAtHexA();
                    tempTerrainTypeHexB = clientForTournament.mainDecoder.getOurTerrainTypeAtHexB();
                    tempTerrainTypeHexC = clientForTournament.mainDecoder.getOurTerrainTypeAtHexC();

                    tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                    ourPlacementMessage = aiForGame1.placeForOurPlayer(tempTileToPlace);
                    ourBuildMessage = aiForGame1.buildForOurPlayer();

                    clientForTournament.send("GAME " + gameID1 + " MOVE " + currentMoveNumberForGame1 + " " + ourPlacementMessage + " " + ourBuildMessage);

                    //"GAME <gid> MOVE <#> PLACE <tile> AT <x> <y> <z> <orientation> <build>  is message 8
                    //OR
                    //GAME FORFEITED OR LOST OR OVER
                    //[Get two back to back, one for each game]

                    decoderForGame1.decodeString(clientForTournament.client.waitAndReceive());
                    decoderForGame2.decodeString(clientForTournament.client.waitAndReceive());

                    gameID2=decoderForGame2.getGameID();

                    //If the game we JUST played above has ended, set to end. We do not need to do anything otherwise.
                    //This huge If statement correctly determines the order the server sends the messages based upon gameID
                    //and assigns the other players move to the correct AI.
                    if(gameID1.equals(decoderForGame1.getGameID())){
                        if(decoderForGame1.getGameOverFlag() || decoderForGame1.getTheyForfeitedFlag() || decoderForGame1.getTheyLostFlag()){
                            game1IsOver = true;
                        }

                        if(decoderForGame2.getGameOverFlag() || decoderForGame2.getTheyForfeitedFlag() || decoderForGame2.getTheyLostFlag()){
                            game2IsOver = true;
                        }else{
                            //make their move;
                            
                            currentMoveNumberForGame2 = decoderForGame2.getCurrentMoveNum();

                            tempTileID = aiForGame2.gameBoard.getGameBoardTileID();
                            tempHexID = aiForGame2.gameBoard.getGameBoardHexID();
                            tempTerrainTypeHexA = decoderForGame2.getTheirTerrainTypeAtHexA();
                            tempTerrainTypeHexB = decoderForGame2.getTheirTerrainTypeAtHexB();
                            tempTerrainTypeHexC = decoderForGame2.getTheirTerrainTypeAtHexC();

                            tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                            theirTileColPos = decoderForGame2.getTheirTileColumnPosition();
                            theirTileRowPos = decoderForGame2.getTheirTileRowPosition();
                            theirBuildType = decoderForGame2.getTheirMoveType();
                            theirBuildColPos = decoderForGame2.getTheirColOddRBuildCoordinate();
                            theirBuildRowPos = decoderForGame2.getTheirRowOddRBuildCoordinate();
                            theirTerrainType = decoderForGame2.getTheirExpandTerrainTypeIfExpansion();
                            isTheirTileFlipped = decoderForGame2.getTheirTileIsFlipped();

                            aiForGame2.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                            aiForGame2.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                        }
                    }else{
                        if(decoderForGame2.getGameOverFlag() || decoderForGame2.getTheyForfeitedFlag() || decoderForGame2.getTheyLostFlag()){
                            game2IsOver = true;
                        }

                        if(decoderForGame1.getGameOverFlag() || decoderForGame1.getTheyForfeitedFlag() || decoderForGame1.getTheyLostFlag()){
                            game1IsOver = true;
                        }else{
                            //make their move;
                            
                            currentMoveNumberForGame1 = decoderForGame1.getCurrentMoveNum();

                            tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                            tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                            tempTerrainTypeHexA = decoderForGame1.getTheirTerrainTypeAtHexA();
                            tempTerrainTypeHexB = decoderForGame1.getTheirTerrainTypeAtHexB();
                            tempTerrainTypeHexC = decoderForGame1.getTheirTerrainTypeAtHexC();

                            tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                            theirTileColPos = decoderForGame1.getTheirTileColumnPosition();
                            theirTileRowPos = decoderForGame1.getTheirTileRowPosition();
                            theirBuildType = decoderForGame1.getTheirMoveType();
                            theirBuildColPos = decoderForGame1.getTheirColOddRBuildCoordinate();
                            theirBuildRowPos = decoderForGame1.getTheirRowOddRBuildCoordinate();
                            theirTerrainType = decoderForGame1.getTheirExpandTerrainTypeIfExpansion();
                            isTheirTileFlipped = decoderForGame1.getTheirTileIsFlipped();

                            aiForGame2.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                            aiForGame2.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                        }
                    }

                    //After placing first move and their first move, if both games haven't ended due to a
                    //forfeit, lost, or game over message, enter while loop to process proceeding moves
                    while(!(game1IsOver && game2IsOver)) {
                        //can be move type, update type (update, forfeit, or lost), or game over type
                        String message = clientForTournament.client.waitAndReceive();
                        masterDecoder.decodeString(message);

                        //we got a game1 message
                        if (masterDecoder.getGameID().equals(gameID1)) {
                            decoderForGame1.decodeString(message);

                            // we got a forfeit or lost message
                            if (decoderForGame1.getTheyForfeitedFlag() || decoderForGame1.getTheyLostFlag() ) {
                                if (decoderForGame1.getGameID().equals(gameID1)) {
                                    game1IsOver = true;
                                }
                            }

                            //we got an update message for other player
                            if (decoderForGame1.getPlayerID2().equals(playerID2) && game1IsOver == false){

                                //update AiGame1 place for other, build for other
                                tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = decoderForGame1.getTheirTerrainTypeAtHexA();
                                tempTerrainTypeHexB = decoderForGame1.getTheirTerrainTypeAtHexB();
                                tempTerrainTypeHexC = decoderForGame1.getTheirTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                                theirTileColPos = decoderForGame1.getTheirTileColumnPosition();
                                theirTileRowPos = decoderForGame1.getTheirTileRowPosition();
                                theirBuildType = decoderForGame1.getTheirMoveType();
                                theirBuildColPos = decoderForGame1.getTheirColOddRBuildCoordinate();
                                theirBuildRowPos = decoderForGame1.getTheirRowOddRBuildCoordinate();
                                theirTerrainType = decoderForGame1.getTheirExpandTerrainTypeIfExpansion();
                                isTheirTileFlipped = decoderForGame1.getTheirTileIsFlipped();

                                aiForGame1.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                                aiForGame1.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                            }

                            //we got a make your move message
                            if (message.substring(0,4).equals("MAKE") && game1IsOver == false){
                                //make AIGame1 place for us, build for us
                                currentMoveNumberForGame1 = decoderForGame1.getCurrentMoveNum();

                                tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = decoderForGame1.getOurTerrainTypeAtHexA();
                                tempTerrainTypeHexB = decoderForGame1.getOurTerrainTypeAtHexB();
                                tempTerrainTypeHexC = decoderForGame1.getOurTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID, tempHexID, tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC);

                                ourPlacementMessage = aiForGame1.placeForOurPlayer(tempTileToPlace);
                                ourBuildMessage = aiForGame1.buildForOurPlayer();

                                //send message
                                clientForTournament.send("GAME " + gameID1 + " MOVE " + currentMoveNumberForGame1 + " " + ourPlacementMessage + " " + ourBuildMessage);

                                lastGameWePlayedOn = gameID1;
                            }
                        }

                        //we got a game 2 message
                        if (masterDecoder.getGameID().equals(gameID2)) {
                            decoderForGame2.decodeString(message);

                            // we got a forfeit or lost message
                            if (decoderForGame2.getTheyForfeitedFlag() || decoderForGame2.getTheyLostFlag() ) {
                                if (decoderForGame2.getGameID().equals(gameID2)) {
                                    game2IsOver = true;
                                }
                            }

                            //we got an update message for other player
                            if (decoderForGame2.getPlayerID2().equals(playerID2) && game2IsOver == false){
                                //update AiGame1 place for other, build for other

                                tempTileID = aiForGame2.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame2.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = decoderForGame2.getTheirTerrainTypeAtHexA();
                                tempTerrainTypeHexB = decoderForGame2.getTheirTerrainTypeAtHexB();
                                tempTerrainTypeHexC = decoderForGame2.getTheirTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                                theirTileColPos = decoderForGame2.getTheirTileColumnPosition();
                                theirTileRowPos = decoderForGame2.getTheirTileRowPosition();
                                theirBuildType = decoderForGame2.getTheirMoveType();
                                theirBuildColPos = decoderForGame2.getTheirColOddRBuildCoordinate();
                                theirBuildRowPos = decoderForGame2.getTheirRowOddRBuildCoordinate();
                                theirTerrainType = decoderForGame2.getTheirExpandTerrainTypeIfExpansion();
                                isTheirTileFlipped = decoderForGame2.getTheirTileIsFlipped();

                                aiForGame2.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                                aiForGame2.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                            }

                            //we got a make your move message
                            if (message.substring(0,4).equals("MAKE")  && game2IsOver == false){
                                //make AIGame1 place for us, build for us
                                currentMoveNumberForGame2 = decoderForGame2.getCurrentMoveNum();

                                tempTileID = aiForGame2.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame2.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = decoderForGame2.getOurTerrainTypeAtHexA();
                                tempTerrainTypeHexB = decoderForGame2.getOurTerrainTypeAtHexB();
                                tempTerrainTypeHexC = decoderForGame2.getOurTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID, tempHexID, tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC);

                                ourPlacementMessage = aiForGame2.placeForOurPlayer(tempTileToPlace);
                                ourBuildMessage = aiForGame2.buildForOurPlayer();

                                //send message
                                clientForTournament.send("GAME " + gameID2 + " MOVE " + currentMoveNumberForGame2 + " " + ourPlacementMessage + " " + ourBuildMessage);

                                lastGameWePlayedOn = gameID2;
                            }
                        }
                    }
                    //"GAME A OVER ..."
                    clientForTournament.client.waitAndReceive();
                    //"GAME B OVER ..."
                    clientForTournament.client.waitAndReceive();
                    //"END OF ROUND <rid> OF <rounds>" or  "END OF ROUND <rid> OF <rounds> WAIT FOR THE NEXT MATCH"
                    clientForTournament.waitReceiveAndDecode();
                    currentRoundID = clientForTournament.mainDecoder.getCurrentRoundID(); //incrementing round
                    game1IsOver = false; //reseting
                    game2IsOver = false; //reseting
                }
                //"END OF CHALLENGES" or "WAIT FOR THE NEXT CHALLENGE TO BEGIN"
                clientForTournament.waitReceiveAndDecode();

                if(clientForTournament.mainDecoder.getEndOfChallenges())
                    challengeIsDone = true;
            }
            //"THANK YOU FOR PLAYING! GOODBYE"
            clientForTournament.waitReceiveAndDecode();
            System.exit(0);
        }
        System.exit(0);
    }
}
