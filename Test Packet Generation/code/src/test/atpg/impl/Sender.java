package pro.atpg.impl;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import pro.atpg.bo.ErrorObject;
import pro.atpg.bo.Packet;
import pro.atpg.bo.Ticket;
import pro.atpg.utility.ATPGUtil;

public class Sender {

	public Socket s;
	
	public ObjectOutputStream oos;
	
	public String sendPacketObject(Packet obj){
		try{
			s=new Socket(obj.toAddress,obj.toPort);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			return("success");
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Connection Failure");
			ErrorObject error=new ErrorObject();
			error.toAddress=ATPGUtil.TOOL_IP;
			error.toPort=ATPGUtil.TOOL_PORT;
			error.packet=obj;
			sendErrorObject(error);
			return("");
		}
	}
	
	public String sendTicketObject(Ticket obj){
		try{
			s=new Socket(obj.toAddress,obj.toPort);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			return("success");
		}catch (Exception e) {
			e.printStackTrace();
			return("");
		}
	}
	
	public String sendErrorObject(ErrorObject obj){
		try{
			s=new Socket(obj.toAddress,obj.toPort);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			return("success");
		}catch (Exception e) {
			e.printStackTrace();
			return("");
		}
	}
	public void sendSuccess(String ip,int port,String msg){
		try{
			s=new Socket(ip,port);
			oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
