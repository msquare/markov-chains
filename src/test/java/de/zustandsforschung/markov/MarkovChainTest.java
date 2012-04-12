package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.zustandsforschung.markov.random.DeterministicRandomGenerator;

public class MarkovChainTest {

	private MarkovChain markovChain;

	@Before
	public void setUp() {
		markovChain = new MarkovChainImpl();
	}

	@Test
	public void testNextSingle() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(Arrays.asList("only"));
		assertEquals(null, markovChain.next("only"));
	}

	@Test
	public void testNextTwo() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(Arrays.asList("first", "second"));
		assertEquals("second", markovChain.next("first"));
	}

	@Test
	public void testNext50Percent() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(Arrays.asList("one", "two", "one", "three"));
		assertEquals("three", markovChain.next("one"));
	}

	@Test
	public void testNext66Percent() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(Arrays.asList("one", "two", "one", "two", "one",
				"three"));
		assertEquals("three", markovChain.next("one"));
	}

	@Test
	public void testProbability100Percent() {
		markovChain.addTokens(Arrays.asList("first", "second"));
		assertEquals(1.0, markovChain.probability("second", "first"), 0.0);
	}

	@Test
	public void testProbability50Percent() {
		markovChain.addTokens(Arrays.asList("one", "two", "one", "three"));
		assertEquals(0.5, markovChain.probability("two", "one"), 0.0);
	}

	@Test
	public void testProbability66Percent() {
		markovChain.addTokens(Arrays.asList("one", "two", "one", "two", "one",
				"three"));
		assertEquals(0.66, markovChain.probability("two", "one"), 0.1);
	}

	@Test
	public void testProbabilityWithTokenizer() {
		markovChain.addTokens("one two one two one three");
		assertEquals(0.66, markovChain.probability("two", "one"), 0.1);
	}

	@Test
	public void testAddTokensWithoutClear() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(Arrays.asList("first"));
		markovChain.addTokens(Arrays.asList("second"));
		assertEquals("second", markovChain.next("first"));
	}

	@Test
	public void testAddTokensWithClear() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(Arrays.asList("first"));
		markovChain.clearPreviousToken();
		markovChain.addTokens(Arrays.asList("second"));
		assertEquals(null, markovChain.next("first"));
	}

}
