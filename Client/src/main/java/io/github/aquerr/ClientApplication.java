package io.github.aquerr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientApplication extends Application implements Runnable
{
//    private ObservableList<String> chatList = FXCollections.observableArrayList();
    private List<Label> messages = new ArrayList<>();
    private VBox chatBox = null;
    private ScrollPane scrollPaneChat = null;
    private int messageIndex = 0;
    private PrintWriter serverPrintWriter = null;
    private TextField inputField = null;
    private Button connectButton = null;

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
        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#2F3136"), null, null)));
        Scene scene = new Scene(gridPane, 300, 300);

        connectButton = new Button("Połącz");
        connectButton.setMinWidth(100);
        connectButton.setOnAction(connect());
        connectButton.setBackground(new Background(new BackgroundFill(Color.web("#D2D3D3"), null, null)));
//        Text text = new Text();
//        text.setWrappingWidth(10);
//        ListView<String> chatList = new ListView<>();
//        chatList.setItems(this.chatList);
//        chatList.setMinWidth(150);
//        chatList.setMinHeight(300);
        chatBox = new VBox();
        chatBox.setMinWidth(370);
        chatBox.setMinHeight(400);
        chatBox.setBackground(new Background(new BackgroundFill(Color.web("#36393F"), null, null)));
        inputField = new TextField();
        inputField.setPromptText("Enter message here...");
        inputField.setOnAction(onTextEnter());
        inputField.setBackground(new Background(new BackgroundFill(Color.web("#36393F"), null, null)));
        inputField.setStyle("-fx-text-fill: #ffffff");
        scrollPaneChat = new ScrollPane();
        scrollPaneChat.setPrefSize(400, 400);
        scrollPaneChat.setContent(chatBox);
        scrollPaneChat.setFitToWidth(true);
        scrollPaneChat.setStyle("-fx-background-color: black;\n" +
                "-fx-background-radius: 5em");
        scrollPaneChat.setStyle("-fx-background-color: black");
//        scrollPaneChat.setVisible(true);
//        scrollPaneChat.setFitToHeight(true);
//        scrollPaneChat.setFitToWidth(true);

//        gridPane.add(chatBox, 1, 0);
        gridPane.add(scrollPaneChat, 1, 0);
        gridPane.add(connectButton, 0, 1);
        gridPane.add(inputField, 1, 1);


        primaryStage.setTitle("Nerdi's Chat Client");
        primaryStage.setResizable(false);
        primaryStage.setWidth(500);
        primaryStage.setHeight(450);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                System.exit(0);
//                Platform.exit();
            }
        });
    }

    public EventHandler<ActionEvent> connect()
    {
        return event ->
        {
            if(this.isConnected)
                return;
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
            Socket socket = new Socket("bartlomiejstepien.pl", 25568);

            serverPrintWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.isConnected = true;
            connectButton.setDisable(true);

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
                        Label label = new Label(finalLine);
                        label.setWrapText(true);
                        label.setTextFill(Color.WHITESMOKE);
                        messages.add(label);
                        chatBox.getChildren().add(messageIndex, messages.get(messageIndex));
                        scrollPaneChat.setVvalue(scrollPaneChat.getVmax());
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
