/**
 * Created by Geoff on 3/8/17.
 */

import java.util.*;

public class Hex {
    //this is... the BEGINNING!

    private int hexID;
    private int hexLevel;

    /*
    NOTE: with a 2D array representation, hexEdgeList is not needed
    */
    //index 1 = upper-left, 2 = upper-right, 3 = right, 4 = bottom-right, 5 = bottom-left, 6 = left
    private int[] hexEdgeList = new int[6];


    private terrainTypes hexTerrainType;

    public Hex(int hexID, int hexLevel, terrainTypes hexTerrainType)
    {
        this.hexID = hexID;
        this.hexLevel = hexLevel;
        for(int i = 0; i < 6; i++)
        {
            hexEdgeList[i] = -1;
        }
        this.hexTerrainType = hexTerrainType;

    }

    public terrainTypes getHexTerrainType() {
        return hexTerrainType;
    }
}
