package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIsbn(String)}
 */
public class Isbn {


    public static final String MESSAGE_ISBN_CONSTRAINTS =
            "ISBN should be a valid 10-digit or 13-digit number (with hyphens).";
    // A 13-digit ISBN should start with either 978 or 979. A 10-digit ISBN would omit the first 3 char.
    public static final String ISBN10_VALIDATION_REGEX = "\\d{10}";
    public static final String ISBN13_VALIDATION_REGEX = "(978|979)\\d{10}";
    public final String value;

    /**
     * Constructs a {@code Isbn}.
     *
     * @param isbn A valid phone number.
     */
    public Isbn(String isbn) {
        requireNonNull(isbn);
        checkArgument(isValidIsbn(isbn), MESSAGE_ISBN_CONSTRAINTS);
        value = isbn;
    }

    /**
     * Returns true if the last digit (check digit) of a 10-digit ISBN is correct
     */
    private static boolean isValidCheckIsbn10(String str){
        int[] arr = {10,9,8,7,6,5,4,3,2};
        int sum=0;
        boolean isCorrect = true;
        for(int i=0; i<9; i++) {
            sum += arr[i] * (str.charAt(i) - '0');
        }
        System.out.print(sum);
        sum %= 11;
        if(sum != 0) {
            sum = 11 - sum;
        }
        if (sum != str.charAt(9)-'0') isCorrect = false;
        if (sum == 10 && (str.charAt(9) == 'X' || str.charAt(9) == 'x')) isCorrect = true; //Special case of X
        return isCorrect;
    }
    /**
     * Returns true if the last digit (check digit) of a 13-digit ISBN is correct
     */
    private static boolean isValidCheckIsbn13(String str){
        int[] arr = {1,3,1,3,1,3,1,3,1,3,1,3};
        int sum=0;
        boolean isCorrect = true;
        for(int i=0; i<12; i++) {
            sum += arr[i] * (str.charAt(i) - '0');
        }
        sum %= 10;
        if(sum != 0) {
            sum = 10 - sum;
        }
        if (sum != str.charAt(12)-'0') isCorrect = false;
        return isCorrect;
    }
    /**
     * Returns true if a given string is a valid isbn number.
     */
    public static boolean isValidIsbn(String test) {
        test = test.replace("-", "");
        return (test.matches(ISBN10_VALIDATION_REGEX) && isValidCheckIsbn10(test))
                || (test.matches(ISBN13_VALIDATION_REGEX) && isValidCheckIsbn13(test));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Isbn // instanceof handles nulls
                && value.equals(((Isbn) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
