package com.mysapp.Invoice;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InvoiceListTableDate {
    SimpleIntegerProperty serial;
    SimpleStringProperty name,invoiceid,date;
    SimpleDoubleProperty totalprice;

    public InvoiceListTableDate(int serial, String name, String invoiceid, double totalprice, String date) {
        this.serial = new SimpleIntegerProperty(serial);
        this.name = new SimpleStringProperty(name);
        this.invoiceid = new SimpleStringProperty(invoiceid);
        this.totalprice = new SimpleDoubleProperty(totalprice);
        this.date = new SimpleStringProperty(date);
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

    public String getInvoiceid() {
        return invoiceid.get();
    }

    public SimpleStringProperty invoiceidProperty() {
        return invoiceid;
    }

    public void setInvoiceid(String invoiceid) {
        this.invoiceid.set(invoiceid);
    }


    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDate() {
        return date.get();
    }

    public double getTotalprice() {
        return totalprice.get();
    }

    public SimpleDoubleProperty totalpriceProperty() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice.set(totalprice);
    }
}
