package com.mysapp.InOutEmployee;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InOutTableData {
    SimpleIntegerProperty Seriel,EmployeeID;
    SimpleStringProperty Name,Date,intime,outtime;

    public InOutTableData(int seriel, int employeeID, String name, String date, String intime, String outtime) {
        Seriel = new SimpleIntegerProperty(seriel);
        EmployeeID = new SimpleIntegerProperty(employeeID);
        Name = new SimpleStringProperty(name);
        Date = new SimpleStringProperty(date);
        this.intime = new SimpleStringProperty(intime);
        this.outtime = new SimpleStringProperty(outtime);
    }

    public int getSeriel() {
        return Seriel.get();
    }

    public SimpleIntegerProperty serielProperty() {
        return Seriel;
    }

    public void setSeriel(int seriel) {
        this.Seriel.set(seriel);
    }

    public int getEmployeeID() {
        return EmployeeID.get();
    }

    public SimpleIntegerProperty employeeIDProperty() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.EmployeeID.set(employeeID);
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

    public String getDate() {
        return Date.get();
    }

    public SimpleStringProperty dateProperty() {
        return Date;
    }

    public void setDate(String date) {
        this.Date.set(date);
    }

    public String getIntime() {
        return intime.get();
    }

    public SimpleStringProperty intimeProperty() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime.set(intime);
    }

    public String getOuttime() {
        return outtime.get();
    }

    public SimpleStringProperty outtimeProperty() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime.set(outtime);
    }
}
