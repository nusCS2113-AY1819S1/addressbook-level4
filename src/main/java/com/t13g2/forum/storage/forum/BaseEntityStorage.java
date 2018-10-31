//@@Meowzz95
package com.t13g2.forum.storage.forum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @param <T>
 */
public abstract class BaseEntityStorage<T> implements Serializable {
    protected ArrayList<T> list = new ArrayList<>();
    private boolean dirty;


    public ArrayList<T> getList() {
        return list;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        dirty = true;
    }


}
