package com.mysapp.SellSheet;

import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SellSheetController implements Initializable{
    @FXML
    private JFXTextField keyword;

    @FXML
    private TableView<SellSheetTableData> sellTable;

    @FXML
    private TableColumn<?, ?> serial,name, proid,sellunit,Amount,profit;

    Connection connection;

    ObservableList<SellSheetTableData> data;

    public SellSheetController(Connection connection) {
        this.connection = connection;
    }

    @FXML
    void Print() {

    }

    @FXML
    void LoadTable() {
        data = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT T1.*,(Sellprice-NetPrice) as Profit FROM (SELECT product.pro_id,product.cat_id,product.pro_name,SUM(buy_product_list.sell_price*buy_product_list.quantity) As Sellprice,SUM(product.net_price*buy_product_list.quantity) as NetPrice,SUM(buy_product_list.quantity) as TotalUnit FROM product JOIN `buy_product_list` ON product.pro_id = buy_product_list.pro_id GROUP BY pro_id) as T1  WHERE pro_name LIKE  '%"+keyword.getText()+"%' and cat_id IN (SELECT cat_id FROM `category` WHERE owner_id = '"+ LoginController.All_OwnerID+"') ORDER BY Profit DESC");
            while (rs.next()){
                data.add(new SellSheetTableData(rs.getDouble("Sellprice"),rs.getDouble("Profit"),rs.getString("Pro_name"),data.size()+1,rs.getInt("pro_id"),rs.getInt("TotalUnit")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sellTable.getItems().clear();
        sellTable.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        proid.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        sellunit.setCellValueFactory(new PropertyValueFactory<>("unitpro"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        profit.setCellValueFactory(new PropertyValueFactory<>("TotalProfit"));
        LoadTable();
    }
}
