package de.zustandsforschung.markov;

import java.util.Map;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.model.Dictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public class MarkovChainImpl implements MarkovChain {

	private final Dictionary dictionary;
	private RandomGenerator randomGenerator;
	private final Tokens previousTokens;
	private final Tokenizer tokenizer;
	private int order;

	public MarkovChainImpl() {
		dictionary = new Dictionary();
		previousTokens = new Tokens();
		tokenizer = new TokenizerImpl();
		order = 1;
	}

	@Override
	public String next(final Tokens tokens) {
		Double random = randomGenerator.next();
		Occurrences occurrences = dictionary.get(tokens);
		if (occurrences != null) {
			Double probability = 0.0;
			for (Map.Entry<String, Double> entry : occurrences.entrySet()) {
				probability += calculateProbability(entry.getValue(),
						occurrences);
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
			if (dictionary.get(previousTokens) == null) {
				dictionary.put(createDictionaryKey(), new Occurrences());
			}
			Occurrences occurrences = dictionary.get(previousTokens);
			if (occurrences.get(token) == null) {
				occurrences.put(token, Double.valueOf(0));
			}
			occurrences.put(token, occurrences.get(token) + 1);
			if (previousTokens.size() >= order) {
				previousTokens.remove(0);
			}
			previousTokens.add(token);
		}
	}

	private Tokens createDictionaryKey() {
		return previousTokens.duplicate();
	}

	@Override
	public void addTokens(final String input) {
		addTokens(tokenizer.tokenize(input));
	}

	@Override
	public double probability(final String after, final String... tokens) {
		Occurrences tokenCount = dictionary.get(new Tokens(tokens));
		if (tokenCount != null) {
			Double count = tokenCount.get(after);
			if (count != null) {
				return calculateProbability(count, tokenCount);
			}
		}
		return 0.0;
	}

	private double calculateProbability(final Double occurrence,
			final Occurrences occurrences) {
		return occurrence / occurrences.totalCount();
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

	@Override
	public void setOrder(final int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tokens findStartTokens(final String startToken) {
		for (Tokens tokens : dictionary.keySet()) {
			if (tokens.size() > 0
					&& tokens.get(tokens.size() - 1).equals(startToken)) {
				return tokens;
			}
		}
		return null;
	}
}
