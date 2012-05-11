package de.zustandsforschung.markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public final class MarkovCommandLine {

	private MarkovCommandLine() {
	}

	public static void main(final String[] args) throws IOException {
		MarkovDictionary markovDictionary = new MarkovDictionary(
				Integer.valueOf(args[2]));
		MarkovDictionaryBuilder markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(markovDictionary);
		fromFile(markovDictionaryBuilder, new File(args[0]));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary, args[3]);
		generator.setRandomGenerator(new RandomGeneratorImpl());
		if (args.length > 4 && "true".equals(args[4])) {
			System.out.print(args[3] + " ");
		}
		System.out.println(generator.generate(Integer.valueOf(args[1])));
	}

	public static void fromFile(final MarkovDictionaryBuilder markovDictionaryBuilder, final File file)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			markovDictionaryBuilder.addTokens(line);
		}
	}
}
