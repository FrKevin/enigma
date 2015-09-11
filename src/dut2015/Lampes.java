package dut2015;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;


public class Lampes {

	JButton label;
	private ArrayList<JButton> tabLampes;
	
	public Lampes(){
		tabLampes = new ArrayList<JButton>();
		initLampes();
	}
	
	private void initLampes(){
		for(char i = 'a'; i<='z';i++){
			label = new JButton(""+i);
			label.setPreferredSize(new Dimension(50,10));
			tabLampes.add(label);
			
		}
	}
	
	
	public void activer(char lettre, boolean b){
        // DLB : on ne voit pas grand chose en changeant setEnabled().
        // L'idéal est de changer la couleur du bouton par exemple.
        // (ne pas oublier de lui donner un couleur opaque).
			tabLampes.get(lettre-65).setEnabled(b);
			
	}
	
	public ArrayList<JButton> getUI() {
		return tabLampes;
	}
}
