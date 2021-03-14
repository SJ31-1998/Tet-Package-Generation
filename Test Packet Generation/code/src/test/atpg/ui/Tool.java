package pro.atpg.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import pro.atpg.bo.Packet;
import pro.atpg.impl.RouterReceiver;
import pro.atpg.impl.Sender;
import pro.atpg.impl.ToolReceiver;
import pro.atpg.utility.ATPGUtil;

	public class Tool implements ActionListener {

		public Sender sender=new Sender();
		
		public static int WIDTH=1200;
		
		public static int HEIGHT=700;
		
		public JFrame mainform;
		
		public JPanel topPanel;
		
		public JPanel bottomPanel;
		
		public JPanel topLeftPanel;
		
		public JPanel topRightPanel;
		
		/***** Top Panel Left components*****/
		
		public JLabel searchLabel,resultLabel;
		
		public JTextField searchText;
		
		public JTextArea resultText;
		
		public JButton searchButton,testButton;
		
		/***** Top Panel Right components*****/
		
		public JLabel ticketListLabel;
		
		public JTable ticketListTable;
		
		public static DefaultTableModel ticketListModel;
		
		public JScrollPane ticketListScroll;
		
		/***** Bottom Panel components*****/
		
		public JLabel problemListLabel;
		
		public JTable problemListTable;
		
		public static DefaultTableModel problemListModel;
		
		public JScrollPane problemListScroll;
	
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
		new ToolReceiver(ATPGUtil.TOOL_PORT);
		
		mainform=new JFrame("ATPG Tool");
		mainform.setLayout(new GridLayout(2,1));
		
		mainform.add(topPanelUI());
		mainform.add(bottomPanelUI());
		
		mainform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainform.setSize(WIDTH,HEIGHT);
		mainform.setVisible(true);
	}
	
	public JPanel topPanelUI(){
		topPanel=new JPanel();
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.add(topLeftPanelUI());
		topPanel.add(topRightPanelUI());
		return(topPanel);
	}
	
	public JPanel bottomPanelUI(){
		bottomPanel=new JPanel();
		bottomPanel.setLayout(null);
		
		problemListLabel=new JLabel("Error List");
		problemListLabel.setBounds(100,30,100,25);
		
		problemListModel=new DefaultTableModel();
		problemListTable=new JTable(problemListModel);
		problemListModel.addColumn("File Number");
		problemListModel.addColumn("Source Address");
		problemListModel.addColumn("Destination Address");
		problemListModel.addColumn("Receive From");
		problemListModel.addColumn("Problem Router");
		
		problemListScroll=new JScrollPane(problemListTable);
		problemListScroll.setBounds(100,50,800,250);
		
		bottomPanel.add(problemListScroll);
		bottomPanel.add(problemListLabel);
		return(bottomPanel);
	}
	
	public JPanel topLeftPanelUI(){
		topLeftPanel=new JPanel();
		topLeftPanel.setLayout(null);
		
		searchLabel=new JLabel("Search");
		searchLabel.setBounds(30,30,100,25);
		
		searchText=new JTextField();
		searchText.setBounds(30,60,400,25);
		
		resultLabel=new JLabel("Result");
		resultLabel.setBounds(30,100,100,25);
		
		resultText=new JTextArea();
		resultText.setBounds(30,130,400,250);
		
		searchButton=new JButton("Search");
		searchButton.setBounds(450,60,100,25);
		searchButton.addActionListener(this);
		
		testButton=new JButton("Test");
		testButton.setBounds(450,130,100,25);
		testButton.addActionListener(this);
				
		topLeftPanel.add(testButton);
		topLeftPanel.add(searchLabel);
		topLeftPanel.add(searchText);
		topLeftPanel.add(resultLabel);
		topLeftPanel.add(resultText);
		topLeftPanel.add(searchButton);
		
		return(topLeftPanel);
	}
	
	public JPanel topRightPanelUI(){
		topRightPanel=new JPanel();
		topRightPanel.setLayout(null);
		
		ticketListLabel=new JLabel("Ticket List");
		ticketListLabel.setBounds(20,30,100,25);
		
		ticketListModel=new DefaultTableModel();
		ticketListTable=new JTable(ticketListModel);
		ticketListModel.addColumn("Ticket Number");
		ticketListModel.addColumn("Title");
		ticketListModel.addColumn("Ticket From");
		ticketListModel.addColumn("Description");
		
		ticketListScroll=new JScrollPane(ticketListTable);
		ticketListScroll.setBounds(20,50,500,250);
		
		topRightPanel.add(ticketListLabel);
		topRightPanel.add(ticketListScroll);
		return(topRightPanel);
	}
	
	public static void main(String args[]){
		Tool tool=new Tool();
		tool.mainMethod();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		if(obj==testButton){
			String nodeName=ATPGUtil.testSourceName;
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
					 pac.destinationAddress=ATPGUtil.ipMap.get(ATPGUtil.testDestinationName);
					 pac.destinationName=ATPGUtil.testDestinationName;
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
		}else if(obj==searchButton){
			String query=searchText.getText();
			resultText.setText("");
			ArrayList<Packet> pacList=ToolReceiver.errorMap.get(query);
			if(pacList.size()>0){
				for(Packet pac:pacList){
					String problemRouter="FileNumber : "+pac.fileNumber+"\n"+"Problem Router : "+pac.toName+"\n ----------------\n";
					resultText.append(problemRouter);
				}
			}else{
				resultText.setText("No Result");
			}
		}
	}
}
