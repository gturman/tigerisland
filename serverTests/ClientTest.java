/**
 * Created by geoff on 4/4/17.
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ClientTest {

    private Client client;

    @Before
    public void testClientConnectionToServer() {
        //assume there is a server running on localhost 4444
        client = new Client("localhost","4444");
        Assert.assertTrue(client instanceof Client);
    }

    @Test
    public void testCommunicationExample() throws IOException {
        String fromClient;
        String fromServer;

        String tournamentPassword = "password123";

        fromServer = client.waitAndReceive();
        Assert.assertTrue(fromServer.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!"));

        fromClient = "ENTER THUNDERDOME " + tournamentPassword;
        client.send(fromClient);

        fromServer = client.waitAndReceive();
        Assert.assertTrue(fromServer.equals("TWO SHALL ENTER, ONE SHALL LEAVE"));


    }

}
