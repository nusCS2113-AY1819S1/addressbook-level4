package com.t13g2.forum.logic.commands;

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
public class LogoutCommandTest {
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
    public void execute_userActive_logoutSuccess() {
        //set the current logged in user as an user.
        User loggedInUser = TypicalUsers.JOSH;
        Context.getInstance().setCurrentUser(loggedInUser);
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getUserRepository().addUser(loggedInUser);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogoutCommand logoutCommand = new LogoutCommand();

        CommandTestUtil.assertCommandSuccess(logoutCommand, model, commandHistory,
                String.format(LogoutCommand.MESSAGE_SUCCESS , loggedInUser.getUsername()), expectedModel);
    }

    @Test
    public void execute_invalidUserNameAndValidUserPassword_loginFailed() {
        //set the current logged in user as an user.
        Context.getInstance().setCurrentUser(null);

        LogoutCommand logoutCommand = new LogoutCommand();

        CommandTestUtil.assertCommandSuccess(logoutCommand, model, commandHistory,
                LogoutCommand.MESSAGE_FAIL, expectedModel);
    }
}
