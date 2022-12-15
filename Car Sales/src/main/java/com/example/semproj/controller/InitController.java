package com.example.semproj.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.semproj.client.ClientConnection;
import com.example.semproj.entity.User;
import com.example.semproj.client.Kursach;
import com.example.semproj.other.SHA_256;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Autorize;

    @FXML
    private TextField Login_Field;

    @FXML
    private PasswordField Password_Field;

    @FXML
    private Button RegistationBtn;

    @FXML
    private Button SignUpBtn;

    @FXML
    void initialize() {

        SignUpBtn.setOnAction(actionEvent -> {
            String Login = Login_Field.getText().trim();
            String Password = Password_Field.getText().trim();
           // SHA_256.hashCode(Password_Field.getText().trim());

                if (!Login.equals("") && !Password.equals(""))
                loginUser(Login, Password);
            else
                Autorize.setText("Login or Password is empty");
        });

        RegistationBtn.setOnAction(actionEvent -> {
            RegistationBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/registrationUI.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });
    }

    private void loginUser(String login, String password) {

        try{
            User user = new User();
            user.USERNAME = login;
            user.PASSWORD = SHA_256.hashCode(password);
            user.TYPE = User.PressedBtnType.SignUn;

            ClientConnection.writer.writeObject(user);
            User recvUser = (User)ClientConnection.reader.readObject();
            System.out.println(recvUser.ADMINCODE);
            if(recvUser.ADMINCODE != null && !recvUser.Locked.equals("true"))
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/project/adminUI.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }else {
                if (recvUser.IsOnline && !recvUser.Locked.equals("true")) {
                    Kursach.ThisUser.USERNAME = login;
                    Kursach.ThisUser.PASSWORD = SHA_256.hashCode(password);
                    Kursach.id = recvUser.id;
                    Kursach.ThisUser.IsOnline = true;
                    RegistationBtn.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/project/userUI.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                } else
                    Autorize.setText("User\ndoesn't\nexist");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}