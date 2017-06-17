package com.daksh.drivercrunch;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.daksh.drivercrunch.databinding.ActivityMapsBinding;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

import static com.daksh.drivercrunch.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2123;
    private static final String TAG = "ASDASD";
    ActivityMapsBinding b;

    private GoogleMap mMap;

    private static final int MAX_RADIUS_KM = 200000;
    private Place searchedPlace;
    private Circle mapCircle;

    private Prefs prefs;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = new Prefs(this);

        b = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        initSeekBar();

        initListeners();

        b.textPlace.setText(prefs.getLocationName());
        b.textRadius.setText((prefs.getRadius() / 1000) + " KM");
    }

    private void initSeekBar() {
        b.seekBar.setMax(MAX_RADIUS_KM);
        b.seekBar.setProgress((int) prefs.getRadius());
    }

    private void initListeners() {
        b.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress,
                    final boolean fromUser) {
                b.textRadius.setText((progress / 1000) + " KM");

                if (fromUser) {
                    if (mapCircle != null) {
                        mapCircle.setRadius(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
            }
        });

        b.textPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startPlacesSearch();
            }
        });

        b.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO actually submit to the database

                final AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this)
                        .setMessage("Submitting...")
                        .setCancelable(false)
                        .create();
                alertDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prefs.setLocationSelected(true);

                        if (place != null) {
                            prefs.setLocation(place.getName().toString(), place.getLatLng(), mapCircle.getRadius());
                        }

                        alertDialog.dismiss();
                        MapsActivity.this.finish();
                        startActivity(new Intent(MapsActivity.this, PredictionsActivity.class));
                    }
                }, 3000);
            }
        });
    }

    private void startPlacesSearch() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            Toast.makeText(this, "Play Services Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(this, data);
                searchedPlace = place;
                b.textPlace.setText(place.getName());
                mapCircle.setCenter(place.getLatLng());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 8.5F));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                // TODO
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(prefs.getLatLng(), 8.5F));
        mapCircle = mMap.addCircle(new CircleOptions()
                .center(prefs.getLatLng())
                .radius(prefs.getRadius())
                .fillColor(Color.argb(128, 255, 0, 0)));
    }
}
