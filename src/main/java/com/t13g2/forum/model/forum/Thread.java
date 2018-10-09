package com.t13g2.forum.model.forum;

import java.util.List;

public class Thread extends BaseModel{

    private int moduleId;
    private String title;

    private List<Comment> comments;

}
