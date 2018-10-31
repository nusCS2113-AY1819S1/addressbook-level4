package com.t13g2.forum.logic.commands;

import static junit.framework.TestCase.assertEquals;

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
 * integration test for AnnounceCommand.
 */
public class AnnounceCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void constructor_nullAnnouncement_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AnnounceCommand(null);
    }

    @Test
    public void execute_userNotLogin_announceFailed() throws Exception {
        //set the current logged in user as null.
        Context.getInstance().setCurrentUser(null);

        Announcement validAnnouncement = new AnnouncementBuilder().build();
        AnnounceCommand announceCommand = new AnnounceCommand(validAnnouncement);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_LOGIN);

        CommandResult commandResult = announceCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_userLoggedIn_announceFailed() throws Exception {
        //set the current logged in user as a user.
        User loginUser = TypicalUsers.JANEDOE;
        Context.getInstance().setCurrentUser(loginUser);

        Announcement validAnnouncement = new AnnouncementBuilder().build();
        AnnounceCommand announceCommand = new AnnounceCommand(validAnnouncement);

        thrown.expect(CommandException.class);
        thrown.expectMessage(User.MESSAGE_NOT_ADMIN);

        CommandResult commandResult = announceCommand.execute(model, commandHistory);
        assertEquals(User.MESSAGE_NOT_ADMIN, commandResult.feedbackToUser);
    }


    @Test
    public void execute_announcementAccepted_announceSuccessful() throws Exception {
        //set the current logged in user as an admin.
        User loginUser = new UserBuilder().build();
        Context.getInstance().setCurrentUser(loginUser);

        Announcement validAnnouncement = new AnnouncementBuilder().build();

        CommandResult commandResult = new AnnounceCommand(validAnnouncement).execute(model, commandHistory);

        assertEquals(String.format(AnnounceCommand.MESSAGE_SUCCESS, validAnnouncement), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }
}
