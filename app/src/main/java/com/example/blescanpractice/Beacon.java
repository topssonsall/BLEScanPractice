package com.example.blescanpractice;

public class Beacon {

    private String uuid;
    private String major;
    private String minor;
    private String macAddress;
    private boolean isFinded;

    public Beacon() {}

    public Beacon(String uuid, String major, String minor, String macAddress, boolean isFinded) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.macAddress = macAddress;
        this.isFinded = isFinded;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public String getMacAddress(){
        return macAddress;
    }

    public boolean isFinded() {
        return isFinded;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public void setMacAddress(String macAddress){
        this.macAddress = macAddress;
    }

    public void setFinded(boolean finded) {
        isFinded = finded;
    }
}
