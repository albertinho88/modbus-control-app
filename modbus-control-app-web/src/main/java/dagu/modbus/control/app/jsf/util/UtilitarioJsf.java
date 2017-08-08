/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.jsf.util;

import java.io.Serializable;
import java.util.List;
import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class UtilitarioJsf implements Serializable {
    
    private static final long serialVersionUID = 4660010445382940958L;
    
    public UtilitarioJsf() {
        super();
    }
    
    protected void ponerMensajeInfo(final String mensaje) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
    }    

    protected static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    protected void ponerMensajeInfo(final List<String> listaMensaje) {
        for (String mensaje : listaMensaje) {
            getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
        }
    }

    protected void ponerMensajeAlerta(final String mensaje) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
    }

    protected void ponerMensajeAlerta(final List<String> listaMensaje) {
        for (String mensaje : listaMensaje) {
            getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
        }
    }

    protected void ponerMensajeError(final String mensaje) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
    }

    protected void ponerMensajeErrorExcepcion(final String mensaje, final String excepcion) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, excepcion));
    }

    protected void ponerMensajeError(final List<String> listaMensaje) {
        for (String mensaje : listaMensaje) {
            getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
        }
    }
    
     protected void ponerMensajePorComponente(FacesMessage.Severity severity, String summary, String detail, String componente) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(severity);
        message.setSummary(summary);
        message.setDetail(detail);
        getContext().addMessage(componente, message);
    }
    
    /**
     *
     * @return the context
     */
    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Retorna un objeto external context
     *
     * @return {@link ExternalContext}
     */
    protected ExternalContext getExternalContext() {
        return getContext().getExternalContext();
    }

    /**
     * Retorna un objeto elcontext
     *
     * @return {@link ELContext}
     */
    protected ELContext getElContext() {
        return getContext().getELContext();
    }

    /**
     * Retorna un objeto aplicacion
     *
     * @return {@link Application}
     */
    protected Application getApplication() {
        return getContext().getApplication();
    }

    /**
     * Retorna un objeto request
     *
     * @return {@link HttpServletRequest}
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    /**
     * Retorna un objeto response
     *
     * @return {@link HttpServletResponse}
     */
    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) getContext().getExternalContext().getResponse();
    }
    
    public String obtenerNavegador(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "Internet Explorer";
        }
        if (userAgent.contains("Firefox")) {
            return "Firefox";
        }
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        }
        if (userAgent.contains("Opera")) {
            return "Opera";
        }
        if (userAgent.contains("Safari")) {
            return "Safari";
        }
        return "Desconocido";
    }
    
}
