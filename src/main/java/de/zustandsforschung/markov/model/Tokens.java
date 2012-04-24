package de.zustandsforschung.markov.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Tokens implements Iterable<String> {

	private final List<String> tokens;

	public Tokens() {
		this(new ArrayList<String>());
	}

	public Tokens(final String... tokens) {
		this(Arrays.asList(tokens));
	}

	public Tokens(final List<String> tokens) {
		this.tokens = tokens;
	}

	@Override
	public Iterator<String> iterator() {
		return tokens.iterator();
	}

	public int size() {
		return tokens.size();
	}

	public void remove(final int i) {
		tokens.remove(i);
	}

	public void add(final String token) {
		tokens.add(token);
	}

	public Tokens duplicate() {
		@SuppressWarnings("unchecked")
		Tokens duplicate = new Tokens(
				(List<String>) ((ArrayList<String>) tokens).clone());
		return duplicate;
	}

	public void clear() {
		tokens.clear();
	}

	public String get(final int i) {
		return tokens.get(i);
	}

	public void addAll(final Tokens tokens) {
		this.tokens.addAll(tokens.tokens);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokens == null) ? 0 : tokens.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tokens other = (Tokens) obj;
		if (tokens == null) {
			if (other.tokens != null)
				return false;
		} else if (!tokens.equals(other.tokens))
			return false;
		return true;
	}

}
