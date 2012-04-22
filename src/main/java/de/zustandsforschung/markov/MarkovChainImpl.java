package de.zustandsforschung.markov;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.model.Dictionary;
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
		Map<String, Double> tokenCount = dictionary.get(tokens);
		if (tokenCount != null) {
			Double probability = 0.0;
			for (Map.Entry<String, Double> entry : tokenCount.entrySet()) {
				probability += calculateProbability(entry.getValue(),
						tokenCount);
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
				dictionary.put(createDictionaryKey(),
						new HashMap<String, Double>());
			}
			Map<String, Double> tokenProbabilities = dictionary
					.get(previousTokens);
			if (tokenProbabilities.get(token) == null) {
				tokenProbabilities.put(token, Double.valueOf(0));
			}
			tokenProbabilities.put(token, tokenProbabilities.get(token) + 1);
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
		Map<String, Double> tokenCount = dictionary.get(new Tokens(Arrays.asList(tokens)));
		if (tokenCount != null) {
			Double count = tokenCount.get(after);
			if (count != null) {
				return calculateProbability(count, tokenCount);
			}
		}
		return 0.0;
	}

	private double calculateProbability(final Double count,
			final Map<String, Double> tokenCount) {
		return count / totalTokenCount(tokenCount);
	}

	private Double totalTokenCount(final Map<String, Double> tokenCount) {
		Double totalCount = 0.0;
		for (Double count : tokenCount.values()) {
			totalCount += count;
		}
		return totalCount;
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
