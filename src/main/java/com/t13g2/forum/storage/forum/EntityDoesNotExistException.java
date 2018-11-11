//@@author Meowzz95
package com.t13g2.forum.storage.forum;

/**
 * Defines an exception that should be thrown when the queried entity
 * does not exist
 */
public class EntityDoesNotExistException extends Exception {
    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
