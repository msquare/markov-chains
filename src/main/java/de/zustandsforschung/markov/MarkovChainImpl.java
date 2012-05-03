package de.zustandsforschung.markov;

import java.util.Map;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public class MarkovChainImpl implements MarkovChain {

	public final MarkovDictionary dictionary;
	private RandomGenerator randomGenerator;
	private final Tokens previousTokens;
	private final Tokenizer tokenizer;

	public MarkovChainImpl() {
		this(new MarkovDictionary());
	}
	
	public MarkovChainImpl(MarkovDictionary dictionary) {
		this.dictionary = dictionary;
		previousTokens = new Tokens();
		tokenizer = new TokenizerImpl();
	}

	@Override
	public String next(final Tokens tokens) {
		Double random = randomGenerator.next();
		Occurrences occurrences = dictionary.get(tokens);
		if (occurrences != null) {
			Double probability = 0.0;
			for (Map.Entry<String, Double> entry : occurrences.entrySet()) {
				probability += occurrences.calculateProbability(entry.getValue());
				if (random < probability) {
					return entry.getKey();
				}
			}
		}
		return null;
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
	public void setRandomGenerator(final RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	@Override
	public void clearPreviousToken() {
		previousTokens.clear();
	}

	@Override
	public String toString() {
		return dictionary.toString();
	}

	@Override
	public String getPunctuationRegex() {
		return tokenizer.getPunctuationRegex();
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tokens findStartTokens(final String startToken) {
		for (Tokens tokens : dictionary.allTokens()) {
			if (tokens.size() > 0
					&& tokens.get(tokens.size() - 1).equals(startToken)) {
				return tokens;
			}
		}
		return null;
	}

}
