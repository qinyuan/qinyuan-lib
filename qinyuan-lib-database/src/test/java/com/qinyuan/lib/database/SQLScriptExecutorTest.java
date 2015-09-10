package com.qinyuan.lib.database;

import com.qinyuan.lib.lang.test.TestFileUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SQLScriptExecutorTest {
    @Test
    public void testExec() throws Exception {
        String output = new SQLScriptExecutor().exec(TestFileUtils.getFile("show-databases.sql"));
        assertThat(output).contains("SCHEMA_NAME\n");
        assertThat(output).contains("information_schema\n");
        assertThat(output).contains("test\n");
    }
}
