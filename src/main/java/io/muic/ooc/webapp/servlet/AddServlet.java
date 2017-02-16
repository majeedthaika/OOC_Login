package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.User;
import io.muic.ooc.webapp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {

    private UserService userService;

    public void setUserManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/add.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = userService.getSecurePassword(request.getParameter("pass"));
        String email = request.getParameter("email");
        String firstname = request.getParameter("first_name");
        String lastname = request.getParameter("last_name");

        User newFields = new User(null, firstname, lastname, username, password, email);

        if (userService.getUser(username) == null) {
            userService.addUser(newFields);
        }
        response.sendRedirect("/");

    }
}
