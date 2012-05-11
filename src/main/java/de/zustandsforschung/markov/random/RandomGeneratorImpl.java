package de.zustandsforschung.markov.random;

import java.util.Random;

public class RandomGeneratorImpl implements RandomGenerator {

	Random random;

	public RandomGeneratorImpl() {
		this.random = new Random();
	}

	@Override
	public Double nextDouble() {
		return random.nextDouble();
	}

	@Override
	public Integer nextInteger(final int n) {
		return random.nextInt(n);
	}

}
