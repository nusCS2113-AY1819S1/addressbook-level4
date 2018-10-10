package com.t13g2.forum.model.forum;

import java.util.List;

/**
 * Represents thread under module in ForumBook
 */
public class Thread extends BaseModel {

    private int moduleId;
    private String title;

    private List<Comment> comments;

}
