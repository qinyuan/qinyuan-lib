package com.qinyuan.lib.database;

import com.qinyuan.lib.database.hibernate.HibernatePropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Property;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class to execute sql script
 * Created by qinyuan on 15-9-10.
 */
public class SQLScriptExecutor {
    /**
     * execute sql script file then return execution output
     *
     * @param scriptFile script file
     * @return execution output
     */
    public String exec(File scriptFile) {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + RandomStringUtils.randomAlphanumeric(40));

        exec(scriptFile, file);
        try {
            return IOUtils.toString(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            FileUtils.deleteQuietly(file);
        }
    }

    public void exec(File scriptFile, File outputFile) {
        SQLExec sqlExec = new SQLExec();

        sqlExec.setDriver(HibernatePropertyUtils.getDriver());
        sqlExec.setUrl(HibernatePropertyUtils.getUrl());
        sqlExec.setUserid(HibernatePropertyUtils.getUsername());
        sqlExec.setPassword(HibernatePropertyUtils.getPassword());
        sqlExec.setSrc(scriptFile);

        // set encoding
        Property property = new Property();
        property.setName("characterEncoding");
        property.setValue(HibernatePropertyUtils.getEncoding());
        sqlExec.addConnectionProperty(property);

        sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(
                SQLExec.OnError.class, "abort")));
        sqlExec.setPrint(true);
        sqlExec.setOutput(outputFile);
        sqlExec.setProject(new Project());

        sqlExec.execute();
    }
}
