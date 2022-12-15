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
import javafx.stage.Stage;

public class ProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Cheap_btn;

    @FXML
    private Button Expensive_btn;

    @FXML
    private Button AddComment_btn;

    @FXML
    private Button AddGrade_btn;

    @FXML
    private Button AddToCart_btn;

    @FXML
    private Button CheckComments_btn;

    @FXML
    private TextField Review_Field;

    @FXML
    private Button Exit_btn;

    @FXML
    private Button Product_btn;

    @FXML
    private Button Profile_btn;

    @FXML
    private Button WishList_btn;

    @FXML
    private TextField Grade;

    @FXML
    private TableView<Car> productTable;

    @FXML
    private TableColumn<Car, String> id;

    @FXML
    private TableColumn<Car, String> name;

    @FXML
    private TableColumn<Car, String> segment;

    @FXML
    private TableColumn<Car, String> cost;

    @FXML
    private TableColumn<Car, String> count;

    @FXML
    private TableColumn<Car, String> grade;

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

            AddToCart_btn.setText(ThemeAndLanguage.AddToCartBtn);
            AddComment_btn.setText(ThemeAndLanguage.AddReviewBtn);
            AddGrade_btn.setText(ThemeAndLanguage.AddGradeBtn);
            CheckComments_btn.setText(ThemeAndLanguage.CheckReviewBtn);
            Cheap_btn.setText(ThemeAndLanguage.BudgetBtn);
            Expensive_btn.setText(ThemeAndLanguage.LuxuryBtn);


        }else {
            Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
            Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
            Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);

            AddToCart_btn.setText(ThemeAndLanguage.AddToCartBtnAngl);
            AddComment_btn.setText(ThemeAndLanguage.AddReviewBtnAngl);
            AddGrade_btn.setText(ThemeAndLanguage.AddGradeBtnAngl);
            CheckComments_btn.setText(ThemeAndLanguage.CheckReviewBtnAngl);
            Cheap_btn.setText(ThemeAndLanguage.BudgetBtnAngl);
            Expensive_btn.setText(ThemeAndLanguage.LuxuryBtnAngl);
        }

        id.setCellValueFactory(cellValue -> cellValue.getValue().idProducts);
        name.setCellValueFactory(cellValue -> cellValue.getValue().CarName);
        segment.setCellValueFactory(cellValue -> cellValue.getValue().Segment);
        cost.setCellValueFactory(cellValue->cellValue.getValue().Cost);
        count.setCellValueFactory(cellValue->cellValue.getValue().Count);
        grade.setCellValueFactory(cellValue->cellValue.getValue().Grade);

        productTable.setEditable(true);

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
                list.add(car);
            }

            productTable.setItems(list);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
////////////////////////////////////


        AddGrade_btn.setOnAction( actionEvent -> {

            String grade = Grade.getText().trim();
            if(grade.equals("1") || grade.equals("2") || grade.equals("3") || grade.equals("4") || grade.equals("5"))
            {
                User UserGrade = new User();
                UserGrade.TempAddBalance = grade;
                Car car = productTable.getSelectionModel().getSelectedItem();
                UserGrade.idProducts = car.getIdProducts();
                UserGrade.TYPE = User.PressedBtnType.AddGrade;
                try {
                    ClientConnection.writer.writeObject(UserGrade);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        AddToCart_btn.setOnAction( actionEvent -> {

            Car car = productTable.getSelectionModel().getSelectedItem();

            User BuyUser = new User();
            BuyUser.TYPE = User.PressedBtnType.AddToCart;
            BuyUser.id = Kursach.id;
            BuyUser.idProducts = car.getIdProducts();

            try {
                ClientConnection.writer.writeObject(BuyUser);
            } catch (IOException e){
                e.printStackTrace();
            }
        });


        AddComment_btn.setOnAction( actionEvent -> {

            Car car = productTable.getSelectionModel().getSelectedItem();
            if(!car.getIdProducts().equals(null)){
                if(!Review_Field.getText().equals(null)) {
                    User Comment = new User();
                    Comment.id = Kursach.id;
                    Comment.idProducts = car.getIdProducts();
                    Comment.TempAddBalance = Review_Field.getText().trim();
                    Comment.TYPE = User.PressedBtnType.Comment;
                    try {
                        ClientConnection.writer.writeObject(Comment);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("Строка пожеланий пуста");
                }

            } else
                System.out.println("Вы не выбрали товар");

        });

        CheckComments_btn.setOnAction(actionEvent -> {
            CheckComments_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/project/commentUI.fxml"));

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



        Cheap_btn.setOnAction( actionEvent -> {
            list.clear();
            User Sort = new User();
            Sort.TYPE = User.PressedBtnType.CheapSort;
            try {
                ClientConnection.writer.writeObject(Sort);
                Sort = (User) ClientConnection.reader.readObject();
                int size = Sort.size;

                productTable.getItems().clear();
                for (int i = 0; i < size; ++i) {
                    Sort = (User) ClientConnection.reader.readObject();
                    Car car = new Car(Sort.idProducts, Sort.CarName, Sort.Segment, Sort.Cost, Sort.Count, Sort.Grade);
                    list.add(car);
                }
                productTable.setItems(list);
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        });

        Expensive_btn.setOnAction( actionEvent -> {
            list.clear();
            User Sort = new User();
            Sort.TYPE = User.PressedBtnType.ExpensiveSort;
            try {
                ClientConnection.writer.writeObject(Sort);
                Sort = (User) ClientConnection.reader.readObject();
                int size = Sort.size;

                productTable.getItems().clear();
                for (int i = 0; i < size; ++i) {
                    Sort = (User) ClientConnection.reader.readObject();
                    Car car = new Car(Sort.idProducts, Sort.CarName, Sort.Segment, Sort.Cost, Sort.Count, Sort.Grade);
                    list.add(car);
                }
                productTable.setItems(list);
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        } );





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

