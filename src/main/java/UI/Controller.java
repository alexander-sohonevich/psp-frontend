package UI;

import Model.AuthPage;
import Model.Car;
import Server.ServerConnection;
import Server.TCPConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Controller {

    TCPConnection connection = ServerConnection.takeConnection().getTcpConnection();

    private static Boolean checkEdition = false;

    public void initialize() {
        mainAnchorPane.setVisible(true);
        //sellingCarPage();
        authPage();
        //adminPage();
        //userPage();

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

                    System.out.println(json);

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

    @FXML
    private TableView adminListTable;
    @FXML
    private TableView userListTable;

    @FXML
    private TableColumn adminListColumn;
    @FXML
    private TableColumn userListColumn;

    @FXML
    private RadioButton addAdminRadioButton;
    @FXML
    private RadioButton addUserRadioButton;

    @FXML
    private TextField addUserLogField;
    @FXML
    private PasswordField addUserPasswordField;

    @FXML
    private Button addUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button deleteAdminButton;

    @FXML
    private Label addUserLabel;
    @FXML
    private Label addPasswordLabel;
    @FXML
    private Label addLoginLabel;


    private void adminPage() {
        userAnchorPane.setVisible(false);

        adminAnchorPane.setVisible(true);

        adminListTable.setVisible(true);

        userListTable.setVisible(true);
        adminListColumn.setVisible(true);
        userListColumn.setVisible(true);
        addAdminRadioButton.setVisible(true);
        addUserRadioButton.setVisible(true);
        addUserLogField.setVisible(true);
        addUserPasswordField.setVisible(true);
        addUserButton.setVisible(true);
        deleteUserButton.setVisible(true);
        deleteAdminButton.setVisible(true);
    }

    //admin page *end

    //user page *start

    private void userPage() {
        adminAnchorPane.setVisible(false);
        addCarPage();
        tableViewPage();
    }

    //add car *start
    @FXML
    private AnchorPane userAnchorPane;

    @FXML
    private Button addCarButton;

    @FXML
    private TextField brandField;

    @FXML
    private TextField modelField;

    @FXML
    private ChoiceBox<String> yearOfIssueField;

    @FXML
    private TextField costField;

    @FXML
    private TextField vinCodeField;

    @FXML
    private ChoiceBox bodyTypeField;

    @FXML
    private TextField bodyColorField;

    @FXML
    private ChoiceBox numberOfDoorsField;

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
    private ChoiceBox carStatusField;

    @FXML
    private Label addCarWarning;


    private void addCarPage() {
        userAnchorPane.setVisible(true);

        addCarWarning.setVisible(false);

        ObservableList<String> years = FXCollections.observableArrayList();
        for (int i = 2020; i > 1949; i--) {
            years.add(String.valueOf(i));
        }
        yearOfIssueField.setItems(years);
        yearOfIssueField.show();
        yearOfIssueField.setValue("2020");
        yearOfIssueField.setVisible(true);

        ObservableList<String> bodyTypesArray = FXCollections.observableArrayList(
                "Седан",
                "Хэтчбек",
                "Универсал",
                "Лифтбэк",
                "Купе",
                "Кабриолет",
                "Родстер",
                "Тарга",
                "Лимузин",
                "Стретч",
                "Внедорожник",
                "Кроссовер",
                "Пикап",
                "Фургон",
                "Минивэн",
                "Микроавтобус",
                "Автобус",
                "Грузовой автомобиль");

        bodyTypeField.setItems(bodyTypesArray);
        bodyTypeField.show();
        bodyTypeField.setValue("Седан");
        bodyTypeField.setVisible(true);

        ObservableList<String> numberOfDoors = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        numberOfDoorsField.setItems(numberOfDoors);
        numberOfDoorsField.show();
        numberOfDoorsField.setValue("5");
        numberOfDoorsField.setVisible(true);

        ObservableList<String> multimedia = FXCollections.observableArrayList(
                "Отсутствует",
                "Обычное радио",
                "Обычная мультимедийная система",
                "Мультимедийная система класса люкс"
        );

        multimediaSystemField.setItems(multimedia);
        multimediaSystemField.show();
        multimediaSystemField.setValue("Отсутствует");
        multimediaSystemField.setVisible(true);

        ObservableList<String> wheelDiameter = FXCollections.observableArrayList(
                "12", "13", "14", "15", "16", "16.5",
                "17", "18", "19", "19.5", "20", "21", "22",
                "22.5", "23", "24", "32", "36");

        wheelDiameterField.setItems(wheelDiameter);
        wheelDiameterField.show();
        wheelDiameterField.setValue("12");
        wheelDiameterField.setVisible(true);

        ObservableList<String> transmission = FXCollections.observableArrayList(
                "RWD",
                "FWD",
                "4WD",
                "AWD"
        );

        transmissionField.setItems(transmission);
        transmissionField.show();
        transmissionField.setValue("RWD");
        transmissionField.setVisible(true);

        ObservableList<String> сarStatus = FXCollections.observableArrayList(
                "В наличии",
                "Под заказ", "Нет в наличии");

        carStatusField.setItems(сarStatus);
        carStatusField.show();
        carStatusField.setValue("В наличии");
        carStatusField.setVisible(true);

        addCarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    if (validatingInsertData()) {
                        String CarJSON = Car.getAddCarJSON(
                                brandField.getText(),
                                modelField.getText(),
                                yearOfIssueField.getValue(),
                                costField.getText(),
                                vinCodeField.getText(),
                                bodyTypeField.getValue().toString(),
                                bodyColorField.getText(),
                                numberOfDoorsField.getValue().toString(),
                                salonField.getText(),
                                salonColorField.getText(),
                                multimediaSystemField.getValue().toString(),
                                transmissionField.getValue().toString(),
                                wheelDiameterField.getValue().toString());

                        connection.sendString("ADD CAR");
                        connection.sendString(CarJSON);
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
            }
        });

    }

    public Boolean validatingInsertData() {
        if (brandField.getText().equals("") ||
                modelField.getText().equals("") ||
                costField.getText().equals("") ||
                vinCodeField.getText().equals("") ||
                bodyColorField.getText().equals("") ||
                salonField.getText().equals("") ||
                salonColorField.getText().equals("")) {
            System.out.println("Data validating failed");
            return false;
        }
        System.out.println("Data validating success");

        return false;
    }
    //add car *end

    //car table *start

    @FXML
    private TableView mainTableCars;

    @FXML
    private TableColumn<Car, String> brandColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Integer> yearOfIssueColumn;
    @FXML
    private TableColumn<Car, Double> costColumn;
    @FXML
    private TableColumn<Car, String> vinCodeColumn;
    @FXML
    private TableColumn<Car, String> bodyTypeColumn;
    @FXML
    private TableColumn<Car, String> bodyColorColumn;
    @FXML
    private TableColumn<Car, Integer> numberOfDoorsColumn;
    @FXML
    private TableColumn<Car, String> salonColumn;
    @FXML
    private TableColumn<Car, String> salonColorColumn;
    @FXML
    private TableColumn<Car, String> multimediaSystemColumn;
    @FXML
    private TableColumn<Car, Double> wheelDiameterColumn;
    @FXML
    private TableColumn<Car, String> transmissionColumn;

    @FXML
    private Label editLabel;

    @FXML
    private Button editSaveButton;
    @FXML
    private Button orderFilterButton;
    @FXML
    private Button inStockFilterButton;
    @FXML
    private Button soldFilterButton;
    @FXML
    private Button sellCarButton;


    private void tableViewPage() {

        new Thread() {
            @Override
            public void run() {
                while (true) {

                    if (checkEdition) {
                        editLabel.setVisible(true);
                        editSaveButton.setVisible(true);
                    } else {
                        editLabel.setVisible(false);
                        editSaveButton.setVisible(false);
                    }

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        mainTableCars.setVisible(true);
        mainTableCars.setEditable(true);
        mainTableCars.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearOfIssueColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfIssue"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        vinCodeColumn.setCellValueFactory(new PropertyValueFactory<>("vinCode"));
        bodyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bodyType"));
        bodyColorColumn.setCellValueFactory(new PropertyValueFactory<>("bodyColor"));
        numberOfDoorsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfDoors"));
        salonColumn.setCellValueFactory(new PropertyValueFactory<>("salon"));
        salonColorColumn.setCellValueFactory(new PropertyValueFactory<>("salonColor"));
        multimediaSystemColumn.setCellValueFactory(new PropertyValueFactory<>("multimediaSystem"));
        wheelDiameterColumn.setCellValueFactory(new PropertyValueFactory<>("wheelDiameter"));
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        Car.setCarToList("Toyota", "Rav4", 2016, 459.33, "12A44GDEGV323GYUUI0",
                "Седан", "Черный", 2, "Кожзам",
                "Красный", "Радио", 19.5, "LWD");

        Car.setCarToList("Toyota", "Prius", 2012, 2421.90, "12A44GDEGV323GYUUI0",
                "Седан", "Черный", 1, "Кожзам",
                "Красный", "Радио", 19.5, "RWD");

        Car.setCarToList("Ford", "Mustang", 2011, 32134.88, "12A44GDEGV323GYUUI0",
                "Седан", "Черный", 3, "Кожзам",
                "Красный", "Радио", 19.5, "4WD");

        Car.setCarToList("Mitsubishi", "Colt", 2017, 292.1, "12A44GDEGV323GYUUI0",
                "Седан", "Черный", 5, "Кожзам",
                "Красный", "Радио", 19.5, "AWD");


        mainTableCars.setItems(Car.getCarsList());
        mainTableCars.refresh();

        orderFilterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    connection.sendString("FILTER CARS");
                    connection.sendString("Под заказ");

                    Car.clearCarsList();
                    Car.parseJSONArrayFromServer(connection.receiveString());

                    mainTableCars.setItems(Car.getCarsList());
                    mainTableCars.refresh();
                }
            }
        });

        inStockFilterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    connection.sendString("FILTER CARS");
                    connection.sendString("В наличии");

                    Car.clearCarsList();
                    Car.parseJSONArrayFromServer(connection.receiveString());

                    mainTableCars.setItems(Car.getCarsList());
                    mainTableCars.refresh();
                }
            }
        });

        soldFilterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    connection.sendString("FILTER CARS");
                    connection.sendString("Продано");

                    Car.clearCarsList();
                    Car.parseJSONArrayFromServer(connection.receiveString());

                    mainTableCars.setItems(Car.getCarsList());
                    mainTableCars.refresh();
                }
            }
        });

        sellCarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    connection.sendString("SELLING CAR");

                    Car car = (Car) mainTableCars.getSelectionModel().getSelectedItem();

                    sellingCarPage(car);


                }
            }
        });

        editSaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    ObservableList<Car> editedInfo = mainTableCars.getItems();
                    connection.sendString("UPDATE ALL CARS");
                    connection.sendString(String.valueOf(editedInfo.size()));
                    for (Car car : editedInfo) {
                        connection.sendString(car.toString());
                    }

                }
            }
        });

        brandColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        brandColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getBrand().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setBrand(newValue);
        });

        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getModel().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setModel(newValue);
        });

        yearOfIssueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yearOfIssueColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, Integer > t) -> {
            TablePosition<Car, Integer> pos = t.getTablePosition();
            Integer newValue = t.getNewValue();
            if (newValue > 1950 && newValue < 2020) {
                int row = pos.getRow();
                Car carObj = t.getTableView().getItems().get(row);
                if (!carObj.getBrand().equals(newValue)) {
                    checkEdition = true;
                }
                carObj.setYearOfIssue(newValue);
            }
        });

        costColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        costColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, Double> t) -> {
            TablePosition<Car, Double> pos = t.getTablePosition();
            Double newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getBrand().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setCost(newValue);
        });

        vinCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        vinCodeColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getVinCode().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setVinCode(newValue);

        });

        bodyTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bodyTypeColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getBodyType().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setBodyType(newValue);

        });

        bodyColorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bodyColorColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getBodyColor().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setBodyColor(newValue);

        });

        numberOfDoorsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberOfDoorsColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, Integer> t) -> {
            TablePosition<Car, Integer> pos = t.getTablePosition();
            Integer newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getBrand().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setNumberOfDoors(newValue);
        });

        salonColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        salonColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getSalon().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setSalon(newValue);

        });

        salonColorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        salonColorColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getSalonColor().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setSalonColor(newValue);

        });

        multimediaSystemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        multimediaSystemColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getMultimediaSystem().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setMultimediaSystem(newValue);
        });

        wheelDiameterColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        wheelDiameterColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, Double> t) -> {
            TablePosition<Car, Double> pos = t.getTablePosition();
            Double newValue = t.getNewValue();
            if (newValue > 12 && newValue < 36) {
                int row = pos.getRow();
                Car carObj = t.getTableView().getItems().get(row);
                if (!carObj.getBrand().equals(newValue)) {
                    checkEdition = true;
                }
                carObj.setWheelDiameter(newValue);
            }
        });

        transmissionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        transmissionColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> t) -> {
            TablePosition<Car, String> pos = t.getTablePosition();
            String newValue = t.getNewValue();
            int row = pos.getRow();
            Car carObj = t.getTableView().getItems().get(row);
            if (!carObj.getTransmission().equals(newValue)) {
                checkEdition = true;
            }
            carObj.setTransmission(newValue);
        });
    }

//car table *end

    //selling car *start

    @FXML
    private AnchorPane sellingCarAnchorPane;

    @FXML
    private TextField sellerSurnameInfo;
    @FXML
    private TextField sellerNameInfo;
    @FXML
    private TextField sellerIDInfo;
    @FXML
    private Button sellingBackButton;
    @FXML
    private Button sellingOkayButton;

    @FXML
    private Label brandSellInfo;
    @FXML
    private Label modelSellInfo;
    @FXML
    private Label bodyTypeSellInfo;
    @FXML
    private Label bodyColorSellInfo;
    @FXML
    private Label vinCodeSellInfo;
    @FXML
    private Label transmissionSellInfo;

    public void sellingCarPage(Car car) {

        brandSellInfo.setText(car.getBrand());
        modelSellInfo.setText(car.getModel());
        bodyTypeSellInfo.setText(car.getBodyType());
        bodyColorSellInfo.setText(car.getBodyColor());
        vinCodeSellInfo.setText(car.getVinCode());
        transmissionSellInfo.setText(car.getTransmission());

        userAnchorPane.setVisible(false);
        sellingCarAnchorPane.setVisible(true);

        sellingBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                userAnchorPane.setVisible(true);
                sellingCarAnchorPane.setVisible(false);
            }
        });

        sellingOkayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (connection.ping()) {
                    String jsonSellingCar = "{" +
                            "buyerSurname: \"%s\" " +
                            "buyerName: \"%s\" " +
                            "buyerID: \"%s\" " +
                            "vinCode: \"%s\" " +
                            "}";
                    jsonSellingCar = String.format(jsonSellingCar, sellerSurnameInfo.getText(), sellerNameInfo.getText(), sellerIDInfo.getText(), vinCodeSellInfo.getText());

                    connection.sendString(jsonSellingCar);
                }
            }
        });

    }


    //selling car *end

//user page *end
}
