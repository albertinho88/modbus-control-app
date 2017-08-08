/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.servicio;

import dagu.modbus.control.app.servicio.modelo.dto.PeticionModbusTcp;
import dagu.modbus.control.app.servicio.modelo.dto.RespuestaModbusTcp;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.Getter;
import lombok.Setter;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.net.TCPMasterConnection;

/**
 *
 * @author jlarragam
 */
@LocalBean
@Stateless
public class TcpModbusServicio implements Serializable {

    private static final long serialVersionUID = -3390713346240429910L;
       
    @Getter
    @Setter
    private TCPMasterConnection con = null; //the connection
    
    @Getter
    @Setter
    private ModbusTCPTransaction trans = null; //the transaction
    
    public RespuestaModbusTcp ejecutarFuncion(PeticionModbusTcp peticion) throws Exception {
        RespuestaModbusTcp resp = null;
        
        if (conectar(peticion.getDireccionIp(), peticion.getPuerto())) {
            
            
            
            desconectar();            
        }
                        
        return resp;
    }
    
    public boolean conectar (String direccionIp, int puerto) throws Exception{                
        
        boolean rsp = false;
        
        try {
            InetAddress addr = InetAddress.getByName(direccionIp);
            //int port = Modbus.DEFAULT_PORT;
            int port = puerto;

            //2. Open the connection
            setCon(new TCPMasterConnection(addr));
            getCon().setPort(port);
            getCon().connect();
            rsp = true;
        } catch (Exception e) {
            throw new Exception("Error al establecer conexión mediane TCP. " + e.getMessage());
        }
        
        return rsp;
        
    }
    
    public void desconectar() {
        //6. Close the connection
        getCon().close();
    }
    
    
    
    
}
