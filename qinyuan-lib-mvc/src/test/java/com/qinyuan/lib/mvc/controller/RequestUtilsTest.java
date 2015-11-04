package com.qinyuan.lib.mvc.controller;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestUtilsTest {
    @Test
    public void testGetRealRemoteAddress() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        when(request.getHeader("x-forwarded-for")).thenReturn("111.206.241.75,111.206.241.75, 183.61.236.115");
        assertThat(RequestUtils.getRealRemoteAddress(request)).isEqualTo("111.206.241.75");

        when(request.getHeader("x-forwarded-for")).thenReturn("113.92.82.187");
        assertThat(RequestUtils.getRealRemoteAddress(request)).isEqualTo("113.92.82.187");

        when(request.getHeader("x-forwarded-for")).thenReturn(null);
        assertThat(RequestUtils.getRealRemoteAddress(request)).isEqualTo("127.0.0.1");
    }
}
