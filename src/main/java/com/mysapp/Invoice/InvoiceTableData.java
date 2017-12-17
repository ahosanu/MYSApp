package com.mysapp.Invoice;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by ahosa on 9/3/2017.
 */
public class InvoiceTableData {
    SimpleStringProperty Name;
    SimpleDoubleProperty Price;
    SimpleIntegerProperty Unit;
    SimpleIntegerProperty Serial;
    SimpleIntegerProperty ID;
    SimpleDoubleProperty Total;
    SimpleDoubleProperty TotaNetPrice;

    public InvoiceTableData(String name, double price, int unit, int serial, int ID, double total) {
        Name = new SimpleStringProperty(name);
        Price = new SimpleDoubleProperty(price);
        Unit = new SimpleIntegerProperty(unit);
        Serial = new SimpleIntegerProperty(serial);
        this.ID = new SimpleIntegerProperty(ID);
        Total = new SimpleDoubleProperty(total);
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public double getPrice() {
        return Price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return Price;
    }

    public void setPrice(double price) {
        this.Price.set(price);
    }

    public int getUnit() {
        return Unit.get();
    }

    public SimpleIntegerProperty unitProperty() {
        return Unit;
    }

    public void setUnit(int unit) {
        this.Unit.set(unit);
    }

    public int getSerial() {
        return Serial.get();
    }

    public SimpleIntegerProperty serialProperty() {
        return Serial;
    }

    public void setSerial(int serial) {
        this.Serial.set(serial);
    }

    public int getID() {
        return ID.get();
    }

    public SimpleIntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public double getTotal() {
        return Total.get();
    }

    public SimpleDoubleProperty totalProperty() {
        return Total;
    }

    public void setTotal(double total) {
        this.Total.set(total);
    }

    public double getTotaNetPrice() {
        return TotaNetPrice.get();
    }

    public SimpleDoubleProperty totaNetPriceProperty() {
        return TotaNetPrice;
    }

    public void setTotaNetPrice(double totaNetPrice) {
        this.TotaNetPrice.set(totaNetPrice);
    }
}
