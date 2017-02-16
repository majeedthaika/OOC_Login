/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.User;
import io.muic.ooc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteServlet extends HttpServlet {

    private UserService userService;

    public void setUserManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        boolean confirm = StringUtils.equals(request.getParameter("confirm"),"true");

        User user = userService.getUser(username);

        String currentUser = (String) request.getSession()
                .getAttribute("username");

        if (user != null && !StringUtils.equals(currentUser, username)) {
            if (confirm) {
                userService.removeUser(user);

                response.sendRedirect("/");
            } else {
                request.setAttribute("user", username);

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/remove.jsp");
                rd.include(request, response);
            }
        } else {
            response.sendRedirect("/");
        }
    }
}
