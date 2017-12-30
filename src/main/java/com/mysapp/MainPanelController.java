package com.mysapp;

import com.mysapp.Category.CategoryController;
import com.mysapp.Dashboard.DashboardController;
import com.mysapp.Employee.AddEmployee;
import com.mysapp.Employee.EmployeeController;
import com.mysapp.InOutEmployee.InOutController;
import com.mysapp.Invoice.CreateInvoice;
import com.mysapp.Invoice.InvoiceList;
import com.mysapp.Member.MemberController;
import com.mysapp.Product.ProductController;
import com.mysapp.SellSheet.SellSheetController;
import com.mysapp.Setting.Settings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable{
    @FXML
    private Text DateText,TimeText,subWindowTitle,AmPmText,UserFullName;

    Task<Void> task = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
            try {
                while (true){
                    Statement statement = connection.createStatement();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dates = new Date();
                    ResultSet rs = statement.executeQuery("SELECT product.pro_name,product.exp_date,product.pro_id FROM category JOIN `product` ON category.cat_id = product.cat_id WHERE product.exp_date < '"+dateFormat.format(dates)+"' and category.owner_id ='"+LoginController.All_OwnerID+"' ");

                    while (rs.next()){
                        updateMessage("Please check Your Product.\n"+rs.getString("pro_name") +" -> " + rs.getString("exp_date"));
                        Thread.sleep(500);
                    }
                    try {
                        Thread.sleep(5*60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rs.close();
                    statement.close();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

    };

    @FXML
    private AnchorPane subWindow;

    Connection connection;

    public MainPanelController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new autoTime(TimeText, AmPmText, DateText);
        UserFullName.setText(LoginController.All_FullName);
        DeshboardWindow();
        task.messageProperty().addListener((observable, oldMessage, newMessage) ->
                Notifications.create().title("Product Expired").hideAfter(Duration.seconds(10)).text(newMessage).darkStyle().show());


        new Thread(task).start();

    }

    @FXML
    void DeshboardWindow() {
        subWindowTitle.setText("Dashboard");
        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard/Dashboard.fxml"));
        loader.setController(new DashboardController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);

    }



    @FXML
    void CategoryWindow() {
        subWindowTitle.setText("Category");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Category/Category.fxml"));
        loader.setController(new CategoryController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    void SettingWindow() {
        subWindowTitle.setText("Setting");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Setting/Settings.fxml"));
        loader.setController(new Settings(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    void Product(ActionEvent event) {

        subWindowTitle.setText("Product");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product/product.fxml"));
        loader.setController(new ProductController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    private void InOutWindow(ActionEvent event) {

        subWindowTitle.setText("In/Out Employee");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InOutEmployee/InOutEmployee.fxml"));
        loader.setController(new InOutController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    private void CreateInvoice() throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/Invoice/CreateInvoice.fxml"));
        CreateInvoice invoice = new CreateInvoice(UserFullName.getText(),connection);
        Loader.setController(invoice);
        Parent root1 = (Parent) Loader.load();
        Stage stage = new Stage();
        stage.setTitle("Create Invoice | MYS | Ahosan Ullah");
        stage.setScene(new Scene(root1));
        stage.show();

    }


    @FXML
    void Employee(ActionEvent event) {

        subWindowTitle.setText("Employee");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Employee/Employee.fxml"));
        loader.setController(new EmployeeController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    void invoiceList(ActionEvent event) {

        subWindowTitle.setText("Invoice List");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Invoice/InvoiceList.fxml"));
        loader.setController(new InvoiceList(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    void MemberList(ActionEvent event) {

        subWindowTitle.setText("Member List");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Member/Member.fxml"));
        loader.setController(new MemberController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }
    @FXML
    void SellSheet(ActionEvent event) {

        subWindowTitle.setText("Sell Sheet");

        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SellSheet/SellSheet.fxml"));
        loader.setController(new SellSheetController(connection));
        Node n = null;
        try {
            n = (Node)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        subWindow.getChildren().setAll(n);
    }

    @FXML
    private void logout(Event event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        LoginController loginController = new LoginController();
        loader.setController(loginController);
        Stage stage = new Stage();
        Parent parent = loader.load();
        stage.setTitle("Login User");
        stage.setScene(new Scene(parent));
        stage.show();
    }

}
