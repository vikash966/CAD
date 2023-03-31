
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.io.IOException;
public class NimServer {
    public static void main(String[] args) {
        try {
            serverSock = new ServerSocket(7654);
            while (true) {
                //connect player 1
                player1 = serverSock.accept();
                clientInput1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                clientOutput1 = new BufferedWriter(new OutputStreamWriter(player1.getOutputStream()));

                input = clientInput1.readLine();
                if (input.startsWith("HELLO ")) {
                    clientOutput1.write("100\n");
                    clientOutput1.flush();
                    firstPlayer = input.substring(6);
                    System.out.println(firstPlayer + " is connected and waiting for opponent.");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


