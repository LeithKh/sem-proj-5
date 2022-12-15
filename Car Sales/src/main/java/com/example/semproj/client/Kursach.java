package com.example.semproj.client;

import com.example.semproj.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Kursach extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Kursach.class.getResource("/com/example/project/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 420);
        stage.setTitle("LKS Autosalon");
        stage.setScene(scene);
        stage.show();
    }

    public static User ThisUser = new User();
    public static String id;
    public static boolean IsRussianLanguage = true;
    public static boolean IsLightTheme = true;


    public static void main(String[] args) {
        try {
            ClientConnection.ConnectToServer();
        } catch (IOException e) {throw  new RuntimeException(e);}
        launch();
    }
}