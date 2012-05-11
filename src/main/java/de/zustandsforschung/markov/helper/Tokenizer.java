package de.zustandsforschung.markov.helper;

import de.zustandsforschung.markov.model.Tokens;

public interface Tokenizer {

	public static final String PUNCTUATION_REGEX = "([\\.,!\\?\";])";

	Tokens tokenize(String input);

}
