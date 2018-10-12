package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents thread under module in ForumBook
 */
public class ForumThread extends BaseModel {
    public static final String MESSAGE_THREAD_CONSTRAINTS =
            "Thread should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String THREAD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_THREAD_ID_CONSTRAINTS =
            "Thread ID should only contain an integer, and it should not be blank";
    public static final String THREAD_ID_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

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
