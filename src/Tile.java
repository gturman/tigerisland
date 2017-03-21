/**
 * Created by KJ on 3/14/2017.
 */
public class Tile {
    private int tileID;
    private Hex hexA;
    private Hex hexB;
    private Hex hexC;

    public Tile(int tileID, int hexID, terrainTypes hexTerrainA, terrainTypes hexTerrainB, terrainTypes hexTerrainC) {
        this.tileID = tileID;

            hexA = new Hex(tileID, hexID, hexTerrainA);
            hexID++;
            hexB = new Hex(tileID, hexID, hexTerrainB);
            hexID++;
            hexC = new Hex(tileID, hexID, hexTerrainC);
            hexID++;

    }

    public void tileRotationClockwise(int numberOfRotations){
        terrainTypes temp;
        if(numberOfRotations%3 == 1){
            temp = this.hexA.getHexTerrainType();
            this.hexA.setTerrainType(this.hexC.getHexTerrainType());
            this.hexC.setTerrainType(this.hexB.getHexTerrainType());
            this.hexB.setTerrainType(temp); //has to be in this order
        }else if(numberOfRotations%3 == 2){
            temp = this.hexA.getHexTerrainType();
            this.hexA.setTerrainType(this.hexB.getHexTerrainType());
            this.hexB.setTerrainType(this.hexC.getHexTerrainType());
            this.hexC.setTerrainType(temp);
        }
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
}