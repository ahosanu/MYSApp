package com.mysapp.Employee;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EmployeeTableData {
    SimpleStringProperty name,mobile_number,edit;
    SimpleIntegerProperty serial,empid,salary;
    public SimpleObjectProperty<ImageView> image = new SimpleObjectProperty<>();


    public EmployeeTableData(String name, String mobile_number, int serial, int empid, int salary,ImageView photo) {
        this.name = new SimpleStringProperty(name);
        this.mobile_number = new SimpleStringProperty(mobile_number);
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

    public String getmobile_number() {
        return mobile_number.get();
    }

    public SimpleStringProperty mobile_numberProperty() {
        return mobile_number;
    }

    public void setmobile_number(String mobile_number) {
        this.mobile_number.set(mobile_number);
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
