package com.pms.filters;

import com.pms.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "GuideFilter", urlPatterns = "/guide/*")
public class GuideFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getFlag() != 1 || user.getLevel() == null || !user.getLevel().equals("guide")) {
            session.setAttribute("user", null);
            request.setAttribute("message", "Invalid Login Method");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, resp);
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
