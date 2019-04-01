package com.josh;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;        // required for Scanner
import org.json.simple.*;  // required for JSON encoding and decoding
import sun.rmi.runtime.Log;

public class Main extends Application {

    UI ui = new UI();

    PrintWriter out = null;
    BufferedReader in = null;
    BufferedReader stdIn = null;

    User user = null;
    List<Message> messageList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Connect();
        ui.ConstructLogin();
        ui.getBtnLogin().setOnAction(e->Login(ui.getTxtLogin().getText()));
    }

    public void Connect() {
        String hostName = "localhost";
        int portNumber = 12345;
        try{

            Socket socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void Login(String username) {
        Request req = null;

        try {
            req = new OpenRequest(username);
        } catch (NoSuchElementException e) {
            out.println("ILLEGAL COMMAND");
        }

        // Send request to server
        out.println(req);

        HandleServerResponse('o', null);
        user = new User(username, new ArrayList<User>());
    }

    public void SendMessage(String message) {
        Request req = null;
        Message msg = new Message(message, user.getName(), 0); //Need to work out "After" Variable

        try {
            req = new PublishRequest(user.getName(), msg);
        } catch (NoSuchElementException e) {
            out.println("ILLEGAL COMMAND");
        }

        // Send request to server
        out.println(req);

        HandleServerResponse('p', message);
    }

    public void SubscribeToChannel(String channel) {
        Request req = null;

        try {
            req = new SubscribeRequest(user.getName(), channel);
        } catch (NoSuchElementException e) {
            out.println("ILLEGAL COMMAND");
        }

        // Send request to server
        out.println(req);

        HandleServerResponse('s', channel);
    }

    public void UnsubscribeFromChannel(String channel) {
        Request req = null;

        try {
            req = new UnsubscribeRequest(user.getName(), channel);
        } catch (NoSuchElementException e) {
            out.println("ILLEGAL COMMAND");
        }

        // Send request to server
        out.println(req);

        HandleServerResponse('u', channel);
    }

    public void GetAllNewMessages() {
        Request req = null;

        try {
            req = new GetRequest(user.getName(), 0); //Need to work out "After" Variable
        } catch (NoSuchElementException e) {
            out.println("ILLEGAL COMMAND");
        }

        // Send request to server
        out.println(req);

        HandleServerResponse('g', null);
    }

    public void Quit() {
        Platform.exit();
    }

    public void HandleServerResponse(char reqType, String userInput) {
        // Read server response; terminate if null (i.e. server quit)
        String serverResponse = null;
        try {
            if ((serverResponse = in.readLine()) == null)
                return;
        } catch (IOException e) {
            System.err.println("Connection Error");
            System.exit(1);
        }

        // Parse JSON response, then try to deserialize JSON
        Object json = JSONValue.parse(serverResponse);
        Response resp;

        // Try to deserialize a success response
        if (SuccessResponse.fromJSON(json) != null) {
            switch (reqType) {
                case 'o':
                    ui.ConstructMainScreen();
                    ui.getBtnSend().setOnAction(e->SendMessage(ui.getTxtMsg().getText()));
                    ui.getBtnSub().setOnAction(e->SubscribeToChannel(ui.getTxtSub().getText()));
                    ui.getBtnUnsub().setOnAction(e->UnsubscribeFromChannel(ui.getTxtUnsub().getText()));
                    ui.getBtnGet().setOnAction(e->GetAllNewMessages());
                    ui.getBtnQuit().setOnAction(e->Quit());
                    break;
                case 'p':
                    ui.getTxtMsg().setText("");
                    break;
                case 's':
                    ui.getTxtSub().setText("");
                    user.addToSubList(new User(userInput, null));
                    Label sub = new Label(userInput);
                    sub.setFont(Font.font(24));
                    ui.getPaneLeft().getChildren().add(sub);
                    break;
                case 'u':
                    ui.getTxtUnsub().setText("");
                    user.RemoveSubByName(userInput);
                    ui.getPaneLeft().getChildren().clear();
                    ui.SubListLabel();

                    for (User user : user.getSubList()) {
                        Label lblSub = new Label(user.getName());
                        lblSub.setFont(Font.font(24));
                        ui.getPaneLeft().getChildren().add(lblSub);
                    }
                    break;
                case 'g':
                    // Try to deserialize a list of messages
                    if ((resp = MessageListResponse.fromJSON(json)) != null) {
                        for (Message m : ((MessageListResponse)resp).getMessages())
                            messageList.add(m);
                        return;
                    }
                    break;
            }
            return;
        }

        // Try to deserialize an error response
        if ((resp = ErrorResponse.fromJSON(json)) != null) {
            System.out.println(((ErrorResponse)resp).getError());
            Alert a = new Alert(Alert.AlertType.ERROR, ((ErrorResponse)resp).getError());
            a.show();
            return;
        }

        // Not any known response
        System.out.println("PANIC: " + serverResponse +
                " parsed as " + json);
    }
}
