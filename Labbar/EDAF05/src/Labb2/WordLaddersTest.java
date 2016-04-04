package Labb2;

import static org.junit.Assert.*;

import java.io.File;
public class WordLaddersTest {

	@Test
	public void testXYZ() {
		WordLadders wordLadders = new WordLadders("10");
	    final File expected = new File(".txt");
	    final File output = folder.newFile("xyz.txt");
	    TestClass.xyz(output);
	    Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(output));
	}
}
