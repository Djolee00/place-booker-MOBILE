package com.placebooker.auth.xss;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class XSSFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(
                new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }
}
