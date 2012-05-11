package de.zustandsforschung.markov;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Occurrences;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGenerator;

public class MarkovTextGeneratorImpl implements MarkovTextGenerator {

	private final String startToken;
	private final MarkovDictionary markovDictionary;
	private RandomGenerator randomGenerator;
	private final StringBuffer generated;
	private Tokens previousTokens;

	public MarkovTextGeneratorImpl(final MarkovDictionary markovDictionary) {
		this(markovDictionary, null);
	}

	public MarkovTextGeneratorImpl(final MarkovDictionary markovDictionary,
			final String startToken) {
		this.markovDictionary = markovDictionary;
		this.startToken = startToken;
		this.generated = new StringBuffer();
		this.previousTokens = new Tokens();
	}

	@Override
	public String generate(final int numTokens) {
		if (startToken != null) {
			previousTokens = findStartTokens(startToken);
		}
		for (int i = 0; i < numTokens; i++) {
			appendToken(previousTokens);
		}
		return generated.toString().trim();
	}

	/**
	 * Finds a list of tokens in the markovDictionary such that the last element
	 * of this list is the startToken. If there's more than one candidate, then
	 * one of them is selected randomly.
	 * 
	 * @param startToken
	 *            Token that should be used as a starting point (markov chain
	 *            starts after this token).
	 * @return List of tokens where the first element is the start token.
	 */
	private Tokens findStartTokens(final String startToken) {
		List<Tokens> startTokensCandidates = new ArrayList<Tokens>();
		for (Tokens tokens : markovDictionary.allTokens()) {
			if (tokens.size() > 0
					&& tokens.get(tokens.size() - 1).equals(startToken)) {
				startTokensCandidates.add(tokens);
			}
		}
		int maxRandomInteger = startTokensCandidates.size();
		return startTokensCandidates.get(randomGenerator
				.nextInteger(maxRandomInteger));
	}

	private void appendToken(final Tokens previousTokens) {
		String token = next(previousTokens);
		if (token != null) {
			if (!token.matches(Tokenizer.PUNCTUATION_REGEX)) {
				generated.append(" ");
			}
			generated.append(token);
		}
		previousTokens.update(markovDictionary.getOrder(), token);
	}

	@Override
	public String next(final Tokens tokens) {
		Double random = randomGenerator.nextDouble();
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

	@Override
	public void setRandomGenerator(final RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

}
