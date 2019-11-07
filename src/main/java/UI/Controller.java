package UI;

import Connection.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Controller {

    private Client client = new Client();

    public void initialize() {

        mainAnchorPane.setFocusTraversable(true);

        authPage();

    }

    @FXML
    private AnchorPane mainAnchorPane;

    //auth page *start
    @FXML
    private AnchorPane authPage;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private RadioButton userRadioButton;
    @FXML
    private RadioButton adminRadioButton;
    @FXML
    private Button enterAuthButton;
    @FXML
    private Button goBackAuthButton;

    String authJSON = "{ " +
            "\"login\": \"%s\", " +
            "\"password\": \"%s\", " +
            "\"userRole\": \"%s\" " +
            "}";

    private void authPage() {
        System.out.println("UI Created\n");
        authPage.setFocusTraversable(true);
        authPage.setStyle("-fx-background-color : #202324;");

        loginField.setFocusTraversable(true);
        passwordField.setFocusTraversable(true);

        ToggleGroup authChoice = new ToggleGroup();
        userRadioButton.setToggleGroup(authChoice);
        adminRadioButton.setToggleGroup(authChoice);
        userRadioButton.setSelected(true);

        enterAuthButton.setFocusTraversable(true);
        goBackAuthButton.setFocusTraversable(true);

        enterAuthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                String login = loginField.getText();
                String password = passwordField.getText();
                String passwordHash = "error hash";

                RadioButton button = (RadioButton) authChoice.getSelectedToggle();
                String userRole = button.getText();

                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                    passwordHash = Base64.getEncoder().encodeToString(hash);
                } catch (NoSuchAlgorithmException e) {
                    System.err.println(e);
                }


                String json = String.format(authJSON, login, passwordHash, userRole);
                System.out.println(json);

            }

        });

        goBackAuthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                client.closeConnection();
                System.exit(0);
            }

        });
    }

    //auth page *end


}
