package com.mysapp.Invoice;

import com.jfoenix.controls.JFXCheckBox;
import com.mysapp.LoginController;
import com.mysapp.PrintDocument;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by ahosa on 9/2/2017.
 */
public class CreateInvoice implements Initializable {
    @FXML
    Text seller_name,TopNetAmount,TodayDate;
    Connection connection = null;



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

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        TodayDate.setText(dateFormat.format(date));
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
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/Invoice/Findproduct.fxml"));
        Stage stage = new Stage();
        FindProductController addProduct = new FindProductController(connection);
        Loader.setController(addProduct);
        Parent root1 = (Parent) Loader.load();
        stage.setTitle("Find Product | Rong-IT | SuperShop");
        stage.setScene(new Scene(root1));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnHidden(E->{
           if(addProduct.isClick){
               System.out.println("yes");
               try {
                   updateProductlist(Integer.parseInt(addProduct.Unit.getText()),addProduct.producttable.getSelectionModel().getSelectedItem().getProid());
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
        });
        stage.show();
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
            table.refresh();
        }
        if(find) {

            System.out.println(LastPrice.isSelected());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user.owner_id FROM user WHERE user.user_id ='" + LoginController.All_UserID + "'");
            if (resultSet.next()) {
                int get_owner_id = resultSet.getInt("owner_id");
                if (get_owner_id == 0)
                    get_owner_id = LoginController.All_UserID;

                resultSet = statement.executeQuery("SELECT category.owner_id, product.pro_id,product.pro_name,product.in_date,product.exp_date,product.sell_price,product.net_price,product.discount FROM category JOIN product ON category.cat_id = product.cat_id WHERE pro_id = '" + id + "' and owner_id = '" + get_owner_id + "' ");
                if (resultSet.next()) {
                    int getunit = unit;
                    double pro_price = 0;
                    if (LastPrice.isSelected())
                        pro_price = resultSet.getDouble("net_price");
                    else
                        pro_price = resultSet.getDouble("sell_price");

                    double total = pro_price * getunit;
                    subTotal += total;

                    data.add(new InvoiceTableData(resultSet.getString("pro_name"), pro_price, unit, data.size() + 1, id, total, resultSet.getDouble("discount")));

                } else {
                    System.out.println("Not Found ");
                }
            }
        }
            table.setItems(data);
            table.refresh();
            SubTotal.setText(subTotal + "");
            double vat_taka = Double.parseDouble(String.format("%.2f", subTotal * .02));
            vat.setText(vat_taka + "");

            double netamount = subTotal + vat_taka - Double.parseDouble(M_Discount.getText()) - Double.parseDouble(Discount.getText());
            Net_amount.setText(netamount + "");
            TopNetAmount.setText(netamount + "");

            Change.setText("-" + netamount);
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
            statement.executeUpdate("INSERT INTO `buyer`(`buyer_name`, `buyer_mobile`, `address`, `email`, `nowdate`, `payed_amount`, `user_id`) VALUES ('"+BuyerName.getText()+"','"+Mobile.getText()+"','"+Address.getText()+"','"+Email.getText()+"','"+TodayDate.getText()+"','"+Pay.getText()+"','"+LoginController.All_UserID+"')",Statement.RETURN_GENERATED_KEYS);
            int key = 0;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                key=rs.getInt(1);
            }
            System.out.println(key);
            for (InvoiceTableData table:data)
                statement.executeUpdate("INSERT INTO `buy_product_list`(`buyer_id`, `pro_id`, `sell_price`, `quantity`, `discount`) VALUES " +
                        "('"+key+"','"+table.getID()+"','"+table.getPrice()+"','"+table.getUnit()+"','"+table.getDiscount()+"')");

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
            Print(key);
        }else{
            Notifications notifications = Notifications.create()
                    .title("Payment Error.")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .onAction(e->{
                        System.out.println("click");
                    });
            notifications.text("Payment Due. Please Pay.");
            notifications.showWarning();
        }

    }
    private void Print(int id){
        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = pj.defaultPage();
        Paper paper = new Paper();
        double margin = 10; // half inch
        paper.setSize(6*72,9*72);
        paper.setImageableArea(0.5*72,0.5*72,6*72,9*72);


        pf.setPaper(paper);


        pj.setPrintable(new PrintDocument(connection,id), pf);
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    private void Delete(){
        data.clear();
        table.getItems().clear();
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

                    Notifications notifications = Notifications.create()
                            .title("Successful")
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT)
                            .onAction(e -> {
                                System.out.println("click");
                            });
                    notifications.text("Successfully Delete.");
                    notifications.showConfirm();


                    int i=1;
                    for(InvoiceTableData Deletedata : data){
                        Deletedata.setSerial(i++);
                    }
                    table.setItems(data);
                    table.refresh();

            });

        }
    }
}
