package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.User;

/**
 * A utility class to help with building User objects.
 */
public class UserBuilder {

    public static final String DEFAULT_USER_NAME = "testAdmin";
    public static final String DEFAULT_USER_PASSWORD = "123";
    public static final boolean DEFAULT_USER_IS_ADMIN = true;
    public static final boolean DEFAULT_USER_IS_BLOCK = false;
    public static final String DEFAULT_USER_EMAIL = "teatAdmin@test.com";
    public static final String DEFAULT_USER_PHONE = "98765432";

    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isBlock;
    private String email;
    private String phone;

    public UserBuilder() {
        this.username = DEFAULT_USER_NAME;
        this.password = DEFAULT_USER_PASSWORD;
        this.isAdmin = DEFAULT_USER_IS_ADMIN;
        this.isBlock = DEFAULT_USER_IS_BLOCK;
        this.email = DEFAULT_USER_EMAIL;
        this.phone = DEFAULT_USER_PHONE;
    }


    /**
     * Initializes the UserBuilder with the data of {@code userToCopy}.
     */
    public UserBuilder(User userToCopy) {
        this.username = userToCopy.getUsername();
        this.password = userToCopy.getPassword();
        this.isAdmin = userToCopy.isAdmin();
        this.isBlock = userToCopy.getIsBlock();
        this.email = userToCopy.getEmail();
        this.phone = userToCopy.getPhone();
    }

    /**
     * Sets the {@code name} of the {@code User} that we are building.
     */
    public UserBuilder withName(String name) {
        this.username = name;
        return this;
    }

    /**
     * Sets the {@code password} of the {@code User} that we are building.
     */
    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
    /**
     * Sets the {@code isAdmin} of the {@code User} that we are building.
     */
    public UserBuilder withIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
    /**
     * Sets the {@code isBlock} of the {@code User} that we are building.
     */
    public UserBuilder withIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
        return this;
    }
    /**
     * Sets the {@code email} of the {@code User} that we are building.
     */
    public UserBuilder withContent(String email) {
        this.email = email;
        return this;
    }
    /**
     * Sets the {@code phone} of the {@code User} that we are building.
     */
    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }


    public User build() {
        return new User(username, password, isAdmin, isBlock, email, phone);
    }

}
