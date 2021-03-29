package com.example.mymonitor.provider;


public class PatientDetails {

    private String name;
    private String phoneNo;
    private String age;

    public PatientDetails(){}

    public PatientDetails(String name, String phoneNo, String age){
        this.name = name;
        this.phoneNo = phoneNo;
        this.age = age;

    }

    public PatientDetails(String name, String phoneNo){
        this.name = name;
        this.phoneNo = phoneNo;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}