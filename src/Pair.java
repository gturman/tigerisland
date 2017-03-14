/**
 * Created by James on 3/14/2017.
 */
public class Pair {
    private int hexID;
    private int connectedEdgeID;

    public Pair(int hexID, int connectedEdgeID){
        this.hexID = hexID;
        this.connectedEdgeID = connectedEdgeID;
    }

    public int getPairHexID(){
        return this.hexID;
    }

    public int getPairEdge(){
        return this.connectedEdgeID;
    }

    public void setPairValues(int hexID, int connectedEdgeID){
        this.hexID = hexID;
        this.connectedEdgeID = connectedEdgeID;
    }
}
