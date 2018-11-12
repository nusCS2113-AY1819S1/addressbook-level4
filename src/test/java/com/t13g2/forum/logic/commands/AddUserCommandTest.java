package com.t13g2.forum.logic.commands;

import static org.junit.Assert.assertEquals;

import com.t13g2.forum.logic.commands.exceptions.CommandException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.ModelManager;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddUserCommandTest {
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
        new LoginCommand(null, null);
    }

    @Test
    public void execute_validUserNameAndValidUserPassword_loginSuccess() {
        //set the current logged in user as an admin.
        Context.getInstance().setCurrentUser(null);
        User validUser = TypicalUsers.JOSH;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getUserRepository().addUser(validUser);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginCommand loginCommand =
            new LoginCommand(validUser.getUsername(), validUser.getPassword());

        CommandTestUtil.assertCommandSuccess(loginCommand, model, commandHistory,
            String.format(String.format(LoginCommand.MESSAGE_SUCCESS, validUser.getUsername()), validUser.getUsername(),
                    validUser.getPassword()), expectedModel);
    }

    @Test
    public void execute_invalidUserNameAndValidUserPassword_loginFailed() throws Exception {

        String invalidUserName = "abcde";
        LoginCommand loginCommand = new LoginCommand(invalidUserName, "000");

        //thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(LoginCommand.MESSAGE_FAIL, invalidUserName));

        CommandResult commandResult = loginCommand.execute(model, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_FAIL,
            invalidUserName), commandResult.feedbackToUser);
    }


    @Test
    public void execute_userLoginWithNullUserName_loginFailed() throws Exception {
        //set the current logged in user as a user.

        User validUser = TypicalUsers.JOSH;
        thrown.expect(NullPointerException.class);
        LoginCommand loginCommand =
                new LoginCommand(null, validUser.getPassword());
    }

    @Test
    public void execute_userLoginWithNullUserPassword_loginFailed() throws Exception {
        //set the current logged in user as a user.

        User validUser = TypicalUsers.JOSH;
        thrown.expect(NullPointerException.class);
        LoginCommand loginCommand =
                new LoginCommand(validUser.getUsername(), null);
    }
}
