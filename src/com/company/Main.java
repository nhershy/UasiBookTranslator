package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.File;

public class Main {

    final static String infile_path = "C:\\Users\\TEST\\Desktop\\input.txt";
    final static String outfile_path = "C:\\Users\\TEST\\Desktop\\output.txt";
    final static String tagger_path = "tagger/english-left3words-distsim.tagger";

    public static void main(String[] args) {

        File infile = new File(infile_path);
        File outfile = new File(outfile_path);
        MaxentTagger tagger = new MaxentTagger(tagger_path);
        List<TaggedWord> taggedWords;

        try {
            //read in entire text file to String
            String fileContents = fileContentsToString(infile);

            // strips off all non-ASCII characters from input
            fileContents = fileContents.replaceAll("[^\\x00-\\x7F]", "");

            //tag each word with its respective POS
            String fileContentsTagged = tagger.tagString(fileContents);

            //create list of taggedWords from tagged fileContent String
            taggedWords = processTaggedWords(fileContentsTagged);

            //clear contents of outfile from previous run
            PrintWriter pw = new PrintWriter(outfile);
            pw.close();

            //remove "will" and add "P" to the next word
            List<TaggedWord> taggedWordsWithFuture = accountForFutureTense(taggedWords);

            //convert processed english words to String of uasi words
            String uasiContent = translateToUasi(taggedWordsWithFuture);

            //print uasi result
            printStringToOutfile(uasiContent, outfile);
        }
        catch (FileNotFoundException e1) {
            System.out.println("File not found.");
        }
        catch (IOException e) {
            System.out.print("Error writing to file.");
        }
    }  //end main

    public static List<TaggedWord> accountForFutureTense(List<TaggedWord> taggedWords) {
        List<TaggedWord> taggedWordsWithFuture = new ArrayList<>();
        for (int i = 0; i < taggedWords.size(); i++) {
            if (taggedWords.get(i).getEng_word().equals("will")) {
                taggedWords.get(i+1).makeFutureTense();
            }
            else {
                taggedWordsWithFuture.add(taggedWords.get(i));
            }
        }
        return taggedWordsWithFuture;
    }

    public static String fileContentsToString(File infile) throws FileNotFoundException, IOException{
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
            String englishWord = tw.getEng_word();
            String uasiWord = translate(englishWord);
            if (!tw.isPunctuation()) {
                uasiContent += uasiWord + " ";
            }
            else {
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
        List<TaggedWord> wordList = new ArrayList<TaggedWord>();
        String[] taggedWords = taggedContents.split("\\s+"); // splits by whitespace
        for (String taggedWord : taggedWords) {
            wordList.add(new TaggedWord(taggedWord));
        }
        return wordList;
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
        switch(englishWord){
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
        for(char c : word.toCharArray()) {
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
            word = word.substring(0,pos)+'v';
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
