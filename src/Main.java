/*
 * Copyright (c) 2020. This project is created by Antonio Torres using MIT Open Source licence. You can use it just for educational purposes.
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Creamos objetos
        FXMLLoader fxmlLoader = new FXMLLoader();

        //Cragamos el archivo .fxml
        fxmlLoader.setLocation(getClass().getResource("/FXMLViews/MainWindow.fxml"));

        Parent root = fxmlLoader.load();

        primaryStage.setTitle("Particle Swarm Optimization - Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
