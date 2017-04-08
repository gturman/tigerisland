import java.util.Scanner;

/**
 * Created by Brendan on 4/4/2017.
 */
public class Decoder {

    public int pid; //Player ID
    public int pid2;
    public int cid; //Challenge ID
    public int rid; //Round ID
    public int gid; //Game ID
    public int time; //How much time to complete turn
    public int moveNum; //the move number

    public String tile; //Not sure how tile is given to us yet
    public String tileTerrain1;
    public String tileTerrain2;

    public String terrainA = "";
    public String terrainB = "";
    public String terrainC = "";

    public terrainTypes terrainHexA;
    public terrainTypes terrainHexB;
    public terrainTypes terrainHexC;
    public int theirColumnPosition = 0;
    public int theirRowPosition = 0;
    public boolean flipped = false;

    public int x; //x-coordinate of tile
    public int y; //y-coordinate of tile
    public int z; //z-coordinate of tile
    public int sx; //x-coordinate of settlement
    public int sy; //y-coordinate of settlement
    public int sz; //z-coordinate of settlement

    public String terrain;

    public int score;
    public int score2;
    public int rounds;
    public int orientation;
    public int xCoordinate;
    public int yCoordinate;

    boolean builtSettlement = false;
    boolean builtTotoro = false;
    boolean builtTiger = false;
    boolean expandedSettlement = false;
    boolean forfeited = false;
    boolean lost = false;


    public String terrains = "";
    public String placement = "";

    Decoder() {

    }

    void decodeString(String messageFromServer){

        isImportantLineToRead(messageFromServer);

    }

    void isImportantLineToRead(String messageFromServer){

        if(messageFromServer.substring(0,4).equals("WAIT")){
            messageStartsWithWait(messageFromServer);
        }
        else if (messageFromServer.substring(0,3).equals("NEW")){
            messageStartsWithNew(messageFromServer);
        }
        else if(messageFromServer.substring(0,5).equals("BEGIN")){
            messageStartsWithBegin(messageFromServer);
        }
        else if(messageFromServer.substring(0,4).equals("MAKE")){
            messageStartsWithMake(messageFromServer);
        }
        else if(messageFromServer.substring(0,4).equals("GAME")){
            messageStartsWithGame(messageFromServer);
        }
        else if(messageFromServer.substring(0,3).equals("END")){
            messageStartsWithEnd(messageFromServer);
        }


    }

    void messageStartsWithGame(String messageFromServer){
        String currentWord;
        String previousWord;

        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();

            if(currentWord.equals("GAME")){
                gid = Integer.parseInt(sc.next());
            }
            else if(currentWord.equals("OVER")){
                sc.next();
                currentWord = sc.next();
                pid = Integer.parseInt(currentWord);
                currentWord = sc.next();
                score = Integer.parseInt(currentWord);
                sc.next();
                currentWord = sc.next();
                pid2 = Integer.parseInt(currentWord);
                currentWord = sc.next();
                score2 = Integer.parseInt(currentWord);

            }
            else if (currentWord.equals("MOVE")) {
                currentWord = sc.next();
                moveNum = Integer.parseInt(currentWord);
            }
            else if (currentWord.equals("PLAYER")){
                currentWord = sc.next();
                pid = Integer.parseInt(currentWord);
            }
            else if(currentWord.equals("PLACED")){
                builtSettlement = false;
                builtTiger = false;
                builtTotoro = false;
                expandedSettlement = false;
                tile = sc.next();

                currentWord = sc.next();
                if(currentWord.equals("AT")){
                    x = Integer.parseInt(sc.next());
                    y = Integer.parseInt(sc.next());
                    z = Integer.parseInt(sc.next());
                    orientation = Integer.parseInt(sc.next());

                    theirColumnPosition = convertCoordinatesBasedOnOrientation(convertCoordinatesFromCubicToROffset(x,y,z).getColumnPosition(),convertCoordinatesFromCubicToROffset(x,y,z).getRowPosition(),orientation).getColumnPosition();
                    theirRowPosition = convertCoordinatesBasedOnOrientation(convertCoordinatesFromCubicToROffset(x,y,z).getColumnPosition(),convertCoordinatesFromCubicToROffset(x,y,z).getRowPosition(),orientation).getRowPosition();
                    terrains =  convertTileStringToTileObject(tile, orientation);

                    if(orientation%2 == 0){
                        flipped = true;
                    }


                    placement = gid + " " + theirColumnPosition + " " + theirRowPosition + " " + terrainHexA + " " + terrainHexB + " " + terrainHexC + " " + flipped;
                    System.out.println(placement);
                    //col, row, terrain A, terrain B, terrain C

                    currentWord = sc.next();
                    if(currentWord.equals("FOUNDED")){
                        sc.next();
                        sc.next();
                        sx = Integer.parseInt(sc.next());
                        sy = Integer.parseInt(sc.next());
                        sz = Integer.parseInt(sc.next());
                        builtSettlement = true;
                    }
                    else if(currentWord.equals("EXPANDED")){
                        sc.next();
                        sc.next();
                        sx = Integer.parseInt(sc.next());
                        sy = Integer.parseInt(sc.next());
                        sz = Integer.parseInt(sc.next());
                        terrain = sc.next();
                        expandedSettlement = true;
                    }
                    else if(currentWord.equals("BUILT")){
                        currentWord = sc.next();
                        if(currentWord.equals("TOTORO")){
                            sc.next();
                            sc.next();
                            sx = Integer.parseInt(sc.next());
                            sy = Integer.parseInt(sc.next());
                            sz = Integer.parseInt(sc.next());
                            builtTotoro = true;
                        }
                        else if(currentWord.equals("TIGER")){
                            sc.next();
                            sc.next();
                            sx = Integer.parseInt(sc.next());
                            sy = Integer.parseInt(sc.next());
                            sz = Integer.parseInt(sc.next());
                            builtTiger = true;
                        }

                    }
                }
                //at xyz
                //forfeited:
                //lost:
            }
            else if(currentWord.equals("FORFEITED:")){
                forfeited = true;
            }
            else if(currentWord.equals("LOST:")){
                lost = true;
            }

            previousWord = currentWord;
        }



    }

    void messageStartsWithEnd(String messageFromServer){
        String currentWord;
        String previousWord = "";
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();

            if(currentWord.equals("ROUND")){
                rid = Integer.parseInt(sc.next());
            }
            else if(currentWord.equals("OF") && !previousWord.equals("END")){
                rounds = Integer.parseInt(sc.next());
            }
            previousWord = currentWord;
        }
        //Signifies end of round or end of match depending...

    }

    void messageStartsWithMake(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("GAME")){
                currentWord=sc.next();
                gid = Integer.parseInt(currentWord);
            }
            else if(currentWord.equals("WITHIN")){
                currentWord = sc.next();
                time = Integer.parseInt(currentWord);
            }
            else if(currentWord.equals("MOVE")){
                currentWord = sc.next();
                if(!currentWord.equals("IN")) {
                    moveNum = Integer.parseInt(currentWord);
                }
            }
            else if(currentWord.equals("PLACE")){
                currentWord = sc.next();
                tile = currentWord;
                //convertTileStringToTileObject(tile,orientation);
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
                rid = Integer.parseInt(currentWord);
            }
            else if(currentWord.equals("OF")){
                currentWord = sc.next();
                rounds = Integer.parseInt(currentWord);
            }

        }
        //This will signify beginning of a round!
        //Need to tell AI it is time to do its' moves
        //if round is odd or even, might be able to tell without talking...

    }

    void messageStartsWithNew(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()){
            currentWord = sc.next();
            if(currentWord.equals("CHALLENGE")){
                currentWord = sc.next();
                cid = Integer.parseInt(currentWord);
            }
            else if(currentWord.equals("PLAY")){
                currentWord = sc.next();
                rounds = Integer.parseInt(currentWord);
            }
            else if(currentWord.equals("PLAYER")){
                currentWord = sc.next();
                pid2 = Integer.parseInt(currentWord);
            }
            //sets challenge id, how many rounds
            //sets opponent player ID
        }

    }

    void messageStartsWithWait(String messageFromServer){
        String currentWord;
        Scanner sc = new Scanner(messageFromServer);

        while(sc.hasNext()) {
            currentWord = sc.next();
            if(currentWord.equals("BEGIN") && sc.hasNext()){
                currentWord = sc.next();
                pid = Integer.parseInt(currentWord);
            }
        }
        //Set to our Player ID
    }

    String convertTileStringToTileObject(String tile, int orientation){
        String terrain1 ="";
        String terrain2 ="";
        String terrainList = "";
        int iterator = 0;

        boolean plusSign = false;

        while(iterator < tile.length()){

            if(tile.charAt(iterator) == '+'){
                plusSign = true;
                iterator++;
            }

            if(!plusSign){
                terrain1 += tile.charAt(iterator);
            }
            else{
                terrain2 += tile.charAt(iterator);
            }

            iterator++;
        }

        //terrain type A will always be a volcano
        tileTerrain1 = terrain1;
        tileTerrain2 = terrain2;

        if(orientation == 1 || orientation == 4){
            terrainA = "VOLCANO";
            terrainB = tileTerrain1;
            terrainC = tileTerrain2;
            terrainList = "VOLCANO" + " " + tileTerrain1 + " " + tileTerrain2;
        }
        else if(orientation == 2 || orientation == 5){
            terrainA = tileTerrain1;
            terrainB = tileTerrain2;
            terrainC = "VOLCANO";
            terrainList = tileTerrain1 + " " + tileTerrain2 + " " + "VOLCANO";
        }
        else if(orientation == 3 || orientation == 6){
            terrainA = tileTerrain2;
            terrainB = "VOLCANO";
            terrainC = tileTerrain1;
            terrainList = tileTerrain2 + " " + "VOLCANO" + " " + tileTerrain1;
        }

        setTerrainTypes(terrainList);


        return terrainList;

    }

    void setTerrainTypes(String terrainList){

        Scanner sc = new Scanner(terrainList);
        String currentTerrain;

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            terrainHexA = terrainTypes.VOLCANO;
        }
        else if(currentTerrain.charAt(0) == 'J'){
            terrainHexA = terrainTypes.JUNGLE;
        }
        else if(currentTerrain.charAt(0) == 'R'){
            terrainHexA = terrainTypes.ROCKY;
        }
        else if(currentTerrain.charAt(0) == 'L'){
            terrainHexA = terrainTypes.LAKE;
        }
        else if(currentTerrain.charAt(0) == 'G') {
            terrainHexA = terrainTypes.GRASSLANDS;
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            terrainHexB = terrainTypes.VOLCANO;
        }
        else if(currentTerrain.charAt(0) == 'J'){
            terrainHexB = terrainTypes.JUNGLE;
        }
        else if(currentTerrain.charAt(0) == 'R'){
            terrainHexB = terrainTypes.ROCKY;
        }
        else if(currentTerrain.charAt(0) == 'L'){
            terrainHexB = terrainTypes.LAKE;
        }
        else if(currentTerrain.charAt(0) == 'G') {
            terrainHexB = terrainTypes.GRASSLANDS;
        }

        currentTerrain = sc.next();

        if(currentTerrain.charAt(0) == 'V'){
            terrainHexC = terrainTypes.VOLCANO;
        }
        else if(currentTerrain.charAt(0) == 'J'){
            terrainHexC = terrainTypes.JUNGLE;
        }
        else if(currentTerrain.charAt(0) == 'R'){
            terrainHexC = terrainTypes.ROCKY;
        }
        else if(currentTerrain.charAt(0) == 'L'){
            terrainHexC = terrainTypes.LAKE;
        }
        else if(currentTerrain.charAt(0) == 'G') {
            terrainHexC = terrainTypes.GRASSLANDS;
        }

    }

    Pair convertCoordinatesFromCubicToROffset(int x, int y, int z){


        //col = x + (z - (z&1)) / 2
        //row = z

        xCoordinate = x + (z-(z&1))/2 + 102;
        yCoordinate = z + 102;

        Pair pair = new Pair(xCoordinate, yCoordinate);
        return pair;

    }

    void convertOddROffsetToCubic(int col, int row){



        x = col - (row - (row&1)) / 2;
        z = row;
        y = -x-z;

        //Are our columns columns or rows...?

    }

    Pair convertCoordinatesBasedOnOrientation(int row, int col, int orientation){
        int newRowPos = 0;
        int newColPos = 0;

        if(col%2 == 0){
            if(orientation==1){
                newRowPos = row + 0;
                newColPos = col + 0;
            }
            else if(orientation==2){
                newRowPos = row + 0;
                newColPos = col - 1;
            }
            else if(orientation == 3){
                newRowPos = row + 0;
                newColPos = col + 1;
            }
            else if(orientation == 4){
                newRowPos = row + 0;
                newColPos = col + 0;
            }
            else if(orientation == 5){
                newRowPos = row - 1;
                newColPos = col + 1;
            }
            else if(orientation == 6){
                newRowPos = row - 1;
                newColPos = col - 1;
            }
        }
        else{
            if(orientation==1){
                newRowPos = row + 0;
                newColPos = col + 0;
            }
            else if(orientation==2){
                newRowPos = row + 1;
                newColPos = col - 1;
            }
            else if(orientation == 3){
                newRowPos = row + 1;
                newColPos = col + 1;
            }
            else if(orientation == 4){
                newRowPos = row + 0;
                newColPos = col + 0;
            }
            else if(orientation == 5){
                newRowPos = row + 0;
                newColPos = col + 1;
            }
            else if(orientation == 6){
                newRowPos = row + 0;
                newColPos = col - 1;
            }
        }

        Pair coordinates = new Pair(newRowPos,newColPos);
        return coordinates;
    }

    void makeNewTileFromGameMessages(int row, int col, int orientation, String terrainA, String terrainB){

        Pair coordinates = convertCoordinatesBasedOnOrientation(row,col,orientation);

        if(orientation == 1){
            //Volcano Hex A
            //terrain A = B
            //terrain B = C
        }
        else if(orientation == 2){
            //flipped
            //Volcano Hex C
            //terrain A = A
            //terrain B = B
        }
        else if(orientation == 3){
            //Volcano Hex B
            //terrain A = C
            //terrain B = A
        }
        else if(orientation == 4){
            //flipped
            //Volcano Hex A
            //terrain A = B
            //terrain B = C
        }
        else if(orientation == 5){
            //Volcano Hex C
            //terrain A = A
            //terrain B = B
        }
        else if(orientation == 6) {
            //flipped
            //Volcano Hex B
            //terrain A = C
            //terrain B = A
        }


    }

}
