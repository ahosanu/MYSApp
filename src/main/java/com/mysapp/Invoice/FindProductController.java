package com.mysapp.Invoice;

import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import com.mysapp.Product.AddProduct;
import com.mysapp.Product.ProductTableDate;
import com.mysapp.Product.UpdateProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FindProductController implements Initializable{
    @FXML
    private JFXTextField keyword;

    @FXML
    TextField Unit;

    @FXML
    public TableView<ProductTableDate> producttable;

    @FXML
    private TableColumn<?, ?> serial;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> proid;

    @FXML
    private TableColumn<?, ?> sellprice;

    @FXML
    private TableColumn<?, ?> lastprice;

    @FXML
    private TableColumn<?, ?> expdate;

    @FXML
    private TableColumn<?, ?> Discount;

    boolean isClick = false;

    private ObservableList<ProductTableDate> Data = FXCollections.observableArrayList();


    Connection connection;
    int id;

    public FindProductController(Connection connection) {
        this.connection = connection;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        proid.setCellValueFactory(new PropertyValueFactory<>("proid"));
        sellprice.setCellValueFactory(new PropertyValueFactory<>("sellprice"));
        lastprice.setCellValueFactory(new PropertyValueFactory<>("lastprice"));
        expdate.setCellValueFactory(new PropertyValueFactory<>("expdate"));
        Discount.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        LoadTable();
    }

    @FXML
    private void LoadTable() {

        producttable.getItems().clear();
        Data = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT pro_id,pro_name,in_date,exp_date,sell_price,net_price,product.discount FROM `product` JOIN `category` ON product.cat_id = category.cat_id WHERE owner_id='"+LoginController.All_OwnerID+"' and pro_name LIKE '%" + keyword.getText() + "%'");
            int i = 1;
            while (resultSet.next()) {

                Data.add(new ProductTableDate(i, resultSet.getString("pro_name"), resultSet.getInt("pro_id"), resultSet.getDouble("sell_price"), resultSet.getDouble("net_price"), resultSet.getString("exp_date"), resultSet.getString("discount"), "editinfo"));
                i++;
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }



        producttable.setItems(Data);
        producttable.refresh();
    }


    @FXML
    private void Add(Event event){
        if(producttable.getSelectionModel() != null && Integer.parseInt(Unit.getText()) > 0) {
            isClick = true;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
