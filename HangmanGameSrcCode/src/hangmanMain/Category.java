package hangmanMain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Category {

	String name;
	String path;
	ArrayList<String> phrases = new ArrayList<String>();
	
	public Category(String name, String path) {
		this.name = name;
		this.path = path;

		InputStream input = this.getClass().getResourceAsStream(path);
		if (input == null) {
			input = this.getClass().getResourceAsStream("/" + path);
		}
		
		try {
		Scanner stan = new Scanner(input);
		while(stan.hasNextLine())
			phrases.add(stan.nextLine());
		stan.close();
		}catch(Exception e) {
			System.err.println("Error initializing categories");
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public Phrase getRandomPhrase() {
		Random rand = new Random();
		
		String out = phrases.get(rand.nextInt(phrases.size()));
		
		return new Phrase(out);
	}
	
}
