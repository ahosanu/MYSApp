package com.mysapp;

import com.mysapp.Category.CategoryController;
import com.mysapp.Employee.EmployeeController;
import com.mysapp.Invoice.CreateInvoice;
import com.mysapp.Invoice.InvoiceList;
import com.mysapp.Product.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable{
    @FXML
    private Text DateText,TimeText,subWindowTitle,AmPmText,UserFullName;



    @FXML
    private AnchorPane subWindow;

    Connection connection;

    public MainPanelController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new autoTime(TimeText, AmPmText, DateText);
        DeshboardWindow();
    }

    @FXML
    void DeshboardWindow() {
        subWindowTitle.setText("Dashboard");
        subWindow.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        loader.setController(new DashboardController());
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

}
