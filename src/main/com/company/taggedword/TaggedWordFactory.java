package com.company.taggedword;

/*
Penn Treebank System:

NN	- Noun, singular or mass
NNS	- Noun, plural
NNP	- Proper noun, singular
NNPS - Proper noun, plural

VB - Verb, base form
VBD - Verb, past tense
VBG - Verb, gerund or present participle
VBN - Verb, past participle
VBP - Verb, non-3rd person singular present
VBZ - Verb, 3rd person singular present
*/

public class TaggedWordFactory {
    public TaggedWordFactory() { }

    public TaggedWord createTaggedWord(String taggedWordString) {
        String englishWord = "";
        String partOfSpeech = "";

        PosObj posObj = new PosObj();

        //if taggedWordString for some reason does not contain
        //a tag. E.g. comes back as "run" and not "run_VB"
        englishWord = taggedWordString.toLowerCase();

        //if taggedWordString contains a tag, as it should
        if (taggedWordString.contains("_")) {
            englishWord = extractBaseWord(taggedWordString);
            partOfSpeech = extractPOS(taggedWordString);
            posObj = POS_toBoolean(englishWord, partOfSpeech);
        }
        return new TaggedWord(englishWord, posObj);
    }

    private boolean checkIfPunctuation(String englishWord) {
        String[] punctuation = {"." , "," , ";" , ":", "?" , "!" ,
                                "\"" , "\'" , ")" , "(", "\''", "``"};
        for (String punct : punctuation) {
            if (englishWord.equals(punct)) {
                return true;
            }
        }
        return false;
    }

    private String extractBaseWord(String word) {
        return word.substring(0, word.indexOf("_")).toLowerCase();
    }

    private String extractPOS(String word) {
        return word.substring(word.indexOf("_") + 1);
    }

    private PosObj POS_toBoolean(String englishWord, String partOfSpeech) {
        //create temp PosObj
        PosObj posobj = new PosObj();

        //check if it is a punctuation mark and not a word
        posobj.setPunctuation(checkIfPunctuation(englishWord));

        //set parts of speech
        if (partOfSpeech.contains("NN")) {
            posobj.setNoun(true);
            switch (partOfSpeech) {
                case "NN":
                    posobj.setSingular(true);
                    break;
                case "NNP":
                    posobj.setProper(true);
                    posobj.setSingular(true);
                    break;
                case "NNPS":
                    posobj.setProper(true);
                    break;
            }
        }
        else if (partOfSpeech.contains("VB")) {
            posobj.setVerb(true);
            switch (partOfSpeech) {
                case "VBD":
                    posobj.setPastTense(true);
                    break;
                case "VBZ":
                    posobj.setThirdPersonSingular(true);
                    break;
                case "VBN":
                    posobj.setPastParticiple(true);
                    break;
            }
        }
        return posobj;
    }
}
