package com.qinyuan.lib.database.hibernate;

import com.qinyuan.lib.database.MySQLDump;
import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.file.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * Class to backup MySQL
 * Created by qinyuan on 15-5-29.
 */
public class HibernateMySQLDump {
    private final static Logger LOGGER = LoggerFactory.getLogger(HibernateMySQLDump.class);

    private final String backupDirectory;
    private String binPath;
    private String password;

    public HibernateMySQLDump(String backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    public void setBinPath(String binPath) {
        this.binPath = binPath;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void run() {
        if (!new File(backupDirectory).isDirectory()) {
            LOGGER.error("Invalid directory {}", backupDirectory);
        }

        MySQLDump mySQLDump = new MySQLDump();
        String fileName = buildFileName(HibernatePropertyUtils.getDatabase());
        mySQLDump.setBackupPath(fileName);
        mySQLDump.setDatabase(HibernatePropertyUtils.getDatabase());
        mySQLDump.setUser(HibernatePropertyUtils.getUsername());
        mySQLDump.setPassword(HibernatePropertyUtils.getPassword());
        mySQLDump.setHost(HibernatePropertyUtils.getHost());
        if (StringUtils.hasText(binPath)) {
            mySQLDump.setBinPath(binPath);
        }
        if (password != null) {
            mySQLDump.setPassword(password);
        }
        mySQLDump.run();
        ZipUtils.zip(fileName, fileName + ".zip");
        FileUtils.deleteQuietly(new File(fileName));
    }

    private String buildFileName(String databaseName) {
        return backupDirectory + "/" + databaseName + "." +
                DateUtils.nowString().replaceAll("[-|:|\\s]", ".") + ".backup.sql";
    }
}
