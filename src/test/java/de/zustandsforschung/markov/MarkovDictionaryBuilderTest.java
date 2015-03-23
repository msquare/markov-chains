package de.zustandsforschung.markov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.model.Tokens;
import de.zustandsforschung.markov.random.DeterministicRandomGenerator;

public class MarkovDictionaryBuilderTest {

    private MarkovDictionaryBuilder<String> markovDictionaryBuilder;
    private MarkovDictionary<String> markovDictionary;
    private MarkovTextGenerator markovTextGenerator;

    @Before
    public void setUp() {
        markovDictionary = new MarkovDictionary<String>();
        markovDictionaryBuilder = new MarkovDictionaryBuilderImpl<String>(
                markovDictionary, false);
        markovTextGenerator = new MarkovTextGeneratorImpl(markovDictionary);
    }

    @Test
    public void testNextSingle() {
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        markovDictionaryBuilder.addTokens(new Tokens<String>("only"));
        assertEquals(null, markovTextGenerator.next(new Tokens<String>("only")));
    }

    @Test
    public void testNextTwo() {
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        markovDictionaryBuilder
                .addTokens(new Tokens<String>("first", "second"));
        assertEquals("second",
                markovTextGenerator.next(new Tokens<String>("first")));
    }

    @Test
    public void testNext50Percent() {
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        markovDictionaryBuilder.addTokens(new Tokens<String>("one", "two",
                "one", "three"));
        assertEquals("three",
                markovTextGenerator.next(new Tokens<String>("one")));
    }

    @Test
    public void testNext66Percent() {
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        markovDictionaryBuilder.addTokens(new Tokens<String>("one", "two",
                "one", "two", "one", "three"));
        assertEquals("three",
                markovTextGenerator.next(new Tokens<String>("one")));
    }

    @Test
    public void testNext50PercentOrder2() {
        markovDictionary = new MarkovDictionary<String>(2);
        markovDictionaryBuilder = new MarkovDictionaryBuilderImpl<String>(
                markovDictionary, false);
        markovDictionaryBuilder.addTokens(new Tokens<String>("one", "two",
                "one", "two", "one", "two", "three"));
        markovTextGenerator = new MarkovTextGeneratorImpl(markovDictionary);
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        assertEquals("three",
                markovTextGenerator.next(new Tokens<String>("one", "two")));
    }

    @Test
    public void testProbability100Percent() {
        markovDictionaryBuilder
                .addTokens(new Tokens<String>("first", "second"));
        assertEquals(1.0, markovDictionary.probability("second", "first"), 0.0);
    }

    @Test
    public void testProbability50Percent() {
        markovDictionaryBuilder.addTokens(new Tokens<String>("one", "two",
                "one", "three"));
        assertEquals(0.5, markovDictionary.probability("two", "one"), 0.0);
    }

    @Test
    public void testProbability66Percent() {
        markovDictionaryBuilder.addTokens(new Tokens<String>("one", "two",
                "one", "two", "one", "three"));
        assertEquals(0.66, markovDictionary.probability("two", "one"), 0.1);
    }

    @Test
    public void testProbability100PercentOrder2() {
        markovDictionary = new MarkovDictionary<String>(2);
        markovDictionaryBuilder = new MarkovDictionaryBuilderImpl<String>(
                markovDictionary, false);
        markovDictionaryBuilder.addTokens(new Tokens<String>("first", "second",
                "third"));
        assertEquals(1.0,
                markovDictionary.probability("third", "first", "second"), 0.0);
    }

    @Test
    public void testAddTokensWithoutClear() {
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        markovDictionaryBuilder.addTokens(new Tokens<String>("first"));
        markovDictionaryBuilder.addTokens(new Tokens<String>("second"));
        assertEquals("second",
                markovTextGenerator.next(new Tokens<String>("first")));
    }

    @Test
    public void testAddTokensWithClear() {
        markovTextGenerator
                .setRandomGenerator(new DeterministicRandomGenerator(0.7));
        markovDictionaryBuilder.addTokens(new Tokens<String>("first"));
        markovDictionaryBuilder.clearPreviousToken();
        markovDictionaryBuilder.addTokens(new Tokens<String>("second"));
        assertEquals(null,
                markovTextGenerator.next(new Tokens<String>("first")));
    }

}
