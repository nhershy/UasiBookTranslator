package com.company;

/*
NN	- Noun, singular or mass
NNS	- Noun, plural
NNP	- Proper noun, singular
NNPS - Proper noun, plural

VB - Verb, base form                        //no change
VBD - Verb, past tense                      //check irregs
VBG - Verb, gerund or present participle    //no change
VBN - Verb, past participle                 //no change
VBP - Verb, non-3rd person singular present //no change
VBZ - Verb, 3rd person singular present     //check irregs
*/

public class TaggedWord {
    private String eng_word;
    private boolean isPunctuation;
    private boolean none;
    private boolean noun;
    private boolean singular;
    private boolean verb;
    private boolean verbPast;
    private boolean verb3rd;
    private boolean verbNoChange;

    public TaggedWord(String taggedWord) {
        this.eng_word = taggedWord.toLowerCase();
        if (taggedWord.contains("_")) {
            this.eng_word = taggedWord.substring(0, taggedWord.indexOf("_")).toLowerCase();
            String partOfSpeech = taggedWord.substring(taggedWord.indexOf("_") + 1);
            POS_Checker(partOfSpeech);
            checkIfPunctuation();
            if (!isPunctuation)
                removeIrregularities();
        }
        else
            this.none = true;
    }

    private void checkIfPunctuation() {
        String[] punctuation = {"." , "," , ";" , ":", "?" , "!" , "\"" , "\'" , ")" , "("};
        for (String punct : punctuation) {
            if (eng_word.equals(punct)) {
                isPunctuation = true;
                break;
            }
        }
    }

    private void removeIrregularities() {
        if (none)
            return;

        if (verb) {
            if (verbPast) {
                if (eng_word.endsWith("ed")) {
                    //remove "ed" from string
                    eng_word = eng_word.substring(0, eng_word.length() - 2);
                }
                //add Uasi "L"
                eng_word = eng_word + "L";
            }
            //http://www.grammar.cl/Present/Verbs_Third_Person.htm
            if (verb3rd) {
                if (eng_word.endsWith("es")) {
                    //remove "es" from the word
                    String baseWord = eng_word.substring(0, eng_word.length() - 2);
                    if (baseWord.endsWith("i")) {
                        //remove the "i" from the end of the word
                        baseWord = baseWord.substring(0, baseWord.length() - 1);
                        eng_word = baseWord + "y";
                    }
                    else if (baseWord.endsWith("ss") || baseWord.endsWith("x") ||
                             baseWord.endsWith("ch") || baseWord.endsWith("sh") ||
                             baseWord.endsWith("o")){
                        eng_word = baseWord;
                    }
                    //re-add the last "e". Eg "bak" becomes "bake"
                    else {
                        eng_word = baseWord + "e";
                    }
                }
            }
        }
        //https://www.englisch-hilfen.de/en/grammar/plural.htm
        else if (noun) {
            if (!singular) {
                if (eng_word.endsWith("ies")) {
                    //remove "ies"
                    String baseWord = eng_word.substring(0, eng_word.length() - 3);
                    //add "y",
                    eng_word = baseWord + "yT";
                }
                else if (eng_word.endsWith("ys")) {
                    //e.g. "boys"
                    //remove the "s"
                    String baseWord = eng_word.substring(0, eng_word.length() - 1);
                    eng_word = baseWord + "T";
                }
                else if (eng_word.endsWith("es")) {
                    String baseWord = eng_word.substring(0, eng_word.length() - 2);
                    if (baseWord.endsWith("o")) {
                        eng_word = baseWord + "T";
                    }
                    else if (baseWord.endsWith("x")) {
                        eng_word = baseWord + "T";
                    }
                    else if (baseWord.endsWith("ss")) {
                        //e.g. "kisses"
                        eng_word = baseWord + "T";
                    }
                    else if (baseWord.endsWith("s")) {
                        eng_word = baseWord + "eT";
                    }
                    else if (baseWord.endsWith("g")) {
                        eng_word = baseWord + "eT";
                    }
                    else if (baseWord.endsWith("k")) {
                        eng_word = baseWord + "eT";
                    }
                }
                else if (eng_word.endsWith("ves")) {
                    //e.g. "thief"
                    String baseWord = eng_word.substring(0, eng_word.length() - 3);
                    eng_word = baseWord + "fT";
                }
                //if it is a normal noun
                else {
                    String baseWord = eng_word.substring(0, eng_word.length() - 1);
                    eng_word = baseWord + "T";
                }
            }
        }
    }

    private void POS_Checker(String pos) {
        if (pos.contains("NN")) {
            this.noun = true;
            switch (pos) {
                case "NN":
                    this.singular = true;
                    break;
                case "NNS":
                    this.singular = false;
                    break;
                case "NNP":
                    this.singular = true;
                    break;
                case "NNPS":
                    this.singular = false;
                    break;
            }
        }
        else if (pos.contains("VB")) {
            this.verb = true;
            switch (pos) {
                case "VB":
                    this.verbNoChange = true;
                    break;
                case "VBD":
                    this.verbPast = true;
                    break;
                case "VBG":
                    this.verbNoChange = true;
                    break;
                case "VBN":
                    this.verbNoChange = true;
                    break;
                case "VBP":
                    this.verbNoChange = true;
                    break;
                case "VBZ":
                    this.verb3rd = true;
                    break;
            }
        }
        else {
            this.none = true;
        }
    }

    private String remove_x_num_of_last_chars(String s, int n) {
        return s.substring(0, s.length() - n);
    }

    public String getEng_word() {
        return eng_word;
    }

    public boolean isPunctuation() {
        return isPunctuation;
    }

    public void makeFutureTense() {
        eng_word += "P";
    }
}

