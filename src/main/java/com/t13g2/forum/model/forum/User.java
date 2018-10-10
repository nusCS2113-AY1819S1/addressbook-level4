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

    public boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean block) {
        isBlock = block;
    }
}
