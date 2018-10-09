package com.t13g2.forum.model.forum;

import java.util.Date;

public class BaseModel {
    private int id;
    private Date created = new Date();
    private Date modified;
    private int createdByUserId;
    private int modifiedByUserId;


}
