package com.mysapp;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Connection connection = null;
    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    public static int All_UserID = 0;
    public static int All_OwnerID = 0;
    public static String All_FullName;

    @FXML
    void LoginUser(ActionEvent event) {
        Notifications notifications = Notifications.create()
                .title("Error")
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .onAction(e->{
                    System.out.println("click");
                });
        if(userName.getText().isEmpty() && password.getText().isEmpty()){
            notifications.text("Empty User And Password");
            notifications.showError();
        }
         else if(userName.getText().isEmpty())
        {
            notifications.text("User is Empty");
            notifications.showError();

        }else if(password.getText().isEmpty()){
            notifications.text("Password is Empty");
            notifications.showError();
        }else{
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT `owner_id`,`full_name`,`user_id`,`password` FROM `user` WHERE `user_name` ='"+userName.getText()+"'");
                if(resultSet.next()) {

                    if (password.getText().equals(resultSet.getString("password"))) {
                        All_UserID = resultSet.getInt("user_id");
                        All_OwnerID = resultSet.getInt("owner_id");
                        All_FullName = resultSet.getString("full_name");


                        FXMLLoader loader;
                        if(All_UserID == All_OwnerID) {
                            loader = new FXMLLoader(getClass().getResource("/mainpanel.fxml"));
                            loader.setController(new MainPanelController(connection));
                        }
                        else {
                            loader = new FXMLLoader(getClass().getResource("/Employeemainpanel.fxml"));
                            loader.setController(new EmployeeMainPanelController(connection));
                        }

                        Stage stage = new Stage();
                        Parent parent = loader.load();
                        stage.setTitle("Main Control Panel");
                        stage.setScene(new Scene(parent));

                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        stage.show();
                        stage.setOnCloseRequest(event1 -> {
                            System.exit(0);
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                    }else{
                        notifications.text("Wrong Password! Try Again.");
                        notifications.showError();
                    }
                }else{
                    notifications.text("Invalid User Name!");
                    notifications.showError();
                }
            }catch (SQLException e) {
                e.printStackTrace();
                notifications.text("Connection Failed.");
                notifications.showWarning();
            } catch (IOException e) {
                e.printStackTrace();
                notifications.text("Loading Failed.");
                notifications.showWarning();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = new SQLConnection().connect();
    }
}

