package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents module in ForumBook
 */
public class Module extends BaseModel {
    public static final String MESSAGE_MODULE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MODULE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private String title;
    private String moduleCode;

    private List<ForumThread> forumThreads;

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
