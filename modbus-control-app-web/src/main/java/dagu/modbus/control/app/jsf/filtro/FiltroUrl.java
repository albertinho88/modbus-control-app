/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.jsf.filtro;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jualb
 */
@WebFilter(filterName = "FiltroUrls", urlPatterns = {"/app/*"}, dispatcherTypes = {DispatcherType.REQUEST,
    DispatcherType.FORWARD})
public class FiltroUrl implements Filter {

    @Getter
    @Setter
    private FilterConfig config;
    
    public FiltroUrl() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        setConfig(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String shortURI = httpRequest.getRequestURI();
            String realURI = shortURI.substring(shortURI.indexOf("/", 2) + 1);
            if (realURI.equals("app/index")) {
                realURI = "/pages/principal.jsf";
                RequestDispatcher rd = request.getRequestDispatcher(realURI);
                rd.forward(request, response);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(FiltroUrl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
