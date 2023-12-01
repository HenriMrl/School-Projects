package com.tests;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class WarningMessage {
    public ZonedDateTime sent;
    private String nick;
    private Double latitude;
    private Double longitude;
    private String dangertype;
    private String areacode;
    private String phonenumber;


    public WarningMessage() {
        this.nick = nick;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dangertype = dangertype;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getAreacode() {
        return areacode;
    }

    long dateAsInt() {
        return sent.toInstant().toEpochMilli();
        }

    void setSent(long epoch) {
        sent = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
        }  

    public String getNick() {
        return nick;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDangertype() {
        return dangertype;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDangertype(String dangertype) {
        this.dangertype = dangertype;
    }
    
    
}
