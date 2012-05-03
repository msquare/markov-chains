package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.DeterministicRandomGenerator;

public class MarkovChainTest {

	private MarkovChain markovChain;
	private MarkovDictionary markovDictionary;

	@Before
	public void setUp() {
		markovDictionary = new MarkovDictionary();
		markovChain = new MarkovChainImpl(markovDictionary);
	}

	@Test
	public void testNextSingle() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("only"));
		assertEquals(null, markovChain.next(new Tokens("only")));
	}

	@Test
	public void testNextTwo() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("first", "second"));
		assertEquals("second", markovChain.next(new Tokens("first")));
	}

	@Test
	public void testNext50Percent() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("one", "two", "one", "three"));
		assertEquals("three", markovChain.next(new Tokens("one")));
	}

	@Test
	public void testNext66Percent() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("one", "two", "one", "two", "one",
				"three"));
		assertEquals("three", markovChain.next(new Tokens("one")));
	}

	@Test
	public void testNext50PercentOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovChain = new MarkovChainImpl(markovDictionary);
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("one", "two", "one", "two", "one",
				"two", "three"));
		assertEquals("three", markovChain.next(new Tokens("one", "two")));
	}

	@Test
	public void testProbability100Percent() {
		markovChain.addTokens(new Tokens("first", "second"));
		assertEquals(1.0, markovDictionary.probability("second", "first"), 0.0);
	}

	@Test
	public void testProbability50Percent() {
		markovChain.addTokens(new Tokens("one", "two", "one", "three"));
		assertEquals(0.5, markovDictionary.probability("two", "one"), 0.0);
	}

	@Test
	public void testProbability66Percent() {
		markovChain.addTokens(new Tokens("one", "two", "one", "two", "one",
				"three"));
		assertEquals(0.66, markovDictionary.probability("two", "one"), 0.1);
	}

	@Test
	public void testProbabilityWithTokenizer() {
		markovChain.addTokens("one two one two one three");
		assertEquals(0.66, markovDictionary.probability("two", "one"), 0.1);
	}

	@Test
	public void testProbability100PercentOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovChain = new MarkovChainImpl(markovDictionary);
		markovChain.addTokens(new Tokens("first", "second", "third"));
		assertEquals(1.0, markovDictionary.probability("third", "first", "second"),
				0.0);
	}

	@Test
	public void testAddTokensWithoutClear() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("first"));
		markovChain.addTokens(new Tokens("second"));
		assertEquals("second", markovChain.next(new Tokens("first")));
	}

	@Test
	public void testAddTokensWithClear() {
		markovChain.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovChain.addTokens(new Tokens("first"));
		markovChain.clearPreviousToken();
		markovChain.addTokens(new Tokens("second"));
		assertEquals(null, markovChain.next(new Tokens("first")));
	}

}
