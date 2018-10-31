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
public class SetAdminCommandTest {
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
    public void execute_adminAndDuplicateRevert_setAdminFailed() throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User toSet = TypicalUsers.JONE;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getUserRepository().addUser(toSet);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SetAdminCommand setAdminCommand = new SetAdminCommand(toSet.getUsername(), false);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(SetAdminCommand.MESSAGE_DUPLICATE_REVERT, toSet.getUsername()));

        CommandResult commandResult = setAdminCommand.execute(model, commandHistory);
        assertEquals(String.format(SetAdminCommand.MESSAGE_DUPLICATE_REVERT,
            toSet.getUsername()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_adminAndLoggedIn_setAdminSuccess() {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User toSet = TypicalUsers.JONE;
        SetAdminCommand setAdminCommand = new SetAdminCommand(toSet.getUsername(), true);

        CommandTestUtil.assertCommandSuccess(setAdminCommand, model, commandHistory,
            String.format(SetAdminCommand.MESSAGE_SUCCESS, toSet.getUsername(), "an admin"), expectedModel);
    }

    @Test
    public void execute_adminDuplicateSet_setAdminFailed() throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User toSet = TypicalUsers.JONE;
        SetAdminCommand setAdminCommand = new SetAdminCommand(toSet.getUsername(), true);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(SetAdminCommand.MESSAGE_DUPLICATE_SET, toSet.getUsername()));

        CommandResult commandResult = setAdminCommand.execute(model, commandHistory);
        assertEquals(String.format(SetAdminCommand.MESSAGE_DUPLICATE_SET,
            toSet.getUsername()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_adminInvalidUserName_setAdminFailed() throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        SetAdminCommand setAdminCommand = new SetAdminCommand("abcd", true);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(BlockUserFromCreatingCommand.MESSAGE_INVALID_USER, "abcd"));

        CommandResult commandResult = setAdminCommand.execute(model, commandHistory);
        assertEquals(String.format(SetAdminCommand.MESSAGE_INVALID_USER, "abcd"),
            commandResult.feedbackToUser);
    }

    @Test
    public void execute_notLoggedIn_setAdminFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        User validUser = TypicalUsers.JONE;
        SetAdminCommand setAdminCommand = new SetAdminCommand(validUser.getUsername(), false);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = setAdminCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedIn_setAdminFailed() throws Exception {
        //set the current logged in user as null.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        SetAdminCommand setAdminCommand = new SetAdminCommand(validUser.getUsername(), false);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_ADMIN);

        CommandResult commandResult = setAdminCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_ADMIN, commandResult.feedbackToUser);
    }
}
