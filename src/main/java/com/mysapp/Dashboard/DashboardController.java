package com.mysapp.Dashboard;

import com.mysapp.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    Connection connection;
    @FXML
    private AreaChart<?, ?> Month;
    @FXML
    Text TotalOrder,Member,totalProduct,totalSell;

    public DashboardController(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            XYChart.Series Sells = new XYChart.Series();
            Sells.setName("Last 30 day Sells");
            Statement statement = connection.createStatement();
            Date date = new Date();
            SimpleDateFormat time_formatter = new SimpleDateFormat("dd/MM/yyyy");

            Date Sdate,Enddate;
            Sdate = time_formatter.parse(time_formatter.format(date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(Sdate);
            cal.add(Calendar.DAY_OF_YEAR,   1);

            int i = 0;
            while(i < 30) {
                Sdate.setTime(cal.getTime().getTime());
                cal.setTime(Sdate);
                cal.add(Calendar.DAY_OF_YEAR,-1);
                Enddate = time_formatter.parse(time_formatter.format(cal.getTime()));
                DateFormat RowTime = new SimpleDateFormat("dd/MM/yyyy");
                Sells.getData().add(new XYChart.Data(RowTime.format(Enddate), getValue(RowTime.format(Enddate))));
                Sdate.setTime(Enddate.getTime());
                i++;
            }
            Month.getData().addAll(Sells);

            ResultSet rs = statement.executeQuery("SELECT * FROM (SELECT COUNT(member_name) as Totalmember FROM `member` WHERE owner_id = '"+ LoginController.All_OwnerID+"') AS Table1 JOIN (SELECT COUNT(user.user_id) as TotalOrder FROM user JOIN buyer ON user.user_id = buyer.user_id WHERE user.owner_id = '"+ LoginController.All_OwnerID+"')  AS Table2 JOIN (SELECT SUM(product.unit) as totalPro FROM `category` JOIN `product` ON category.cat_id = product.cat_id WHERE owner_id = '"+ LoginController.All_OwnerID+"')  AS Table3 JOIN (SELECT SUM(sell_price*quantity) AS TotalSell FROM buy_product_list WHERE buy_product_list.buyer_id IN (SELECT buyer.buyer_id FROM `user` JOIN `buyer` ON user.user_id = buyer.user_id WHERE user.owner_id = '"+ LoginController.All_OwnerID+"'  and buyer.nowdate = '"+time_formatter.format(date.getTime())+"'))  AS Table4");
            if(rs.next())
            {
                totalSell.setText("0");
                totalProduct.setText("0");
                totalProduct.setText("0");
                TotalOrder.setText("0");
                if(rs.getString("TotalSell") != null)
                    totalSell.setText(rs.getString("TotalSell"));
                if(rs.getString("totalPro") != null)
                    totalProduct.setText(rs.getString("totalPro"));
                if(rs.getString("TotalOrder") != null)
                    TotalOrder.setText(rs.getString("TotalOrder"));
                if(rs.getString("Totalmember") != null)
                    Member.setText(rs.getString("Totalmember"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private double getValue(String format) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SUM(sell_price*quantity) AS TotalSell FROM buy_product_list WHERE buy_product_list.buyer_id IN (SELECT buyer.buyer_id FROM `user` JOIN `buyer` ON user.user_id = buyer.user_id WHERE user.owner_id = '"+LoginController.All_OwnerID+"'  and buyer.nowdate = '"+format+"')");
            if(resultSet.next())
                return resultSet.getDouble("TotalSell");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
