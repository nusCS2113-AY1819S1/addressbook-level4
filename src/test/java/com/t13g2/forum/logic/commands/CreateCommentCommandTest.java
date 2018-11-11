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
public class CreateCommentCommandTest {
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
    public void constructor_nullThread_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateThreadCommand(null, null, null);
    }

    @Test
    public void execute_userLoggedInCreateComment_createCommentSuccess() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        Module validModule = TypicalModules.CS1231;
        int moduleId = 0;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            moduleId = unitOfWork.getModuleRepository().getModuleByCode(validModule.getModuleCode()).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ForumThread forumThread = new ForumThreadBuilder().withModuleId(moduleId).build();
        Comment comment = new CommentBuilder().withThreadId(forumThread.getId()).build();

        CreateThreadCommand createThreadCommand =
                new CreateThreadCommand(validModule.getModuleCode(), forumThread, comment);
        createThreadCommand.execute(model, commandHistory);

        CreateCommentCommand createCommentCommand =
                new CreateCommentCommand(forumThread.getId(), comment.getContent());

        String message = "\n"
                + "Module ID: " + moduleId + "\n"
                + "Thread ID: " + forumThread.getId() + "\n"
                + "Comment ID: " + "79" + "\n"
                + "Comment Content: " + comment.getContent();

        CommandTestUtil.assertCommandSuccess(createCommentCommand, model, commandHistory,
                String.format(createCommentCommand.MESSAGE_SUCCESS, message), expectedModel);
    }

    @Test
    public void execute_notLoggedIn_createCommentFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        Module validModule = TypicalModules.CS1231;
        int moduleId = 0;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            moduleId = unitOfWork.getModuleRepository().addModule(validModule);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ForumThread forumThread = new ForumThreadBuilder().withModuleId(moduleId).build();
        Comment comment = new CommentBuilder().withThreadId(forumThread.getId()).build();
        CreateCommentCommand createCommentCommand =
                new CreateCommentCommand(forumThread.getId(), comment.getContent());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = createCommentCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedInInvalidThreadId_createCommentFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        int inValidThreadId = 8888;
        Comment comment = new CommentBuilder().build();
        CreateCommentCommand createCommentCommand =
                new CreateCommentCommand(inValidThreadId, comment.getContent());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_THREAD_ID);

        CommandResult commandResult = createCommentCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_INVALID_THREAD_ID, commandResult.feedbackToUser);
    }
}
