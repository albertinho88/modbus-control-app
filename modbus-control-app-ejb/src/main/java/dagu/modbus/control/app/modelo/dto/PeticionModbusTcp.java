/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.modelo.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jlarragam
 */
public class PeticionModbusTcp implements Serializable {
    
    private static final long serialVersionUID = -7854148392717815639L;
    
    @Getter
    @Setter
    private String direccionIp;
    
    @Getter
    @Setter
    private int puerto;
    
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
    private int conteoPalabras;
}
