/**
 * Created by Geoff on 3/8/17.
 */

public class Hex {
    private int hexID;
    private int hexLevel;
    private int parentTileID;
    private Pair hexCoordinate = new Pair(0, 0);
    private terrainTypes hexTerrainType;
    private int settlerCount;
    private int totoroCount;
    private int tigerCount;
    private int settlementID;
    private int playerID;
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

    public void setHexLevel(int hexLevel){
        this.hexLevel = hexLevel;
    }

    public Pair getHexCoordinate(){
        return hexCoordinate;
    }

    public void setTerrainType(terrainTypes newTerrain){
        this.hexTerrainType = newTerrain;
    }

    public terrainTypes getHexTerrainType(){
        return this.hexTerrainType;
    }

    public int getHexLevel() {
        return this.hexLevel;
    }

    public int getParentTileID() {
        return this.parentTileID;
    }

    public int getSettlerCount() {
        return settlerCount;
    }

    public int getTotoroCount() {
        return totoroCount;
    }

    public int getTigerCount() {
        return tigerCount;
    }

    public void setSettlerCount(int settlerCount) {
        this.settlerCount = settlerCount;
    }

    public void setTotoroCount(int totoroCount) {
        this.totoroCount = totoroCount;
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