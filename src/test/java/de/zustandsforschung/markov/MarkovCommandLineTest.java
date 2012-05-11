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

		MarkovDictionary markovDictionary = new MarkovDictionary(1);
		MarkovDictionaryBuilder markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(markovDictionary);
		MarkovTextGenerator markovTextGenerator = new MarkovTextGeneratorImpl(
				markovDictionary);
		markovTextGenerator.setRandomGenerator(new RandomGeneratorImpl());
		MarkovCommandLine.fromFile(markovDictionaryBuilder, tempFile);

		assertEquals("two", markovTextGenerator.next(new Tokens("one")));
	}
}
