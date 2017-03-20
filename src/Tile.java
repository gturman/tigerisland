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
        tileID++;

            hexA = new Hex(hexID, tileID, hexTerrainA);
            hexID++;
            hexB = new Hex(hexID, tileID, hexTerrainB);
            hexID++;
            hexC = new Hex(hexID, tileID, hexTerrainC);
            hexID++;

    }

    public Hex getHexA(){
        return hexA;
    }
    public Hex getHexB(){
        return hexB;
    }
    public Hex getHexC(){
        return hexC;
    }
}