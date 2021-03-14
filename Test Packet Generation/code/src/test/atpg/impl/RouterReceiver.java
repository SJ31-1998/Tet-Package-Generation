package pro.atpg.impl;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import pro.atpg.bo.Packet;
import pro.atpg.ui.Router;
import pro.atpg.utility.ATPGUtil;

public class RouterReceiver implements Runnable {
	
	public ServerSocket ss;
	
	public int portSelection=1;
	
	public Socket s;
	
	public Sender sender=new Sender();
	
	public String routerName;
	
	public ObjectInputStream ois;
	
	public int port;
	
	
	public RouterReceiver(int port,String name) {
		routerName=name;
		this.port=port;
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
				System.out.println("Start");
				s=ss.accept();
				ois=new ObjectInputStream(s.getInputStream());
				Object obj=ois.readObject();
				
				if(obj instanceof Packet){
					Packet packet=(Packet) obj;	
					Vector<String> vec=new Vector<String>();
					vec.add(""+packet.fileNumber);
					vec.add(""+packet.packetNumber);
					vec.add(packet.sourceName);
					vec.add(packet.fromName);
					vec.add(""+port);
					Router.receiveInfoModel.addRow(vec);
					
					if(packet.type.equalsIgnoreCase("original")){
						ArrayList<String> neigh=ATPGUtil.neighMap.get(routerName);
						for(String str:neigh){
							if(!packet.paths.contains(str)){
								 Packet pac=packet;
								 pac.fromAddress=ATPGUtil.ipMap.get(routerName);
								 pac.toAddress=ATPGUtil.ipMap.get(str);
								 pac.toPort=ATPGUtil.portMap.get(str).get(portSelection-1);
								 pac.toName=str;
								 pac.fromName=routerName;
								 pac.paths.add(routerName);
								 
								 	Vector<String> vec1=new Vector<String>();
									vec1.add(""+packet.fileNumber);
									vec1.add(""+packet.packetNumber);
									vec1.add(packet.sourceName);
									vec1.add(packet.toName);
									vec1.add(""+packet.toPort);
									Router.forwardInfoModel.addRow(vec1);
								 sender.sendPacketObject(pac);
								 
								 if(portSelection>=3){
									 portSelection=1;
								 }else{
									 portSelection+=1;
								 }
							}
						}
						
					}else if(packet.type.equalsIgnoreCase("test")){
						ArrayList<String> neigh=ATPGUtil.neighMap.get(routerName);
						for(String str:neigh){
							if(!packet.paths.contains(str)){
								 Packet pac=packet;
								 pac.fromAddress=ATPGUtil.ipMap.get(routerName);
								 pac.toAddress=ATPGUtil.ipMap.get(str);
								 pac.toPort=ATPGUtil.portMap.get(str).get(portSelection-1);
								 pac.toName=str;
								 pac.fromName=routerName;
								 pac.paths.add(routerName);
								 	Vector<String> vec1=new Vector<String>();
									vec1.add(""+packet.fileNumber);
									vec1.add(""+packet.packetNumber);
									vec1.add(packet.sourceName);
									vec1.add(packet.toName);
									vec1.add(""+packet.toPort);
									Router.forwardInfoModel.addRow(vec1);
								 sender.sendPacketObject(pac);
								 
								 if(portSelection>=3){
									 portSelection=1;
								 }else{
									 portSelection+=1;
								 }
							}
						}
						
					}	
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
