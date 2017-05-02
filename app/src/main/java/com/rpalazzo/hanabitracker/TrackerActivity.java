/* Copyright 2016 Robert Palazzo <rpalazzo@gmail.com>
 * Proprietary and confidential
 * Copying of this file without express written consent is prohibited.
 */

package com.rpalazzo.hanabitracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

public class TrackerActivity extends AppCompatActivity {

    private int nCards;
    private int MulticolorMode;
    private ArrayList<com.rpalazzo.hanabitracker.Card> cardArrayList = new ArrayList<com.rpalazzo.hanabitracker.Card>();
    private Stack<Card> undoStack = new Stack<Card>();

    private LinearLayout background;
    private TableLayout tl;
    private ImageButton multicolorButton;

    private ImageButton buttonCard1;
    private ImageButton buttonCard2;
    private ImageButton buttonCard3;
    private ImageButton buttonCard4;
    private ImageButton buttonCard5;
    private ImageButton buttonClue1;
    private ImageButton buttonClue2;
    private ImageButton buttonClue3;
    private ImageButton buttonClue4;
    private ImageButton buttonClue5;
    private ImageButton buttonClueRed;
    private ImageButton buttonClueYellow;
    private ImageButton buttonClueBlue;
    private ImageButton buttonClueWhite;
    private ImageButton buttonClueGreen;
    private ImageButton buttonClueMulticolor;

    private TextView textViewNeg1;
    private TextView textViewNeg2;
    private TextView textViewNeg3;
    private TextView textViewNeg4;
    private TextView textViewNeg5;

    private int annotatedCard;

    private Boolean onBackPressed;


    public enum SELECTION_MODE {
        NONE, CARD, CLUE
    }
    private SELECTION_MODE currentSelectionMode;

    public enum CLUE_SELECTION {
        NONE, ONE, TWO, THREE, FOUR, FIVE, RED, YELLOW, BLUE, WHITE, GREEN, MULTICOLOR
    }
    private CLUE_SELECTION clueSelection;

    static final int ANNOTATION_REQUEST = 1;  // The request code

    /* TrackerActivity LIFE CYCLE NOTES
     *
     * https://docs.google.com/presentation/d/1IozN-ldJPyIA6Hq8pU29bQPm_tBWegKkh_iMmSRfM8E/edit#slide=id.p
     *
     * OnCreate() - if hand file does not exist, create it;
     *              else do nothing
     *
     * OnStart() - read hand file; set onBackPressed false
     *
     * OnBackPressed() - Set onBackPressed true
     *
     * OnStop() - if onBackPressed, delete file
     *            else (over)write file
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        //Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
        Log.v("HanabiTracker", "Entering TrackerActivity::OnCreate()");


        // Layout 4 or 5 cards;  if 4 cards the oldest column is never used, so collapse it
        nCards = getIntent().getIntExtra("nCards", 0);
        if (nCards == 0) {
            tl = (TableLayout) findViewById(R.id.tableLayout1);
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String strnegativeinfo = SP.getString("agedirection_key", "NA");
            if (strnegativeinfo.equals("Left to right")) {
                tl.setColumnCollapsed(4, true);
            }
            else {
                tl.setColumnCollapsed(0, true);
            }
        }

        // Hide or display the multicolor clue button
        MulticolorMode = getIntent().getIntExtra("nMulticolorMode",0);
        if (MulticolorMode != 1) {
            //ImageButton multicolorButton = new ImageButton(getApplicationContext());
            multicolorButton = (ImageButton) findViewById(R.id.button_clueMulticolor);
            multicolorButton.setVisibility(View.GONE);
        }


        // If hand file exists, do nothing -- previously written file will be read in OnStart()
        // Else write hand file with all default (unknown) cards
        File file = new File(getFilesDir(), "hand");
        if(file.exists()) {
            // do nothing
        }
        else {
            try {
                FileOutputStream fos = openFileOutput("hand", Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                for (int i = 0; i < 5; i++) {
                    Card c = new Card(MulticolorMode);
                    os.writeObject(c);
                }
                os.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        currentSelectionMode = SELECTION_MODE.NONE;
        clueSelection = CLUE_SELECTION.NONE;

        background = (LinearLayout)findViewById(R.id.linearLayout1);
        buttonCard1 = (ImageButton)findViewById(R.id.button_card1);
        buttonCard2 = (ImageButton)findViewById(R.id.button_card2);
        buttonCard3 = (ImageButton)findViewById(R.id.button_card3);
        buttonCard4 = (ImageButton)findViewById(R.id.button_card4);
        buttonCard5 = (ImageButton)findViewById(R.id.button_card5);
        textViewNeg1 = (TextView) findViewById(R.id.text_negative1);
        textViewNeg2 = (TextView) findViewById(R.id.text_negative2);
        textViewNeg3 = (TextView) findViewById(R.id.text_negative3);
        textViewNeg4 = (TextView) findViewById(R.id.text_negative4);
        textViewNeg5 = (TextView) findViewById(R.id.text_negative5);
        buttonClue1 = (ImageButton)findViewById(R.id.button_clue1);
        buttonClue2 = (ImageButton)findViewById(R.id.button_clue2);
        buttonClue3 = (ImageButton)findViewById(R.id.button_clue3);
        buttonClue4 = (ImageButton)findViewById(R.id.button_clue4);
        buttonClue5 = (ImageButton)findViewById(R.id.button_clue5);
        buttonClueRed = (ImageButton)findViewById(R.id.button_clueRed);
        buttonClueYellow = (ImageButton)findViewById(R.id.button_clueYellow);
        buttonClueBlue = (ImageButton)findViewById(R.id.button_clueBlue);
        buttonClueWhite = (ImageButton)findViewById(R.id.button_clueWhite);
        buttonClueGreen = (ImageButton)findViewById(R.id.button_clueGreen);
        buttonClueMulticolor = (ImageButton)findViewById(R.id.button_clueMulticolor);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strbackgroundcolor = SP.getString("backgroundcolor_key", "NA");
        if (strbackgroundcolor.equals("Black")) {
            //background.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            background.setBackgroundColor(getResources().getColor(android.R.color.black));
        }

        buttonCard1.setOnLongClickListener(new View.OnLongClickListener() {
           public boolean onLongClick(View v) {
               LongClickCard(0);
               return true;
           }
        });

        buttonCard2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                LongClickCard(1);
                return true;
            }
        });

        buttonCard3.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                LongClickCard(2);
                return true;
            }
        });

        buttonCard4.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                LongClickCard(3);
                return true;
            }
        });

        buttonCard5.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                LongClickCard(4);
                return true;
            }
        });


        String strnegativeclue = SP.getString("negativeclue_key", "NA");
        if (strnegativeclue.equals("Enabled")) {

            buttonClue1.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.ONE);
                    return true;
                }
            });

            buttonClue2.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.TWO);
                    return true;
                }
            });

            buttonClue3.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.THREE);
                    return true;
                }
            });

            buttonClue4.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.FOUR);
                    return true;
                }
            });

            buttonClue5.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.FIVE);
                    return true;
                }
            });

            buttonClueRed.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.RED);
                    return true;
                }
            });

            buttonClueYellow.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.YELLOW);
                    return true;
                }
            });

            buttonClueBlue.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.BLUE);
                    return true;
                }
            });

            buttonClueWhite.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.WHITE);
                    return true;
                }
            });

            buttonClueGreen.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.GREEN);
                    return true;
                }
            });

            buttonClueMulticolor.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    LongClickClue(CLUE_SELECTION.MULTICOLOR);
                    return true;
                }
            });
        }

        Log.v("HanabiTracker", "Exiting TrackerActivity::OnCreate()");

    }


    @Override
    public void onStart() {  // called after onCreate() and when restarting after OS-caused stop
        super.onStart();

        //Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.v("HanabiTracker", "Entering TrackerActivity::OnStart()");

        onBackPressed = false;

        cardArrayList.clear();

        try {
            FileInputStream fis = openFileInput("hand");
            ObjectInputStream is = new ObjectInputStream(fis);

            for (int i = 0; i < 5; i++) {
                Card temp = new Card(MulticolorMode);
                temp  = (Card) is.readObject();
                cardArrayList.add(i, temp);
            }

            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        paint();

        Log.v("HanabiTracker", "Exiting TrackerActivity::OnStart()");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Toast.makeText(getApplicationContext(), "onBackPressed()", Toast.LENGTH_SHORT).show();
        Log.v("HanabiTracker", "Entering TrackerActivity::OnBackPressed()");
        onBackPressed = true;
        Log.v("HanabiTracker", "Exiting TrackerActivity::OnBackPressed()");

    }


    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.v("HanabiTracker", "Entering TrackerActivity::OnStop()");

        if (onBackPressed) {
            File file = new File(getFilesDir(), "hand");
            file.delete();
        }
        else {
            PreserveHand();
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::OnStop()");
    }


    public void PreserveHand () {
        Log.v("HanabiTracker", "Entering TrackerActivity::PerserveHand()");
        try {
            FileOutputStream fos = openFileOutput("hand", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            for (Card card : cardArrayList) {
                os.writeObject(card);
            }
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::PreserveHand()");
    }


    void LongClickCard(int i) {
        Log.v("HanabiTracker", "Entering TrackerActivity::LongClickCard()");

        cleanupMulticardRainbow();
        annotatedCard = i;

        Intent intent = new Intent(this, AnnotationActivity.class);
        intent.putExtra("card", cardArrayList.get(i));
        startActivityForResult(intent, ANNOTATION_REQUEST);

        Log.v("HanabiTracker", "Exiting TrackerActivity::LongClickCard()");
    }

    void LongClickClue(CLUE_SELECTION clue) {
        Log.v("HanabiTracker", "Entering TrackerActivity::LongClickClue()");

        cleanupMulticardRainbow();
        pushCardstoUndoStack();

        // unselect any selected clues
        clueSelection = CLUE_SELECTION.NONE;
        currentSelectionMode = SELECTION_MODE.NONE;

        //Before setting negative info, verify there are no conflicting clues already given
        switch (clue) {
            case ONE:
                if (doesAnyCardHaveClue(1) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case TWO:
                if (doesAnyCardHaveClue(2) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case THREE:
                if (doesAnyCardHaveClue(3) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case FOUR:
                if (doesAnyCardHaveClue(4) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case FIVE:
                if (doesAnyCardHaveClue(5) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case RED:
                if (doesAnyCardHaveClue(Card.Color.RED) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case YELLOW:
                if (doesAnyCardHaveClue(Card.Color.YELLOW) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case BLUE:
                if (doesAnyCardHaveClue(Card.Color.BLUE) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case WHITE:
                if (doesAnyCardHaveClue(Card.Color.WHITE) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case GREEN:
                if (doesAnyCardHaveClue(Card.Color.GREEN) == true) {
                    errorFeedback();
                    return;
                }
                break;
            case MULTICOLOR:
                if (doesAnyCardHaveClue(Card.Color.MULTICOLOR) == true) {
                    errorFeedback();
                    return;
                }
                break;
        }


        //iterate through cards setting negative info for each
        for (Card card : cardArrayList) {
            switch (clue) {
                case ONE:
                    card.setNotOne(true);
                    break;
                case TWO:
                    card.setNotTwo(true);
                    break;
                case THREE:
                    card.setNotThree(true);
                    break;
                case FOUR:
                    card.setNotFour(true);
                    break;
                case FIVE:
                    card.setNotFive(true);
                    break;
                case RED:
                    card.setNotRed(true);
                    break;
                case YELLOW:
                    card.setNotYellow(true);
                    break;
                case BLUE:
                    card.setNotBlue(true);
                    break;
                case WHITE:
                    card.setNotWhite(true);
                    break;
                case GREEN:
                    card.setNotGreen(true);
                    break;
                case MULTICOLOR:
                    card.setNotMulticolor(true);
                    break;
            }

        }

        paint();

        Log.v("HanabiTracker", "Exiting TrackerActivity::LongClickClue()");
    }

    protected Boolean doesAnyCardHaveClue(int rank) {
        for (Card card : cardArrayList) {
            if (card.getRank() == rank) {
                return true;
            }
        }
        return false;
    }

    protected Boolean doesAnyCardHaveClue(Card.Color color) {
        for (Card card : cardArrayList) {
            if (card.getSuit() == color) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v("HanabiTracker", "Entering TrackerActivity::onActivityResult()");

        if (requestCode == ANNOTATION_REQUEST) {

            if (resultCode == AnnotationActivity.ReturnOK) {
                pushCardstoUndoStack();

                Card temp = new Card(MulticolorMode);
                temp  = (Card) data.getSerializableExtra("card");
                cardArrayList.remove(annotatedCard);
                cardArrayList.add(annotatedCard, temp);
            }
        }
        PreserveHand();

        Log.v("HanabiTracker", "Exiting TrackerActivity::OnAcivityResult()");
    }


    public void onDel1(View view) { onDel(0); }
    public void onDel2(View view) { onDel(1); }
    public void onDel3(View view) { onDel(2); }
    public void onDel4(View view) { onDel(3); }
    public void onDel5(View view) { onDel(4); }

    private void onDel(int index) {
        Log.v("HanabiTracker", "Entering TrackerActivity::OnDel()");

        cleanupMulticardRainbow();
        pushCardstoUndoStack();

        // remove selected card
        cardArrayList.remove(index);

        // add new card
        com.rpalazzo.hanabitracker.Card c = new com.rpalazzo.hanabitracker.Card(MulticolorMode);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strnegativeinfo = SP.getString("agedirection_key", "NA");
        if (strnegativeinfo.equals("Left to right")) {
            cardArrayList.add(0, c);
        }
        else {
            cardArrayList.add(c);
        }

        // unselect any selected clues
        clueSelection = CLUE_SELECTION.NONE;
        currentSelectionMode = SELECTION_MODE.NONE;

        // unselect any selected cards
        for (int i = 0; i < 5; i++) {
            cardArrayList.get(i).setSelected(Boolean.FALSE);
        }

        paint();
        Log.v("HanabiTracker", "Exiting TrackerActivity::OnDel()");
    }

    public void onCard1(View view) { onCard(0); }
    public void onCard2(View view) { onCard(1); }
    public void onCard3(View view) { onCard(2); }
    public void onCard4(View view) { onCard(3); }
    public void onCard5(View view) { onCard(4); }

    private void onCard(int index) {

        Log.v("HanabiTracker", "Entering TrackerActivity::OnCard()");

        // do NOT call cleanupMulticardRainbow here!!!

        if (currentSelectionMode == SELECTION_MODE.NONE || currentSelectionMode == SELECTION_MODE.CARD) {
            if (cardArrayList.get(index).getSelected() == Boolean.TRUE)
            {
                cardArrayList.get(index).setSelected(Boolean.FALSE);

                //a card was unselected, if any cards are still currentSelectionMode then SELECTION_MODE.CARD
                currentSelectionMode = SELECTION_MODE.NONE;
                for (int j = 0; j < 5; j++) {
                    if (cardArrayList.get(j).getSelected() == Boolean.TRUE) {
                        currentSelectionMode = SELECTION_MODE.CARD;
                    }
                }
            }
            else {
                cardArrayList.get(index).setSelected(Boolean.TRUE);
                currentSelectionMode = SELECTION_MODE.CARD;
            }
        }
        else { // Apply currently selected clue to this card
            switch (clueSelection){
                case ONE:
                    ApplyNumberToCard(index, 1);
                    break;
                case TWO:
                    ApplyNumberToCard(index, 2);
                    break;
                case THREE:
                    ApplyNumberToCard(index, 3);
                    break;
                case FOUR:
                    ApplyNumberToCard(index, 4);
                    break;
                case FIVE:
                    ApplyNumberToCard(index, 5);
                    break;
                case RED:
                    ApplyColorToCard(index, Card.Color.RED);
                    break;
                case YELLOW:
                    ApplyColorToCard(index, Card.Color.YELLOW);
                    break;
                case BLUE:
                    ApplyColorToCard(index, Card.Color.BLUE);
                    break;
                case WHITE:
                    ApplyColorToCard(index, Card.Color.WHITE);
                    break;
                case GREEN:
                    ApplyColorToCard(index, Card.Color.GREEN);
                    break;
                case MULTICOLOR:
                    ApplyColorToCard(index, Card.Color.MULTICOLOR);
                    break;
            }
         }
        paint();

        Log.v("HanabiTracker", "Exiting TrackerActivity::OnCard()");
    }

    // Called from OnCard();  A clue is selected and then one or more cards are clued.
    private void ApplyNumberToCard( int index, int n) {
        Log.v("HanabiTracker", "Entering TrackerActivity::ApplyNumberToCard()");

        // If the card number is unknown (0) or already set to this numbeer...
        if (cardArrayList.get(index).getRank() == 0 || cardArrayList.get(index).getRank() == n) {

            // ... save off the current state before making any changes
            pushCardstoUndoStack();

            // ... set the negative information for all other cards that are not already this number
            for (int j = 0; j < 5; j++) {
                if (cardArrayList.get(j).getRank() != n) {
                    cardArrayList.get(j).setNotNumber(n, Boolean.TRUE);
                }
            }

            // ... set the card to this number (do this AFTER setting negative info)
            cardArrayList.get(index).setRank(n);
        }
        else { errorFeedback(); }

        Log.v("HanabiTracker", "Exiting TrackerActivity::ApplyNumberToCard()");

    }

    // Called from OnCard();  A clue is selected and then one or more cards are clued.
    private void ApplyColorToCard( int index, Card.Color color) {
        Log.v("HanabiTracker", "Entering TrackerActivity::ApplyColorToCard()");

        // If the card suit is unknown or already set to this color...
        if (cardArrayList.get(index).getSuit() == Card.Color.UNKNOWN || (cardArrayList.get(index).getSuit() == color)) {

            // ... save off the current state before making any changes
            pushCardstoUndoStack();

            // ... set the negative information for all other cards that are not already this color
            for (int j = 0; j < 5; j++) {
                if ((j != index) && (cardArrayList.get(j).getDirtyFlag() == false)) { // if not this card or already dealt with (dirty)
                    if (cardArrayList.get(j).getSuit() != color) {
                        cardArrayList.get(j).setNotColor(color, Boolean.TRUE);
                    }
                    if (cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW ) {
                        // This change may get reverted.  See Note below.
                        cardArrayList.get(j).setRainbowState(Card.Rainbow.PENDING_NOT_RAINBOW);
                    }
                }
            }

            // While cluing multiple cards, cards after the first will be marked PENDING_NOT_RAINBOW.
            // If these cards are subsequently selected, reset them to POSSIBLE_RAINBOW.
            if (cardArrayList.get(index).getRainbowState() == Card.Rainbow.PENDING_NOT_RAINBOW) {
                cardArrayList.get(index).setRainbowState(Card.Rainbow.POSSIBLE_RAINBOW);
            }

            // ... set the suit to this color (do this AFTER setting negative info)
            cardArrayList.get(index).setSuit(color);

            // ... set dirtyFlag so we know to keep this card as a possible rainbow
            if (cardArrayList.get(index).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW) {
                cardArrayList.get(index).setDirtyFlag(Boolean.TRUE);
            }
        }

        // Allow a different color clue if in multicolor mode and
        else if ( MulticolorMode == 2 && cardArrayList.get(index).getRainbowState() != Card.Rainbow.IS_NOT_RAINBOW) {

            // ... save off the current state before making any changes
            pushCardstoUndoStack();

            // ... set the negative information for all other cards that are not already this color
            for (int j = 0; j < 5; j++) {
                if ( j != index && cardArrayList.get(j).getDirtyFlag() == false) {
                    if (cardArrayList.get(j).getSuit() != color) {
                        cardArrayList.get(j).setNotColor(color, Boolean.TRUE);
                    }
                    if (cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW ) {
                        cardArrayList.get(j).setRainbowState(Card.Rainbow.PENDING_NOT_RAINBOW);
                    }
                }
            }

            cardArrayList.get(index).setSuit(Card.Color.MULTICOLOR);
            cardArrayList.get(index).setRainbowState(Card.Rainbow.IS_RAINBOW);

        }

        // otherwise prevent conflicting color clues
        else {
            errorFeedback();
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::ApplyColorToCard()");
    }

    public void onClueRed(View view) { OnClue(CLUE_SELECTION.RED); }
    public void onClueYellow(View view) { OnClue(CLUE_SELECTION.YELLOW); }
    public void onClueBlue(View view) { OnClue(CLUE_SELECTION.BLUE); }
    public void onClueWhite(View view) { OnClue(CLUE_SELECTION.WHITE); }
    public void onClueGreen(View view) { OnClue(CLUE_SELECTION.GREEN); }
    public void onClueMulticolor(View view) { OnClue(CLUE_SELECTION.MULTICOLOR); }
    public void onClue1(View view) { OnClue(CLUE_SELECTION.ONE); }
    public void onClue2(View view) { OnClue(CLUE_SELECTION.TWO); }
    public void onClue3(View view) { OnClue(CLUE_SELECTION.THREE); }
    public void onClue4(View view) { OnClue(CLUE_SELECTION.FOUR); }
    public void onClue5(View view) { OnClue(CLUE_SELECTION.FIVE); }

    private void OnClue(CLUE_SELECTION clue) {
        Log.v("HanabiTracker", "Entering TrackerActivity::OnClue()");

        cleanupMulticardRainbow();

        // if the currently selected clue is selected again; deselect it
        if (clueSelection == clue) {
            clueSelection = CLUE_SELECTION.NONE;
            currentSelectionMode = SELECTION_MODE.NONE;
        }

        // else if no clue or different clue selected, select that clue
        else if (currentSelectionMode == SELECTION_MODE.NONE || currentSelectionMode == SELECTION_MODE.CLUE) {
            currentSelectionMode = SELECTION_MODE.CLUE;
            clueSelection = clue;
        }

        // else assign this clue to all currrently selected cards
        else { // SELECTION_MODE.CARD

            // Updating multiple cards is an atomic operation so we must first
            // verify the clue can be applied to each selected card before beginning
            // to change them.
            for (int j = 0; j < 5; j++) {
                if (cardArrayList.get(j).getSelected() == true) {

                    switch (clue) {
                        case RED:
                            if (!(cardArrayList.get(j).getSuit() == Card.Color.UNKNOWN ||
                                    cardArrayList.get(j).getSuit() == Card.Color.RED  ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.IS_RAINBOW ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW)) {
                                errorFeedback();
                                return;
                                }
                            break;
                        case YELLOW:
                            if (!(cardArrayList.get(j).getSuit() == Card.Color.UNKNOWN ||
                                    cardArrayList.get(j).getSuit() == Card.Color.YELLOW  ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.IS_RAINBOW ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW)) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case BLUE:
                            if (!(cardArrayList.get(j).getSuit() == Card.Color.UNKNOWN ||
                                    cardArrayList.get(j).getSuit() == Card.Color.BLUE  ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.IS_RAINBOW ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW)) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case WHITE:
                            if (!(cardArrayList.get(j).getSuit() == Card.Color.UNKNOWN ||
                                    cardArrayList.get(j).getSuit() == Card.Color.WHITE  ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.IS_RAINBOW ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW)) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case GREEN:
                            if (!(cardArrayList.get(j).getSuit() == Card.Color.UNKNOWN ||
                                    cardArrayList.get(j).getSuit() == Card.Color.GREEN  ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.IS_RAINBOW ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW)) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case MULTICOLOR:
                            if (!(cardArrayList.get(j).getSuit() == Card.Color.UNKNOWN ||
                                    cardArrayList.get(j).getSuit() == Card.Color.MULTICOLOR  ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.IS_RAINBOW ||
                                    cardArrayList.get(j).getRainbowState() == Card.Rainbow.POSSIBLE_RAINBOW)) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case ONE:
                            if (cardArrayList.get(j).getRank() != 0 &&
                                    cardArrayList.get(j).getRank() != 1) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case TWO:
                            if (cardArrayList.get(j).getRank() != 0 &&
                                    cardArrayList.get(j).getRank() != 2) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case THREE:
                            if (cardArrayList.get(j).getRank() != 0 &&
                                    cardArrayList.get(j).getRank() != 3) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case FOUR:
                            if (cardArrayList.get(j).getRank() != 0 &&
                                    cardArrayList.get(j).getRank() != 4) {
                                errorFeedback();
                                return;
                            }
                            break;
                        case FIVE:
                            if (cardArrayList.get(j).getRank() != 0 &&
                                    cardArrayList.get(j).getRank() != 5) {
                                errorFeedback();
                                return;
                            }
                            break;
                    }
                }
            }

            // There were no errors, so push cards to undoStack
            pushCardstoUndoStack();

            // Change the selected card(s) to the clued suit or rank
            for (int i = 0; i < 5; i++) {
                if (cardArrayList.get(i).getSelected() == true) {

                    switch (clue) {
                        case RED:
                            applyColorToCard2(cardArrayList.get(i), Card.Color.RED);
                            break;
                        case YELLOW:
                            applyColorToCard2(cardArrayList.get(i), Card.Color.YELLOW);
                            break;
                        case BLUE:
                            applyColorToCard2(cardArrayList.get(i), Card.Color.BLUE);
                            break;
                        case WHITE:
                            applyColorToCard2(cardArrayList.get(i), Card.Color.WHITE);
                            break;
                        case GREEN:
                            applyColorToCard2(cardArrayList.get(i), Card.Color.GREEN);
                            break;
                        case MULTICOLOR:
                            applyColorToCard2(cardArrayList.get(i), Card.Color.MULTICOLOR);
                            break;
                        case ONE:
                            cardArrayList.get(i).setRank(1);
                            break;
                        case TWO:
                            cardArrayList.get(i).setRank(2);
                            break;
                        case THREE:
                            cardArrayList.get(i).setRank(3);
                            break;
                        case FOUR:
                            cardArrayList.get(i).setRank(4);
                            break;
                        case FIVE:
                            cardArrayList.get(i).setRank(5);
                            break;
                    }
                }

                // For all the UNselected cards, set negative info
                else {
                    switch (clue) {
                        case RED:
                            cardArrayList.get(i).setNotRed(Boolean.TRUE);
                            cardArrayList.get(i).setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
                            // todo: error if not all possible cards are clued
                            break;
                        case YELLOW:
                            cardArrayList.get(i).setNotYellow(Boolean.TRUE);
                            cardArrayList.get(i).setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
                            break;
                        case BLUE:
                            cardArrayList.get(i).setNotBlue(Boolean.TRUE);
                            cardArrayList.get(i).setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
                            break;
                        case WHITE:
                            cardArrayList.get(i).setNotWhite(Boolean.TRUE);
                            cardArrayList.get(i).setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
                            break;
                        case GREEN:
                            cardArrayList.get(i).setNotGreen(Boolean.TRUE);
                            cardArrayList.get(i).setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
                            break;
                        case MULTICOLOR:
                            cardArrayList.get(i).setNotMulticolor(Boolean.TRUE);
                            //cardArrayList.get(i).setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
                            break;
                        case ONE:
                            cardArrayList.get(i).setNotOne(Boolean.TRUE);
                            break;
                        case TWO:
                            cardArrayList.get(i).setNotTwo(Boolean.TRUE);
                            break;
                        case THREE:
                            cardArrayList.get(i).setNotThree(Boolean.TRUE);
                            break;
                        case FOUR:
                            cardArrayList.get(i).setNotFour(Boolean.TRUE);
                            break;
                        case FIVE:
                            cardArrayList.get(i).setNotFive(Boolean.TRUE);
                            break;
                    }
                }
                cardArrayList.get(i).setSelected(Boolean.FALSE);
            }
            currentSelectionMode = SELECTION_MODE.NONE;
        }
        paint();
        Log.v("HanabiTracker", "Exiting TrackerActivity::OnClue()");
    }

    public void applyColorToCard2(Card card, Card.Color color) {
        Log.v("HanabiTracker", "Entering TrackerActivity::applyColorToCard2()");
        if (card.getSuit() == Card.Color.UNKNOWN) {
            card.setSuit(color);
            //cardArrayList.get(i).setRainbowState(Card.Rainbow.POSSIBLE_RAINBOW);
        }
        else if (card.getSuit() == color) {
            // do nothing
        }
        else { // some other color
            card.setSuit(Card.Color.MULTICOLOR);
            card.setRainbowState(Card.Rainbow.IS_RAINBOW);
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::applyColorToCard2()");
    }

    public void pushCardstoUndoStack() {
        Log.v("HanabiTracker", "Entering TrackerActivity::pushCardstoUndoStack()");
        Card temp;
        for (Card c : cardArrayList) {
            temp = new Card(c);
            undoStack.push(temp);
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::pushCardstoUndoStack()");
    }

    public void onUndo(View view) {
        Log.v("HanabiTracker", "Entering TrackerActivity::OnUndo()");

        cleanupMulticardRainbow();

        if (undoStack.size() >= 5) {
            cardArrayList.clear();
            for (int i = 0; i < 5; i++) {
                Card temp = new Card(undoStack.pop());
                cardArrayList.add(0, temp);
            }
        }
        else {
            errorFeedback();
        }

        // unselect any selected clues
        clueSelection = CLUE_SELECTION.NONE;
        currentSelectionMode = SELECTION_MODE.NONE;
        // unselect any selected cards
        for (int i = 0; i < 5; i++) {
            cardArrayList.get(i).setSelected(Boolean.FALSE);
        }

        paint();
        Log.v("HanabiTracker", "Exiting TrackerActivity::OnUndo()");
    }


    public void paint() {
        Log.v("HanabiTracker", "Entering TrackerActivity::paint()");

        buttonCard1.setImageResource(cardArrayList.get(0).getImageReference());
        buttonCard2.setImageResource(cardArrayList.get(1).getImageReference());
        buttonCard3.setImageResource(cardArrayList.get(2).getImageReference());
        buttonCard4.setImageResource(cardArrayList.get(3).getImageReference());
        buttonCard5.setImageResource(cardArrayList.get(4).getImageReference());

        textViewNeg1.setText(cardArrayList.get(0).getNegativeInfo());
        textViewNeg2.setText(cardArrayList.get(1).getNegativeInfo());
        textViewNeg3.setText(cardArrayList.get(2).getNegativeInfo());
        textViewNeg4.setText(cardArrayList.get(3).getNegativeInfo());
        textViewNeg5.setText(cardArrayList.get(4).getNegativeInfo());

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strnegativeinfo = SP.getString("negativeinfo_key", "NA");
        if (strnegativeinfo.equals("Hide")) {
            textViewNeg1.setText("");
            textViewNeg2.setText("");
            textViewNeg3.setText("");
            textViewNeg4.setText("");
            textViewNeg5.setText("");
        }

        String strbackgroundcolor = SP.getString("backgroundcolor_key", "NA");
        if (strbackgroundcolor.equals("Black")) {
            textViewNeg1.setTextColor(getResources().getColor(android.R.color.white));
            textViewNeg2.setTextColor(getResources().getColor(android.R.color.white));
            textViewNeg3.setTextColor(getResources().getColor(android.R.color.white));
            textViewNeg4.setTextColor(getResources().getColor(android.R.color.white));
            textViewNeg5.setTextColor(getResources().getColor(android.R.color.white));
        }

        buttonClue1.setImageResource(R.drawable.c1);
        buttonClue2.setImageResource(R.drawable.c2);
        buttonClue3.setImageResource(R.drawable.c3);
        buttonClue4.setImageResource(R.drawable.c4);
        buttonClue5.setImageResource(R.drawable.c5);

        buttonClueRed.setImageResource(R.drawable.cr);
        buttonClueYellow.setImageResource(R.drawable.cy);
        buttonClueBlue.setImageResource(R.drawable.cb);
        buttonClueWhite.setImageResource(R.drawable.cw);
        buttonClueGreen.setImageResource(R.drawable.cg);
        buttonClueMulticolor.setImageResource(R.drawable.cm);

        switch (clueSelection) {
            case NONE:
                break;
            case ONE:
                buttonClue1.setImageResource(R.drawable.c1s);
                break;
            case TWO:
                buttonClue2.setImageResource(R.drawable.c2s);
                break;
            case THREE:
                buttonClue3.setImageResource(R.drawable.c3s);
                break;
            case FOUR:
                buttonClue4.setImageResource(R.drawable.c4s);
                break;
            case FIVE:
                buttonClue5.setImageResource(R.drawable.c5s);
                break;
            case RED:
                buttonClueRed.setImageResource(R.drawable.crs);
                break;
            case YELLOW:
                buttonClueYellow.setImageResource(R.drawable.cys);
                break;
            case BLUE:
                buttonClueBlue.setImageResource(R.drawable.cbs);
                break;
            case WHITE:
                buttonClueWhite.setImageResource(R.drawable.cws);
                break;
            case GREEN:
                buttonClueGreen.setImageResource(R.drawable.cgs);
                break;
            case MULTICOLOR:
                buttonClueMulticolor.setImageResource(R.drawable.cms);
                break;
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::paint()");
    }

    public void errorFeedback() {
        Log.v("HanabiTracker", "Entering TrackerActivity::errorFeedback()");

        //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 100, 200};
        v.vibrate(pattern, -1);

        // unselect any selected clues
        clueSelection = CLUE_SELECTION.NONE;
        currentSelectionMode = SELECTION_MODE.NONE;

        // unselect any selected cards
        for (int i = 0; i < 5; i++) {
            cardArrayList.get(i).setSelected(Boolean.FALSE);
        }

        paint();
        Log.v("HanabiTracker", "Exiting TrackerActivity::errorFeedback()");
    }

    // This function must be called at the beginning of every button action except a Card,
    // i.e., after a color clue, number clue, delete, or undo
    private void cleanupMulticardRainbow() {
        Log.v("HanabiTracker", "Entering TrackerActivity::cleanupMulticardRainbow()");
        for (Card card : cardArrayList) {
            if (card.getRainbowState() == Card.Rainbow.PENDING_NOT_RAINBOW) {
                card.setRainbowState(Card.Rainbow.IS_NOT_RAINBOW);
            }
            if (card.getDirtyFlag() == Boolean.TRUE) {
                card.setRainbowState(Card.Rainbow.POSSIBLE_RAINBOW);
                card.setDirtyFlag(Boolean.FALSE);
            }
        }
        Log.v("HanabiTracker", "Exiting TrackerActivity::cleanupMulticardRainbow()");
    }

}
