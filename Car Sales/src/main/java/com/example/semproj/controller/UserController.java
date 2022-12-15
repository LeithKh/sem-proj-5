package com.example.semproj.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.semproj.client.ClientConnection;
import com.example.semproj.entity.User;
import com.example.semproj.client.Kursach;
import com.example.semproj.client.ThemeAndLanguage;
import com.example.semproj.other.SHA_256;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label Five;

    @FXML
    private Label Four;
    @FXML
    private Label Two;

    @FXML
    private Label One;

    @FXML
    private AnchorPane ActorP;

    @FXML
    private CheckBox Theme;

    @FXML
    private CheckBox Language;

    @FXML
    private Label Balance_Fiield;

    @FXML
    private TextField ChangeBalance_Field;

    @FXML
    private TextField ChangePassword_Field;

    @FXML
    private Button ChangePassword_btn;

    @FXML
    private Button Exit_btn;

    @FXML
    private Label FirstName_Field;
    @FXML
    private Label LastName_Field;

    @FXML
    private Button Product_btn;

    @FXML
    private Button Profile_btn;

    @FXML
    private Button UpBalance_btn;

    @FXML
    private Label UserName_Field;

    @FXML
    private Button WishList_btn;


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


            One.setText(ThemeAndLanguage.Firstname);
            Two.setText(ThemeAndLanguage.Lastname);
            Four.setText(ThemeAndLanguage.Username);
            Five.setText(ThemeAndLanguage.Balance);


            UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtn);
            ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtn);
            ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalance);
            ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePassword);

        }else {

            Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
            Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
            WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
            Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);


            One.setText(ThemeAndLanguage.FirstnameAngl);
            Two.setText(ThemeAndLanguage.LastnameAngl);
            Four.setText(ThemeAndLanguage.UsernameAngl);
            Five.setText(ThemeAndLanguage.BalanceAngl);


            UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtnAngl);
            ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtnAngl);
            ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalanceAngl);
            ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePasswordAngl);
        }


        Kursach.ThisUser.TYPE = User.PressedBtnType.InitUser;
        try {
            ClientConnection.writer.writeObject(Kursach.ThisUser);
            Kursach.ThisUser = (User)ClientConnection.reader.readObject();

            FirstName_Field.setText(Kursach.ThisUser.FIRSTNAME);
            LastName_Field.setText(Kursach.ThisUser.LASTNAME);
            UserName_Field.setText(Kursach.ThisUser.USERNAME);
            Balance_Fiield.setText(Kursach.ThisUser.Balance);

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) { e.printStackTrace(); }




        ChangePassword_btn.setOnAction(actionEvent -> {
            try {
                if(!ChangePassword_Field.equals("") && !ChangePassword_Field.equals(Kursach.ThisUser.PASSWORD))
                {
                    Kursach.ThisUser.TYPE = User.PressedBtnType.ChangePassword;
                    Kursach.ThisUser.PASSWORD = SHA_256.hashCode(ChangePassword_Field.getText().trim());
                            //ChangePassword_Field.getText().trim();

                    ClientConnection.writer.writeObject(Kursach.ThisUser);
                    ChangePassword_Field.setText("Пароль изменен");
                }
                else
                {
                    ChangePassword_Field.setPromptText("Вы ничего не ввели либо используете старый пароль");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        UpBalance_btn.setOnAction( actionEvent -> {
            try {
                if (!ChangeBalance_Field.equals("")) {
                    Kursach.ThisUser.TYPE = User.PressedBtnType.AddBalance;
                    Kursach.ThisUser.TempAddBalance = ChangeBalance_Field.getText().trim();
                    ClientConnection.writer.writeObject(Kursach.ThisUser);

                    ChangeBalance_Field.setText("Ожидайте обработки запроса");
                } else {
                    ChangePassword_Field.setPromptText("Вы ничего не ввели");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Language.setOnAction(actionEvent -> {
            if(Kursach.IsRussianLanguage){
                Kursach.IsRussianLanguage = false;

                Profile_btn.setText(ThemeAndLanguage.ProfileBtnAngl);
                Product_btn.setText(ThemeAndLanguage.ProductBtnAngl);
                WishList_btn.setText(ThemeAndLanguage.WishlistBtnAngl);
                Exit_btn.setText(ThemeAndLanguage.ExitBtnAngl);


                One.setText(ThemeAndLanguage.FirstnameAngl);
                Two.setText(ThemeAndLanguage.LastnameAngl);
                Four.setText(ThemeAndLanguage.UsernameAngl);
                Five.setText(ThemeAndLanguage.BalanceAngl);


                UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtnAngl);
                ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtnAngl);
                ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalanceAngl);
                ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePasswordAngl);

            }else{
                Kursach.IsRussianLanguage = true;

                Profile_btn.setText(ThemeAndLanguage.ProfileBtn);
                Product_btn.setText(ThemeAndLanguage.ProductBtn);
                WishList_btn.setText(ThemeAndLanguage.WishlistBtn);

                Exit_btn.setText(ThemeAndLanguage.ExitBtn);


                One.setText(ThemeAndLanguage.Firstname);
                Two.setText(ThemeAndLanguage.Lastname);

                Four.setText(ThemeAndLanguage.Username);
                Five.setText(ThemeAndLanguage.Balance);


                UpBalance_btn.setText(ThemeAndLanguage.AddBalanceBtn);
                ChangePassword_btn.setText(ThemeAndLanguage.ChangePasswordBtn);
                ChangeBalance_Field.setPromptText(ThemeAndLanguage.PromAddBalance);
                ChangePassword_Field.setPromptText(ThemeAndLanguage.PromChangePassword);
            }
        });

        Theme.setOnAction(actionEvent -> {
            if(Kursach.IsLightTheme){
                Kursach.IsLightTheme = false;
                ActorP.setStyle(ThemeAndLanguage.Dark);
            }else{
                Kursach.IsLightTheme = true;
                ActorP.setStyle(ThemeAndLanguage.Light);
            }
        });

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
