/**
 * Created by William on 3/14/2017.
 */
public class Tile {

    //Tiles contain three Hexes
    private Hex hexOne,hexTwo,hexThree;
    private Hex[] hexes = new Hex[3];

    Tile(Hex one, Hex two, Hex three) {
        this.hexes[0] = one;
        this.hexes[1] = two;
        this.hexes[2] = three;

    }

    //returns a hex of a particular value
    public Hex getHex(int index){
        return hexes[index];
    }

    //returns an array containing all hexes that make up the tile
    public Hex[] getHexes() {
        return hexes;
    }
}
