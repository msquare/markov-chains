package de.zustandsforschung.markov.helper;

import java.util.List;

public interface Tokenizer {

	List<String> tokenize(String input);

	String getSeparatorRegex();

}
