package hangmanMain;

public class HangmanGame {

	protected Phrase gamePhrase = new Phrase("");
	protected int guessesLeft = 6;

	public HangmanGame(Phrase phrase) {
		this.gamePhrase = phrase;
	}

	public boolean checkWin() {
		return gamePhrase.win() && guessesLeft >= 0;
	}

}
