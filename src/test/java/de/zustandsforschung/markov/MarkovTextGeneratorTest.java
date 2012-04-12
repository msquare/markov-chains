package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

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
		markovChain.addTokens(Arrays.asList("one"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one", generator.generate(1));
	}

	@Test
	public void testGenerateTwo() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(Arrays.asList("one", "two"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one two", generator.generate(2));
	}

	@Test
	public void testGenerateSpacesBeforePunctuation() {
		MarkovChain markovChain = new MarkovChainImpl();
		markovChain.setRandomGenerator(new RandomGeneratorImpl());
		markovChain.addTokens(Arrays.asList("one", ","));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(markovChain);
		assertEquals("one,", generator.generate(2));
	}

}
