package de.zustandsforschung.markov.helper;

import de.zustandsforschung.markov.model.Tokens;

public interface Tokenizer {

	Tokens tokenize(String input);

	String getPunctuationRegex();

}
