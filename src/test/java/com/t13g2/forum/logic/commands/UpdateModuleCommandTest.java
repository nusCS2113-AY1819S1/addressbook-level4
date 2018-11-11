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
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.TypicalModules;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;
import com.t13g2.forum.testutil.UserBuilder;

//@@author xllx1
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateModuleCommandTest {
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
    public void execute_adminAndUpdateValidModuleId_updateModuleSuccess() {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        Module validModule = TypicalModules.CS1111;
        int moduleId = 0;
        String updatedTitle = "CS module";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            moduleId = unitOfWork.getModuleRepository().addModule(validModule);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateModuleCommand updateModuleCommand = new UpdateModuleCommand(moduleId, "", updatedTitle);

        CommandTestUtil.assertCommandSuccess(updateModuleCommand, model, commandHistory,
            String.format(UpdateModuleCommand.MESSAGE_SUCCESS, validModule.getModuleCode(),
                updatedTitle), expectedModel);
    }

    @Test
    public void execute_adminAndUpdateInvalidModuleId_updateModuleFailed() throws Exception {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        Module validModule = TypicalModules.GET1020;
        int invalidModuleId = 999999999;
        UpdateModuleCommand updateModuleCommand = new UpdateModuleCommand(invalidModuleId, "", "Darwin");

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(UpdateModuleCommand.MESSAGE_INVALID_MODULE_ID, invalidModuleId));

        CommandResult commandResult = updateModuleCommand.execute(model, commandHistory);
        assertEquals(String.format(UpdateModuleCommand.MESSAGE_INVALID_MODULE_ID,
            invalidModuleId), commandResult.feedbackToUser);
    }

    @Test
    public void execute_notLoggedIn_updateModuleFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        UpdateModuleCommand updateModuleCommand = new UpdateModuleCommand(1, "GET1020", "Darwin");

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = updateModuleCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedIn_createModuleFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        UpdateModuleCommand updateModuleCommand = new UpdateModuleCommand(1, "GET1002", "");

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_ADMIN);

        CommandResult commandResult = updateModuleCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_ADMIN, commandResult.feedbackToUser);
    }
}
