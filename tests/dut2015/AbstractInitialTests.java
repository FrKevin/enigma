package dut2015;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static dut2015.Position.*;

public abstract class AbstractInitialTests {

    private Enigma enigma;
    
    protected abstract Enigma makeEnigma();

    @Before
    public void init() {
        enigma = makeEnigma();
    }
    
    @Test
    public void enigmaMachineHasNoRotorByDefault() {
        assertEquals(0,enigma.getRotor(LEFT));
        assertEquals(0,enigma.getRotor(MIDDLE));
        assertEquals(0,enigma.getRotor(RIGHT));
        assertEquals(' ',enigma.getRotorLetter(LEFT));
        assertEquals(' ',enigma.getRotorLetter(MIDDLE));
        assertEquals(' ',enigma.getRotorLetter(RIGHT));
        assertEquals("   ",enigma.getCurrentLetters());
    }

    @Test
    public void rotorAreByDefaultSetToA() {
        enigma.setRotor(LEFT, 1);
        enigma.setRotor(MIDDLE, 2);
        enigma.setRotor(RIGHT, 3);
        assertEquals('A',enigma.getRotorLetter(LEFT));
        assertEquals('A',enigma.getRotorLetter(MIDDLE));
        assertEquals('A',enigma.getRotorLetter(RIGHT));
        assertEquals("AAA",enigma.getCurrentLetters());        
    }
    
    @Test
    public void testEncoding() {
        enigma.setRotor(LEFT, 1);
        enigma.setRotor(MIDDLE, 2);
        enigma.setRotor(RIGHT, 3);
        // taken from http://en.wikipedia.org/wiki/Enigma_rotor_details
        assertEquals('B',enigma.encode('A'));
        assertEquals('D',enigma.encode('A'));
        assertEquals('Z',enigma.encode('A'));
        assertEquals('G',enigma.encode('A'));
        assertEquals('O',enigma.encode('A'));
    }
    
    @Test
    public void simpleRotorChange() {
        // taken from http://en.wikipedia.org/wiki/Enigma_rotor_details
        enigma.setRotor(LEFT, 1);
        enigma.setRotor(MIDDLE, 2);
        enigma.setRotor(RIGHT, 3);
        enigma.moveRotorToLetter(RIGHT, 'U');
        enigma.encode('X');
        assertEquals("AAV",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABW",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABX",enigma.getCurrentLetters());
    }
    
    @Test
    public void doubleRotorChange() {
        // taken from http://en.wikipedia.org/wiki/Enigma_rotor_details
        enigma.setRotor(LEFT, 1);
        enigma.setRotor(MIDDLE, 2);
        enigma.setRotor(RIGHT, 3);
        enigma.moveRotorToLetter(MIDDLE, 'D');
        enigma.moveRotorToLetter(RIGHT, 'U');
        enigma.encode('X');
        assertEquals("ADV",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("AEW",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("BFX",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("BFY",enigma.getCurrentLetters());
    }
    @Test
    public void checkThatRotorIChangesAtQ() {
        enigma.setRotor(LEFT, 3);
        enigma.setRotor(MIDDLE, 2);
        enigma.setRotor(RIGHT, 1);
        enigma.moveRotorToLetter(RIGHT, 'P');
        assertEquals("AAP",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("AAQ",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABR",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABS",enigma.getCurrentLetters());
    }
    
    @Test
    public void checkThatRotorIIChangesAtE() {
        enigma.setRotor(LEFT, 3);
        enigma.setRotor(MIDDLE, 1);
        enigma.setRotor(RIGHT, 2);
        enigma.moveRotorToLetter(RIGHT, 'D');
        assertEquals("AAD",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("AAE",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABF",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABG",enigma.getCurrentLetters());
    }
    
    @Test
    public void checkThatRotorIIIChangesAtV() {
        enigma.setRotor(LEFT, 2);
        enigma.setRotor(MIDDLE, 1);
        enigma.setRotor(RIGHT, 3);
        enigma.moveRotorToLetter(RIGHT, 'U');
        assertEquals("AAU",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("AAV",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABW",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABX",enigma.getCurrentLetters());
    }
    
    @Test
    public void checkThatRotorIVChangesAtJ() {
        enigma.setRotor(LEFT, 2);
        enigma.setRotor(MIDDLE, 1);
        enigma.setRotor(RIGHT, 4);
        enigma.moveRotorToLetter(RIGHT, 'I');
        assertEquals("AAI",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("AAJ",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABK",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABL",enigma.getCurrentLetters());
    }
    
    public void checkThatRotorVChangesAtJ() {
        enigma.setRotor(LEFT, 2);
        enigma.setRotor(MIDDLE, 1);
        enigma.setRotor(RIGHT, 5);
        enigma.moveRotorToLetter(RIGHT, 'Y');
        assertEquals("AAY",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("AAZ",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABA",enigma.getCurrentLetters());
        enigma.encode('X');
        assertEquals("ABB",enigma.getCurrentLetters());
    }
    
    public void checkThatRotorChangesOnlyOnce(int rotorNumber) {
        enigma.setRotor(LEFT, 2==rotorNumber?3:2);
        enigma.setRotor(MIDDLE, 1==rotorNumber?3:1);
        enigma.setRotor(RIGHT, rotorNumber);
        enigma.moveRotorToLetter(RIGHT, 'A');
        assertEquals("AAA",enigma.getCurrentLetters());
        for (int i=0;i<26;i++) {
            enigma.encode('X');
            assertTrue(enigma.getRotorLetter(MIDDLE)=='A' || enigma.getRotorLetter(MIDDLE)=='B');
        }
    }
    
    @Test
    public void checkThatRotorIChangesOnlyOnce() {
        checkThatRotorChangesOnlyOnce(1);
    }
    
    @Test
    public void checkThatRotorIIChangesOnlyOnce() {
        checkThatRotorChangesOnlyOnce(2);
    }
    
    @Test
    public void checkThatRotorIIIChangesOnlyOnce() {
        checkThatRotorChangesOnlyOnce(3);
    }
    
    @Test
    public void checkThatRotorIVChangesOnlyOnce() {
        checkThatRotorChangesOnlyOnce(4);
    }
    
    @Test
    public void checkThatRotorVChangesOnlyOnce() {
        checkThatRotorChangesOnlyOnce(5);
    }
    
    @Test
    public void checkMessage1() {
        String plain = "PREMIERXTESTXUNXPEUXREEL";
        String encoded = "CHTPOPOOLLNLCOVOEIBOUPNI";
        enigma.setRotor(LEFT, 1);
        enigma.setRotor(MIDDLE, 2);
        enigma.setRotor(RIGHT, 3);
        enigma.moveRotorToLetter(LEFT, 'D');
        enigma.moveRotorToLetter(MIDDLE, 'L');
        enigma.moveRotorToLetter(RIGHT, 'B');
        for (int i =0;i<plain.length();i++) {
            assertEquals("Problem with "+(i+1)+"th char",encoded.charAt(i),enigma.encode(plain.charAt(i)));
        }
        enigma.moveRotorToLetter(LEFT, 'D');
        enigma.moveRotorToLetter(MIDDLE, 'L');
        enigma.moveRotorToLetter(RIGHT, 'B');
        for (int i =0;i<plain.length();i++) {
            assertEquals("Problem with "+(i+1)+"th char",plain.charAt(i),enigma.encode(encoded.charAt(i)));
        }
    }
}
