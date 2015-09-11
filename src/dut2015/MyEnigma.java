package dut2015;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class MyEnigma extends JFrame {
	private static final long serialVersionUID = 1L;
	private Lampes lampes;
	private JPanel rotorUtilises;
	private LogicEnigma enigma;
	private JTextField jtx;
	private ArrayList<UIRotor> RotorsUtilise = new ArrayList<UIRotor>();

	public MyEnigma(int longueur, int largeur) {
		lampes = new Lampes();
		enigma = new LogicEnigma();
		jtx = new JTextField();
		 init();
		
	}
	private void init(){
		this.setTitle("Enigma");
		this.setSize(800, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		ImageIcon icone = new ImageIcon("enigma.png"); 
													
		this.setIconImage(icone.getImage());
		this.setLayout(new GridLayout(0, 1));;	
		initListRotor();
		initLampes();
		initTextArea();
		
		// On ajoute le bouton au content pane de la JFrame
		this.setVisible(true);
		this.pack();
	}
	
	private void initTextArea() {
		// on ajoute la zone de texte a la fenetre
		Font police = new Font("Arial", Font.BOLD, 14);
		jtx.setFont(police);
		jtx.setPreferredSize(new Dimension(150, 30));
		jtx.setForeground(Color.BLUE);
		jtx.addKeyListener(new ClavierListener(enigma, lampes, RotorsUtilise));
		this.add(jtx);
	}
	
	private void initLampes() {
		JPanel lampe = new JPanel();
		lampe.setLayout(new GridLayout(0, 10));
		// on ajoute les lampes dans un jpanel
		for (int i = 0; i < lampes.getUI().size(); i++) {
			lampe.add(lampes.getUI().get(i));
		}
		this.add(BorderLayout.SOUTH, lampe);
	}
	public void initListRotor() {
		rotorUtilises = new JPanel();
		RotorsUtilise.add(new UIRotor(enigma, Position.LEFT));
		RotorsUtilise.add(new UIRotor(enigma, Position.MIDDLE));
		RotorsUtilise.add(new UIRotor(enigma, Position.RIGHT));
		
		for (int i = 0; i < RotorsUtilise.size(); i++) {
			rotorUtilises.add(RotorsUtilise.get(i));
		}
		
		DefaultListModel<Rotor> model = new DefaultListModel<Rotor>();
		for (int i = 0; i <8; i++)
			model.addElement((Rotor) enigma.tousLesRotors[i]);

		JList<Rotor> jList = new JList<Rotor>(model);
		rotorUtilises.add(jList);
		
		TransferHandler provide = new TransferHandler() {
			public int getSourceActions(JComponent c) {
				return MOVE;
			}

			protected Transferable createTransferable(JComponent c) {
				return ((JList<Rotor>) c).getSelectedValue();
			}
			protected void exportDone(JComponent source, Transferable data, int action) {
				if (action == MOVE) {
					JList<Rotor> list = (JList<Rotor>) source;
					((DefaultListModel) list.getModel()).removeElement(data);
				}
			}
		};
		
		jList.setTransferHandler(provide);
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JComponent comp = (JComponent) me.getSource();
				TransferHandler handler = comp.getTransferHandler();
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);
			}
		};
		jList.addMouseListener(mouseListener);
		rotorUtilises.setLayout(new GridLayout(1, 8));
		this.add(BorderLayout.NORTH, rotorUtilises);

	}

}