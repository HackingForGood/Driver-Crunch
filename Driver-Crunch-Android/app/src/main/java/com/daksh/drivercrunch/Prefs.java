package com.daksh.drivercrunch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by daksh on 17-Jun-17.
 */

class Prefs {

    private static final String NAME = "NAME";
    private static final String LAT = "LAT";
    private static final String LNG = "LNG";
    private static final String RADIUS = "RADIUS";

    private final Activity activity;
    private final SharedPreferences prefs;

    private static final String LOGGED_IN = "LOGGED_IN";
    private static final String LOCATION_SELECTED = "LOCATION_SELECTED";

    Prefs(final Activity activity) {
        this.activity = activity;
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    boolean isLoggedIn() {
        return prefs.getBoolean(LOGGED_IN, false);
    }

    void setLoggedIn(final boolean loggedIn) {
        prefs.edit().putBoolean(LOGGED_IN, loggedIn).apply();
    }

    boolean isLocationSelected() {
        return prefs.getBoolean(LOCATION_SELECTED, false);
    }

    void setLocationSelected(final boolean locationSelected) {
        prefs.edit().putBoolean(LOCATION_SELECTED, locationSelected).apply();
    }

    void setLocation(final String name, final LatLng latLng, final double radius) {
        prefs.edit().putString(NAME, name).apply();
        prefs.edit().putString(LAT, String.valueOf(latLng.latitude)).apply();
        prefs.edit().putString(LNG, String.valueOf(latLng.longitude)).apply();
        prefs.edit().putString(RADIUS, String.valueOf(radius)).apply();
    }

    String getLocationName() {
        return prefs.getString(NAME, "Boston");
    }

    LatLng getLatLng() {
        return new LatLng(Double.parseDouble(prefs.getString(LAT, "42.31")),
                Double.parseDouble(prefs.getString(LNG, "-71.19")));
    }

    double getRadius() {
        return Double.parseDouble(prefs.getString(RADIUS, String.valueOf(50000)));
    }
}
