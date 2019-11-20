package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public class Car {
    private static ObservableList<Car> carsList = FXCollections.observableArrayList();

    private String brand;
    private String model;
    private int yearOfIssue;
    private double cost;
    private String vinCode;
    private String bodyType;
    private String bodyColor;
    private int numberOfDoors;
    private String salon;
    private String salonColor;
    private String multimediaSystem;
    private double wheelDiameter;
    private String transmission;


    private static final String addCarJSON = "{ " +
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

    public static String getAddCarJSON(String brand, String model, String yearOfIssue, String cost, String vinCode,
                                       String bodyType, String bodyColor, String numberOfDoors, String salon,
                                       String salonColor, String multimediaSystem, String wheelDiameter, String transmission) {

        String addCarJSONInstance = String.format(addCarJSON, brand, model, yearOfIssue, cost,
                vinCode, bodyType, bodyColor, numberOfDoors, salon, salonColor, multimediaSystem, wheelDiameter, transmission);

        return addCarJSONInstance;
    }


    private Car(String brand, String model, int yearOfIssue, double cost, String vinCode,
                String bodyType, String bodyColor, int numberOfDoors, String salon,
                String salonColor, String multimediaSystem, double wheelDiameter, String transmission) {
        this.brand = brand;
        this.model = model;
        this.yearOfIssue = yearOfIssue;
        this.cost = cost;
        this.vinCode = vinCode;
        this.bodyType = bodyType;
        this.bodyColor = bodyColor;
        this.numberOfDoors = numberOfDoors;
        this.salon = salon;
        this.salonColor = salonColor;
        this.multimediaSystem = multimediaSystem;
        this.wheelDiameter = wheelDiameter;
        this.transmission = transmission;
    }

    public static ObservableList<Car> getCarsList() {
        return carsList;
    }

    public static void setCarToList(
            String brand, String model, int yearOfIssue, Double cost, String vinCode,
            String bodyType, String bodyColor, int numberOfDoors, String salon,
            String salonColor, String multimediaSystem, double wheelDiameter, String transmission) {

        Car newCar = new Car(brand, model, yearOfIssue, cost, vinCode, bodyType, bodyColor, numberOfDoors, salon, salonColor, multimediaSystem, wheelDiameter, transmission);

        carsList.add(newCar);
    }

    public static void clearCarsList() {
        carsList.clear();
    }

    public static void parseJSONArrayFromServer(String json) {
        StringBuilder buffer = new StringBuilder(json);

        buffer.deleteCharAt(0);
        buffer.deleteCharAt(buffer.length() - 1);

        String tmpJSON = new String(buffer);
        String[] jsonArray = tmpJSON.split(",");

        for (String obj : jsonArray) {
            JSONObject object = new JSONObject(obj);

            setCarToList(object.getString("brand"),
                    object.getString("model"),
                    object.getInt("yearOfIssue"),
                    object.getDouble("cost"),
                    object.getString("vinCode"),
                    object.getString("bodyType"),
                    object.getString("bodyColor"),
                    object.getInt("numberOfDoors"),
                    object.getString("salon"),
                    object.getString("salonColor"),
                    object.getString("multimediaSystem"),
                    object.getDouble("wheelDiameter"),
                    object.getString("transmission"));
        }

    }

    @Override
    public String toString() {
        return "{ " +
                "\"brand\": \" " + brand + "\", " +
                "\"model\": \" " + model + " \", " +
                "\"yearOfIssue\": \" " + yearOfIssue + "  \", " +
                "\"cost\": \" " + cost + "\", " +
                "\"vinCode\": \"" + vinCode + "\", " +
                "\"bodyType\": \"" + bodyType + "\", " +
                "\"bodyColor\": \"" + bodyColor + "\", " +
                "\"numberOfDoors\": \" " + numberOfDoors + " \", " +
                "\"salon\": \" " + salon + "\", " +
                "\"salonColor\": \"" + salonColor + "\", " +
                "\"multimediaSystem\": \"" + multimediaSystem + "\", " +
                "\"transmission\": \"" + transmission + "\", " +
                "\"wheelDiameter\": \"" + wheelDiameter + "\" " +
                "}";
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public double getCost() {
        return cost;
    }

    public String getVinCode() {
        return vinCode;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public String getSalon() {
        return salon;
    }

    public String getSalonColor() {
        return salonColor;
    }

    public String getMultimediaSystem() {
        return multimediaSystem;
    }

    public double getWheelDiameter() {
        return wheelDiameter;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public void setSalonColor(String salonColor) {
        this.salonColor = salonColor;
    }

    public void setMultimediaSystem(String multimediaSystem) {
        this.multimediaSystem = multimediaSystem;
    }

    public void setWheelDiameter(double wheelDiameter) {
        this.wheelDiameter = wheelDiameter;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

}

