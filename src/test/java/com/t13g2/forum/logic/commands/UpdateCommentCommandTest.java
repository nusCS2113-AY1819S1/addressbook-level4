package com.t13g2.forum.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.ModelManager;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.CommentBuilder;
import com.t13g2.forum.testutil.ForumThreadBuilder;
import com.t13g2.forum.testutil.TypicalModules;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;

//@@author HansKoh
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateCommentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getForumBook(), new UserPrefs());
    }

    @Test
    public void execute_userLoggedInUpdateComment_updateCommentSuccess() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        Module validModule = TypicalModules.CS1231;
        int moduleId = 0;
        int threadId = 0;
        int commentId = 0;

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getModuleRepository().addModule(validModule);
            unitOfWork.commit();
            moduleId = unitOfWork.getModuleRepository().getModuleByCode(validModule.getModuleCode()).getId();
            Context.getInstance().setCurrentModuleId(moduleId);
            ForumThread forumThread = new ForumThreadBuilder().withModuleId(moduleId).build();
            Context.getInstance().setCurrentThreadId(forumThread.getId());
            Comment comment = new CommentBuilder().withThreadId(forumThread.getId()).build();
            CreateThreadCommand createThreadCommand =
                    new CreateThreadCommand(validModule.getModuleCode(), forumThread, comment);
            createThreadCommand.execute(model, commandHistory);
            commentId = comment.getId();
            threadId = forumThread.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cContentToUpdate = "new comment content";
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand(commentId, cContentToUpdate);
        String updateMessage = "\n\n"
                + "Under Module Code: " + validModule.getModuleCode() + "\n"
                + "Under Thread ID: " + threadId + "\n"
                + "Updated Comment ID: " + commentId + "\n"
                + "Updated Comment Content: " + cContentToUpdate + "\n";
        CommandTestUtil.assertCommandSuccess(updateCommentCommand, model, commandHistory,
                String.format(updateCommentCommand.MESSAGE_SUCCESS, updateMessage), expectedModel);
    }

    @Test
    public void execute_userLoggedInNotCommentOwner_updateCommentFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        Module validModule = TypicalModules.CS1231;
        int moduleId = 0;
        int commentId = 0;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getModuleRepository().addModule(validModule);
            unitOfWork.commit();
            moduleId = unitOfWork.getModuleRepository().getModuleByCode(validModule.getModuleCode()).getId();
            Context.getInstance().setCurrentModuleId(moduleId);
            ForumThread forumThread = new ForumThreadBuilder().withModuleId(moduleId).build();
            Context.getInstance().setCurrentThreadId(forumThread.getId());
            Comment comment = new CommentBuilder().withThreadId(forumThread.getId()).build();
            CreateThreadCommand createThreadCommand =
                    new CreateThreadCommand(validModule.getModuleCode(), forumThread, comment);
            createThreadCommand.execute(model, commandHistory);
            commentId = comment.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Context.getInstance().setCurrentUser(TypicalUsers.JIM);
        String cContentToUpdate = "new comment content";
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand(commentId, cContentToUpdate);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NOT_COMMENT_OWNER);

        CommandResult commandResult = updateCommentCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_NOT_COMMENT_OWNER, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedInNotUnderCurrentScope_updateCommentFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        Module validModule = TypicalModules.CS1231;
        int moduleId = 0;
        int commentId = 0;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getModuleRepository().addModule(validModule);
            unitOfWork.commit();
            moduleId = unitOfWork.getModuleRepository().getModuleByCode(validModule.getModuleCode()).getId();
            Context.getInstance().setCurrentModuleId(validModule.getId());
            ForumThread forumThread = new ForumThreadBuilder().withModuleId(moduleId).build();
            Context.getInstance().setCurrentThreadId(8888);
            Comment comment = new CommentBuilder().withThreadId(forumThread.getId()).build();

            CreateThreadCommand createThreadCommand =
                    new CreateThreadCommand(validModule.getModuleCode(), forumThread, comment);
            createThreadCommand.execute(model, commandHistory);
            commentId = comment.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cContentToUpdate = "new comment content";
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand(commentId, cContentToUpdate);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_COMMENT);

        CommandResult commandResult = updateCommentCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_INVALID_COMMENT, commandResult.feedbackToUser);
    }

    @Test
    public void execute_notLoggedInDeleteComment_deleteCommentFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        Module validModule = TypicalModules.CS1231;
        ForumThread forumThread = new ForumThreadBuilder().withModuleId(validModule.getId()).build();
        Comment comment = new CommentBuilder().withThreadId(forumThread.getId()).build();
        String cContentToUpdate = "new comment content";
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand(comment.getThreadId(), cContentToUpdate);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = updateCommentCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }
}
