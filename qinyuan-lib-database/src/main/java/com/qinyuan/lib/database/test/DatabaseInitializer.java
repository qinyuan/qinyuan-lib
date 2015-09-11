package com.qinyuan.lib.database.test;

import com.qinyuan.lib.database.SQLScriptExecutor;
import com.qinyuan.lib.database.hibernate.HibernatePropertyUtils;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.file.ClasspathFileUtils;

import java.io.File;

/**
 * Class to initialize database
 * Created by qinyuan on 15-9-10.
 */
public class DatabaseInitializer {
    public void init(File scriptFile) {
        String database = HibernatePropertyUtils.getDatabase();
        HibernateUtils.executeSQLUpdate(
                "DROP DATABASE IF EXISTS `" + database + "`",
                "CREATE DATABASE `" + database + "`",
                "USE " + database
        );
        new SQLScriptExecutor().exec(scriptFile);
    }

    public final static String DEFAULT_INIT_FILE = "init.sql";

    /**
     * use default file init.sql to initialize database
     */
    public void init() {
        init(ClasspathFileUtils.getFile(DEFAULT_INIT_FILE));
    }
}
