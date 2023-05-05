package bk.ltuddd.iotapp.data.model;

public class User {

    private String userUid;
    private String phoneNumber;
    private String password;

    public User(String userUid,String phoneNumber, String password) {
        this.userUid = userUid;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
