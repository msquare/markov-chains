package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public class MarkovCommandLineTest {

	@Test
	public void testFromFile() throws IOException {
		File tempFile = File.createTempFile("markov", ".tmp");
		tempFile.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		out.write("one two");
		out.close();

		MarkovDictionary<String> markovDictionary = new MarkovDictionary<String>(
				1);
		MarkovDictionaryBuilder<String> markovDictionaryBuilder = new MarkovDictionaryBuilderImpl<String>(
				markovDictionary, false);
		MarkovTextGenerator markovTextGenerator = new MarkovTextGeneratorImpl(
				markovDictionary);
		markovTextGenerator.setRandomGenerator(new RandomGeneratorImpl());
		MarkovCommandLine.fromFile(markovDictionaryBuilder, tempFile);

		assertEquals("two", markovTextGenerator.next(new Tokens<String>("one")));
	}
}
