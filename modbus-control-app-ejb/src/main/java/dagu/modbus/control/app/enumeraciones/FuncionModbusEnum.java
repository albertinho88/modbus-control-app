/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.enumeraciones;

import lombok.Getter;

/**
 *
 * @author jlarragam
 */
public enum FuncionModbusEnum {
    
    READ_COILS("1"),
    READ_INPUT_DISCRETES("2"),
    READ_MULTIPLE_REGISTERS("3"),
    READ_INPUT_REGISTERS("4"),
    WRITE_SINGLE_COIL("5"),
    WRITE_SINGLE_REGISTER("6"),
    WRITE_MULTIPLE_COILS("15"),
    WRITE_MULTIPLE_REGISTER("16");
    
    @Getter
    private final String codigo;
    
    private FuncionModbusEnum(String codigo) {
        this.codigo = codigo;
    }
    
}
