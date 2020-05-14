package controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Planet;
import model.PlanetDAO;
import model.User;
import model.UserDAO;
import utils.Validation;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;

public class Controller  {
    @FXML
    private Button backBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Label Errors;
    @FXML
    private Label registrationLabel;
    @FXML
    private Button signupButton;
    @FXML
    private ComboBox<String> densityBox;
    @FXML
    private TextField emailRegister;
    @FXML
    private TextField usernameRegister;
    @FXML
    private PasswordField passwordRegister;
    @FXML
    private Button registerBtn;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private Button logOutButton;
    @FXML
    private TableColumn planetName;
    @FXML
    private TableColumn satellitesCount;
    @FXML
    private TableColumn surfaceTemp;
    @FXML
    private TableColumn density;
    @FXML
    private TableColumn composition;
    @FXML
    private TableColumn planetType;
    @FXML
    private TextField planetNameField;
    @FXML
    private TextField densityField;
    @FXML
    private TextField surfaceTempField;
    @FXML
    private ComboBox satelliteCountBox;
    @FXML
    private CheckBox gasCheckBox;
    @FXML
    private CheckBox liquidCheckBox;
    @FXML
    private CheckBox metalCheckBox;
    @FXML
    private RadioButton classicalPlanetCheckBox;
    @FXML
    private RadioButton earthAnalogCheckBox;
    @FXML
    private RadioButton hypotheticalPlanetCheckBox;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView tableView;
    @FXML
    private Button loadDataFromDatabase;
    @FXML
    private TextField idField;
    @FXML
    private Label errorDashBox;

    ResultSet rsAllEntries;


    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        if(actionEvent.getSource() == loginBtn) {
               login(actionEvent);
        }
        if(actionEvent.getSource() == signupButton) {
            // register();
            Switch.switchRegister((Stage)signupButton.getScene().getWindow(), new RegisterStage());
        }
        if(actionEvent.getSource() == backBtn) {
            Switch.switchLogin((Stage)backBtn.getScene().getWindow(), new LoginStage());
        }
        if(actionEvent.getSource() == registerBtn) {
            String email = String.valueOf(emailRegister.getText());
            String username = String.valueOf(usernameRegister.getText());
            String password = String.valueOf(passwordRegister.getText());
            String passConfirm = String.valueOf(passwordConfirm.getText());
            register(email, username, password, passConfirm);
        }
        if(actionEvent.getSource() == logOutButton) {
            Switch.switchLogin((Stage)logOutButton.getScene().getWindow(), new LoginStage());
        }
        if(actionEvent.getSource() == createButton) {
            try {
                String planetName = String.valueOf(planetNameField.getText());
                int density = Integer.parseInt(densityField.getText());
                int surfaceTemperature = Integer.parseInt(surfaceTempField.getText());
                String satelliteCount = satelliteCountBox.getSelectionModel().getSelectedItem().toString();
                boolean gas = gasCheckBox.isSelected();
                boolean liquids = liquidCheckBox.isSelected();
                boolean metals = metalCheckBox.isSelected();
                String composition = composition(gas, liquids, metals);
                boolean classicalPlanet = classicalPlanetCheckBox.isSelected();
                boolean earthAnalog = earthAnalogCheckBox.isSelected();
                boolean hypotheticalPlanet = hypotheticalPlanetCheckBox.isSelected();
                String planetType = planetType(classicalPlanet, earthAnalog, hypotheticalPlanet);

                Planet planet = new Planet(planetName, composition, planetType, satelliteCount, surfaceTemperature, density);
                create(planet);
            }
            catch (RuntimeException e) {
                errorDashBox.setText("Insert data in all fields! \n *Planet name - String \n *Density - Integer" +
                        " \n *Surface temperature - Integer");
            }
        }
        if(actionEvent.getSource() == deleteButton) {
            String planetName = String.valueOf(planetNameField.getText());
            delete(planetName);
        }
        if(actionEvent.getSource() == loadDataFromDatabase) {
            loadDatafromDB();
        }
        if(actionEvent.getSource() == searchButton) {
            search(String.valueOf(planetNameField.getText()));
        }
        if(actionEvent.getSource() == updateButton) {
            update();

        }
    }

    public void exit(MouseEvent event) {
        System.exit(0);
    }
    public String composition(boolean gas, boolean liquids, boolean metals) {

        String composition = "";
        if (gas) {
            composition = "Gas";
        }
        else if (liquids) {
            composition = "Liquids";
        }
        else if (metals) {
            composition = "Metals";
        }
        return  composition;
    }
    public String planetType(boolean classPlnt, boolean earthAnlg, boolean hypoPlnt){
        String planetType = "";
        if (classPlnt) {
            planetType = "Classical planet";
        }
        else if (earthAnlg) {
            planetType = "Earth analog";
        }
        else if (hypoPlnt) {
            planetType = "Hypothetical planet";
        }
        return  planetType;
    }


    public void dashboard(javafx.event.ActionEvent event){
        Switch.switchDashboard((Stage)loginBtn.getScene().getWindow(), new dashboardStage());
    }

    private void login(javafx.event.ActionEvent event) {
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        int userLength = user.length();
        int passLength = pass.length();

        if(Validation.isValidUsername(user) && Validation.isValidPassword(pass)) {
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.login(user, pass);
            if(msg.contains("Successful")) {
                dashboard(event);
            }
            else {
                Errors.setText(msg);
            }
        }
        else if(userLength < 5 || userLength > 12) {
            Errors.setText("Username has to be between 5-12 characters!");
        }
        else if(passLength < 5 || passLength > 12) {
            Errors.setText("Password has to be between 5-12 characters!");
        }
        else {
            Errors.setText("Using invalid characters! Only letters and numbers allowed in username.");
        }
    }

    //register dashboard event

    public void register(String email, String username, String password, String passConfirm) {

        if(Validation.isValidEmailAddress(email) && Validation.isValidUsername(username)
                && Validation.isValidPassword(password) && passConfirm.equals(password) ){
            HashMap users = new HashMap();
            users.put(email, new User(email, username, password));
            //   User user = new User(email, username, password);

            UserDAO userDAO = new UserDAO();
            String msg = userDAO.register((User) users.get(email));
            registrationLabel.setText(msg);
        }
        else {
            registrationLabel.setText("Username and password must contain 5-12 characters!\n Or your email is inncorect.");
        }
        if (!passConfirm.equals(password)) { registrationLabel.setText("Password was not confirmed! Try again.");}
        boolean isRegistered = true;
    }

    public void create(Planet planet) {
        String msg = "";
        HashMap planets = new HashMap();
        planets.put(planet.getPlanetName(), new Planet(planet.getPlanetName(), planet.getComposition(),
                planet.getPlanetType(), planet.getSatellitesCount(),
                planet.getSurfaceTemperature(), planet.getDensity()));

        PlanetDAO planetDAO = new PlanetDAO();
        msg = planetDAO.addPlanet((Planet) planets.get(planet.getPlanetName()));

        if(msg.contains("added")) {
            errorDashBox.setText("Planet successfully added!");
        }
        else if(msg.contains("Failed")) {
            errorDashBox.setText("Planet failed to create!");
        }

        updateTableFromDB();
    }

    public void update() {
        int id = 0;
        String planetName = "";
        int density = 0;
        String satelliteCount = "";
        String composition = "";
        String planetType = "";
        int surfaceTemperature = 0;
        boolean gas = false;
        boolean liquids = false;
        boolean metals = false;
        boolean classicalPlanet = false;
        boolean earthAnalog = false;
        boolean hypotheticalPlanet = false;

        try {
             gas = gasCheckBox.isSelected();
             liquids = liquidCheckBox.isSelected();
             metals = metalCheckBox.isSelected();
             classicalPlanet = classicalPlanetCheckBox.isSelected();
             earthAnalog = earthAnalogCheckBox.isSelected();
             hypotheticalPlanet = hypotheticalPlanetCheckBox.isSelected();

            composition = composition(gas, liquids, metals);
            planetType = planetType(classicalPlanet, earthAnalog, hypotheticalPlanet);

         id = Integer.parseInt(idField.getText());
         planetName = "";
         density = Integer.parseInt(densityField.getText());
         surfaceTemperature = Integer.parseInt(surfaceTempField.getText());
         satelliteCount = "";
         
         String planetNameCheck = String.valueOf(planetNameField.getText());

            if (!planetNameCheck.equals("")) {
                planetName = String.valueOf(planetNameField.getText());
            }
            if (surfaceTemperature != 0) {
               // surfaceTemperature = Integer.parseInt(surfaceTempField.getText());
                surfaceTemperature = Integer.parseInt(surfaceTempField.getText());
                System.out.println(surfaceTemperature);
            }
            if (satelliteCountBox.getSelectionModel().getSelectedItem().toString() != null) {
                satelliteCount = satelliteCountBox.getSelectionModel().getSelectedItem().toString();
            }

        }
        catch (RuntimeException e) {

        }
        PlanetDAO planetDAO = new PlanetDAO();
        planetDAO.updatePlanet(planetName,density, surfaceTemperature, satelliteCount, composition, planetType, id);
        updateTableFromDB();
    }

    public void delete(String planetName){
        PlanetDAO planetDAO = new PlanetDAO();
        planetDAO.deletePlanet(planetName);
        updateTableFromDB();
    }

    private void loadDatafromDB() {
        updateTableFromDB();
    }

    public void search(String planetName) {
        PlanetDAO planetDAO = new PlanetDAO();

        try {
            rsAllEntries = planetDAO.searchByPlanetName(planetName);
        }
        catch (NullPointerException e) {

        }
        fetchColumnList();
        fetchRowList();
    }

    public void updateTableFromDB() {
        PlanetDAO planetDAO = new PlanetDAO();

        try {
            rsAllEntries = planetDAO.loadTableView();
        }
        catch (NullPointerException e) {
        }
        fetchColumnList();
        fetchRowList();
    }


    //only fetch columns
    private void fetchColumnList() {
        try {
            tableView.getColumns().clear();

            if (rsAllEntries != null) {
                //SQL FOR SELECTING ALL OF CUSTOMER
                for (int i = 0; i < rsAllEntries.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rsAllEntries.getMetaData().getColumnName(i + 1).toUpperCase());
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    tableView.getColumns().removeAll(col);
                    tableView.getColumns().addAll(col);
                }
            } else {

            }
        } catch (SQLException e) {

        }
    }

    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    //fetches rows and data from the list
    private void fetchRowList() {
        try {
            data.clear();
            if (rsAllEntries != null) {
                while (rsAllEntries.next()) {
                    //Iterate Row
                    ObservableList row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rsAllEntries.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rsAllEntries.getString(i));
                    }
                    data.add(row);
                }
                //Connects table with list
                tableView.setItems(data);
            } else {

            }
        } catch (SQLException ex) {
        }
    }


}

