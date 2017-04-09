import java.io.IOException;

/**
 * Created by geoff on 4/6/17.
 */

public class TournamentClient {

    public Client client;
    public String tournamentPassword;
    public String username;
    public String password;
    public Decoder mainDecoder;
    public String currentMessage;

    public void send(String message) {
        client.send(message);
    }

    public void waitReceiveAndDecode() throws IOException {

        this.mainDecoder = new Decoder();
        String fromServer;
        currentMessage = client.waitAndReceive();
        mainDecoder.decodeString(currentMessage);

    }

    public String authentication() throws IOException {

        this.mainDecoder = new Decoder();
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
                //String pid = "pid";
                if (messageFromServer.equals("WAIT FOR THE TOURNAMENT TO BEGIN " + pid)) {
                    return "OK";
                }
            }
        }

        // if we don't get the messages correctly, in order
        return null;
    }

    TournamentClient(String hostname, String port, String tournamentPassword,
                     String username, String password) throws IOException {

        client = new Client(hostname, port);

        System.out.println("Connected...");

        this.tournamentPassword = tournamentPassword;
        this.username = username;
        this.password = password;

    }
}
