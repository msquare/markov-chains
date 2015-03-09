package de.zustandsforschung.markov.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Occurrences<T> implements Serializable {
	private static final long serialVersionUID = 5983325696717800011L;
	private final Map<T, Double> occurrences;

	public Occurrences() {
		occurrences = new HashMap<T, Double>();
	}

	/**
	 * Returns the T with the highest probability.
	 */
	public T highestProbability() {
		T winner = null;
		for (Entry<T, Double> e : entrySet())
			if ((winner == null) || (e.getValue() > occurrences.get(winner)))
				winner = e.getKey();

		return winner;
	}

	public Set<T> keySet() {
		return occurrences.keySet();
	}

	public Set<Entry<T, Double>> entrySet() {
		return occurrences.entrySet();
	}

	public Double get(final T token) {
		return occurrences.get(token);
	}

	public void put(final T token, final Double count) {
		occurrences.put(token, count);
	}

	public Collection<Double> values() {
		return occurrences.values();
	}

	public void increaseCount(final T token) {
		if (occurrences.get(token) == null)
			occurrences.put(token, Double.valueOf(0));
		occurrences.put(token, occurrences.get(token) + 1);
	}

	public Double totalCount() {
		Double totalCount = 0.0;
		for (Double count : values())
			totalCount += count;
		return totalCount;
	}

	public double calculateProbability(final Double occurrence) {
		return occurrence / totalCount();
	}

	@Override
	public String toString() {
		return occurrences.toString();
	}

}
