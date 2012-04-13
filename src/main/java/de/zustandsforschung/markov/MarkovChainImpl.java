package de.zustandsforschung.markov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.random.RandomGenerator;

public class MarkovChainImpl implements MarkovChain {

	private final Map<String, Map<String, Double>> dictionary;
	private RandomGenerator randomGenerator;
	private String previousToken = null;
	private Tokenizer tokenizer = null;

	public MarkovChainImpl() {
		dictionary = new HashMap<String, Map<String, Double>>();
		tokenizer = new TokenizerImpl();
	}

	@Override
	public String next(final String token) {
		Double random = randomGenerator.next();
		Map<String, Double> tokenCount = dictionary.get(token);
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
	public void addTokens(final List<String> tokens) {
		for (String token : tokens) {
			if (dictionary.get(previousToken) == null) {
				dictionary.put(previousToken, new HashMap<String, Double>());
			}
			Map<String, Double> tokenProbabilities = dictionary
					.get(previousToken);
			if (tokenProbabilities.get(token) == null) {
				tokenProbabilities.put(token, Double.valueOf(0));
			}
			tokenProbabilities.put(token, tokenProbabilities.get(token) + 1);
			previousToken = token;
		}
	}

	@Override
	public void addTokens(final String input) {
		addTokens(tokenizer.tokenize(input));
	}

	@Override
	public double probability(final String after, final String token) {
		Map<String, Double> tokenCount = dictionary.get(token);
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
		previousToken = null;
	}

	@Override
	public String toString() {
		return dictionary.toString();
	}

	@Override
	public String getPunctuationRegex() {
		return tokenizer.getPunctuationRegex();
	}
}
