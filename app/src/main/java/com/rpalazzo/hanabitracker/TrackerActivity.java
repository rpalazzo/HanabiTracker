package com.rpalazzo.hanabitracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;


import java.util.ArrayList;

public class TrackerActivity extends AppCompatActivity {

    private int nCards;
    private int MulticolorMode;
    private ArrayList<com.rpalazzo.hanabitracker.Card> cardArrayList = new ArrayList<com.rpalazzo.hanabitracker.Card>();

    private LinearLayout ll;
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


    public enum SELECTION {
        NONE, CARD, CLUE
    }
    private SELECTION currentSelection;

    public enum CLUE_SELECTION {
        NONE, ONE, TWO, THREE, FOUR, FIVE, RED, YELLOW, BLUE, WHITE, GREEN, MULTICOLOR
    }
    private CLUE_SELECTION clueSelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        Log.v("TrackerActivity","Entering");


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */

        // Layout 4 or 5 cards
        nCards = getIntent().getIntExtra("nCards", 0);
        if (nCards == 0) {
            tl = (TableLayout) findViewById(R.id.tableLayout1);
            tl.setColumnCollapsed(0, true);  //TODO: if aging from left to right, then Collapse 4
        }

        // Hide or display the multicolor clue button
        MulticolorMode = getIntent().getIntExtra("nMulticolorMode",0);
        if (MulticolorMode != 1) {
            //ImageButton multicolorButton = new ImageButton(getApplicationContext());
            multicolorButton = (ImageButton) findViewById(R.id.button_clueMulticolor);
            multicolorButton.setVisibility(View.GONE);
        }

        // Populate cardArrayList with unknown/unknown cards
        for (int i = 0; i < 5; i++) {
            com.rpalazzo.hanabitracker.Card c = new com.rpalazzo.hanabitracker.Card();
            c.setRank(0);
            c.setSuit(com.rpalazzo.hanabitracker.Card.Color.UNKNOWN);
            cardArrayList.add(c);
        }

        currentSelection = SELECTION.NONE;
        clueSelection = CLUE_SELECTION.NONE;

        buttonCard1 = (ImageButton)findViewById(R.id.button_card1);
        buttonCard2 = (ImageButton)findViewById(R.id.button_card2);
        buttonCard3 = (ImageButton)findViewById(R.id.button_card3);
        buttonCard4 = (ImageButton)findViewById(R.id.button_card4);
        buttonCard5 = (ImageButton)findViewById(R.id.button_card5);
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
        paint();


        Log.v("TrackerActivity","Exiting");
    }

    public void onDel1(View view) { onDel(0); }
    public void onDel2(View view) { onDel(1); }
    public void onDel3(View view) { onDel(2); }
    public void onDel4(View view) { onDel(3); }
    public void onDel5(View view) { onDel(4); }

    private void onDel(int index) {
        cardArrayList.remove(index);

        com.rpalazzo.hanabitracker.Card c = new com.rpalazzo.hanabitracker.Card();
        c.setRank(0);
        c.setSuit(com.rpalazzo.hanabitracker.Card.Color.UNKNOWN);
        cardArrayList.add(c);

        clueSelection = CLUE_SELECTION.NONE;
        currentSelection = SELECTION.NONE;

        paint();
    }

    public void onCard1(View view) { onCard(0); }
    public void onCard2(View view) { onCard(1); }
    public void onCard3(View view) { onCard(2); }
    public void onCard4(View view) { onCard(3); }
    public void onCard5(View view) { onCard(4); }

    private void onCard(int index) {
        if (currentSelection == SELECTION.NONE || currentSelection == SELECTION.CARD) {
            if (cardArrayList.get(index).getSelected() == Boolean.TRUE)
            {
                cardArrayList.get(index).setSelected(Boolean.FALSE);

                //a card was unselected, if any cards are still currentSelection then SELECTION.CARD
                currentSelection = SELECTION.NONE;
                for (int j = 0; j < 5; j++) {
                    if (cardArrayList.get(j).getSelected() == Boolean.TRUE) {
                        currentSelection = SELECTION.CARD;
                    }
                }
            }
            else {
                cardArrayList.get(index).setSelected(Boolean.TRUE);
                currentSelection = SELECTION.CARD;
            }
        }
        else { // currentSelection == SELECTION.CLUE
            switch (clueSelection){
                case ONE:
                    cardArrayList.get(index).setRank(1);
                    break;
                case TWO:
                    cardArrayList.get(index).setRank(2);
                    break;
                case THREE:
                    cardArrayList.get(index).setRank(3);
                    break;
                case FOUR:
                    cardArrayList.get(index).setRank(4);
                    break;
                case FIVE:
                    cardArrayList.get(index).setRank(5);
                    break;
                case RED:
                    cardArrayList.get(index).setSuit(com.rpalazzo.hanabitracker.Card.Color.RED);
                    break;
                case YELLOW:
                    cardArrayList.get(index).setSuit(com.rpalazzo.hanabitracker.Card.Color.YELLOW);
                    break;
                case BLUE:
                    cardArrayList.get(index).setSuit(com.rpalazzo.hanabitracker.Card.Color.BLUE);
                    break;
                case WHITE:
                    cardArrayList.get(index).setSuit(com.rpalazzo.hanabitracker.Card.Color.WHITE);
                    break;
                case GREEN:
                    cardArrayList.get(index).setSuit(com.rpalazzo.hanabitracker.Card.Color.GREEN);
                    break;
                case MULTICOLOR:
                    cardArrayList.get(index).setSuit(com.rpalazzo.hanabitracker.Card.Color.MULTICOLOR);
                    break;
            }
         }
        paint();
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

        // if the currently selected clue is selected again; deselect it
        if (clueSelection == clue) {
            clueSelection = CLUE_SELECTION.NONE;
            currentSelection = SELECTION.NONE;
        }

        // else if no clue or different clue selected, select that clue
        else if (currentSelection == SELECTION.NONE || currentSelection == SELECTION.CLUE) {
            currentSelection = SELECTION.CLUE;
            clueSelection = clue;
        }

        // else assign this clue to all currrently selected cards
        else { // SELECTION.CARD
            for (int i = 0; i < 5; i++) {
                if (cardArrayList.get(i).getSelected() == Boolean.TRUE) {
                    if (clue == CLUE_SELECTION.RED ||
                            clue == CLUE_SELECTION.BLUE ||
                            clue == CLUE_SELECTION.YELLOW ||
                            clue == CLUE_SELECTION.WHITE ||
                            clue == CLUE_SELECTION.GREEN ||
                            clue == CLUE_SELECTION.MULTICOLOR) {
                        switch (clue) {
                            case RED:
                                cardArrayList.get(i).setSuit(com.rpalazzo.hanabitracker.Card.Color.RED);
                                break;
                            case YELLOW:
                                cardArrayList.get(i).setSuit(com.rpalazzo.hanabitracker.Card.Color.YELLOW);
                                break;
                            case BLUE:
                                cardArrayList.get(i).setSuit(com.rpalazzo.hanabitracker.Card.Color.BLUE);
                                break;
                            case WHITE:
                                cardArrayList.get(i).setSuit(com.rpalazzo.hanabitracker.Card.Color.WHITE);
                                break;
                            case GREEN:
                                cardArrayList.get(i).setSuit(com.rpalazzo.hanabitracker.Card.Color.GREEN);
                                break;
                            case MULTICOLOR:
                                cardArrayList.get(i).setSuit(com.rpalazzo.hanabitracker.Card.Color.MULTICOLOR);
                                break;
                        }
                    }
                    else{
                        switch (clue) {
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
                }
                cardArrayList.get(i).setSelected(Boolean.FALSE);
            }
            currentSelection = SELECTION.NONE;
        }
        paint();
    }

    public void paint() {
        buttonCard1.setImageResource(cardArrayList.get(0).getImageReference());
        buttonCard2.setImageResource(cardArrayList.get(1).getImageReference());
        buttonCard3.setImageResource(cardArrayList.get(2).getImageReference());
        buttonCard4.setImageResource(cardArrayList.get(3).getImageReference());
        buttonCard5.setImageResource(cardArrayList.get(4).getImageReference());

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
    }
}
