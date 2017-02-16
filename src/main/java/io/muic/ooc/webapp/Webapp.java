package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.DatabaseService;
import io.muic.ooc.webapp.service.SecurityService;
import java.io.File;
import javax.servlet.ServletException;

import io.muic.ooc.webapp.service.UserService;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Webapp {

    public static void main(String[] args) {

        String docBase = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(443);
        SecurityService securityService = new SecurityService();
        DatabaseService databaseService = new DatabaseService();
        UserService userService = new UserService();

        ServletRouter servletRouter = new ServletRouter();
        securityService.setDatabaseService(databaseService);
        userService.setDatabaseService(databaseService);
        servletRouter.setSecurityService(securityService);
        servletRouter.setUserService(userService);

        Context ctx;
        try {
            ctx = tomcat.addWebapp("/", new File(docBase).getAbsolutePath());
            servletRouter.init(ctx);
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException ex) {
            ex.printStackTrace();
        }

    }
}
