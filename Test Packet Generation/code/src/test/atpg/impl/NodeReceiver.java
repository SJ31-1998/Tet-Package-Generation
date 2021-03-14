package pro.atpg.impl;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import pro.atpg.bo.Packet;
import pro.atpg.ui.Node;
import pro.atpg.ui.Router;
import pro.atpg.utility.ATPGUtil;

public class NodeReceiver implements Runnable{

	public ServerSocket ss;
	
	public String nodeName;
	
	public Socket s;
	
	public ObjectInputStream ois;
	
	public Sender sender=new Sender();
	
	public static HashMap<Integer,HashMap<Integer,Packet>> contentMap;
	
	public static ArrayList<String> resultList;
	
	public int port;
	
	
	public NodeReceiver(int port,String name) {
		this.port=port;
		nodeName=name;
		contentMap=new HashMap<Integer, HashMap<Integer,Packet>>();
		resultList=new ArrayList<String>();
		try{
			ss=new ServerSocket(port);
			Thread thread=new Thread(this);
			thread.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		System.out.println("Start");
		try{
			while(true){
				s=ss.accept();
				ois=new ObjectInputStream(s.getInputStream());
				Object obj=ois.readObject();
				
				if(obj instanceof Packet){
					Packet packet=(Packet) obj;	
					
					if(packet.type.equalsIgnoreCase("original")){
						sender.sendSuccess(packet.sourceAddress,ATPGUtil.portMap.get(packet.sourceName).get(1),""+packet.fileNumber);
						Vector<String> vec=new Vector<String>();
						vec.add(""+packet.fileNumber);
						vec.add(""+packet.packetNumber);
						vec.add(packet.sourceName);
						vec.add(packet.fromName);
						vec.add(""+port);
						if(nodeName.equalsIgnoreCase(packet.destinationName)){
							Node.receiveInfoModel.addRow(vec);
						}
						
					}else if(packet.type.equalsIgnoreCase("test")){
						
						if(contentMap.containsKey(packet.fileNumber)){
							HashMap<Integer,Packet> pacMap=contentMap.get(packet.fileNumber);
							if(!pacMap.containsKey(packet.packetNumber)){
								pacMap.put(packet.packetNumber,packet);
								contentMap.put(packet.fileNumber,pacMap);
							}
							
							//fully received
							if(pacMap.size()==packet.toalPacketSize){
								sender.sendSuccess(ATPGUtil.TOOL_IP,ATPGUtil.TOOL_PORT,""+packet.fileNumber);
							}
							
						}else{
							HashMap<Integer,Packet> pacMap=new HashMap<Integer, Packet>();
							pacMap.put(packet.packetNumber,packet);
							contentMap.put(packet.fileNumber,pacMap);
						}
						
					}
				}else if(obj instanceof String){
					if(!resultList.contains(obj)){
						resultList.add(""+obj);
						JOptionPane.showMessageDialog(null,"Successfully Sent");
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
