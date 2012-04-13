package de.zustandsforschung.markov.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenizerImpl implements Tokenizer {

	public static final String PUNCTUATION_REGEX = "([\\.,!\\?\";])";

	@Override
	public List<String> tokenize(final String input) {
		final List<String> tokens = new ArrayList<String>();
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
