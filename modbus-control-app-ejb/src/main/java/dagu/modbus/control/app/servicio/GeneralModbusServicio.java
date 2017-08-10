/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.servicio;

import dagu.modbus.control.app.enumeraciones.FuncionModbusEnum;
import dagu.modbus.control.app.modelo.dto.RespuestaModbusTcp;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import net.wimpi.modbus.util.BitVector;

/**
 *
 * @author jlarragam
 */
@LocalBean
@Stateless
public class GeneralModbusServicio implements Serializable {
    
    private static final long serialVersionUID = -8980588198701705105L;
    
    public String obtenerRespuestaPorFuncion (RespuestaModbusTcp resp) throws Exception {
        String respuesta = "";
        
        if (FuncionModbusEnum.READ_COILS.getCodigo().equals(resp.getCodigoFuncion())) {
            respuesta += "Bit Count: ".concat(String.valueOf(resp.getConteoBits()));
            respuesta += "\r\n";
            
            respuesta += "Data: ".concat(resp.getVectorBit().toString());
            
            //for (int i=0; i<=resp.getVectorBit().size(); i++) {
                
            //}
            
            
        }
        
        return respuesta;
    }
    
}
