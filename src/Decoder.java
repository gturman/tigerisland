import java.util.Scanner;

/**
 * Created by Brendan on 4/4/2017.
 */
public class Decoder {

    public int pid; //Player ID
    public int opponentPid;
    public int cid; //Challenge ID
    public int rid; //Round ID
    public int gid; //Game ID
    public int time; //How much time to complete turn
    public int moveNum; //the move number
    public String tile; //Not sure how tile is given to us yet
    public int x; //x-coordinate of tile
    public int y; //y-coordinate of tile
    public int z; //z-coordinate of tile
    public int sx; //x-coordinate of settlement
    public int sy; //y-coordinate of settlement
    public int sz; //z-coordinate of settlement
    public String terrain;
    public int score;
    public int rounds;
    public int orientation;
    boolean builtSettlement = false;
    boolean builtTotoro = false;
    boolean builtTiger = false;
    boolean expandedSettlement = false;

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
                opponentPid = Integer.parseInt(currentWord);
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
            if(currentWord.equals("BEGIN")){
                currentWord = sc.next();
                pid = Integer.parseInt(currentWord);
            }
        }
        //Set to our Player ID
    }




}

