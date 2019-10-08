package translator;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.*;

public class Main {

    final static String INPUT_FILE_PATH = "src/main/resources/input.txt";
    final static String OUTPUT_FILE_PATH = "src/main/resources/output.txt";
    final static String TAGGER_PATH = "src/main/resources/english-left3words-distsim.tagger";

    public static void main(String[] args) {

        BasicConfigurator.configure();

        File infile = new File(INPUT_FILE_PATH);
        File outfile = new File(OUTPUT_FILE_PATH);
        File taggerFile = new File(TAGGER_PATH);

        MaxentTagger tagger = new MaxentTagger();

        try {
            InputStream targetStream = new FileInputStream(taggerFile);
            tagger = new MaxentTagger(targetStream);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        List<TaggedWord> taggedWords;

        try {
            //clear contents of outfile from previous run
            PrintWriter pw = new PrintWriter(outfile);
            pw.close();

            //read in entire text file to String
            String fileContents = fileContentsToString(infile);

            // strips off all non-ASCII characters from input
            fileContents = fileContents.replaceAll("[^\\x00-\\x7F]", "");

            //tag each word with its respective POS
            String fileContentsTagged = tagger.tagString(fileContents);

            //create list of taggedWords from tagged fileContent String
            taggedWords = processTaggedWords(fileContentsTagged);

            //TODO: THIS DOESN'T WORK YET
            //convert irregular plural english noun
            //to its normal singular form
            //pluralToSingularForm(taggedWords);

            //prepare english words for uasi translation
            //make necessary modifications
            primeTheTranslator(taggedWords);

            //remove "will" and add "P" to the next word
            List<TaggedWord> taggedWordsWithFuture = accountForFutureTense(taggedWords);

            //convert processed english words to String of uasi words
            String uasiContent = translateToUasi(taggedWordsWithFuture);

            //print uasi result
            printStringToOutfile(uasiContent, outfile);
        } catch (FileNotFoundException e1) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.print("Error writing to file.");
        }

    }  //end main

    public static void primeTheTranslator(List<TaggedWord> taggedWords) {
        for (TaggedWord tw : taggedWords) {
            PrimeTheTranslator.prime(tw);
        }
    }

    private static HashMap<String, String> importIrregularPluralNouns() {
        HashMap<String, String> nounMap = new HashMap();
        try {
            File file = new File("flat_files/irregularPluralNouns.txt");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.charAt(0) != '#') {
                    String[] split = line.split("\\s+");
                    nounMap.put(split[1], split[0]);
                }
            }
            return nounMap;
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: irregularPluralNouns.txt");
            return null;
        }
    }

    public static void pluralToSingularForm(List<TaggedWord> taggedWords) {
        HashMap<String, String> nounMap = importIrregularPluralNouns();
        for (TaggedWord tw : taggedWords) {
            for (Map.Entry<String, String> entry : nounMap.entrySet()) {
                if (tw.getEnglishWord().equals(entry.getKey())) {
                    tw.setEnglishWord(entry.getValue());
                }
            }
        }
    }

    public static List<TaggedWord> accountForFutureTense(List<TaggedWord> taggedWords) {
        List<TaggedWord> taggedWordsWithFuture = new ArrayList<>();
        for (int i = 0; i < taggedWords.size(); i++) {
            if (taggedWords.get(i).getEnglishWord().equals("will")) {
                taggedWords.get(i + 1).makeFutureTense();
            } else {
                taggedWordsWithFuture.add(taggedWords.get(i));
            }
        }
        return taggedWordsWithFuture;
    }

    public static String fileContentsToString(File infile) throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(infile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line + " ");
        }
        reader.close();
        return out.toString();
    }

    public static String translateToUasi(List<TaggedWord> taggedWords) {
        String uasiContent = "";

        for (TaggedWord tw : taggedWords) {
            String englishWord = tw.getEnglishWord();
            String uasiWord = translate(englishWord);
            if (!tw.isPunctuation()) {
                uasiContent += uasiWord + " ";
            } else {
                //remove last space
                uasiContent = uasiContent.substring(0, uasiContent.length() - 1);
                uasiContent += uasiWord + " ";
            }
        }
        return uasiContent;
    }

    public static void printStringToOutfile(String outputString, File outfile) throws IOException {
        FileWriter fw = new FileWriter(outfile, true); //true = append
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(outputString);
        bw.close();
    }

    public static List<TaggedWord> processTaggedWords(String taggedContents) {
        TaggedWordFactory taggedWordFactory = new TaggedWordFactory();
        List<TaggedWord> taggedList = new ArrayList<TaggedWord>();
        String[] taggedWordsString = taggedContents.split("\\s+"); // splits by whitespace
        for (String word : taggedWordsString) {
            TaggedWord tw = taggedWordFactory.createTaggedWord(word);
            taggedList.add(tw);
        }
        return taggedList;
    }

    public static String translate(String englishWord) {
        String word = englishWord;
        if (isExceptionWord(word))
            return convertExceptionWord(word);
        word = replaceDoubleLetter(word);
        word = shiftVowels(word);
        word = endsInW(word);
        word = replaceTION(word);
        word = replaceCA(word);
        return word;
    }

    public static boolean isExceptionWord(String englishWord) {
        String iregWords[] = {"me", "she", "he", "we", "be", "do",
                "to", "a", "an", "the", "what", "and"};
        for (String s : iregWords) {
            if (s.equals(englishWord))
                return true;
        }
        return false;
    }

    public static String convertExceptionWord(String englishWord) {
        String w = "";
        switch (englishWord) {
            case "me":
                w = "ma";
                break;
            case "she":
                w = "sha";
                break;
            case "he":
                w = "ha";
                break;
            case "we":
                w = "wa";
                break;
            case "be":
                w = "ba";
                break;
            case "do":
                w = "da";
                break;
            case "to":
                w = "ta";
                break;
            case "a":
                w = "ikaku";
                break;
            case "an":
                w = "ikaku";
                break;
            case "the":
                w = "osi";
                break;
            case "what":
                w = "ako";
                break;
            case "and":
                w = "upa";
                break;
        }
        return w;
    }

    public static String replaceDoubleLetter(String word) {
        if (word.contains("oo"))
            word = word.replaceAll("oo", "uu");
        else if (word.contains("ee"))
            word = word.replaceAll("ee", "ii");
        return word;
    }

    public static String shiftVowels(String word) {
        String newWord = "";
        char letter;
        for (char c : word.toCharArray()) {
            switch (c) {
                case 'a':
                    letter = 'e';
                    break;
                case 'e':
                    letter = 'i';
                    break;
                case 'i':
                    letter = 'o';
                    break;
                case 'o':
                    letter = 'u';
                    break;
                case 'u':
                    letter = 'a';
                    break;
                case 'y':
                    letter = 'S';
                    break;
                default:
                    letter = c;
                    break;
            }
            newWord += letter;
        }
        return newWord;
    }

    public static String endsInW(String word) {
        int pos = word.length() - 1;
        if (word.charAt(pos) == 'w')
            word = word.substring(0, pos) + 'v';
        return word;
    }

    public static String replaceTION(String word) {
        if (word.contains("toun"))
            word = word.replaceAll("toun", "shun");
        return word;
    }

    public static String replaceCA(String word) {
        if (word.contains("ce"))
            word = word.replaceAll("ce", "ke");
        return word;
    }
}
