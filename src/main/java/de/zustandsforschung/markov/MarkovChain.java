package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public interface MarkovChain {

	String next(Tokens tokens);

	void addTokens(Tokens tokens);

	void addTokens(String string);

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
	Tokens findStartTokens(String startToken);

}
