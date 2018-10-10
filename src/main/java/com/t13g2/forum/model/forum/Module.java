package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents module in ForumBook
 */
public class Module extends BaseModel {

    private String title;
    private String moduleCode;

    private List<ForumThread> forumThreads;

}
