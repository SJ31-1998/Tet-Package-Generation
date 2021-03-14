package pro.atpg.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import pro.atpg.bo.Packet;
import pro.atpg.bo.Ticket;
import pro.atpg.impl.NodeReceiver;
import pro.atpg.impl.Sender;
import pro.atpg.utility.ATPGUtil;

public class Node implements ActionListener {
	
	public int portSelection=1;
	
	public String nodeName; 
	
	public static int WIDTH=1200;
	
	public static int HEIGHT=700;
	
	public JFrame mainform;
	
	public JPanel leftPanel;
	
	public JPanel rightPanel;
	
	public JPanel leftTopPanel;
	
	public JPanel leftBottomPanel;
	
	public JPanel rightTopPanel;
	
	public JPanel rightBottomPanel;
	
	/*****  Left Panel Top components  *****/
	
	public JLabel destinationLabel;
	
	public JTextField destinationText;
	
	public JLabel dataLabel;
	
	public JTextArea dataText;
	
	public JButton sendButton;
	
	public JButton clearButton;

	/*****  Left Panel Bottom components  *****/
	
	public JLabel ticketTitleLabel;
	
	public JTextField ticketTitleText;
	
	public JLabel ticketDescLabel;
	
	public JTextArea ticketDescText;
	
	public JButton ticketSendButton;
	
	public JButton ticketClearButton;
	
	/***** Right Panel Top components*****/
	
	public JLabel sendInfoLabel;
	
	public JTable sendInfoTable;
	
	public static DefaultTableModel sendInfoModel;
	
	public JScrollPane sendInfoScroll;
	
	/***** Right Panel Bottom components*****/
	
	public JLabel receiveInfoLabel;
	
	public JTable receiveInfoTable;
	
	public static DefaultTableModel receiveInfoModel;
	
	public JScrollPane receiveInfoScroll;
	
	public Sender sender=new Sender();
	
	
	static {
	try {
		SubstanceLookAndFeel
				.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
		SubstanceLookAndFeel
				.setCurrentTheme("org.jvnet.substance.theme.SubstanceInvertedTheme");
		SubstanceLookAndFeel
				.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
		SubstanceLookAndFeel
				.setCurrentButtonShaper("org.jvnet.substance.button.ClassicButtonShaper");
		UIManager.setLookAndFeel(new SubstanceLookAndFeel());
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
	
	public void mainMethod(){
		new ATPGUtil();
		while(true){
			String inp=JOptionPane.showInputDialog("Enter the Node Name");
			if(inp!=null && inp.length()>0){
				nodeName=inp.toUpperCase();
				break;
			}
		}
		for(int port:ATPGUtil.portMap.get(nodeName)){
			new NodeReceiver(port,nodeName);
		}
		mainform=new JFrame("Node : "+nodeName);
		mainform.setLayout(new GridLayout(1,2));
		
		mainform.add(leftPanelUI());
		mainform.add(rightPanelUI());
		
		mainform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainform.setResizable(false);
		mainform.setSize(WIDTH,HEIGHT);
		mainform.setVisible(true);
	}
	
	public JPanel leftPanelUI(){
		
		leftPanel=new JPanel();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		leftPanel.setLayout(new GridLayout(2,1));
		
		leftPanel.add(leftTopPanelUI());
		leftPanel.add(leftBottomPanelUI());
		
		return(leftPanel);
	}
	
	public JPanel rightPanelUI(){
	
		rightPanel=new JPanel();
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		rightPanel.setLayout(new GridLayout(2,1));
		
		rightPanel.add(rightTopPanelUI());
		rightPanel.add(rightBottomPanelUI());
		
		return(rightPanel);
	}
	
	public JPanel leftTopPanelUI(){
		
		leftTopPanel=new JPanel();
		leftTopPanel.setBorder(BorderFactory.createTitledBorder("Data Send"));
		leftTopPanel.setLayout(null);
		
		destinationLabel=new JLabel("Destination");
		destinationLabel.setBounds(30,30,100,25);
		
		destinationText=new JTextField();
		destinationText.setBounds(30,60,350,25);
		
		dataLabel=new JLabel("Data");
		dataLabel.setBounds(30,95,100,25);
		
		dataText=new JTextArea();
		dataText.setBounds(30,125,350,180);
		
		sendButton=new JButton("Send");
		sendButton.setBounds(400,125,100,25);
		sendButton.addActionListener(this);
		
		clearButton=new JButton("Clear");
		clearButton.setBounds(400,170,100,25);
		clearButton.addActionListener(this);
		
		leftTopPanel.add(destinationLabel);
		leftTopPanel.add(destinationText);
		leftTopPanel.add(dataLabel);
		leftTopPanel.add(dataText);
		leftTopPanel.add(sendButton);
		leftTopPanel.add(clearButton);
		
		return(leftTopPanel);
	}
	
	public JPanel leftBottomPanelUI(){
	
		leftBottomPanel=new JPanel();
		leftBottomPanel.setBorder(BorderFactory.createTitledBorder("Ticket Send"));
		leftBottomPanel.setLayout(null);
		
		ticketTitleLabel=new JLabel("Title");
		ticketTitleLabel.setBounds(30,30,100,25);
		
		ticketTitleText=new JTextField();
		ticketTitleText.setBounds(30,60,350,25);
		
		ticketDescLabel=new JLabel("Description");
		ticketDescLabel.setBounds(30,95,100,25);
		
		ticketDescText=new JTextArea();
		ticketDescText.setBounds(30,125,350,180);
		
		ticketSendButton=new JButton("Post");
		ticketSendButton.setBounds(400,125,100,25);
		ticketSendButton.addActionListener(this);
		
		ticketClearButton=new JButton("Clear");
		ticketClearButton.setBounds(400,170,100,25);
		ticketClearButton.addActionListener(this);
		
		leftBottomPanel.add(ticketTitleLabel);
		leftBottomPanel.add(ticketTitleText);
		leftBottomPanel.add(ticketDescLabel);
		leftBottomPanel.add(ticketDescText);
		leftBottomPanel.add(ticketSendButton);
		leftBottomPanel.add(ticketClearButton);
		
		return(leftBottomPanel);
	}
	
	public JPanel rightTopPanelUI(){
		
		rightTopPanel=new JPanel();
		rightTopPanel.setLayout(null);
		
		sendInfoLabel=new JLabel("Sending Packet Information");
		sendInfoLabel.setBounds(20,20,200,25);
		
		sendInfoModel=new DefaultTableModel();
		sendInfoTable=new JTable(sendInfoModel);
		sendInfoModel.addColumn("File Number");
		sendInfoModel.addColumn("Packer Number");
		sendInfoModel.addColumn("Destination Address");
		sendInfoModel.addColumn("Forward Address");
		sendInfoModel.addColumn("Forward Port");
		
		sendInfoScroll=new JScrollPane(sendInfoTable);
		sendInfoScroll.setBounds(20,50,550,250);
		
		rightTopPanel.add(sendInfoScroll);
		rightTopPanel.add(sendInfoLabel);
		return(rightTopPanel);
	}
	
	
	public JPanel rightBottomPanelUI(){
		rightBottomPanel=new JPanel();
		rightBottomPanel.setLayout(null);
		
		receiveInfoLabel=new JLabel("Receiving Packet Information");
		receiveInfoLabel.setBounds(20,20,200,25);
		
		receiveInfoModel=new DefaultTableModel();
		receiveInfoTable=new JTable(receiveInfoModel);
		receiveInfoModel.addColumn("File Number");
		receiveInfoModel.addColumn("Packer Number");
		receiveInfoModel.addColumn("Source Address");
		receiveInfoModel.addColumn("Receiving Address");
		receiveInfoModel.addColumn("Receiving Port");
		
		receiveInfoScroll=new JScrollPane(receiveInfoTable);
		receiveInfoScroll.setBounds(20,50,550,250);
		
		rightBottomPanel.add(receiveInfoScroll);
		rightBottomPanel.add(receiveInfoLabel);
		
		return(rightBottomPanel);
	}
	public static void main(String args[]){
		Node node=new Node();
		node.mainMethod();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object action=e.getSource();
		
		if(action==sendButton){
			String destination=destinationText.getText();
			String content=dataText.getText();
			
			ArrayList<String> neigh=ATPGUtil.neighMap.get(nodeName);
			
			for(String str:neigh){
				 Packet pac=new Packet();
				 pac.sourceAddress=ATPGUtil.ipMap.get(nodeName);
				 pac.sourceName=nodeName;
				 pac.fromAddress=ATPGUtil.ipMap.get(nodeName);
				 pac.fromName=nodeName;
				 pac.toAddress=ATPGUtil.ipMap.get(str);
				 pac.toName=str;
				 pac.toPort=ATPGUtil.portMap.get(str).get(portSelection-1);
				 pac.destinationAddress=ATPGUtil.ipMap.get(destination);
				 pac.destinationName=destination;
				 pac.fileNumber=new Random().nextInt(100);
				 pac.packetNumber=1;
				 pac.packetData=content;
				 pac.type="original";
				 pac.fileSize=content.length();
				 pac.toalPacketSize=1;
				 pac.paths=new ArrayList<String>(); 
				 pac.paths.add(nodeName);
				 	Vector<String> vec1=new Vector<String>();
					vec1.add(""+pac.fileNumber);
					vec1.add(""+pac.packetNumber);
					vec1.add(pac.sourceName);
					vec1.add(pac.toName);
					vec1.add(""+pac.toPort);
					Node.sendInfoModel.addRow(vec1);
				 sender.sendPacketObject(pac);
				 
				 if(portSelection>=3){
					 portSelection=1;
				 }else{
					 portSelection+=1;
				 }
			}
			
			
		}else if(action==clearButton){
			destinationText.setText("");
			dataText.setText("");
		}else if(action==ticketSendButton){
			String title=ticketTitleText.getText();
			String desc=ticketDescText.getText();
			Ticket ticket=new Ticket();
			ticket.sourceName=nodeName;
			ticket.ticketNumber=new Random().nextInt(100);
			ticket.title=title;
			ticket.description=desc;
			ticket.toAddress=ATPGUtil.TOOL_IP;
			ticket.toPort=ATPGUtil.TOOL_PORT;
			sender.sendTicketObject(ticket);
		}
	}
	
}
