package com.example.semproj.DAO;

import com.example.semproj.server.Configs;
import com.example.semproj.server.Const;
import com.example.semproj.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseHandlerDAO extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void Registration(User user)
    {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + ","
                + Const.USERS_PASSWORD + "," + Const.USER_ADMINCODE + ","+ Const.USER_BALANCE + "," + Const.USER_LOCKED + ")"
                + "VALUES(?,?,?,?,?,?,?)";
        try {
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, user.FIRSTNAME);
        prSt.setString(2, user.LASTNAME);
        prSt.setString(3, user.USERNAME);
        prSt.setString(4, user.PASSWORD);
        if(user.ADMINCODE.equals("admin"))
            prSt.setString(5, user.ADMINCODE);
        else{ prSt.setString(5, null);}
        prSt.setString(6, user.Balance);
        prSt.setString(7, user.Locked);


        prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user){

        ResultSet resultSet = null;
        String select = "SELECT * FROM "+ Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {

            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.USERNAME);
            prSt.setString(2, user.PASSWORD);

            resultSet = prSt.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  resultSet;
    }

    public int getCountUsers()
    {
        int cout = 0;
        String select = "SELECT * FROM "+ Const.USER_TABLE;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                cout++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cout;
    }
    public void ChangeGradeCar(String Grade, String id)
    {
        String change = "UPDATE " + Const.PRODUCT_TABLE + " SET " + Const.PRODUCTS_GRADE + "=? WHERE " + Const.PRODUCTS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, Grade);
            prSt.setString(2, id);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ChangeUserPassword(User user)
    {
        String change = "UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_PASSWORD + "=? WHERE " + Const.USERS_USERNAME + "=?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, user.PASSWORD);
            prSt.setString(2, user.USERNAME);
            prSt.execute();
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void ChangeUserBalance(User user)
    {
        String change = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_BALANCE + "=? WHERE " + Const.USERS_ID + "=?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, user.Balance);
            prSt.setString(2, user.id);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddCarToProductTable(User user){
        String insert = "INSERT INTO " + Const.PRODUCT_TABLE + "( CarName,Segment,Cost,Count,Grade )" + "VALUES(?,?,?,?,?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.CarName);
            prSt.setString(2, user.Segment);
            prSt.setString(3, user.Cost);
            prSt.setString(4, user.Count);
            prSt.setString(5, user.Grade);
            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void DeleteCarFromProductTable(User user)
    {
        String delete = "DELETE FROM "+ Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCTS_ID + "=?";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, user.idProducts);
            prSt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void AddProductToCart(User user)
    {
        String insert = "INSERT INTO " + Const.WISHLIST_TABLE + "(" + Const.WISHLIST_USERSID + ","
                + Const.WISHLIST_PRODUCTSID + ")" + "VALUES(?,?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.id);
            prSt.setString(2, user.idProducts);
            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void BalanceRequst(User user)
    {
        String insert = "INSERT INTO " + Const.REQUEST_TABLE + "(" + Const.REQUESTS_USERSID + ","
                + Const.REQUESTS_REQUESTBALANCE + ")" + "VALUES(?,?)";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            String userId = new String();
            ResultSet result = getUser(user);

            while(result.next())
            {
                userId = result.getString(1);
            }

            prSt.setString(1, userId);
            prSt.setString(2, user.TempAddBalance);
            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public ArrayList<User> GetReqest()
    {
        ArrayList<User> list =new ArrayList<>();
        String select = "SELECT idUsers,RequestBalance FROM carshowroom_shema.requestupbalance";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            int i = 0;
            while (resultSet.next())
            {
                User newUser = new User();
                newUser.idProducts = resultSet.getString(1);
                newUser.Cost = resultSet.getString(2);
                list.add(i, newUser);
                ++i;
            }

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return  list;
    }



    public ArrayList<User> GetUsersForAdmin()
    {
        ArrayList<User> list =new ArrayList<>();
        String select = "SELECT idUsers,username,balance,users.locked FROM carshowroom_shema.users";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            int i = 0;
            while (resultSet.next())
            {
                User newUser = new User();
                newUser.idProducts = resultSet.getString(1);
                newUser.CarName = resultSet.getString(2);
                newUser.Grade = resultSet.getString(4);
                newUser.Cost = resultSet.getString(3);
                list.add(i, newUser);
                ++i;
            }

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return  list;
    }

    public ArrayList<User> GetBuyInfo(User user)
    {
        ArrayList<User> list =new ArrayList<>();
        String select = "SELECT products.idProducts,CarName,Count,Cost FROM "+ Const.PRODUCT_TABLE + " INNER JOIN " + Const.WISHLIST_TABLE
                + " ON Products.idProducts = UserWishList.idProducts WHERE UserWishList.idUsers=?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.id);
            ResultSet resultSet = prSt.executeQuery();

            int i = 0;
            while (resultSet.next())
            {
                User newUser = new User();
                newUser.idProducts = resultSet.getString(1);
                newUser.CarName = resultSet.getString(2);
                newUser.Count = resultSet.getString(3);
                newUser.Cost = resultSet.getString(4);
                list.add(i, newUser);
                ++i;
            }

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return  list;
    }

    public ArrayList<User> Sorting(boolean type)
    {
        ArrayList<User> list =new ArrayList<>();
        String Segment;
        if(type)
            Segment = "Бюджет";
        else
            Segment = "Люкс";

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCTS_SEGMENT + "=?";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,Segment);
            ResultSet resultSet = prSt.executeQuery();

            int i = 0;
            while (resultSet.next()){
                User user = new User();
                user.idProducts = resultSet.getString(1);
                user.CarName = resultSet.getString(2);
                user.Segment = resultSet.getString(3);
                user.Cost = resultSet.getString(4);
                user.Count = resultSet.getString(5);
                user.Grade = resultSet.getString(6);
                list.add(i, user);
                i++;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void AddComment(User user)
    {
        String insert = "INSERT INTO " + Const.COMMENT_TABLE + "(" + Const.COMMENTS_USERSID + ","
                + Const.COMMENTS_PRODUCTSID + "," + Const.COMMENTS_COMMENT  + ")" + "VALUES(?,?,?)";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.id);
            prSt.setString(2, user.idProducts);
            prSt.setString(3, user.TempAddBalance);
            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public ArrayList<User> GetComments()
    {
        ArrayList<User> list =new ArrayList<>();
        String select = "SELECT comments.idComments, products.CarName, comments.Comment  FROM  carshowroom_shema.comments " +
                "INNER JOIN carshowroom_shema.products" +
                " ON Products.idProducts = comments.idProducts";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            int i = 0;
            while (resultSet.next())
            {
                User newUser = new User();
                newUser.idProducts = resultSet.getString(1);
                newUser.CarName = resultSet.getString(2);
                newUser.Grade = resultSet.getString(3);
                list.add(i, newUser);
                ++i;
            }

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return  list;
    }

    public ArrayList<User> GetCarsInfo(){
        ArrayList<User> list =new ArrayList<>();
        String select = "SELECT * FROM "+ Const.PRODUCT_TABLE;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            int i = 0;
            while (resultSet.next()){
                User user = new User();
                user.idProducts = resultSet.getString(1);
                user.CarName = resultSet.getString(2);
                user.Segment = resultSet.getString(3);
                user.Cost = resultSet.getString(4);
                user.Count = resultSet.getString(5);
                user.Grade = resultSet.getString(6);
                list.add(i, user);
                i++;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void DeleteFormWtshList(User user)
    {
        String delete = "DELETE FROM "+ Const.WISHLIST_TABLE + " WHERE " + Const.WISHLIST_USERSID + "=? AND " + Const.WISHLIST_PRODUCTSID + "=?";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, user.id);
            prSt.setString(2, user.idProducts);
            prSt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void AcceptRequest(User user)
    {
        String delete = "DELETE FROM "+ Const.REQUEST_TABLE + " WHERE " + Const.REQUESTS_USERSID + "=? AND " + Const.REQUESTS_REQUESTBALANCE + "=?";
        String select = "SELECT balance FROM "+ Const.USER_TABLE + " WHERE " + Const.USERS_ID + "=?";
        String change = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_BALANCE + "=? WHERE " + Const.USERS_ID + "=?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, user.id);
            prSt.setString(2, user.Cost);
            prSt.execute();

            /*PreparedStatement prSt1 = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.id);
            ResultSet resultSet = prSt1.executeQuery();
            String balance = "";
            while(resultSet.next())
            {
                balance = resultSet.getString(1);
            }
            int Balanc = Integer.parseInt(balance);
            int ReqBalance = Integer.parseInt(user.Cost);
            int Res = Balanc + ReqBalance;
            user.Cost = Integer.toString(Res);
            */

            PreparedStatement prSt2 = getDbConnection().prepareStatement(change);
            prSt2.setString(1, user.Cost);
            prSt2.setString(2, user.id);
            prSt2.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void BlockUser(User user)
    {
        String change = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_LOCKED + "=? WHERE " + Const.USERS_ID + "=?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, user.Grade);
            prSt.setString(2, user.id);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void UpdateProductsTableAfterSell(User user)
    {
        String change = "UPDATE " + Const.PRODUCT_TABLE + " SET " + Const.PRODUCTS_COUNT + "=? WHERE " + Const.PRODUCTS_ID + "=?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, user.Count);
            prSt.setString(2, user.idProducts);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
