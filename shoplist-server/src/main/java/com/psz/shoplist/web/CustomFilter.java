package com.psz.shoplist.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFilter implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        log.info("Inside CustomFilter handling request: {}", req);
       chain.doFilter(req, resp);
       log.info("Inside CustomFilter get response: {}", resp);
        
    }
    
}
