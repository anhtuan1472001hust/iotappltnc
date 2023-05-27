package bk.ltuddd.iotapp.data.model;

public class DeviceType {




    private Dht11 dht11;
    private Lamp lamp;
    private String type;

    public DeviceType() {
    }


    public Dht11 getDht11() {
        return dht11;
    }

    public void setDht11(Dht11 dht11) {
        this.dht11 = dht11;
    }

    public Lamp getLamp() {
        return lamp;
    }

    public void setLamp(Lamp lamp) {
        this.lamp = lamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
