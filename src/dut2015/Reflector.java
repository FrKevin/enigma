package dut2015;

/* DLB : attention à l'utilisation du singleton
 * Ce qu'il faudrait faire :
 * - declarer code comme attribut d'instance (pas de classe)
 * - enlever la méthode de classe setCode()
 * - declarer la methode getCode() comme une méthode d'instance (enlever le static).
 *
 * Autre solution : stocker tout dans la classe, et ne jamais créer d'instances
 * (constructeur privé). Nécessite de rendre la methode reflect() de classe.
 */
public class Reflector {
	private static String code;
	private static Reflector instance=null;
	
	private Reflector(){
		Reflector.setCode("YRUHQSLDPXNGOKMIEBFZCWVJAT");
	}
	public static  Reflector getReflector(){
		if(instance==null){
			instance=new Reflector();
		}
		return instance;
	}
	public int reflect(char c){
		return getCode().indexOf(c);
	}
	public static String getCode() {
		return code;
	}
	public static void setCode(String code) {
		Reflector.code = code;
	}
}