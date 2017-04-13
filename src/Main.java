import enums.BuildType;
import enums.terrainTypes;
import tournament.AI;
import tournament.Decoder;
import tournament.TournamentClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{

        String hostName, portNumber, tournamentPassword, username, password;
        boolean challengeIsDone = false;
        String overallRoundID;
        String currentRoundID = "ZERO";
        String playerID1;
        String playerID2;
        AI aiForGame1;
        AI aiForGame2;
        String gameID1;
        String gameID2;
        Decoder masterDecoder;
        boolean game1IsOver;
        boolean game2IsOver;
        terrainTypes tempTerrainTypeHexA;
        terrainTypes tempTerrainTypeHexB;
        terrainTypes tempTerrainTypeHexC;
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
        String newestMessage;

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Preparing to enter Thunder Dome...");
        System.out.print("Enter IP: ");
        hostName = stdIn.readLine();
        System.out.print("Port: ");
        portNumber = stdIn.readLine();
        System.out.print("tournament.TournamentClient password: ");
        tournamentPassword = stdIn.readLine();
        System.out.print("Username: ");
        username = stdIn.readLine();
        System.out.print("Password: ");
        password = stdIn.readLine();

        TournamentClient clientForTournament = new TournamentClient(hostName, portNumber, tournamentPassword, username, password);

        playerID1 = clientForTournament.authenticateConnectionAndGetPlayerID();

        //while we still have challenges to play
        while(!challengeIsDone){
            masterDecoder = new Decoder(); // need new decoder to reset flags
            //"NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCHES" is message 4
            masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
            overallRoundID = masterDecoder.getOverallRoundID();

            //while we still have rounds to play
            while(!currentRoundID.equals(overallRoundID)){
                // instantiate new AIs each round of each game
                aiForGame1 = new AI();
                aiForGame2 = new AI();

                // reset game IDs for each round
                gameID1 = "";
                gameID2 = "";

                // reset game over flags for each round
                game1IsOver = false;
                game2IsOver = false;

                //System.out.println("current before matches before set: "+currentRoundID);
                //"BEGIN ROUND <rid> OF <rounds>" is message 5
                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                //currentRoundID = masterDecoder.getCurrentRoundID();

                //"NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>" is message 6
                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                playerID2 = masterDecoder.getPlayerID2();

                //System.out.println("current before matches: "+currentRoundID);
                //System.out.println("overall before matches: "+overallRoundID);

                // start round and begin processing messages
                //System.out.println("got in a round!");
                // while at least one game of the two in a round are proceeding
                while(!(game1IsOver && game2IsOver)) {
                //System.out.println("we are in one or more matches in a round!");
                    newestMessage = clientForTournament.client.waitAndReceive(); // get message
                    masterDecoder.decodeString(newestMessage); // extract message information for processing

                    if(masterDecoder.getEndOfRoundFlag() == true){ // If for some reason round ends before match ends, immediately break out of this loop
                        break;
                    }

                    //System.out.println("g1 is currently: "+gameID1);
                    //System.out.println("g2 is currently: "+gameID2);
                    //System.out.println("masterGameID = "+masterDecoder.getGameID());

                    // set game IDs for each match in the round; if a game ID is empty, it has not been set yet
                    if(gameID1.isEmpty()) {
                        gameID1 = masterDecoder.getGameID();
                        //System.out.println("g1 set to: "+gameID1);
                    } else if(gameID2.isEmpty() && !(masterDecoder.getGameID().equals(gameID1))) {
                        gameID2 = masterDecoder.getGameID();
                        //System.out.println("g2 set to: "+gameID2);
                    }

                    if(masterDecoder.getGameOverFlag()) { // determine if a game is to be ended
                        if(masterDecoder.getGameID().equals(gameID1)) {
                        //System.out.println("g1 lost set");
                            game1IsOver = true;
                        }
                        if(masterDecoder.getGameID().equals(gameID2)) {
                        //System.out.println("g2 lost set");
                            game2IsOver = true;
                        }
                    }

                    // update game - only do for other game if game is still ongoing
                    if(masterDecoder.getCurrentMovePlayerID() != playerID1) { // if move talks about other player ID, it is a status update to update gameboard state
                        if(masterDecoder.getGameID().equals(gameID1)) {
                            if(game1IsOver == false) { // only need to do update if game is not over
                                //update AiGame1 place for other, build for other
                                tempTerrainTypeHexA = masterDecoder.getTheirTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getTheirTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getTheirTerrainTypeAtHexC();

                                theirTileColPos = masterDecoder.getTheirTileColumnPosition();
                                theirTileRowPos = masterDecoder.getTheirTileRowPosition();
                                theirBuildType = masterDecoder.getTheirMoveType();
                                theirBuildColPos = masterDecoder.getTheirColOddRBuildCoordinate();
                                theirBuildRowPos = masterDecoder.getTheirRowOddRBuildCoordinate();
                                theirTerrainType = masterDecoder.getTheirExpandTerrainTypeIfExpansion();
                                isTheirTileFlipped = masterDecoder.getTheirTileIsFlipped();

                                aiForGame1.placeForOtherPlayer(tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                                aiForGame1.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                            }
                        }
                        if(masterDecoder.getGameID().equals(gameID2)) {
                            if(game2IsOver == false) { // only need to do update if game is not over
                                //update AiGame2 place for other, build for other
                                tempTerrainTypeHexA = masterDecoder.getTheirTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getTheirTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getTheirTerrainTypeAtHexC();

                                theirTileColPos = masterDecoder.getTheirTileColumnPosition();
                                theirTileRowPos = masterDecoder.getTheirTileRowPosition();
                                theirBuildType = masterDecoder.getTheirMoveType();
                                theirBuildColPos = masterDecoder.getTheirColOddRBuildCoordinate();
                                theirBuildRowPos = masterDecoder.getTheirRowOddRBuildCoordinate();
                                theirTerrainType = masterDecoder.getTheirExpandTerrainTypeIfExpansion();
                                isTheirTileFlipped = masterDecoder.getTheirTileIsFlipped();

                                aiForGame2.placeForOtherPlayer(tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                                aiForGame2.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                            }
                        }
                    }

                    // make your move
                    if (newestMessage.substring(0,4).equals("MAKE")) {
                        if(masterDecoder.getGameID().equals(gameID1)) { // make move in game 1
                            if(game1IsOver == false) {// only do move if game is still ongoing
                                //make AIGame1 place for us, build for us
                                currentMoveNumberForGame1 = masterDecoder.getCurrentMoveNum();

                                tempTerrainTypeHexA = masterDecoder.getOurTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getOurTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getOurTerrainTypeAtHexC();

                                ourPlacementMessage = aiForGame1.placeForOurPlayer(tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC);
                                ourBuildMessage = aiForGame1.buildForOurPlayer();

                                //send message
                                clientForTournament.send("GAME " + gameID1 + " MOVE " + currentMoveNumberForGame1 + " " + ourPlacementMessage + " " + ourBuildMessage);
                            }
                        }
                        if(masterDecoder.getGameID().equals(gameID2)) { // make move in game 2
                            if (game2IsOver == false) {// only do move if game is still ongoing
                                //make AIGame2 place for us, build for us
                                currentMoveNumberForGame2 = masterDecoder.getCurrentMoveNum();

                                tempTerrainTypeHexA = masterDecoder.getOurTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getOurTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getOurTerrainTypeAtHexC();

                                ourPlacementMessage = aiForGame2.placeForOurPlayer(tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC);
                                ourBuildMessage = aiForGame2.buildForOurPlayer();

                                //send message
                                clientForTournament.send("GAME " + gameID2 + " MOVE " + currentMoveNumberForGame2 + " " + ourPlacementMessage + " " + ourBuildMessage);
                            }
                        }
                    }
                }
                // get both game over messages
                //clientForTournament.client.waitAndReceive();
                //clientForTournament.client.waitAndReceive();

                if(masterDecoder.getEndOfRoundFlag() == false){
                    //"END OF ROUND <rid> OF <rounds>" or  "END OF ROUND <rid> OF <rounds> WAIT FOR THE NEXT MATCH"
                    masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                    currentRoundID = masterDecoder.getCurrentRoundID(); // get round number; exit loop if it's last round
                }
                //System.out.println("we escaped the damned match loop!");
            }
            //"END OF CHALLENGES" or "WAIT FOR THE NEXT CHALLENGE TO BEGIN"
            masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
            //System.out.println("we escaped the damned round loop!");
            if(masterDecoder.getEndOfChallenges() == true)
                challengeIsDone = true;

            currentRoundID = "ZERO"; // reset round ID
        }
        //"THANK YOU FOR PLAYING! GOODBYE"
        clientForTournament.client.waitAndReceive();
        System.exit(0);
    }
}