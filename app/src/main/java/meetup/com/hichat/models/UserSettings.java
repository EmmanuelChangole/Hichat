package meetup.com.hichat.models;

public class UserSettings
{
    private Users users;
    private UsersAccountSettings usersAccountSettings;
    public UserSettings()
    {

    }

    public UserSettings(Users users, UsersAccountSettings usersAccountSettings) {
        this.users = users;
        this.usersAccountSettings = usersAccountSettings;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UsersAccountSettings getUsersAccountSettings() {
        return usersAccountSettings;
    }

    public void setUsersAccountSettings(UsersAccountSettings usersAccountSettings) {
        this.usersAccountSettings = usersAccountSettings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "users=" + users +
                ", usersAccountSettings=" + usersAccountSettings +
                '}';
    }
}
