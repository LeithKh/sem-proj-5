package com.example.semproj.controller;

import java.io.IOException;

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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Button Accept_btn;

    @FXML
    private AnchorPane ActorP;

    @FXML
    private Button Add_btn;

    @FXML
    private Button Block_btn;

    @FXML
    private TextField CarName_Field;

    @FXML
    private Label CarName_lable;

    @FXML
    private TextField Cost_Field;

    @FXML
    private Label Cost_label;

    @FXML
    private TextField Count_Field;

    @FXML
    private Label Count_label;

    @FXML
    private Button Delete_btn;

    @FXML
    private Button Refresh_btn;

    @FXML
    private CheckBox Language;

    @FXML
    private CheckBox SegmentBox;

    @FXML
    private Label Segment_label;

    @FXML
    private CheckBox Theme;


/////////////////////////////////
    @FXML
    private TableView<Car> RequestTable;

    @FXML
    private TableColumn<Car, String> idUser;

    @FXML
    private TableColumn<Car, String> Request;


///////////////////////////////////
    @FXML
    private TableView<Car> UserTable;

    @FXML
    private TableColumn<Car, String> id;

    @FXML
    private TableColumn<Car, String> name;

    @FXML
    private TableColumn<Car, String> cost;

    @FXML
    private TableColumn<Car, String> locked;


////////////////////////////////////
    @FXML
    private TableView<Car> productTable;

    @FXML
    private TableColumn<Car, String> id1;

    @FXML
    private TableColumn<Car, String> name1;

    @FXML
    private TableColumn<Car, String> segment;

    @FXML
    private TableColumn<Car, String> cost1;

    @FXML
    private TableColumn<Car, String> count;

    @FXML
    private TableColumn<Car, String> grade;

    private ObservableList<Car> Productlist = FXCollections.observableArrayList();

    private ObservableList<Car> Userlist = FXCollections.observableArrayList();

    private ObservableList<Car> Requestlist = FXCollections.observableArrayList();


    @FXML
    void initialize() {


        if(Kursach.IsLightTheme)
            Theme.setSelected(false);
        else
            Theme.setSelected(true);

        if(Kursach.IsRussianLanguage)
            Language.setSelected(false);
        else
            Language.setSelected(true);

        Theme.setOnAction(actionEvent -> {
            if(Kursach.IsLightTheme){
                Kursach.IsLightTheme = false;
                ActorP.setStyle(ThemeAndLanguage.Dark);
            }else{
                Kursach.IsLightTheme = true;
                ActorP.setStyle(ThemeAndLanguage.Light);
            }
        });

        Language.setOnAction(actionEvent -> {
            if(Kursach.IsRussianLanguage){
                Kursach.IsRussianLanguage = false;
                Block_btn.setText("Block User");
                Accept_btn.setText("Accept request");
                CarName_lable.setText("Car Name");
                Segment_label.setText("Segment");
                Cost_label.setText("Cost");
                Count_label.setText("Count");
                Add_btn.setText("Add");
                Delete_btn.setText("Delete");
                Refresh_btn.setText("Refresh");

            }else{
                Kursach.IsRussianLanguage = true;
                Block_btn.setText("Заблокировать");
                Accept_btn.setText("Принять запрос");
                CarName_lable.setText("Название");
                Segment_label.setText("Сегмент");
                Cost_label.setText("Цена");
                Count_label.setText("Количество");
                Add_btn.setText("Добавить");
                Delete_btn.setText("Снять с продажи");
                Refresh_btn.setText("Перезагрузить");
            }
        });

        //ProductTable
        id1.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        name1.setCellValueFactory(cellValue -> cellValue.getValue().CarName);
        segment.setCellValueFactory(cellValue -> cellValue.getValue().Segment);
        cost1.setCellValueFactory(cellValue->cellValue.getValue().Cost);
        count.setCellValueFactory(cellValue->cellValue.getValue().Count);
        grade.setCellValueFactory(cellValue->cellValue.getValue().Grade);
        productTable.setEditable(true);

        //User Table
        id.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        name.setCellValueFactory(cellValue -> cellValue.getValue().CarName);
        cost.setCellValueFactory(cellValue->cellValue.getValue().Cost); //balance
        locked.setCellValueFactory(cellValue->cellValue.getValue().Grade); //lock
        UserTable.setEditable(true);

        //RequestTable
        idUser.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        Request.setCellValueFactory(cellValue -> cellValue.getValue().Cost);
        RequestTable.setEditable(true);

        User user = new User();
        user.TYPE = User.PressedBtnType.AddProductTable;
        try {
            ClientConnection.writer.writeObject(user);
            user = (User) ClientConnection.reader.readObject();
            int size = user.size;

            productTable.getItems().clear();
            for (int i = 0; i < size; ++i) {
                user = (User) ClientConnection.reader.readObject();
                Car car = new Car(user.idProducts, user.CarName, user.Segment, user.Cost, user.Count, user.Grade);
                Productlist.add(car);
            }

            productTable.setItems(Productlist);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        user.TYPE = User.PressedBtnType.GetAllUsers;
        try {
            ClientConnection.writer.writeObject(user);
            user = (User) ClientConnection.reader.readObject();
            int size = user.size;

            UserTable.getItems().clear();
            for (int i = 0; i < size; ++i) {
                user = (User) ClientConnection.reader.readObject();
                Car car = new Car(user.idProducts, user.CarName, user.Segment, user.Cost, user.Count, user.Grade);
                Userlist.add(car);
            }

            UserTable.setItems(Userlist);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        user.TYPE = User.PressedBtnType.GetReqests;
        try {
            ClientConnection.writer.writeObject(user);
            user = (User) ClientConnection.reader.readObject();
            int size = user.size;

            RequestTable.getItems().clear();
            for (int i = 0; i < size; ++i) {
                user = (User) ClientConnection.reader.readObject();
                Car car = new Car(user.idProducts, user.CarName, user.Segment, user.Cost, user.Count, user.Grade);
                Requestlist.add(car);
            }

            RequestTable.setItems(Requestlist);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Block_btn.setOnAction(actionEvent -> {
            Car car = UserTable.getSelectionModel().getSelectedItem();
            if(car.getIdProducts() != null) {
                User block = new User();
                block.TYPE = User.PressedBtnType.BlockUser;
                block.id = car.getIdProducts();
                System.out.println(car.Grade.getValue().toString());
                if(Boolean.parseBoolean(car.Grade.getValue().toString())) {
                block.Grade="false";
                }
                else {
                    block.Grade = "true";
                }

                try {
                    ClientConnection.writer.writeObject(block);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Accept_btn.setOnAction( actionEvent -> {
            Car car = RequestTable.getSelectionModel().getSelectedItem();

            if(car.getIdProducts() != null) {
                User accept = new User();
                accept.TYPE = User.PressedBtnType.AcceptRequest;
                accept.id = car.getIdProducts();
                accept.Cost = car.getCost();
                try {
                    ClientConnection.writer.writeObject(accept);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Add_btn.setOnAction(actionEvent -> {
            User add = new User();

            if(CarName_Field.getText() != null && Cost_Field.getText() !=null && Count_Field.getText() != null) {
                add.CarName = CarName_Field.getText().trim();
                if (SegmentBox.isSelected()) {
                    add.Segment = "Люкс";
                } else {
                    add.Segment = "Бюджет";
                }
                add.Cost = Cost_Field.getText().trim();
                add.Count = Count_Field.getText().trim();
                add.Grade = "0/5";
                add.TYPE = User.PressedBtnType.AddCar;
                try {
                    ClientConnection.writer.writeObject(add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Delete_btn.setOnAction( actionEvent -> {
            Car car = productTable.getSelectionModel().getSelectedItem();
            if(car.getIdProducts() != null) {
                User delete = new User();
                delete.TYPE = User.PressedBtnType.DeleteCar;
                delete.idProducts = car.getIdProducts();
                try {
                    ClientConnection.writer.writeObject(delete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Refresh_btn.setOnAction( actionEvent -> {
            Refresh_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/adminUI.fxml"));

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
