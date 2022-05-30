package com.example;

import com.example.controller.frontend.MainPageController;
import com.example.controller.superadmin.HeadLineOperationController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private MainPageController mainPageController;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("request path : " + req.getServletPath());
        System.out.println("request method : " + req.getMethod());

        if ("/frontend/getMainPageInfo".equals(req.getServletPath())
                && "GET".equals(req.getMethod())) {
            new MainPageController().getMainPageInfo(req, resp);
        } else if ("/superadmin/addHeadLine".equals(req.getServletPath())
                && "POST".equals(req.getMethod())) {
            new HeadLineOperationController().addHeadLine(req, resp);
        }
        super.service(req, resp);
    }
}
