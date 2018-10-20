package com.t13g2.forum.storage.forum;

import java.util.List;

import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;

/**
 *
 */
public class CommentRepository extends BaseRepository implements ICommentRepository {
    public CommentRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public int addComment(Comment comment) {
        forumBookStorage.getComments().getList().add(comment);
        forumBookStorage.getComments().setDirty();
        return comment.getId();
    }

    @Override
    public void deleteComment(Comment comment) {
        this.deleteComment(comment.getId());
    }

    @Override
    public void deleteComment(int commentId) {
        forumBookStorage.getComments().getList().removeIf(comment -> comment.getId() == commentId);
        forumBookStorage.getComments().setDirty();
    }

    @Override
    public Comment getComment(int commentId) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByThread(int threadId) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByThread(ForumThread forumThread) {
        return null;
    }
}
