/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.jsf.controlador;

import dagu.modbus.control.app.jsf.bb.MonitorearModbusTcpBb;
import dagu.modbus.control.app.jsf.util.UtilitarioJsf;
import dagu.modbus.control.app.servicio.TcpModbusServicio;
import dagu.modbus.control.app.modelo.dto.PeticionModbusTcp;
import dagu.modbus.control.app.modelo.dto.RespuestaModbusTcp;
import dagu.modbus.control.app.servicio.GeneralModbusServicio;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;

/**
 *
 * @author Alberto
 */
@Named("monitorearModbusTcpControlador")
@ViewScoped
public class MonitorearModbusTcpControlador extends UtilitarioJsf implements Serializable {

    private static final long serialVersionUID = 5190834464726043861L;
              
    @Inject
    @Getter
    private MonitorearModbusTcpBb bb;
    
    @EJB
    @Getter
    private TcpModbusServicio serv;
    
    @EJB
    @Getter
    private GeneralModbusServicio generalModbusServicio;
    
    @PostConstruct
    public void init() {
        getBb().inicializar();
    }
    
    public void enviarTrama() {
        PeticionModbusTcp peticion = new PeticionModbusTcp();
        peticion.setDireccionIp(getBb().getDireccionIp());
        peticion.setPuerto(Integer.parseInt(getBb().getPuerto()));
        peticion.setCodigoFuncion(getBb().getCodigoFuncion());
        peticion.setReferencia(Integer.parseInt(getBb().getReferencia()));
        peticion.setConteoBits(Integer.parseInt(getBb().getConteoBits()));
        
        try {
            RespuestaModbusTcp resp = getServ().ejecutarFuncion(peticion);
            
            getBb().setRespuesta(getGeneralModbusServicio().obtenerRespuestaPorFuncion(resp));
            
            ponerMensajeInfo("Conexión exitosa!");
            
        } catch(Exception e) {
            Logger.getLogger(MonitorearModbusTcpControlador.class.getName()).log(Level.INFO, "Error! " + e.getMessage());
            ponerMensajeError(e.getMessage());
        }
    }        
    
}
