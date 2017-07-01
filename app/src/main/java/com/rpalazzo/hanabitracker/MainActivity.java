/* Copyright 2016 Robert Palazzo <rpalazzo@gmail.com>
 * Proprietary and confidential
 * Copying of this file without express written consent is prohibited.
 */

package com.rpalazzo.hanabitracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;

public class MainActivity extends AppCompatActivity {

    private MultiStateToggleButton mstbNoCards = null;
    private MultiStateToggleButton mstbMulticolorMode = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mstbNoCards = (MultiStateToggleButton) this.findViewById(R.id.mstb_no_cards);
        mstbMulticolorMode = (MultiStateToggleButton) this.findViewById(R.id.mstb_multicolor_mode);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Integer default_value = 0;
        Integer noCards = sharedPref.getInt(getString(R.string.sharedprefs_no_cards), default_value);
        mstbNoCards.setValue(noCards);
        Integer multiMode = sharedPref.getInt(getString(R.string.sharedprefs_multicolor_mode), default_value);
        mstbMulticolorMode.setValue(multiMode);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAboutButton(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onStartButton(View view) {

        Log.v("MainActivity","Entering");

        mstbNoCards = (MultiStateToggleButton) this.findViewById(R.id.mstb_no_cards);
        int nCards = mstbNoCards.getValue();

        mstbMulticolorMode = (MultiStateToggleButton) this.findViewById(R.id.mstb_multicolor_mode);
        int nMulticolorMode = mstbMulticolorMode.getValue();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.sharedprefs_no_cards), nCards);
        editor.putInt(getString(R.string.sharedprefs_multicolor_mode), nMulticolorMode);
        editor.commit();

        Intent intent = new Intent(this, TrackerActivity.class);
        intent.putExtra("nCards", nCards);
        intent.putExtra("nMulticolorMode", nMulticolorMode);
        startActivity(intent);

        Log.v("MainActivity","Exiting");
    }

    public void onSettingsButton (View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
