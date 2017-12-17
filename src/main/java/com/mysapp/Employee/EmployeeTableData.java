package com.mysapp.Employee;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EmployeeTableData {
    SimpleStringProperty name,joindate,edit;
    SimpleIntegerProperty serial,empid,salary;
    public SimpleObjectProperty<ImageView> image = new SimpleObjectProperty<>();


    public EmployeeTableData(String name, String joindate, int serial, int empid, int salary,ImageView photo) {
        this.name = new SimpleStringProperty(name);
        this.joindate = new SimpleStringProperty(joindate);
        this.serial = new SimpleIntegerProperty(serial);
        this.empid = new SimpleIntegerProperty(empid);
        this.salary = new SimpleIntegerProperty(salary);
        edit = new SimpleStringProperty("ok");
        this.image.set(photo);
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

    public String getJoindate() {
        return joindate.get();
    }

    public SimpleStringProperty joindateProperty() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate.set(joindate);
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

    public int getEmpid() {
        return empid.get();
    }

    public SimpleIntegerProperty empidProperty() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid.set(empid);
    }

    public int getSalary() {
        return salary.get();
    }

    public SimpleIntegerProperty salaryProperty() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
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
    public Object getImage() {
        return image.get();
    }

    public SimpleObjectProperty<ImageView> imageProperty() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image.set(image);
    }
}
