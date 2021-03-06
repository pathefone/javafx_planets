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

    public void updatePlanet(String planetName, int density, int surfaceTemperature, String satelliteCount,
                                  String composition, String planetType, int id) {

        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.PLANET_DB, "root", "");
            if (planetName != "") {
                String query1 = "UPDATE " + Constant.PLANET_TABLE + " SET name = '" + planetName + "' WHERE id = " + id;
                System.out.println(query1);
                   PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.executeUpdate();
            }
            if (density != 0) {
                String query2 = "UPDATE " + Constant.PLANET_TABLE + " SET density = " + density + " WHERE id = " + id;
                System.out.println(query2);
                PreparedStatement preparedStatement = connection.prepareStatement(query2);
                preparedStatement.executeUpdate();

            }
            if (surfaceTemperature != 0) {
                String query3 = "UPDATE " + Constant.PLANET_TABLE + " SET surfaceTemperature = " + surfaceTemperature
                        + " WHERE id = " + id;
                System.out.println(query3);
                PreparedStatement preparedStatement = connection.prepareStatement(query3);
                preparedStatement.executeUpdate();
            }
            if (satelliteCount != "") {
                String query4 = "UPDATE " + Constant.PLANET_TABLE + " SET satellitesCount = " + satelliteCount
                        + " WHERE id = " + id;
                System.out.println(query4);
                PreparedStatement preparedStatement = connection.prepareStatement(query4);
                preparedStatement.executeUpdate();
            }
            if (composition != "") {
                String query5 = "UPDATE " + Constant.PLANET_TABLE + " SET composition = '" + composition
                        + "' WHERE id = " + id;
                System.out.println(query5);
               PreparedStatement preparedStatement = connection.prepareStatement(query5);
                preparedStatement.executeUpdate();
            }
            if (planetType != "") {
                String query6 = "UPDATE " + Constant.PLANET_TABLE + " SET type = '" + planetType
                        + "' WHERE id = " + id;
                System.out.println(query6);
                PreparedStatement preparedStatement = connection.prepareStatement(query6);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
