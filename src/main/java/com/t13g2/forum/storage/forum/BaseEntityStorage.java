package com.t13g2.forum.storage.forum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class BaseEntityStorage<T> implements Serializable {
    protected boolean dirty;
    protected List<T> list = new ArrayList<>();


    public List<T> getList() {
        return list;
    }

    public void setDirty() {
        dirty = true;
    }

}
