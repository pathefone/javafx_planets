package controller;

import javafx.stage.Stage;

public class Switch {
    public static void switchRegister(Stage window, RegisterStage app) {
        try {
            app.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchLogin(Stage window, LoginStage app) {
        try {
            app.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchDashboard(Stage window, dashboardStage app) {
        try {
            app.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
