package t13g2.forum.logic.commands;

import static org.junit.Assert.assertTrue;
import static t13g2.forum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static t13g2.forum.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import t13g2.forum.commons.events.ui.ShowHelpRequestEvent;
import t13g2.forum.logic.CommandHistory;
import t13g2.forum.model.Model;
import t13g2.forum.model.ModelManager;
import t13g2.forum.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        assertCommandSuccess(new HelpCommand(), model, commandHistory, SHOWING_HELP_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
