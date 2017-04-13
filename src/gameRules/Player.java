package gameRules;

import enums.turnPhase;

/**
 * Created by William on 3/14/2017.
 */
public class Player {
    private int playerID;
    private turnPhase playerTurnPhase;
    private int score;
    private int settlementCount;
    private int villagerCount;
    private int totoroCount;
    private int tigerCount;

    public Player(int playerID){
        this.playerID = playerID;
        this.playerTurnPhase = turnPhase.TILE_PLACEMENT;
        this.score = 0;
        this.settlementCount = 0;
        this.villagerCount = 20;
        this.totoroCount = 3;
        this.tigerCount = 2;
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

    public int getScore(){
        return score;
    }

    public void increaseScore(int points){
        this.score += points;
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

    public int getVillagerCount(){
        return villagerCount;
    }

    public void setVillagerCount(int villagerCount) {
        this.villagerCount = villagerCount;
    }

    public void decreaseVillagerCount(int modifier){
        villagerCount -= modifier;
    }

    public int getTotoroCount(){
        return totoroCount;
    }

    public void setTotoroCount(int totoroCount) {
        this.totoroCount = totoroCount;
    }

    public void decrementTotoroCount(){
        this.totoroCount--;
    }

    public int getTigerCount(){
        return tigerCount;
    }

    public void setTigerCount(int tigerCount) {
        this.tigerCount = tigerCount;
    }

    public void decrementTigerCount() {
        this.tigerCount--;
    }
}

