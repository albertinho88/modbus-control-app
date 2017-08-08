/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.servicio;

import dagu.modbus.control.app.servicio.modelo.dto.RespuestaModbusTcp;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author jlarragam
 */
@LocalBean
@Stateless
public class GeneralModbusServicio implements Serializable {
    
    private static final long serialVersionUID = -8980588198701705105L;
    
    public RespuestaModbusTcp enviarTramaFuncion (String codigoFuncion, int referencia, int numeroBits, int numeroPalabras) {
        RespuestaModbusTcp rsp = null;
        
        return rsp;
    }
    
}
