import java.io.*;
import java.net.*;

/**
 * Created by geoff on 4/4/17.
 */
public class TournamentMockServer {

    public static String processInput(String input) throws InterruptedException{
        String output;

	System.out.println("sanity, processing input...");

	System.out.println("does (" + input + ") equal (ENTER THUNDERDOME password123)");
	if (input.equals("ENTER THUNDERDOME password123")) {

	    Thread.sleep(1000);

	    output = "TWO SHALL ENTER, ONE SHALL LEAVE";
	    return output;
	}

	System.out.println("does (" + input + ") equal (I AM username password)");
	if (input.equals("I AM username password")) {
	    output = "WAIT FOR THE TOURNAMENT TO BEGIN pid";
	    return output;
	}

	if (input == null) {
            output = "THANK YOU FOR PLAYING! GOODBYE";
        }
        else output = null;

        return output;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        try(
        ServerSocket serverSocket = new ServerSocket(4444);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ){

            String inputLine, outputLine;
            out.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");

            while ((inputLine = in.readLine()) != null) {

                outputLine = processInput(inputLine);
		if (outputLine == null) {
			System.out.println("Had trouble processing input,"+
					" was null, exiting...");
			break;
		}
                if (outputLine.equals("THANK YOU FOR PLAYING! GOODBYE"))
                    break;
		out.println(outputLine);

            }

        } catch( IOException e)
        {
            System.out.println("Exception caught when trying to listen on port "
                    + 4444 + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
