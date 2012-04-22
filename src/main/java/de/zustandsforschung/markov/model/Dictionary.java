package de.zustandsforschung.markov.model;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class Dictionary {

	private final Map<Tokens, Map<String, Double>> dictionary;
	
	public Dictionary() {
		dictionary = new HashMap<Tokens, Map<String, Double>>();
	}

	public Map<String, Double> get(Tokens tokens) {
		return dictionary.get(tokens);
	}

	public void put(Tokens tokens,
			HashMap<String, Double> occurrences) {
		dictionary.put(tokens, occurrences);
	}

	public Set<Tokens> keySet() {
		return dictionary.keySet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dictionary == null) ? 0 : dictionary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dictionary other = (Dictionary) obj;
		if (dictionary == null) {
			if (other.dictionary != null)
				return false;
		} else if (!dictionary.equals(other.dictionary))
			return false;
		return true;
	}
	
	
	
}
