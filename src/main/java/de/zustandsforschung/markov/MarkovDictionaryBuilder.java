package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.Tokens;

public interface MarkovDictionaryBuilder {

	void addTokens(String string);

	void addTokens(Tokens tokens);

	void clearPreviousToken();

}
