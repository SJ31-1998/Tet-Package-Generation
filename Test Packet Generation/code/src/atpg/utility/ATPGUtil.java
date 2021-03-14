package pro.atpg.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ATPGUtil {

	
	public static String TOOL_IP;
	
	public static int TOOL_PORT;
	
	public static String testSourceName;
	
	public static String testDestinationName;
	
	public static HashMap<String,String> ipMap;
	
	public static HashMap<String,ArrayList<String>> neighMap;
	
	public static HashMap<String,ArrayList<Integer>> portMap;
	
	public static String testContent="ABCD EFGH IJKL MNOP QRST UVWX YZ";
	
	public ATPGUtil() {
		int total=100;
		for(int i=0;i<total;i++){
			testContent+="::";
		}
		
		ipMap=new HashMap<String, String>();
		neighMap=new HashMap<String, ArrayList<String>>();
		portMap=new HashMap<String, ArrayList<Integer>>();
		try{
			Properties prop=new Properties();
			InputStream input=new FileInputStream("config.properties");
			prop.load(input);
			String routers[]=prop.getProperty("router").split(",");
			String nodes[]=prop.getProperty("node").split(",");
			
			for(String router:routers){
				ipMap.put(router,prop.getProperty("IP"+router));
				
				ArrayList<String> tempNeigh=new ArrayList<String>();
				if(prop.getProperty("Neigh"+router).indexOf(",")!=-1){
					for(String nei:prop.getProperty("Neigh"+router).split(",")){
						tempNeigh.add(nei);
					}
					neighMap.put(router, tempNeigh);
				}else{
					tempNeigh.add(prop.getProperty("Neigh"+router));
					neighMap.put(router, tempNeigh);
				}
				
				ArrayList<Integer> tempPort=new ArrayList<Integer>();
				if(prop.getProperty("Port"+router).indexOf(",")!=-1){
					for(String port:prop.getProperty("Port"+router).split(",")){
						tempPort.add(Integer.parseInt(port));
					}
					portMap.put(router, tempPort);
				}else{
					tempPort.add(Integer.parseInt(prop.getProperty("Port"+router)));
					portMap.put(router, tempPort);
				}
			}
			
			for(String node:nodes){
				ipMap.put(node,prop.getProperty("IP"+node));
				
				ArrayList<String> tempNeigh=new ArrayList<String>();
				if(prop.getProperty("Neigh"+node).indexOf(",")!=-1){
					for(String nei:prop.getProperty("Neigh"+node).split(",")){
						tempNeigh.add(nei);
					}
					neighMap.put(node, tempNeigh);
				}else{
					tempNeigh.add(prop.getProperty("Neigh"+node));
					neighMap.put(node, tempNeigh);
				}
				
				ArrayList<Integer> tempPort=new ArrayList<Integer>();
				if(prop.getProperty("Port"+node).indexOf(",")!=-1){
					for(String port:prop.getProperty("Port"+node).split(",")){
						tempPort.add(Integer.parseInt(port));
					}
					portMap.put(node, tempPort);
				}else{
					tempPort.add(Integer.parseInt(prop.getProperty("Port"+node)));
					portMap.put(node, tempPort);
				}
			}
			
			TOOL_IP=prop.getProperty("ToolIP");
			TOOL_PORT=Integer.parseInt(prop.getProperty("ToolPort"));
			testDestinationName=prop.getProperty("TestDestination");
			testSourceName=prop.getProperty("TestSource");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		File file=new File("config.properties");
		System.out.println(file.getAbsolutePath());
		new ATPGUtil();
	}
	
}
