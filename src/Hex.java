/**
 * Created by Geoff on 3/8/17.
 */

import java.util.*;

public class Hex {
    //this is... the BEGINNING!

    private int hexID;
    private int hexLevel;
    private Pair[] hexEdgeList = new Pair[6];
    private terrainTypes hexTerrainType;

    public Hex(int hexID, int hexLevel, terrainTypes hexTerrainType)
    {
        this.hexID = hexID;
        this.hexLevel = hexLevel;
        for(int i = 0; i < 6; i++)
        {
            hexEdgeList[i] = new Pair(-1, -1);
        }
        this.hexTerrainType = hexTerrainType;

    }

    public void mergeHexes(Hex firstHex, Hex secondHex, int firstEdge, int secondEdge){
        if(firstEdge > 6 || firstEdge < 0 || secondEdge > 6 || secondEdge < 0){
            throw new RuntimeException("Edge index is outside of valid edge list index");
        }
        firstHex.getHexEdgeList()[firstEdge].setPairValues(secondHex.getHexID(), secondEdge);
        secondHex.getHexEdgeList()[secondEdge].setPairValues(firstHex.getHexID(), secondEdge);
    }

    public Pair[] getHexEdgeList(){
        return hexEdgeList;
    }

    public int getHexID() {
        return hexID;
    }

   /* public int getConnectedHexEdge(Pair[] hexEdgeList, int edge){
        return hexEdgeList[edge].getConnectedEdgeID();
    }*/




}
