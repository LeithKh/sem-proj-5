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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label One;

    @FXML
    private Label Balance_Label;


    @FXML
    private Button Delete_btn;

    @FXML
    private Button Buy_btn;
    @FXML
    private Button Exit_btn;

    @FXML
    private Label Info_Label;

    @FXML
    private Button Product_btn;

    @FXML
    private Button Profile_btn;


    @FXML
    private Button WishList_btn;

    @FXML
    private TableView<Car> cartTable;

    @FXML
    private TableColumn<Car, String> cost;

    @FXML
    private TableColumn<Car, String> count;

    @FXML
    private TableColumn<Car, String> name;

    @FXML
    private TableColumn<Car, String> id;

    private ObservableList<Car> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane ActorP;

    @FXML
    void initialize() {

        if(Kursach.IsLightTheme)
            ActorP.setStyle(ThemeAndLanguage.Light);
        else
            ActorP.setStyle(ThemeAndLanguage.Dark);

        if(Kursach.IsRussianLanguage)
        {
            Profile_btn.setText(ThemeAndLanguage.ProfileBtn);
            Product_btn.setText(ThemeAndLanguage.ProductBtn);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtn);
            Exit_btn.setText(ThemeAndLanguage.ExitBtn);

            One.setText(ThemeAndLanguage.Balance);
            Delete_btn.setText(ThemeAndLanguage.DeleteBtn);
            Buy_btn.setText(ThemeAndLanguage.BuyBtn);

        }else {
            Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
            Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
            Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);

            One.setText(ThemeAndLanguage.BalanceAngl);
            Delete_btn.setText(ThemeAndLanguage.DeleteBtnAngl);
            Buy_btn.setText(ThemeAndLanguage.BuyBtnAngl);
        }

        Kursach.ThisUser.TYPE = User.PressedBtnType.CheckBalance;
        try{
            ClientConnection.writer.writeObject(Kursach.ThisUser);
            Kursach.ThisUser = (User)ClientConnection.reader.readObject();

        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }


        Balance_Label.setText(Kursach.ThisUser.Balance);
        id.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        name.setCellValueFactory(cellValue -> cellValue.getValue().CarName);
        cost.setCellValueFactory(cellValue->cellValue.getValue().Cost);
        count.setCellValueFactory(cellValue->cellValue.getValue().Count);

        cartTable.setEditable(true);

        User user = new User();
        user.TYPE = User.PressedBtnType.AddCartTable;
        user.id = Kursach.id;
        try {
            ClientConnection.writer.writeObject(user);
            user = (User) ClientConnection.reader.readObject();
            int size = user.size;

            cartTable.getItems().clear();
            for (int i = 0; i < size; ++i) {
                user = (User)ClientConnection.reader.readObject();
                Car car = new Car(user.idProducts, user.CarName, user.Segment, user.Cost, user.Count, user.Grade);
                list.add(car);
            }

            cartTable.setItems(list);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
///////////////////////////

        Buy_btn.setOnAction(actionEvent -> {
            if (cartTable.getSelectionModel().getSelectedItem()==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("ERROR");
                alert.setHeaderText("Car not selected");
                alert.setContentText("click on the car you want to buy");
                alert.showAndWait();
            }else {
                Car car = cartTable.getSelectionModel().getSelectedItem();

                User BuyUser = new User();
                BuyUser.id = Kursach.id;
                BuyUser.idProducts = car.getIdProducts();
                BuyUser.Count = car.getCount();
                BuyUser.Balance = Kursach.ThisUser.Balance;
                BuyUser.TYPE = User.PressedBtnType.BuyCar;

                int balance = Integer.parseInt(Balance_Label.getText());
                System.out.println(balance);
                int cost = Integer.parseInt(car.getCost());
                System.out.println(cost);
                if (!car.getIdProducts().equals(null)) {
                    if (!BuyUser.Count.equals("0")) {
                        if (balance >= cost) {
                            balance -= cost;
                            BuyUser.Balance = Integer.toString(balance);
                            try {
                                ClientConnection.writer.writeObject(BuyUser);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Info_Label.setText("Вы купили машину перезагрузите страницу");
                        } else {
                            Info_Label.setText("Недостаточно денег на счете");
                        }
                    } else {
                        Info_Label.setText("Нет в наличии");
                    }
                } else {
                    Info_Label.setText("Вы не выбрали товар для покупки");
                }
            }
        });

        Delete_btn.setOnAction( actionEvent -> {
            Car car = cartTable.getSelectionModel().getSelectedItem();

            User BuyUser = new User();
            BuyUser.id = Kursach.id;
            BuyUser.idProducts = car.getIdProducts();
            BuyUser.TYPE = User.PressedBtnType.DeleteFromCart;
            try {
                ClientConnection.writer.writeObject(BuyUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Info_Label.setText("Перезагрузите страницу");
        });
/////////////////////////// Cheap_btn Expensive_btn







        Profile_btn.setOnAction( actionEvent -> {

            Profile_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/userUI.fxml"));

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

        Product_btn.setOnAction( actionEvent -> {

            Product_btn.getScene().getWindow().hide();
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

        WishList_btn.setOnAction( actionEvent -> {

            WishList_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/cartUI.fxml"));

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

        Exit_btn.setOnAction(actionEvent -> {
            Exit_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/hello-view.fxml"));

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
