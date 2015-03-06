package de.zustandsforschung.markov.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Tokens<T> implements Iterable<T>, Serializable {
	private static final long serialVersionUID = -311817455986024131L;
	private final List<T> tokens;

	public Tokens() {
		this(new ArrayList<T>());
	}

	public Tokens(final T... tokens) {
		this(Arrays.asList(tokens));
	}

	public Tokens(final List<T> tokens) {
		this.tokens = tokens;
	}

	@Override
	public Iterator<T> iterator() {
		return tokens.iterator();
	}

	public int size() {
		return tokens.size();
	}

	public Tokens<T> duplicate() {
		@SuppressWarnings("unchecked")
		Tokens<T> duplicate = new Tokens<T>(
				(List<T>) ((ArrayList<T>) tokens).clone());
		return duplicate;
	}

	public void clear() {
		tokens.clear();
	}

	public T get(final int i) {
		return tokens.get(i);
	}

	public void addAll(final Tokens<T> tokens) {
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
		Tokens<T> other = (Tokens<T>) obj;
		if (tokens == null) {
			if (other.tokens != null)
				return false;
		} else if (!tokens.equals(other.tokens))
			return false;
		return true;
	}

	public void update(final int order, final T token) {
		if (size() >= order)
			tokens.remove(0);

		tokens.add(token);
	}

	@Override
	public String toString() {
		return tokens.toString();
	}

}
