package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents thread under module in ForumBook
 */
public class ForumThread extends BaseModel {
    private int moduleId;
    private String title;

    // Identity Field
    private List<Comment> comments;

    public int getModuleId() {
        return moduleId;
    }

    public String getTitle() {
        return title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
