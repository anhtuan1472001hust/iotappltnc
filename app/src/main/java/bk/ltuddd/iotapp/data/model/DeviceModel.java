package bk.ltuddd.iotapp.data.model;

import java.io.Serializable;

public class DeviceModel implements Serializable {

    private double humid;
    private double temp;
    private long serial;
    private String name;
    private String type;

    // state : 1 -> on , 0 -> off
    private int state;

    private boolean selected = false;



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }



    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DeviceModel() {
    }


    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
