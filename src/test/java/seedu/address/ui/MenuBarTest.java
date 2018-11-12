package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static seedu.address.ui.MainWindow.COMMAND_USAGE;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import org.testfx.api.FxToolkit;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StageHandle;

import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExpensesCommand;
import seedu.address.logic.commands.AddLeavesCommand;
import seedu.address.logic.commands.AddRecruitmentPostCommand;
import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.logic.commands.AddWorksCommand;
import seedu.address.logic.commands.CalculateLeavesCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearExpensesCommand;
import seedu.address.logic.commands.ClearRecruitmentPostCommand;
import seedu.address.logic.commands.ClearScheduleCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeavesCommand;
import seedu.address.logic.commands.DeleteRecruitmentPostCommand;
import seedu.address.logic.commands.DeleteScheduleCommand;
import seedu.address.logic.commands.DeleteWorksCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditRecruitmentPostCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyPayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveExpensesCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectExpensesCommand;
import seedu.address.logic.commands.SelectRecruitmentPostCommand;
import seedu.address.logic.commands.SelectScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MenuBarTest extends GuiUnitTest {

    private MainWindow mainWindow;
    private EmptyMainWindowHandle mainWindowHandle;
    private ResultDisplayHandle resultDisplayHandle;
    private Stage stage;
    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() throws Exception {
        //main window
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            mainWindowHandle = new EmptyMainWindowHandle(stage);

            stage.setScene(mainWindow.getRoot().getScene());
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();

        //command box
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);

        //result display
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
    }

    @Test
    public void getCurrentGuiSetting_validGuiSetting_correctGuiSetting() {
        assertEquals(mainWindow.getCurrentGuiSetting(), new GuiSettings(stage.getWidth(), stage.getHeight(),
                (int) stage.getX(), (int) stage.getY()));
    }

    @Test
    public void menuBarRaisedEvent_isClicked_correctDisplay() {
        guiRobot.interact(mainWindow::handleAdd);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(AddCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleEdit);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + EditCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(EditCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleSelect);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(SelectCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleClear);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(ClearCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleList);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ListCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(ListCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleDelete);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(DeleteCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleFind);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + FindCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(FindCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleFilter);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + FilterCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(FilterCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleModifyPay);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ModifyPayCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(ModifyPayCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleAllModifyPay);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ModifyAllPayCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(ModifyAllPayCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleAddSchedule);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(AddScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleDeleteSchedule);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(DeleteScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleAddWorks);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddWorksCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleDeleteWorks);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteWorksCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleAddLeaves);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddLeavesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleDeleteLeaves);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteLeavesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleCalculateLeaves);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + CalculateLeavesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleSelectSchedule);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleClearSchedules);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        guiRobot.interact(mainWindow::handleHistory);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + HistoryCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(HistoryCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleUndo);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + UndoCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(UndoCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleRedo);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + RedoCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(RedoCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleAddExpenses);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(AddExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleDeleteExpenses);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + RemoveExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(RemoveExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleClearExpenses);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(ClearExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleSelectExpenses);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(SelectExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleAddRecruitmentPost);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddRecruitmentPostCommand.MESSAGE_USAGE2, resultDisplayHandle.getText());
        assertEquals(AddRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleDeleteRecruitmentPost);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteRecruitmentPostCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(DeleteRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleSelectRecruitment);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectRecruitmentPostCommand.MESSAGE_USAGE,
                resultDisplayHandle.getText());
        assertEquals(SelectRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleEditRecruitment);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + EditRecruitmentPostCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(EditRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        guiRobot.interact(mainWindow::handleClearRecruitment);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearRecruitmentPostCommand.MESSAGE_USAGE, resultDisplayHandle.getText());
        assertEquals(ClearRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());
    }



    @Test
    public void hide_validGuiSetting_correctGuiSetting() {
        guiRobot.interact(mainWindow::hide);
        assertFalse(stage.isIconified());
    }

    @Test
    public void getScheduleListPanel_noScheduleListPanel_null() {
        assertEquals(null, mainWindow.getScheduleListPanel());
    }

    @Test
    public void getPersonListPanel_noPersonListPanel_null() {
        assertEquals(null, mainWindow.getPersonListPanel());
    }

    @Test
    public void getExpensesListPanel_noExpensesListPanel_null() {
        assertEquals(null, mainWindow.getExpensesListPanel());
    }

    /**
     * A handle for an empty {@code MainWindow}. The components in {@code MainWindow} are not initialized.
     */
    public class EmptyMainWindowHandle extends StageHandle {

        private EmptyMainWindowHandle(Stage stage) {
            super(stage);
        }

    }
}
