package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public class MarkovCommandLineTest {

	@Test
	public void testFromFile() throws IOException {
		File tempFile = File.createTempFile("markov", ".tmp");
		tempFile.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		out.write("one two");
		out.close();

		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		MarkovCommandLine.fromFile(markovChain, tempFile);

		assertEquals("two", markovChain.next(Arrays.asList("one")));
	}

}
