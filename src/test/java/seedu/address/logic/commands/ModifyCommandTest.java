package seedu.address.logic.commands;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GENDER;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_HEIGHT;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DIFFICULTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_WEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_GENDER;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_HEIGHT;
//import static seedu.address.commons.core.Messages.MESSAGE_VALID_DIFFICULTY;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_WEIGHT;
/*import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CALORIES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION;*/
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEIGHT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT;
import static seedu.address.logic.commands.CommandTestUtil.assertModifyCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ModifyCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProfileWindowManager;

public class ModifyCommandTest {
    private static final String MESSAGE_MODIFY_USERPROFILE_SUCCESS = "User profile has been modified!";

    private static String currentGender;
    private static String currentUsername;
    private static String currentHeight;
    private static String currentWeight;
    private static String currentDifficulty;
    private static String currentCalories;
    private static String currentDuration;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ProfileWindowManager profileWindowManager;
    private String fileName;
    private Document doc;
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    @SuppressWarnings("Duplicates")
    public void setUp() throws IOException {
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divGender = doc.getElementById("gender");
        Element divUsername = doc.getElementById("username");
        Element divHeight = doc.getElementById("height");
        Element divWeight = doc.getElementById("weight");
        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        currentGender = divGender.ownText();
        currentUsername = divUsername.ownText();
        currentHeight = divHeight.ownText();
        currentWeight = divWeight.ownText();
        currentDifficulty = divDifficulty.ownText();
        currentCalories = divCalories.ownText();
        currentDuration = divDuration.ownText();
    }

    @Test
    public void constructor_nullRecommendArguments_throwsNullPointerException() throws ParseException {
        thrown.expect(NullPointerException.class);
        new ModifyCommandParser().parse(null);
    }

    @Test
    public void execute_singleField_success() throws IOException, ParseException {
        ArrayList<String> expectedAttributes = new ArrayList<>();
        ArrayList<String> actualAttributes = new ArrayList<>();
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        //execute the command
        Element divGender = doc.getElementById("gender");
        divGender.text(VALID_GENDER);
        String expectedSuccessMessage = MESSAGE_MODIFY_USERPROFILE_SUCCESS;

        expectedAttributes.add(VALID_GENDER);
        actualAttributes.add(divGender.ownText());
        //valid gender
        String commandGender = " " + PREFIX_GENDER + VALID_GENDER;
        assertModifyCommandSuccess(modifyCommandParser.parse(commandGender), actualAttributes, model,
                commandHistory, expectedSuccessMessage, expectedAttributes);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void execute_singleFieldHeight_failure() throws ParseException {
        String expectedFailureMessage = String.format(MESSAGE_INVALID_HEIGHT, MESSAGE_VALID_HEIGHT);
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        //invalid height
        String commandInvalidHeight = " " + PREFIX_HEIGHT + INVALID_HEIGHT;

        thrown.expectMessage(expectedFailureMessage);
        modifyCommandParser.parse(commandInvalidHeight);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void execute_singleFieldWeight_failure() throws ParseException {
        String expectedFailureMessage = String.format(MESSAGE_INVALID_WEIGHT, MESSAGE_VALID_WEIGHT);
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        //invalid weight
        String commandInvalidWeight = " " + PREFIX_WEIGHT + INVALID_WEIGHT;
        thrown.expectMessage(expectedFailureMessage);
        modifyCommandParser.parse(commandInvalidWeight);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void execute_singleFieldGender_failure() throws ParseException {
        String expectedFailureMessage = String.format(MESSAGE_INVALID_GENDER, MESSAGE_VALID_GENDER);
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        //invalid weight
        String commandInvalidGender = " " + PREFIX_GENDER + INVALID_GENDER;
        thrown.expectMessage(expectedFailureMessage);
        modifyCommandParser.parse(commandInvalidGender);
    }

    @Test
    public void execute_multipleField_success() throws IOException, ParseException {
        ArrayList<String> expectedAttributes = new ArrayList<>();
        ArrayList<String> actualAttributes = new ArrayList<>();
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");
        Element divGender = doc.getElementById("gender");
        Element divUsername = doc.getElementById("username");
        Element divHeight = doc.getElementById("height");
        Element divWeight = doc.getElementById("weight");
        Element divCalories = doc.getElementById("calories");

        divGender.text(VALID_GENDER);
        divHeight.text(VALID_HEIGHT);
        divWeight.text(VALID_WEIGHT);
        divUsername.text(VALID_USERNAME);
        divCalories.text(VALID_CALORIES);

        //expected attributes
        expectedAttributes.add(VALID_CALORIES);
        expectedAttributes.add(VALID_GENDER);
        expectedAttributes.add(VALID_HEIGHT);
        expectedAttributes.add(VALID_USERNAME);
        expectedAttributes.add(VALID_WEIGHT);

        //actual attributes
        actualAttributes.add(profileWindowManager.trimmedCalories(divCalories.ownText()));
        actualAttributes.add(profileWindowManager.trimmedGender(divGender.ownText()));
        actualAttributes.add(profileWindowManager.extractHeight(divHeight.ownText()));
        actualAttributes.add(profileWindowManager.trimmedUsername(divUsername.ownText()));
        actualAttributes.add(profileWindowManager.extractWeight(divWeight.ownText()));

        String command = " " + PREFIX_GENDER + VALID_GENDER + " " + PREFIX_CALORIES + VALID_CALORIES + " "
                + PREFIX_HEIGHT + VALID_HEIGHT + " " + PREFIX_WEIGHT + VALID_WEIGHT + " " + PREFIX_USERNAME
                + VALID_USERNAME;

        String expectedSuccessMessage = MESSAGE_MODIFY_USERPROFILE_SUCCESS;
        assertModifyCommandSuccess(modifyCommandParser.parse(command), actualAttributes, model, commandHistory,
                expectedSuccessMessage, expectedAttributes);
    }

    @Test
    public void execute_multipleField_failure() throws ParseException {
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        String command = " " + PREFIX_GENDER + VALID_GENDER + " " + PREFIX_CALORIES + VALID_CALORIES + " "
                + PREFIX_WEIGHT + VALID_WEIGHT + " " + PREFIX_USERNAME + INVALID_USERNAME;
        String expectedFailureMessage = String.format(MESSAGE_INVALID_USERNAME, MESSAGE_VALID_USERNAME);

        thrown.expectMessage(expectedFailureMessage);
        modifyCommandParser.parse(command);
    }

    @Test
    public void execute_allField_success() throws IOException, ParseException {
        ArrayList<String> expectedAttributes = new ArrayList<>();
        ArrayList<String> actualAttributes = new ArrayList<>();
        ModifyCommandParser modifyCommandParser = new ModifyCommandParser();

        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divGender = doc.getElementById("gender");
        Element divUsername = doc.getElementById("username");
        Element divHeight = doc.getElementById("height");
        Element divWeight = doc.getElementById("weight");
        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        divGender.text(VALID_GENDER);
        divHeight.text(VALID_HEIGHT);
        divWeight.text(VALID_WEIGHT);
        divUsername.text(VALID_USERNAME);
        divDifficulty.text(VALID_DIFFICULTY);
        divCalories.text(VALID_CALORIES);
        divDuration.text(VALID_DURATION);

        //expected attributes
        expectedAttributes.add(VALID_CALORIES);
        expectedAttributes.add(VALID_DIFFICULTY);
        expectedAttributes.add(VALID_DURATION);
        expectedAttributes.add(VALID_GENDER);
        expectedAttributes.add(VALID_HEIGHT);
        expectedAttributes.add(VALID_USERNAME);
        expectedAttributes.add(VALID_WEIGHT);

        //actual attributes
        actualAttributes.add(profileWindowManager.trimmedCalories(divCalories.ownText()));
        actualAttributes.add(profileWindowManager.trimmedDifficulty(divDifficulty.ownText()));
        actualAttributes.add(profileWindowManager.trimmedDuration(divDuration.ownText()));
        actualAttributes.add(profileWindowManager.trimmedGender(divGender.ownText()));
        actualAttributes.add(profileWindowManager.extractHeight(divHeight.ownText()));
        actualAttributes.add(profileWindowManager.trimmedUsername(divUsername.ownText()));
        actualAttributes.add(profileWindowManager.extractWeight(divWeight.ownText()));

        String command = " " + PREFIX_GENDER + VALID_GENDER + " " + PREFIX_CALORIES + VALID_CALORIES + " "
                + PREFIX_DIFFICULTY + VALID_DIFFICULTY + " " + PREFIX_DURATION + VALID_DURATION + " "
                + PREFIX_HEIGHT + VALID_HEIGHT + " " + PREFIX_WEIGHT + VALID_WEIGHT + " " + PREFIX_USERNAME
                + VALID_USERNAME;

        String expectedSuccessMessage = MESSAGE_MODIFY_USERPROFILE_SUCCESS;
        assertModifyCommandSuccess(modifyCommandParser.parse(command), actualAttributes, model, commandHistory,
                expectedSuccessMessage, expectedAttributes);
    }

    @Test
    public void equals() {
        String gender = "male";
        String username = "John";
        String calories = "150";
        String duration = "20m";
        String height = "1.71";
        String weight = "77.5";
        String difficulty = "advanced";
        ModifyCommand modifyCommand = new ModifyCommand(gender, username, height, weight, calories, difficulty,
                duration);

        // same values -> returns true
        ModifyCommand commandWithSameValues = new ModifyCommand(gender, username, height, weight, calories, difficulty,
                duration);
        assertTrue(modifyCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(modifyCommand.equals(modifyCommand));

        // null -> returns false
        assertFalse(modifyCommand.equals(null));

        // different types -> returns false
        assertFalse(modifyCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        difficulty = "beginner";
        ModifyCommand commandWithDifferentValues = new ModifyCommand(gender, username, height, weight, calories,
                difficulty, duration);
        assertFalse(modifyCommand.equals(commandWithDifferentValues));
    }


    @After
    @SuppressWarnings("Duplicates")
    public void revert() throws IOException {
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divGender = doc.getElementById("gender");
        Element divUsername = doc.getElementById("username");
        Element divHeight = doc.getElementById("height");
        Element divWeight = doc.getElementById("weight");
        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        divGender.text(currentGender);
        divHeight.text(currentHeight);
        divUsername.text(currentUsername);
        divDifficulty.text(currentDifficulty);
        divWeight.text(currentWeight);
        divCalories.text(currentCalories);
        divDuration.text(currentDuration);
    }
}
