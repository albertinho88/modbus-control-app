/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagu.modbus.control.app.servicio;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

/**
 *
 * @author Alberto
 */
@LocalBean
@Stateless
public class MonitorearDispositivosModbusServicio implements Serializable{

    private static final long serialVersionUID = 4350764010488632524L;        
    
    public void probar() {
        try {
            
            /* The important instances of the classes mentioned before */
            TCPMasterConnection con = null; //the connection
            ModbusTCPTransaction trans = null; //the transaction
            ReadInputDiscretesRequest req = null; //the request
            ReadInputDiscretesResponse res = null; //the response

            /* Variables for storing the parameters */
            InetAddress addr = null; //the slave's address
            int port = Modbus.DEFAULT_PORT;
            int ref = 0; //the reference; offset where to start reading from
            int count = 0; //the number of DI's to read
            int repeat = 1; //a loop for repeating the transaction
            
            String[] args = new String[5];
            args[0] = "localhost:502";
            args[1] = "1";
            args[2] = "3";
            args[3] = "4";
            
            //1. Setup the parameters
            if (args.length < 3) {
              System.exit(1);
            } else {
              try {
                String astr = args[0];
                int idx = astr.indexOf(':');
                if(idx > 0) {
                  port = Integer.parseInt(astr.substring(idx+1));
                  astr = astr.substring(0,idx);
                }
                addr = InetAddress.getByName(astr);
                ref = Integer.decode(args[1]).intValue();
                count = Integer.decode(args[2]).intValue();
                if (args.length == 4) {
                  repeat = Integer.parseInt(args[3]);
                }
              } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
              }
            }
            
            //2. Open the connection
            con = new TCPMasterConnection(addr);
            //con.setPort(port);
            /*con.connect();

            //3. Prepare the request
            req = new ReadInputDiscretesRequest(ref, count);

            //4. Prepare the transaction
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);*/
            
            Logger.getLogger(MonitorearDispositivosModbusServicio.class.getName()).log(Level.INFO, "testing.....");
            
        } catch(Exception e) {
            Logger.getLogger(MonitorearDispositivosModbusServicio.class.getName()).log(Level.SEVERE, "Error, " + e.getMessage());
        }  
    }
    
}
