package com.mysapp.Invoice;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InvoiceList implements Initializable{
    @FXML
    JFXDatePicker getdate;
    @FXML
    JFXTextField keyword;
    @FXML
    TableColumn<?,?> serial,name,invoiceid,totalprice,date;
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
        LoadTable();

    }

    @FXML
    private void LoadTable(){
        data = FXCollections.observableArrayList();
        
        data.add(new InvoiceListTableDate(1,"sdsd","xy-1212",122,"12/12/14"));
        InvoiceTable.setItems(data);
        InvoiceTable.refresh();
    }
    @FXML
    private void update(){

    }
}
