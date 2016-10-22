package com.rpalazzo.hanabitracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
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

    static final int MultiStateYes = 0;
    static final int MultiStateUnknown = 1;
    static final int MultiStateNo = 2;

    static final int ReturnOK = 0;
    static final int ReturnCancel = 1;


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
        button1.setValue(MultiStateUnknown);
        button2.setValue(MultiStateUnknown);
        button3.setValue(MultiStateUnknown);
        button4.setValue(MultiStateUnknown);
        button5.setValue(MultiStateUnknown);
        buttonRed.setValue(MultiStateUnknown);
        buttonYellow.setValue(MultiStateUnknown);
        buttonBlue.setValue(MultiStateUnknown);
        buttonWhite.setValue(MultiStateUnknown);
        buttonGreen.setValue(MultiStateUnknown);
        buttonMulti.setValue(MultiStateUnknown);

        // Update buttons with Negative Info to "No" state
        if (annotationCard.getNotOne() == true) {
            button1.setValue(MultiStateNo);
        }
        if (annotationCard.getNotTwo() == true) {
            button2.setValue(MultiStateNo);
        }
        if (annotationCard.getNotThree() == true) {
            button3.setValue(MultiStateNo);
        }
        if (annotationCard.getNotFour() == true) {
            button4.setValue(MultiStateNo);
        }
        if (annotationCard.getNotFive() == true) {
            button5.setValue(MultiStateNo);
        }
        if (annotationCard.getNotRed() == true) {
            buttonRed.setValue(MultiStateNo);
        }
        if (annotationCard.getNotYellow() == true) {
            buttonYellow.setValue(MultiStateNo);
        }
        if (annotationCard.getNotBlue() == true) {
            buttonBlue.setValue(MultiStateNo);
        }
        if (annotationCard.getNotWhite() == true) {
            buttonWhite.setValue(MultiStateNo);
        }
        if (annotationCard.getNotGreen() == true) {
            buttonGreen.setValue(MultiStateNo);
        }
        if (annotationCard.getNotMulticolor() == true) {
            buttonMulti.setValue(MultiStateNo);
        }


        // Update rank buttons to "Yes" state
        int rank = annotationCard.getRank();
        switch (rank) {
            case 1:
                button1.setValue(MultiStateYes);
                break;
            case 2:
                button2.setValue(MultiStateYes);
                break;
            case 3:
                button3.setValue(MultiStateYes);
                break;
            case 4:
                button4.setValue(MultiStateYes);
                break;
            case 5:
                button5.setValue(MultiStateYes);
                break;
        }

        // Update color buttons to "Yes" state
        Card.Color suit = annotationCard.getSuit();
        switch (suit) {
            case RED:
                buttonRed.setValue(MultiStateYes);
                break;
            case YELLOW:
                buttonYellow.setValue(MultiStateYes);
                break;
            case BLUE:
                buttonBlue.setValue(MultiStateYes);
                break;
            case WHITE:
                buttonWhite.setValue(MultiStateYes);
                break;
            case GREEN:
                buttonGreen.setValue(MultiStateYes);
                break;
            case MULTICOLOR:
                buttonMulti.setValue(MultiStateYes);
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

        if (button1.getValue() == MultiStateNo ) { annotationCard.setNotOne(true); }
        else { annotationCard.setNotOne(false); }

        if (button2.getValue() == MultiStateNo ) { annotationCard.setNotTwo(true); }
        else { annotationCard.setNotTwo(false); }

        if (button3.getValue() == MultiStateNo ) { annotationCard.setNotThree(true); }
        else { annotationCard.setNotThree(false); }

        if (button4.getValue() == MultiStateNo ) { annotationCard.setNotFour(true); }
        else { annotationCard.setNotFour(false); }

        if (button5.getValue() == MultiStateNo ) { annotationCard.setNotFive(true); }
        else { annotationCard.setNotFive(false); }

        if (buttonRed.getValue() == MultiStateNo ) { annotationCard.setNotRed(true); }
        else { annotationCard.setNotRed(false); }

        if (buttonYellow.getValue() == MultiStateNo ) { annotationCard.setNotYellow(true); }
        else { annotationCard.setNotYellow(false); }

        if (buttonBlue.getValue() == MultiStateNo ) { annotationCard.setNotBlue(true); }
        else { annotationCard.setNotBlue(false); }

        if (buttonWhite.getValue() == MultiStateNo ) { annotationCard.setNotWhite(true); }
        else { annotationCard.setNotWhite(false); }

        if (buttonGreen.getValue() == MultiStateNo ) { annotationCard.setNotGreen(true); }
        else { annotationCard.setNotGreen(false); }

        // If multicolor mode == All, toggle multicolor icon on/off if multicolor multistate is set to unknown/no
        if (buttonMulti.getValue() == MultiStateNo ) {
            annotationCard.setNotMulticolor(true);
            if (annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
                annotationCard.setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
            }
        }
        else if (buttonMulti.getValue() == MultiStateUnknown && annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
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

        if (button1.getValue() == MultiStateYes){
            rankYesCount++;
            rank = 1;
        }
        if (button2.getValue() == MultiStateYes){
            rankYesCount++;
            rank = 2;
        }
        if (button3.getValue() == MultiStateYes){
            rankYesCount++;
            rank = 3;
        }
        if (button4.getValue() == MultiStateYes){
            rankYesCount++;
            rank = 4;
        }
        if (button5.getValue() == MultiStateYes){
            rankYesCount++;
            rank = 5;
        }
        if (buttonRed.getValue() == MultiStateYes){
            suitYesCount++;
            suit = Card.Color.RED;
        }
        if (buttonYellow.getValue() == MultiStateYes){
            suitYesCount++;
            suit = Card.Color.YELLOW;
        }
        if (buttonBlue.getValue() == MultiStateYes){
            suitYesCount++;
            suit = Card.Color.BLUE;
        }
        if (buttonWhite.getValue() == MultiStateYes){
            suitYesCount++;
            suit = Card.Color.WHITE;
        }
        if (buttonGreen.getValue() == MultiStateYes){
            suitYesCount++;
            suit = Card.Color.GREEN;
        }
        if (buttonMulti.getValue() == MultiStateYes){
            suitYesCount++;
            suit = Card.Color.MULTICOLOR;
        }

        if (rankYesCount >= 2){
            Toast.makeText(getApplicationContext(), R.string.multirankerror, Toast.LENGTH_LONG).show();
        }
        else if (suitYesCount >= 2) {
            Toast.makeText(getApplicationContext(), R.string.multisuiterror, Toast.LENGTH_LONG).show();
        }
        else {
            annotationCard.setRank(rank);
            annotationCard.setSuit(suit);
            if (suit == Card.Color.MULTICOLOR && annotationCard.getRainbowState() != Card.Rainbow.NA_RAINBOW) {
                annotationCard.setRainbowState(Card.Rainbow.IS_RAINBOW);
            }

            resultIntent.putExtra("card", annotationCard);
            setResult(ReturnOK, resultIntent);
            finish();
        }
    }

    public void onCancelButton (View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", annotationCard);
        setResult(ReturnCancel, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", annotationCard);
        setResult(ReturnCancel, resultIntent);
        finish();
    }
}
