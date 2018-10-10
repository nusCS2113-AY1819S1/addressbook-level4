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

    //TODO: set from storage
    public boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean block) {
        isBlock = block;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" User ID: ")
            // .append(id)
            .append(" User Name: ")
            .append(username);
        return builder.toString();
    }
}
