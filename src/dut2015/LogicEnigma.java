package dut2015;

import static dut2015.Position.LEFT;
import static dut2015.Position.MIDDLE;
import static dut2015.Position.RIGHT;


public class LogicEnigma implements Enigma {
	private Rotor tabRotor []= new Rotor[3];
	public Rotor tousLesRotors[]=new Rotor[8];
	private Reflector reflector;
	
	public LogicEnigma(){
        // DLB vous auriez pu créer des instances de Rotor correspondant à ces véritables rotors.
		String code[]={"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "AJDKSIRUXBLHWTMCQGZNPYFVOE", "BDFHJLCPRTXVZNYEIWGAKMUSQO" , "ESOVPZJAYQUIRHXLNFTGKDCMWB", "VZBRGITYUPSDNHLXAWMJQOFECK", "JPGVOUMFYQBENHZRDKASXLICTW", "NZJHGRCXMYSWBOUFAIVLPEKQDT", "FKQHTLXOCBJSPDZRAMEWNIUYGV"};
		char pivot[]={ 'Q','E','V','J','Z', 'M','M','M'};
		for(int i=0; i<8;i++){
			tousLesRotors[i]=new Rotor((i+1),code[i],pivot[i] );
		}
		this.reflector=Reflector.getReflector();
	}
	@Override
	public void setRotor(Position pos, int rotorNumber) {
		tabRotor[pos.ordinal()]=tousLesRotors[rotorNumber-1];
	}
	
	@Override
	public int getRotor(Position pos) {
		if(tabRotor[pos.ordinal()] == null)
			return 0;
		else
			return tabRotor[pos.ordinal()].getId();
	}
	
	
	
	@Override
	public void moveRotorToLetter(Position pos, char letter) {
		if(getRotor(pos) !=0){
			tabRotor[pos.ordinal()].setLetter(letter);
		}
	}

	@Override
	public char getRotorLetter(Position pos) {
		if(getRotor(pos) !=0){
			return tabRotor[pos.ordinal()].getCurrentLetter();
		}
		return ' ';
	}

	@Override
	public String getCurrentLetters() {
		return ""+getRotorLetter(LEFT)+getRotorLetter(MIDDLE)+getRotorLetter(RIGHT);
	}

	@Override
	public char encode(char c) {
		if(c>='A' && c<='Z' && getRotor(LEFT) !=0 &&getRotor(MIDDLE) !=0 && getRotor(RIGHT) !=0){
			char encode=c;
			if(tabRotor[2].deplacementRotor()){	
				tabRotor[2].setaTrourner(true);
				if(tabRotor[1].deplacementRotor() && tabRotor[1].isaTrourner()){
					tabRotor[0].deplacement();
				}else{
					tabRotor[1].deplacement();
					tabRotor[1].setaTrourner(true);
					
				}
			}else if(tabRotor[2].isaTrourner()){
				if(tabRotor[1].deplacementRotor()){
					tabRotor[1].deplacement();
					tabRotor[0].deplacement();

				}
			}
			tabRotor[RIGHT.ordinal()].deplacement();
			for(int i=2; i>=0; i--){
				encode=tabRotor[i].codageGauche(encode);
			}
			int j=reflector.reflect(encode);
			for(int i=0; i<3; i++){
				j=tabRotor[i].codageDroit(j);
			}
			return (char)(j+'A');
		}
		return 'X';
	}
	public void render(){
		for(int i=0;i <3 ; i++){
			System.out.println(tabRotor[i]);
		}
	}

}