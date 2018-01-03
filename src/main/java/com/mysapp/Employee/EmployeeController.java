package com.mysapp.Employee;

import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
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
    TableColumn<?,?> mobile_number;
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
        mobile_number.setCellValueFactory(new PropertyValueFactory<>("mobile_number"));
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
        try {
            Statement statement = connection.createStatement();
            ResultSet rs= statement.executeQuery("SELECT user_id,full_name,phone,salary,sex,photo FROM `user` WHERE owner_id='"+LoginController.All_UserID+"' and full_name LIKE '%"+keyword.getText()+"%'");
            int i = 1;
            while (rs.next()) {
                Image image = null;
                if(rs.getBoolean("photo")) {
                    BufferedImage read = null;
                    try {
                        read = ImageIO.read(new File("./User Image/"+rs.getInt("user_id")+"_User_Image.png"));
                        image = SwingFXUtils.toFXImage(read, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                        image = new Image("/image/noimg.png");
                    }

                 }else{
                        image = new Image("/image/noimg.png");

                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(40);
                imageView.setFitHeight(35);

                Data.add(new EmployeeTableData(rs.getString("full_name"), rs.getString("phone"), i++, rs.getInt("user_id"), rs.getInt("salary"), imageView));
            }
            employeetable.getItems().clear();
            employeetable.setItems(Data);
            employeetable.refresh();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void update() throws IOException {
        if(employeetable.getSelectionModel().isEmpty()){
            return;
        }
        int cell = employeetable.getSelectionModel().getSelectedCells().get(0).getColumn();
        int row  = employeetable.getSelectionModel().getSelectedCells().get(0).getRow();
        if(cell == 6 && row < Data.size()) {
            int id = employeetable.getSelectionModel().getSelectedItem().getEmpid();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Employee/UpdateEmployee.fxml"));
            UpdateEmployee updateEmployee = new UpdateEmployee(connection, id);
            loader.setController(updateEmployee);
            Stage stage = new Stage();
            Parent parent = loader.load();
            stage.setTitle("Update Employee");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event1 ->{
                DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    Statement statement = connection.createStatement();
                    int i = statement.executeUpdate("UPDATE `user` SET `type`='"+updateEmployee.type.getSelectionModel().getSelectedItem()+"',`email`='"+updateEmployee.Email_id.getText()+"',`full_name`='"+updateEmployee.Emp_name.getText()+"',`address`='"+updateEmployee.Address.getText()+"',`permanent_address`='"+updateEmployee.Per_Address.getText()+"',`salary`='"+updateEmployee.salary.getText()+"',`phone`='"+updateEmployee.mobile.getText()+"',`photo`='"+updateEmployee.isPhoto+"',`dateofbirth`='"+updateEmployee.BirthDate.getValue().format(dateformate)+"',`sex`='"+updateEmployee.sex.getSelectionModel().getSelectedItem()+"' WHERE user_id='"+updateEmployee.id+"'");
                    LoadTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
        }else if(cell == 7 && row < Data.size()){
            Dialog<ButtonType> canDelete = new Dialog();
            canDelete.setTitle("Delete Warning!");
            canDelete.setContentText("Are you delete This \""+employeetable.getSelectionModel().getSelectedItem().getName()+"\"?");
            canDelete.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
            canDelete.showAndWait().filter(result -> result == ButtonType.YES).ifPresent(result -> {
                try {
                    Statement DeleteSatement = connection.createStatement();
                    int ok = DeleteSatement.executeUpdate("DELETE FROM `user` WHERE user_id ='"+employeetable.getSelectionModel().getSelectedItem().getEmpid()+"'");
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
    }



    @FXML
    private void AddEmployee() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Employee/addEmployee.fxml"));
        AddEmployee addEmployee = new AddEmployee(connection);
        loader.setController(addEmployee);
        Stage stage = new Stage();
        Parent parent = loader.load();
        stage.setTitle("Add New Employee");
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setOnHidden(event1 -> {
            if(addEmployee.isSubmit) {
                DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.println("Return Result");
                try {
                    Statement statement = connection.createStatement();
                    int i = statement.executeUpdate("INSERT INTO `user`(`user_name`, `password`, `type`, `email`, `full_name`, `address`, `permanent_address`, `salary`, `phone`, `photo`, `dateofbirth`, `owner_id`, `sex`) VALUES " +
                            "('"+addEmployee.user_name.getText()+"','12345','"+addEmployee.type.getSelectionModel().getSelectedItem()+"','"+addEmployee.Email_id.getText()+"'," +
                            "'"+addEmployee.Emp_name.getText()+"','"+addEmployee.Address.getText()+"','"+addEmployee.Per_Address.getText()+"','"+addEmployee.salary.getText()+"','"+addEmployee.mobile.getText()+"','"+addEmployee.isPhoto+"','"+addEmployee.BirthDate.getValue().format(dateformate)+"'," +
                            "'"+ LoginController.All_UserID+"','"+addEmployee.sex.getSelectionModel().getSelectedItem()+"')");
                   LoadTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();
    }
}
