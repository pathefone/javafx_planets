package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Constant;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PlanetDAO implements Initializable {

    @FXML
    private TableColumn<Planet, String> planetName;
    @FXML
    private TableColumn<Planet, String> satellitesCount;
    @FXML
    private TableColumn<Planet, Integer> surfaceTemp;
    @FXML
    private TableColumn<Planet, Integer> density;
    @FXML
    private TableColumn<Planet, String> composition;
    @FXML
    private TableColumn<Planet, String> planetType;
    @FXML
    private TableView<Planet> tableView;

    public String addPlanet(Planet planet) {
        String query = "INSERT INTO " + Constant.PLANET_TABLE + "(name, satellitesCount, surfaceTemperature," +
                " density, composition, type)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        String msg = "";

        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, planet.getPlanetName());
            preparedStatement.setString(2, planet.getSatellitesCount());
            preparedStatement.setInt(3, planet.getSurfaceTemperature());
            preparedStatement.setInt(4, planet.getDensity());
            preparedStatement.setString(5, planet.getComposition());
            preparedStatement.setString(6, planet.getPlanetType());
            preparedStatement.executeUpdate();
            msg = "Planet added!";
        }
        catch (SQLException e) {
            e.printStackTrace();
            msg = "Failed creating new planet!";
        }
        return msg;
    }

    public void deletePlanet(String planetName) {
        String query = "DELETE FROM " + Constant.PLANET_TABLE + " WHERE (name = ?)";
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, planetName);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
            public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    ObservableList<Planet> data;
    @FXML
    public void loadDataToTable() {
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");

             data = FXCollections.observableArrayList();

            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM planetlist");
            while (rs.next()) {
                data.add(new Planet(rs.getString(2), rs.getString(6), rs.getString(7), rs.getString(3), rs.getInt(4), rs.getInt(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        planetName.setCellValueFactory(new PropertyValueFactory<>("planetName"));
        composition.setCellValueFactory(new PropertyValueFactory<>("composition"));
        planetType.setCellValueFactory(new PropertyValueFactory<>("planetType"));
        satellitesCount.setCellValueFactory(new PropertyValueFactory<>("satellitesCount"));
        surfaceTemp.setCellValueFactory(new PropertyValueFactory<>("surfaceTemperature"));
        density.setCellValueFactory(new PropertyValueFactory<>("density"));

        tableView.setItems(null);
        tableView.setItems(data);


    }
}
