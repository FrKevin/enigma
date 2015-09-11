package dut2015;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ClavierListener extends KeyAdapter {
	private LogicEnigma enigma;
	private Lampes lampes;
	private  static ArrayList<UIRotor> uirotor;
	private char lettre;
	private boolean pressed=false;
	
	public ClavierListener(LogicEnigma enigma,Lampes lampes, ArrayList<UIRotor> a) {
		this.enigma=enigma;
		this.lampes = lampes;
		this.uirotor=a;
	}
    
    // DLB : bonne idée de travailler avec keyPressed() et keyReleased().
    // pour qqe chose de plus visuel, utilisez de la couleur.
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(!pressed){
			pressed=true;
			int keyCode=arg0.getKeyCode();
			if(keyCode>=65 && keyCode<=90){
				char encode= Character.toUpperCase(arg0.getKeyChar());;
				lettre = enigma.encode(encode);
				System.out.println("J'encode " + encode +", resultat= "+ lettre);
				lampes.activer(lettre,false);
			}
		}
	} 
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(pressed){
			pressed=false;
			if(arg0.getKeyCode()>=65 && arg0.getKeyCode()<=90)
				lampes.activer(lettre,true);
			uirotor.get(0).update(enigma.getRotorLetter(Position.LEFT));
			uirotor.get(1).update(enigma.getRotorLetter(Position.MIDDLE));
			uirotor.get(2).update(enigma.getRotorLetter(Position.RIGHT));
		}
    }
}
