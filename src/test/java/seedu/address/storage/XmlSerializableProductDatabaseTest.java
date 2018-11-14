package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ProductDatabase;
import seedu.address.testutil.TypicalProducts;

public class XmlSerializableProductDatabaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableProductDatabaseTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalProductsProductDatabase.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidProductProductDatabase.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateProductProductDatabase.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalProductsFile_success() throws Exception {
        XmlSerializableProductDatabase dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableProductDatabase.class);
        ProductDatabase addressBookFromFile = dataFromFile.toModelType();
        ProductDatabase typicalProductsProductDatabase = TypicalProducts.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalProductsProductDatabase);
    }

    @Test
    public void toModelType_invalidProductFile_throwsIllegalValueException() throws Exception {
        XmlSerializableProductDatabase dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableProductDatabase.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateProducts_throwsIllegalValueException() throws Exception {
        XmlSerializableProductDatabase dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableProductDatabase.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableProductDatabase.MESSAGE_DUPLICATE_PRODUCT);
        dataFromFile.toModelType();
    }
}
