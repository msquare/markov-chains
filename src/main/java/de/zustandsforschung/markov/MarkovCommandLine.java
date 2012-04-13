package de.zustandsforschung.markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public final class MarkovCommandLine {

	private MarkovCommandLine() {
	}

	public static void main(final String[] args) throws IOException {
		MarkovChain markovChain = fromFile(new File(args[0]));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		System.out.println(generator.generate(Integer.valueOf(args[1])));
	}

	public static MarkovChain fromFile(final File file) throws IOException {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());

		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			markovChain.addTokens(line);
		}
		return markovChain;
	}
}
