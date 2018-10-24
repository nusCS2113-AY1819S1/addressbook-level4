//@@Meowzz95
package com.t13g2.forum.storage.forum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class BaseEntityStorage<T> implements Serializable {
    protected List<T> list = new ArrayList<>();
    private boolean dirty;


    public List<T> getList() {
        return list;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        dirty = true;
    }


}
