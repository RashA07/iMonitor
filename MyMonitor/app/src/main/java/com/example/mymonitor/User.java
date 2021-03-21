package com.example.mymonitor;

public class User {

    private static String key;
    private static String name;
    private static String device;
    private static UserEnum usertype;

    public User (String key, String name, String device, UserEnum usertype){
        User.key = key;
        User.name = name;
        User.device = device;
        User.usertype = usertype;
    }

    public static String getKey() {
        return key;
    }

    public static String getDevice() {
        return device;
    }

    public static String getName() {
        return name;
    }

    public static UserEnum getUsertype() {
        return usertype;
    }

}
