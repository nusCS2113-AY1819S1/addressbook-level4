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
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.TypicalModules;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;
import com.t13g2.forum.testutil.UserBuilder;

//@@author xllx1
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateModuleCommandTest {
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
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateModuleCommand(null);
    }

    @Test
    public void execute_adminLoggedInCreateModule_createModuleSuccess() {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        Module validModule = TypicalModules.GET1020;
        CreateModuleCommand createModuleCommand = new CreateModuleCommand(validModule);

        CommandTestUtil.assertCommandSuccess(createModuleCommand, model, commandHistory,
            String.format(CreateModuleCommand.MESSAGE_SUCCESS, validModule.getModuleCode()), expectedModel);
    }

    @Test
    public void execute_adminLoggedInDuplicateModule_createModuleFailed() throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        Module validModule = TypicalModules.GET1020;
        CreateModuleCommand createModuleCommand = new CreateModuleCommand(validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(CreateModuleCommand.MESSAGE_DUPLICATE_MODULE, validModule.getModuleCode()));

        CommandResult commandResult = createModuleCommand.execute(model, commandHistory);
        assertEquals(String.format(createModuleCommand.MESSAGE_DUPLICATE_MODULE,
            validModule.getModuleCode()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_notLoggedIn_createModuleFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        Module validModule = TypicalModules.GET1020;
        CreateModuleCommand createModuleCommand = new CreateModuleCommand(validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = createModuleCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedIn_createModuleFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        Module validModule = TypicalModules.GET1020;
        CreateModuleCommand createModuleCommand = new CreateModuleCommand(validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_ADMIN);

        CommandResult commandResult = createModuleCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_ADMIN, commandResult.feedbackToUser);
    }
}
