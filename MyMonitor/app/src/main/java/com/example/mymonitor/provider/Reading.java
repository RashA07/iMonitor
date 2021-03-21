package com.example.mymonitor.provider;

public class Reading {

    String HeartRate;
    String SP02;
    String Temperature;

    public Reading(){}

    public Reading (String HeartRate, String SP02, String Temperature){
        this.HeartRate=HeartRate;
        this.SP02=SP02;
        this.Temperature=Temperature;
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
}
