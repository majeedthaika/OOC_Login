package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.User;
import io.muic.ooc.webapp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ModifyServlet extends HttpServlet {

    private UserService userService;

    public void setUserManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        User user = userService.getUser(username);

        if (user != null) {
            request.setAttribute("title", "Edit "+user.getUserName()+"'s Info");
            request.setAttribute("readonly", "readonly");

            request.setAttribute("user", user.getUserName());
            request.setAttribute("pass", user.getPassword());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("first_name", user.getFirstName());
            request.setAttribute("last_name", user.getLastName());

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/modify.jsp");
            rd.include(request, response);
        } else {
            request.setAttribute("title", "Add New User");
            request.setAttribute("readonly", "");

            request.setAttribute("user", null);
            request.setAttribute("pass", null);
            request.setAttribute("email", null);
            request.setAttribute("first_name", null);
            request.setAttribute("last_name", null);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/modify.jsp");
            rd.include(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = userService.getSecurePassword(request.getParameter("pass"));
        String email = request.getParameter("email");
        String firstname = request.getParameter("first_name");
        String lastname = request.getParameter("last_name");

        User newFields = new User(firstname, lastname, username, password, email);

        // show success / failure !!!!!!!!
        if (userService.getUser(username) != null) {
            userService.editUser(newFields);
        } else {
            userService.addUser(newFields);
        }
        response.sendRedirect("/");
    }
}
