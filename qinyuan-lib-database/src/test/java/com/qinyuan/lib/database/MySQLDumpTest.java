package com.qinyuan.lib.database;

import com.qinyuan.lib.database.test.DatabaseInitializer;
import com.qinyuan.lib.lang.test.TestFileUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test MySQLDump
 * Created by qinyuan on 15-5-29.
 */
public class MySQLDumpTest {
    @Test
    public void testRun() throws Exception {
        new DatabaseInitializer().init();

        File file = TestFileUtils.getTempFile("backup2.sql");
        if (file.isFile()) {
            FileUtils.deleteQuietly(file);
        }

        assertThat(file.isFile()).isFalse();

        MySQLDump dump = new MySQLDump();
        dump.setPassword("qinyuan");
        dump.setDatabase("test");
        dump.setBackupPath(file.getAbsolutePath());
        dump.run();

        assertThat(file.isFile()).isTrue();
        assertThat(IOUtils.toString(new FileInputStream(file))).contains("CREATE TABLE `test_table1` (");
    }
}
