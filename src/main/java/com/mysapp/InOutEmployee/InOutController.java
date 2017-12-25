package com.mysapp.InOutEmployee;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InOutController implements Initializable{
    Connection connection;
    @FXML
    TableColumn<?,?> Seriel,EmployeeID, Name,date,intime,outtime;
    @FXML
    JFXTextField keyword;

    @FXML
    JFXDatePicker getdate;

    @FXML
    TableView<InOutTableData> InOutTable;

    ObservableList<InOutTableData> data;


    public InOutController(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Seriel.setCellValueFactory(new PropertyValueFactory<>("Seriel"));
        EmployeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        intime.setCellValueFactory(new PropertyValueFactory<>("intime"));
        outtime.setCellValueFactory(new PropertyValueFactory<>("outtime"));

        LoadTable();
    }

    @FXML
    private void LoadTable(){
        data = FXCollections.observableArrayList();
        DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String Find_date = "";
        try{
            Find_date = getdate.getValue().format(dateformate);
        }catch (Exception e){
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery("SELECT user.owner_id FROM user WHERE user.user_id ='"+ LoginController.All_UserID+"'");
            if(resultSet1.next()) {
                int get_owner_id = resultSet1.getInt("owner_id");
                if (get_owner_id == 0)
                    get_owner_id = LoginController.All_UserID;

                resultSet1 = statement.executeQuery("SELECT user.user_id,user.full_name,in_out.nowdate,in_out.in_time,in_out.out_time FROM user JOIN in_out ON user.user_id = in_out.user_id WHERE " +
                        "user.user_id LIKE '"+keyword.getText()+"%' AND in_out.nowdate LIKE '%"+Find_date+"%' AND user.owner_id ='"+get_owner_id+"'");

                while (resultSet1.next()){
                    data.add(new InOutTableData(data.size()+1,resultSet1.getInt("user_id"),resultSet1.getString("full_name"),resultSet1.getString("nowdate"),resultSet1.getString("in_time"),resultSet1.getString("out_time")));
                }
                InOutTable.getItems().clear();
                resultSet1.close();
                statement.close();
                InOutTable.setItems(data);
                InOutTable.refresh();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
