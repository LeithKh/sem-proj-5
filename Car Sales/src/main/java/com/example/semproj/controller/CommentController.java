package com.example.semproj.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.semproj.client.ClientConnection;
import com.example.semproj.entity.Car;
import com.example.semproj.entity.User;
import com.example.semproj.client.Kursach;
import com.example.semproj.client.ThemeAndLanguage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CommentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ActorP;

    @FXML
    private Button Back_btn;

    @FXML
    private TableColumn<Car, String> grade;

    @FXML
    private TableColumn<Car, String> id;

    @FXML
    private TableColumn<Car, String> name;

    @FXML
    private TableView<Car> productTable;

    private ObservableList<Car> list = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        if(Kursach.IsLightTheme)
            ActorP.setStyle(ThemeAndLanguage.Light);
        else
            ActorP.setStyle(ThemeAndLanguage.Dark);


        if(Kursach.IsRussianLanguage)
            Back_btn.setText(ThemeAndLanguage.BackBtn);
        else
            Back_btn.setText(ThemeAndLanguage.BackBtnAngl);



        id.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        name.setCellValueFactory(cellValue -> cellValue.getValue().CarName);
        grade.setCellValueFactory(cellValue->cellValue.getValue().Grade);

        productTable.setEditable(true);

        User user = new User();
        user.TYPE = User.PressedBtnType.InitComments;
        try {
            ClientConnection.writer.writeObject(user);
            user = (User) ClientConnection.reader.readObject();
            int size = user.size;

            productTable.getItems().clear();
            for (int i = 0; i < size; ++i) {
                user = (User) ClientConnection.reader.readObject();
                Car car = new Car(user.idProducts, user.CarName, user.Segment, user.Cost, user.Count, user.Grade);
                list.add(car);
            }

            productTable.setItems(list);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


////////////////////////////////////

        Back_btn.setOnAction(actionEvent -> {
            Back_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/productUI.fxml"));

            try {
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

}
