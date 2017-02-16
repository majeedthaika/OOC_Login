package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.SecurityService;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionFilter implements Filter {

    private Set<String> validURLList;
    private SecurityService securityService;

    public void setSecurityManager(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void init(FilterConfig config) throws ServletException {
        validURLList = new HashSet<String>(Arrays.asList("/index.jsp", "/user", "/modify", "/remove", "/logout"));
    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
//        System.out.println(url);

        boolean authorized = securityService.isAuthorized(request);

        if (url.endsWith(".css")){
            chain.doFilter(req, res);
        } else {
            if (!authorized && !StringUtils.equals("/login", url)) {
                response.sendRedirect("/login");
            } else if (authorized && !validURLList.contains(url)) {
                response.sendRedirect("/index.jsp");
            } else {
                chain.doFilter(req, res);
            }
        }
    }

    public void destroy() {}
}