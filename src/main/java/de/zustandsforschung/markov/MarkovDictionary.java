package de.zustandsforschung.markov;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class MarkovDictionary {

	private final Map<List<String>, Map<String, Double>> dictionary;
	
	public MarkovDictionary() {
		dictionary = new HashMap<List<String>, Map<String, Double>>();
	}

	public Map<String, Double> get(List<String> tokens) {
		return dictionary.get(tokens);
	}

	public void put(List<String> tokens,
			HashMap<String, Double> occurrences) {
		dictionary.put(tokens, occurrences);
	}

	public Set<List<String>> keySet() {
		return dictionary.keySet();
	}
	
}
