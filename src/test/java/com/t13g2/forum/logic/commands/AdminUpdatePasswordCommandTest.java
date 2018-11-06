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
public class AdminUpdatePasswordCommandTest {
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
    public void constructor_nullModuleCode_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AdminUpdatePasswordCommand(null, null);
    }

    @Test
    public void execute_adminLoggedInAndValidUserName_updateSuccess() {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        User validUser = TypicalUsers.JOSH;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getUserRepository().addUser(validUser);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdminUpdatePasswordCommand adminUpdatePasswordCommand =
            new AdminUpdatePasswordCommand(validUser.getUsername(), "000");

        CommandTestUtil.assertCommandSuccess(adminUpdatePasswordCommand, model, commandHistory,
            String.format(AdminUpdatePasswordCommand.MESSAGE_SUCCESS, validUser.getUsername(),
                "000"), expectedModel);
    }

    @Test
    public void execute_adminLoggedInInvalidUserName_updateFailed() throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        String invalidUserName = "abcde";
        AdminUpdatePasswordCommand adminUpdatePasswordCommand = new AdminUpdatePasswordCommand(invalidUserName, "000");

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AdminUpdatePasswordCommand.MESSAGE_INVALID_USER, invalidUserName));

        CommandResult commandResult = adminUpdatePasswordCommand.execute(model, commandHistory);
        assertEquals(String.format(AdminUpdatePasswordCommand.MESSAGE_INVALID_USER,
            invalidUserName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_notLoggedIn_updateFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        User validUser = TypicalUsers.JOSH;
        AdminUpdatePasswordCommand adminUpdatePasswordCommand =
            new AdminUpdatePasswordCommand(validUser.getUsername(), "000");

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = adminUpdatePasswordCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedIn_updateFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        User validUserToUpdate = TypicalUsers.JOSH;
        AdminUpdatePasswordCommand adminUpdatePasswordCommand =
            new AdminUpdatePasswordCommand(validUserToUpdate.getUsername(), "000");

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_ADMIN);

        CommandResult commandResult = adminUpdatePasswordCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_ADMIN, commandResult.feedbackToUser);
    }
}
