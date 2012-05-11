package de.zustandsforschung.markov;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;

public class MarkovDictionaryBuilderImpl implements MarkovDictionaryBuilder {

	public final MarkovDictionary markovDictionary;
	private final Tokens previousTokens;
	private final Tokenizer tokenizer;

	public MarkovDictionaryBuilderImpl() {
		this(new MarkovDictionary());
	}

	public MarkovDictionaryBuilderImpl(final MarkovDictionary dictionary) {
		this.markovDictionary = dictionary;
		previousTokens = new Tokens();
		tokenizer = new TokenizerImpl();
	}

	@Override
	public void addTokens(final Tokens tokens) {
		for (String token : tokens) {
			addToken(token);
		}
	}

	private void addToken(final String token) {
		addTokenToDictionary(token);
		previousTokens.update(markovDictionary.getOrder(), token);
	}

	private void addTokenToDictionary(final String token) {
		Occurrences occurrences = markovDictionary.getOrCreate(previousTokens);
		occurrences.increaseCount(token);
	}

	@Override
	public void addTokens(final String input) {
		addTokens(tokenizer.tokenize(input));
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
