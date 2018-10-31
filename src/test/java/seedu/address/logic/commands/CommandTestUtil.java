package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ProductDatabase;
import seedu.address.model.distributor.DNameContainsKeywordsPredicate;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.product.NameContainsKeywordsPredicate;
import seedu.address.model.product.Product;
import seedu.address.testutil.EditDistributorDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_LOGIN_USERNAME = "slap";
    public static final String VALID_LOGIN_PASSWORD = "password";

    public static final String LOGIN_VALID_DESC_USERNAME = " " + PREFIX_USERNAME + VALID_LOGIN_USERNAME;
    public static final String LOGIN_VALID_DESC_PASSWORD = " " + PREFIX_PASSWORD + VALID_LOGIN_PASSWORD;
    public static final String LOGIN_INVALID_DESC_USERNAME = " " + PREFIX_USERNAME + VALID_LOGIN_USERNAME + "@@";
    public static final String LOGIN_INVALID_DESC_PASSWORD = " " + PREFIX_PASSWORD + VALID_LOGIN_PASSWORD + "@@";
    public static final String NEWPASS_VALID_DESC_PASSWORD = " " + PREFIX_NEW_PASSWORD + VALID_LOGIN_PASSWORD + "AA";
    public static final String NEWPASS_INVALID_DESC_PASSWORD = " " + PREFIX_NEW_PASSWORD + VALID_LOGIN_PASSWORD + "AA@";

    public static final String VALID_NAME_APPLE = "Apple";
    public static final String VALID_NAME_BANANA = "Banana";
    public static final String VALID_SN_APPLE = "11111111";
    public static final String VALID_SN_BANANA = "22222222";
    public static final String VALID_DIST_APPLE = "Apple Farm";
    public static final String VALID_DIST_BANANA = "Banana Farm";
    public static final String VALID_INFO_APPLE = "Fruit";
    public static final String VALID_INFO_BANANA = "Fruit";
    public static final String VALID_TAG_HEALTHY = "healthy";
    public static final String VALID_TAG_SWEET = "sweet";

    public static final String VALID_DIST_NAME_AHBENG = "Ah Beng";
    public static final String VALID_DIST_NAME_AHHUAT = "Ah Huat";
    public static final String VALID_DIST_NAME_AHLEE = "Ah Lee";
    public static final String VALID_DIST_NAME_AHSENG = "Ah Seng";
    public static final String VALID_DIST_PHONE_AHBENG = "11111111";
    public static final String VALID_DIST_PHONE_AHHUAT = "22222222";
    public static final String VALID_DIST_PHONE_AHLEE = "33333333";
    public static final String VALID_DIST_PHONE_AHSENG = "44444444";



    public static final String NAME_DESC_APPLE = " " + PREFIX_NAME + VALID_NAME_APPLE;
    public static final String NAME_DESC_BANANA = " " + PREFIX_NAME + VALID_NAME_BANANA;
    public static final String SN_DESC_AMY = " " + PREFIX_SERIAL_NR + VALID_SN_APPLE;
    public static final String SN_DESC_BOB = " " + PREFIX_SERIAL_NR + VALID_SN_BANANA;
    public static final String DIST_DESC_AMY = " " + PREFIX_DISTRIBUTOR + VALID_DIST_APPLE;
    public static final String DIST_DESC_BOB = " " + PREFIX_DISTRIBUTOR + VALID_DIST_BANANA;
    public static final String INFO_DESC_AMY = " " + PREFIX_PRODUCT_INFO + VALID_INFO_APPLE;
    public static final String INFO_DESC_BOB = " " + PREFIX_PRODUCT_INFO + VALID_INFO_BANANA;
    public static final String TAG_DESC_HEALTHY = " " + PREFIX_TAG + VALID_TAG_HEALTHY;
    public static final String TAG_DESC_SWEET = " " + PREFIX_TAG + VALID_TAG_SWEET;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_SERIAL_NR + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_DISTRIBUTOR + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_PRODUCT_INFO; //null string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditDistributorCommand.EditDistributorDescriptor DESC_AHSENG;
    public static final EditDistributorCommand.EditDistributorDescriptor DESC_AHLEE;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_APPLE)
                .withPhone(VALID_SN_APPLE).withEmail(VALID_DIST_APPLE).withAddress(VALID_INFO_APPLE)
                .withTags(VALID_TAG_HEALTHY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BANANA)
                .withPhone(VALID_SN_BANANA).withEmail(VALID_DIST_BANANA).withAddress(VALID_INFO_BANANA)
                .withTags(VALID_TAG_HEALTHY, VALID_TAG_SWEET).build();
        DESC_AHSENG = new EditDistributorDescriptorBuilder().withName(VALID_DIST_NAME_AHSENG)
                .withPhone(VALID_DIST_PHONE_AHSENG).build();
        DESC_AHLEE = new EditDistributorDescriptorBuilder().withName(VALID_DIST_NAME_AHLEE)
                .withPhone(VALID_DIST_PHONE_AHLEE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered product list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ProductDatabase expectedProductDatabase = new ProductDatabase(actualModel.getProductInfoBook());
        List<Product> expectedFilteredList = new ArrayList<>(actualModel.getFilteredProductList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedProductDatabase, actualModel.getProductInfoBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredProductList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the product at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredProductList().size());

        Product product = model.getFilteredProductList().get(targetIndex.getZeroBased());
        final String[] splitName = product.getName().fullName.split("\\s+");
        model.updateFilteredProductList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredProductList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the distributor at the given {@code targetIndex} in the
     * {@code model}'s distributor book.
     */
    public static void showDistributorAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDistributorList().size());

        Distributor distributor = model.getFilteredDistributorList().get(targetIndex.getZeroBased());
        final String[] splitName = distributor.getDistName().fullDistName.split("\\s+");
        model.updateFilteredDistributorList(new DNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(6, model.getFilteredDistributorList().size());
    }

    /**
     * Deletes the first product in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Product firstProduct = model.getFilteredProductList().get(0);
        model.deletePerson(firstProduct);
        model.commitAddressBook();
    }

}
