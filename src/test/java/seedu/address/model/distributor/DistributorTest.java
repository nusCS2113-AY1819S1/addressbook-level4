package seedu.address.model.distributor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_NAME_AHBENG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_PHONE_AHBENG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_NAME_AHHUAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_PHONE_AHHUAT;
import static seedu.address.testutil.TypicalDistributors.AHBENG;
import static seedu.address.testutil.TypicalDistributors.AHHUAT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.DistributorBuilder;

public class DistributorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Distributor distributor = new DistributorBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
    }

    @Test
    public void isSameDistributor() {
        // same object -> returns true
        assertTrue(AHBENG.isSameDistributor(AHBENG));

        // null -> returns false
        assertFalse(AHBENG.isSameDistributor(null));

        // different phone -> returns false
        Distributor editedAhBeng = new DistributorBuilder(AHBENG).withPhone(VALID_DIST_PHONE_AHBENG).build();
        assertFalse(AHBENG.isSameDistributor(editedAhBeng));

        // different name -> returns false
        editedAhBeng = new DistributorBuilder(AHBENG).withName(VALID_DIST_NAME_AHBENG).build();
        assertFalse(AHBENG.isSameDistributor(editedAhBeng));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Distributor ahBengCopy = new DistributorBuilder(AHBENG).build();
        assertTrue(AHBENG.equals(ahBengCopy));

        // same object -> returns true
        assertTrue(AHBENG.equals(AHBENG));

        // null -> returns false
        assertFalse(AHBENG.equals(null));

        // different person -> returns false
        assertFalse(AHBENG.equals(AHHUAT));

        // different name -> returns false
        Distributor editedAhBeng = new DistributorBuilder(AHBENG).withName(VALID_DIST_NAME_AHHUAT).build();
        assertFalse(AHBENG.equals(editedAhBeng));

        // different phone -> returns false
        editedAhBeng = new DistributorBuilder(AHBENG).withPhone(VALID_DIST_PHONE_AHHUAT).build();
        assertFalse(AHBENG.equals(editedAhBeng));

    }
}