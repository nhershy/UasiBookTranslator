package com.company.taggedword;

public class TaggedWord {
    private String englishWord = "";

    //POS booleans
    private boolean isPunctuation;
    //nouns
    private boolean noun;
    private boolean singular;
    private boolean proper;
    //verbs
    private boolean verb;
    private boolean pastTense;
    private boolean thirdPersonSingular;
    private boolean pastParticiple;

    public TaggedWord(String englishWord, PosObj posobj) {
        this.englishWord = englishWord;
        this.isPunctuation = posobj.isPunctuation();
        this.noun = posobj.isNoun();
        this.singular = posobj.isSingular();
        this.proper = posobj.isProper();
        this.verb = posobj.isVerb();
        this.pastTense = posobj.isPastTense();
        this.thirdPersonSingular = posobj.isThirdPersonSingular();
        this.pastParticiple = posobj.isPastParticiple();
    }

    public void makeFutureTense() {
        englishWord += "P";
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public boolean isPunctuation() {
        return isPunctuation;
    }

    public boolean isNoun() {
        return noun;
    }

    public boolean isSingular() {
        return singular;
    }

    public boolean isProper() {
        return proper;
    }

    public boolean isVerb() {
        return verb;
    }

    public boolean isPastTense() {
        return pastTense;
    }

    public boolean isThirdPersonSingular() {
        return thirdPersonSingular;
    }

    public boolean isPastParticiple() {
        return pastParticiple;
    }
}

