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

    public int getHexID(){
        return this.hexID;
    }

    public int getConnectedEdgeID(){
        return this.connectedEdgeID;
    }

    public void setPairValues(int hexID, int connectedEdgeID){
        this.hexID = hexID;
        this.connectedEdgeID = connectedEdgeID;
    }






}
