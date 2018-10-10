package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents module in ForumBook
 */
public class Module extends BaseModel {

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
