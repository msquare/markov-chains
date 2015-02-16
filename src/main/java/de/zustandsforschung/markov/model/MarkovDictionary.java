package de.zustandsforschung.markov.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MarkovDictionary<T> implements Serializable {
	private static final long serialVersionUID = -2463181382981797814L;
	private final Map<Tokens<T>, Occurrences<T>> dictionary;
	private final int order;

	public MarkovDictionary() {
		this(1);
	}

	public MarkovDictionary(final int order) {
		dictionary = new HashMap<Tokens<T>, Occurrences<T>>();
		this.order = order;
	}

	public Occurrences<T> getOrCreate(final Tokens<T> tokens) {
		if (dictionary.get(tokens) == null) {
			dictionary.put(tokens.duplicate(), new Occurrences<T>());
		}

		return dictionary.get(tokens);
	}

	public Occurrences<T> get(final Tokens<T> tokens) {
		return dictionary.get(tokens);
	}

	public Set<Tokens<T>> allTokens() {
		return dictionary.keySet();
	}

	public double probability(final T after, final T... tokens) {
		Occurrences<T> occurrences = get(new Tokens<T>(tokens));
		if (occurrences != null) {
			Double count = occurrences.get(after);
			if (count != null) {
				return occurrences.calculateProbability(count);
			}
		}
		return 0.0;
	}

	public int getOrder() {
		return order;
	}

}
