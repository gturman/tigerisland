/**
 * Created by geoff on 4/4/17.
 */

import java.io.*;
import java.net.*;

public class Client {

    public Socket Socket;
    public PrintWriter out;
    public BufferedReader in;

    Client(String hostname, String port){

        int portNumber = Integer.parseInt(port);

        try{
            Socket = new Socket(hostname, portNumber);
            out = new PrintWriter(Socket.getOutputStream(),
                    true);
            in = new BufferedReader(new InputStreamReader(
                    Socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Server ERROR: Don't know about host " + hostname);
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("Server ERROR: Unable to connect to " + hostname);
            System.out.println("If you're just running tests, make sure to run" +
                            " \"java TournamentMockServer\". If we're trying to" +
                            " connect to Dave's server, there's something wrong!");
            System.exit(1);
        }

    }

    public String waitAndReceive() throws IOException {

        String fromServer;

        //System.out.println("Waiting on server...");
        fromServer = in.readLine();
        if (fromServer != null) {
            System.out.print("Got: ");
            System.out.println(fromServer);
            return fromServer;
        }
        else {
            return null;
        }
    }

    public void send(String message) {
        System.out.println("Sent: "+message);
        out.println(message);
    }

}
