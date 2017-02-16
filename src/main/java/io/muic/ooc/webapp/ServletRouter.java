/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.DatabaseService;
import io.muic.ooc.webapp.service.UserService;
import io.muic.ooc.webapp.servlet.*;
import io.muic.ooc.webapp.service.SecurityService;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.FilterRegistration;

public class ServletRouter {

    private SecurityService securityService;
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void init(Context ctx) {
        initHome(ctx);
        initLogin(ctx);
        initUserPage(ctx);
        initSessionFilter(ctx);
    }

    private void initHome(Context ctx) {
        HomeServlet homeServlet = new HomeServlet();
        homeServlet.setUserManager(userService);
        Tomcat.addServlet(ctx, "HomeServlet", homeServlet);
        ctx.addServletMapping("/index.jsp", "HomeServlet");
    }

    private void initLogin(Context ctx) {
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setSecurityManager(securityService);
        Tomcat.addServlet(ctx, "LoginServlet", loginServlet);
        ctx.addServletMapping("/login", "LoginServlet");

        LogoutServlet logoutServlet = new LogoutServlet();
        logoutServlet.setSecurityManager(securityService);
        Tomcat.addServlet(ctx, "LogoutServlet", logoutServlet);
        ctx.addServletMapping("/logout", "LogoutServlet");
    }

    private void initUserPage(Context ctx) {
        UserServlet userServlet = new UserServlet();
        userServlet.setUserManager(userService);
        Tomcat.addServlet(ctx, "UserServlet", userServlet);
        ctx.addServletMapping("/user", "UserServlet");

        DeleteServlet deleteServlet = new DeleteServlet();
        deleteServlet.setUserManager(userService);
        Tomcat.addServlet(ctx, "DeleteServlet", deleteServlet);
        ctx.addServletMapping("/remove", "DeleteServlet");

        AddServlet addServlet = new AddServlet();
        addServlet.setUserManager(userService);
        Tomcat.addServlet(ctx, "AddServlet", addServlet);
        ctx.addServletMapping("/new", "AddServlet");

        ModifyServlet modifyServlet = new ModifyServlet();
        modifyServlet.setUserManager(userService);
        Tomcat.addServlet(ctx, "ModifyServlet", modifyServlet);
        ctx.addServletMapping("/modify", "ModifyServlet");
    }

    private void initSessionFilter(Context ctx) {
        SessionFilter sessionFilter = new SessionFilter();
        sessionFilter.setSecurityManager(securityService);
        FilterDef sessionFilterDef = new FilterDef();
        sessionFilterDef.setFilter(sessionFilter);
        sessionFilterDef.setFilterName("SessionFilter");

        FilterMap sessionFilterMap = new FilterMap();
        sessionFilterMap.addURLPattern("/*");
        sessionFilterMap.setFilterName("SessionFilter");

        ctx.addFilterDef(sessionFilterDef);
        ctx.addFilterMap(sessionFilterMap);
    }
}
