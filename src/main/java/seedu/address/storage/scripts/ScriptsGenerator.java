package seedu.address.storage.scripts;

/**
 * This class will help the program to generate scripts automatically by BruteForce.
 */
public class ScriptsGenerator {
    private static final String NEXT_LINE = System.lineSeparator();

    private static final String ADD_COMMAND = "n/Alex Yeoh g/Male nat/SG p/87438807 "
            + "e/alexyeoh@example.com a/Blk 30 Geylang Street 29, #06-40 t/friends" + NEXT_LINE
            + "n/Bernice Yu g/Female nat/SG p/99272758 e/berniceyu@example.com "
            + "a/Blk 30 Lorong 3 Serangoon Gardens, #07-18 t/colleagues" + NEXT_LINE
            + "n/Charlotte Oliveiro g/F nat/AU p/93210283 e/charlotte@example.com "
            + "a/Blk 11 Ang Mo Kio Street 74, #11-04 t/neighbours" + NEXT_LINE
            + "n/David Li g/M nat/CN p/91031282 e/lidavid@example.com "
            + "a/Blk 436 Serangoon Gardens Street 26, #16-43 t/family" + NEXT_LINE
            + "n/Irfan Ibrahim g/M nat/MY p/92492021 e/irfan@example.com "
            + "a/Blk 47 Tampines Street 20, #17-35 t/classmates" + NEXT_LINE
            + "n/Roy Balakrishna g/M nat/IN p/92624417 e/royb@example.com "
            + "a/Blk 45 Aljunied Street 85, #11-31 t/colleagues" + NEXT_LINE
            + "n/Khadijah Saine g/F nat/IN p/92142978 e/KhadijahSaine@example.com "
            + "a/Blk 112, Simei Street 1 t/classmates" + NEXT_LINE
            + "n/Jeni Eisenhower g/F nat/US p/91077545 e/JeniEisenhower@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Tien Agan g/F nat/US p/96175570 e/TienAgan@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Brendan g/M nat/SG p/95704308 e/Brendan@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Fay g/M nat/SG p/97894720 e/Fay@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Tracey g/F nat/SG p/99944817 e/Tracey@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Christy g/F nat/SG p/98496742 e/Christy@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Heidi g/F nat/SG p/95067553 e/Heidi@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Christene g/F nat/SG p/96573233 e/Christene@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Les g/F nat/SG p/94615325 e/Les@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Luigi g/M nat/SG p/96259361 e/Luigi@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Reatha g/F nat/IN p/96775760 e/Reatha@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Dorethea g/F nat/CN p/94680649 e/Dorethea@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Deon g/M nat/SG p/96200344 e/Deon@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Joleen g/F nat/SG p/98481923 e/Joleen@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Glynis g/F nat/SG p/91174790 e/Glynis@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Corinna g/F nat/SG p/91082076 e/Corinna@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Lianne g/F nat/SG p/97181574 e/Lianne@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Deanne g/F nat/SG p/96849130 e/Deanne@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Donna g/F nat/SG p/95435362 e/Donna@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Chandra g/M nat/IN p/91410083 e/Chandra@example.com "
            + "a/NUS Utown t/classmates" + NEXT_LINE
            + "n/Tsu Wei Quan g/M nat/SG p/96259561 e/tsuweiquan@gmail.com "
            + "a/Blk 111, Simei Street 1 t/colleagues" + NEXT_LINE
            + "n/Novin Tong Yong Kang g/M nat/SG p/99999999 "
            + "e/E0176909@u.nus.edu a/Pasir Ris street 99,#02-25 t/colleagues" + NEXT_LINE
            + "n/TsuraJovin g/M nat/SG p/99999999 e/tsurajovin@gmail.com "
            + "a/Bedok street 99,#02-25 t/colleagues" + NEXT_LINE
            + "n/Joel g/M nat/MY p/9784230 e/joeltan98@hotmail.com "
            + "a/Jurong West Street 52 t/colleagues" + NEXT_LINE
            + "n/JoelTan g/M nat/MY p/9784230 e/joel.twh@u.nus.edu "
            + "a/Jurong West Street 52 t/colleagues" + NEXT_LINE
            + "n/Rajdeep g/M nat/SG p/9784230 e/rajdeepsh@outlook.com "
            + "a/Pasir ris street 2 t/colleagues" + NEXT_LINE
            + "n/Clara g/F nat/SG p/9784230 e/Clara@example.com "
            + "a/UTown t/colleagues" + NEXT_LINE;


    private static final String GROUP_COMMAND = "g/1 p/11 p/21 p/31 p/34 p/14 p/5 p/4 p/3 p/2 p/12" + NEXT_LINE
            + "g/2 p/1 p/2 p/3 p/16 p/26 p/11 p/27 p/28 p/29 p/23" + NEXT_LINE
            + "g/3 p/5 p/6 p/7 p/8 p/9 p/10 p/11 p/12 p/13 p/14" + NEXT_LINE
            + "g/4 p/11 p/12 p/13 p/14 p/15 p/16 p/17 p/18 p/19 p/20" + NEXT_LINE
            + "g/5 p/1 p/2 p/3 p/4 p/5 p/6 p/7 p/8 p/9 p/20" + NEXT_LINE
            + "g/6 p/21 p/22 p/23 p/24 p/25 p/26 p/27 p/28 p/29 p/10" + NEXT_LINE
            + "g/7 p/14 p/25 p/13 p/4 p/5 p/6 p/17 p/28 p/29 p/3" + NEXT_LINE
            + "g/8 p/1 p/22 p/13 p/20 p/10 p/9 p/19 p/17 p/18 p/16" + NEXT_LINE
            + "g/9 p/21 p/26 p/31 p/30 p/10 p/5 p/4 p/7 p/9 p/8" + NEXT_LINE
            + "g/10 p/30 p/20 p/10 p/14 p/15 p/16 p/27 p/18 p/19 p/11" + NEXT_LINE
            + "g/11 p/12 p/2 p/3 p/24 p/15 p/26 p/17 p/18 p/29 p/30" + NEXT_LINE
            + "g/12 p/11 p/2 p/3 p/24 p/25 p/26 p/7 p/18 p/19 p/1" + NEXT_LINE
            + "g/13 p/11 p/12 p/3 p/24 p/15 p/6 p/7 p/8 p/29 p/30" + NEXT_LINE
            + "g/14 p/11 p/12 p/3 p/24 p/25 p/6 p/7 p/28 p/29 p/5" + NEXT_LINE
            + "g/15 p/1 p/2 p/3 p/4 p/5 p/6 p/27 p/28 p/19 p/10" + NEXT_LINE
            + "g/16 p/1 p/2 p/23 p/24 p/15 p/16 p/7 p/28 p/9 p/8" + NEXT_LINE
            + "g/17 p/1 p/22 p/3 p/24 p/5 p/16 p/17 p/8 p/19 p/2" + NEXT_LINE
            + "g/18 p/1 p/12 p/3 p/4 p/15 p/6 p/27 p/28 p/29 p/7" + NEXT_LINE
            + "g/19 p/1 p/22 p/3 p/23 p/15 p/6 p/7 p/8 p/9 p/28" + NEXT_LINE
            + "g/20 p/1 p/2 p/3 p/14 p/5 p/16 p/7 p/18 p/9 p/20" + NEXT_LINE;

    private static final String CREATE_GROUP_COMMAND = "n/CS2113 l/LT15 t/java t/friday t/4pm" + NEXT_LINE
            + "n/CS2040C l/LT15 t/cpp t/tuesday t/5pm" + NEXT_LINE
            + "n/MA1508 l/E1-01-01 t/maths t/wednesday t/2pm" + NEXT_LINE
            + "n/CG2027 l/E2-02-02 t/circuits t/monday t/12pm" + NEXT_LINE
            + "n/TUT[01] l/E3-03-03 t/pc1222 t/monday t/6pm" + NEXT_LINE
            + "n/TUT[02] l/LT12 t/pc1222 t/thursday t/8am" + NEXT_LINE
            + "n/TUT[03] l/LT12 t/pc1222 t/thursday t/10am" + NEXT_LINE
            + "n/TUT[04] l/LT12 t/pc1222 t/thursday t/12pm" + NEXT_LINE
            + "n/TUT[05] l/LT12 t/pc1222 t/thursday t/4pm" + NEXT_LINE
            + "n/TUT[06] l/LT12 t/pc1222 t/thursday t/5pm" + NEXT_LINE
            + "n/CG2028 l/LT16 t/cpu t/monday t/8am" + NEXT_LINE
            + "n/EE2028 l/LT15 t/fsm t/monday t/10am" + NEXT_LINE
            + "n/IS3261 l/E1-01-01 t/android t/monday t/2pm" + NEXT_LINE
            + "n/MA1501 l/E2-02-02 t/maths t/tuesday t/8am" + NEXT_LINE
            + "n/TUT[07] l/E3-03-03 t/pc1222 t/tuesday t/10am" + NEXT_LINE
            + "n/TUT[08] l/LT12 t/pc1222 t/tuesday t/12pm" + NEXT_LINE
            + "n/TUT[09] l/LT08 t/pc1222 t/tuesday t/2pm" + NEXT_LINE
            + "n/CS1010 l/LT15 t/c t/tuesday t/4pm" + NEXT_LINE
            + "n/CS4210 l/LT24 t/ai t/friday t/8am" + NEXT_LINE
            + "n/CS2113T l/LT24 t/java t/friday t/10am" + NEXT_LINE;

    private static final String ADD_TEST_COMMAND = "Alex Yeoh tn/cs2113quiz1 tm/11" + NEXT_LINE
            + "Alex Yeoh tn/cs2113quiz2 tm/33" + NEXT_LINE
            + "Alex Yeoh tn/cs2113quiz3 tm/38" + NEXT_LINE
            + "Alex Yeoh tn/cs2113quiz4 tm/77" + NEXT_LINE
            + "Bernice Yu tn/cs2113quiz1 tm/77" + NEXT_LINE
            + "Bernice Yu tn/cs2113quiz2 tm/40" + NEXT_LINE
            + "Bernice Yu tn/cs2113quiz3 tm/66" + NEXT_LINE
            + "Bernice Yu tn/cs2113quiz4 tm/18" + NEXT_LINE
            + "David Li tn/cs2113quiz1 tm/50" + NEXT_LINE
            + "David Li tn/cs2113quiz2 tm/55" + NEXT_LINE
            + "David Li tn/cs2113quiz3 tm/45" + NEXT_LINE
            + "David Li tn/cs2113quiz4 tm/96" + NEXT_LINE
            + "Irfan Ibrahim tn/cs2113quiz1 tm/88" + NEXT_LINE
            + "Irfan Ibrahim tn/cs2113quiz2 tm/78" + NEXT_LINE
            + "Irfan Ibrahim tn/cs2113quiz3 tm/66" + NEXT_LINE
            + "Irfan Ibrahim tn/cs2113quiz4 tm/90" + NEXT_LINE
            + "Roy Balakrishnan tn/cs2113quiz1 tm/39" + NEXT_LINE
            + "Roy Balakrishnan tn/cs2113quiz2 tm/70" + NEXT_LINE
            + "Roy Balakrishnan tn/cs2113quiz3 tm/22" + NEXT_LINE
            + "Roy Balakrishnan tn/cs2113quiz4 tm/68" + NEXT_LINE
            + "Charlotte Oliveiro tn/cs2113quiz1 tm/77" + NEXT_LINE
            + "Charlotte Oliveiro tn/cs2113quiz2 tm/79" + NEXT_LINE
            + "Charlotte Oliveiro tn/cs2113quiz3 tm/59" + NEXT_LINE
            + "Charlotte Oliveiro tn/cs2113quiz4 tm/88" + NEXT_LINE;

    public static String getAddCommand() {
        return ADD_COMMAND;
    }

    public static String getGroupCommand() {
        return GROUP_COMMAND;
    }

    public static String getCreateGroupCommand() {
        return CREATE_GROUP_COMMAND;
    }

    public static String getAddTestCommand() {
        return ADD_TEST_COMMAND;
    }
}
