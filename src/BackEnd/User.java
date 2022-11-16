package BackEnd;

public class User {
    private String ID;
    private String name;
    private String phoneNumber;
    private String userName;
    private String password;

    public User(String ID, String name, String phoneNumber, String userName, String password) {
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
