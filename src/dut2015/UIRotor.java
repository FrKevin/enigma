package dut2015;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;


public class UIRotor extends JPanel{
	private JLabel lettre = new JLabel("-"); 
	//private JLabel num = new JLabel("-");
	private JLabel rotor = new JLabel("-");
	
	private Enigma enigma;
	private Position position;
	
	public UIRotor(Enigma enigma,Position p){
		this.enigma = enigma;
		this.position = p;
		
		lettre.setHorizontalAlignment(SwingConstants.CENTER);
		lettre.setOpaque(true);
		
		rotor.setHorizontalAlignment(SwingConstants.CENTER);
		rotor.setOpaque(true);
		
		this.setLayout(new GridLayout(4,1));
		
		JButton haut = new JButton("+");
		
		haut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				char current = enigma.getRotorLetter(position);
				enigma.moveRotorToLetter(position, next(current));
				lettre.setText("" + enigma.getRotorLetter(position));
			}
		});
		JButton bas = new JButton("-");

		
		bas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				char current = enigma.getRotorLetter(position);
				enigma.moveRotorToLetter(position, previous(current));
				lettre.setText("" + enigma.getRotorLetter(position));
			}
		});
		add(haut);
		add(lettre);
		add(bas);
		add(rotor);
		TransferHandler receiveRotor = new TransferHandler() {
			@Override
			public boolean canImport(TransferSupport ts) {
				return ts.isDataFlavorSupported(Rotor.ROTOR_FLAVOR);
			}

			@Override
			public boolean importData(TransferSupport ts) {
				try {
					if (ts.isDrop()) {
						int rotorNumber = (Integer) ts.getTransferable().getTransferData(Rotor.ROTOR_FLAVOR);
						enigma.setRotor(position, rotorNumber);
						lettre.setText("" + enigma.getRotorLetter(position));
						rotor.setText(""+rotorNumber);
						return true;
					}
				} catch (Exception e) {
					System.err.println(e);
					return false;
				}
				return false;
			}
		};
		setTransferHandler(receiveRotor);
		
	}
	private char next(char current) {
		if(current=='Z'){
			return'A';
		}else
			return (char) (current+1);
	}
	public Position getPosition(){
		return position;
	}
	
	private char previous(char current){
		if(current=='A'){
			return 'Z';
		}else
			return (char) (current-1);
	}
	public void update(char c){
		lettre.setText(c+"");
	}
}
