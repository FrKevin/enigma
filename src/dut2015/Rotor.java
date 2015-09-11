package dut2015;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Rotor implements Transferable{
	private final int id;
	private final String codage;
	private int pointeur;
	private final char pivot;
	private static final String identite="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private boolean next;
	private boolean aTrourner=false;
	public static DataFlavor ROTOR_FLAVOR=new DataFlavor(Rotor.class,"ROTOR_FLAVOR");
	
	public Rotor(int id,String c, char pivot){
		this(id, c,0,pivot);
	}
	
	/**
     * initialisation d'un rotor
     * 
     * @param codage
     *            le codage du rotor: exemple EKMFLGDQVZNTOWYHXUSPAIBRCJ
     * @param pointeur
     *            le pointeur du rotor,exemple: codage[pointeur]. Par defaut, 0
     */
	public Rotor(int id, String codage, int pointeur, char pivot){
		this.id=id;
		this.codage=codage;
		this.pointeur=pointeur;
		this.pivot=pivot;
		this.next=false;
	}

	public char codageGauche(char c){
		int index=(identite.indexOf(c)+this.pointeur)%this.codage.length();
		char ce= this.codage.charAt(index);
		int i=identite.indexOf(ce)-(pointeur%this.identite.length());
		if( i< 0){
			int diff=identite.length()+i;
			return identite.charAt(diff);
		}
		else
			return identite.charAt(i);
		
	}
	public int codageDroit(int i){
		char c=identite.charAt((i+this.pointeur)%this.codage.length());
		int en=codage.indexOf(c)-this.pointeur;
		if(en<0)
			return (this.codage.length()+en);
		else
			return en;
		
	}
	
	public void deplacement(){
		if(this.pointeur==identite.length() )
			this.pointeur=0;
		else
			this.pointeur+=1;
	}
	public boolean deplacementRotor(){
		if(next){
			next=false;
			return true;
		}
		return this.identite.charAt(pointeur%(identite.length()))== pivot;
	}

	public void setLetter(char letter) {
		next=false;
		aTrourner=false;
		if(identite.indexOf(letter)> identite.indexOf(pivot)){
			next=true;
		}
		this.pointeur=identite.indexOf(letter);
	}
	public char getCurrentLetter(){
		return identite.charAt(pointeur % identite.length());
	}

	public int getId() {
		return id;
	}
	public boolean isaTrourner() {
		return aTrourner;
	}

	public void setaTrourner(boolean aTrourner) {
		this.aTrourner = aTrourner;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{ROTOR_FLAVOR};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor == ROTOR_FLAVOR;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return id;
	}

	public String toString(){
		return "rotor " + id;
	}
	

}	