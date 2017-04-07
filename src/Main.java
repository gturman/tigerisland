import java.io.*;

/**
 * Created by geoff on 4/6/17.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        /* This is if we wanna do it from command line
        if (args.length != 5) {
            System.err.println(
                    "Usage: <host name> <port number> <tournament password>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String tournamentPassword = args[2];
        String username = args[3];
        String password = args[4];
        */

        String hostName, portNumber, tournamentPassword, username, password;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Preparing to enter Thunder Dome...");
        System.out.print("Enter IP: ");
        hostName = stdIn.readLine();
        System.out.print("Port: ");
        portNumber = stdIn.readLine();
        System.out.print("Tournament password: ");
        tournamentPassword = stdIn.readLine();
        System.out.print("Username: ");
        username = stdIn.readLine();
        System.out.print("Password: ");
        password = stdIn.readLine();

        Tournament ThunderDome = new Tournament(hostName, portNumber, tournamentPassword, username, password);

        if (ThunderDome.authentication() == "OK"){

            /* pseudocode for approach to play a full ThuderDome tournament

            boolean challengeDone == False;
            while (!challengeDone){

                //wait for "NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCHES"

                int totalRounds = Integer.parseInt(rounds);
                int roundsLeft = totalRounds;
                while (roundsLeft != 0){

                    //wait for "BEGIN ROUND <rid> OF <rounds> "
                    //wait for "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>"

                    boolean matchDone == False;
                    while (!matchDone){

                        //spin thread A for Game A AI player
                        //spin thread B for Game B AI player

                        //wait for a message from the server

                        //if the message is MAKE YOUR MOVE IN GAME <gid=GameA>
                            //tell thread A to make a move
                        //if the message is MAKE YOUR MOVE IN GAME <gid=GameB>
                            //tell thread B to make a move

                        //if the message is GAME <gid=GameA>
                            //tell thread A about the move
                        //if the message is GAME <gid=GameB>
                            //tell thread B about the move

                        //if the message is GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
                            matchDone = True;
                    }

                    //if the message is END OF ROUND <rid> OF <rounds> WAIT FOR THE NEXT MATCH
                        //roundsLeft--;
                    //if the message is END OF ROUND <rid> OF <rounds>
                        //break;

                }

                //wait for a message from the server
                //if the message is END OF CHALLENGES
                    done == True
                //if the message is WAIT FOR THE NEXT CHALLENGE TO BEGIN
                    done == False

            }
            */
        }

        // wait for THANK YOU FOR PLAYING! GOODBYE
        System.exit(0);
    }

}
