package com.t13g2.forum.testutil;

import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1508E;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_MODULE_TITLE_CS1231;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_MODULE_TITLE_MA1508E;

import com.t13g2.forum.model.forum.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    public static final Module GET1020 = new ModuleBuilder().withCode("GET1020")
        .withTitle("Darwin and Revolution").build();

    // Manually added - Module's details found in {@code CommandTestUtil}
    public static final Module MA1508E = new ModuleBuilder().withCode(VALID_MODULE_CODE_MA1508E)
        .withTitle(VALID_MODULE_TITLE_MA1508E).build();
    public static final Module CS1231 = new ModuleBuilder().withCode(VALID_MODULE_CODE_CS1231)
        .withTitle(VALID_MODULE_TITLE_CS1231).build();
    public static final Module CS1111 = new ModuleBuilder().withCode("CS1111").withTitle("Random CS module").build();



    //prevent instantiation.
    private TypicalModules() {}
}
