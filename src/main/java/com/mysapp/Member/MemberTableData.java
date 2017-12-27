package com.mysapp.Member;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemberTableData {
    SimpleIntegerProperty memberPoint,memberID,serial;
    SimpleStringProperty name;

    public MemberTableData(int memberPoint, int memberID, int serial, String name) {
        this.memberPoint = new SimpleIntegerProperty(memberPoint);
        this.memberID = new SimpleIntegerProperty(memberID);
        this.serial = new SimpleIntegerProperty(serial);
        this.name = new SimpleStringProperty(name);
    }

    public int getMemberPoint() {
        return memberPoint.get();
    }

    public SimpleIntegerProperty memberPointProperty() {
        return memberPoint;
    }

    public void setMemberPoint(int memberPoint) {
        this.memberPoint.set(memberPoint);
    }

    public int getMemberID() {
        return memberID.get();
    }

    public SimpleIntegerProperty memberIDProperty() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID.set(memberID);
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
}
