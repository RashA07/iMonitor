package com.example.mymonitor.provider;

public class Reading {

    String Date;
    String HeartRate;
    String SP02;
    String Temperature;
    String Time;
    String ID;

    public Reading(){}

    public Reading (String HeartRate, String SP02, String Temperature, String Date, String Time, String ID){
        this.HeartRate=HeartRate;
        this.SP02=SP02;
        this.Temperature=Temperature;
        this.Date = Date;
        this.Time = Time;
        this.ID = ID;
    }

    public String getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(String heartRate) {
        HeartRate = heartRate;
    }

    public String getSP02() {
        return SP02;
    }

    public void setSP02(String SP02) {
        this.SP02 = SP02;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
