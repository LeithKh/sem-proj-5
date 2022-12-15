package com.example.semproj.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.semproj.client.ClientConnection;
import com.example.semproj.entity.User;
import com.example.semproj.other.SHA_256;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField AdminCode_Field;
    @FXML
    private TextField Firsntame_Field;

    @FXML
    private TextField Login_Field;

    @FXML
    private TextField Lastname_Field;

    @FXML
    private PasswordField Password_Field;

    @FXML
    private Button RegistrationBtn;

    User user;

    @FXML
    void initialize() {
        RegistrationBtn.setOnAction(actionEvent -> {
            if(SetUserRegistrationInfo(User.PressedBtnType.Registration)) {

                System.out.println(user.FIRSTNAME);
                System.out.println(user.LASTNAME);
                System.out.println(user.USERNAME);
                System.out.println(user.PASSWORD);
                System.out.println(user.ADMINCODE);

                AdminCode_Field.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/project/hello-view.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }

        });
    }

    public boolean SetUserRegistrationInfo(User.PressedBtnType Type) {
        if (!Firsntame_Field.getText().matches("[0-9]+.") && !Lastname_Field.getText().matches("[0-9]+.")) {
            user = new User(Firsntame_Field.getText().trim(), Lastname_Field.getText().trim(), Login_Field.getText().trim(), SHA_256.hashCode(Password_Field.getText().trim()),
                    AdminCode_Field.getText().trim(), Type);
            try {
                ClientConnection.writer.writeObject(user);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("ERROR");
            alert.setHeaderText("Incorrect first/last name input");
            alert.setContentText("use of numbers is forbidden");
            alert.showAndWait();
            return false;
        }
    }
}
