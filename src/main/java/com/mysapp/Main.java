package com.mysapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(String.valueOf(getClass().getResource("./StyleFile/Font/Lobster-Regular.ttf")),10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        loader.setController(new LoginController());
        Parent root = loader.load();
        primaryStage.setTitle("Login User");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(E->{
            System.exit(0);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
