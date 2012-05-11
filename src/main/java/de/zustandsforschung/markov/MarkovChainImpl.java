package de.zustandsforschung.markov;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;

public class MarkovChainImpl implements MarkovChain {

	public final MarkovDictionary dictionary;
	private final Tokens previousTokens;
	private final Tokenizer tokenizer;

	public MarkovChainImpl() {
		this(new MarkovDictionary());
	}

	public MarkovChainImpl(final MarkovDictionary dictionary) {
		this.dictionary = dictionary;
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
		updatePreviousToken(token);
	}

	private void addTokenToDictionary(final String token) {
		Occurrences occurrences = dictionary.getOrCreate(previousTokens);
		occurrences.increaseCount(token);
	}

	private void updatePreviousToken(final String token) {
		if (previousTokens.size() >= dictionary.getOrder()) {
			previousTokens.remove(0);
		}
		previousTokens.add(token);
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
		return dictionary.toString();
	}

}
