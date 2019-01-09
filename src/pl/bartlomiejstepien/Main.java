package pl.bartlomiejstepien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7);

            Socket client = serverSocket.accept();
            PrintWriter outWriter = new PrintWriter(client.getOutputStream(), true);
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String line;
            while ((line = clientReader.readLine()) != null)
            {
                //Response with the same message
                outWriter.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
