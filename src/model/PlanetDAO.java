package model;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.Constant;
import java.sql.*;


public class PlanetDAO{
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

    public ResultSet loadTableView(){
        String query2 = "SELECT * FROM planetlist";

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");
            preparedStatement = connection.prepareStatement(query2);
            resultSet = preparedStatement.executeQuery(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet searchByPlanetName(String planetName) {
        String query = "";
        query = "SELECT * FROM " + Constant.PLANET_TABLE + " WHERE name LIKE '" + planetName + "'";
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
