package com.mysapp.Invoice;

import com.mysapp.LoginController;
import com.mysapp.Product.ProductTableDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowInvoice implements Initializable{
    Connection connection;
    int id;
    @FXML
    TextArea Address;
    @FXML
    TextField Unit,SubTotal,vat,Discount, M_Discount,Net_amount,Pay, Change,productID,Membersl,BuyerName,Mobile,Email;
    @FXML
    TableColumn<?, ?> serila,Name, ID,Price,Total,Units;
    @FXML
    Text seller_name,TopNetAmount,TodayDate;
    @FXML
    private TableView<InvoiceTableData> table = new TableView<InvoiceTableData>();
    private ObservableList<InvoiceTableData> data = FXCollections.observableArrayList();
    public ShowInvoice(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ID.setCellValueFactory(
                new PropertyValueFactory<>("ID")
        );
        Name.setCellValueFactory(
                new PropertyValueFactory<>("Name")
        );
        serila.setCellValueFactory(
                new PropertyValueFactory<>("Serial")
        );
        Price.setCellValueFactory(
                new PropertyValueFactory<>("Price")
        );
        Units.setCellValueFactory(
                new PropertyValueFactory<>("Unit")
        );
        Total.setCellValueFactory(
                new PropertyValueFactory<>("Total")
        );

        double subTotal = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT full_name FROM `user` WHERE user_id = '"+ LoginController.All_UserID+"'");
            if(rs.next())
                seller_name.setText(rs.getString("full_name"));
            rs = statement.executeQuery("SELECT product.pro_name,buy_product_list.* FROM product JOIN `buy_product_list` ON product.pro_id = buy_product_list.pro_id WHERE buy_product_list.buyer_id = '"+id+"'");
            while (rs.next()){
                double db_sell_price = rs.getDouble("sell_price");
                int db_unit = rs.getInt("quantity");
                double db_total = db_sell_price*db_unit;
                subTotal += db_total;
                data.add(new InvoiceTableData(rs.getString("pro_name"),db_sell_price,db_unit,data.size()+1,rs.getInt("pro_id"),db_total,rs.getDouble("discount")));
            }
            table.setItems(data);
            rs = statement.executeQuery("SELECT * FROM `buyer` WHERE buyer_id='"+id+"'");
            if(rs.next()){
                SubTotal.setText(subTotal + "");
                double vat_taka = Double.parseDouble(String.format("%.2f", subTotal * .02));
                vat.setText(vat_taka + "");

                double netamount = subTotal + vat_taka - Double.parseDouble(M_Discount.getText()) - Double.parseDouble(Discount.getText());
                Net_amount.setText(netamount + "");
                TopNetAmount.setText(netamount + "");
                Pay.setText(rs.getString("payed_amount"));
                Change.setText("-" + netamount);

                Membersl.setText(rs.getString("Member_id"));
                BuyerName.setText(rs.getString("buyer_name"));
                Email.setText(rs.getString("email"));
                Mobile.setText(rs.getString("buyer_mobile"));
                Address.setText(rs.getString("address"));
                TodayDate.setText(rs.getString("nowdate"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        TodayDate.setText(dateFormat.format(date));
    }

    @FXML
    private void SavePrint(){

    }
}
