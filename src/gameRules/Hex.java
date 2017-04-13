package gameRules;

import enums.terrainTypes;
import dataStructures.Pair;

/**
 * Created by Geoff on 3/8/17.
 */

public class Hex {
    private int hexID;
    private int parentTileID;
    private Pair hexCoordinate = new Pair(0, 0);
    private int hexLevel;
    private terrainTypes hexTerrainType;
    private int owningPlayerID;
    private int settlementID;
    private int villagerCount;
    private int totoroCount;
    private int tigerCount;
    private boolean alreadyTraversed;

    public Hex(int hexID, int parentTileID, terrainTypes hexTerrainType) {
        this.hexID = hexID;
        this.parentTileID = parentTileID;
        this.hexLevel = 1;
        this.hexTerrainType = hexTerrainType;
        this.owningPlayerID = 0;
        this.settlementID = 0;
        this.villagerCount = 0;
        this.totoroCount = 0;
        this.tigerCount = 0;
        this.alreadyTraversed = false;
    }

    public int getHexID() {
        return hexID;
    }

    public int getParentTileID() {
        return this.parentTileID;
    }

    public Pair getCoordinatePair(){
        return hexCoordinate;
    }

    public int getLevel() {
        return this.hexLevel;
    }

    public void setLevel(int hexLevel){
        this.hexLevel = hexLevel;
    }

    public terrainTypes getTerrainType(){
        return this.hexTerrainType;
    }

    public void setTerrainType(terrainTypes newTerrain){
        this.hexTerrainType = newTerrain;
    }

    public int getOwningPlayerID() {
        return owningPlayerID;
    }

    public void setOwningPlayerID(int owningPlayerID) {
        this.owningPlayerID = owningPlayerID;
    }

    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public int getVillagerCount() {
        return villagerCount;
    }

    public void setVillagerCount(int villagerCount) {
        this.villagerCount = villagerCount;
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

    public boolean getIfAlreadyTraversed() {
        return this.alreadyTraversed;
    }

    public boolean setIfAlreadyTraversed(boolean value) {
        return this.alreadyTraversed = value;
    }

    public boolean isNotBuiltOn(){
        return (villagerCount == 0 && totoroCount == 0 && tigerCount == 0);
    }
}