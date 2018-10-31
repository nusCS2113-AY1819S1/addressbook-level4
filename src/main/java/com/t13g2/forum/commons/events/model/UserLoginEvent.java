package com.t13g2.forum.commons.events.model;

import com.t13g2.forum.commons.events.BaseEvent;

//@@author xllx1
/** Indicates the user has logged in*/
public class UserLoginEvent extends BaseEvent {

    public final String userName;
    public final boolean userType;
    public final boolean isBlocked;

    public UserLoginEvent(String uName, boolean uType, boolean blocked) {
        this.userName = uName;
        this.userType = uType;
        this.isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "User logged in, user name: " + userName + ", isBlocked: " + isBlocked;
    }
}
