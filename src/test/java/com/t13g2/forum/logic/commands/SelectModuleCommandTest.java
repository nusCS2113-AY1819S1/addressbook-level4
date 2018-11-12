package com.t13g2.forum.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.t13g2.forum.commons.core.Messages;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.logic.util.DisplayFormatter;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.ModelManager;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.TypicalModules;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;

//@@author HansKoh
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectModuleCommandTest {
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
    public void execute_userLoggedInSelectModule_selectModuleSuccess() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        Module validModule = TypicalModules.CS2113;
        String message = "";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getModuleRepository().addModule(validModule);
            unitOfWork.commit();
            Module module = unitOfWork.getModuleRepository().getModuleByCode(validModule.getModuleCode());
            List<ForumThread> threadList = unitOfWork.getForumThreadRepository().getThreadsByModule(module);
            message = DisplayFormatter.diplayThreadList(threadList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String messageSuccess = "Listed all threads under Module Code: " + validModule.getModuleCode() + "\n"
                + "****************************************************************************\n"
                + "****************************************************************************\n"
                + "%s";

        SelectModuleCommand selectModuleCommand = new SelectModuleCommand(validModule.getModuleCode());

        CommandTestUtil.assertCommandSuccess(selectModuleCommand, model, commandHistory,
                String.format(messageSuccess, message), expectedModel);

    }

    @Test
    public void execute_userLoggedInInvalidModule_selectModuleFailed() throws Exception {
        //set the current logged in user as a user.
        User validUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(validUser);

        String invalidModule = "AB1234";
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_MODULE_CODE);

        SelectModuleCommand selectModuleCommand = new SelectModuleCommand(invalidModule);

        CommandResult commandResult = selectModuleCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_INVALID_MODULE_CODE, commandResult.feedbackToUser);
    }

    @Test
    public void execute_notLoggedInSelectModule_selectModuleFail() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        Module validModule = TypicalModules.CS2113;
        SelectModuleCommand selectModuleCommand = new SelectModuleCommand(validModule.getModuleCode());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = selectModuleCommand.execute(model, commandHistory);
        assertEquals(Messages.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }
}
