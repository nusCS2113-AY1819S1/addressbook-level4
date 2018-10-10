package com.t13g2.forum.model.forum;

/**
 * Represents user in ForumBook
 */
public class User extends BaseModel {
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isBlock;
    private String email;
    private String phone;

    public static final String MESSAGE_USER_NAME_CONSTRAINTS =
        "User name can take any values, and it should not be blank";

    /**
     * The first character of the user name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USER_NAME_VALIDATION_REGEX = "[^\\s].*";

    public User() {
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidUserName(String testUserName) {
        return (testUserName.matches(USER_NAME_VALIDATION_REGEX));
    }

    public boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean block) {
        isBlock = block;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" User ID: ")
            .append(id)
            .append(" User Name: ")
            .append(username);
        return builder.toString();
    }
}
