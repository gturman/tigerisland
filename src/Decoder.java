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

    private int scoreOfPlayer1;
    private int scoreOfPlayer2;
    private int numberOfRounds;
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

    private BuildType theirMoveType;

    private String terrainOrderingOfTheirTile = "";

    Decoder() {

    }

    void decodeString(String messageFromServer){

        messageTypeDecode(messageFromServer);

    }

    void messageTypeDecode(String messageFromServer){

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

    void messageStartsWithWait(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()) {
            currentWord = sc.next();
            if(currentWord.equals("BEGIN") && sc.hasNext()){
                currentWord = sc.next();
                setPlayerID1(currentWord);
            }
        }
    }

    void messageStartsWithNew(String messageFromServer){
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
                setNumberOfRounds(Integer.parseInt(currentWord));
            }
            else if(currentWord.equals("PLAYER")){
                currentWord = sc.next();
                setPlayerID2(currentWord);
            }
        }
    }

    void messageStartsWithBegin(String messageFromServer){
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

    void messageStartsWithMake(String messageFromServer){
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

    void messageStartsWithGame(String messageFromServer){
        String currentWord;

        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();

            if(currentWord.equals("GAME")) {

                setGameID(sc.next());

            }else if(currentWord.equals("OVER")){
                sc.next();
                currentWord = sc.next();

                if(currentWord.equals(getPlayerID1())){
                    setScoreOfPlayer1(Integer.parseInt(sc.next()));
                }else{
                    setScoreOfPlayer2(Integer.parseInt(sc.next()));
                }

                sc.next();
                currentWord = sc.next();

                if(currentWord.equals(getPlayerID1())){
                    setScoreOfPlayer1(Integer.parseInt(sc.next()));
                }else{
                    setScoreOfPlayer2(Integer.parseInt(sc.next()));
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
            }
            else if(currentWord.equals("LOST:")){
                setTheyLostFlag(true);
            }


        }



    }

    void setTheirExpansionTerrainType(String theirExpandedTerrainTypeFromMessage){
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

    void messageStartsWithEnd(String messageFromServer){
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

    String convertTileStringToTileObject(String tile, int orientation, boolean ourTileFlag){
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

    void setTheirTileTerrainTypes(String terrainList){

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

    void setOurTileTerrainTypes(String terrainList){

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

    Pair convertTileCoordinatesFromCubicToOddROffset(int x, int y, int z){
        setColOddRTileCoordinate(x + (z-(z&1))/2 + 102);
        setRowOddRTileCoordinate(z + 102);
        Pair pair = new Pair(getColOddRTileCoordinate(), getRowOddRTileCoordinate());
        return pair;
    }

    Pair convertBuildCoordinatesFromCubicToOddROffset(int x, int y, int z){
        setTheirColOddRBuildCoordinate(x + (z-(z&1))/2 + 102);
        setTheirRowOddRBuildCoordinate(z + 102);
        Pair pair = new Pair(getTheirColOddRBuildCoordinate(), getTheirRowOddRBuildCoordinate());
        return pair;
    }

    Pair convertCoordinatesBasedOnOrientation(int col, int row, int orientation){
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









    String getPlayerID1(){
        return this.playerID1;
    }

    void setPlayerID1(String playerID1){
        this.playerID1 = playerID1;
    }

    String getPlayerID2(){
        return this.playerID2;
    }

    void setPlayerID2(String playerID2){
        this.playerID2 = playerID2;
    }

    String getChallengeID() {
        return challengeID;
    }

    void setChallengeID(String challengeID){
        this.challengeID = challengeID;
    }

    String getCurrentRoundID() {
        return this.currentRoundID;
    }

    void setCurrentRoundID(String currentRoundID){
        this.currentRoundID = currentRoundID;
    }

    String getGameID() {
        return gameID;
    }

    void setGameID(String gameID){
        this.gameID = gameID;
    }

    String getCurrentMovePlayerID() {
        return currentMovePlayerID;
    }

    void setCurrentMovePlayerID(String currentMovePlayerID) {
        this.currentMovePlayerID = currentMovePlayerID;
    }

    BuildType getTheirMoveType() {
        return theirMoveType;
    }

    void setTheirMoveType(BuildType theirMoveType){
        this.theirMoveType = theirMoveType;
    }

    double getTimeToCompleteTurn() {
        return this.timeToCompleteTurn;
    }

    void setTimeToCompleteTurn(double timeToCompleteTurn) {
        this.timeToCompleteTurn = timeToCompleteTurn;
    }

    int getCurrentMoveNum() {
        return this.currentMoveNum;
    }

    void setCurrentMoveNum(int currentMoveNum) {
        this.currentMoveNum = currentMoveNum;
    }

    String getTileTerrainStringOfFormatAandB() {
        return this.tileTerrainStringOfFormatAandB;
    }

    void setTileTerrainStringOfFormatAandB(String tileTerrainStringOfFormatAandB) {
        this.tileTerrainStringOfFormatAandB = tileTerrainStringOfFormatAandB;
    }

    String getTerrainHexAFromMessage() {
        return this.terrainHexAFromMessage;
    }

    void setTerrainHexAFromMessage(String terrainHexAFromMessage) {
        this.terrainHexAFromMessage = terrainHexAFromMessage;
    }

    String getTerrainHexBFromMessage() {
        return this.terrainHexBFromMessage;
    }

    void setTerrainHexBFromMessage(String terrainHexBFromMessage) {
        this.terrainHexBFromMessage = terrainHexBFromMessage;
    }

    String getTerrainHexCFromMessage() {
        return this.terrainHexCFromMessage;
    }

    void setTerrainHexCFromMessage(String terrainHexCFromMessage) {
        this.terrainHexCFromMessage = terrainHexCFromMessage;
    }

    terrainTypes getTheirTerrainTypeAtHexA() {
        return this.theirTerrainTypeAtHexA;
    }

    void setTheirTerrainTypeAtHexA(terrainTypes theirTerrainTypeAtHexA) {
        this.theirTerrainTypeAtHexA = theirTerrainTypeAtHexA;
    }

    terrainTypes getTheirTerrainTypeAtHexB() {
        return this.theirTerrainTypeAtHexB;
    }

    void setTheirTerrainTypeAtHexB(terrainTypes theirTerrainTypeAtHexB) {
        this.theirTerrainTypeAtHexB = theirTerrainTypeAtHexB;
    }

    terrainTypes getTheirTerrainTypeAtHexC() {
        return this.theirTerrainTypeAtHexC;
    }

    void setTheirTerrainTypeAtHexC(terrainTypes theirTerrainTypeAtHexC) {
        this.theirTerrainTypeAtHexC = theirTerrainTypeAtHexC;
    }

    int getTheirTileColumnPosition() {
        return this.theirTileColumnPosition;
    }

    void setTheirTileColumnPosition(int theirTileColumnPosition) {
        this.theirTileColumnPosition = theirTileColumnPosition;
    }

    int getTheirTileRowPosition() {
        return this.theirTileRowPosition;
    }

    void setTheirTileRowPosition(int theirTileRowPosition) {
        this.theirTileRowPosition = theirTileRowPosition;
    }

    boolean getTheirTileIsFlipped() {
        return this.theirTileIsFlipped;
    }

    void setTheirTileIsFlipped(boolean theirTileIsFlipped) {
        this.theirTileIsFlipped = theirTileIsFlipped;
    }

    terrainTypes getOurTerrainTypeAtHexA() {
        return this.ourTerrainTypeAtHexA;
    }

    void setOurTerrainTypeAtHexA(terrainTypes ourTerrainTypeAtHexA) {
        this.ourTerrainTypeAtHexA = ourTerrainTypeAtHexA;
    }

    terrainTypes getOurTerrainTypeAtHexB() {
        return this.ourTerrainTypeAtHexB;
    }

    void setOurTerrainTypeAtHexB(terrainTypes ourTerrainTypeAtHexB) {
        this.ourTerrainTypeAtHexB = ourTerrainTypeAtHexB;
    }

    terrainTypes getOurTerrainTypeAtHexC() {
        return this.ourTerrainTypeAtHexC;
    }

    void setOurTerrainTypeAtHexC(terrainTypes ourTerrainTypeAtHexC) {
        this.ourTerrainTypeAtHexC = ourTerrainTypeAtHexC;
    }

    int getxCubicTileCoordinate() {
        return this.xCubicTileCoordinate;
    }

    void setxCubicTileCoordinate(int xCubicTileCoordinate) {
        this.xCubicTileCoordinate = xCubicTileCoordinate;
    }

    int getyCubicTileCoordinate() {
        return this.yCubicTileCoordinate;
    }

    void setyCubicTileCoordinate(int yCubicTileCoordinate) {
        this.yCubicTileCoordinate = yCubicTileCoordinate;
    }

    int getzCubicTileCoordinate() {
        return this.zCubicTileCoordinate;
    }

    void setzCubicTileCoordinate(int zCubicTileCoordinate) {
        this.zCubicTileCoordinate = zCubicTileCoordinate;
    }

    int getxCubicBuildCoordinate() {
        return this.xCubicBuildCoordinate;
    }

    void setxCubicBuildCoordinate(int xCubicBuildCoordinate) {
        this.xCubicBuildCoordinate = xCubicBuildCoordinate;
    }

    int getyCubicBuildCoordinate() {
        return this.yCubicBuildCoordinate;
    }

    void setyCubicBuildCoordinate(int yCubicBuildCoordinate) {
        this.yCubicBuildCoordinate = yCubicBuildCoordinate;
    }

    int getzCubicBuildCoordinate() {
        return this.zCubicBuildCoordinate;
    }

    void setzCubicBuildCoordinate(int zCubicBuildCoordinate) {
        this.zCubicBuildCoordinate = zCubicBuildCoordinate;
    }

    String getTheirExpandedTerrainTypeFromMessage() {
        return this.theirExpandedTerrainTypeFromMessage;
    }

    void setTheirExpandedTerrainTypeFromMessage(String theirExpandedTerrainTypeFromMessage) {
        this.theirExpandedTerrainTypeFromMessage = theirExpandedTerrainTypeFromMessage;
    }

    terrainTypes getTheirExpandTerrainTypeIfExpansion() {
        return this.theirExpandTerrainTypeIfExpansion;
    }

    void setTheirExpandTerrainTypeIfExpansion(terrainTypes theirExpandTerrainTypeIfExpansion) {
        this.theirExpandTerrainTypeIfExpansion = theirExpandTerrainTypeIfExpansion;
    }

    int getScoreOfPlayer1() {
        return this.scoreOfPlayer1;
    }

    void setScoreOfPlayer1(int scoreOfPlayer1) {
        this.scoreOfPlayer1 = scoreOfPlayer1;
    }

    int getScoreOfPlayer2() {
        return this.scoreOfPlayer2;
    }

    void setScoreOfPlayer2(int scoreOfPlayer2) {
        this.scoreOfPlayer2 = scoreOfPlayer2;
    }

    int getNumberOfRounds() {
        return this.numberOfRounds;
    }

    void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    int getOrientation() {
        return this.orientation;
    }

    void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    int getColOddRTileCoordinate() {
        return this.colOddRTileCoordinate;
    }

    void setColOddRTileCoordinate(int colOddRTileCoordinate) {
        this.colOddRTileCoordinate = colOddRTileCoordinate;
    }

    int getRowOddRTileCoordinate() {
        return this.rowOddRTileCoordinate;
    }

    void setRowOddRTileCoordinate(int rowOddRTileCoordinate) {
        this.rowOddRTileCoordinate = rowOddRTileCoordinate;
    }

    int getTheirColOddRBuildCoordinate() {
        return this.theirColOddRBuildCoordinate;
    }

    void setTheirColOddRBuildCoordinate(int theirColOddRBuildCoordinate) {
        this.theirColOddRBuildCoordinate = theirColOddRBuildCoordinate;
    }

    int getTheirRowOddRBuildCoordinate() {
        return this.theirRowOddRBuildCoordinate;
    }

    void setTheirRowOddRBuildCoordinate(int theirRowOddRBuildCoordinate) {
        this.theirRowOddRBuildCoordinate = theirRowOddRBuildCoordinate;
    }

    boolean getTheyForfeitedFlag() {
        return this.theyForfeitedFlag;
    }

    void setTheyForfeitedFlag(boolean theyForfeitedFlag) {
        this.theyForfeitedFlag = theyForfeitedFlag;
    }

    boolean getTheyLostFlag() {
        return this.theyLostFlag;
    }

    void setTheyLostFlag(boolean theyLostFlag) {
        this.theyLostFlag = theyLostFlag;
    }

    boolean getEndOfRoundFlag() {
        return this.endOfRoundFlag;
    }

    void setEndOfRoundFlag(boolean endOfRoundFlag) {
        this.endOfRoundFlag = endOfRoundFlag;
    }

    boolean getEndOfChallenges() {
        return this.endOfChallenges;
    }

    void setEndOfChallenges(boolean endOfChallenges) {
        this.endOfChallenges = endOfChallenges;
    }

    boolean getWaitingForNextMatchFlag() {
        return this.waitingForNextMatchFlag;
    }

    void setWaitingForNextMatchFlag(boolean waitingForNextMatchFlag) {
        this.waitingForNextMatchFlag = waitingForNextMatchFlag;
    }

    String getTerrainOrderingOfTheirTile() {
        return this.terrainOrderingOfTheirTile;
    }

    void setTerrainOrderingOfTheirTile(String terrainOrderingOfTheirTile) {
        this.terrainOrderingOfTheirTile = terrainOrderingOfTheirTile;
    }

}
