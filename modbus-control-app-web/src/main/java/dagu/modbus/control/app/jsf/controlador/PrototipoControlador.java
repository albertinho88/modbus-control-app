/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.jsf.controlador;

import dagu.modbus.control.app.jsf.bb.PrototipoBb;
import dagu.modbus.control.app.servicio.MonitorearDispositivosModbusServicio;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

/**
 *
 * @author Alberto
 */
@Named("prototipoControlador")
@ViewScoped
public class PrototipoControlador implements Serializable {

    private static final long serialVersionUID = -6440203849723141364L;       
    
    @Inject
    @Getter
    private PrototipoBb bb;
    
    @EJB
    @Getter
    private MonitorearDispositivosModbusServicio serv;
    
    @PostConstruct
    public void init() {
        
    }
    
    public void conectar() {
        
        getServ().probar();
    }
    
}
