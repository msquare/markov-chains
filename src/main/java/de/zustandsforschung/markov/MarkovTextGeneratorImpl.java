package de.zustandsforschung.markov;

import de.zustandsforschung.markov.model.Tokens;

public class MarkovTextGeneratorImpl implements MarkovTextGenerator {

	private final MarkovChain markovChain;
	private final String startToken;

	public MarkovTextGeneratorImpl(final MarkovChain markovChain) {
		this.markovChain = markovChain;
		this.startToken = null;
	}

	public MarkovTextGeneratorImpl(final MarkovChain markovChain,
			final String startToken) {
		this.markovChain = markovChain;
		this.startToken = startToken;
	}

	@Override
	public String generate(final int numTokens) {
		StringBuffer generated = new StringBuffer();
		Tokens previousTokens = new Tokens();
		if (startToken != null) {
			previousTokens = markovChain.findStartTokens(startToken);
		}
		for (int i = 0; i < numTokens; i++) {
			String token = markovChain.next(previousTokens);
			if (token != null) {
				if (!token.matches(markovChain.getPunctuationRegex())) {
					generated.append(" ");
				}
				generated.append(token);
			} else {
				break;
			}
			if (previousTokens.size() >= markovChain.getOrder()) {
				previousTokens.remove(0);
			}
			previousTokens.add(token);
		}
		return generated.toString().trim();
	}

}
