package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Nationality in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCountryCode(String)}
 */
public class Nationality {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Country Code should only contain 2 UPPERCASE characters and valid. It should not be blank";

    /**
     * The regex follow closely to the ISO 3166-1 alpha-2 Country Code
     * It contain all the valid country code.
     */
    public static final String COUNTRY_VALIDATION_REGEX =
            "^(AF|AX|AL|DZ|AS|AD|AO|AI|AQ|AG|AR|AM|AW|AU|AT|AZ|" +
                    "BS|BH|BD|BB|BY|BE|BZ|BJ|BM|BT|BO|BQ|BA|BW|BV|BR|IO|BN|BG|BF|BI|" +
                    "KH|CM|CA|CV|KY|CF|TD|CL|CN|CX|CC|CO|KM|CG|CD|CK|CR|CI|HR|CU|CW|" +
                    "CY|CZ|DK|DJ|DM|DO|EC|EG|SV|GQ|ER|EE|ET|FK|FO|FJ|FI|FR|GF|PF|TF|" +
                    "GA|GM|GE|DE|GH|GI|GR|GL|GD|GP|GU|GT|GG|GN|GW|GY|HT|HM|VA|HN|HK|" +
                    "HU|IS|IN|ID|IR|IQ|IE|IM|IL|IT|JM|JP|JE|JO|KZ|KE|KI|KP|KR|KW|KG|" +
                    "LA|LV|LB|LS|LR|LY|LI|LT|LU|MO|MK|MG|MW|MY|MV|ML|MT|MH|MQ|MR|MU|" +
                    "YT|MX|FM|MD|MC|MN|ME|MS|MA|MZ|MM|NA|NR|NP|NL|NC|NZ|NI|NE|NG|NU|" +
                    "NF|MP|NO|OM|PK|PW|PS|PA|PG|PY|PE|PH|PN|PL|PT|PR|QA|RE|RO|RU|RW|" +
                    "BL|SH|KN|LC|MF|PM|VC|WS|SM|ST|SA|SN|RS|SC|SL|SG|SX|SK|SI|SB|SO|" +
                    "ZA|GS|SS|ES|LK|SD|SR|SJ|SZ|SE|CH|SY|TW|TJ|TZ|TH|TL|TG|TK|TO|TT|" +
                    "TN|TR|TM|TC|TV|UG|UA|AE|GB|US|UM|UY|UZ|VU|VE|VN|VG|VI|WF|EH|YE|" +
                    "ZM|ZW)$";

    public final String nationality;

    /**
     * Constructs a {@code Nationality}.
     *
     * @param nationality A valid country.
     */
    public Nationality(String nationality) {
        requireNonNull(nationality);
        checkArgument(isValidCountryCode(nationality), MESSAGE_NAME_CONSTRAINTS);
        this.nationality = nationality;
    }

    /**
     * Returns true if a given string is a valid nationality.
     */
    public static boolean isValidCountryCode(String input) {
        return input.matches(COUNTRY_VALIDATION_REGEX);
    }

    @Override
    public String toString(){
        return nationality;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nationality // instanceof handles nulls
                && nationality.equals(((Nationality) other).nationality)); // state check
    }

    @Override
    public int hashCode() {
        return nationality.hashCode();
    }
}
