package com.qinyuan.lib.mvc.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller to output identity code
 * Created by qinyuan on 15-6-28.
 */
public class IdentityCodeController extends AbstractController {
    public final static String IDENTITY_CODE_SESSION_KEY = "identityCode";

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        String str = IdentifyCode.getPicture(response);
        request.getSession().setAttribute(IDENTITY_CODE_SESSION_KEY, str);
        return null;
    }
}
