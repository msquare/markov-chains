package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class MarkovCommandLineTest {

	@Test
	public void testFromFile() throws IOException {
		File tempFile = File.createTempFile("markov", ".tmp");
		tempFile.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		out.write("one two");
		out.close();

		MarkovChain markovChain = MarkovCommandLine.fromFile(tempFile);

		assertEquals("two", markovChain.next("one"));
	}

}
