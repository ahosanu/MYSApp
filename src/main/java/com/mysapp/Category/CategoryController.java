package com.mysapp.Category;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import com.mysapp.Product.ProductTableDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    Connection connection;

    @FXML
    TableColumn<?,?> serial,categoryid,NumberOfProduct,name,TotalAmount,Discount;
    @FXML
    TableView<CategoryTableData> categoeyTable;
    @FXML
    Text ViewTotalProduct,ViewTotalAmount;

    @FXML
    JFXTextField keyword,Category_Discount,Category_name;

    @FXML
    JFXButton cat_submit_btn,cat_delete;

    private ObservableList<CategoryTableData> Data;

    public CategoryController(Connection connection) {
        this.connection = connection;
    }

    @FXML
    private void LoadTable() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM category LEFT JOIN (SELECT cat_id as bycatid, SUM(net_price) as totalamount,COUNT(pro_name) as totalproduct FROM `product` GROUP BY cat_id) as productall  ON category.cat_id = productall.bycatid WHERE owner_id='"+LoginController.All_OwnerID+"' and cat_name LIKE '%"+keyword.getText()+"%'");
            Data = FXCollections.observableArrayList();

            while (rs.next()) {
                Data.add(new CategoryTableData(Data.size() + 1, rs.getString("cat_name"), rs.getInt("cat_id"), rs.getInt("totalproduct"), rs.getDouble("totalamount"), rs.getDouble("Discount")));
            }
            categoeyTable.getItems().clear();
            categoeyTable.setItems(Data);
            categoeyTable.refresh();
            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void MousOnClick(){
        if(categoeyTable.getSelectionModel().isEmpty())
            return;
        Category_name.setText(String.valueOf(categoeyTable.getSelectionModel().getSelectedItem().getName()));
        Category_Discount.setText(String.valueOf(categoeyTable.getSelectionModel().getSelectedItem().getDiscount()));
        ViewTotalProduct.setText(String.valueOf(categoeyTable.getSelectionModel().getSelectedItem().getNumberOfProduct()));
        ViewTotalAmount.setText(String.valueOf(categoeyTable.getSelectionModel().getSelectedItem().getTotalAmount()));
        cat_submit_btn.setText("Update");
        cat_delete.setVisible(true);

    }

    @FXML
    private void SubmitBtn() throws SQLException {
        Statement statement = connection.createStatement();

        if(categoeyTable.getSelectionModel().isEmpty()){
            int ok=0;
            if(!Category_name.getText().isEmpty()) {
                Statement statement1 = connection.createStatement();
                try {
                    ok = statement.executeUpdate("INSERT INTO `category`(`cat_name`, `owner_id`, `Discount`) VALUES ('" + Category_name.getText() + "', '" + LoginController.All_OwnerID + "' ,'" + Double.parseDouble(Category_Discount.getText()) + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                    ok = 0;
                }
                if(ok == 1){
                    Notifications notifications = Notifications.create()
                            .title("Successful")
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT)
                            .onAction(e->{
                                System.out.println("click");
                            }).text("Successfully Added.");
                    notifications.showConfirm();
                    LoadTable();
                    categoeyTable.getSelectionModel().clearSelection();
                    Category_name.setText("");
                    Category_Discount.setText("0");
                    ViewTotalAmount.setText("0.00");
                    ViewTotalProduct.setText("0");
                }else{
                    Notifications notifications = Notifications.create()
                            .title("Error")
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT)
                            .onAction(e->{
                                System.out.println("click");
                            }).text("Adding Failed.");
                    notifications.showError();
                }
            }else{
                Notifications notifications = Notifications.create()
                        .title("Error")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .onAction(e->{
                            System.out.println("click");
                        }).text("Empty Category Name.");
                notifications.showError();
            }

        }else {
            int ok;
            try {
                ok = statement.executeUpdate("UPDATE `category` SET `cat_name`='" + Category_name.getText() + "',`Discount`='" + Double.parseDouble(Category_Discount.getText()) + "' WHERE cat_id='" + categoeyTable.getSelectionModel().getSelectedItem().getCategoryid() + "'");
            }catch (Exception e){
                ok = 0;
            }
            if(ok == 1){
                Notifications notifications = Notifications.create()
                        .title("Successful")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .onAction(e->{
                            System.out.println("click");
                        }).text("Successfully Update.");
                notifications.showConfirm();
                LoadTable();
                categoeyTable.getSelectionModel().clearSelection();
                Category_name.setText("");
                Category_Discount.setText("0");
                ViewTotalAmount.setText("0.00");
                ViewTotalProduct.setText("0");
                cat_delete.setVisible(false);
                cat_submit_btn.setText("Add Category");
            }else{
                Notifications notifications = Notifications.create()
                        .title("Error")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .onAction(e->{
                            System.out.println("click");
                        }).text("Updating Failed.");
                notifications.showError();
            }
        }
        statement.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        NumberOfProduct.setCellValueFactory(new PropertyValueFactory<>("NumberOfProduct"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TotalAmount.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
        Discount.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        cat_delete.setVisible(false);
        Category_Discount.setText("0");
        LoadTable();
    }
    @FXML
    private void Delete_Cat() throws SQLException {
        Statement statement = connection.createStatement();
        int ok = statement.executeUpdate("DELETE FROM `category` WHERE cat_id='"+categoeyTable.getSelectionModel().getSelectedItem().getCategoryid()+"'");
        if(ok == 1){
            Notifications notifications = Notifications.create()
                    .title("Successful")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .onAction(e->{
                        System.out.println("click");
                    }).text("Successfully Delete.");
            notifications.showConfirm();
            LoadTable();
            categoeyTable.getSelectionModel().clearSelection();
            Category_name.setText("");
            Category_Discount.setText("0");
            ViewTotalAmount.setText("0.00");
            ViewTotalProduct.setText("0");
            cat_delete.setVisible(false);
            cat_submit_btn.setText("Add Category");
        }else{
            Notifications notifications = Notifications.create()
                    .title("Error")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .onAction(e->{
                        System.out.println("click");
                    }).text("Deleting Failed.");
            notifications.showError();
        }
    }
}
