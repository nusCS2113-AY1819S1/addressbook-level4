package com.t13g2.forum.logic.commands;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.ModelManager;
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.AnnouncementBuilder;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.testutil.TypicalUsers;
import com.t13g2.forum.testutil.UserBuilder;

//@@author xllx1
/**
 * integration test for CheckAnnounceCommand.
 */
public class CheckAnnounceCommandTest {
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
    public void execute_userLoggedIn_checkAnnounceSuccessful() throws Exception {
        //set the current logged in user as an admin.
        User admin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(admin);

        Announcement validAnnouncement = new AnnouncementBuilder().build();
        CommandResult commandResult = new AnnounceCommand(validAnnouncement).execute(model, commandHistory);
        assertEquals(String.format(AnnounceCommand.MESSAGE_SUCCESS, validAnnouncement),
            commandResult.feedbackToUser);

        //set the current logged in user as a user.
        User loginUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(loginUser);

        String expectedMessage = String.format(CheckAnnouncmentCommand.MESSAGE_SUCCESS, validAnnouncement);
        CommandTestUtil.assertCommandSuccess(new CheckAnnouncmentCommand(), model, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_userNotLogin_checkAnnounceFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);
        CheckAnnouncmentCommand checkAnnouncmentCommand = new CheckAnnouncmentCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = checkAnnouncmentCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }
}
