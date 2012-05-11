package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public interface MarkovTextGenerator {

	String generate(int numTokens);

	String next(Tokens tokens);

	/**
	 * Finds a list of tokens in the markovDictionary such that the last element of
	 * this list is the startToken.
	 * 
	 * @param startToken
	 *            Token that should be used as a starting point (markov chain
	 *            starts after this token).
	 * @return List of tokens where the first element is the start token.
	 */
	Tokens findStartTokens(String startToken);

	void setRandomGenerator(RandomGenerator randomGenerator);

}
