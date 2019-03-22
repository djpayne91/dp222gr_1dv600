package hangmanTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hangmanMain.HangmanGame;
import hangmanMain.Phrase;

class HangmanGameTest {

	HangmanGame SUT = new HangmanGame(new Phrase("test phrase"));
	
	@Test
	void testCheckWin() {
		assertFalse(SUT.checkWin());
	}

}
