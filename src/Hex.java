/**
 * Created by Geoff on 3/8/17.
 */

import java.util.*;

public class Hex {
    //this is... the BEGINNING!

    private int hexID;
    private int hexLevel;
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

}
