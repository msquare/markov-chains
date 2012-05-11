package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.DeterministicRandomGenerator;

public class MarkovDictionaryBuilderTest {

	private MarkovDictionaryBuilder markovDictionaryBuilder;
	private MarkovDictionary markovDictionary;
	private MarkovTextGenerator markovTextGenerator;

	@Before
	public void setUp() {
		markovDictionary = new MarkovDictionary();
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(markovDictionary);
		markovTextGenerator = new MarkovTextGeneratorImpl(markovDictionary);
	}

	@Test
	public void testNextSingle() {
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovDictionaryBuilder.addTokens(new Tokens("only"));
		assertEquals(null, markovTextGenerator.next(new Tokens("only")));
	}

	@Test
	public void testNextTwo() {
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovDictionaryBuilder.addTokens(new Tokens("first", "second"));
		assertEquals("second", markovTextGenerator.next(new Tokens("first")));
	}

	@Test
	public void testNext50Percent() {
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "one", "three"));
		assertEquals("three", markovTextGenerator.next(new Tokens("one")));
	}

	@Test
	public void testNext66Percent() {
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "one", "two", "one",
				"three"));
		assertEquals("three", markovTextGenerator.next(new Tokens("one")));
	}

	@Test
	public void testNext50PercentOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(markovDictionary);
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "one", "two", "one",
				"two", "three"));
		markovTextGenerator = new MarkovTextGeneratorImpl(markovDictionary);
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		assertEquals("three",
				markovTextGenerator.next(new Tokens("one", "two")));
	}

	@Test
	public void testProbability100Percent() {
		markovDictionaryBuilder.addTokens(new Tokens("first", "second"));
		assertEquals(1.0, markovDictionary.probability("second", "first"), 0.0);
	}

	@Test
	public void testProbability50Percent() {
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "one", "three"));
		assertEquals(0.5, markovDictionary.probability("two", "one"), 0.0);
	}

	@Test
	public void testProbability66Percent() {
		markovDictionaryBuilder.addTokens(new Tokens("one", "two", "one", "two", "one",
				"three"));
		assertEquals(0.66, markovDictionary.probability("two", "one"), 0.1);
	}

	@Test
	public void testProbabilityWithTokenizer() {
		markovDictionaryBuilder.addTokens("one two one two one three");
		assertEquals(0.66, markovDictionary.probability("two", "one"), 0.1);
	}

	@Test
	public void testProbability100PercentOrder2() {
		markovDictionary = new MarkovDictionary(2);
		markovDictionaryBuilder = new MarkovDictionaryBuilderImpl(markovDictionary);
		markovDictionaryBuilder.addTokens(new Tokens("first", "second", "third"));
		assertEquals(1.0,
				markovDictionary.probability("third", "first", "second"), 0.0);
	}

	@Test
	public void testAddTokensWithoutClear() {
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovDictionaryBuilder.addTokens(new Tokens("first"));
		markovDictionaryBuilder.addTokens(new Tokens("second"));
		assertEquals("second", markovTextGenerator.next(new Tokens("first")));
	}

	@Test
	public void testAddTokensWithClear() {
		markovTextGenerator
				.setRandomGenerator(new DeterministicRandomGenerator(0.7));
		markovDictionaryBuilder.addTokens(new Tokens("first"));
		markovDictionaryBuilder.clearPreviousToken();
		markovDictionaryBuilder.addTokens(new Tokens("second"));
		assertEquals(null, markovTextGenerator.next(new Tokens("first")));
	}

}
