/**
 * Created by William on 3/27/2017.
 */
public class Game {
    /*
    This class will handle the separate actions of updating GameBoard and updating Player.
    Basically this simulates Main, where we will have two players and a gameBoard.
    I wrote the functions here to avoid potential errors.
     */

    public Player playerOne = new Player(1);
    public Player playerTwo = new Player(2);
    public GameBoard gameBoard = new GameBoard();

    Game(){

    }

    //build a settlement: check if valid. if it is valid, update gameBoard, update player

    public void buildSettlement(int colPos, int rowPos, Player playerBuildingSettlement, GameBoard gameBoard){
        if(gameBoard.isValidSettlementLocation(colPos, rowPos)){
            gameBoard.buildSettlement(colPos, rowPos, playerBuildingSettlement);
        }
    }

}
