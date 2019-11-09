package UI;

import Model.AuthPage;
import Server.ServerConnection;
import Server.TCPConnection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller {

    TCPConnection connection = ServerConnection.takeConnection().getTcpConnection();


    public void initialize() {
        mainAnchorPane.setVisible(true);
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
    @FXML
    private Label authTitle;

    String authJSON = "{ " +
            "\"login\": \"%s\", " +
            "\"password\": \"%s\", " +
            "\"userRole\": \"%s\" " +
            "}";

    private void authPage() {
        System.out.println("UI Created\n");

        authPage.setVisible(true);
        adminAnchorPane.setVisible(false);
        userAnchorPane.setVisible(false);

        authTitle.setVisible(false);

        loginField.setVisible(true);
        passwordField.setVisible(true);

        ToggleGroup authChoice = new ToggleGroup();
        userRadioButton.setToggleGroup(authChoice);
        adminRadioButton.setToggleGroup(authChoice);
        userRadioButton.setSelected(true);

        enterAuthButton.setVisible(true);
        goBackAuthButton.setVisible(true);

        enterAuthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                authTitle.setVisible(true);
                authTitle.setText("Соединение отсутствует");
                authTitle.setTextFill(Color.web("red"));
                if (connection.ping()) {

                    authTitle.setVisible(false);
                    String login = loginField.getText();
                    String password = passwordField.getText();

                    if (login.length() < 3) {
                        authTitle.setVisible(true);
                        authTitle.setText("Логин короче 3-х символов");
                        authTitle.setTextFill(Color.web("black"));
                        return;
                    }

                    if (password.length() < 8) {
                        authTitle.setVisible(true);
                        authTitle.setText("Пароль короче 8 символов");
                        authTitle.setTextFill(Color.web("black"));
                        return;
                    }

                    authTitle.setVisible(false);

                    String passwordHash;
                    RadioButton button = (RadioButton) authChoice.getSelectedToggle();
                    String userRole = button.getText();

                    passwordHash = AuthPage.encryptPassword(password);

                    String json = String.format(authJSON, login, passwordHash, userRole);

                    connection.sendString(json);

                    if (connection.receiveString().equals("1")) {
                        authPage.setVisible(false);
                        if (userRole.equals("Пользователь")) {
                            userPage();
                        } else {
                            adminPage();
                        }

                    } else {
                        authTitle.setVisible(true);
                        authTitle.setText("Неверный логин или пароль");
                        authTitle.setTextFill(Color.web("red"));
                    }
                }
            }

        });

        goBackAuthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.exit(0);
            }

        });
    }
    //auth page *end


    //admin page *start

    @FXML
    private AnchorPane adminAnchorPane;

    private void adminPage() {
        adminAnchorPane.setVisible(true);
    }

    //admin page *end

    //user page *start

    @FXML
    private AnchorPane userAnchorPane;

    @FXML
    private Button addCarButton;

    @FXML
    private TextField brandField;

    @FXML
    private TextField modelField;

    @FXML
    private TextField yearOfIssueField;

    @FXML
    private TextField costField;

    @FXML
    private TextField vinCodeField;

    @FXML
    private TextField bodyTypeField;

    @FXML
    private TextField bodyColorField;

    @FXML
    private TextField numberOfDoorsField;

    @FXML
    private TextField salonField;

    @FXML
    private TextField salonColorField;

    @FXML
    private ChoiceBox multimediaSystemField;

    @FXML
    private ChoiceBox transmissionField;

    @FXML
    private ChoiceBox wheelDiameterField;

    @FXML
    private Label addCarWarning;

    String addCarJSON = "{ " +
            "\"brand\": \"%s\", " +
            "\"model\": \"%s\", " +
            "\"yearOfIssue\": \"%s\", " +
            "\"cost\": \"%s\", " +
            "\"vinCode\": \"%s\", " +
            "\"bodyType\": \"%s\", " +
            "\"bodyColor\": \"%s\", " +
            "\"numberOfDoors\": \"%s\", " +
            "\"salon\": \"%s\", " +
            "\"salonColor\": \"%s\", " +
            "\"multimediaSystem\": \"%s\", " +
            "\"transmission\": \"%s\", " +
            "\"wheelDiameter\": \"%s\" " +
            "}";

    private void userPage() {
        userAnchorPane.setVisible(true);

        addCarWarning.setVisible(false);
        brandField.setVisible(true);
        modelField.setVisible(true);
        yearOfIssueField.setVisible(true);
        costField.setVisible(true);
        vinCodeField.setVisible(true);
        bodyTypeField.setVisible(true);
        bodyColorField.setVisible(true);
        numberOfDoorsField.setVisible(true);
        salonField.setVisible(true);
        salonColorField.setVisible(true);
        multimediaSystemField.setVisible(true);
        transmissionField.setVisible(true);
        wheelDiameterField.setVisible(true);

        addCarButton.setVisible(true);

        addCarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (validatingInsertData()) {
                    String json = String.format(addCarJSON,
                            brandField.getText(),
                            modelField.getText(),
                            yearOfIssueField.getText(),
                            costField.getText(),
                            vinCodeField.getText(),
                            bodyTypeField.getText(),
                            bodyColorField.getText(),
                            numberOfDoorsField.getText(),
                            salonField.getText(),
                            salonColorField.getText(),
                            multimediaSystemField.getValue(),
                            transmissionField.getValue(),
                            wheelDiameterField.getValue());

                    connection.sendString(json);
                    String result = connection.receiveString();

                    addCarWarning.setVisible(true);
                    addCarWarning.setText(result);
                    if (result.equals("Успешно")) {
                        addCarWarning.setTextFill(Color.web("Green"));
                    }
                } else {
                    addCarWarning.setVisible(true);
                    addCarWarning.setText("Заполните ВСЕ поля");
                }
            }
        });

    }

    public Boolean validatingInsertData() {
        if (brandField.getText().equals("") ||
                modelField.getText().equals("") ||
                yearOfIssueField.getText().equals("") ||
                costField.getText().equals("") ||
                vinCodeField.getText().equals("") ||
                bodyTypeField.getText().equals("") ||
                bodyColorField.getText().equals("") ||
                numberOfDoorsField.getText().equals("") ||
                salonField.getText().equals("") ||
                salonColorField.getText().equals("")) {
            System.out.println("Data validating success");
            return true;
        }
        System.out.println("Data validating failed");
        return false;
    }

    //user page *end
}
