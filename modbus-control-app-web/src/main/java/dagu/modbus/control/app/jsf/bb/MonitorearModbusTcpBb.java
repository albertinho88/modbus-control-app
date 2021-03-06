/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.jsf.bb;

import java.io.Serializable;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;
import net.wimpi.modbus.facade.ModbusTCPMaster;

/**
 *
 * @author Alberto
 */
@Dependent
public class MonitorearModbusTcpBb implements Serializable {

    private static final long serialVersionUID = 3119330952319407673L;        
    
    @Getter
    @Setter
    private String direccionIp;
    
    @Getter
    @Setter
    private String puerto;
    
    @Getter
    @Setter
    private String codigoFuncion;
    
    @Getter
    @Setter
    private String referencia;
    
    @Getter
    @Setter
    private String conteoBits;
    
    @Getter
    @Setter
    private String respuesta;
    
    @Getter
    @Setter
    private ModbusTCPMaster modbusTcpMaster;
    
    public void inicializar() {
        setDireccionIp("127.0.0.1");
        setPuerto("502");
        setCodigoFuncion(null);
        setReferencia(null);
        setConteoBits(null);
        setRespuesta(null);
        setModbusTcpMaster(null);
    }
}
