package com.qinyuan.lib.contact.qq;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QQValidatorTest {
    @Test
    public void testValidate() throws Exception {
        QQValidator validator = new QQValidator();
        assertThat(validator.validate("48946556")).isTrue();
        assertThat(validator.validate(null)).isFalse();
        assertThat(validator.validate("5564a")).isFalse();
        assertThat(validator.validate("")).isFalse();
        assertThat(validator.validate(" ")).isFalse();
    }
}
