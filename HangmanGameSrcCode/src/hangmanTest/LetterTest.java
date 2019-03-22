package hangmanTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hangmanMain.Letter;

class LetterTest {
	
	@Test
	void testSetLetter() {
		Letter letter = new Letter('t');
		letter.setLetter('T');
		assertEquals('T', letter.getLetter());
		Letter newLetter = new Letter(';');
		assertTrue(newLetter.isShown());
	}
	
	@Test
	void testGetLetter() {
		Letter letter = new Letter('t');
		assertEquals('t', letter.getLetter());
	}
	
	@Test
	void testIsShown() {
		Letter letter = new Letter('t');
		assertFalse(letter.isShown());
	}
	
	@Test
	void testSetShown() {
		Letter letter = new Letter('t');
		letter.setShown(true);
		assertTrue(letter.isShown());
	}

}
