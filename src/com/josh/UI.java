package com.josh;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UI {

    //Login Screen
    private Stage stage = new Stage();
    private BorderPane root;
    private BorderPane paneHeader;

    private Label lblTitle;
    private Button btnLogin;
    private Button btnQuit;
    private TextField txtLogin;

    //Main Screen
    private VBox paneLeft;
    private VBox paneRight;
    private VBox paneCenter;

    private TextArea txtMsg;
    private Button btnSend;

    private TextField txtSub;
    private Button btnSub;

    private TextField txtUnsub;
    private Button btnUnsub;

    private Button btnGet;

    public Stage getStage() {
        return stage;
    }

    public BorderPane getRoot() {
        return root;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public TextField getTxtLogin() {
        return txtLogin;
    }

    public TextArea getTxtMsg() {
        return txtMsg;
    }

    public Button getBtnSend() {
        return btnSend;
    }

    public TextField getTxtSub() {
        return txtSub;
    }

    public Button getBtnSub() {
        return btnSub;
    }

    public TextField getTxtUnsub() {
        return txtUnsub;
    }

    public Button getBtnUnsub() {
        return btnUnsub;
    }

    public Button getBtnGet() {
        return btnGet;
    }

    public Button getBtnQuit() {
        return btnQuit;
    }

    public BorderPane getPaneHeader() {
        return paneHeader;
    }

    public VBox getPaneLeft() {
        return paneLeft;
    }

    public VBox getPaneRight() {
        return paneRight;
    }

    public VBox getPaneCenter() {
        return paneCenter;
    }

    public Label getLblTitle() {
        return lblTitle;
    }

    public void ConstructLogin() {
        LoginTab();

        Scene scene = new Scene(root);

        stage.setMaximized(true);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private void LoginTab() {
        btnLogin = new Button("Login");
        btnQuit = new Button("Quit");
        txtLogin = new TextField();
        lblTitle = new Label("Astro Board");
        lblTitle.setFont(Font.font(50));
        root = new BorderPane();

        FlowPane paneLoginDetails = new FlowPane(txtLogin, btnLogin);
        paneLoginDetails.setHgap(50);
        paneLoginDetails.setAlignment(Pos.CENTER);

        FlowPane paneQuit = new FlowPane(btnQuit);
        paneQuit.setHgap(50);
        paneQuit.setAlignment(Pos.CENTER);

        paneHeader = new BorderPane();
        paneHeader.setLeft(paneQuit);
        paneHeader.setCenter(lblTitle);
        paneHeader.setRight(paneLoginDetails);

        //Test Colors
        paneQuit.setBackground(new Background(new BackgroundFill(Color.web("#861574"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneLoginDetails.setBackground(new Background(new BackgroundFill(Color.web("#555555"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneHeader.setBackground(new Background(new BackgroundFill(Color.web("#999999"), CornerRadii.EMPTY, Insets.EMPTY)));

        root.setTop(paneHeader);
    }

    public void SubListLabel() {
        Label lblSubs = new Label("Your Channels");
        lblSubs.setFont(Font.font(24));
        lblSubs.setAlignment(Pos.CENTER);
        paneLeft.getChildren().add(lblSubs);
    }

    public void ConstructMainScreen() {
//        ClearCanvas();

//        LoginTab();

        paneLeft = new VBox();
        paneLeft.setPrefWidth(300);
        paneLeft.setAlignment(Pos.TOP_CENTER);
        paneLeft.setPadding(new Insets(20));
        SubListLabel();
        //Test Colors
        paneLeft.setBackground(new Background(new BackgroundFill(Color.web("#289548"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setLeft(paneLeft);

        paneRight = new VBox();
        paneRight.setPrefWidth(300);
        paneRight.setAlignment(Pos.TOP_CENTER);
        paneRight.setPadding(new Insets(20));
        paneRight.setSpacing(20);

        //Test Colors
        paneRight.setBackground(new Background(new BackgroundFill(Color.web("#684598"), CornerRadii.EMPTY, Insets.EMPTY)));

        txtSub = new TextField();
        btnSub = new Button("Subscribe!");

        txtUnsub = new TextField();
        btnUnsub = new Button("Unsubscribe!");

        paneRight.getChildren().addAll(txtSub, btnSub, txtUnsub, btnUnsub);

        root.setRight(paneRight);

        paneCenter = new VBox();
        paneCenter.setAlignment(Pos.TOP_CENTER);
        paneCenter.setSpacing(25);
        paneCenter.setPadding(new Insets(25, 200, 25, 200));
        //Test Colors
        paneCenter.setBackground(new Background(new BackgroundFill(Color.web("#765484"), CornerRadii.EMPTY, Insets.EMPTY)));

        txtMsg = new TextArea();
        btnSend = new Button("Send Message");

        btnGet = new Button("Get All New Messages!");

        paneCenter.getChildren().addAll(txtMsg, btnSend, btnGet);

        root.setCenter(paneCenter);

        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "You are now logged in!");
        a.show();
    }

    private void ClearCanvas() {
        root.getChildren().clear();
    }
}