package com.cromewell.chat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Jo on 23.07.2016.
 *
 * Stores user data.
 */
public class UserData {

    private LocalDateTime lastTimeConnected;
    private String name;
    private String ip;

    public UserData(){
    }

    public UserData(LocalDateTime lastTimeConnected, String name, String ip){
        this.lastTimeConnected = lastTimeConnected;
        this.name = name;
        this.ip = ip;
    }

    public LocalDateTime getLastTimeConnected() {
        return lastTimeConnected;
    }

    public void setLastTimeConnected(LocalDateTime lastTimeConnected) {
        this.lastTimeConnected = lastTimeConnected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
