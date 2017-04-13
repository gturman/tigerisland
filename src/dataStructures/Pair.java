package dataStructures;

/**
 * Created by James on 3/14/2017.
 */
public class Pair {
    private int columnPosition;
    private int rowPosition;

    public Pair(int columnPosition, int rowPosition){
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition(){
        return this.columnPosition;
    }

    public int getRowPosition(){
        return this.rowPosition;
    }

    public void setCoordinates(int columnPosition, int rowPosition){
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
    }
}
