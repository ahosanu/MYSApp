package com.mysapp.Product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.Category.CategorySelection;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateProduct implements Initializable{
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

    int pro_id;

    public UpdateProduct(Connection connection,int pro_id) {
        this.connection = connection;
        this.pro_id = pro_id;
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

        ArrayList<CategorySelection> categorySelections = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `cat_id`,`cat_name` FROM `category`");
            Category.getItems().clear();
            while(resultSet.next()){
                categorySelections.add(new CategorySelection(resultSet.getString("cat_name"),resultSet.getInt("cat_id")));
                Category.getItems().add(resultSet.getString("cat_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `product` WHERE pro_id = '"+pro_id+"'");
            if(resultSet.next()){
                proName.setText(resultSet.getString("pro_name"));
                proPrice.setText(resultSet.getString("net_price"));
                sellPrice.setText(resultSet.getString("sell_price"));
                discount.setText(resultSet.getString("discount").isEmpty() ? "0" : resultSet.getString("discount"));
                Description.setText(resultSet.getString("pro_description"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate expdate = LocalDate.parse(resultSet.getString("exp_date"),formatter);
                ExpiryDate.setValue(expdate);
                LocalDate inpdate = LocalDate.parse(resultSet.getString("in_date"),formatter);
                indate.setValue(inpdate);
                for (CategorySelection cat: categorySelections) {
                    if(cat.getId() == resultSet.getInt("cat_id")){
                        Category.getSelectionModel().select(cat.getName());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void Submit(Event event){
        if(proName.getText().length() >= 2 && proPrice.getText().length() > 0 && Double.parseDouble(sellPrice.getText()) >= 0 && Double.parseDouble(proPrice.getText()) >=0 ){
            isSubmit = true;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
