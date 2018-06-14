package bean;

import domain.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named(value = "login")
public class LoginBean implements Serializable
{
    private User user;

    private String username;

    //validate login
    public String createUser()
    {

        user = new User(username, "");

        return "chat";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
