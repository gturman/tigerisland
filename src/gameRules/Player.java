package gameRules;

import enums.turnPhase;

/**
 * Created by William on 3/14/2017.
 */
public class Player {

    private int playerID;
    private int settlementCount;
    private int settlerCount;
    private int totoroCount;
    private int tigerCount;
    private int score;
    private turnPhase playerTurnPhase;

    public Player(int playerID){
        this.tigerCount = 2;
        this.playerID = playerID;
        this.score = 0;
        this.settlerCount = 20;
        this.totoroCount = 3;
        this.settlementCount = 0;
        this.playerTurnPhase = turnPhase.TILE_PLACEMENT;
    }

    public int getPlayerID(){
        return playerID;
    }

    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    public turnPhase getTurnPhase() {
        return playerTurnPhase;
    }

    public void setTurnPhase(turnPhase playerTurnPhase) {
        this.playerTurnPhase = playerTurnPhase;
    }

    public int getSettlerCount(){
        return settlerCount;
    }

    public void setSettlerCount(int settlerCount) {
        this.settlerCount = settlerCount;
    }

    public void decreaseSettlerCount(int modifier){
        settlerCount -= modifier;
    }

    public int getTotoroCount(){
        return totoroCount;
    }

    public void setTotoroCount(int totoroCount) {
        this.totoroCount = totoroCount;
    }

    public void decreaseTotoroCount(){
        this.totoroCount--;
    }

    public int getTigerCount(){return tigerCount;}

    public void setTigerCount(int tigerCount) {
        this.tigerCount = tigerCount;
    }

    public void decreaseTigerCount() {
        this.tigerCount -= 1;
    }

    public int getSettlementCount(){
        return settlementCount;
    }

    public void setSettlementCount(int settlementCount){
        this.settlementCount = settlementCount;
    }

    public void increaseSettlementCount(){
        this.settlementCount++;
    }

    public int getScore(){
        return score;
    }

    public void increaseScore(int points){
        this.score += points;
    }
}

