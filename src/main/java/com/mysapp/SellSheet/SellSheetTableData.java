package com.mysapp.SellSheet;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SellSheetTableData {
    SimpleDoubleProperty totalAmount,TotalProfit;
    SimpleStringProperty name;
    SimpleIntegerProperty serial,product_id,unitpro;

    public SellSheetTableData(double totalAmount, double totalProfit, String name, int serial, int product_id,int unit) {
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        TotalProfit = new SimpleDoubleProperty(totalProfit);
        this.name = new SimpleStringProperty(name);
        this.serial = new SimpleIntegerProperty(serial);
        this.product_id = new SimpleIntegerProperty(product_id);
        this.unitpro = new SimpleIntegerProperty(unit);
    }

    public int getUnitpro() {
        return unitpro.get();
    }

    public SimpleIntegerProperty unitproProperty() {
        return unitpro;
    }

    public void setUnitpro(int unitpro) {
        this.unitpro.set(unitpro);
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public SimpleDoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public double getTotalProfit() {
        return TotalProfit.get();
    }

    public SimpleDoubleProperty totalProfitProperty() {
        return TotalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.TotalProfit.set(totalProfit);
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

    public int getProduct_id() {
        return product_id.get();
    }

    public SimpleIntegerProperty product_idProperty() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id.set(product_id);
    }
}
