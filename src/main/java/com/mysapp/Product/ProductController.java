package com.mysapp.Product;

import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

public class ProductController implements Initializable{
    @FXML
    private JFXTextField keyword;

    @FXML
    private TableView<ProductTableDate> producttable;

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

    @FXML
    private TableColumn<ProductTableDate, Label> edit,delete;

    private ObservableList<ProductTableDate> Data = FXCollections.observableArrayList();


    Connection connection;
    int id;

    public ProductController(Connection connection) {
        this.connection = connection;
    }

    @FXML
    void AddProduct(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product/addproduct.fxml"));
            AddProduct addPro = new AddProduct(connection);
            loader.setController(addPro);
            Stage stage = new Stage();
            Parent parent = loader.load();
            stage.setTitle("Add New Product");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnHidden(event1 -> {
                if(addPro.isSubmit) {
                    DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    //Data.add(new ProductTableDate(Data.size(),,11212,Double.parseDouble(addPro.sellPrice.getText()),Double.parseDouble(addPro.proPrice.getText()),addPro.ExpiryDate.getValue().format(dateformate),addPro.discount.getText(),"ok",addPro.Description.getText()));
                    System.out.println("Return Result");
                    try {

                        Statement statement = connection.createStatement();
                        int cat_id = 1;
                        ResultSet resultSet = statement.executeQuery("SELECT cat_id FROM `category` WHERE cat_name = '" + addPro.Category.getSelectionModel().getSelectedItem() + "'");
                        if (resultSet.next())
                            cat_id = resultSet.getInt("cat_id");
                        statement.close();
                        resultSet.close();


                        statement = connection.createStatement();
                        int i = statement.executeUpdate("INSERT INTO `product`(`pro_name`, `pro_description`, `in_date`, `exp_date`, `sell_price`, `net_price`, `discount`, `cat_id`) VALUES" +
                                " ('" + addPro.proName.getText() + "','" + addPro.Description.getText() + "','" + addPro.indate.getValue().format(dateformate) + "','" + addPro.ExpiryDate.getValue().format(dateformate) + "','" + addPro.sellPrice.getText() + "','" + addPro.proPrice.getText() + "','" + addPro.discount.getText() + "','" + cat_id + "')");
                        LoadTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            });
           // stage.showAndWait();
        stage.show();
    }

    @FXML
    private void update() throws IOException {
        if(producttable.getSelectionModel().isEmpty()){
            return;
        }
        int cell = producttable.getSelectionModel().getSelectedCells().get(0).getColumn();
        int row  = producttable.getSelectionModel().getSelectedCells().get(0).getRow();
        if(cell == 7 && row < Data.size()) {

            id = producttable.getSelectionModel().getSelectedItem().getProid();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product/Updateproduct.fxml"));
            UpdateProduct updatePro = new UpdateProduct(connection, id);
            loader.setController(updatePro);
            Stage stage = new Stage();
            Parent parent = loader.load();
            stage.setTitle("Update Product");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnHidden(event1 -> {
                if (updatePro.isSubmit) {
                    DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    //Data.add(new ProductTableDate(Data.size(),,11212,Double.parseDouble(addPro.sellPrice.getText()),Double.parseDouble(addPro.proPrice.getText()),addPro.ExpiryDate.getValue().format(dateformate),addPro.discount.getText(),"ok",addPro.Description.getText()));
                    System.out.println("Return Result");
                    try {

                        Statement statement = connection.createStatement();
                        int cat_id = 1;
                        ResultSet resultSet = statement.executeQuery("SELECT cat_id FROM `category` WHERE cat_name = '" + updatePro.Category.getSelectionModel().getSelectedItem() + "'");
                        if (resultSet.next())
                            cat_id = resultSet.getInt("cat_id");
                        statement.close();
                        resultSet.close();

                        statement = connection.createStatement();
                        statement.executeUpdate("UPDATE `product` SET `pro_name`='" + updatePro.proName.getText() + "',`pro_description`='" + updatePro.Description.getText() + "',`in_date`='" + updatePro.indate.getValue().format(dateformate) + "',`exp_date`='" + updatePro.ExpiryDate.getValue().format(dateformate) + "',`sell_price`='" + updatePro.sellPrice.getText() + "',`net_price`='" + updatePro.proPrice.getText() + "',`discount`='" + updatePro.discount.getText() + "',`cat_id`='" + cat_id + "' WHERE `pro_id`='" + id + "'");

                        LoadTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            // stage.showAndWait();
            stage.show();
        }else if(cell == 8){
            Dialog<ButtonType> canDelete = new Dialog();
            canDelete.setTitle("Delete Warning!");
            canDelete.setContentText("Are you delete This \""+producttable.getSelectionModel().getSelectedItem().getName()+"\"?");
            canDelete.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
            canDelete.showAndWait().filter(result -> result == ButtonType.YES).ifPresent(result -> {
                System.out.println("ok"+producttable.getSelectionModel().getSelectedItem().getProid());
                try {
                    Statement DeleteSatement = connection.createStatement();
                    int ok = DeleteSatement.executeUpdate("DELETE FROM `product` WHERE pro_id ='"+producttable.getSelectionModel().getSelectedItem().getProid()+"'");
                    if(ok == 1){
                        Notifications notifications = Notifications.create()
                                .title("Successful")
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT)
                                .onAction(e->{
                                    System.out.println("click");
                                });
                        notifications.text("Successfully Delete.");
                        notifications.showConfirm();
                        LoadTable();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        producttable.getSelectionModel().clearSelection();
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
        edit.setCellFactory(new Callback<TableColumn<ProductTableDate, Label>, TableCell<ProductTableDate, Label>>() {
            @Override
            public TableCell<ProductTableDate, Label> call(TableColumn<ProductTableDate, Label> param) {
                Label imgs = new Label();
                imgs.getStyleClass().add("FAtableIcon");
                TableCell<ProductTableDate, Label> cell = new TableCell<ProductTableDate, Label>(){
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty)
                            imgs.setText("\uF013");
                    }
                };
                cell.setGraphic(imgs);
                return cell;
            }

        });
        delete.setCellFactory(new Callback<TableColumn<ProductTableDate, Label>, TableCell<ProductTableDate, Label>>() {
            @Override
            public TableCell<ProductTableDate, Label> call(TableColumn<ProductTableDate, Label> param) {
                Label imgs = new Label();
                imgs.getStyleClass().add("FAtableIcon");
                imgs.getStyleClass().add("Delete");
                TableCell<ProductTableDate, Label> cell = new TableCell<ProductTableDate, Label>(){
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty)
                            imgs.setText("\uF00d");
                    }
                };
                cell.setGraphic(imgs);
                return cell;
            }

        });

       LoadTable();
    }

    @FXML
    private void LoadTable() {

        producttable.getItems().clear();
        Data = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT user.owner_id FROM user WHERE user.user_id ='"+ LoginController.All_UserID+"'");
            if(resultSet1.next()) {
                int get_owner_id = resultSet1.getInt("owner_id");
                if (get_owner_id == 0)
                    get_owner_id = LoginController.All_UserID;

                ResultSet resultSet = statement.executeQuery("SELECT pro_id,pro_name,in_date,exp_date,sell_price,net_price,product.discount FROM `product` JOIN `category` ON product.cat_id = category.cat_id WHERE owner_id='"+get_owner_id+"' and pro_name LIKE '%" + keyword.getText() + "%'");
                int i = 1;
                while (resultSet.next()) {

                    Data.add(new ProductTableDate(i, resultSet.getString("pro_name"), resultSet.getInt("pro_id"), resultSet.getDouble("sell_price"), resultSet.getDouble("net_price"), resultSet.getString("exp_date"), resultSet.getString("discount"), "editinfo"));
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        producttable.setItems(Data);
        producttable.refresh();
    }
}
