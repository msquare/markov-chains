package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;

public class MarkovDictionaryBuilderImpl<T> implements
        MarkovDictionaryBuilder<T> {

    private final MarkovDictionary<T> markovDictionary;
    private Tokens<T> previousTokens;

    /**
     * If true, then no tokens, which are smaller than the order, are put to the
     * dictionary.
     */
    private boolean strict;

    public MarkovDictionaryBuilderImpl() {
        this(new MarkovDictionary<T>(), false);
    }

    public MarkovDictionaryBuilderImpl(final MarkovDictionary<T> dictionary,
            boolean strict) {
        this.markovDictionary = dictionary;
        this.previousTokens = new Tokens<T>();
        this.strict = strict;
    }

    @Override
    public void addTokens(final Tokens<T> tokens) {
        if (strict)
            previousTokens = new Tokens<T>();
        for (T token : tokens)
            addToken(token);
    }

    private void addToken(final T token) {
        if (!strict || (previousTokens.size() == markovDictionary.getOrder()))
            addTokenToDictionary(token);
        previousTokens.update(markovDictionary.getOrder(), token);
    }

    private void addTokenToDictionary(final T token) {
        Occurrences<T> occurrences = markovDictionary
                .getOrCreate(previousTokens);
        occurrences.increaseCount(token);
    }

    @Override
    public void clearPreviousToken() {
        previousTokens.clear();
    }

    @Override
    public String toString() {
        return markovDictionary.toString();
    }

}
