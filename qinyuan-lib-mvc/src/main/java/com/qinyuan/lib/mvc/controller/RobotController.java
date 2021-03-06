package com.qinyuan.lib.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
public class RobotController {
    @RequestMapping("robot.txt")
    public void index(HttpServletResponse response) {
        printRobot(response);
    }

    @RequestMapping("robots.txt")
    public void index2(HttpServletResponse response) {
        printRobot(response);
    }

    private void printRobot(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.print("User-agent: *\nDisallow:");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
