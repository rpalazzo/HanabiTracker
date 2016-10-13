package com.rpalazzo.hanabitracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;


public class AnnotationActivity extends AppCompatActivity {

    private Card annotationCard = null;
    private MultiStateToggleButton button1 = null;
    private MultiStateToggleButton button2 = null;
    private MultiStateToggleButton button3 = null;
    private MultiStateToggleButton button4 = null;
    private MultiStateToggleButton button5 = null;
    private MultiStateToggleButton buttonRed = null;
    private MultiStateToggleButton buttonYellow = null;
    private MultiStateToggleButton buttonBlue = null;
    private MultiStateToggleButton buttonWhite = null;
    private MultiStateToggleButton buttonGreen = null;
    private MultiStateToggleButton buttonMulti = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        annotationCard =  (Card) getIntent().getSerializableExtra("card");

        button1 = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_1);
        button2 = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_2);
        button3 = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_3);
        button4 = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_4);
        button5 = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_5);
        buttonRed = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_red);
        buttonYellow = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_yellow);
        buttonBlue = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_blue);
        buttonWhite = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_white);
        buttonGreen = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_green);
        buttonMulti = (MultiStateToggleButton) this.findViewById(R.id.mstb_ann_multicolor);


        // Initialize all buttons to "Unknown" state
        button1.setValue(1);
        button2.setValue(1);
        button3.setValue(1);
        button4.setValue(1);
        button5.setValue(1);
        buttonRed.setValue(1);
        buttonYellow.setValue(1);
        buttonBlue.setValue(1);
        buttonWhite.setValue(1);
        buttonGreen.setValue(1);
        buttonMulti.setValue(1);

        // Update buttons with Negative Info to "No" state
        if (annotationCard.getNotOne() == true) {
            button1.setValue(2);
        }
        if (annotationCard.getNotTwo() == true) {
            button2.setValue(2);
        }
        if (annotationCard.getNotThree() == true) {
            button3.setValue(2);
        }
        if (annotationCard.getNotFour() == true) {
            button4.setValue(2);
        }
        if (annotationCard.getNotFive() == true) {
            button5.setValue(2);
        }
        if (annotationCard.getNotRed() == true) {
            buttonRed.setValue(2);
        }
        if (annotationCard.getNotYellow() == true) {
            buttonYellow.setValue(2);
        }
        if (annotationCard.getNotBlue() == true) {
            buttonBlue.setValue(2);
        }
        if (annotationCard.getNotWhite() == true) {
            buttonWhite.setValue(2);
        }
        if (annotationCard.getNotGreen() == true) {
            buttonGreen.setValue(2);
        }
        if (annotationCard.getNotMulticolor() == true) {
            buttonMulti.setValue(2);
        }


        // Update rank buttons to "Yes" state
        int rank = annotationCard.getRank();
        switch (rank) {
            case 1:
                button1.setValue(0);
                break;
            case 2:
                button2.setValue(0);
                break;
            case 3:
                button3.setValue(0);
                break;
            case 4:
                button4.setValue(0);
                break;
            case 5:
                button5.setValue(0);
                break;
        }

        // Update color buttons to "Yes" state
        Card.Color suit = annotationCard.getSuit();
        switch (suit) {
            case RED:
                buttonRed.setValue(0);
                break;
            case YELLOW:
                buttonYellow.setValue(0);
                break;
            case BLUE:
                buttonBlue.setValue(0);
                break;
            case WHITE:
                buttonWhite.setValue(0);
                break;
            case GREEN:
                buttonGreen.setValue(0);
                break;
            case MULTICOLOR:
                buttonMulti.setValue(0);
                break;
        }

        /* This block will disable buttonMulti for both Multicolor mode "None" and "Distinct".  Unfortunately we want it enabled for "Distinct"
        if (annotationCard.getRainbowState() == Card.Rainbow.NA_RAINBOW) {
            buttonMulti.setEnabled(false);
            //boolean[] states = {false, false, false};
            //buttonMulti.setStates(states);
            buttonMulti.setColors(0xDCDCDC, 0xDCDCDC);
        }*/
    }


    public void onOkButton (View view) {
        Intent resultIntent = new Intent();

        if (button1.getValue() == 2 ) { annotationCard.setNotOne(true); }
        else { annotationCard.setNotOne(false); }

        if (button2.getValue() == 2 ) { annotationCard.setNotTwo(true); }
        else { annotationCard.setNotTwo(false); }

        if (button3.getValue() == 2 ) { annotationCard.setNotThree(true); }
        else { annotationCard.setNotThree(false); }

        if (button4.getValue() == 2 ) { annotationCard.setNotFour(true); }
        else { annotationCard.setNotFour(false); }

        if (button5.getValue() == 2 ) { annotationCard.setNotFive(true); }
        else { annotationCard.setNotFive(false); }

        if (buttonRed.getValue() == 2 ) { annotationCard.setNotRed(true); }
        else { annotationCard.setNotRed(false); }

        if (buttonYellow.getValue() == 2 ) { annotationCard.setNotYellow(true); }
        else { annotationCard.setNotYellow(false); }

        if (buttonBlue.getValue() == 2 ) { annotationCard.setNotBlue(true); }
        else { annotationCard.setNotBlue(false); }

        if (buttonWhite.getValue() == 2 ) { annotationCard.setNotWhite(true); }
        else { annotationCard.setNotWhite(false); }

        if (buttonGreen.getValue() == 2 ) { annotationCard.setNotGreen(true); }
        else { annotationCard.setNotGreen(false); }

        if (buttonMulti.getValue() == 2 ) {
            annotationCard.setNotMulticolor(true);
            if (annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
                annotationCard.setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
            }
        }
        else if (buttonMulti.getValue() == 1 && annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
            annotationCard.setNotMulticolor(false);
            if (annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
                annotationCard.setRainbowState(Card.Rainbow.POSSIBLE_RAINBOW);
            }
        }
        else {
            annotationCard.setNotMulticolor(false);
        }


        int rankYesCount = 0;
        int rank = 0;
        int suitYesCount = 0;
        Card.Color suit = Card.Color.UNKNOWN;

        if (button1.getValue() == 0){
            rankYesCount++;
            rank = 1;
        }
        if (button2.getValue() == 0){
            rankYesCount++;
            rank = 2;
        }
        if (button3.getValue() == 0){
            rankYesCount++;
            rank = 3;
        }
        if (button4.getValue() == 0){
            rankYesCount++;
            rank = 4;
        }
        if (button5.getValue() == 0){
            rankYesCount++;
            rank = 5;
        }
        if (buttonRed.getValue() == 0){
            suitYesCount++;
            suit = Card.Color.RED;
        }
        if (buttonYellow.getValue() == 0){
            suitYesCount++;
            suit = Card.Color.YELLOW;
        }
        if (buttonBlue.getValue() == 0){
            suitYesCount++;
            suit = Card.Color.BLUE;
        }
        if (buttonWhite.getValue() == 0){
            suitYesCount++;
            suit = Card.Color.WHITE;
        }
        if (buttonGreen.getValue() == 0){
            suitYesCount++;
            suit = Card.Color.GREEN;
        }
        if (buttonMulti.getValue() == 0){
            suitYesCount++;
            suit = Card.Color.MULTICOLOR;
        }

        if (rankYesCount >= 2){
            Toast.makeText(getApplicationContext(), "Multiple ranks cannot be set to Yes.", Toast.LENGTH_LONG).show();
        }
        else if (suitYesCount >= 2) {
            Toast.makeText(getApplicationContext(), "Multiple suits cannot be set to Yes.", Toast.LENGTH_LONG).show();
        }
        else {
            annotationCard.setRank(rank);
            annotationCard.setSuit(suit);
            if (suit == Card.Color.MULTICOLOR && annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
                annotationCard.setRainbowState(Card.Rainbow.IS_RAINBOW);
            }

            resultIntent.putExtra("card", annotationCard);
            setResult(0, resultIntent);
            finish();
        }
    }

    public void onCancelButton (View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", annotationCard);
        setResult(1, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", annotationCard);
        setResult(1, resultIntent);
        finish();
    }
}
