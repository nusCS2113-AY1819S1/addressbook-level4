//@@Meowzz95
package com.t13g2.forum.commons.util;

import java.util.List;

import com.t13g2.forum.model.forum.BaseModel;

/**
 * This class provides utility methods
 */
public class Extensions {
    /**
     * Utility methods to update a object in a list
     *
     * @param list
     * @param object
     * @param <T>
     */
    public static <T extends BaseModel> void updateObjectInList(List<T> list, T object) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == object.getId()) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            list.set(index, object);
        }
    }
}
