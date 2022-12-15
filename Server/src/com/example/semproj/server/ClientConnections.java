package com.example.semproj.server;

import com.example.semproj.DAO.DatabaseHandlerDAO;
import com.example.semproj.entity.User;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClientConnections {

        public void RequestHandler(Socket s) throws IOException {// полкчение инфо от клиента 
            Socket socket = s;
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client Connected");
            while(true) {
                try {
                    DatabaseHandlerDAO action = new DatabaseHandlerDAO();
                    User recvUser = (User)reader.readObject();//получение типа действий и инфо от клиента

                    if (recvUser.TYPE == User.PressedBtnType.Registration) {
                        action.Registration(recvUser);//вызов DAO для записи в БД
                        System.out.println("User added in database");
                    }


                    if(recvUser.TYPE == User.PressedBtnType.SignUn)//Авторизация
                    {
                        ResultSet result = action.getUser(recvUser);//получение списка пользователей из БД
                        int count = 0;
                        try {
                            while (result.next()) {//перебор всех пользователей
                                count++;
                                recvUser.id = result.getString(1);
                                recvUser.ADMINCODE = result.getString(6);
                            }
                            if(count >= 1) {//если пользователь есть в БД
                                recvUser.IsOnline = true; //присвоение существования

                                writer.writeObject(recvUser);
                            }
                            else
                                writer.writeObject(recvUser);
                        } catch (SQLException e) {e.printStackTrace();}


                    }


                    if(recvUser.TYPE == User.PressedBtnType.InitUser)//инициализация
                    {
                        ResultSet result = action.getUser(recvUser);
                        try {
                            User user = new User();
                            while(result.next()) {//получение списка пользователей с этим логином и паролем
                                user.FIRSTNAME = result.getString(2);
                                user.LASTNAME = result.getString(3);
                                user.USERNAME = result.getString(4);
                                user.PASSWORD = result.getString(5);
                                user.ADMINCODE = result.getString(6);
                                user.Balance = result.getString(7);
                                user.Locked = result.getString(8);
                            }
                            writer.writeObject(user);
                        } catch (SQLException e) { e.printStackTrace(); }
                    }

                    if(recvUser.TYPE == User.PressedBtnType.ChangePassword)
                    {
                        action.ChangeUserPassword(recvUser);//изсенение пароля в БД с помощью логина
                        System.out.println("Password changed");

                    }


                    if (recvUser.TYPE == User.PressedBtnType.AddBalance)//добавление баланса по ID
                    {
                        action.BalanceRequst(recvUser);
                        System.out.println("The Request was accepted");
                    }


                    if(recvUser.TYPE == User.PressedBtnType.AddProductTable)//возвращение всех машин БД
                    {
                        ArrayList<User> list = action.GetCarsInfo();
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i){
                            writer.writeObject(list.get(i));
                        }
                    }


                    if(recvUser.TYPE == User.PressedBtnType.AddCartTable)//получение корзины пользователя
                    {
                        ArrayList<User> list = action.GetBuyInfo(recvUser);
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i){
                            writer.writeObject(list.get(i));
                        }
                    }


                    if(recvUser.TYPE == User.PressedBtnType.AddGrade)//добавление оценки авто
                    {
                        float Count = action.getCountUsers();
                        float Grade = Integer.parseInt(recvUser.TempAddBalance);
                        int resultGrade = (int)Math.ceil(Count / Grade);
                        while(true)
                        {
                            if(resultGrade >= 0 && resultGrade <= 5)
                                break;
                            resultGrade -= 5;
                        }
                        String grade = Integer.toString(resultGrade);
                        grade += "/5";
                        action.ChangeGradeCar(grade, recvUser.idProducts);
                    }


                    if(recvUser.TYPE == User.PressedBtnType.AddToCart)//добавление к корзине
                    {
                        action.AddProductToCart(recvUser);
                        System.out.println("The Car added to client's Wishlist");
                    }

                    if(recvUser.TYPE == User.PressedBtnType.Comment)
                    {
                        action.AddComment(recvUser);
                        System.out.println("Comment added");
                    }

                    if(recvUser.TYPE == User.PressedBtnType.InitComments)//получение коментов
                    {
                        ArrayList<User> list = action.GetComments();
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i){
                            writer.writeObject(list.get(i));
                        }
                    }

                    if(recvUser.TYPE == User.PressedBtnType.BuyCar)
                    {
                        action.DeleteFormWtshList(recvUser);
                        int count = Integer.parseInt(recvUser.Count);
                        System.out.println(count);
                        count--;
                        System.out.println(count);
                        recvUser.Count = Integer.toString(count);
                        action.UpdateProductsTableAfterSell(recvUser);
                        action.ChangeUserBalance(recvUser);
                    }

                    if(recvUser.TYPE == User.PressedBtnType.DeleteFromCart)
                    {
                        action.DeleteFormWtshList(recvUser);
                    }

                    if(recvUser.TYPE == User.PressedBtnType.CheckBalance){
                        ResultSet resultSet = action.getUser(recvUser);
                        int count = 0;
                        try {
                            while (resultSet.next()) {
                                count++;
                                recvUser.Balance = resultSet.getString(7);
                            }
                            if(count >= 1) {
                                recvUser.IsOnline = true;
                                writer.writeObject(recvUser);
                            }
                            else
                                writer.writeObject(recvUser);
                        } catch (SQLException e) {e.printStackTrace();}
                    }

                    if(recvUser.TYPE == User.PressedBtnType.CheapSort)
                    {
                        ArrayList<User> list = action.Sorting(true);
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i){
                            writer.writeObject(list.get(i));
                        }
                    }

                    if(recvUser.TYPE == User.PressedBtnType.ExpensiveSort)
                    {
                        ArrayList<User> list = action.Sorting(false);
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i){
                            writer.writeObject(list.get(i));
                        }
                    }

                    if(recvUser.TYPE == User.PressedBtnType.GetAllUsers)
                    {
                        ArrayList<User> list = action.GetUsersForAdmin();
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i){
                            writer.writeObject(list.get(i));
                        }
                    }
                    if(recvUser.TYPE == User.PressedBtnType.GetReqests)
                    {
                        ArrayList<User> list = action.GetReqest();
                        recvUser.size = list.size();
                        writer.writeObject(recvUser);
                        for(int i = 0;i < list.size(); ++i) {
                            writer.writeObject(list.get(i));
                        }
                    }
                    if(recvUser.TYPE == User.PressedBtnType.AddCar)
                    {
                        action.AddCarToProductTable(recvUser);
                    }

                    if (recvUser.TYPE == User.PressedBtnType.DeleteCar)
                    {
                        action.DeleteCarFromProductTable(recvUser);
                    }

                    if(recvUser.TYPE == User.PressedBtnType.BlockUser)
                    {
                        action.BlockUser(recvUser);
                        System.out.println("User access status changed");
                    }
                    if(recvUser.TYPE == User.PressedBtnType.AcceptRequest)
                    {
                        action.AcceptRequest(recvUser);
                        System.out.println("Request resolved");
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
}
