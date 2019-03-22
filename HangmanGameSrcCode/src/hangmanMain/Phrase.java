package hangmanMain;

public class Phrase {

	private Letter[] phrase;
	
	public Phrase(String str) {
		this.phrase = new Letter[str.length()];
		for (int i = 0; i < str.length(); i++) {
			this.phrase[i] = new Letter(str.charAt(i));
		}
	}
	
	public boolean win() {
		for (Letter letter : phrase) {
			if (!letter.isShown())
				return false;
		}
		return true;
	}

	public boolean guess(char c) {
		boolean out = false;
		for (Letter letter : phrase) {
			if (Character.toLowerCase(letter.getLetter()) == Character.toLowerCase(c)) {
				letter.setShown(true);
				out = true;
			}
		}
		return out;
	}

	public String toString() {
		StringBuilder out = new StringBuilder();
		for (Letter letter : phrase) {
			if(letter.isShown()) {
				if (Character.isWhitespace(letter.getLetter()))
					out.append(" ");
				out.append(letter.getLetter());
			}
			else
				out.append("_\u200A");
			
		}
		return out.toString();
	}
	
	public String getPhrase() {
		StringBuilder out = new StringBuilder();
		for(Letter l : phrase) {
			out.append(l.getLetter());
		}
		return out.toString();
	}

	public void reset() {
		for (Letter l : phrase) {
			if (Character.isLetter(l.getLetter()))
				l.setShown(false);
		}
	}
}
