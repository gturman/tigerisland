
import org.junit.*;
import java.io.*;

/**
 * Created by geoff on 4/6/17.
 */
public class TournamentTest {

    private Tournament ThunderDome;

    @Before
    public void testTournamentInit() throws IOException {
        //assume there is a server running on localhost 4444


        ThunderDome = new Tournament("localhost","4444", "password123",
                "username", "password");
        Assert.assertTrue(ThunderDome instanceof Tournament);
    }

    @Test
    public void testTournamentAuthentication() throws IOException {

        Assert.assertTrue(ThunderDome.authentication() == "OK");

    }
}
