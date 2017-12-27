package com.mysapp;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintDocument implements Printable {
    Connection connection;
    int id;
    public PrintDocument(Connection connection,int id) {
        this.connection = connection;
        this.id = id;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0)
            return NO_SUCH_PAGE;
        try {
            Statement statement = connection.createStatement();
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setFont(new Font("Berkshire Swash", Font.PLAIN, 20));
            g2.setPaint(Color.black);
            g2.drawString("MYS App", 150, 50);
            g2.setFont(new Font("Consolas", Font.PLAIN, 9));
            g2.drawString("House – 80, Road – 8/A, Satmasjid Road,", 100, 70);
            g2.drawString("Dhanmondi, Dhaka-1209,Bangladesh.", 105, 84);
            g2.drawString("----------------------------------------------------------------------------", 10, 94);
            ResultSet rs = statement.executeQuery("SELECT * FROM `buyer` WHERE buyer_id ='"+id+"'");
            //g2.setFont(new Font("Arial", Font.PLAIN, 10));
            if(rs.next()) {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("nowdate"));
                g2.drawString("Invoice ID:   MYS-" + rs.getInt("buyer_id") + "-" + ((date.getTime() / 10000) + rs.getInt("buyer_id")), 20, 104);
                g2.drawString("Name:  " + rs.getString("buyer_name"), 20, 116);
                g2.drawString("Mobile :  " + rs.getString("buyer_mobile"), 20, 128);
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date dates = new Date();
                g2.drawString("Date And Time :  " + dateFormat.format(dates), 20, 140);
                g2.drawString("----------------------------------------------------------------------------", 10, 152);
                g2.drawString("----------------------------------------------------------------------------", 10, 157);
                g2.drawString("       Product Name               Unit         Price          Total Price", 10, 169);
                g2.drawString("----------------------------------------------------------------------------", 10, 181);
                int i = 0, y = 194;
                rs = statement.executeQuery("SELECT product.pro_name,buy_product_list.quantity,buy_product_list.sell_price,(buy_product_list.quantity*buy_product_list.sell_price) as total FROM product JOIN `buy_product_list` ON product.pro_id = buy_product_list.pro_id WHERE buyer_id ='" + id + "'");
                double subtotal = 0;
                while (rs.next()) {
                    i++;
                    String name = rs.getString("pro_name");
                    if (name.length() <= 24)
                        g2.drawString("  " + i + ". " + name, 10, y);
                    else {
                        g2.drawString("  " + i + ". " + name.substring(0, 24), 10, y);
                        y += 13;
                        g2.drawString("     " + name.substring(24, name.length()), 10, y);
                    }

                    g2.drawString(rs.getString("quantity"), 180, y);
                    g2.drawString(rs.getString("sell_price"), 240, y);
                    subtotal+=rs.getDouble("total");
                    g2.drawString(rs.getString("total"), 320, y);
                    y += 13;
                }


                g2.drawString("----------------------------------------------------------------------------", 10, y);
                y += 10;
                g2.drawString("----------------------------------------------------------------------------", 10, y);
                y += 18;
                g2.setFont(new Font("Consolas", Font.BOLD, 20));
                g2.drawString("SUB TOTAL:", 10, y);
                g2.drawString(String.valueOf(subtotal), 280, y);
                y += 18;
                g2.setFont(new Font("Consolas", Font.BOLD, 10));
                g2.drawString("Discount:", 10, y);
                g2.drawString("0.00", 280, y);
                y += 18;
                g2.setFont(new Font("Consolas", Font.BOLD, 10));
                g2.drawString("Vat 2%:", 10, y);
                double vat = subtotal*.02;
                g2.drawString(String.valueOf(vat), 280, y);
                y += 10;
                g2.drawString("----------------------------------------------------------------------------", 10, y);
                y += 10;
                g2.drawString("----------------------------------------------------------------------------", 10, y);
                y += 18;
                g2.setFont(new Font("Consolas", Font.BOLD, 20));
                g2.drawString("NET TOTAL:", 10, y);
                g2.drawString(String.valueOf((subtotal+vat)), 280, y);
                y += 20;
                g2.setFont(new Font("Consolas", Font.PLAIN, 9));
                g2.drawString("United International University", 100, y);
                y += 12;
                g2.drawString("Copyright By Md. Ahosan Ullah", 110, y);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return PAGE_EXISTS;
    }
}
