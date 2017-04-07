import java.io.IOException;

/**
 * Created by geoff on 4/6/17.
 */

public class Tournament {

    public TournamentClient client;
    public String tournamentPassword;
    public String username;
    public String password;

    public String authentication() throws IOException {

        String messageFromServer;
        String messageFromClient;

        Decoder decode = new Decoder();

        messageFromServer = client.waitAndReceive();
        if (messageFromServer.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")){
            messageFromClient = "ENTER THUNDERDOME " + tournamentPassword;
            client.send(messageFromClient);

            messageFromServer = client.waitAndReceive();
            if (messageFromServer.equals("TWO SHALL ENTER, ONE SHALL LEAVE")) {
                messageFromClient = "I AM " + username + " " + password;
                client.send(messageFromClient);

                messageFromServer = client.waitAndReceive();
                //TODO: need to fix decode method
                //int pid = decode.messageStartsWithWait(messageFromServer);
                String pid = "pid";
                if (messageFromServer.equals("WAIT FOR THE TOURNAMENT TO BEGIN " + pid)) {
                    return "OK";
                }
            }
        }

        // if we don't get the messages correctly, in order
        return null;
    }

    Tournament(String hostname, String port, String tournamentPassword,
               String username, String password) throws IOException {

        client = new TournamentClient(hostname, port);
        this.tournamentPassword = tournamentPassword;
        this.username = "username";
        this.password = "password";

    }
}
