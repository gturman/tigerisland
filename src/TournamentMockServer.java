import java.io.*;
import java.net.*;

/**
 * Created by geoff on 4/4/17.
 */
public class TournamentMockServer {

    public static PrintWriter out;
    public static int counter;

    public static void processInput(String input){
        if (counter == 0 && input.equals("ENTER THUNDERDOME heygang")){
            out.println("TWO SHALL ENTER, ONE SHALL LEAVE");
        }

        if (counter == 1 && input.equals("I AM F F")){
            out.println("WAIT FOR THE TOURNAMENT TO BEGIN 1");
            out.println("NEW CHALLENGE 0 YOU WILL PLAY 7 MATCHES");
            out.println("BEGIN ROUND 1 OF 7");
            out.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 8");
            out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
        }

        if (counter == 2 && input.equals("GAME A MOVE 0 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")){
            out.println("GAME A MOVE 0 PLAYER 1 PLACED GRASS+LAKE AT -2 2 0 1 FOUNDED SETTLEMENT AT 0 1 -1");
            out.println("GAME B MOVE 0 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE ROCK+JUNGLE");
        }

        if (counter == 3 && input.equals("GAME B MOVE 1 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")){
            out.println("GAME A MOVE 1 PLAYER 1 PLACED GRASS+LAKE AT -2 2 0 1 FOUNDED SETTLEMENT AT 0 1 -1");
            out.println("GAME B MOVE 1 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 2 PLACE GRASS+LAKE");
        }

        if (counter == 4 && input.equals("GAME A MOVE 2 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")){
            out.println("GAME A MOVE 2 PLAYER 1 PLACED GRASS+LAKE AT -2 2 0 1 FOUNDED SETTLEMENT AT 0 1 -1");
            out.println("GAME B MOVE 2 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 3 PLACE GRASS+LAKE");
        }

        if (counter == 5 && input.equals("GAME B MOVE 3 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")){
            out.println("GAME A MOVE 3 PLAYER 1 PLACED GRASS+LAKE AT -2 2 0 1 FOUNDED SETTLEMENT AT 0 1 -1");
            out.println("GAME B MOVE 3 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 4 PLACE GRASS+LAKE");
        }

        if (counter == 6 && input.equals("GAME A MOVE 4 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")){
            out.println("GAME A MOVE 4 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("GAME B MOVE 4 PLAYER 1 FORFEITED: TIMEOUT");
            out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 5 PLACE ROCK+JUNGLE");
        }

        if (counter == 7 && input.equals("GAME A MOVE 5 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")) {
            out.println("GAME A MOVE 5 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("GAME A MOVE 5 PLAYER 1 FORFEITED: TIMEOUT");
            out.println("GAME A OVER PLAYER 1 FORFEITED PLAYER 8 WIN");
            out.println("GAME B OVER PLAYER 1 FORFEITED PLAYER 8 WIN");
            out.println("END OF ROUND 1 OF 3 WAIT FOR NEXT MATCH");
            out.println("BEGIN ROUND 2 OF 3");
            out.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 6");
            out.println("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
        }

        if (counter == 8 && input.equals("GAME B MOVE 0 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")) {
            out.println("GAME A MOVE 0 PLAYER 6 FORFEITED: TIMEOUT");
            out.println("GAME B MOVE 0 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");

            out.println("GAME B MOVE 1 PLAYER 6 FORFEITED: TIMEOUT");

            out.println("GAME A OVER PLAYER 6 FORFEITED PLAYER 8 WIN");
            out.println("GAME B OVER PLAYER 6 FORFEITED PLAYER 8 WIN");

            out.println("END OF ROUND 2 OF 3 WAIT FOR NEXT MATCH");
            out.println("BEGIN ROUND 3 OF 3");
            out.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 3");
            out.println("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
        }

        if (counter == 9 && input.equals("GAME A MOVE 0 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")) {
            out.println("GAME A MOVE 0 PLAYER 3 FORFEITED: TIMEOUT");
            out.println("GAME B MOVE 0 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");

            out.println("GAME B MOVE 1 PLAYER 3 FORFEITED: TIMEOUT");

            out.println("GAME A OVER PLAYER 3 FORFEITED PLAYER 8 WIN");
            out.println("GAME B OVER PLAYER 3 FORFEITED PLAYER 8 WIN");

            out.println("END OF ROUND 3 OF 3");
            out.println("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
            out.println("NEW CHALLENGE 0 YOU WILL PLAY 1 MATCH");
            out.println("BEGIN ROUND 1 OF 1");
            out.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 3");
            out.println("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
        }

        if (counter == 10 && input.equals("GAME A MOVE 0 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")) {
            out.println("GAME A MOVE 0 PLAYER 3 FORFEITED: TIMEOUT");
            out.println("GAME B MOVE 0 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");

            out.println("GAME B MOVE 1 PLAYER 3 FORFEITED: TIMEOUT");

            out.println("GAME A OVER PLAYER 3 FORFEITED PLAYER 8 WIN");
            out.println("GAME B OVER PLAYER 3 FORFEITED PLAYER 8 WIN");

            out.println("END OF ROUND 1 OF 1");
            out.println("END OF CHALLENGES");
            out.println("THANK YOU FOR PLAYING! GOODBYE");

        }

        counter++;
    }

    public static void main(String[] args) throws IOException, InterruptedException {


        ServerSocket serverSocket = new ServerSocket(4444);
        Socket clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        counter = 0;

        out.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");

        while ((inputLine = in.readLine())!= null){
            processInput(inputLine);
        }

    }

}
