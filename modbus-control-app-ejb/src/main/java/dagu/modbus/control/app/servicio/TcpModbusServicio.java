/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.servicio;

import dagu.modbus.control.app.modelo.dto.PeticionModbusTcp;
import dagu.modbus.control.app.modelo.dto.RespuestaModbusTcp;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.Getter;
import lombok.Setter;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.WriteCoilResponse;
import net.wimpi.modbus.msg.WriteMultipleCoilsRequest;
import net.wimpi.modbus.msg.WriteMultipleCoilsResponse;
import net.wimpi.modbus.msg.WriteMultipleRegistersRequest;
import net.wimpi.modbus.msg.WriteMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterResponse;
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
            
            resp = enviarTramaPorFuncion(peticion);
            resp.setCodigoFuncion(peticion.getCodigoFuncion());
            
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
    
    private RespuestaModbusTcp enviarTramaPorFuncion(PeticionModbusTcp peticion) throws Exception {
        
        RespuestaModbusTcp rsp = null;
        
        switch (peticion.getCodigoFuncion()) {
            case "1": rsp = readCoilsRequest(peticion); break;
            case "2": rsp = readInputDiscretesRequest(peticion); break;
            case "3": rsp = readMultipleRegistersRequest(peticion); break;
            case "4": rsp = readInputRegistersRequest(peticion); break;
            case "5": rsp = writeCoilRequest(peticion); break;
            case "6": rsp = writeSingleRegisterRequest(peticion); break;
            case "15": rsp = writeMultipleCoilsRequest(peticion); break;
            case "16": rsp = writeMultipleRegisterRequest(peticion); break;
            default: break;
        }
        
        return rsp;       
    }
    
    private RespuestaModbusTcp readCoilsRequest(PeticionModbusTcp peticion) throws Exception {
        ReadCoilsRequest req = null;
        ReadCoilsResponse res = null;
                  
        //3. Prepare the request
        req = new ReadCoilsRequest(peticion.getReferencia(), peticion.getConteoBits());

        //4. Prepare the transaction
        setTrans(new ModbusTCPTransaction(getCon()));        
        getTrans().setRequest(req);
        
        getTrans().execute();
        res = (ReadCoilsResponse) getTrans().getResponse();
        
        return new RespuestaModbusTcp(res.getBitCount(), res.getCoils());                
    }
    
    private RespuestaModbusTcp readInputDiscretesRequest(PeticionModbusTcp peticion) throws Exception {
        ReadInputDiscretesRequest req = null; //the request
        ReadInputDiscretesResponse res = null; //the response
        
        req = new ReadInputDiscretesRequest(peticion.getReferencia(), peticion.getConteoBits());
        
        //4. Prepare the transaction
        setTrans(new ModbusTCPTransaction(getCon()));        
        getTrans().setRequest(req);
        
        getTrans().execute();
        res = (ReadInputDiscretesResponse) getTrans().getResponse();
        
        return new RespuestaModbusTcp(res.getBitCount(), res.getDiscretes());
    }
    
    private RespuestaModbusTcp readMultipleRegistersRequest(PeticionModbusTcp peticion) throws Exception {
        ReadMultipleRegistersRequest req = null;
        ReadMultipleRegistersResponse res = null;
        
        req = new ReadMultipleRegistersRequest(peticion.getConteoBits(), peticion.getConteoPalabras());
        
        //4. Prepare the transaction
        setTrans(new ModbusTCPTransaction(getCon()));        
        getTrans().setRequest(req);
        
        getTrans().execute();
        res = (ReadMultipleRegistersResponse) getTrans().getResponse();
        
        return new ;
    }
    private RespuestaModbusTcp readInputRegistersRequest(PeticionModbusTcp peticion) throws Exception {
        ReadInputRegistersRequest req = null;
        ReadInputRegistersResponse res = null;
        
        return null;
    }
    private RespuestaModbusTcp writeCoilRequest(PeticionModbusTcp peticion) throws Exception {
        WriteCoilRequest req = null;
        WriteCoilResponse res = null;
        
        return null;
    }
    private RespuestaModbusTcp writeSingleRegisterRequest(PeticionModbusTcp peticion) throws Exception {
        WriteSingleRegisterRequest req = null;
        WriteSingleRegisterResponse res = null;
        
        return null;
    }
    private RespuestaModbusTcp writeMultipleCoilsRequest(PeticionModbusTcp peticion) throws Exception {
        WriteMultipleCoilsRequest req = null;
        WriteMultipleCoilsResponse res = null;
        
        return null;
    }
    private RespuestaModbusTcp writeMultipleRegisterRequest(PeticionModbusTcp peticion) throws Exception {
        WriteMultipleRegistersRequest req = null;
        WriteMultipleRegistersResponse res = null;
        
        return null;
    }
    
}
