package de.zustandsforschung.markov.random;

public class DeterministicRandomGenerator implements RandomGenerator {

	private Double randomNumber;
	
	public DeterministicRandomGenerator(double randomNumber) {
		this.randomNumber = randomNumber;
	}

	@Override
	public Double next() {
		return randomNumber;
	}

}
