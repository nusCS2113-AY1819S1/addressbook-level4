package com.t13g2.forum.commons.events.model;

import com.t13g2.forum.commons.events.BaseEvent;

//@@xllx1
/** Indicates the user has logged in*/
public class UserLoginEvent extends BaseEvent {

    public final String userName;
    public final boolean userType;

    public UserLoginEvent(String uName, boolean uType) {
        this.userName = uName;
        this.userType = uType;
    }

    @Override
    public String toString() {
        return "User logged in, user name: " + userName;
    }
}
