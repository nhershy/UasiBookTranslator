package com.company.taggedword;

//Part-of-Speech boolean object

public class PosObj {
    //POS booleans
    private boolean isPunctuation = false;
    //nouns
    private boolean noun = false;
    private boolean singular = false;
    private boolean proper = false;
    //verbs
    private boolean verb = false;
    private boolean pastTense = false;
    private boolean thirdPersonSingular = false;
    private boolean pastParticiple = false;

    public PosObj() { }

    public boolean isPunctuation() {
        return isPunctuation;
    }

    public void setPunctuation(boolean punctuation) {
        isPunctuation = punctuation;
    }

    public boolean isNoun() {
        return noun;
    }

    public void setNoun(boolean noun) {
        this.noun = noun;
    }

    public boolean isSingular() {
        return singular;
    }

    public void setSingular(boolean singular) {
        this.singular = singular;
    }

    public boolean isProper() {
        return proper;
    }

    public void setProper(boolean proper) {
        this.proper = proper;
    }

    public boolean isVerb() {
        return verb;
    }

    public void setVerb(boolean verb) {
        this.verb = verb;
    }

    public boolean isPastTense() {
        return pastTense;
    }

    public void setPastTense(boolean pastTense) {
        this.pastTense = pastTense;
    }

    public boolean isThirdPersonSingular() {
        return thirdPersonSingular;
    }

    public void setThirdPersonSingular(boolean thirdPersonSingular) {
        this.thirdPersonSingular = thirdPersonSingular;
    }

    public boolean isPastParticiple() {
        return pastParticiple;
    }

    public void setPastParticiple(boolean pastParticiple) {
        this.pastParticiple = pastParticiple;
    }
}
