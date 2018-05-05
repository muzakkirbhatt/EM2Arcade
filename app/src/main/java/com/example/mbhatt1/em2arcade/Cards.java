package com.example.mbhatt1.em2arcade;

public class Cards {
    private int value;
    private int aceValue;
    private String suit;
    private String face;
    private String fileName;

    public int getAceValue() {
        return aceValue;
    }

    public void setAceValue(int aceValue) {
        this.aceValue = aceValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
