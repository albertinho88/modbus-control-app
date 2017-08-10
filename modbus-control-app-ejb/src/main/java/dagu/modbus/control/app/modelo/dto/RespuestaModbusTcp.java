/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.modelo.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.util.BitVector;

/**
 *
 * @author jlarragam
 */
public class RespuestaModbusTcp implements Serializable {
    
    private static final long serialVersionUID = -5228101540169537139L;
    
    @Getter
    @Setter
    private String codigoFuncion;
    
    @Getter
    @Setter
    private int referencia;
    
    @Getter
    @Setter
    private int conteoBits;
    
    @Getter
    @Setter
    private BitVector vectorBit;
    
    @Getter
    @Setter
    private InputRegister[] registrosEntrada;
    
    @Getter
    @Setter
    private Register[] registros;
    
    public RespuestaModbusTcp (int conteoBits, BitVector vectorBit) {        
        setConteoBits(conteoBits);
        setVectorBit(vectorBit);
    }
    
}
