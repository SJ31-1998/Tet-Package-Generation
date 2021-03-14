package pro.atpg.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import pro.atpg.impl.NodeReceiver;
import pro.atpg.impl.RouterReceiver;
import pro.atpg.utility.ATPGUtil;

public class Router {

	public String routerName;
	
	public static int WIDTH=700;
	
	public static int HEIGHT=700;
	
	public JFrame mainform;
	
	public JPanel topPanel;
	
	public JPanel bottomPanel;
	
	/***** Top Panel components*****/
	
	public JLabel receiveInfoLabel;
	
	public JTable receiveInfoTable;
	
	public static DefaultTableModel receiveInfoModel;
	
	public JScrollPane receiveInfoScroll;
	
	/***** Bottom Panel components*****/
	
	public JLabel forwardInfoLabel;
	
	public JTable forwardInfoTable;
	
	public static DefaultTableModel forwardInfoModel;
	
	public JScrollPane forwardInfoScroll;
	
	/*static{
		try{
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
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
			String inp=JOptionPane.showInputDialog("Enter the Router Name");
			if(inp!=null && inp.length()>0){
				routerName=inp.toUpperCase();
				break;
			}
		}
		
		for(int port:ATPGUtil.portMap.get(routerName)){
			new RouterReceiver(port,routerName);
		}
		
		mainform=new JFrame("Router : "+routerName);
		mainform.setLayout(new GridLayout(2,1));
		
		mainform.add(topPanelUI());
		mainform.add(bottomPanelUI());
		
		mainform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainform.setResizable(false);
		mainform.setSize(WIDTH,HEIGHT);
		mainform.setVisible(true);
	}
	
	public JPanel topPanelUI() {

		topPanel=new JPanel();
		topPanel.setLayout(null);
		
		receiveInfoLabel=new JLabel("Receiving Packet Information");
		receiveInfoLabel.setBounds(20,20,200,25);
		
		receiveInfoModel=new DefaultTableModel();
		receiveInfoTable=new JTable(receiveInfoModel);
		receiveInfoModel.addColumn("File Number");
		receiveInfoModel.addColumn("Packet Number");
		receiveInfoModel.addColumn("Source Address");
		receiveInfoModel.addColumn("Receiving Address");
		receiveInfoModel.addColumn("Receiving Port");
		
		receiveInfoScroll=new JScrollPane(receiveInfoTable);
		receiveInfoScroll.setBounds(20,50,600,250);
		
		topPanel.add(receiveInfoScroll);
		topPanel.add(receiveInfoLabel);
		
		return(topPanel);
	}
	
	public JPanel bottomPanelUI(){
		
		bottomPanel=new JPanel();
		bottomPanel.setLayout(null);
		
		forwardInfoLabel=new JLabel("Forwarding Packet Information");
		forwardInfoLabel.setBounds(20,20,200,25);
		
		forwardInfoModel=new DefaultTableModel();
		forwardInfoTable=new JTable(forwardInfoModel);
		forwardInfoModel.addColumn("File Number");
		forwardInfoModel.addColumn("Packet Number");
		forwardInfoModel.addColumn("Source Address");
		forwardInfoModel.addColumn("Forward Address");
		forwardInfoModel.addColumn("Forward Port");
		
		forwardInfoScroll=new JScrollPane(forwardInfoTable);
		forwardInfoScroll.setBounds(20,50,600,250);
		
		bottomPanel.add(forwardInfoScroll);
		bottomPanel.add(forwardInfoLabel);
		return(bottomPanel);
	}
	
	public static void main(String args[]){
		Router router=new Router();
		router.mainMethod();
	}
}
