package pro.atpg.bo;

import java.io.Serializable;

public class ErrorObject implements Serializable {

	public String toAddress;
	
	public int toPort;
	
	public Packet packet;
	
}
