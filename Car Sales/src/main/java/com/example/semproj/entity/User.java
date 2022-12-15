package com.example.semproj.entity;

import java.io.Serializable;

public class User implements Serializable {
    public User.PressedBtnType TYPE;
    public String id;
    public String FIRSTNAME;
    public String LASTNAME;
    public String USERNAME;
    public String PASSWORD;
    public String ADMINCODE;
    public Boolean IsOnline = false;
    public String Balance = "1000";
    public String TempAddBalance;
    public String Locked = "false";

    //Product info
    public String idProducts;
    public String CarName;
    public String Segment;
    public String Cost;
    public String Count;
    public String Grade;
    public boolean IsLast = false;
    public int size;

    public User() {
    }

    public User(String FIRSTNAME, String LASTNAME, String USERNAME, String PASSWORD, String ADMINCODE, User.PressedBtnType TYPE) {
        this.TYPE = TYPE;
        this.FIRSTNAME = FIRSTNAME;
        this.LASTNAME = LASTNAME;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.ADMINCODE = ADMINCODE;
    }

    public User(User user) {
        this.TYPE = user.TYPE;
        this.FIRSTNAME = user.FIRSTNAME;
        this.LASTNAME = user.LASTNAME;
        this.USERNAME = user.USERNAME;
        this.PASSWORD = user.PASSWORD;
        this.ADMINCODE = user.ADMINCODE;
    }

   public enum PressedBtnType {
        SignUn,
        Registration,
        InitUser,
        ChangePassword,
        AddBalance,
        AddProductTable,
        AddGrade,
        AddCartTable,
        AddToCart,
        BuyCar,
        CheckBalance,
        DeleteFromCart,
        CheapSort,
        ExpensiveSort,
        Comment,
        InitComments,
        GetAllUsers,
        GetReqests,
        BlockUser,
        AcceptRequest,
        AddCar,
        DeleteCar
    }
}