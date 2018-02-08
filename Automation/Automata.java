import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Enumeration;
import java.util.Vector;
public class Automata extends JFrame implements ActionListener{

	Vector states, running;
	
	Container c;
	JButton proceedButton  = new JButton("Proceed");
	JButton resetButton = new JButton("Reset");	
	JLabel inputLabel = new JLabel("Input:");
	JLabel resultLabel = new JLabel("Result:");	
	JTextField inputField = new JTextField(50);
	JTextField resultField = new JTextField(50);
	
	String processOrder;
	int processCount=0;
	chartPanel cp;
 
	public Automata(Vector states, String processOrder){
		super("Automata");
		this.processOrder = processOrder;
		
		proceedButton.setBounds(10,500,85,25);
		proceedButton.addActionListener(this);
		
		resetButton.setBounds(10,530,85,25);
		resetButton.addActionListener(this);
		
		inputLabel.setBounds(400,500,100,25);
		resultLabel.setBounds(400,530,100,25);
		
		inputField.setBounds(450,505,200,20);
		inputField.setText(processOrder);
		
		resultField.setBounds(450,535,200,20);
		
		c = getContentPane();
		
		this.states=states;
		running = new Vector();
		running.add(states.elementAt(0));
		
		cp = new chartPanel(running, states);

		c.setLayout(null);
		c.add(proceedButton);
		c.add(resetButton);
		c.add(inputLabel);
		c.add(resultLabel);
		c.add(inputField);
		c.add(resultField);
		c.add(cp);
		
		setResizable(false);
		setSize(800,600);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		cp.paint(g, resultField.getText());
	
	}
	
	private void nextStep(){
		State tmpState;
		State tmpNext;
		Vector tmp = new Vector();;
		
		for(int i=0; i<running.size();++i){
			tmpState = (State) running.elementAt(i);
			
			Enumeration enum = tmpState.table.keys();
			
			while(enum.hasMoreElements()){
				Character key = (Character)enum.nextElement();
				Vector tmpStd = (Vector) tmpState.table.get(key);
				
				for(int j=0;j<tmpStd.size();++j){
					tmpNext = (State)cp.getState((String)tmpStd.elementAt(j));
					if(processOrder.substring(processCount, processCount+1).equals(key+""))
						tmp.add(tmpNext);
				}
			}			
		}
			running.clear();
			
		for(int i=0; i<tmp.size();++i)
			running.add(tmp.elementAt(i));
		
		++processCount;
		if(processCount >= processOrder.length()){
			
			tmpState = null;			
			if(!running.isEmpty())
				tmpState = (State) running.firstElement();
			
			for(int i=0;i<running.size();++i){
				tmpState = (State) running.elementAt(i);
				if(tmpState != null && tmpState.kind.contains("last")){
					resultField.setText("Accepted");
					repaint();
					break;
				}
				else
					resultField.setText("Rejected");
			}
			proceedButton.setVisible(false);
		}else
			resultField.setText("Running");
		
		repaint();	
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == resetButton){
			processCount = 0;
			resultField.setText("Reset");
			proceedButton.setVisible(true);
			running.clear();
			running.add(states.elementAt(0));
			repaint();
			
		}else if(e.getSource() == proceedButton)
			nextStep();
	}
	

	public static void main(String[] args) {
		
		fileReader fd = new fileReader("automata.inp");
		Automata automata = new Automata(fd.getStates(), fd.getPath()); //creating the Frame
		automata.addWindowListener(
			new WindowAdapter(){
				public void windowClosing( WindowEvent e ){
					System.exit( 0 );
				}
			}
		);   
    }
}