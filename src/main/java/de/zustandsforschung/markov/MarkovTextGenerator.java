package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public interface MarkovTextGenerator {

	String generate(int numTokens);

	String next(Tokens tokens);

	void setRandomGenerator(RandomGenerator randomGenerator);

}
