package io.github.aquerr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientApplication extends Application implements Runnable
{
//    private ObservableList<String> chatList = FXCollections.observableArrayList();
    private List<Label> messages = new ArrayList<>();
    VBox chatBox = null;
    private int messageIndex = 0;
    private PrintWriter serverPrintWriter = null;
    private TextField inputField = null;

    private boolean isConnected = false;

    private Thread chatThread = new Thread(this::run);

    public void launchClient(String... args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(300);
        gridPane.setMinHeight(300);
        gridPane.setGridLinesVisible(true);
        Scene scene = new Scene(gridPane, 300, 300);

        Button connectButton = new Button("Połącz");
        connectButton.setMinWidth(100);
        connectButton.setOnAction(connect());
//        Text text = new Text();
//        text.setWrappingWidth(10);
//        ListView<String> chatList = new ListView<>();
//        chatList.setItems(this.chatList);
//        chatList.setMinWidth(150);
//        chatList.setMinHeight(300);
        chatBox = new VBox();
        chatBox.setMinWidth(400);
        chatBox.setMinHeight(400);
        inputField = new TextField();
        inputField.setPromptText("Enter message here...");
        inputField.setOnAction(onTextEnter());


        gridPane.add(chatBox, 1, 0);
        gridPane.add(connectButton, 0, 1);
        gridPane.add(inputField, 1, 1);


        primaryStage.setTitle("Nerdi Chat Client");
        primaryStage.setResizable(false);
        primaryStage.setWidth(500);
        primaryStage.setHeight(450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public EventHandler<ActionEvent> connect()
    {
        return event ->
        {
            this.chatThread.start();
        };
    }

    public EventHandler<ActionEvent> onTextEnter()
    {
        return event ->
        {
            if(!this.isConnected)
                return;

            String messageToSent = inputField.getText();
            this.serverPrintWriter.println(messageToSent);
            this.serverPrintWriter.flush();
            inputField.clear();
        };
    }

    @Override
    public void run()
    {
        try
        {
            this.isConnected = true;
            Socket socket = new Socket("bartlomiejstepien.pl", 25568);

            serverPrintWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedReader consoleInputStream = new BufferedReader(new InputStreamReader(System.in));

//            ChatReceiver chatReceiver = new ChatReceiver(inputStream);
//            chatReceiver.startListening();

            //Sending to server
//            String messageFromClient;
//            while((messageFromClient = consoleInputStream.readLine()) != null)
//            {
//                chatList.add(messageFromClient);
//                serverPrintWriter.println(messageFromClient);
//                serverPrintWriter.flush();
//            }
            String line;
            while((line = inputStream.readLine()) != null)
            {
                String finalLine = line;
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        messages.add(new Label(finalLine));
                        chatBox.getChildren().add(messageIndex, messages.get(messageIndex));
                        messageIndex++;
                    }
                });
            }

            //Getting from server
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
