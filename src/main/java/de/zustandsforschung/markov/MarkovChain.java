package de.zustandsforschung.markov;

import java.util.List;

import de.zustandsforschung.markov.random.RandomGenerator;

public interface MarkovChain {

	String next(List<String> tokens);

	void addTokens(List<String> tokens);

	void addTokens(String string);

	double probability(String after, String... tokens);

	void setRandomGenerator(RandomGenerator randomGenerator);

	void clearPreviousToken();

	String getPunctuationRegex();

	void setOrder(int order);

	int getOrder();

	/**
	 * Finds a list of tokens in the dictionary such that the last element of
	 * this list is the startToken.
	 * 
	 * @param startToken
	 *            Token that should be used as a starting point (markov chain
	 *            starts after this token).
	 * @return List of tokens where the first element is the start token.
	 */
	List<String> findStartTokens(String startToken);

}
