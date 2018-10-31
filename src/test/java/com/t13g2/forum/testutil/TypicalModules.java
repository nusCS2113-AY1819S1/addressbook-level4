package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    public static final Module GET1020 = new ModuleBuilder().withCode("GET1020")
        .withTitle("Darwin and Revolution").build();
    public static final Module MA1511 = new ModuleBuilder().withCode("MA1511")
        .withTitle("Engineering Calculus").build();

    //prevent instantiation.
    private TypicalModules() {}
}
