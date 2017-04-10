import java.io.*;
import java.net.*;

/**
 * Created by geoff on 4/4/17.
 */
public class TournamentMockServer {

    public static PrintWriter out;

    public static void processInput(String input){
        if (input.equals("ENTER THUNDERDOME heygang")){
            out.println("TWO SHALL ENTER, ONE SHALL LEAVE");
        }

        if (input.equals("I AM F F")){
            out.println("WAIT FOR THE TOURNAMENT TO BEGIN 1");
            out.println("NEW CHALLENGE 0 YOU WILL PLAY 7 MATCHES");
            out.println("BEGIN ROUND 1 OF 7");
            out.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 8");
            out.println("MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
        }

        if (input.equals("GAME A MOVE 0 PLACE GRASS+LAKE AT -2 2 0 1 FOUND SETTLEMENT AT 0 1 -1")){
            out.println("GAME A MOVE 0 PLAYER 1 PLACED GRASS+LAKE AT -2 2 0 1 FOUNDED SETTLEMENT AT 0 1 -1");
            out.println("GAME B MOVE 0 PLAYER 8 PLACED GRASS+LAKE AT 2 0 -2 3 FOUNDED SETTLEMENT AT 0 -1 1");
            out.println("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE ROCK+JUNGLE");
        }

        if (input.equals("GAME B MOVE 0 PLACE ROCK+JUNGLE")){

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {


        ServerSocket serverSocket = new ServerSocket(4444);
        Socket clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;

        out.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");

        while ((inputLine = in.readLine())!= null){
            processInput(inputLine);
        }

    }

}
