/**
 * Created by KJ on 3/14/2017.
 */
public class Tile {

    private int tileID;
    private Hex hexA;
    private Hex hexB;
    private Hex hexC;
    // todo: tileID and hexID will eventually be static global variables
    public Tile(int tileID, int hexID, terrainTypes hexTerrainA, terrainTypes hexTerrainB, terrainTypes hexTerrainC) {
        this.tileID = tileID;
        tileID++;
        hexA = new Hex(hexID, hexTerrainA);
        hexID++;
        hexB = new Hex(hexID, hexTerrainB);
        hexID++;
        hexC = new Hex(hexID, hexTerrainC);
        hexID++;

        hexA.mergeHexes(hexB, 2, 5);
        hexA.mergeHexes(hexC, 3, 0);
        hexB.mergeHexes(hexC, 4, 1);
    }

    public int getTileID(){
        return tileID;
    }
}