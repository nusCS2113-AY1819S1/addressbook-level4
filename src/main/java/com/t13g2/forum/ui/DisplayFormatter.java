package com.t13g2.forum.ui;

import java.util.List;

import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;


/**
 * To format the message of a threadList and commentList
 */
public class DisplayFormatter {

    private static String message;

    /**
     * return a string message of threadList to display
     * @param threadList
     * @return
     */
    public static String diplayThreadList (List<ForumThread> threadList) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            message = "";
            for (ForumThread thread : threadList) {
                message += "(By "
                        + unitOfWork.getUserRepository().getUser(thread.getCreatedByUserId()).getUsername()
                        + ")\n"
                        + thread.getId() + ": " + thread.getTitle() + "\n"
                        + "----------------------------------------------------------------------------\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * return a string message of commentList to display
     * @param commentList
     * @return
     */
    public static String displayCommentList (List<Comment> commentList) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            message = "";
            for (Comment comment : commentList) {
                message += "(By "
                        + unitOfWork.getUserRepository().getUser(comment.getCreatedByUserId()).getUsername()
                        + ")\n"
                        + comment.getId() + ": " + comment.getContent() + "\n"
                        + "----------------------------------------------------------------------------\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
