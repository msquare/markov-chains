package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public class MarkovTextGeneratorTest {

	private MarkovChain markovChain;
	private MarkovDictionary markovDictionary;

	@Before
	public void setUp() {
		markovDictionary = new MarkovDictionary();
		markovChain = new MarkovChainImpl(markovDictionary);
	}
	
	@Test
	public void testGenerateEmpty() {
		markovChain.setRandomGenerator(new RandomGeneratorImpl());

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain, markovDictionary);
		assertEquals("", generator.generate(1));
	}

	@Test
	public void testGenerateSingle() {
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain, markovDictionary);
		assertEquals("one", generator.generate(1));
	}

	@Test
	public void testGenerateTwo() {
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", "two"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain, markovDictionary);
		assertEquals("one two", generator.generate(2));
	}

	@Test
	public void testGenerateOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovChain = new MarkovChainImpl(markovDictionary);
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain, markovDictionary);
		assertEquals("one two three", generator.generate(3));
	}

	@Test
	public void testGenerateSpacesBeforePunctuation() {
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", ","));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain, markovDictionary);
		assertEquals("one,", generator.generate(2));
	}

	@Test
	public void testGenerateUseStartToken() {
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovChain, markovDictionary, "two");
		assertEquals("three", generator.generate(1));
	}

	@Test
	public void testGenerateUseStartTokenOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovChain = new MarkovChainImpl(markovDictionary);
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovChain, markovDictionary, "two");
		assertEquals("three", generator.generate(1));
	}

}
