package de.zustandsforschung.markov.random;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomGeneratorTest {

	@Test
	public void testNextRandom() {
		RandomGenerator randomGen = new RandomGeneratorImpl();
		Double random = randomGen.next();
		assertTrue(random >= 0.0);
		assertTrue(random < 1.0);
	}

}
