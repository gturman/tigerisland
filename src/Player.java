/**
 * Created by William on 3/14/2017.
 *
 */
public class Player {

    private int villagerCount;
    private int totoroCount;
    private int tigerCount;
    private int playerID;
    private int score;
    private int settlementCount;
    private int tilesDrawn;
    private turnPhase playerTurnPhase;

    Player(){
        //todo: assign playerID or initialize as Player P1 and Player P2
        this.tigerCount = 2;
        this.playerID = 0;
        this.score = 0;
        this.villagerCount = 20;
        this.totoroCount = 3;
        this.settlementCount = 0;
        this.tilesDrawn = 0;
        this.playerTurnPhase = turnPhase.TILE_PLACEMENT;
    }

    Player(int playerID){
        //todo: assign playerID or initialize as Player P1 and Player P2
        this.tigerCount = 2;
        this.playerID = playerID;
        this.score = 0;
        this.villagerCount = 20;
        this.totoroCount = 3;
        this.settlementCount = 0;
        this.tilesDrawn = 0;
        this.playerTurnPhase = turnPhase.TILE_PLACEMENT;
    }

    public int getTigerCount(){return tigerCount;}

    public int getVillagerCount(){
        return villagerCount;
    }

    public void decreaseVillagerCount(int modifier){
        villagerCount -= modifier;
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

    public turnPhase getTurnPhase() {
        return playerTurnPhase;
    }

    public void setTurnPhase(turnPhase playerTurnPhase) {
        this.playerTurnPhase = playerTurnPhase;
    }

    public void setVillagerCount(int villagerCount) {
        this.villagerCount = villagerCount;
    }
}

