package de.zustandsforschung.markov;

public class MarkovTextGeneratorImpl implements MarkovTextGenerator {

	private final MarkovChain markovChain;

	public MarkovTextGeneratorImpl(final MarkovChain markovChain) {
		this.markovChain = markovChain;
	}

	@Override
	public String generate(final int numTokens) {
		StringBuffer generated = new StringBuffer();
		String previousToken = null;
		for (int i = 0; i < numTokens; i++) {
			String token = markovChain.next(previousToken);
			previousToken = token;
			if (token != null) {
				if (!token.matches(markovChain.getSeparatorRegex())) {
					generated.append(" ");
				}
				generated.append(token);
			} else {
				break;
			}
		}
		return generated.toString().trim();
	}
}
