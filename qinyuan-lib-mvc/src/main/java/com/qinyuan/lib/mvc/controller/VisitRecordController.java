package com.qinyuan.lib.mvc.controller;

import com.google.gson.GsonBuilder;
import com.qinyuan.lib.mvc.visitor.VisitorRecorder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitRecordController extends AbstractController {
    private VisitorRecorder visitorRecorder;

    public VisitRecordController(VisitorRecorder visitorRecorder) {
        this.visitorRecorder = visitorRecorder;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/json");

        String json = "";
        if (visitorRecorder != null) {
            json = new GsonBuilder().setPrettyPrinting().create().toJson(visitorRecorder.getRecords());
        }
        response.getWriter().print(json);
        return null;
    }
}
