package pro.atpg.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {
	
	public String type="test";
	
	public int fileNumber;
	
	public int fileSize;
	
	public int packetNumber;
	
	public int packetSize;
	
	public String packetData;
	
	public int toalPacketSize;
	
	public String sourceAddress;
	
	public String sourceName;
	
	public String fromAddress;
	
	public String fromName;
	
	public int fromPort;
	
	public String toAddress;
	
	public int toPort;
	
	public String toName;
	
	public String destinationAddress;
	
	public String destinationName;
	
	public ArrayList<String> paths; 
	
}
