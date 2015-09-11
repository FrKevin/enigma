package dut2015;


public class MainTest extends AbstractInitialTests{

	@Override
	protected Enigma makeEnigma() {
		return new LogicEnigma();
	}



}
