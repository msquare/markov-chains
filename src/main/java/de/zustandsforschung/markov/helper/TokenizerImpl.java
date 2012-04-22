package de.zustandsforschung.markov.helper;

import de.zustandsforschung.markov.model.Tokens;

public class TokenizerImpl implements Tokenizer {

	public static final String PUNCTUATION_REGEX = "([\\.,!\\?\";])";

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

	@Override
	public String getPunctuationRegex() {
		return PUNCTUATION_REGEX;
	}

}
