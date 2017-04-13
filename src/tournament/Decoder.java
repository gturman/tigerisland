package tournament;

import dataStructures.Pair;
import enums.BuildType;
import enums.terrainTypes;

import java.util.Scanner;

public class Decoder {

    private String playerID1;
    private String playerID2;
    private String challengeID;
    private String currentRoundID;
    private String gameID;
    private String currentMovePlayerID;
    private double timeToCompleteTurn;
    private int currentMoveNum;

    private String tileTerrainStringOfFormatAandB; //Not sure how tile is given to us yet

    private String terrainHexAFromMessage = "";
    private String terrainHexBFromMessage = "";
    private String terrainHexCFromMessage = "";

    private terrainTypes theirTerrainTypeAtHexA;
    private terrainTypes theirTerrainTypeAtHexB;
    private terrainTypes theirTerrainTypeAtHexC;
    private int theirTileColumnPosition = 0;
    private int theirTileRowPosition = 0;
    private boolean theirTileIsFlipped = false;

    private terrainTypes ourTerrainTypeAtHexA;
    private terrainTypes ourTerrainTypeAtHexB;
    private terrainTypes ourTerrainTypeAtHexC;

    private int xCubicTileCoordinate;
    private int yCubicTileCoordinate;
    private int zCubicTileCoordinate;
    private int xCubicBuildCoordinate;
    private int yCubicBuildCoordinate;
    private int zCubicBuildCoordinate;

    private String theirExpandedTerrainTypeFromMessage;  // expanded terrain type
    private terrainTypes theirExpandTerrainTypeIfExpansion;

    private String scoreOfPlayer1;
    private String scoreOfPlayer2;
    private String overallRoundID;
    private int orientation;
    private int colOddRTileCoordinate;
    private int rowOddRTileCoordinate;
    private int theirColOddRBuildCoordinate;
    private int theirRowOddRBuildCoordinate;

    private boolean theyForfeitedFlag = false;
    private boolean theyLostFlag = false;
    private boolean endOfRoundFlag = false;
    private boolean endOfChallenges = false;
    private boolean waitingForNextMatchFlag = false;
    private boolean waitingForNextChallengeFlag = false;

    private boolean gameOverFlag = false;

    private BuildType theirMoveType;

    private String terrainOrderingOfTheirTile = "";

    public Decoder() {}

    public void decodeString(String messageFromServer){
        messageTypeDecode(messageFromServer);
    }

    private void messageTypeDecode(String messageFromServer){
        if(messageFromServer.substring(0,4).equals("WAIT")){
            messageStartsWithWait(messageFromServer);
        } else if (messageFromServer.substring(0,3).equals("NEW")){
            messageStartsWithNew(messageFromServer);
        } else if(messageFromServer.substring(0,5).equals("BEGIN")){
            messageStartsWithBegin(messageFromServer);
        } else if(messageFromServer.substring(0,4).equals("MAKE")){
            messageStartsWithMake(messageFromServer);
        } else if(messageFromServer.substring(0,4).equals("GAME")){
            messageStartsWithGame(messageFromServer);
        } else if(messageFromServer.substring(0,3).equals("END")){
            messageStartsWithEnd(messageFromServer);
        }
    }

    public void messageStartsWithWait(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()) {
            currentWord = sc.next();
            if(currentWord.equals("BEGIN") && sc.hasNext()){
                currentWord = sc.next();
                setPlayerID1(currentWord);
            }
            else if (currentWord.equals("BEGIN") && !sc.hasNext()){
                setWaitingForNextChallengeFlag(true);
            }
        }
    }

    public void messageStartsWithNew(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("CHALLENGE")){
                currentWord = sc.next();
                setChallengeID(currentWord);
            }
            else if(currentWord.equals("PLAY")){
                currentWord = sc.next();
                setOverallRoundID(currentWord);
            }
            else if(currentWord.equals("PLAYER")){
                currentWord = sc.next();
                setPlayerID2(currentWord);
            }
        }
    }

    public void messageStartsWithBegin(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("ROUND")){
                currentWord = sc.next();
                setCurrentRoundID(currentWord);
            }
        }
    }

    public void messageStartsWithMake(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("GAME")){
                currentWord=sc.next();
                setGameID(currentWord);
            } else if(currentWord.equals("WITHIN")){
                currentWord = sc.next();
                setTimeToCompleteTurn(Double.parseDouble(currentWord));
            } else if(currentWord.equals("MOVE")){
                currentWord = sc.next();
                if(!currentWord.equals("IN")) {
                    setCurrentMoveNum(Integer.parseInt(currentWord));
                }
            } else if(currentWord.equals("PLACE")){
                currentWord = sc.next();
                setTileTerrainStringOfFormatAandB(currentWord);
                setOrientation(1);
                convertTileStringToTileObject(getTileTerrainStringOfFormatAandB(), getOrientation(), true);
            }
        }
    }

    public void messageStartsWithGame(String messageFromServer){
        String currentWord;

        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();

            if(currentWord.equals("GAME")) {

                setGameID(sc.next());

            }else if(currentWord.equals("OVER")){
                sc.next();
                currentWord = sc.next();

                setGameOverFlag(true);

                if(currentWord.equals(getPlayerID1())){
                    setScoreOfPlayer1(sc.next());
                }else{
                    setScoreOfPlayer2(sc.next());
                }

                sc.next();
                currentWord = sc.next();

                if(currentWord.equals(getPlayerID1())){
                    setScoreOfPlayer1(sc.next());
                }else{
                    setScoreOfPlayer2(sc.next());
                }
            } else if (currentWord.equals("MOVE")) {
                currentWord = sc.next();
                setCurrentMoveNum(Integer.parseInt(currentWord));
            } else if (currentWord.equals("PLAYER")){
                currentWord = sc.next();
                setCurrentMovePlayerID(currentWord);
            } else if(currentWord.equals("PLACED")){

                setTileTerrainStringOfFormatAandB(sc.next());

                sc.next();

                setxCubicTileCoordinate(Integer.parseInt(sc.next()));
                setyCubicTileCoordinate(Integer.parseInt(sc.next()));
                setzCubicTileCoordinate(Integer.parseInt(sc.next()));
                setOrientation(Integer.parseInt(sc.next()));


                setTheirTileColumnPosition(convertCoordinatesBasedOnOrientation(convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getColumnPosition(), convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getRowPosition(),orientation).getColumnPosition());
                setTheirTileRowPosition(convertCoordinatesBasedOnOrientation(convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getColumnPosition(), convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getRowPosition(),orientation).getRowPosition());
                setTerrainOrderingOfTheirTile(convertTileStringToTileObject(tileTerrainStringOfFormatAandB, orientation, false));


                if(getOrientation()%2 == 0){
                    setTheirTileIsFlipped(true);
                }else{
                    setTheirTileIsFlipped(false);
                }

                 //DEBUGGING PURPOSES
                 //  theirPlacementOfTile = gameID + " " + theirTileColumnPosition + " " + theirTileRowPosition + " " + theirTerrainTypeAtHexA + " " + theirTerrainTypeAtHexB + " " + theirTerrainTypeAtHexC + " " + theirTileIsFlipped;
                 // System.out.println(theirPlacementOfTile);
                 //col, row, terrain A, terrain B, terrain C

                currentWord = sc.next();
                if(currentWord.equals("FOUNDED")){
                    sc.next();
                    sc.next();
                    setxCubicBuildCoordinate(Integer.parseInt(sc.next()));
                    setyCubicBuildCoordinate(Integer.parseInt(sc.next()));
                    setzCubicBuildCoordinate(Integer.parseInt(sc.next()));
                    setTheirMoveType(BuildType.FOUND_SETTLEMENT);
                }
                else if(currentWord.equals("EXPANDED")){
                    sc.next();
                    sc.next();
                    setxCubicBuildCoordinate(Integer.parseInt(sc.next()));
                    setyCubicBuildCoordinate(Integer.parseInt(sc.next()));
                    setzCubicBuildCoordinate(Integer.parseInt(sc.next()));
                    setTheirExpandedTerrainTypeFromMessage(sc.next());
                    setTheirExpansionTerrainType(getTheirExpandedTerrainTypeFromMessage());
                    setTheirMoveType(BuildType.EXPAND_SETTLMENT);
                }
                else if(currentWord.equals("BUILT")){
                    currentWord = sc.next();
                    if(currentWord.equals("TOTORO")){
                        sc.next();
                        sc.next();
                        setxCubicBuildCoordinate(Integer.parseInt(sc.next()));
                        setyCubicBuildCoordinate(Integer.parseInt(sc.next()));
                        setzCubicBuildCoordinate(Integer.parseInt(sc.next()));
                        setTheirMoveType(BuildType.PLACE_TOTORO);
                    }
                    else if(currentWord.equals("TIGER")){
                        sc.next();
                        sc.next();
                        setxCubicBuildCoordinate(Integer.parseInt(sc.next()));
                        setyCubicBuildCoordinate(Integer.parseInt(sc.next()));
                        setzCubicBuildCoordinate(Integer.parseInt(sc.next()));
                        setTheirMoveType(BuildType.PLACE_TIGER);
                    }
                }
                convertBuildCoordinatesFromCubicToOddROffset(xCubicBuildCoordinate, yCubicBuildCoordinate, zCubicBuildCoordinate);
            } else if(currentWord.equals("FORFEITED:")){
                setTheyForfeitedFlag(true);
                return;
            }
            else if(currentWord.equals("LOST:")){
                setTheyLostFlag(true);
                return;
            }
        }
    }

    public void setTheirExpansionTerrainType(String theirExpandedTerrainTypeFromMessage){
        if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'V'){
            setTheirExpandTerrainTypeIfExpansion(terrainTypes.VOLCANO);
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'J'){
            setTheirExpandTerrainTypeIfExpansion(terrainTypes.JUNGLE);
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'R'){
            setTheirExpandTerrainTypeIfExpansion(terrainTypes.ROCKY);
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'L'){
            setTheirExpandTerrainTypeIfExpansion(terrainTypes.LAKE);
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'G') {
            setTheirExpandTerrainTypeIfExpansion(terrainTypes.GRASSLANDS);
        }
    }

    public void messageStartsWithEnd(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("ROUND")){
                setEndOfRoundFlag(true);
                setTheyForfeitedFlag(false);
                setTheyLostFlag(false);
            } else if(currentWord.equals("CHALLENGES")){
               setEndOfChallenges(true);
            } else if(currentWord.equals("WAIT")){
                setWaitingForNextMatchFlag(true);
            }
        }
    }

    public String convertTileStringToTileObject(String tile, int orientation, boolean ourTileFlag){
        String terrain1 = "";
        String terrain2 = "";
        String terrainListOrder = "";
        int iterator = 0;

        boolean isPlusSign = false;

        while(iterator < tile.length()){

            if(tile.charAt(iterator) == '+'){
                isPlusSign = true;
                iterator++;
            }

            if(!isPlusSign){
                terrain1 += tile.charAt(iterator);
            }
            else{
                terrain2 += tile.charAt(iterator);
            }

            iterator++;
        }

        if(orientation == 1 || orientation == 4){
            setTerrainHexAFromMessage("VOLCANO");
            setTerrainHexBFromMessage(terrain1);
            setTerrainHexCFromMessage(terrain2);
            terrainListOrder = "VOLCANO" + " " + terrain1 + " " + terrain2;
        }
        else if(orientation == 2 || orientation == 5){
            setTerrainHexAFromMessage(terrain1);
            setTerrainHexBFromMessage(terrain2);
            setTerrainHexCFromMessage("VOLCANO");
            terrainListOrder = terrain1 + " " + terrain2 + " " + "VOLCANO";
        }
        else if(orientation == 3 || orientation == 6){
            setTerrainHexAFromMessage(terrain2);
            setTerrainHexBFromMessage("VOLCANO");
            setTerrainHexCFromMessage(terrain1);
            terrainListOrder = terrain2 + " " + "VOLCANO" + " " + terrain1;
        }

        if(ourTileFlag == false) {
            setTheirTileTerrainTypes(terrainListOrder);
        } else{
            setOurTileTerrainTypes(terrainListOrder);
        }

        return terrainListOrder;

    }

    public void setTheirTileTerrainTypes(String terrainList){

        Scanner sc = new Scanner(terrainList);
        String currentTerrain;

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            setTheirTerrainTypeAtHexA(terrainTypes.VOLCANO);
        } else if(currentTerrain.charAt(0) == 'J'){
            setTheirTerrainTypeAtHexA(terrainTypes.JUNGLE);
        } else if(currentTerrain.charAt(0) == 'R'){
            setTheirTerrainTypeAtHexA(terrainTypes.ROCKY);
        } else if(currentTerrain.charAt(0) == 'L'){
            setTheirTerrainTypeAtHexA(terrainTypes.LAKE);
        } else if(currentTerrain.charAt(0) == 'G') {
            setTheirTerrainTypeAtHexA(terrainTypes.GRASSLANDS);
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            setTheirTerrainTypeAtHexB(terrainTypes.VOLCANO);
        } else if(currentTerrain.charAt(0) == 'J'){
            setTheirTerrainTypeAtHexB(terrainTypes.JUNGLE);
        } else if(currentTerrain.charAt(0) == 'R'){
            setTheirTerrainTypeAtHexB(terrainTypes.ROCKY);
        } else if(currentTerrain.charAt(0) == 'L'){
            setTheirTerrainTypeAtHexB(terrainTypes.LAKE);
        } else if(currentTerrain.charAt(0) == 'G') {
            setTheirTerrainTypeAtHexB(terrainTypes.GRASSLANDS);
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            setTheirTerrainTypeAtHexC(terrainTypes.VOLCANO);
        }
        else if(currentTerrain.charAt(0) == 'J'){
            setTheirTerrainTypeAtHexC(terrainTypes.JUNGLE);
        }
        else if(currentTerrain.charAt(0) == 'R'){
            setTheirTerrainTypeAtHexC(terrainTypes.ROCKY);
        }
        else if(currentTerrain.charAt(0) == 'L'){
            setTheirTerrainTypeAtHexC(terrainTypes.LAKE);
        }
        else if(currentTerrain.charAt(0) == 'G') {
            setTheirTerrainTypeAtHexC(terrainTypes.GRASSLANDS);
        }

    }

    public void setOurTileTerrainTypes(String terrainList){

        Scanner sc = new Scanner(terrainList);
        String currentTerrain;

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            setOurTerrainTypeAtHexA(terrainTypes.VOLCANO);
        } else if(currentTerrain.charAt(0) == 'J'){
            setOurTerrainTypeAtHexA(terrainTypes.JUNGLE);
        } else if(currentTerrain.charAt(0) == 'R'){
            setOurTerrainTypeAtHexA(terrainTypes.ROCKY);
        } else if(currentTerrain.charAt(0) == 'L'){
            setOurTerrainTypeAtHexA(terrainTypes.LAKE);
        } else if(currentTerrain.charAt(0) == 'G') {
            setOurTerrainTypeAtHexA(terrainTypes.GRASSLANDS);
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            setOurTerrainTypeAtHexB(terrainTypes.VOLCANO);
        } else if(currentTerrain.charAt(0) == 'J'){
            setOurTerrainTypeAtHexB(terrainTypes.JUNGLE);
        } else if(currentTerrain.charAt(0) == 'R'){
            setOurTerrainTypeAtHexB(terrainTypes.ROCKY);
        } else if(currentTerrain.charAt(0) == 'L'){
            setOurTerrainTypeAtHexB(terrainTypes.LAKE);
        } else if(currentTerrain.charAt(0) == 'G') {
            setOurTerrainTypeAtHexB(terrainTypes.GRASSLANDS);
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            setOurTerrainTypeAtHexC(terrainTypes.VOLCANO);
        } else if(currentTerrain.charAt(0) == 'J'){
            setOurTerrainTypeAtHexC(terrainTypes.JUNGLE);
        } else if(currentTerrain.charAt(0) == 'R'){
            setOurTerrainTypeAtHexC(terrainTypes.ROCKY);
        } else if(currentTerrain.charAt(0) == 'L'){
            setOurTerrainTypeAtHexC(terrainTypes.LAKE);
        } else if(currentTerrain.charAt(0) == 'G') {
            setOurTerrainTypeAtHexC(terrainTypes.GRASSLANDS);
        }

    }

    public Pair convertTileCoordinatesFromCubicToOddROffset(int x, int y, int z){
        setColOddRTileCoordinate(x + (z-(z&1))/2 + 102);
        setRowOddRTileCoordinate(z + 102);
        Pair pair = new Pair(getColOddRTileCoordinate(), getRowOddRTileCoordinate());
        return pair;
    }

    public Pair convertBuildCoordinatesFromCubicToOddROffset(int x, int y, int z){
        setTheirColOddRBuildCoordinate(x + (z-(z&1))/2 + 102);
        setTheirRowOddRBuildCoordinate(z + 102);
        Pair pair = new Pair(getTheirColOddRBuildCoordinate(), getTheirRowOddRBuildCoordinate());
        return pair;
    }

    public Pair convertCoordinatesBasedOnOrientation(int col, int row, int orientation){
        int newRowPos = 0;
        int newColPos = 0;

        if(row%2 == 0){
            if(orientation==1){
                newRowPos = col;
                newColPos = row;
            }
            else if(orientation==2){
                newRowPos = col;
                newColPos = row - 1;
            }
            else if(orientation == 3){
                newRowPos = col;
                newColPos = row + 1;
            }
            else if(orientation == 4){
                newRowPos = col;
                newColPos = row;
            }
            else if(orientation == 5){
                newRowPos = col - 1;
                newColPos = row + 1;
            }
            else if(orientation == 6){
                newRowPos = col - 1;
                newColPos = row - 1;
            }
        } else{
            if(orientation==1){
                newRowPos = col;
                newColPos = row;
            }
            else if(orientation==2){
                newRowPos = col + 1;
                newColPos = row - 1;
            }
            else if(orientation == 3){
                newRowPos = col + 1;
                newColPos = row + 1;
            }
            else if(orientation == 4){
                newRowPos = col;
                newColPos = row;
            }
            else if(orientation == 5){
                newRowPos = col;
                newColPos = row + 1;
            }
            else if(orientation == 6){
                newRowPos = col;
                newColPos = row - 1;
            }
        }

        Pair coordinates = new Pair(newRowPos,newColPos);
        return coordinates;
    }

    public String getPlayerID1(){
        return this.playerID1;
    }

    public void setPlayerID1(String playerID1){
        this.playerID1 = playerID1;
    }

    public String getPlayerID2(){
        return this.playerID2;
    }

    public void setPlayerID2(String playerID2){
        this.playerID2 = playerID2;
    }

    public String getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(String challengeID){
        this.challengeID = challengeID;
    }

    public String getCurrentRoundID() {
        return this.currentRoundID;
    }

    public void setCurrentRoundID(String currentRoundID){
        this.currentRoundID = currentRoundID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID){
        this.gameID = gameID;
    }

    public String getCurrentMovePlayerID() {
        return currentMovePlayerID;
    }

    public void setCurrentMovePlayerID(String currentMovePlayerID) {
        this.currentMovePlayerID = currentMovePlayerID;
    }

    public BuildType getTheirMoveType() {
        return theirMoveType;
    }

    public void setTheirMoveType(BuildType theirMoveType){
        this.theirMoveType = theirMoveType;
    }

    public double getTimeToCompleteTurn() {
        return this.timeToCompleteTurn;
    }

    public void setTimeToCompleteTurn(double timeToCompleteTurn) {
        this.timeToCompleteTurn = timeToCompleteTurn;
    }

    public int getCurrentMoveNum() {
        return this.currentMoveNum;
    }

    public void setCurrentMoveNum(int currentMoveNum) {
        this.currentMoveNum = currentMoveNum;
    }

    public String getTileTerrainStringOfFormatAandB() {
        return this.tileTerrainStringOfFormatAandB;
    }

    public void setTileTerrainStringOfFormatAandB(String tileTerrainStringOfFormatAandB) {
        this.tileTerrainStringOfFormatAandB = tileTerrainStringOfFormatAandB;
    }

    public String getTerrainHexAFromMessage() {
        return this.terrainHexAFromMessage;
    }

    public void setTerrainHexAFromMessage(String terrainHexAFromMessage) {
        this.terrainHexAFromMessage = terrainHexAFromMessage;
    }

    public String getTerrainHexBFromMessage() {
        return this.terrainHexBFromMessage;
    }

    public void setTerrainHexBFromMessage(String terrainHexBFromMessage) {
        this.terrainHexBFromMessage = terrainHexBFromMessage;
    }

    public String getTerrainHexCFromMessage() {
        return this.terrainHexCFromMessage;
    }

    public void setTerrainHexCFromMessage(String terrainHexCFromMessage) {
        this.terrainHexCFromMessage = terrainHexCFromMessage;
    }

    public terrainTypes getTheirTerrainTypeAtHexA() {
        return this.theirTerrainTypeAtHexA;
    }

    public void setTheirTerrainTypeAtHexA(terrainTypes theirTerrainTypeAtHexA) {
        this.theirTerrainTypeAtHexA = theirTerrainTypeAtHexA;
    }

    public terrainTypes getTheirTerrainTypeAtHexB() {
        return this.theirTerrainTypeAtHexB;
    }

    public void setTheirTerrainTypeAtHexB(terrainTypes theirTerrainTypeAtHexB) {
        this.theirTerrainTypeAtHexB = theirTerrainTypeAtHexB;
    }

    public terrainTypes getTheirTerrainTypeAtHexC() {
        return this.theirTerrainTypeAtHexC;
    }

    public void setTheirTerrainTypeAtHexC(terrainTypes theirTerrainTypeAtHexC) {
        this.theirTerrainTypeAtHexC = theirTerrainTypeAtHexC;
    }

    public int getTheirTileColumnPosition() {
        return this.theirTileColumnPosition;
    }

    public void setTheirTileColumnPosition(int theirTileColumnPosition) {
        this.theirTileColumnPosition = theirTileColumnPosition;
    }

    public int getTheirTileRowPosition() {
        return this.theirTileRowPosition;
    }

    public void setTheirTileRowPosition(int theirTileRowPosition) {
        this.theirTileRowPosition = theirTileRowPosition;
    }

    public boolean getTheirTileIsFlipped() {
        return this.theirTileIsFlipped;
    }

    public void setTheirTileIsFlipped(boolean theirTileIsFlipped) {
        this.theirTileIsFlipped = theirTileIsFlipped;
    }

    public terrainTypes getOurTerrainTypeAtHexA() {
        return this.ourTerrainTypeAtHexA;
    }

    public void setOurTerrainTypeAtHexA(terrainTypes ourTerrainTypeAtHexA) {
        this.ourTerrainTypeAtHexA = ourTerrainTypeAtHexA;
    }

    public terrainTypes getOurTerrainTypeAtHexB() {
        return this.ourTerrainTypeAtHexB;
    }

    public void setOurTerrainTypeAtHexB(terrainTypes ourTerrainTypeAtHexB) {
        this.ourTerrainTypeAtHexB = ourTerrainTypeAtHexB;
    }

    public terrainTypes getOurTerrainTypeAtHexC() {
        return this.ourTerrainTypeAtHexC;
    }

    public void setOurTerrainTypeAtHexC(terrainTypes ourTerrainTypeAtHexC) {
        this.ourTerrainTypeAtHexC = ourTerrainTypeAtHexC;
    }

    public int getxCubicTileCoordinate() {
        return this.xCubicTileCoordinate;
    }

    public void setxCubicTileCoordinate(int xCubicTileCoordinate) {
        this.xCubicTileCoordinate = xCubicTileCoordinate;
    }

    public int getyCubicTileCoordinate() {
        return this.yCubicTileCoordinate;
    }

    public void setyCubicTileCoordinate(int yCubicTileCoordinate) {
        this.yCubicTileCoordinate = yCubicTileCoordinate;
    }

    public int getzCubicTileCoordinate() {
        return this.zCubicTileCoordinate;
    }

    public void setzCubicTileCoordinate(int zCubicTileCoordinate) {
        this.zCubicTileCoordinate = zCubicTileCoordinate;
    }

    public int getxCubicBuildCoordinate() {
        return this.xCubicBuildCoordinate;
    }

    public void setxCubicBuildCoordinate(int xCubicBuildCoordinate) {
        this.xCubicBuildCoordinate = xCubicBuildCoordinate;
    }

    public int getyCubicBuildCoordinate() {
        return this.yCubicBuildCoordinate;
    }

    public void setyCubicBuildCoordinate(int yCubicBuildCoordinate) {
        this.yCubicBuildCoordinate = yCubicBuildCoordinate;
    }

    public int getzCubicBuildCoordinate() {
        return this.zCubicBuildCoordinate;
    }

    public void setzCubicBuildCoordinate(int zCubicBuildCoordinate) {
        this.zCubicBuildCoordinate = zCubicBuildCoordinate;
    }

    public String getTheirExpandedTerrainTypeFromMessage() {
        return this.theirExpandedTerrainTypeFromMessage;
    }

    public void setTheirExpandedTerrainTypeFromMessage(String theirExpandedTerrainTypeFromMessage) {
        this.theirExpandedTerrainTypeFromMessage = theirExpandedTerrainTypeFromMessage;
    }

    public terrainTypes getTheirExpandTerrainTypeIfExpansion() {
        return this.theirExpandTerrainTypeIfExpansion;
    }

    public void setTheirExpandTerrainTypeIfExpansion(terrainTypes theirExpandTerrainTypeIfExpansion) {
        this.theirExpandTerrainTypeIfExpansion = theirExpandTerrainTypeIfExpansion;
    }

    public String getScoreOfPlayer1() {
        return this.scoreOfPlayer1;
    }

    public void setScoreOfPlayer1(String scoreOfPlayer1) {
        this.scoreOfPlayer1 = scoreOfPlayer1;
    }

    public String getScoreOfPlayer2() {
        return this.scoreOfPlayer2;
    }

    public void setScoreOfPlayer2(String scoreOfPlayer2) {
        this.scoreOfPlayer2 = scoreOfPlayer2;
    }

    public String getOverallRoundID() {
        return this.overallRoundID;
    }

    public void setOverallRoundID(String overallRoundID) {
        this.overallRoundID = overallRoundID;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getColOddRTileCoordinate() {
        return this.colOddRTileCoordinate;
    }

    public void setColOddRTileCoordinate(int colOddRTileCoordinate) {
        this.colOddRTileCoordinate = colOddRTileCoordinate;
    }

    public int getRowOddRTileCoordinate() {
        return this.rowOddRTileCoordinate;
    }

    public void setRowOddRTileCoordinate(int rowOddRTileCoordinate) {
        this.rowOddRTileCoordinate = rowOddRTileCoordinate;
    }

    public int getTheirColOddRBuildCoordinate() {
        return this.theirColOddRBuildCoordinate;
    }

    public void setTheirColOddRBuildCoordinate(int theirColOddRBuildCoordinate) {
        this.theirColOddRBuildCoordinate = theirColOddRBuildCoordinate;
    }

    public int getTheirRowOddRBuildCoordinate() {
        return this.theirRowOddRBuildCoordinate;
    }

    public void setTheirRowOddRBuildCoordinate(int theirRowOddRBuildCoordinate) {
        this.theirRowOddRBuildCoordinate = theirRowOddRBuildCoordinate;
    }

    public boolean getTheyForfeitedFlag() {
        return this.theyForfeitedFlag;
    }

    public void setTheyForfeitedFlag(boolean theyForfeitedFlag) {
        this.theyForfeitedFlag = theyForfeitedFlag;
    }

    public boolean getTheyLostFlag() {
        return this.theyLostFlag;
    }

    public void setTheyLostFlag(boolean theyLostFlag) {
        this.theyLostFlag = theyLostFlag;
    }

    public boolean getEndOfRoundFlag() {
        return this.endOfRoundFlag;
    }

    public void setEndOfRoundFlag(boolean endOfRoundFlag) {
        this.endOfRoundFlag = endOfRoundFlag;
    }

    public boolean getEndOfChallenges() {
        return this.endOfChallenges;
    }

    public void setEndOfChallenges(boolean endOfChallenges) {
        this.endOfChallenges = endOfChallenges;
    }

    public boolean getWaitingForNextMatchFlag() {
        return this.waitingForNextMatchFlag;
    }

    public void setWaitingForNextMatchFlag(boolean waitingForNextMatchFlag) {
        this.waitingForNextMatchFlag = waitingForNextMatchFlag;
    }

    public String getTerrainOrderingOfTheirTile() {
        return this.terrainOrderingOfTheirTile;
    }

    public void setTerrainOrderingOfTheirTile(String terrainOrderingOfTheirTile) {
        this.terrainOrderingOfTheirTile = terrainOrderingOfTheirTile;
    }

    public boolean getGameOverFlag() {
        return this.gameOverFlag;
    }

    public void setGameOverFlag(boolean gameOverFlag) {
        this.gameOverFlag = gameOverFlag;
    }

    public boolean getWaitingForNextChallengeFlag() {
        return this.waitingForNextChallengeFlag;
    }

    public void setWaitingForNextChallengeFlag(boolean waitingForNextChallengeFlag) {
        this.waitingForNextChallengeFlag = waitingForNextChallengeFlag;
    }
}
