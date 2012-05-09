package de.zustandsforschung.markov;

import java.util.Map;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public class MarkovTextGeneratorImpl implements MarkovTextGenerator {

	private final MarkovChain markovChain;
	private final String startToken;
	private final MarkovDictionary markovDictionary;
	private RandomGenerator randomGenerator;

	public MarkovTextGeneratorImpl(final MarkovChain markovChain,
			final MarkovDictionary markovDictionary) {
		this(markovChain, markovDictionary, null);
	}

	public MarkovTextGeneratorImpl(final MarkovChain markovChain,
			final MarkovDictionary markovDictionary, final String startToken) {
		this.markovChain = markovChain;
		this.markovDictionary = markovDictionary;
		this.startToken = startToken;
	}

	@Override
	public String generate(final int numTokens) {
		StringBuffer generated = new StringBuffer();
		Tokens previousTokens = new Tokens();
		if (startToken != null) {
			previousTokens = findStartTokens(startToken);
		}
		for (int i = 0; i < numTokens; i++) {
			String token = next(previousTokens);
			if (token != null) {
				if (!token.matches(markovChain.getPunctuationRegex())) {
					generated.append(" ");
				}
				generated.append(token);
			} else {
				break;
			}
			if (previousTokens.size() >= markovDictionary.getOrder()) {
				previousTokens.remove(0);
			}
			previousTokens.add(token);
		}
		return generated.toString().trim();
	}

	@Override
	public String next(final Tokens tokens) {
		Double random = randomGenerator.next();
		Occurrences occurrences = markovDictionary.get(tokens);
		if (occurrences != null) {
			Double probability = 0.0;
			for (Map.Entry<String, Double> entry : occurrences.entrySet()) {
				probability += occurrences.calculateProbability(entry
						.getValue());
				if (random < probability) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tokens findStartTokens(final String startToken) {
		for (Tokens tokens : markovDictionary.allTokens()) {
			if (tokens.size() > 0
					&& tokens.get(tokens.size() - 1).equals(startToken)) {
				return tokens;
			}
		}
		return null;
	}

	@Override
	public void setRandomGenerator(final RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

}
