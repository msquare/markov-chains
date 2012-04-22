package de.zustandsforschung.markov.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.zustandsforschung.markov.model.Tokens;

public class TokenizerImpl implements Tokenizer {

	public static final String PUNCTUATION_REGEX = "([\\.,!\\?\";])";

	@Override
	public Tokens tokenize(final String input) {
		final Tokens tokens = new Tokens();
		if (input != null && !"".equals(input)) {
			final String preparedInput = input.replaceAll(PUNCTUATION_REGEX,
					" $1 ");
			tokens.addAll(Arrays.asList(preparedInput.split(" +")));
		}
		return tokens;
	}

	@Override
	public String getPunctuationRegex() {
		return PUNCTUATION_REGEX;
	}

}
