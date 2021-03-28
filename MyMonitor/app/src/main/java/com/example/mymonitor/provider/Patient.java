package com.example.mymonitor.provider;


import java.util.ArrayList;
import java.util.List;

public class Patient {

    private PatientDetails details;
    private Device device;
    private List<String> clinics;

    public Patient(){}

    public Patient(PatientDetails details, Device device, List<String> clinics) {
        this.details = details;
        this.device = device;
        this.clinics = clinics;
    }

    public Patient(PatientDetails details, Device device){
        this.details = details;
        this.device = device;

        clinics = new ArrayList<>();
        //default emergency call
        clinics.add("-MWCkgsPm0RMYc0gzYm4");


    }

    public PatientDetails getDetails() {
        return details;
    }

    public void setDetails(PatientDetails details) {
        this.details = details;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevices(Device device) {
        this.device = device;
    }

    public List<String> getClinics() {
        return clinics;
    }

    public void setClinics(List<String> clinics) {
        this.clinics = clinics;
    }

    public void addClinic(String clinic){this.clinics.add(clinic);}

}


