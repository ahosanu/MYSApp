package com.mysapp.Invoice;

import com.jfoenix.controls.JFXCheckBox;
import com.mysapp.LoginController;
import com.mysapp.Product.ProductTableDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by ahosa on 9/2/2017.
 */
public class CreateInvoice implements Initializable {
    @FXML
    Text seller_name,TopNetAmount;
    Connection connection = null;
    int sl_no = 1;

    @FXML
    TextArea Address;
    @FXML
    TextField Unit,SubTotal,vat,Discount, M_Discount,Net_amount,Pay, Change,productID,Membersl,BuyerName,Mobile,Email;
    @FXML
    private TableColumn<ProductTableDate, Label> delete;

    public int userID;
    @FXML
    private TableView<InvoiceTableData> table = new TableView<InvoiceTableData>();
    private ObservableList<InvoiceTableData> data = FXCollections.observableArrayList();
    @FXML
    TableColumn<?, ?> serila,Name, ID,Price,Total,Units;
    @FXML
    JFXCheckBox LastPrice;


    private StringProperty fullName = new SimpleStringProperty();

    public CreateInvoice(String Fullname, Connection connection){
        this.fullName.set(Fullname);
        this.userID = LoginController.All_UserID;
        this.connection=connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seller_name.setText(fullName.getValue());
        ID.setCellValueFactory(
                new PropertyValueFactory<>("ID")
        );
        Name.setCellValueFactory(
                new PropertyValueFactory<>("Name")
        );
        serila.setCellValueFactory(
                new PropertyValueFactory<>("Serial")
        );
        Price.setCellValueFactory(
                new PropertyValueFactory<>("Price")
        );
        Units.setCellValueFactory(
                new PropertyValueFactory<>("Unit")
        );
        Total.setCellValueFactory(
                new PropertyValueFactory<>("Total")
        );
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
    }

    @FXML
    private void AddProduct() throws SQLException {
        if(!productID.getText().isEmpty()) {
            updateProductlist(Integer.parseInt(Unit.getText()), Integer.parseInt(productID.getText()));
            productID.setText("");
            Unit.setText("1");
        }
    }

    @FXML
    private void FindProduct() throws IOException {
       /* FXMLLoader Loader = new FXMLLoader(getClass().getResource("FindProduct.fxml"));
        Stage stage = new Stage();
        FindProduct addProduct = new FindProduct(table,data,connection,SubTotal,vat,Discount,M_Discount,Net_amount,Pay,Change);
        Loader.setController(addProduct);
        Parent root1 = (Parent) Loader.load();
        stage.setTitle("Find Product | Rong-IT | SuperShop");
        stage.setScene(new Scene(root1));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setOnCloseRequest(E->{
            System.out.println(addProduct.SelectID +" - " + addProduct.AddUnit);
            try {
                updateProductlist(Integer.parseInt(Unit.getText()), addProduct.SelectID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            productID.setText("");
            Unit.setText("1");
        });
        stage.show();*/
    }

    void updateProductlist(int unit,int id) throws SQLException {
        boolean find = true;
        double subTotal = 0;
        for(int i=0; i < data.size(); i++){
            if(data.get(i).getID() == id){
                data.get(i).setUnit(data.get(i).getUnit()+unit);
                data.get(i).setTotal(data.get(i).getUnit() * data.get(i).getPrice());
                find=false;
            }
            subTotal += data.get(i).getTotal();
        }
        if(find){

            System.out.println(LastPrice.isSelected());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `product` WHERE `pro_id`="+id);
            if(resultSet.next()) {
                int getunit = Integer.parseInt(Unit.getText());
                double pro_price = 0;
                if(LastPrice.isSelected())
                    pro_price = resultSet.getDouble("net_price");
                else
                    pro_price = resultSet.getDouble("sell_price");

                double total = pro_price * getunit;
                subTotal += total;

                data.add(new InvoiceTableData(resultSet.getString("pro_name"), pro_price, unit, sl_no, id, total));
                sl_no++;
            }else{
                System.out.println("Not Found ");
            }
        }
        table.setItems(data);
        table.refresh();
        SubTotal.setText(subTotal+"");
        double vat_taka = Double.parseDouble(String.format("%.2f", subTotal*.02));
        vat.setText(vat_taka+"");

        double netamount = subTotal+vat_taka-Double.parseDouble(M_Discount.getText())-Double.parseDouble(Discount.getText());
        Net_amount.setText(netamount+"");
        TopNetAmount.setText(netamount+"");

        Change.setText("-"+netamount);
        LastPrice.setSelected(false);
    }

    @FXML
    private void NetAmount_reload(){
        double s_total = Double.parseDouble(SubTotal.getText());
        double Discounts = 0;
        if (!Discount.getText().isEmpty())
            Discounts = Double.parseDouble(Discount.getText());
        double m_Discount = Double.parseDouble(M_Discount.getText());
        double vat_tax = Double.parseDouble(vat.getText());

        double pay_amount = 0;
        if(!Pay.getText().isEmpty())
            pay_amount = Double.parseDouble(Pay.getText());
        s_total = s_total + vat_tax - m_Discount - Discounts;

        Net_amount.setText(s_total+"");
        TopNetAmount.setText(s_total+"");

        pay_amount -= s_total;
        DecimalFormat df = new DecimalFormat("#.##");
        Change.setText(df.format(pay_amount)+"");
    }

    @FXML
    private void SavePrint() throws SQLException {
        if(!Pay.getText().isEmpty() && Double.parseDouble(Change.getText()) >= 0) {
            DateFormat dateFormat = new SimpleDateFormat("dMyyhhmmss");
            DateFormat RowTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date date = new Date();

            String CinvoiceID = userID + dateFormat.format(date);
            System.out.println(date.getTime());

            Statement statement = connection.createStatement();
            int x = statement.executeUpdate("INSERT INTO `invoice`(`BuyerName`, `MemberID`, `Email`, `Mobile`, `Address`, `RowTime`, `InvoiceID`, `SubTotal`, `Vat`, `MDiscount`, `Discount`, `NetAmount`, `Pay`, `ChangeAmount`, `SellerID`,`TodayTime`,`TotalnetPrice`) VALUES" +
                    " ('" + BuyerName.getText() + "','" + Membersl.getText()  + "','" + Email.getText().toString() + "','" + Mobile.getText() + "','" + Address.getText() + "','" + RowTime.format(date).toString() + "','" + CinvoiceID + "','" + SubTotal.getText() + "','" + vat.getText() + "','" + M_Discount.getText() + "','" + Discount.getText() + "','" + Net_amount.getText() + "','" + Pay.getText() + "','" + Change.getText() + "','" + userID + "','"+date.getTime()+"','"+getNetAmount()+"')");

            if (x == 1) {
                Statement StateInvoiceID = connection.createStatement();
                for (InvoiceTableData InData : data) {
                    StateInvoiceID.executeUpdate("INSERT INTO `invoicepro`(`InvoiceID`, `Name`, `Unit`, `Price`, `Total`, `ProID`,`netPrice`) VALUES " +
                            "('" + CinvoiceID + "','" + InData.getName() + "','" + InData.getUnit() + "','" + InData.getPrice() + "','" + InData.getTotal() + "','" + InData.getID() + "','"+InData.getTotaNetPrice()+"')");
                }
                StateInvoiceID.close();
            }
            statement.close();
            data.clear();
            table.setItems(data);
            table.refresh();

            SubTotal.setText("0.00");
            vat.setText("0.00");
            M_Discount.setText("0.00");
            Discount.setText("0.00");
            Net_amount.setText("0.00");
            TopNetAmount.setText("0.00");
            Pay.setText("");
            Change.setText("0.00");
            Membersl.setText("");
            BuyerName.setText("");
            Email.setText("");
            Mobile.setText("");
            Address.setText("");
        }

    }
    @FXML
    private void Print(){

    }

    @FXML
    private void Delete(){

    }

    double getNetAmount(){
        double total =0;
        for (InvoiceTableData InData : data) {
            total+=InData.getTotaNetPrice();
        }
        return total;
    }


    @FXML
    private void TableClick() {
        if (table.getSelectionModel().isEmpty())
            return;
        int cell = table.getSelectionModel().getSelectedCells().get(0).getColumn();
        if (cell == 6) {
            Dialog<ButtonType> canDelete = new Dialog();
            canDelete.setTitle("Delete Warning!");
            canDelete.setContentText("Are you delete This \"" + table.getSelectionModel().getSelectedItem().getName() + "\"?");
            canDelete.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
            canDelete.showAndWait().filter(result -> result == ButtonType.YES).ifPresent(result -> {
                //int i = 0;
                for(InvoiceTableData Deletedata : data){
                    if(Deletedata.getID() == table.getSelectionModel().getSelectedItem().getID()){
                        data.remove(Deletedata);
                        break;
                    }

                }



                int ok = 0;
                System.out.println("ok" + table.getSelectionModel().getSelectedItem().getID());
                if (ok == 1) {
                    Notifications notifications = Notifications.create()
                            .title("Successful")
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT)
                            .onAction(e -> {
                                System.out.println("click");
                            });
                    notifications.text("Successfully Delete.");
                    notifications.showConfirm();

                    data.clear();
                    table.setItems(data);
                    table.refresh();
                }
            });

        }
    }
}
