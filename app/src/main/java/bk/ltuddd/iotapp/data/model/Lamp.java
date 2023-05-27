package bk.ltuddd.iotapp.data.model;

public class Lamp {



    // state : 1 -> on , 0 -> off
    private int state;
    private String name;
    private String type;

    public Lamp() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
