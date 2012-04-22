package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public class MarkovTextGeneratorTest {

	@Test
	public void testGenerateEmpty() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("", generator.generate(1));
	}

	@Test
	public void testGenerateSingle() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one", generator.generate(1));
	}

	@Test
	public void testGenerateTwo() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", "two"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one two", generator.generate(2));
	}

	@Test
	public void testGenerateOrder2() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.setOrder(2);
		markovChain.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one two three", generator.generate(3));
	}

	@Test
	public void testGenerateSpacesBeforePunctuation() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", ","));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one,", generator.generate(2));
	}

	@Test
	public void testGenerateUseStartToken() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovChain, "two");
		assertEquals("three", generator.generate(1));
	}

	@Test
	public void testGenerateUseStartTokenOrder2() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.setOrder(2);
		markovChain.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovChain, "two");
		assertEquals("three", generator.generate(1));
	}

}
