/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.jsf.controlador;

import dagu.modbus.control.app.enumeraciones.FuncionModbusEnum;
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
import lombok.Setter;
import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.util.BitVector;

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
    private TcpModbusServicio tcpModbusServicio;
    
    @EJB
    @Getter
    private GeneralModbusServicio generalModbusServicio;
    
    
    
    @PostConstruct
    public void init() {
        getBb().inicializar();
        getBb().setRespuesta("");
    }
    
    public void iniciarComunicacion() {
        
        try {
            
            getBb().setModbusTcpMaster(new ModbusTCPMaster(getBb().getDireccionIp(), Integer.parseInt(getBb().getPuerto())));            
            getBb().getModbusTcpMaster().connect();
            
            PeticionModbusTcp peticion = new PeticionModbusTcp();
            peticion.setDireccionIp(getBb().getDireccionIp());
            peticion.setPuerto(Integer.parseInt(getBb().getPuerto()));
            peticion.setCodigoFuncion(getBb().getCodigoFuncion());
            peticion.setReferencia(Integer.parseInt(getBb().getReferencia()));
            peticion.setConteoBits(Integer.parseInt(getBb().getConteoBits()));
            
            String rsp = "";
            
            if (FuncionModbusEnum.READ_COILS.getCodigo().equals(getBb().getCodigoFuncion())) {
                rsp  =  getBb().getModbusTcpMaster().readCoils(peticion.getReferencia(), peticion.getConteoBits()).toString();                
            } else if (FuncionModbusEnum.READ_INPUT_DISCRETES.getCodigo().equals(getBb().getCodigoFuncion())) {
                rsp = getBb().getModbusTcpMaster().readInputDiscretes(peticion.getReferencia(), peticion.getConteoBits()).toString(); 
            } else if (FuncionModbusEnum.READ_MULTIPLE_REGISTERS.getCodigo().equals(getBb().getCodigoFuncion())) {
                Register[] rs = getBb().getModbusTcpMaster().readMultipleRegisters(peticion.getReferencia(), peticion.getConteoBits());
                
                for(Register r : rs) {
                    rsp += r.getValue() + "\r\n";
                }
                
            } else if (FuncionModbusEnum.READ_INPUT_REGISTERS.getCodigo().equals(getBb().getCodigoFuncion())) {
                InputRegister[] irs = getBb().getModbusTcpMaster().readInputRegisters(peticion.getReferencia(), peticion.getConteoBits());
                
                for (InputRegister ir : irs) {
                    rsp += ir.getValue() + "\r\n";                                                            
                }
            } else if (FuncionModbusEnum.WRITE_COIL.getCodigo().equals(getBb().getCodigoFuncion())) {
                
            }
            
            /*switch (getBb().getCodigoFuncion()) {                                                                
                case "5": rsp = writeCoilRequest(peticion, con); break;
                case "6": rsp = writeSingleRegisterRequest(peticion, con); break;
                case "15": rsp = writeMultipleCoilsRequest(peticion, con); break;
                case "16": rsp = writeMultipleRegisterRequest(peticion, con); break;
                default: break;
            }*/
            
            getBb().setRespuesta(getBb().getRespuesta() + rsp + "\r\n");

        } catch (Exception e) {
            ponerMensajeError("Error al establecer comunicación. " + e.getMessage());
        }        
        
    }
    
    public void desconectar() {
        if (getBb().getModbusTcpMaster() != null) {
            getBb().getModbusTcpMaster().disconnect();
        }
    }
    
    public void enviarTrama() {
        TCPMasterConnection con;
        
        PeticionModbusTcp peticion = new PeticionModbusTcp();
        peticion.setDireccionIp(getBb().getDireccionIp());
        peticion.setPuerto(Integer.parseInt(getBb().getPuerto()));
        peticion.setCodigoFuncion(getBb().getCodigoFuncion());
        peticion.setReferencia(Integer.parseInt(getBb().getReferencia()));
        peticion.setConteoBits(Integer.parseInt(getBb().getConteoBits()));
                
        try {
            
            con = getTcpModbusServicio().conectar(peticion.getDireccionIp(), peticion.getPuerto());
            RespuestaModbusTcp resp = getTcpModbusServicio().ejecutarFuncion(peticion, con);
            getTcpModbusServicio().desconectar(con);            
            getBb().setRespuesta(getGeneralModbusServicio().obtenerRespuestaPorFuncion(resp));                        
            
        } catch(Exception e) {
            Logger.getLogger(MonitorearModbusTcpControlador.class.getName()).log(Level.INFO, "Error! " + e.getMessage());
            ponerMensajeError(e.getMessage());
        }
    }
    
    public void onTimeout(){  
        ponerMensajeError("BOOM");        
    }  
    
}
