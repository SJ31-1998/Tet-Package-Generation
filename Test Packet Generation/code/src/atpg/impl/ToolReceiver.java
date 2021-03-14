package pro.atpg.impl;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import pro.atpg.bo.ErrorObject;
import pro.atpg.bo.Packet;
import pro.atpg.bo.Ticket;
import pro.atpg.ui.Tool;
import pro.atpg.utility.ATPGUtil;

public class ToolReceiver implements Runnable {
	
	public ServerSocket ss;
	
	public Socket s;
	
	public ObjectInputStream ois;
	
	public ArrayList<Integer> receiveError;
	
	public ArrayList<Integer> receiveErrorTest;
	
	public static ArrayList<String> resultList;
	
	public Sender sender=new Sender();  
	
	public static HashMap<String,ArrayList<Packet>> errorMap; 
	
	public ToolReceiver(int port) {
		receiveError=new ArrayList<Integer>();  
		receiveErrorTest=new ArrayList<Integer>();
		resultList=new ArrayList<String>();
		errorMap=new HashMap<String,ArrayList<Packet> >();
		try{
			ss=new ServerSocket(port);
			Thread thread=new Thread(this);
			thread.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try{
			while(true){
				s=ss.accept();
				ois=new ObjectInputStream(s.getInputStream());
				Object obj=ois.readObject();
				System.out.println("Receive packet");
				if(obj instanceof Packet){
						
				}else if(obj instanceof ErrorObject){
					ErrorObject error=(ErrorObject) obj;
					if(error.packet.type.equalsIgnoreCase("original")){
						
						if(!receiveError.contains(error.packet.fileNumber)){
							receiveError.add(error.packet.fileNumber);
							String nodeName=error.packet.sourceName;
							int portSelection=1;
							int fileNumber=new Random().nextInt(100);
							for(int i=0;i<100;i++){
								ArrayList<String> neigh=ATPGUtil.neighMap.get(nodeName);
								for(String str:neigh){
									 Packet pac=new Packet();
									 pac.sourceAddress=ATPGUtil.ipMap.get(nodeName);
									 pac.sourceName=nodeName;
									 pac.fromAddress=ATPGUtil.ipMap.get(nodeName);
									 pac.fromName=nodeName;
									 pac.toAddress=ATPGUtil.ipMap.get(str);
									 pac.toPort=ATPGUtil.portMap.get(str).get(portSelection-1);
									 pac.toName=str;
									 pac.destinationAddress=error.packet.destinationAddress;
									 pac.destinationName=error.packet.destinationName;
									 pac.fileNumber=fileNumber;
									 pac.packetNumber=(i+1);
									 pac.packetData=ATPGUtil.testContent;
									 pac.type="test";
									 pac.fileSize=ATPGUtil.testContent.length();
									 pac.toalPacketSize=100;
									 pac.paths=new ArrayList<String>();
									 pac.paths.add(nodeName);
									 sender.sendPacketObject(pac);
									 
									 if(portSelection>=3){
										 portSelection=1;
									 }else{
										 portSelection+=1;
									 }
								}
							}
							
							
						}
						
					}else if(error.packet.type.equalsIgnoreCase("test")){
						if(!receiveErrorTest.contains(error.packet.fileNumber)){
							receiveErrorTest.add(error.packet.fileNumber);
							Vector<String> vec=new Vector<String>();
							vec.add(""+error.packet.fileNumber);
							vec.add(""+error.packet.sourceAddress+":"+error.packet.sourceName);
							vec.add(""+error.packet.destinationAddress+":"+error.packet.destinationName);
							vec.add(""+error.packet.fromName);
							vec.add(""+error.packet.toName);
							resultList.add(""+error.packet.fileNumber);
							Tool.problemListModel.addRow(vec);
							if(errorMap.containsKey(error.packet.sourceName)){
								ArrayList<Packet> pacList=errorMap.get(error.packet.sourceName);
								pacList.add(error.packet);
								errorMap.put(error.packet.sourceName,pacList);
							}else{
								ArrayList<Packet> pacList=new ArrayList<Packet>();
								pacList.add(error.packet);
								errorMap.put(error.packet.sourceName,pacList);
							}
						}
					
					}
				}else if(obj instanceof Ticket){
					Ticket ticket=(Ticket)obj;
					Vector<String> vec=new Vector<String>();
					vec.add(""+ticket.ticketNumber);
					vec.add(ticket.title);
					vec.add(ticket.sourceName);
					vec.add(ticket.description);
					Tool.ticketListModel.addRow(vec);
				}else if(obj instanceof String){
					if(!resultList.contains(obj)){
						resultList.add(""+obj);
						JOptionPane.showMessageDialog(null,"Test packets sent successfully");
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
