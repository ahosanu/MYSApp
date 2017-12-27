package com.mysapp.Member;

import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MemberController implements Initializable{
    @FXML
    private JFXTextField keyword;

    @FXML
    private TableView<MemberTableData> memberTable;

    @FXML
    private TableColumn<?, ?> serial;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> memberID;

    @FXML
    private TableColumn<?, ?> memberPoint;

    @FXML
    private JFXTextField Member_name;

    @FXML
    private JFXTextField member_init_point;


    ObservableList<MemberTableData> data;
    Connection connection;

    public MemberController(Connection connection) {
        this.connection = connection;
    }

    @FXML
    void LoadTable() {
        data = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `member` WHERE member_id LIKE '%"+keyword.getText()+"%' and owner_id = '"+LoginController.All_OwnerID+"'");
            while (rs.next()){
                data.add(new MemberTableData(rs.getInt("point"),rs.getInt("member_id"),data.size()+1,rs.getString("member_name")));
            }
            rs.close();
            statement.close();
            memberTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void SubmitBtn() throws SQLException {
        if(!Member_name.getText().isEmpty() && Integer.parseInt(member_init_point.getText()) > 0) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `member`(`owner_id`, `member_name`, `point`) VALUES ('"+LoginController.All_OwnerID+"','"+Member_name.getText()+"','"+member_init_point.getText()+"')");
            Notifications notifications = Notifications.create()
                    .title("Successful")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notifications.text("Successfully Added.");
            notifications.showConfirm();
            LoadTable();
        }else {
            Notifications notifications = Notifications.create()
                    .title("Error!")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notifications.text("Wrong Input.");
            notifications.showWarning();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        memberPoint.setCellValueFactory(new PropertyValueFactory<>("memberPoint"));
        memberID.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        LoadTable();
    }
}
