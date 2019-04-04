package com.josh;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;        // required for Scanner
import java.util.List;

import org.json.simple.*;  // required for JSON encoding and decoding

public class Main extends Application {

    UI ui = new UI();

    PrintWriter out = null;
    BufferedReader in = null;
    BufferedReader stdIn = null;

    User user = null;
    List<Message> messageList = new ArrayList<>();
    int lastMessageRecieved = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Connect();
        ui.ConstructLogin();
        ui.getBtnLogin().setOnAction(e->Login(ui.getTxtLogin().getText()));
    }

    private void CheckRefresh() {
        Thread checkRefresh = new Thread(new Runnable() {
            @Override
            public void run() {
                while (user != null) {
                    try {
                        Thread.sleep(2000);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                GetAllNewMessages();
                            }
                        });
                    } catch (InterruptedException e) {}
                }
            }
        });
        checkRefresh.start();
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

        user = new User(username, new ArrayList<User>());
        user.addToSubList(user);
        CheckRefresh();

        HandleServerResponse('o', null);
    }

    public void SendMessage(String message) {
        Request req = null;
        Message msg = null;

        if(ui.getImageString() == null) {
            msg = new Message(message, user.getName(), 0);
        } else {
            msg = new Message(message, user.getName(), 0, ui.getImageString());
        }

        ui.setImageString(null);

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
            req = new GetRequest(user.getName(), lastMessageRecieved); //Need to work out "After" Variable
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
                    ui.ConstructMainScreen(user.getName());
                    ui.getBtnAddPic().setOnAction(e->ui.AddPicture(ui.getStage()));
                    ui.getBtnSend().setOnAction(e->SendMessage(ui.getTxtMsg().getText()));
                    ui.getBtnSub().setOnAction(e->SubscribeToChannel(ui.getTxtSub().getText()));
                    ui.getBtnUnsub().setOnAction(e->UnsubscribeFromChannel(ui.getTxtUnsub().getText()));


                    ui.getBtnSetPref().setOnAction(e->ui.showModal());



                    ui.getBtnQuit().setOnAction(e->Quit());
                    break;
                case 'p':
                    ui.getTxtMsg().setText("");
                    ui.getPaneCenter().getChildren().remove(ui.getImgViewPreview());
                    break;
                case 's':
                    ui.getTxtSub().setText("");
                    List<User> currentSubs = user.getSubList();
                    boolean alreadySubbed = false;
                    for (User sub : currentSubs) {
                        if(sub.getName().equals(userInput)) {
                            alreadySubbed = true;
                        }
                    }

                    if(alreadySubbed == false) {
                        user.addToSubList(new User(userInput, null));
                        ui.DrawNewSub(userInput);
                    }

                    break;
                case 'u':
                    ui.getTxtUnsub().setText("");
                    user.RemoveSubByName(userInput);
                    ui.getPaneLeft().getChildren().clear();
                    ui.SubListLabel();

                    for (User user : user.getSubList()) {
                        Text lblSub = new Text(user.getName());
                        lblSub.setFont(Font.font(24));
                        lblSub.setFill(Color.web(ui.textColor));
                        ui.getPaneLeft().getChildren().add(lblSub);
                    }
                    break;
            }
            return;
        }

        // Try to deserialize a list of messages
        if ((resp = MessageListResponse.fromJSON(json)) != null) {

//
//            ArrayList<Message> messages = messageList;
//
//            Message a = new Message("dd", "dd", 1);
//
//            ((int) a.getWhen()).compareTo(1);
//
//            Comparator<Message> compareByWhen = (Message o1, Message o2) -> o1.getWhen() .compareTo( o2.getWhen() );
//
//            Collections.sort(employees, compareById);
//
//            Collections.sort(employees, compareById.reversed());

            String messageBoxCSS = "-fx-border-color: #cecece;\n" +
                    "-fx-border-insets: 5;\n" +
                    "-fx-padding: 25;\n" +
                    "-fx-margin: 0;\n" +
                    "-fx-border-style: solid none solid none;\n" +
                    "-fx-border-width: 1;\n";

            List<Message> newMessages = new ArrayList<>();
            for (Message m : ((MessageListResponse)resp).getMessages()) {
                messageList.add(m);
                newMessages.add(m);
                if(m.getWhen() > lastMessageRecieved) lastMessageRecieved = (int)m.getWhen();
            }

            for (Message msg : newMessages) {

                VBox tempVbox = new VBox();
                tempVbox.setStyle(messageBoxCSS);
                tempVbox.setSpacing(10);

//                Text lblWhen = new Text("When (DEBUG) : " + Long.toString(msg.getWhen()));
                Text lblFrom = new Text(msg.getFrom());
                Text lblMsg = new Text(msg.getBody());

                lblFrom.setFont(Font.font(24));
                lblFrom.setFill(Color.web(ui.usernameColor));

//                lblWhen.setFill(Color.web(ui.textColor));
                lblMsg.setFill(Color.web(ui.textColor));
                tempVbox.getChildren().addAll( /*lblWhen,*/ lblFrom, lblMsg);

                if(!msg.getPic().equals("")) {
                    Image img = new Image(new ByteArrayInputStream(msg.decodeImage()));
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(500);
                    imgView.setFitHeight(500);
                    imgView.setPreserveRatio(true);
                    tempVbox.getChildren().add(imgView);
                }

                ui.getListOfMsgUsernames().add(lblFrom);
                ui.getListOfMsgs().add(lblMsg);
                ui.getPaneMessages().getChildren().add(tempVbox);
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
