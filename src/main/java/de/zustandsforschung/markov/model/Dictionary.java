package de.zustandsforschung.markov.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary {

	private final Map<Tokens, Occurrences> dictionary;

	public Dictionary() {
		dictionary = new HashMap<Tokens, Occurrences>();
	}

	public Occurrences getOrCreate(final Tokens tokens) {
		if (dictionary.get(tokens) == null) {
			dictionary.put(tokens.duplicate(), new Occurrences());
		}

		return dictionary.get(tokens);
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

	public double probability(final String after, final String... tokens) {
		Occurrences occurrences = get(new Tokens(tokens));
		if (occurrences != null) {
			Double count = occurrences.get(after);
			if (count != null) {
				return occurrences.calculateProbability(count);
			}
		}
		return 0.0;
	}

}
