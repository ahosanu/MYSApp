package com.mysapp.Product;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddProduct implements Initializable{
    Connection connection;
    @FXML
    JFXTextField proName,sellPrice,proPrice,discount;
    @FXML
    JFXTextArea Description;
    @FXML
    JFXDatePicker indate;
    @FXML
    JFXDatePicker ExpiryDate;
    @FXML
    DatePicker ExpiryDate2;
    @FXML
    JFXComboBox<String> Category;

    boolean isSubmit = false;

    SimpleStringProperty SproName,SsellPrice,SproPrice,Sdiscount,SexpiryDate;

    public AddProduct(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellPrice.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                char c = sellPrice.getText().charAt(oldValue.intValue());
                /** Check if the new character is the number or other's */
                if( c > '9' || c < '0'){
                    /** if it's not number then just setText to previous one */
                    sellPrice.setText(sellPrice.getText().substring(0,sellPrice.getText().length()-1));
                }
            }
        });
        proPrice.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                char c = proPrice.getText().charAt(oldValue.intValue());
                /** Check if the new character is the number or other's */
                if( c > '9' || c < '0'){
                    /** if it's not number then just setText to previous one */
                    proPrice.setText(proPrice.getText().substring(0,proPrice.getText().length()-1));
                }
            }
        });


        ExpiryDate.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);


            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        try {
            Statement statement = connection.createStatement();   Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT user.owner_id FROM user WHERE user.user_id ='"+ LoginController.All_UserID+"'");
            if(resultSet1.next()) {
                int get_owner_id = resultSet1.getInt("owner_id");
                if (get_owner_id == 0)
                    get_owner_id = LoginController.All_UserID;

                ResultSet resultSet = statement.executeQuery("SELECT `cat_name` FROM `category` WHERE owner_id='" + get_owner_id + "' ");
                Category.getItems().clear();
                while (resultSet.next()) {
                    Category.getItems().add(resultSet.getString("cat_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void Submit(Event event){
        if(proName.getText().length() >= 2 && proPrice.getText().length() > 0 && sellPrice.getText().length() > 0){
            isSubmit = true;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
