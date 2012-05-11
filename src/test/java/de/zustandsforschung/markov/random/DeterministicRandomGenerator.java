package de.zustandsforschung.markov.random;

public class DeterministicRandomGenerator implements RandomGenerator {

	private final Double randomNumber;

	public DeterministicRandomGenerator(final double randomNumber) {
		this.randomNumber = randomNumber;
	}

	@Override
	public Double nextDouble() {
		return randomNumber;
	}

	@Override
	public Integer nextInteger(final int n) {
		return n - 1;
	}

}
