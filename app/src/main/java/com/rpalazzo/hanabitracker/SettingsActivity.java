/* Copyright 2016 Robert Palazzo <rpalazzo@gmail.com>
 * Proprietary and confidential
 * Copying of this file without express written consent is prohibited.
 */

package com.rpalazzo.hanabitracker;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        // Negative Info
        Preference negativeinfo = findPreference("negativeinfo_key");
        negativeinfo.setSummary(negativeinfo.getSharedPreferences().
                getString("negativeinfo_key", getString(R.string.negativeinfo_default)));

        negativeinfo.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });

        // Age Direction
        Preference agedirection = findPreference("agedirection_key");
        agedirection.setSummary(agedirection.getSharedPreferences().
                getString("agedirection_key", getString(R.string.agedirection_default)));

        agedirection.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });

        // Negative Clue
        Preference negativeclue = findPreference("negativeclue_key");
        negativeclue.setSummary(negativeclue.getSharedPreferences().
                getString("negativeclue_key", getString(R.string.negativeclue_default)));

        negativeclue.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });

        // Background Color
        Preference backgroundcolor = findPreference("backgroundcolor_key");
        backgroundcolor.setSummary(backgroundcolor.getSharedPreferences().
                getString("backgroundcolor_key", getString(R.string.backgroundcolor_default)));

        backgroundcolor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });
    }
}
