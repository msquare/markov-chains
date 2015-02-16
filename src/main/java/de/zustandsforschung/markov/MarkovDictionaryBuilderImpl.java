package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;

public class MarkovDictionaryBuilderImpl<T> implements
		MarkovDictionaryBuilder<T> {

	private final MarkovDictionary<T> markovDictionary;
	private final Tokens<T> previousTokens;

	public MarkovDictionaryBuilderImpl() {
		this(new MarkovDictionary<T>());
	}

	public MarkovDictionaryBuilderImpl(final MarkovDictionary<T> dictionary) {
		this.markovDictionary = dictionary;
		this.previousTokens = new Tokens<T>();
	}

	@Override
	public void addTokens(final Tokens<T> tokens) {
		for (T token : tokens) {
			addToken(token);
		}
	}

	private void addToken(final T token) {
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
