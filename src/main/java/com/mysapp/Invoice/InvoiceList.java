package com.mysapp.Invoice;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.Employee.UpdateEmployee;
import com.mysapp.LoginController;
import com.mysapp.Product.ProductTableDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class InvoiceList implements Initializable{
    @FXML
    JFXDatePicker getdate;
    @FXML
    JFXTextField keyword;
    @FXML
    TableColumn<?,?> serial,name,invoiceid,totalprice,date;
    @FXML
    TableColumn<InvoiceListTableDate,Label> print,view;
    @FXML
    TableView<InvoiceListTableDate> InvoiceTable;

    Connection connection;

    ObservableList<InvoiceListTableDate> data;

    public InvoiceList(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        invoiceid.setCellValueFactory(new PropertyValueFactory<>("invoiceid"));
        totalprice.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        getdate.setConverter(new StringConverter<LocalDate>() {
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
        print.setCellFactory(new Callback<TableColumn<InvoiceListTableDate, Label>, TableCell<InvoiceListTableDate, Label>>() {
            @Override
            public TableCell<InvoiceListTableDate, Label> call(TableColumn<InvoiceListTableDate, Label> param) {
                Label imgs = new Label();
                imgs.getStyleClass().add("FAtableIcon");
                TableCell<InvoiceListTableDate, Label> cell = new TableCell<InvoiceListTableDate, Label>(){
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty)
                            imgs.setText("\uf02f");
                    }
                };
                cell.setGraphic(imgs);
                return cell;
            }

        });
        view.setCellFactory(new Callback<TableColumn<InvoiceListTableDate, Label>, TableCell<InvoiceListTableDate, Label>>() {
            @Override
            public TableCell<InvoiceListTableDate, Label> call(TableColumn<InvoiceListTableDate, Label> param) {
                Label imgs = new Label();
                imgs.getStyleClass().add("FAtableIcon");
                TableCell<InvoiceListTableDate, Label> cell = new TableCell<InvoiceListTableDate, Label>(){
                    @Override
                    protected void updateItem(Label item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty)
                            imgs.setText("\uf24d");
                    }
                };
                cell.setGraphic(imgs);
                return cell;
            }

        });
        LoadTable();

    }

    @FXML
    private void LoadTable()  {
        data = FXCollections.observableArrayList();
        DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String Find_date = "";
        try{
            Find_date = getdate.getValue().format(dateformate);
        }catch (Exception e){
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery("SELECT user.owner_id FROM user WHERE user.user_id ='"+ LoginController.All_UserID+"'");
            if(resultSet1.next()) {
                int get_owner_id = resultSet1.getInt("owner_id");
                if (get_owner_id == 0)
                    get_owner_id = LoginController.All_UserID;


                resultSet1 = statement.executeQuery("SELECT * FROM `buyer` WHERE nowdate LIKE '%"+Find_date+"%' and buyer_id LIKE '%"+keyword.getText()+"%' and buyer.user_id IN (SELECT user.owner_id FROM user WHERE user_id='"+LoginController.All_UserID+"')");
                while (resultSet1.next()) {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse( resultSet1.getString("nowdate"));
                    data.add(new InvoiceListTableDate(data.size()+1, resultSet1.getString("buyer_name"), "MYS-"+resultSet1.getInt("buyer_id")+"-"+((date.getTime()/10000)+resultSet1.getInt("buyer_id")), resultSet1.getDouble("payed_amount"), resultSet1.getString("nowdate"), resultSet1.getInt("buyer_id")));

                }
                InvoiceTable.setItems(data);
                InvoiceTable.refresh();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    @FXML
    private void TableClick() throws IOException {
        if(InvoiceTable.getSelectionModel().isEmpty()){
            return;
        }
        int cell = InvoiceTable.getSelectionModel().getSelectedCells().get(0).getColumn();
        int row  = InvoiceTable.getSelectionModel().getSelectedCells().get(0).getRow();
        System.out.println(cell);
        if(cell == 5 && row < data.size()) {
            //Show old invoice
            int id = InvoiceTable.getSelectionModel().getSelectedItem().getByer_id();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Invoice/ShowInvoice.fxml"));
            ShowInvoice showInvoice = new ShowInvoice(connection, id);
            loader.setController(showInvoice);
            Stage stage = new Stage();
            Parent parent = loader.load();
            stage.setTitle("Show invoice");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }else if(cell == 6 && row < data.size()) {

        }
    }

}
