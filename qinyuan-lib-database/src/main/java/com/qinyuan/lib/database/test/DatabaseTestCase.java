package com.qinyuan.lib.database.test;

import org.junit.Before;

public abstract class DatabaseTestCase {
    @Before
    public final void setUp() {
        new DatabaseInitializer().init();
    }
}
