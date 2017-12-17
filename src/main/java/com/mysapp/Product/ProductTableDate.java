package com.mysapp.Product;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class ProductTableDate {
    private SimpleIntegerProperty serial;
    private SimpleStringProperty name;
    private SimpleIntegerProperty proid;
    private SimpleDoubleProperty sellprice;
    private SimpleDoubleProperty lastprice;
    private SimpleStringProperty expdate;
    private SimpleStringProperty Discount;
    private SimpleStringProperty edit;
    private SimpleStringProperty Description;

    public ProductTableDate(int serial, String name, int proid, double sellprice, double lastprice, String expdate, String discount,String edit) {
        this.serial = new SimpleIntegerProperty(serial);
        this.name = new SimpleStringProperty(name);
        this.proid = new SimpleIntegerProperty(proid);
        this.sellprice = new SimpleDoubleProperty(sellprice);
        this.lastprice = new SimpleDoubleProperty(lastprice);
        this.expdate = new SimpleStringProperty(expdate);
        this.Discount = new SimpleStringProperty(discount);
        this.edit = new SimpleStringProperty(edit);

    }
    public ProductTableDate(int serial, String name, int proid, double sellprice, double lastprice, String expdate, String discount,String edit,String Description) {
        this.serial = new SimpleIntegerProperty(serial);
        this.name = new SimpleStringProperty(name);
        this.proid = new SimpleIntegerProperty(proid);
        this.sellprice = new SimpleDoubleProperty(sellprice);
        this.lastprice = new SimpleDoubleProperty(lastprice);
        this.expdate = new SimpleStringProperty(expdate);
        this.Discount = new SimpleStringProperty(discount);
        this.edit = new SimpleStringProperty(edit);
        this.Description = new SimpleStringProperty(Description);

    }


    public int getSerial() {
        return serial.get();
    }

    public SimpleIntegerProperty serialProperty() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial.set(serial);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getProid() {
        return proid.get();
    }

    public SimpleIntegerProperty proidProperty() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid.set(proid);
    }

    public double getSellprice() {
        return sellprice.get();
    }

    public SimpleDoubleProperty sellpriceProperty() {
        return sellprice;
    }

    public void setSellprice(double sellprice) {
        this.sellprice.set(sellprice);
    }

    public double getLastprice() {
        return lastprice.get();
    }

    public SimpleDoubleProperty lastpriceProperty() {
        return lastprice;
    }

    public void setLastprice(double lastprice) {
        this.lastprice.set(lastprice);
    }

    public String getExpdate() {
        return expdate.get();
    }

    public SimpleStringProperty expdateProperty() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate.set(expdate);
    }

    public String getDiscount() {
        return Discount.get();
    }

    public SimpleStringProperty discountProperty() {
        return Discount;
    }

    public void setDiscount(String discount) {
        this.Discount.set(discount);
    }

    public String getEdit() {
        return edit.get();
    }

    public SimpleStringProperty editProperty() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit.set(edit);
    }

    public String getDescription() {
        return Description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description.set(description);
    }
}
