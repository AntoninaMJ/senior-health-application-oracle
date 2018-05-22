package com.antonina.health.form;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class ResultForm {

    @Max(200)
    @Min(80)
    private Integer pressureSys;
    @Max(160)
    @Min(50)
    private Integer pressureDia;
    @DecimalMax("42.0")
    @DecimalMin("34.0")
    private BigDecimal temperature;
    @Max(5)
    @Min(1)
    private Integer mood;

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
