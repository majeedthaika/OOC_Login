/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.User;
import io.muic.ooc.webapp.service.UserService;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {

    private UserService userService;

    public void setUserManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("userTable", null);

        if (!users.isEmpty()) {
            StringBuilder userTable = new StringBuilder(
                    "<table class=\"table table-bordered table-striped\">" +
                    "<thead class=\"thead-inverse\"><tr><th>Username</th><th>Action</th></thead><tbody>");
            for (User user : users) {
                userTable.append("<tr><td>");
                userTable.append("<a href=\"/user?username="+user.getUserName()+"\">"+user.getUserName()+"</a>");
                userTable.append("</td><td>");
                userTable.append("<a href=\"/remove?username="+user.getUserName()+"&confirm=false\" " +
                        "method=\"post\" type=\"button\" class=\"btn btn-danger\">Remove</a>");
                userTable.append("</td></tr>");
            }
            userTable.append("</tbody></table>");
            request.setAttribute("userTable", userTable);
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
        rd.include(request, response);
    }
}
