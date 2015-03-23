package de.zustandsforschung.markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.zustandsforschung.markov.helper.Tokenizer;
import de.zustandsforschung.markov.helper.TokenizerImpl;
import de.zustandsforschung.markov.model.MarkovDictionary;
import de.zustandsforschung.markov.random.RandomGeneratorImpl;

public final class MarkovCommandLine {

    private MarkovCommandLine() {
    }

    public static void main(final String[] args) throws IOException {
        MarkovDictionary<String> markovDictionary = new MarkovDictionary<String>(
                Integer.valueOf(args[2]));
        MarkovDictionaryBuilder<String> markovDictionaryBuilder = new MarkovDictionaryBuilderImpl<String>(
                markovDictionary, false);
        fromFile(markovDictionaryBuilder, new File(args[0]));

        MarkovTextGenerator generator = new MarkovTextGeneratorImpl(
                markovDictionary, args[3]);
        generator.setRandomGenerator(new RandomGeneratorImpl());
        if (args.length > 4 && "true".equals(args[4])) {
            System.out.print(args[3] + " ");
        }
        System.out.println(generator.generate(Integer.valueOf(args[1])));
    }

    public static void fromFile(
            final MarkovDictionaryBuilder<String> markovDictionaryBuilder,
            final File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Tokenizer t = new TokenizerImpl();
        while ((line = br.readLine()) != null) {
            markovDictionaryBuilder.addTokens(t.tokenize(line));
        }
        br.close();
    }
}
