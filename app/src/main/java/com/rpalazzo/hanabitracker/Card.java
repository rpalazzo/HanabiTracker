package com.rpalazzo.hanabitracker;

import android.graphics.drawable.Drawable;

import com.rpalazzo.hanabitracker.R;

/**
 * Created by rpalazzo on 5/19/16.
 */
public class Card {

    private int rank;  // 0 = unknown, 1-5
    private Color suit;



    private Boolean isSelected;

    public Card() {
        rank = 0; //unknown
        suit = Color.UNKNOWN;
        isSelected = Boolean.FALSE;
    }


    public enum Color {
        UNKNOWN, RED, YELLOW, BLUE, WHITE, GREEN, MULTICOLOR
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Color getSuit() {
        return suit;
    }

    public void setSuit(Color suit) {
        this.suit = suit;
    }


    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getImageReference() {

        int retval = 0;

        switch (this.rank) {
            case 0:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.uus;}
                        else {retval = R.drawable.uu;}
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.rus;}
                        else {retval = R.drawable.ru;}
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.yus;}
                        else {retval = R.drawable.yu;}
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.bus;}
                        else {retval = R.drawable.bu;}
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.wus;}
                        else {retval = R.drawable.wu;}
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.gus;}
                        else {retval = R.drawable.gu;}
                        break;
                }
                break;
            case 1:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.u1s;}
                        else {retval = R.drawable.u1;}
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.r1s;}
                        else {retval = R.drawable.r1;}
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.y1s;}
                        else {retval = R.drawable.y1;}
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.b1s;}
                        else {retval = R.drawable.b1;}
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.w1s;}
                        else {retval = R.drawable.w1;}
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.g1s;}
                        else {retval = R.drawable.g1;}
                        break;
                }
                break;
            case 2:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.u2s;}
                        else {retval = R.drawable.u2;}
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.r2s;}
                        else {retval = R.drawable.r2;}
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.y2s;}
                        else {retval = R.drawable.y2;}
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.b2s;}
                        else {retval = R.drawable.b2;}
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.w2s;}
                        else {retval = R.drawable.w2;}
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.g2s;}
                        else {retval = R.drawable.g2;}
                        break;
                }
                break;
            case 3:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.u3s;}
                        else {retval = R.drawable.u3;}
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.r3s;}
                        else {retval = R.drawable.r3;}
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.y3s;}
                        else {retval = R.drawable.y3;}
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.b3s;}
                        else {retval = R.drawable.b3;}
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.w3s;}
                        else {retval = R.drawable.w3;}
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.g3s;}
                        else {retval = R.drawable.g3;}
                        break;
                }
                break;
            case 4:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.u4s;}
                        else {retval = R.drawable.u4;}
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.r4s;}
                        else {retval = R.drawable.r4;}
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.y4s;}
                        else {retval = R.drawable.y4;}
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.b4s;}
                        else {retval = R.drawable.b4;}
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.w4s;}
                        else {retval = R.drawable.w4;}
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.g4s;}
                        else {retval = R.drawable.g4;}
                        break;
                }
                break;
            case 5:
                switch (this.suit){
                    case UNKNOWN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.u5s;}
                        else {retval = R.drawable.u5;}
                        break;
                    case RED:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.r5s;}
                        else {retval = R.drawable.r5;}
                        break;
                    case YELLOW:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.y5s;}
                        else {retval = R.drawable.y5;}
                        break;
                    case BLUE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.b5s;}
                        else {retval = R.drawable.b5;}
                        break;
                    case WHITE:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.w5s;}
                        else {retval = R.drawable.w5;}
                        break;
                    case GREEN:
                        if (this.isSelected == Boolean.TRUE) {retval = R.drawable.g5s;}
                        else {retval = R.drawable.g5;}
                        break;
                }
                break;
        }
        return retval;
    }



}
