package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_NAME_AHLEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_NAME_AHSENG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_PHONE_AHLEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_PHONE_AHSENG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DistributorBook;
import seedu.address.model.distributor.Distributor;

/**
 * A utility class containing a list of {@code Distributor} objects to be used in tests.
 */
public class TypicalDistributors {

    public static final Distributor AHBENG = new DistributorBuilder().withName("Ah Beng").withPhone("11111111")
            .withProds("Grape").withTags("fruits").build();
    public static final Distributor AHHUAT = new DistributorBuilder().withName("Ah Huat").withPhone("22222222")
            .withProds("Orange").withTags("meat").build();
    public static final Distributor AHLONG = new DistributorBuilder().withName("Ah Long").withPhone("55555555")
            .withProds("Chocolate").withTags("snacks").build();
    public static final Distributor AHTING = new DistributorBuilder().withName("Ah Ting").withPhone("66666666")
            .withProds("Doritos").withTags("snacks").build();

    // Manually added
    public static final Distributor AHKUAH = new DistributorBuilder().withName("Ah Kuah").withPhone("77777777").build();
    public static final Distributor AHMEI = new DistributorBuilder().withName("Ah Mei").withPhone("88888888").build();

    // Manually added - Distributor's details found in {@code CommandTestUtil}
    public static final Distributor AHLEE = new DistributorBuilder().withName(VALID_DIST_NAME_AHLEE)
            .withPhone(VALID_DIST_PHONE_AHLEE).build();
    public static final Distributor AHSENG = new DistributorBuilder().withName(VALID_DIST_NAME_AHSENG)
            .withPhone(VALID_DIST_PHONE_AHSENG).build();

    public static final String KEYWORD_MATCHING_SENG = "Seng"; // A keyword that matches SENG

    private TypicalDistributors() {} // prevents instantiation

    /**
     * Returns an {@code DistributorBook} with all the typical distributors.
     * */
    public static DistributorBook getTypicalDistributorBook() {
        DistributorBook db = new DistributorBook();
        for (Distributor distributor : getTypicalDistributors()) {
            db.addDistributor(distributor);
        }
        return db;
    }

    public static List<Distributor> getTypicalDistributors() {
        return new ArrayList<>(Arrays.asList(AHBENG, AHHUAT, AHLONG, AHTING));
    }
}
