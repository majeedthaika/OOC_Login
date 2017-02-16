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

            request.setAttribute("id", user.getId());
            request.setAttribute("user", user.getUserName());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("first_name", user.getFirstName());
            request.setAttribute("last_name", user.getLastName());

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/modify.jsp");
            rd.include(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("id"));
        User user = userService.getUserByID(Integer.parseInt(request.getParameter("id")));
        user.setUserName(request.getParameter("user"));
        user.setEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("first_name"));
        user.setLastName(request.getParameter("last_name"));

        userService.editUser(user);
        response.sendRedirect("/user?username="+user.getUserName());
    }
}
