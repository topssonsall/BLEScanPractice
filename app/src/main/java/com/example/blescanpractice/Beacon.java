package com.example.blescanpractice;

public class Beacon {

    private String uuid;
    private String major;
    private String minor;
    private String macAddress;
    private Double distance;
    private Long findedTime;
    private boolean isFinded;

    public Beacon() {}

    public Beacon(String uuid, String major, String minor, String macAddress, Double distance, boolean isFinded, Long findedTime) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.macAddress = macAddress;
        this.distance = distance;
        this.isFinded = isFinded;
        this.findedTime = findedTime;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getFindedTime() {
        return findedTime;
    }

    public void setFindedTime(Long findedTime) {
        this.findedTime = findedTime;
    }
}
