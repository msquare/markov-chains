package de.zustandsforschung.markov.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary {

	private final Map<Tokens, Occurrences> dictionary;

	public Dictionary() {
		dictionary = new HashMap<Tokens, Occurrences>();
	}

	public Occurrences get(final Tokens tokens) {
		return dictionary.get(tokens);
	}

	public void put(final Tokens tokens, final Occurrences occurrences) {
		dictionary.put(tokens, occurrences);
	}

	public Set<Tokens> keySet() {
		return dictionary.keySet();
	}

}
