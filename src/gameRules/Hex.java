package gameRules;

import enums.terrainTypes;
import dataStructures.Pair;

/**
 * Created by Geoff on 3/8/17.
 */

public class Hex {
    private int hexID;
    private int hexLevel;
    private int parentTileID;
    private Pair hexCoordinate = new Pair(0, 0);
    private terrainTypes hexTerrainType;
    private int playerID;
    private int settlementID;
    private int settlerCount;
    private int totoroCount;
    private int tigerCount;
    private boolean alreadyTraversed;

    public Hex(int hexID, int parentTileID, terrainTypes hexTerrainType) {
        this.hexID = hexID;
        this.hexLevel = 1;
        this.parentTileID = parentTileID;
        this.hexTerrainType = hexTerrainType;
        this.settlerCount = 0;
        this.totoroCount = 0;
        this.tigerCount = 0;
        this.settlementID = 0;
        this.playerID = 0;
        this.alreadyTraversed = false;
    }

    public boolean isNotBuiltOn(){
        return (settlerCount == 0 && totoroCount == 0 && tigerCount == 0);
    }

    public int getHexID() {
        return hexID;
    }

    public int getLevel() {
        return this.hexLevel;
    }

    public void setLevel(int hexLevel){
        this.hexLevel = hexLevel;
    }

    public Pair getCoordinatePair(){
        return hexCoordinate;
    }

    public terrainTypes getTerrainType(){
        return this.hexTerrainType;
    }

    public void setTerrainType(terrainTypes newTerrain){
        this.hexTerrainType = newTerrain;
    }

    public int getParentTileID() {
        return this.parentTileID;
    }

    public int getSettlerCount() {
        return settlerCount;
    }

    public void setSettlerCount(int settlerCount) {
        this.settlerCount = settlerCount;
    }

    public int getTotoroCount() {
        return totoroCount;
    }

    public void setTotoroCount(int totoroCount) {
        this.totoroCount = totoroCount;
    }

    public int getTigerCount() {
        return tigerCount;
    }

    public void setTigerCount(int tigerCount) {
        this.tigerCount = tigerCount;
    }

    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public boolean getIfAlreadyTraversed() {
        return this.alreadyTraversed;
    }

    public boolean setIfAlreadyTraversed(boolean value) {
        return this.alreadyTraversed = value;
    }
}