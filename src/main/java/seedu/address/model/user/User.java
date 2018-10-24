package seedu.address.model.user;

/**
 * This is a representation of a user.
 */
public class User {

    private String email;
    private String unhashedPassword;
    private int role;

    public User(String email, String unhashedPassword, int role) {
        this.email = email;
        this.unhashedPassword = unhashedPassword;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnhashedPassword() {
        return unhashedPassword;
    }

    public void setUnhashedPassword(String unhashedPassword) {
        this.unhashedPassword = unhashedPassword;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
