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
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
import net.wimpi.modbus.procimg.SimpleRegister;

/**
 *
 * @author jlarragam
 */
@LocalBean
@Stateless
public class TcpModbusServicio implements Serializable {

    private static final long serialVersionUID = -3390713346240429910L;                   
    
    public RespuestaModbusTcp ejecutarFuncion(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        RespuestaModbusTcp resp = null;               
        
        if (con != null) {            
            resp = enviarTramaPorFuncion(peticion, con);
            resp.setCodigoFuncion(peticion.getCodigoFuncion());                                    
        }
                        
        return resp;
    }
    
    public TCPMasterConnection conectar (String direccionIp, int puerto) throws Exception{                
        TCPMasterConnection con = null; //the connection        
        
        try {
            InetAddress addr = InetAddress.getByName(direccionIp);
            //int port = Modbus.DEFAULT_PORT;
            int port = puerto;

            //2. Open the connection
            con = new TCPMasterConnection(addr);
            con.setPort(port);
            con.connect();
            
        } catch (Exception e) {            
            throw new Exception("Error al establecer conexión mediane TCP. " + e.getMessage());            
        }
        
        return con;
        
    }
    
    public void desconectar(TCPMasterConnection con) {
        //6. Close the connection
        con.close();
    }
    
    public RespuestaModbusTcp enviarTramaPorFuncion(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        
        RespuestaModbusTcp rsp = null;
        
        switch (peticion.getCodigoFuncion()) {
            case "1": rsp = readCoilsRequest(peticion, con); break;
            case "2": rsp = readInputDiscretesRequest(peticion, con); break;
            case "3": rsp = readMultipleRegistersRequest(peticion, con); break;
            case "4": rsp = readInputRegistersRequest(peticion, con); break;
            case "5": rsp = writeCoilRequest(peticion, con); break;
            case "6": rsp = writeSingleRegisterRequest(peticion, con); break;
            case "15": rsp = writeMultipleCoilsRequest(peticion, con); break;
            case "16": rsp = writeMultipleRegisterRequest(peticion, con); break;
            default: break;
        }
        
        return rsp;       
    }
    
    private RespuestaModbusTcp readCoilsRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        ReadCoilsRequest req;
        ReadCoilsResponse res;
        ModbusTCPTransaction trans; //the transaction
                  
        //3. Prepare the request
        req = new ReadCoilsRequest(peticion.getReferencia(), peticion.getConteoBits());

        //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        res = (ReadCoilsResponse) trans.getResponse();
        
        return new RespuestaModbusTcp(res.getBitCount(), res.getCoils());                
    }
    
    private RespuestaModbusTcp readInputDiscretesRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        ReadInputDiscretesRequest req; //the request
        ReadInputDiscretesResponse res; //the response
        ModbusTCPTransaction trans; //the transaction
        
        req = new ReadInputDiscretesRequest(peticion.getReferencia(), peticion.getConteoBits());
        
        //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        res = (ReadInputDiscretesResponse) trans.getResponse();
        
        return new RespuestaModbusTcp(res.getBitCount(), res.getDiscretes());
    }
    
    private RespuestaModbusTcp readMultipleRegistersRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        ReadMultipleRegistersRequest req;
        ReadMultipleRegistersResponse res;
        ModbusTCPTransaction trans; //the transaction
        
        req = new ReadMultipleRegistersRequest(peticion.getReferencia(), peticion.getConteoPalabras());
        
        //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        res = (ReadMultipleRegistersResponse) trans.getResponse();
        
        return new RespuestaModbusTcp(res.getByteCount(), res.getRegisters());
    }
    private RespuestaModbusTcp readInputRegistersRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        ReadInputRegistersRequest req;
        ReadInputRegistersResponse res;
        ModbusTCPTransaction trans; //the transaction
        
        req = new ReadInputRegistersRequest(peticion.getReferencia(), peticion.getConteoPalabras());
        
         //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        res = (ReadInputRegistersResponse) trans.getResponse();                
        
        return new RespuestaModbusTcp(res.getByteCount(), res.getRegisters());
    }
    private RespuestaModbusTcp writeCoilRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        WriteCoilRequest req;
        WriteCoilResponse res;
        ModbusTCPTransaction trans; //the transaction
        
        req = new WriteCoilRequest(peticion.getReferencia(), peticion.isCoil());
        
        //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        res = (WriteCoilResponse) trans.getResponse();
                
        return new RespuestaModbusTcp(res.getReference(), res.getCoil());
    }
    private RespuestaModbusTcp writeSingleRegisterRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        WriteSingleRegisterRequest req;
        WriteSingleRegisterResponse res;
        ModbusTCPTransaction trans; //the transaction
        
        
        req = new WriteSingleRegisterRequest(peticion.getReferencia(), new SimpleRegister(peticion.getValorRegistro()));
        
        //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        res = (WriteSingleRegisterResponse) trans.getResponse();

        RespuestaModbusTcp rs = new RespuestaModbusTcp();
        rs.setReferencia(res.getReference());
        rs.setValorRegistro(res.getRegisterValue());
        
        return rs;
    }
    private RespuestaModbusTcp writeMultipleCoilsRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        WriteMultipleCoilsRequest req;
        WriteMultipleCoilsResponse res;
        ModbusTCPTransaction trans; //the transaction
        
        req = new WriteMultipleCoilsRequest(peticion.getReferencia(), peticion.getConteoBits());
        
        //4. Prepare the transaction
        trans = new ModbusTCPTransaction(con);        
        trans.setRequest(req);
        
        trans.execute();
        
        res = (WriteMultipleCoilsResponse) trans.getResponse();
        
        RespuestaModbusTcp rs = new RespuestaModbusTcp();
        rs.setReferencia(res.getReference());
        rs.setConteoBits(res.getBitCount());
        
        return rs;
    }
    private RespuestaModbusTcp writeMultipleRegisterRequest(PeticionModbusTcp peticion, TCPMasterConnection con) throws Exception {
        WriteMultipleRegistersRequest req;
        WriteMultipleRegistersResponse res;
        ModbusTCPTransaction trans; //the transaction
        
        
        //req = new WriteMultipleRegistersRequest(peticion.getReferencia(), )
        
        return null;
    }
    
}
