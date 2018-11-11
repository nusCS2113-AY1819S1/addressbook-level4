//@@author Meowzz95
package com.t13g2.forum.storage.forum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Wraps list of entities and keep track of if it is modified
 * @param <T>
 */
public abstract class BaseEntityStorage<T> implements Serializable {
    protected ArrayList<T> list = new ArrayList<>();
    private boolean dirty;

    /**
     * Gets the underlying list
     *
     * @return list of type T
     */
    public ArrayList<T> getList() {
        return list;
    }

    /**
     * Checks if this storage is modified since last load
     * @return
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets this storage's status to be 'modified'
     * @return
     */
    public void setDirty() {
        dirty = true;
    }

    /**
     * Sets this storage's status to be 'unmodified'
     * @return
     */
    public void setFresh() {
        dirty = false;
    }


}
