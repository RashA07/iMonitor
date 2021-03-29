package com.example.mymonitor.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clinic {

    private String name;
    private String phoneNo;
    private Boolean status;

    private HashMap<String,String> patients;

    public Clinic(){}

    public Clinic(String name, String phoneNo, Boolean status) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.status = status;
        this.patients = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getStatusString() {
        if (status){
            return "Online";
        }
        else {
            return "Offline";
        }
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public HashMap<String,String> getPatients() {
        return patients;
    }

    public void setPatients(HashMap<String,String> patients) {
        this.patients = patients;
    }
}
