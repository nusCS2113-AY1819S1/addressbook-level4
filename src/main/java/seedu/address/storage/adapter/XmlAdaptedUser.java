package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.user.User;

/**
 * This represents a user in Trajectory.
 */
@XmlRootElement(name = "user")

public class XmlAdaptedUser {

    private String email;
    private String unhashedPassword;
    private String role;

    public XmlAdaptedUser(){

    }

    public XmlAdaptedUser(String email, String unhashedPassword, String role) {
        this.email = email;
        this.unhashedPassword = unhashedPassword;
        this.role = role;
    }

    public XmlAdaptedUser(User user) {
        this.email = user.getEmail();
        this.unhashedPassword = user.getUnhashedPassword();
        this.role = user.getRole() + "";
    }

    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "unhashedPassword")
    public String getUnhashedPassword() {
        return unhashedPassword;
    }

    public void setUnhashedPassword(String unhashedPassword) {
        this.unhashedPassword = unhashedPassword;
    }

    @XmlElement(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User toModelType() {
        return new User(email, unhashedPassword, Integer.parseInt(role));
    }
}
