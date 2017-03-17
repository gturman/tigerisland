/**
 * Created by William on 3/14/2017.
 * Edited by BC and CC
 */
public class Player {

    private int meepleCount;
    private int totoroCount;
    private int playerID;
    private int score;
    private int settlementCount;
    private int tilesDrawn;

    Player(){
        //todo: assign playerID or initialize as Player P1 and Player P2
        int playerID = 1;
        int score = 0;
        int meepleCount = 20;
        int totoroCount = 3;
        int settlementCount = 0;
        int tilesDrawn =0;

        this.playerID = playerID;
        this.score = score;
        this.meepleCount = meepleCount;
        this.totoroCount = totoroCount;
        this.settlementCount = settlementCount;
        this.tilesDrawn = tilesDrawn;
    }

    public int getMeepleCount(){
        return meepleCount;
    }

    public void modifyMeepleCount(int modifier){
        meepleCount -= modifier;
    }

    public int getTotoroCount(){
        return totoroCount;
    }

    public void decreaseTotoroCount(){
        this.totoroCount--;
    }

    public int getSettlementCount(){
        return settlementCount;
    }

    public void setSettlementCount(int settlementCount){
        this.settlementCount = settlementCount;
    }

    public int getScore(){
        return score;
    }

    public void increaseScore(int points){
        this.score+=points;
    }

    public int getPlayerID(){
        if (playerID == 1){
            int player = 1;
            return player;
        }
        else
        {
            int player = 2;
            return player;

        }

        //return playerID;
    }

    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    public void increaseTileCount(){
        this.tilesDrawn+=1;
    }

    public int getTileCount() {
        return tilesDrawn;
    }

}

