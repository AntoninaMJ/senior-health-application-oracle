package com.antonina.health.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Result {

    private Long id;
    private User user;
    private LocalDateTime dateTime;
    private Integer pressureSys;
    private Integer pressureDia;
    private BigDecimal temperature;
    private Integer mood;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getPressureSys() {
        return pressureSys;
    }

    public void setPressureSys(Integer pressureSys) {
        this.pressureSys = pressureSys;
    }

    public Integer getPressureDia() {
        return pressureDia;
    }

    public void setPressureDia(Integer pressureDia) {
        this.pressureDia = pressureDia;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }
}
