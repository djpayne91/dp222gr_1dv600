package hangmanTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hangmanMain.Phrase;

class PhraseTest {

	@Test
	void testGetPhrase() {
		Phrase SUT = new Phrase("Test Phrase");
		assertEquals("Test Phrase", SUT.getPhrase());
	}

	@Test
	void testGuess() {
		Phrase SUT = new Phrase("Test Phrase");
		assertTrue(SUT.guess('t'));
		assertFalse(SUT.guess('o'));
	}
	
	@Test
	void testReset() {
		Phrase SUT = new Phrase("Test");
		SUT.guess('t');
		assertEquals("T_ _ t", SUT.toString());
		SUT.reset();
		assertEquals("_ _ _ _ ", SUT.toString());
	}
	
	@Test
	void testWin() {
		Phrase SUT = new Phrase("Test");
		assertFalse(SUT.win());
		SUT.guess('t');
		SUT.guess('e');
		SUT.guess('s');
		assertTrue(SUT.win());
	}
}
