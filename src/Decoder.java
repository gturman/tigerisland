import java.util.Scanner;

/**
 * Created by Brendan on 4/4/2017.
 */
public class Decoder {

    public String playerID1;
    public String playerID2;
    String challengeID;
    public String currentRoundID;
    public String gameID;
    String currentMovePlayerID;
    public float timeToCompleteTurn;
    public int currentMoveNum;

    public String tileTerrainStringOfFormatAandB; //Not sure how tile is given to us yet
    public String tileTerrain1;
    public String tileTerrain2;

    public String terrainHexAFromMessage = "";
    public String terrainHexBFromMessage = "";
    public String terrainHexCFromMessage = "";

    public terrainTypes theirTerrainTypeAtHexA;
    public terrainTypes theirTerrainTypeAtHexB;
    public terrainTypes theirTerrainTypeAtHexC;
    public int theirTileColumnPosition = 0;
    public int theirTileRowPosition = 0;
    public boolean theirTileIsFlipped = false;

    public terrainTypes ourTerrainTypeAtHexA;
    public terrainTypes ourTerrainTypeAtHexB;
    public terrainTypes ourTerrainTypeAtHexC;

    public int xCubicTileCoordinate;
    public int yCubicTileCoordinate;
    public int zCubicTileCoordinate;
    public int xCubicBuildCoordinate;
    public int yCubicBuildCoordinate;
    public int zCubicBuildCoordinate;

    public String theirExpandedTerrainTypeFromMessage;  // expanded terrain type
    terrainTypes theirExpandTerrainTypeIfExpansion;

    public int scoreOfPlayer1;
    public int scoreOfPlayer2;
    public int numberOfRounds;
    public int orientation;
    public int xOddRTileCoordinate;
    public int yOddRTileCoordinate;
    int theirXOddRBuildCoordinate;
    int theirYOddRBuildCoordinate;

    boolean theyForfeitedFlag = false;
    boolean theyLostFlag = false;
    boolean endOfRoundFlag = false;
    boolean endOfChallenges = false;
    boolean waitingForNextMatchFlag = false;

    BuildType theirMoveType;

    public String terrainOrderingOfTheirTile = "";

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
        //Set to our Player ID
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
                numberOfRounds = Integer.parseInt(currentWord);
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
                timeToCompleteTurn = Float.parseFloat(currentWord);
            } else if(currentWord.equals("MOVE")){
                currentWord = sc.next();
                if(!currentWord.equals("IN")) {
                    currentMoveNum = Integer.parseInt(currentWord);
                }
            } else if(currentWord.equals("PLACE")){
                currentWord = sc.next();
                tileTerrainStringOfFormatAandB = currentWord;
                orientation = 1;
                convertTileStringToTileObject(tileTerrainStringOfFormatAandB, orientation, true);


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
                    scoreOfPlayer1 = Integer.parseInt(sc.next());
                }else{
                    scoreOfPlayer2 = Integer.parseInt(sc.next());
                }

                sc.next();
                currentWord = sc.next();

                if(currentWord.equals(getPlayerID1())){
                    scoreOfPlayer1 = Integer.parseInt(sc.next());
                }else{
                    scoreOfPlayer2 = Integer.parseInt(sc.next());
                }
            } else if (currentWord.equals("MOVE")) {
                currentWord = sc.next();
                currentMoveNum = Integer.parseInt(currentWord);
            } else if (currentWord.equals("PLAYER")){
                currentWord = sc.next();
                setCurrentMovePlayerID(currentWord);
            } else if(currentWord.equals("PLACED")){

                tileTerrainStringOfFormatAandB = sc.next();

                sc.next();

                xCubicTileCoordinate = Integer.parseInt(sc.next());
                yCubicTileCoordinate = Integer.parseInt(sc.next());
                zCubicTileCoordinate = Integer.parseInt(sc.next());
                orientation = Integer.parseInt(sc.next());



                theirTileColumnPosition = convertCoordinatesBasedOnOrientation(convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getColumnPosition(), convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getRowPosition(),orientation).getColumnPosition();
                theirTileRowPosition = convertCoordinatesBasedOnOrientation(convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getColumnPosition(), convertTileCoordinatesFromCubicToOddROffset(xCubicTileCoordinate, yCubicTileCoordinate, zCubicTileCoordinate).getRowPosition(),orientation).getRowPosition();
                terrainOrderingOfTheirTile =  convertTileStringToTileObject(tileTerrainStringOfFormatAandB, orientation, false);

                if(orientation%2 == 0){
                    theirTileIsFlipped = true;
                }else{
                    theirTileIsFlipped = false;
                }

                 //DEBUGGING PURPOSES
                 //  theirPlacementOfTile = gameID + " " + theirTileColumnPosition + " " + theirTileRowPosition + " " + theirTerrainTypeAtHexA + " " + theirTerrainTypeAtHexB + " " + theirTerrainTypeAtHexC + " " + theirTileIsFlipped;
                 // System.out.println(theirPlacementOfTile);
                 //col, row, terrain A, terrain B, terrain C

                currentWord = sc.next();
                if(currentWord.equals("FOUNDED")){
                    sc.next();
                    sc.next();
                    xCubicBuildCoordinate = Integer.parseInt(sc.next());
                    yCubicBuildCoordinate = Integer.parseInt(sc.next());
                    zCubicBuildCoordinate = Integer.parseInt(sc.next());
                    setTheirMoveType(BuildType.FOUND_SETTLEMENT);
                }
                else if(currentWord.equals("EXPANDED")){
                    sc.next();
                    sc.next();
                    xCubicBuildCoordinate = Integer.parseInt(sc.next());
                    yCubicBuildCoordinate = Integer.parseInt(sc.next());
                    zCubicBuildCoordinate = Integer.parseInt(sc.next());
                    theirExpandedTerrainTypeFromMessage = sc.next();
                    setTheirExpansionTerrainType(theirExpandedTerrainTypeFromMessage);
                    setTheirMoveType(BuildType.EXPAND_SETTLMENT);
                }
                else if(currentWord.equals("BUILT")){
                    currentWord = sc.next();
                    if(currentWord.equals("TOTORO")){
                        sc.next();
                        sc.next();
                        xCubicBuildCoordinate = Integer.parseInt(sc.next());
                        yCubicBuildCoordinate = Integer.parseInt(sc.next());
                        zCubicBuildCoordinate = Integer.parseInt(sc.next());
                        setTheirMoveType(BuildType.PLACE_TOTORO);
                    }
                    else if(currentWord.equals("TIGER")){
                        sc.next();
                        sc.next();
                        xCubicBuildCoordinate = Integer.parseInt(sc.next());
                        yCubicBuildCoordinate = Integer.parseInt(sc.next());
                        zCubicBuildCoordinate = Integer.parseInt(sc.next());
                        setTheirMoveType(BuildType.PLACE_TIGER);
                    }

                }

                convertBuildCoordinatesFromCubicToOddROffset(xCubicBuildCoordinate, yCubicBuildCoordinate, zCubicBuildCoordinate);
            } else if(currentWord.equals("FORFEITED:")){
                theyForfeitedFlag = true;
            }
            else if(currentWord.equals("LOST:")){
                theyLostFlag = true;
            }


        }



    }

    void setTheirExpansionTerrainType(String theirExpandedTerrainTypeFromMessage){
        if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'V'){
            theirExpandTerrainTypeIfExpansion = terrainTypes.VOLCANO;
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'J'){
            theirExpandTerrainTypeIfExpansion = terrainTypes.JUNGLE;
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'R'){
            theirExpandTerrainTypeIfExpansion = terrainTypes.ROCKY;
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'L'){
            theirExpandTerrainTypeIfExpansion = terrainTypes.LAKE;
        } else if(theirExpandedTerrainTypeFromMessage.charAt(0) == 'G') {
            theirExpandTerrainTypeIfExpansion = terrainTypes.GRASSLANDS;
        }
    }

    void messageStartsWithEnd(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("ROUND")){
                endOfRoundFlag = true;
            } else if(currentWord.equals("CHALLENGES")){
               endOfChallenges = true;
            } else if(currentWord.equals("WAIT")){
                waitingForNextMatchFlag = true;
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

        tileTerrain1 = terrain1;
        tileTerrain2 = terrain2;

        if(orientation == 1 || orientation == 4){
            terrainHexAFromMessage = "VOLCANO";
            terrainHexBFromMessage = tileTerrain1;
            terrainHexCFromMessage = tileTerrain2;
            terrainListOrder = "VOLCANO" + " " + tileTerrain1 + " " + tileTerrain2;
        }
        else if(orientation == 2 || orientation == 5){
            terrainHexAFromMessage = tileTerrain1;
            terrainHexBFromMessage = tileTerrain2;
            terrainHexCFromMessage = "VOLCANO";
            terrainListOrder = tileTerrain1 + " " + tileTerrain2 + " " + "VOLCANO";
        }
        else if(orientation == 3 || orientation == 6){
            terrainHexAFromMessage = tileTerrain2;
            terrainHexBFromMessage = "VOLCANO";
            terrainHexCFromMessage = tileTerrain1;
            terrainListOrder = tileTerrain2 + " " + "VOLCANO" + " " + tileTerrain1;
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
            theirTerrainTypeAtHexA = terrainTypes.VOLCANO;
        } else if(currentTerrain.charAt(0) == 'J'){
            theirTerrainTypeAtHexA = terrainTypes.JUNGLE;
        } else if(currentTerrain.charAt(0) == 'R'){
            theirTerrainTypeAtHexA = terrainTypes.ROCKY;
        } else if(currentTerrain.charAt(0) == 'L'){
            theirTerrainTypeAtHexA = terrainTypes.LAKE;
        } else if(currentTerrain.charAt(0) == 'G') {
            theirTerrainTypeAtHexA = terrainTypes.GRASSLANDS;
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            theirTerrainTypeAtHexB = terrainTypes.VOLCANO;
        } else if(currentTerrain.charAt(0) == 'J'){
            theirTerrainTypeAtHexB = terrainTypes.JUNGLE;
        } else if(currentTerrain.charAt(0) == 'R'){
            theirTerrainTypeAtHexB = terrainTypes.ROCKY;
        } else if(currentTerrain.charAt(0) == 'L'){
            theirTerrainTypeAtHexB = terrainTypes.LAKE;
        } else if(currentTerrain.charAt(0) == 'G') {
            theirTerrainTypeAtHexB = terrainTypes.GRASSLANDS;
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            theirTerrainTypeAtHexC = terrainTypes.VOLCANO;
        }
        else if(currentTerrain.charAt(0) == 'J'){
            theirTerrainTypeAtHexC = terrainTypes.JUNGLE;
        }
        else if(currentTerrain.charAt(0) == 'R'){
            theirTerrainTypeAtHexC = terrainTypes.ROCKY;
        }
        else if(currentTerrain.charAt(0) == 'L'){
            theirTerrainTypeAtHexC = terrainTypes.LAKE;
        }
        else if(currentTerrain.charAt(0) == 'G') {
            theirTerrainTypeAtHexC = terrainTypes.GRASSLANDS;
        }

    }

    void setOurTileTerrainTypes(String terrainList){

        Scanner sc = new Scanner(terrainList);
        String currentTerrain;

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            ourTerrainTypeAtHexA = terrainTypes.VOLCANO;
        } else if(currentTerrain.charAt(0) == 'J'){
            ourTerrainTypeAtHexA = terrainTypes.JUNGLE;
        } else if(currentTerrain.charAt(0) == 'R'){
            ourTerrainTypeAtHexA = terrainTypes.ROCKY;
        } else if(currentTerrain.charAt(0) == 'L'){
            ourTerrainTypeAtHexA = terrainTypes.LAKE;
        } else if(currentTerrain.charAt(0) == 'G') {
            ourTerrainTypeAtHexA = terrainTypes.GRASSLANDS;
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            ourTerrainTypeAtHexB = terrainTypes.VOLCANO;
        } else if(currentTerrain.charAt(0) == 'J'){
            ourTerrainTypeAtHexB = terrainTypes.JUNGLE;
        } else if(currentTerrain.charAt(0) == 'R'){
            ourTerrainTypeAtHexB = terrainTypes.ROCKY;
        } else if(currentTerrain.charAt(0) == 'L'){
            ourTerrainTypeAtHexB = terrainTypes.LAKE;
        } else if(currentTerrain.charAt(0) == 'G') {
            ourTerrainTypeAtHexB = terrainTypes.GRASSLANDS;
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            ourTerrainTypeAtHexC = terrainTypes.VOLCANO;
        } else if(currentTerrain.charAt(0) == 'J'){
            ourTerrainTypeAtHexC = terrainTypes.JUNGLE;
        } else if(currentTerrain.charAt(0) == 'R'){
            ourTerrainTypeAtHexC = terrainTypes.ROCKY;
        } else if(currentTerrain.charAt(0) == 'L'){
            ourTerrainTypeAtHexC = terrainTypes.LAKE;
        } else if(currentTerrain.charAt(0) == 'G') {
            ourTerrainTypeAtHexC = terrainTypes.GRASSLANDS;
        }

    }

    Pair convertTileCoordinatesFromCubicToOddROffset(int x, int y, int z){
        xOddRTileCoordinate = x + (z-(z&1))/2 + 102;
        yOddRTileCoordinate = z + 102;
        Pair pair = new Pair(xOddRTileCoordinate, yOddRTileCoordinate);
        return pair;
    }

    Pair convertBuildCoordinatesFromCubicToOddROffset(int x, int y, int z){
        theirXOddRBuildCoordinate = x + (z-(z&1))/2 + 102;
        theirYOddRBuildCoordinate = z + 102;
        Pair pair = new Pair(theirXOddRBuildCoordinate, theirYOddRBuildCoordinate);
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

    String getPlayerID2(){
        return this.playerID2;
    }

    String getChallengeID() {
        return challengeID;
    }

    void setPlayerID1(String playerID1){
        this.playerID1 = playerID1;
    }

    void setPlayerID2(String playerID2){
        this.playerID2 = playerID2;
    }

    void setChallengeID(String challengeID){
        this.challengeID = challengeID;
    }

    void setCurrentRoundID(String currentRoundID){
        this.currentRoundID = currentRoundID;
    }

    void setGameID(String gameID){
        this.gameID = gameID;
    }

    void setCurrentMovePlayerID(String currentMovePlayerID) {
        this.currentMovePlayerID = currentMovePlayerID;
    }

    void setTheirMoveType(BuildType theirMoveType){
        this.theirMoveType = theirMoveType;
    }

}
