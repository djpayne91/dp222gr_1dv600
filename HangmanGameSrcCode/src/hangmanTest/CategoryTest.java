package hangmanTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hangmanMain.Category;

class CategoryTest {

	Category SUT = new Category("testName", "test.txt");

	@Test
	void testGetName() {
		assertEquals("testName", SUT.getName());
	}

	@Test
	void testGetRandomPhrase() {
		assertEquals("testing", SUT.getRandomPhrase().getPhrase());
	}

	@Test
	void incorrectPath() {
		assertThrows(Exception.class, () -> {
			Category test = new Category("test", "badPath.txt");
			test.getRandomPhrase();
		});
	}
}
