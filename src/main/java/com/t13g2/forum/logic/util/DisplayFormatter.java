package com.t13g2.forum.logic.util;

import java.util.List;

import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;


//@@author HansKoh
/**
 * To format the message of a threadList and commentList
 */
public class DisplayFormatter {

    private static String message;
    private static int index;

    /**
     *
     * @param moduleList
     * @return
     */
    public static String displayModuleList (List<Module> moduleList) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            message = "";
            for (Module module : moduleList) {
                message += module.getModuleCode() + ": " + module.getTitle() + "\n";
                message += "----------------------------------------------------------------------------------\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * return a string message of threadList to display
     * @param threadList
     * @return
     */
    public static String diplayThreadList (List<ForumThread> threadList) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            message = "";
            for (index = threadList.size() - 1; index >= 0; index--) {
                message += "Thread ID    : " + threadList.get(index).getId() + "\n"
                        + "Thread Title: " + threadList.get(index).getTitle() + "\n"
                        + "Created by  : " + unitOfWork.getUserRepository().getUser((threadList.get(index)
                        .getCreatedByUserId())).getUsername() + "\n"
                        + "----------------------------------------------------------------------------------\n";
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
            for (index = 0; index < commentList.size(); index++) {
                message += "Comment ID: " + commentList.get(index).getId() + "\n"
                        + "Content       : " + commentList.get(index).getContent() + "\n"
                        + "Created by  : " + unitOfWork.getUserRepository().getUser((commentList.get(index)
                        .getCreatedByUserId())).getUsername() + "\n"
                        + "----------------------------------------------------------------------------------\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
