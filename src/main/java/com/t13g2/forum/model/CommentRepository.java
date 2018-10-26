//@@Meowzz95
package com.t13g2.forum.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.sun.istack.NotNull;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.IForumBookStorage;

/**
 *
 */
public class CommentRepository extends BaseRepository implements ICommentRepository {
    public CommentRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public int addComment(@NotNull Comment comment) {
        Objects.requireNonNull(comment, "comment can't be null");
        forumBookStorage.getComments().getList().add(comment);
        forumBookStorage.getComments().setDirty();
        return comment.getId();
    }

    @Override
    public void deleteComment(@NotNull Comment comment) {
        Objects.requireNonNull(comment, "comment can't be null");
        this.deleteComment(comment.getId());
    }

    @Override
    public void deleteComment(int commentId) {
        forumBookStorage.getComments().getList().removeIf(comment -> comment.getId() == commentId);
        forumBookStorage.getComments().setDirty();
    }

    @Override
    public Comment getComment(int commentId) throws EntityDoesNotExistException {
        return this.getById(forumBookStorage.getComments().getList(), commentId);
    }

    @Override
    public List<Comment> getCommentsByThread(int threadId) {
        return forumBookStorage.getComments().getList().stream()
            .filter(aComment -> aComment.getThreadId() == threadId).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getCommentsByThread(@NotNull ForumThread forumThread) {
        Objects.requireNonNull(forumThread, "forumThread can't be null");
        return this.getCommentsByThread(forumThread.getId());
    }
}
