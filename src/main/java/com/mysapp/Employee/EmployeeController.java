package com.mysapp.Employee;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    TableColumn<?,?> serial;
    @FXML
    TableColumn<?,?> empid;
    @FXML
    TableColumn<?,?> salary;
    @FXML
    TableColumn<?,?> name;
    @FXML
    TableColumn<?,?> joindate;
    @FXML
    TableColumn<EmployeeTableData, ImageView> photo;

    @FXML
    private TableColumn<EmployeeTableData, Label> edit,delete;

    @FXML
    TableView<EmployeeTableData> employeetable;

    @FXML
    JFXTextField keyword;

    ObservableList<EmployeeTableData> Data;


    Connection connection;

    public EmployeeController(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        empid.setCellValueFactory(new PropertyValueFactory<>("empid"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        joindate.setCellValueFactory(new PropertyValueFactory<>("joindate"));
        edit.setCellFactory(new Callback<TableColumn<EmployeeTableData, Label>, TableCell<EmployeeTableData, Label>>() {
            @Override
            public TableCell<EmployeeTableData, Label> call(TableColumn<EmployeeTableData, Label> param) {
                Label imgs = new Label();
                imgs.getStyleClass().add("FAtableIcon");
                TableCell<EmployeeTableData, Label> cell = new TableCell<EmployeeTableData, Label>(){
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



        delete.setCellFactory(new Callback<TableColumn<EmployeeTableData, Label>, TableCell<EmployeeTableData, Label>>() {
            @Override
            public TableCell<EmployeeTableData, Label> call(TableColumn<EmployeeTableData, Label> param) {
                Label imgs = new Label();
                imgs.getStyleClass().add("FAtableIcon");
                imgs.getStyleClass().add("Delete");
                TableCell<EmployeeTableData, Label> cell = new TableCell<EmployeeTableData, Label>(){
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
        photo.setCellValueFactory(
                new PropertyValueFactory<EmployeeTableData,ImageView>("image"));
        LoadTable();

    }

    @FXML
    private void LoadTable(){
        Data = FXCollections.observableArrayList();
        Image img = new Image("/image/user.png");
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        Data.add(new EmployeeTableData("ahosan","12/12/12",1,1,1212,imageView));
        Data.add(new EmployeeTableData("ahosan","12/12/12",1,1,1212,imageView));
        Data.add(new EmployeeTableData("ahosan","12/12/12",1,1,1212,imageView));
        Data.add(new EmployeeTableData("ahosan","12/12/12",1,1,1212,imageView));

        employeetable.setItems(Data);
    }


    @FXML
    private void update(){

    }



    @FXML
    private void AddEmployee(){

    }
}
