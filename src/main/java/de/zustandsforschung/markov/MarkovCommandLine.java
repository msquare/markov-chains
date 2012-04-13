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
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.setOrder(Integer.valueOf(args[2]));
		fromFile(markovChain, new File(args[0]));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovChain, args[3]);
		if (args.length > 4 && "true".equals(args[4])) {
			System.out.print(args[3] + " ");
		}
		System.out.println(generator.generate(Integer.valueOf(args[1])));
	}

	public static void fromFile(final MarkovChain markovChain, final File file)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			markovChain.addTokens(line);
		}
	}
}
