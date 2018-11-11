//@@author Meowzz95
package com.t13g2.forum.storage.forum;

import java.io.Serializable;

import com.t13g2.forum.model.forum.Comment;

/**
 * Wraps list of entities and keep track of if it is modified
 */
public class CommentStorage extends BaseEntityStorage<Comment> implements Serializable {
}
