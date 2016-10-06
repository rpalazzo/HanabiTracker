package com.rpalazzo.hanabitracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

public class AnnotationActivity extends AppCompatActivity {

    private Card annotationCard = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        annotationCard =  (Card) getIntent().getSerializableExtra("card");
        annotationCard.setRank(5);
        annotationCard.setSuit(Card.Color.RED);

        MultiStateToggleButton button = (MultiStateToggleButton) this.findViewById(R.id.mstb_multi_id);
        button.setOnValueChangedListener(new MultiStateToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                Log.d("mstb", "Position: " + position);
            }
        });




    }

    public void onOkButton (View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", annotationCard);
        setResult(0, resultIntent);
        finish();
    }

    public void onCancelButton (View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", annotationCard);
        setResult(1, resultIntent);
        finish();
    }

}
