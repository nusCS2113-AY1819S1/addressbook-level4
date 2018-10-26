package seedu.address.logic;

/**
 * It contains all the valid commands of the .
 */
public class ValidCommandList {
    private static final String ADD_COMMAND = "n/Novin Tong Yong Kang g/M nat/SG p/99999999 "
            + "e/novin@example.com a/Pasir Ris street 99,#02-25 d/69 t/friends\n"
            + "n/Tsu Wei Quan g/F nat/SG p/66666666 e/tsuTheBoss@gmail.com "
            + "a/Tampines street 82,#02-33 d/99 t/owesMoney\n"
            + "n/Rajdeep g/M nat/SG p/98743546 e/rajthenewb@gmail.com "
            + "a/Bedok street 32 d/82 t/rich\n"
            + "n/Joel g/M nat/SG p/9784230 e/JoeltheHacker@gmail.com "
            + "a/Jurong West Street 52 d/76 t/pro\n";

    private static final String GROUP_COMMAND = "n/tut[01] l/e1-11-20 t/ma1301\n"
            + "n/tut[02] l/e3-11-20 t/ma1301\n"
            + "n/tut[03] l/e5-11-20 t/ma1301\n"
            + "n/tut[04] l/e11-11-20 t/ma1301\n"
            + "n/tut[05] l/s22-11-20 t/ma1301\n"
            + "n/tut[06] l/as4-11-20 t/ma1301\n"
            + "n/tut[07] l/e9-3-2 t/ma1301\n"
            + "n/tut[08] l/e5-1-2 t/ma1301\n";

    public static String getAddCommand() {
        return ADD_COMMAND;
    }

    public static String getGroupCommand() {
        return GROUP_COMMAND;
    }
}
