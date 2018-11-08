package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents module in ForumBook
 */
public class Module extends BaseModel {
    public static final String MESSAGE_MODULE_CODE_CONSTRAINTS =
        "Invalid parameter: mCode should follow (2 or 3 capital letters)+(4 numbers)+(0 or 1 capital letter)"
            + "\nand it should not be blank. E.g. MA1580E, CS2113 or USP1000A, USP1000";
    public static final String MESSAGE_MODULE_TITLE_CONSTRAINTS =
        "Invalid parameter: mTitle should not be blank";
    public static final String MESSAGE_MODULE_ID_OUT_OF_BOUND = "mId is outside of Integer range!";
    public static final String MESSAGE_MODULE_ID_NEGATIVE = "mId should be in the range of 0 ~ INT_MAX";
    public static final String MODULE_CODE_VALIDATION_REGEX = "[A-Z]{2,3}[0-9]{4}[A-Z]{0,1}";
    public static final String MODULE_TITLE_VALIDATION_REGEX = "[^\\s].*";

    private String title;
    private String moduleCode;

    private List<ForumThread> forumThreads;

    public Module(String title, String moduleCode) {
        this.title = title;
        this.moduleCode = moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public List<ForumThread> getForumThreads() {
        return forumThreads;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

}
