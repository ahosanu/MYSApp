package com.mysapp.Category;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CategoryTableData {
    SimpleStringProperty name;
    SimpleIntegerProperty serial,categoryid,NumberOfProduct;
    SimpleDoubleProperty TotalAmount,Discount;

    public CategoryTableData(int serial, String name, int categoryid, int numberOfProduct, double totalAmount, double discount) {
        this.name = new SimpleStringProperty(name);
        this.serial = new SimpleIntegerProperty(serial);
        this.categoryid = new SimpleIntegerProperty(categoryid);
        NumberOfProduct = new SimpleIntegerProperty(numberOfProduct);
        TotalAmount = new SimpleDoubleProperty(totalAmount);
        Discount = new SimpleDoubleProperty(discount);
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

    public int getSerial() {
        return serial.get();
    }

    public SimpleIntegerProperty serialProperty() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial.set(serial);
    }

    public int getCategoryid() {
        return categoryid.get();
    }

    public SimpleIntegerProperty categoryidProperty() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid.set(categoryid);
    }

    public int getNumberOfProduct() {
        return NumberOfProduct.get();
    }

    public SimpleIntegerProperty numberOfProductProperty() {
        return NumberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.NumberOfProduct.set(numberOfProduct);
    }

    public double getTotalAmount() {
        return TotalAmount.get();
    }

    public SimpleDoubleProperty totalAmountProperty() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.TotalAmount.set(totalAmount);
    }

    public double getDiscount() {
        return Discount.get();
    }

    public SimpleDoubleProperty discountProperty() {
        return Discount;
    }

    public void setDiscount(double discount) {
        this.Discount.set(discount);
    }
}
