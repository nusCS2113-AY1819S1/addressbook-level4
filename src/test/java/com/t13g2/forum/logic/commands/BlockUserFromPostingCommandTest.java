package com.t13g2.forum.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.ModelManager;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;
import com.t13g2.forum.testutil.UserBuilder;

//@@author xllx1
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlockUserFromPostingCommandTest {
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
    public void execute_adminAndDuplicateUnblock_blockUserFailed () throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User toBlock = TypicalUsers.JIM;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getUserRepository().addUser(toBlock);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BlockUserFromCreatingCommand blockCommand =
            new BlockUserFromCreatingCommand(toBlock.getUsername(), false);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(BlockUserFromCreatingCommand.MESSAGE_DUPLICATE_UNBLOCK,
            toBlock.getUsername()));

        CommandResult commandResult = blockCommand.execute(model, commandHistory);
        assertEquals(String.format(BlockUserFromCreatingCommand.MESSAGE_DUPLICATE_UNBLOCK,
            toBlock.getUsername()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_adminAndValidUserName_blockUserSuccess () {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User toBlock = TypicalUsers.JIM;
        BlockUserFromCreatingCommand blockCommand = new BlockUserFromCreatingCommand(toBlock.getUsername(), true);

        CommandTestUtil.assertCommandSuccess(blockCommand, model, commandHistory,
            String.format(BlockUserFromCreatingCommand.MESSAGE_SUCCESS, "blocked", toBlock.getUsername()),
            expectedModel);
    }

    @Test
    public void execute_adminDuplicateBlock_blockUserFailed () throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User toBlock = TypicalUsers.JIM;
        BlockUserFromCreatingCommand blockCommand =
            new BlockUserFromCreatingCommand(toBlock.getUsername(), true);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(BlockUserFromCreatingCommand.MESSAGE_DUPLICATE_UNBLOCK,
            toBlock.getUsername()));

        CommandResult commandResult = blockCommand.execute(model, commandHistory);
        assertEquals(String.format(BlockUserFromCreatingCommand.MESSAGE_DUPLICATE_UNBLOCK,
            toBlock.getUsername()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_adminInvalidUserName_blockUserFailed () throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        BlockUserFromCreatingCommand blockCommand = new BlockUserFromCreatingCommand("abcd", true);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(BlockUserFromCreatingCommand.MESSAGE_INVALID_USER, "abcd"));

        CommandResult commandResult = blockCommand.execute(model, commandHistory);
        assertEquals(String.format(BlockUserFromCreatingCommand.MESSAGE_INVALID_USER, "abcd"),
            commandResult.feedbackToUser);
    }

    @Test
    public void execute_userNotLogin_blockUserFailed () throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        User validUser = TypicalUsers.JIM;
        BlockUserFromCreatingCommand blockCommand = new BlockUserFromCreatingCommand(validUser.getUsername(), true);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = blockCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedIn_blockUserFailed () throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JIM;
        Context.getInstance().setCurrentUser(validUser);

        BlockUserFromCreatingCommand blockCommand =
            new BlockUserFromCreatingCommand(TypicalUsers.JANEDOE.getUsername(), true);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_ADMIN);

        CommandResult commandResult = blockCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_ADMIN, commandResult.feedbackToUser);
    }
}
