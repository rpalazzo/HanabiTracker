/* Copyright 2016 Robert Palazzo <rpalazzo@gmail.com>
 * Proprietary and confidential
 * Copying of this file without express written consent is prohibited.
 */

package com.rpalazzo.hanabitracker;

import android.graphics.drawable.Drawable;
import android.support.annotation.BoolRes;

import com.rpalazzo.hanabitracker.R;


public class Card implements java.io.Serializable {

    private int rank;  // 0 = unknown, 1-5
    private Color suit;
    private Boolean isSelected;

    // Negative information:  True => known negative;  False => UNKNOWN (not known positive)
    private Boolean isNotOne;
    private Boolean isNotTwo;
    private Boolean isNotThree;
    private Boolean isNotFour;
    private Boolean isNotFive;
    private Boolean isNotRed;
    private Boolean isNotYellow;
    private Boolean isNotBlue;
    private Boolean isNotWhite;
    private Boolean isNotGreen;
    private Boolean isNotMulticolor;
    private String NegativeInfo;
    private Rainbow rainbowState;
    private Boolean dirtyFlag; //used to track rainbows in multi-card clues

    //serialVersionUID is used to verify that the sender and receiver of a serialized object are compatible
    //see https://developer.android.com/reference/java/io/Serializable.html
    private static final long serialVersionUID = 1L;

    public enum Color {
        UNKNOWN, RED, YELLOW, BLUE, WHITE, GREEN, MULTICOLOR
    }

    public enum Rainbow {
        IS_NOT_RAINBOW, POSSIBLE_RAINBOW, IS_RAINBOW, PENDING_NOT_RAINBOW, NA_RAINBOW
    }


    public Card() {
        rank = 0; //unknown
        suit = Color.UNKNOWN;
        isSelected = Boolean.FALSE;

        isNotOne = Boolean.FALSE;
        isNotTwo = Boolean.FALSE;
        isNotThree = Boolean.FALSE;
        isNotFour = Boolean.FALSE;
        isNotFive = Boolean.FALSE;
        isNotRed = Boolean.FALSE;
        isNotYellow = Boolean.FALSE;
        isNotBlue = Boolean.FALSE;
        isNotWhite = Boolean.FALSE;
        isNotGreen = Boolean.FALSE;
        isNotMulticolor = Boolean.FALSE;
        NegativeInfo = "";
        rainbowState = Rainbow.POSSIBLE_RAINBOW;
        dirtyFlag = Boolean.FALSE;
    }

    public Card(int multicolorMode) {
        rank = 0; //unknown
        suit = Color.UNKNOWN;
        isSelected = Boolean.FALSE;

        isNotOne = Boolean.FALSE;
        isNotTwo = Boolean.FALSE;
        isNotThree = Boolean.FALSE;
        isNotFour = Boolean.FALSE;
        isNotFive = Boolean.FALSE;
        isNotRed = Boolean.FALSE;
        isNotYellow = Boolean.FALSE;
        isNotBlue = Boolean.FALSE;
        isNotWhite = Boolean.FALSE;
        isNotGreen = Boolean.FALSE;
        isNotMulticolor = Boolean.FALSE;
        NegativeInfo = "";
        if (multicolorMode == 2) {
            rainbowState = Rainbow.POSSIBLE_RAINBOW;
        }
        else {
            rainbowState = Rainbow.NA_RAINBOW;
        }
        dirtyFlag = Boolean.FALSE;
    }

    public Card(Card c) {
        this.rank = c.rank;
        this.suit = c.suit;
        this.isSelected = Boolean.FALSE;
        this.isNotOne = c.isNotOne;
        this.isNotTwo = c.isNotTwo;
        this.isNotThree = c.isNotThree;
        this.isNotFour = c.isNotFour;
        this.isNotFive = c.isNotFive;
        this.isNotRed = c.isNotRed;
        this.isNotYellow = c.isNotYellow;
        this.isNotBlue = c.isNotBlue;
        this.isNotWhite = c.isNotWhite;
        this.isNotGreen = c.isNotGreen;
        this.isNotMulticolor = c.isNotMulticolor;
        this.NegativeInfo = c.NegativeInfo;
        this.rainbowState = c.rainbowState;
        this.dirtyFlag = c.dirtyFlag;
    }


    public int getImageReference() {

        int retval = 0;

        switch (this.rank) {
            case 0:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.umus;
                            } else {
                                retval = R.drawable.uus;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.umu;
                            }
                            else{
                                {retval = R.drawable.uu;}
                            }
                        }
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rmus;
                            } else {
                                retval = R.drawable.rus;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rmu;
                            }
                            else{
                                {retval = R.drawable.ru;}
                            }
                        }
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ymus;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.yus;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ymu;
                            }
                            else{
                                {retval = R.drawable.yu;}
                            }
                        }
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bmus;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.bus;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bmu;
                            }
                            else{
                                {retval = R.drawable.bu;}
                            }
                        }
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wmus;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.wus;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wmu;
                            }
                            else{
                                {retval = R.drawable.wu;}
                            }
                        }
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gmus;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.gus;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gmu;
                            }
                            else{
                                {retval = R.drawable.gu;}
                            }
                        }
                        break;
                    case MULTICOLOR:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.mus;}
                        else {retval = R.drawable.mu;}
                        break;
                }
                break;
            case 1:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um1s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.u1s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um1;
                            }
                            else{
                                {retval = R.drawable.u1;}
                            }
                        }
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm1s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.r1s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm1;
                            }
                            else{
                                {retval = R.drawable.r1;}
                            }
                        }
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym1s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.y1s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym1;
                            }
                            else{
                                {retval = R.drawable.y1;}
                            }
                        }
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm1s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.b1s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm1;
                            }
                            else{
                                {retval = R.drawable.b1;}
                            }
                        }
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm1s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.w1s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm1;
                            }
                            else{
                                {retval = R.drawable.w1;}
                            }
                        }
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm1s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.g1s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm1;
                            }
                            else{
                                {retval = R.drawable.g1;}
                            }
                        }
                        break;
                    case MULTICOLOR:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.m1s;}
                        else {retval = R.drawable.m1;}
                        break;
                }
                break;
            case 2:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um2s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.u2s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um2;
                            }
                            else{
                                {retval = R.drawable.u2;}
                            }
                        }
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm2s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.r2s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm2;
                            }
                            else{
                                {retval = R.drawable.r2;}
                            }
                        }
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym2s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.y2s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym2;
                            }
                            else{
                                {retval = R.drawable.y2;}
                            }
                        }
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm2s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.b2s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm2;
                            }
                            else{
                                {retval = R.drawable.b2;}
                            }
                        }
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm2s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.w2s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm2;
                            }
                            else{
                                {retval = R.drawable.w2;}
                            }
                        }
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm2s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.g2s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm2;
                            }
                            else{
                                {retval = R.drawable.g2;}
                            }
                        }
                        break;
                    case MULTICOLOR:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.m2s;}
                        else {retval = R.drawable.m2;}
                        break;
                }
                break;
            case 3:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um3s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.u3s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um3;
                            }
                            else{
                                {retval = R.drawable.u3;}
                            }
                        }
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm3s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.r3s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm3;
                            }
                            else{
                                {retval = R.drawable.r3;}
                            }
                        }
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym3s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.y3s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym3;
                            }
                            else{
                                {retval = R.drawable.y3;}
                            }
                        }
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm3s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.b3s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm3;
                            }
                            else{
                                {retval = R.drawable.b3;}
                            }
                        }
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm3s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.w3s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm3;
                            }
                            else{
                                {retval = R.drawable.w3;}
                            }
                        }
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm3s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.g3s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm3;
                            }
                            else{
                                {retval = R.drawable.g3;}
                            }
                        }
                        break;
                    case MULTICOLOR:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.m3s;}
                        else {retval = R.drawable.m3;}
                        break;
                }
                break;
            case 4:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um4s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.u4s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um4;
                            }
                            else{
                                {retval = R.drawable.u4;}
                            }
                        }
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm4s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.r4s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm4;
                            }
                            else{
                                {retval = R.drawable.r4;}
                            }
                        }
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym4s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.y4s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym4;
                            }
                            else{
                                {retval = R.drawable.y4;}
                            }
                        }
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm4s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.b4s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm4;
                            }
                            else{
                                {retval = R.drawable.b4;}
                            }
                        }
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm4s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.w4s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm4;
                            }
                            else{
                                {retval = R.drawable.w4;}
                            }
                        }
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm4s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.g4s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm4;
                            }
                            else{
                                {retval = R.drawable.g4;}
                            }
                        }
                        break;
                    case MULTICOLOR:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.m4s;}
                        else {retval = R.drawable.m4;}
                        break;
                }
                break;
            case 5:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um5s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.u5s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.um5;
                            }
                            else{
                                {retval = R.drawable.u5;}
                            }
                        }
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm5s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.r5s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.rm5;
                            }
                            else{
                                {retval = R.drawable.r5;}
                            }
                        }
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym5s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.y5s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.ym5;
                            }
                            else{
                                {retval = R.drawable.y5;}
                            }
                        }
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm5s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.b5s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.bm5;
                            }
                            else{
                                {retval = R.drawable.b5;}
                            }
                        }
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm5s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.w5s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.wm5;
                            }
                            else{
                                {retval = R.drawable.w5;}
                            }
                        }
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm5s;
                            } else { // not PossibleMulticolor
                                retval = R.drawable.g5s;
                            }
                        }
                        else { // not selected
                            if (this.rainbowState == Rainbow.POSSIBLE_RAINBOW) {
                                retval = R.drawable.gm5;
                            }
                            else{
                                {retval = R.drawable.g5;}
                            }
                        }
                        break;
                    case MULTICOLOR:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.m5s;}
                        else {retval = R.drawable.m5;}
                        break;
                }
                break;
        }
        return retval;
    }

    public String  getNegativeInfo() {
        NegativeInfo = "";

        if (rank == 0) {
            if (isNotOne)   { NegativeInfo += "1"; }
            if (isNotTwo)   { NegativeInfo += "2"; }
            if (isNotThree) { NegativeInfo += "3"; }
            if (isNotFour)  { NegativeInfo += "4"; }
            if (isNotFive)  { NegativeInfo += "5"; }
        }
        if (suit == Color.UNKNOWN) {
            if (isNotRed)        { NegativeInfo += "R"; }
            if (isNotYellow)     { NegativeInfo += "Y"; }
            if (isNotBlue)       { NegativeInfo += "B"; }
            if (isNotWhite)      { NegativeInfo += "W"; }
            if (isNotGreen)      { NegativeInfo += "G"; }
            if (isNotMulticolor) { NegativeInfo += "M"; }
        }
        if (NegativeInfo.length() != 0) {
            NegativeInfo = "NOT: " + NegativeInfo;
        }

        return NegativeInfo;
    }

    public int getRank() { return rank; }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Color getSuit() {
        return suit;
    }

    public void setSuit(Color suit) {
        this.suit = suit;
        // explict clue overrides any implicit negative info
        setNotColor(suit, Boolean.FALSE);
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }


    public Boolean getNotOne() {
        return isNotOne;
    }

    public void setNotOne(Boolean notOne) {
        isNotOne = notOne;
    }

    public Boolean getNotTwo() {
        return isNotTwo;
    }

    public void setNotTwo(Boolean notTwo) {
        isNotTwo = notTwo;
    }

    public Boolean getNotThree() {
        return isNotThree;
    }

    public void setNotThree(Boolean notThree) {
        isNotThree = notThree;
    }

    public Boolean getNotFour() {
        return isNotFour;
    }

    public void setNotFour(Boolean notFour) {
        isNotFour = notFour;
    }

    public Boolean getNotFive() {
        return isNotFive;
    }

    public void setNotFive(Boolean notFive) {
        isNotFive = notFive;
    }

    public Boolean getNotRed() {
        return isNotRed;
    }

    public void setNotRed(Boolean notRed) {
        isNotRed = notRed;
    }

    public void setNotNumber(int n, Boolean bool) {
        switch (n) {
            case 1:
                isNotOne = bool;
                break;
            case 2:
                isNotTwo = bool;
                break;
            case 3:
                isNotThree = bool;
                break;
            case 4:
                isNotFour = bool;
                break;
            case 5:
                isNotFive = bool;
                break;
        }
    }

    public void setNotColor(Color color, Boolean bool) {
        switch (color) {
            case RED:
                isNotRed = bool;
                break;
            case YELLOW:
                isNotYellow = bool;
                break;
            case BLUE:
                isNotBlue = bool;
                break;
            case WHITE:
                isNotWhite = bool;
                break;
            case GREEN:
                isNotGreen = bool;
                break;
            case MULTICOLOR:
                isNotMulticolor = bool;
                break;
        }
    }

    public Boolean getNotColor(Color color) {
        Boolean bool = Boolean.FALSE;
        switch (color) {
            case RED:
                bool = isNotRed;
                break;
            case YELLOW:
                bool = isNotYellow;
                break;
            case BLUE:
                bool = isNotBlue;
                break;
            case WHITE:
                bool = isNotWhite;
                break;
            case GREEN:
                bool = isNotGreen;
                break;
            case MULTICOLOR:
                bool = isNotMulticolor;
                break;
        }
        return bool;
    }

    public Boolean getNotYellow() {
        return isNotYellow;
    }

    public void setNotYellow(Boolean notYellow) {
        isNotYellow = notYellow;
    }

    public Boolean getNotBlue() {
        return isNotBlue;
    }

    public void setNotBlue(Boolean notBlue) {
        isNotBlue = notBlue;
    }

    public Boolean getNotWhite() {
        return isNotWhite;
    }

    public void setNotWhite(Boolean notWhite) {
        isNotWhite = notWhite;
    }

    public Boolean getNotGreen() {
        return isNotGreen;
    }

    public void setNotGreen(Boolean notGreen) {
        isNotGreen = notGreen;
    }

    public Boolean getNotMulticolor() {
        return isNotMulticolor;
    }

    public void setNotMulticolor(Boolean notMulticolor) {
        isNotMulticolor = notMulticolor;
    }

    public Rainbow getRainbowState() { return rainbowState; }

    public void setRainbowState(Rainbow rainbowState) { this.rainbowState = rainbowState; }

    public Boolean getDirtyFlag() { return dirtyFlag; }

    public void setDirtyFlag(Boolean dirtyFlag) { this.dirtyFlag = dirtyFlag; }


}
