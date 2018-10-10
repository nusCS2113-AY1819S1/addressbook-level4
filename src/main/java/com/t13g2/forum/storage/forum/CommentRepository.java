package com.t13g2.forum.storage.forum;

import java.util.List;

import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;

public class CommentRepository extends BaseRepository implements ICommentRepository {
    public CommentRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public int addComment(Comment comment) {
        return 0;
    }

    @Override
    public void deleteComment(Comment comment) {

    }

    @Override
    public void deleteComment(int commentId) {

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
