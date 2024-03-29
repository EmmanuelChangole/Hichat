package meetup.com.hichat.models;

public class Users
{
    private String user_id;
    private long phone_number;
    private String  email;
    private String user_name;

    public Users(String user_id, long phone_number, String email, String user_name) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.user_name = user_name;
    }
    public Users(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
