package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.DeterministicRandomGenerator;
import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public class MarkovTextGeneratorTest {

	private MarkovDictionaryBuilder markovDictionaryBuilder;
	private MarkovDictionary markovDictionary;

	@Before
	public void setUp() {
		markovDictionary = new MarkovDictionary();
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(
				markovDictionary);
	}

	@Test
	public void testGenerateEmpty() {
		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary);
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("", generator.generate(1));
	}

	@Test
	public void testGenerateSingle() {
		markovDictionaryBuilder.addTokens(new Tokens("one"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary);
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("one", generator.generate(1));
	}

	@Test
	public void testGenerateTwo() {
		markovDictionaryBuilder.addTokens(new Tokens("one", "two"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary);
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("one two", generator.generate(2));
	}

	@Test
	public void testGenerateOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(
				markovDictionary);
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary);
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("one two three", generator.generate(3));
	}

	@Test
	public void testGenerateSpacesBeforePunctuation() {
		markovDictionaryBuilder.addTokens(new Tokens("one", ","));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary);
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("one,", generator.generate(2));
	}

	@Test
	public void testGenerateUseStartToken() {
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary, "two");
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("three", generator.generate(1));
	}

	@Test
	public void testGenerateUseStartTokenOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(
				markovDictionary);
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "three"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary, "two");
		generator.setRandomGenerator(new RandomGeneratorImpl());
		assertEquals("three", generator.generate(1));
	}

	@Test
	public void testGenerateUseStartTokenOrder2With2Candidates()
			throws Exception {
		markovDictionary = new MarkovDictionary(2);
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(
				markovDictionary);
		markovDictionaryBuilder.addTokens(new Tokens("something", "candidate",
				"something else", "candidate", "start"));

		MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
				markovDictionary, "candidate");
		generator.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		assertEquals("start", generator.generate(1));
	}

}
