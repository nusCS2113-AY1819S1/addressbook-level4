package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    public static final Module GET1020 = new ModuleBuilder().withCode("GET1020")
        .withTitle("Darwin and Revolution").build();

    //prevent instantiation.
    private TypicalModules() {}
}
