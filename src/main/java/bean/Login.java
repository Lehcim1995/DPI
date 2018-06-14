package bean;

import domain.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named(value = "login")
public class Login implements Serializable
{
    private User user;

    private String username;

    //validate login
    public String validateUsernamePassword() {
//        boolean valid = LoginDAO.validate(user, pwd);
//        if (valid) {
//            HttpSession session = SessionUtils.getSession();
//            session.setAttribute("username", user);
//            return "admin";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(
//                    null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN,
//                            "Incorrect Username and Passowrd",
//                            "Please enter correct username and Password"));

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
