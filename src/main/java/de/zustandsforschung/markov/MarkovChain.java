package de.zustandsforschung.markov;

import java.util.List;

import de.zustandsforschung.markov.random.RandomGenerator;

public interface MarkovChain {

	String next(String string);

	void addTokens(List<String> tokens);

	void addTokens(String string);

	double probability(String after, String token);

	void setRandomGenerator(RandomGenerator randomGenerator);

	void clearPreviousToken();

	String getPunctuationRegex();

}
