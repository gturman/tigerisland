package tournament;

import tournament.Client;
import tournament.Decoder;

import java.io.IOException;

public class TournamentClient {

    public Client client;
    private String tournamentPassword;
    private String username;
    private String password;
    public String currentMessage;
    //public tournament.Decoder mainDecoder;

    public void send(String message) {
        client.send(message);
    }

    public String authenticateConnectionAndGetPlayerID() throws IOException {

        Decoder mainDecoder = new Decoder();
        String messageFromServer;
        String messageFromClient;

        System.out.println("Waiting for first tournament message...");
        messageFromServer = client.waitAndReceive();
        if (messageFromServer.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")){
            messageFromClient = "ENTER THUNDERDOME " + tournamentPassword;
            client.send(messageFromClient);

            messageFromServer = client.waitAndReceive();
            if (messageFromServer.equals("TWO SHALL ENTER, ONE SHALL LEAVE")) {
                messageFromClient = "I AM " + username + " " + password;
                client.send(messageFromClient);

                messageFromServer = client.waitAndReceive();
                mainDecoder.messageStartsWithWait(messageFromServer);
                String pid = mainDecoder.getPlayerID1();
                if (messageFromServer.equals("WAIT FOR THE TOURNAMENT TO BEGIN " + pid)) {
                    return pid;
                }
            }
        }

        // if we don't get the messages correctly, in order
        System.out.println("Unable to authenticate...");
        return "we're bad";
    }

    public TournamentClient(String hostname, String port, String tournamentPassword,
                     String username, String password) throws IOException {

        client = new Client(hostname, port);
        System.out.println("Connected...");

        this.tournamentPassword = tournamentPassword;
        this.username = username;
        this.password = password;
    }
}
