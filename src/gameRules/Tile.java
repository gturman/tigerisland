package gameRules;

import enums.terrainTypes;

/**
 * Created by KJ on 3/14/2017.
 */
public class Tile {
    private int tileID;
    private Hex hexA;
    private Hex hexB;
    private Hex hexC;

    private boolean isFlipped;

    public Tile(int tileID, int hexID, terrainTypes hexTerrainA, terrainTypes hexTerrainB, terrainTypes hexTerrainC) {
        this.tileID = tileID;

            hexA = new Hex(hexID, tileID, hexTerrainA);
            hexID++;
            hexB = new Hex(hexID, tileID, hexTerrainB);
            hexID++;
            hexC = new Hex(hexID, tileID, hexTerrainC);
            hexID++;

    }

    public int getTileID() {
        return this.tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public Hex getHexA(){
        return this.hexA;
    }
    public Hex getHexB(){
        return this.hexB;
    }
    public Hex getHexC(){
        return this.hexC;
    }

    public void rotateTileClockwise(int numberOfRotations){
        terrainTypes temp;
        if(this.isFlipped()) {
            if(numberOfRotations%3 == 1){
                temp = this.hexA.getTerrainType();
                this.hexA.setTerrainType(this.hexB.getTerrainType());
                this.hexB.setTerrainType(this.hexC.getTerrainType());
                this.hexC.setTerrainType(temp); //has to be in this order
            }else if(numberOfRotations%3 == 2){
                temp = this.hexA.getTerrainType();
                this.hexA.setTerrainType(this.hexC.getTerrainType());
                this.hexC.setTerrainType(this.hexB.getTerrainType());
                this.hexB.setTerrainType(temp);
            }
        }else{
            if(numberOfRotations%3 == 1){
                temp = this.hexA.getTerrainType();
                this.hexA.setTerrainType(this.hexC.getTerrainType());
                this.hexC.setTerrainType(this.hexB.getTerrainType());
                this.hexB.setTerrainType(temp); //has to be in this order
            }else if(numberOfRotations%3 == 2){
                temp = this.hexA.getTerrainType();
                this.hexA.setTerrainType(this.hexB.getTerrainType());
                this.hexB.setTerrainType(this.hexC.getTerrainType());
                this.hexC.setTerrainType(temp);
            }
        }

    }

    public void flip() {
        isFlipped = (isFlipped) ? false : true;

        Hex temp = this.hexB;
        this.hexB = this.hexC;
        this.hexC = temp;
    }

    public boolean isFlipped() {
        return isFlipped;
    }
}