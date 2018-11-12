package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.storage.recruitment.XmlSerializableRecruitmentList;
import seedu.address.testutil.TypicalRecruitments;


public class XmlSerializableRecruitmentListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableRecruitmentListTest");
    private static final Path TYPICAL_RECRUITMENTS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalRecruitmentsRecruitmentList.xml");
    private static final Path INVALID_RECRUITMENTS_FILE = TEST_DATA_FOLDER.resolve(
            "invalidRecruitmentRecruitmentList.xml");
    private static final Path DUPLICATE_RECRUITMENTS_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateRecruitmentRecruitmentList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRecruitmentsFile_success() throws Exception {
        XmlSerializableRecruitmentList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RECRUITMENTS_FILE,
                XmlSerializableRecruitmentList.class);
        RecruitmentList recruitmentListFromFile = dataFromFile.toModelType();
        RecruitmentList typicalRecruitmentsRecruitmentList = TypicalRecruitments.getTypicalRecruitmentList();
        assertEquals(recruitmentListFromFile, typicalRecruitmentsRecruitmentList);
    }

    @Test
    public void toModelType_invalidRecruitmentList_throwsIllegalValueException() throws Exception {
        XmlSerializableRecruitmentList dataFromFile = XmlUtil.getDataFromFile(INVALID_RECRUITMENTS_FILE,
                XmlSerializableRecruitmentList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePosts_throwsIllegalValueException() throws Exception {
        XmlSerializableRecruitmentList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RECRUITMENTS_FILE,
                XmlSerializableRecruitmentList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRecruitmentList.MESSAGE_DUPLICATE_RECRUITMENT);
        dataFromFile.toModelType();
    }
}
