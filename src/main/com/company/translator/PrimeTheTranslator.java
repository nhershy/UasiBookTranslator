package com.company.translator;

//TODO: this file is experimental (i.e. trial and error)

import com.company.taggedword.TaggedWord;

public class PrimeTheTranslator {
    private PrimeTheTranslator() {}

    public static void prime(TaggedWord taggedWord) {
        if (taggedWord.isPunctuation())
            return;

        if (taggedWord.isVerb()) {
            if (taggedWord.isPastTense()) {
                if (taggedWord.getEnglishWord().endsWith("ed")) {
                    //remove "ed" from string
                    taggedWord.setEnglishWord(removeLastChars(taggedWord.getEnglishWord(), 2));
                }
                //add Uasi "L"
                taggedWord.setEnglishWord(taggedWord.getEnglishWord() + "L");
            }
            //http://www.grammar.cl/Present/Verbs_Third_Person.htm
            if (taggedWord.isThirdPersonSingular()) {
                if (taggedWord.getEnglishWord().endsWith("es")) {
                    //remove "es" from the word
                    String baseWord = removeLastChars(taggedWord.getEnglishWord(), 2);
                    if (baseWord.endsWith("i")) {
                        //remove the "i" from the end of the word
                        baseWord = removeLastChars(baseWord, 1);
                        taggedWord.setEnglishWord(baseWord + "y");
                    }
                    else if (baseWord.endsWith("ss") || baseWord.endsWith("x") ||
                            baseWord.endsWith("ch") || baseWord.endsWith("sh") ||
                            baseWord.endsWith("o")){
                        taggedWord.setEnglishWord(baseWord);
                    }
                    //re-add the last "e". Eg "bak" becomes "bake"
                    else {
                        taggedWord.setEnglishWord(baseWord + "e");
                    }
                }
            }
        }
        //https://www.englisch-hilfen.de/en/grammar/plural.htm
        else if (taggedWord.isNoun()) {
            if (!taggedWord.isSingular()) {
                if (taggedWord.getEnglishWord().endsWith("ies")) {
                    //remove "ies"
                    String baseWord = removeLastChars(taggedWord.getEnglishWord(), 3);
                    //add "y",
                    taggedWord.setEnglishWord(baseWord + "yT");
                }
                else if (taggedWord.getEnglishWord().endsWith("ys")) {
                    //e.g. "boys"
                    //remove the "s"
                    String baseWord = removeLastChars(taggedWord.getEnglishWord(), 1);
                    taggedWord.setEnglishWord(baseWord + "T");
                }
                else if (taggedWord.getEnglishWord().endsWith("es")) {
                    String baseWord = removeLastChars(taggedWord.getEnglishWord(), 2);
                    if (baseWord.endsWith("o")) {
                        taggedWord.setEnglishWord(baseWord + "T");
                    }
                    else if (baseWord.endsWith("x")) {
                        taggedWord.setEnglishWord(baseWord + "T");
                    }
                    else if (baseWord.endsWith("ss")) {
                        //e.g. "kisses"
                        taggedWord.setEnglishWord(baseWord + "T");
                    }
                    else if (baseWord.endsWith("s")) {
                        taggedWord.setEnglishWord(baseWord + "eT");
                    }
                    else if (baseWord.endsWith("g")) {
                        taggedWord.setEnglishWord(baseWord + "eT");
                    }
                    else if (baseWord.endsWith("k")) {
                        taggedWord.setEnglishWord(baseWord + "eT");
                    }
                }
                else if (taggedWord.getEnglishWord().endsWith("ves")) {
                    //e.g. "thief"
                    String baseWord = removeLastChars(taggedWord.getEnglishWord(), 3);
                    taggedWord.setEnglishWord(baseWord + "fT");
                }
                //if it is a normal noun
                else {
                    String baseWord = removeLastChars(taggedWord.getEnglishWord(), 1);
                    taggedWord.setEnglishWord(baseWord + "T");
                }
            }
        }
    }

    private static String removeLastChars(String s, int n) {
        return s.substring(0, s.length() - n);
    }
}
