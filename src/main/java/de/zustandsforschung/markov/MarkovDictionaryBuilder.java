package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.Tokens;

public interface MarkovDictionaryBuilder<T> {

    void addTokens(Tokens<T> tokens);

    void clearPreviousToken();

}
