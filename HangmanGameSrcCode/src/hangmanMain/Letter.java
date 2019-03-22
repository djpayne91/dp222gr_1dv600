package hangmanMain;

public class Letter {

	private char letter;
	private boolean show = false;

	public Letter(char letter) {
		setLetter(letter);
	}

	public void setLetter(char letter) {
		this.letter = letter;
		if (!Character.isLetter(letter))
			this.setShown(true);
	}	

	public char getLetter() {
		return this.letter;
	}

	public boolean isShown() {
		return show;
	}

	public void setShown(boolean show) {
		this.show = show;
	}
}
