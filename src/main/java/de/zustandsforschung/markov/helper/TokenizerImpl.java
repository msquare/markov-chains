package de.zustandsforschung.markov.helper;

import de.zustandsforschung.markov.model.Tokens;

public class TokenizerImpl implements Tokenizer {

	@Override
	public Tokens tokenize(final String input) {
		final Tokens tokens = new Tokens();
		if (input != null && !"".equals(input)) {
			final String preparedInput = input.replaceAll(PUNCTUATION_REGEX,
					" $1 ");
			tokens.addAll(new Tokens(preparedInput.split(" +")));
		}
		return tokens;
	}

}
