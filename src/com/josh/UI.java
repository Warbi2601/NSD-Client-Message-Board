package com.josh;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UI {

    public static String lightColor = "3b3e44"; //e6e6e6
    public static String darkColor = "36393F"; //0d0d0d
    public static String textColor = "DCDADE";
    public static String usernameColor = "FFFF4D"; //DCDADE

    //Login Screen
    private Stage stage = new Stage();
    private BorderPane root;
    private BorderPane paneHeader;

    private Label lblTitle;
    private Button btnLogin;
    private Button btnLogout;
    private Button btnQuit;
    private TextField txtLogin;

    //Main Screen
    private VBox paneLeft;
    private VBox paneRight;
    private VBox paneCenter;
    private VBox paneMessages;

    private TextArea txtMsg;
    private Button btnSend;

    private TextField txtSub;
    private Button btnSub;

    private TextField txtUnsub;
    private Button btnUnsub;

    private Button btnSetPref;

    private Label lblSubs;

    private Label lblUsername;

    private FileChooser fileChooser;
    private Button btnAddPic;
    private Image imagePreview;
    private ImageView imgViewPreview;

    private String imageString;

    private Label lblMainColor;
    private Label lblSecColor;
    private Label lblTextColor;
    private Label lblUsernameColor;

    private Circle circleMain;
    private Circle circleSec;
    private Circle circleText;
    private Circle circleUsername;

    private List<Text> listOfMsgUsernames = new ArrayList<>();
    private List<Text> listOfMsgs = new ArrayList<>();

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

    public Button getBtnLogout() {
        return btnLogout;
    }

    public Button getBtnAddPic() {
        return btnAddPic;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public Image getImagePreview() {
        return imagePreview;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImagePreview(Image imagePreview) {
        this.imagePreview = imagePreview;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public VBox getPaneMessages() {
        return paneMessages;
    }

    public ImageView getImgViewPreview() {
        return imgViewPreview;
    }

    public Button getBtnSetPref() {
        return btnSetPref;
    }

    public Label getLblMainColor() {
        return lblMainColor;
    }

    public Label getLblSecColor() {
        return lblSecColor;
    }

    public Label getLblTextColor() {
        return lblTextColor;
    }

    public Label getLblUsernameColor() {
        return lblUsernameColor;
    }

    public Circle getCircleMain() {
        return circleMain;
    }

    public Circle getCircleSec() {
        return circleSec;
    }

    public Circle getCircleText() {
        return circleText;
    }

    public Circle getCircleUsername() {
        return circleUsername;
    }

    public List<Text> getListOfMsgUsernames() {
        return listOfMsgUsernames;
    }

    public Label getLblSubs() {
        return lblSubs;
    }

    public Label getLblUsername() {
        return lblUsername;
    }

    public List<Text> getListOfMsgs() {
        return listOfMsgs;
    }

    public void ConstructLogin() {
        LoginTab();

        Scene scene = new Scene(root);

        stage.setMaximized(true);
        stage.setTitle("Astro Board");
        stage.setScene(scene);
        stage.show();
    }

    private void LoginTab() {
        btnLogin = new Button("Login");
        btnQuit = new Button("Quit");
        txtLogin = new TextField();
        lblTitle = new Label("Astro Board");
        lblTitle.setFont(Font.font(50));
        lblTitle.setTextFill(Color.web(textColor));

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

//        //Test Colors
//        paneQuit.setBackground(new Background(new BackgroundFill(Color.web("#861574"), CornerRadii.EMPTY, Insets.EMPTY)));
//        paneLoginDetails.setBackground(new Background(new BackgroundFill(Color.web("#555555"), CornerRadii.EMPTY, Insets.EMPTY)));
        paneHeader.setBackground(new Background(new BackgroundFill(Color.web(lightColor), CornerRadii.EMPTY, Insets.EMPTY)));

        root.setTop(paneHeader);
    }

    public void SubListLabel() {
        lblSubs = new Label("Your Channels");
        lblSubs.setFont(Font.font(24));
        lblSubs.setTextFill(Color.web(textColor));
        lblSubs.setAlignment(Pos.CENTER);
        paneLeft.getChildren().add(lblSubs);
    }

    public void DrawNewSub(String userInput) {
        Text sub = new Text(userInput);
        sub.setFont(Font.font(24));
        sub.setFill(Color.web(textColor));
        listOfMsgs.add(sub);
        paneLeft.getChildren().add(sub);
    }

    public void AddPicture(Stage stage) {

        fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                // Reading a Image file from file system
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                imageString = Base64.getEncoder().encodeToString(imageData);
            } catch (FileNotFoundException e) {
                System.out.println("Image not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the Image " + ioe);
            }
            imagePreview = new Image(file.toURI().toString());
            imgViewPreview = new ImageView(imagePreview);
            imgViewPreview.setFitWidth(500);
            imgViewPreview.setFitHeight(500);
            imgViewPreview.setPreserveRatio(true);

            paneCenter.getChildren().add(1, imgViewPreview);
        }
    }

    public void ConstructMainScreen(String username) {
//        ClearCanvas();

//        LoginTab();

        txtLogin.setVisible(false);
        btnLogin.setVisible(false);

//        btnLogout = new Button("Logout");
//
//        paneHeader.getChildren().remove(btnLogin);
//        paneHeader.getChildren().add(btnLogout);

        paneLeft = new VBox();
        paneLeft.setPrefWidth(300);
        paneLeft.setAlignment(Pos.TOP_CENTER);
        paneLeft.setPadding(new Insets(20));
        SubListLabel();
        DrawNewSub(username);

        //Test Colors
        paneLeft.setBackground(new Background(new BackgroundFill(Color.web(darkColor), CornerRadii.EMPTY, Insets.EMPTY)));

        root.setLeft(paneLeft);

        paneRight = new VBox();
        paneRight.setPrefWidth(300);
        paneRight.setAlignment(Pos.TOP_CENTER);
        paneRight.setPadding(new Insets(20));
        paneRight.setSpacing(20);

        //Test Colors
        paneRight.setBackground(new Background(new BackgroundFill(Color.web(darkColor), CornerRadii.EMPTY, Insets.EMPTY)));

        lblUsername = new Label("Welcome " + username);
        lblUsername.setFont(Font.font(24));
        lblUsername.setTextFill(Color.web(textColor));

        txtSub = new TextField();
        btnSub = new Button("Subscribe");

        txtUnsub = new TextField();
        btnUnsub = new Button("Unsubscribe");

        btnSetPref = new Button("Set Preferences");

        paneRight.getChildren().addAll(lblUsername, txtSub, btnSub, txtUnsub, btnUnsub, btnSetPref);

        root.setRight(paneRight);

        paneCenter = new VBox();
        paneCenter.setAlignment(Pos.TOP_CENTER);
        paneCenter.setSpacing(25);
        paneCenter.setPadding(new Insets(25, 200, 25, 200));
        //Test Colors
        paneCenter.setBackground(new Background(new BackgroundFill(Color.web(lightColor), CornerRadii.EMPTY, Insets.EMPTY)));

        txtMsg = new TextArea();
        btnSend = new Button("Send Message");

        btnAddPic = new Button("Add Picture");
        paneCenter.getChildren().add(btnAddPic);

        paneMessages = new VBox();
        paneMessages.setSpacing(0);

        paneCenter.getChildren().addAll(txtMsg, btnSend, paneMessages);

        ScrollPane scrollPane = new ScrollPane(paneCenter);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "You are now logged in");
        a.show();
    }

    private void ClearCanvas() {
        root.getChildren().clear();
    }

    public void showModal() {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox paneModal = new VBox();

        FlowPane paneMainColor = new FlowPane();
        FlowPane paneSecColor = new FlowPane();
        FlowPane paneTextColor = new FlowPane();
        FlowPane paneUsernameColor = new FlowPane();

        circleMain = new Circle(20);
        circleMain.setFill(Color.web(darkColor));

        circleSec = new Circle(20);
        circleSec.setFill(Color.web(lightColor));

        circleText = new Circle(20);
        circleText.setFill(Color.web(textColor));

        circleUsername = new Circle(20);
        circleUsername.setFill(Color.web(usernameColor));

        lblMainColor = new Label("Main Color: " + darkColor);
        lblSecColor = new Label("Second Color: " + lightColor);
        lblTextColor = new Label("Text Color: " + textColor);
        lblUsernameColor = new Label("Username Color: " + usernameColor);

        Button btnMainColor = new Button("Change");
        Button btnSecColor = new Button("Change");
        Button btnTextColor = new Button("Change");
        Button btnUsernameColor = new Button("Change");

        paneMainColor.getChildren().addAll(circleMain, lblMainColor, btnMainColor);
        paneSecColor.getChildren().addAll(circleSec, lblSecColor, btnSecColor);
        paneTextColor.getChildren().addAll(circleText, lblTextColor, btnTextColor);
        paneUsernameColor.getChildren().addAll(circleUsername, lblUsernameColor, btnUsernameColor);

        btnMainColor.setOnAction(e->ChangeColor('m'));
        btnSecColor.setOnAction(e->ChangeColor('s'));
        btnTextColor.setOnAction(e->ChangeColor('t'));
        btnUsernameColor.setOnAction(e->ChangeColor('u'));

        paneModal.getChildren().addAll(paneMainColor, paneSecColor, paneTextColor, paneUsernameColor);

        Scene scene = new Scene(paneModal);

        dialog.setScene(scene);
        dialog.setTitle("Set Preferences");
        dialog.showAndWait();
    }

    public void SetColor(ColorPicker colorPicker, Circle circle, char element) {
        circle.setFill(colorPicker.getValue());

        String colorHex = toRGBCode(colorPicker.getValue());
        switch (element) {
            case 'm':
                darkColor = colorHex;
                lblMainColor.setText("Main Color: " + colorHex);
                paneLeft.setBackground(new Background(new BackgroundFill(Color.web(darkColor), CornerRadii.EMPTY, Insets.EMPTY)));
                paneRight.setBackground(new Background(new BackgroundFill(Color.web(darkColor), CornerRadii.EMPTY, Insets.EMPTY)));
                circleMain.setFill(Color.web(darkColor));
                break;
            case 's':
                lightColor = colorHex;
                lblSecColor.setText("Second Color: " + colorHex);
                paneCenter.setBackground(new Background(new BackgroundFill(Color.web(lightColor), CornerRadii.EMPTY, Insets.EMPTY)));
                paneHeader.setBackground(new Background(new BackgroundFill(Color.web(lightColor), CornerRadii.EMPTY, Insets.EMPTY)));
                circleSec.setFill(Color.web(lightColor));
                break;
            case 't':
                textColor = colorHex;
                lblTextColor.setText("Text Color: " + colorHex);
                circleText.setFill(Color.web(textColor));
                lblTitle.setTextFill(Color.web(textColor));
                lblSubs.setTextFill(Color.web(textColor));
                lblUsername.setTextFill(Color.web(textColor));

                for (Text msg : listOfMsgs) {
                    msg.setFill(Color.web(textColor));
                }

                break;
            case 'u':
                usernameColor = colorHex;
                lblUsernameColor.setText("Username Color: " + colorHex);
                circleUsername.setFill(Color.web(usernameColor));

                for (Text from : listOfMsgUsernames) {
                    from.setFill(Color.web(usernameColor));
                }

                break;
        }
    }

    public void ChangeColor(char element) {
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.RED);

        final Circle circle = new Circle(50);
        circle.setFill(colorPicker.getValue());

        colorPicker.setOnAction(e->SetColor(colorPicker, circle, element));

        Button btnSelectColor = new Button("Choose New Color");


        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.getChildren().addAll(circle, colorPicker, btnSelectColor);

        Scene scene = new Scene(root, 400, 300);

        Stage s = new Stage();
        s.setScene(scene);
        s.show();

        btnSelectColor.setOnAction(e->CloseModal(s));

    }

    public void CloseModal(Stage s) {
        s.close();
    }

    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
}