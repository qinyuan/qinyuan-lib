package com.qinyuan.lib.image;

import com.google.common.collect.Lists;
import org.junit.Test;

public class DatabaseRedundantImageValidatorTest {
    @Test
    public void testIsRedundant() throws Exception {
        DatabaseRedundantImageValidator validator = new DatabaseRedundantImageValidator();

        String testFile = "/var/www/html/lottery/uNpAdKRFzdUWtetipOhu_left-logo2.png";
        System.out.println(validator.isRedundant(testFile)); // columns are not set, return false

        validator.setColumns(Lists.newArrayList("AppConfig.propertyName"));
        System.out.println(validator.isRedundant(testFile));

        validator.setColumns(Lists.newArrayList("AppConfig.propertyValue"));
        System.out.println(validator.isRedundant(testFile));

        System.out.println(validator.isRedundant(testFile.replace(".png", "")));
    }
}
