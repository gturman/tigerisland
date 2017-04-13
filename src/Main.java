import java.io.*;


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

        String newestMessage = "";

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

                // reset game IDs each round
                gameID1 = "";
                gameID2 = "";

                game1IsOver = false; //resetting game 1 over flag for next round
                game2IsOver = false; //resetting game 2 over flag for next round

                //create new decoder each round for each game
          //      decoderForGame1 = new Decoder();
          //      decoderForGame2 = new Decoder();
        //        System.out.println("current before matches before set: "+currentRoundID);
                //"BEGIN ROUND <rid> OF <rounds>" is message 5
                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
             //   currentRoundID = masterDecoder.getCurrentRoundID();

                //"NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>" is message 6
                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                playerID2 = masterDecoder.getPlayerID2();

         //       System.out.println("current before matches: "+currentRoundID);
          //      System.out.println("overall before matches: "+overallRoundID);

                // start round and begin processing messages
         //       System.out.println("got in a round!");
                // while at least one game of the two in a round are proceeding
                while(!(game1IsOver && game2IsOver)) {
           //         System.out.println("we are in one or more matches in a round!");
                    newestMessage = clientForTournament.client.waitAndReceive(); // get message
                    masterDecoder.decodeString(newestMessage); // extract message information for processing

                    if(masterDecoder.getEndOfRoundFlag() == true){ // If for some reason round ends before match ends, immediately break out of this loop
                        break;
                    }

         //           System.out.println("g1 is currently: "+gameID1);
         //           System.out.println("g2 is currently: "+gameID2);
         //           System.out.println("masterGameID = "+masterDecoder.getGameID());

                    // set game IDs for each match in the round; if a game ID is empty, it has not been set yet
                    if(gameID1.isEmpty()) {
                        gameID1 = masterDecoder.getGameID();
                   //     System.out.println("g1 set to: "+gameID1);
                    } else if(gameID2.isEmpty() && !(masterDecoder.getGameID().equals(gameID1))) {
                        gameID2 = masterDecoder.getGameID();
                 //       System.out.println("g2 set to: "+gameID2);
                    }

                    if(masterDecoder.getGameOverFlag()) { // determine if a game is to be ended
                        if(masterDecoder.getGameID().equals(gameID1)) {
            //                System.out.println("g1 lost set");
                            game1IsOver = true;
                        }
                        if(masterDecoder.getGameID().equals(gameID2)) {
               //             System.out.println("g2 lost set");
                            game2IsOver = true;
                        }
                    }

                    // update game - only do for other game if game is still ongoing
                    if(masterDecoder.getCurrentMovePlayerID() != playerID1) { // if move talks about other player ID, it is a status update to update gameboard state
                        if(masterDecoder.getGameID().equals(gameID1)) {
                            if(game1IsOver == false) { // only need to do update if game is not over
                                //update AiGame1 place for other, build for other
                                tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = masterDecoder.getTheirTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getTheirTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getTheirTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                                theirTileColPos = masterDecoder.getTheirTileColumnPosition();
                                theirTileRowPos = masterDecoder.getTheirTileRowPosition();
                                theirBuildType = masterDecoder.getTheirMoveType();
                                theirBuildColPos = masterDecoder.getTheirColOddRBuildCoordinate();
                                theirBuildRowPos = masterDecoder.getTheirRowOddRBuildCoordinate();
                                theirTerrainType = masterDecoder.getTheirExpandTerrainTypeIfExpansion();
                                isTheirTileFlipped = masterDecoder.getTheirTileIsFlipped();

                                aiForGame1.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                                aiForGame1.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                            }
                        }
                        if(masterDecoder.getGameID().equals(gameID2)) {
                            if(game2IsOver == false) { // only need to do update if game is not over
                                //update AiGame2 place for other, build for other
                                tempTileID = aiForGame2.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame2.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = masterDecoder.getTheirTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getTheirTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getTheirTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                                theirTileColPos = masterDecoder.getTheirTileColumnPosition();
                                theirTileRowPos = masterDecoder.getTheirTileRowPosition();
                                theirBuildType = masterDecoder.getTheirMoveType();
                                theirBuildColPos = masterDecoder.getTheirColOddRBuildCoordinate();
                                theirBuildRowPos = masterDecoder.getTheirRowOddRBuildCoordinate();
                                theirTerrainType = masterDecoder.getTheirExpandTerrainTypeIfExpansion();
                                isTheirTileFlipped = masterDecoder.getTheirTileIsFlipped();

                                aiForGame2.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
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

                                tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = masterDecoder.getOurTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getOurTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getOurTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID, tempHexID, tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC);

                                ourPlacementMessage = aiForGame1.placeForOurPlayer(tempTileToPlace);
                                ourBuildMessage = aiForGame1.buildForOurPlayer();

                                //send message
                                clientForTournament.send("GAME " + gameID1 + " MOVE " + currentMoveNumberForGame1 + " " + ourPlacementMessage + " " + ourBuildMessage);
                            }
                        }
                        if(masterDecoder.getGameID().equals(gameID2)) { // make move in game 2
                            if (game2IsOver == false) {// only do move if game is still ongoing
                                //make AIGame2 place for us, build for us
                                currentMoveNumberForGame2 = masterDecoder.getCurrentMoveNum();

                                tempTileID = aiForGame2.gameBoard.getGameBoardTileID();
                                tempHexID = aiForGame2.gameBoard.getGameBoardHexID();
                                tempTerrainTypeHexA = masterDecoder.getOurTerrainTypeAtHexA();
                                tempTerrainTypeHexB = masterDecoder.getOurTerrainTypeAtHexB();
                                tempTerrainTypeHexC = masterDecoder.getOurTerrainTypeAtHexC();

                                tempTileToPlace = new Tile(tempTileID, tempHexID, tempTerrainTypeHexA, tempTerrainTypeHexB, tempTerrainTypeHexC);

                                ourPlacementMessage = aiForGame2.placeForOurPlayer(tempTileToPlace);
                                ourBuildMessage = aiForGame2.buildForOurPlayer();

                                //send message
                                clientForTournament.send("GAME " + gameID2 + " MOVE " + currentMoveNumberForGame2 + " " + ourPlacementMessage + " " + ourBuildMessage);
                            }
                        }
                    }
                }
                // get both game over messages
             //   clientForTournament.client.waitAndReceive();
             //   clientForTournament.client.waitAndReceive();

                //"END OF ROUND <rid> OF <rounds>" or  "END OF ROUND <rid> OF <rounds> WAIT FOR THE NEXT MATCH"
                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                currentRoundID = masterDecoder.getCurrentRoundID(); // get round number; exit loop if it's last round
      //          System.out.println("we escaped the damned match loop!");
            }
            //"END OF CHALLENGES" or "WAIT FOR THE NEXT CHALLENGE TO BEGIN"
            masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
       //     System.out.println("we escaped the damned round loop!");
            if(masterDecoder.getEndOfChallenges() == true)
                challengeIsDone = true;

            currentRoundID = "ZERO"; // reset round ID
        }
        //"THANK YOU FOR PLAYING! GOODBYE"
        clientForTournament.client.waitAndReceive();
        System.exit(0);
    }
}

/*
                masterDecoder.setPlayerID1(playerID1);
                masterDecoder.setPlayerID2(playerID2);

                //"MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE <tile>" is message 7
                //Here we do our first move of every round for 1 singular game
                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());*/
/*
                gameID1 = masterDecoder.getGameID();
                currentMoveNumberForGame1 = masterDecoder.getCurrentMoveNum();

                tempTileID = aiForGame1.gameBoard.getGameBoardTileID();
                tempHexID = aiForGame1.gameBoard.getGameBoardHexID();
                tempTerrainTypeHexA = masterDecoder.getOurTerrainTypeAtHexA();
                tempTerrainTypeHexB = masterDecoder.getOurTerrainTypeAtHexB();
                tempTerrainTypeHexC = masterDecoder.getOurTerrainTypeAtHexC();

                tempTileToPlace = new Tile(tempTileID,tempHexID,tempTerrainTypeHexA,tempTerrainTypeHexB,tempTerrainTypeHexC);

                //OUR FIRST MOVE!
                ourPlacementMessage = aiForGame1.placeForOurPlayer(tempTileToPlace);
                ourBuildMessage = aiForGame1.buildForOurPlayer();

                clientForTournament.send("GAME " + gameID1 + " MOVE " + currentMoveNumberForGame1 + " " + ourPlacementMessage + " " + ourBuildMessage);

                //"GAME <gid> MOVE <#> PLACE <tile> AT <x> <y> <z> <orientation> <build>  is message 8
                //OR
                //GAME FORFEITED OR LOST OR OVER
                //[Get two back to back, one for each game]

                //set game1 and game2 decoders, and get game IDs !!!!!!!

                masterDecoder.setGameOverFlag(false);
                masterDecoder.setTheyForfeitedFlag(false);
                masterDecoder.setTheyLostFlag(false);

                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                if (masterDecoder.getGameID().equals(gameID1)){
                    if(masterDecoder.getGameOverFlag() || masterDecoder.getTheyForfeitedFlag() || masterDecoder.getTheyLostFlag()){
                        game1IsOver = true;
                    }//else {
                        decoderForGame1 = masterDecoder;
                        decoderForGame1.setGameID(gameID1);
                        decoderForGame1.setPlayerID1(playerID1);
                        decoderForGame1.setPlayerID2(playerID2);
                  //  }
                } else {
                    gameID2 = masterDecoder.getGameID();
                    if(masterDecoder.getGameOverFlag() || masterDecoder.getTheyForfeitedFlag() || masterDecoder.getTheyLostFlag()){
                        game2IsOver = true;
                    }//else {
                        decoderForGame2 = masterDecoder;
                        decoderForGame2.setGameID(gameID2);
                        decoderForGame2.setPlayerID1(playerID1);
                        decoderForGame2.setPlayerID2(playerID2);
                  //  }
                }

                masterDecoder.setGameOverFlag(false);
                masterDecoder.setTheyForfeitedFlag(false);
                masterDecoder.setTheyLostFlag(false);

                masterDecoder.decodeString(clientForTournament.client.waitAndReceive());
                if (masterDecoder.getGameID().equals(gameID1)){
                    if(masterDecoder.getGameOverFlag() || masterDecoder.getTheyForfeitedFlag() || masterDecoder.getTheyLostFlag()){
                        game1IsOver = true;
                    }//else {
                        decoderForGame1 = masterDecoder;
                        decoderForGame1.setGameID(gameID1);
                        decoderForGame1.setPlayerID1(playerID1);
                        decoderForGame1.setPlayerID2(playerID2);
                    //}
                } else {
                    gameID2 = masterDecoder.getGameID();
                    if(masterDecoder.getGameOverFlag() || masterDecoder.getTheyForfeitedFlag() || masterDecoder.getTheyLostFlag()){
                        game2IsOver = true;
                    }//else {
                        decoderForGame2 = masterDecoder;
                        decoderForGame2.setGameID(gameID2);
                        decoderForGame2.setPlayerID1(playerID1);
                        decoderForGame2.setPlayerID2(playerID2);
                    //}
                }

                if (!game2IsOver) {
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

                    //THEIR FIRST MOVE!
                    aiForGame2.placeForOtherPlayer(tempTileToPlace,theirTileColPos,theirTileRowPos,isTheirTileFlipped);
                    aiForGame2.buildForOtherPlayer(theirBuildType,theirBuildColPos, theirBuildRowPos, theirTerrainType);
                }

                System.out.println("game 1 over flag is " + game1IsOver);
                System.out.println("game 2 over flag is "+ game2IsOver);
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
                        //if (decoderForGame1.getTheyForfeitedFlag() || decoderForGame1.getTheyLostFlag() ) {
                        if (decoderForGame1.getGameOverFlag() ) {
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

                        }
                    }

                    //we got a game 2 message
                    if (masterDecoder.getGameID().equals(gameID2)) {
                        decoderForGame2.decodeString(message);

                        // we got a forfeit or lost message
                        //if (decoderForGame2.getTheyForfeitedFlag() || decoderForGame2.getTheyLostFlag() ) {
                            if (decoderForGame2.getGameOverFlag() ) {
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

                        }
                    }
                }*/

//"GAME A OVER ..."
//clientForTournament.client.waitAndReceive();
//System.out.println("Should have got: GAME <gid1> OVER...");
//"GAME B OVER ..."
//clientForTournament.client.waitAndReceive();
//System.out.println("Should have got: GAME <gid2> OVER...");
