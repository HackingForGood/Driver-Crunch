package com.daksh.drivercrunch;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daksh.drivercrunch.databinding.ActivityPredictionsBinding;

import java.util.ArrayList;
import java.util.List;

public class PredictionsActivity extends AppCompatActivity {

    private Prefs prefs;

    private ActivityPredictionsBinding b;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = new Prefs(this);

        b = DataBindingUtil.setContentView(this, R.layout.activity_predictions);

        initList();

        b.list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        b.list.setAdapter(new RecyclerView.Adapter<EntryViewHolder>() {

            @Override
            public EntryViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.list_item, parent, false);

                return new EntryViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(final EntryViewHolder holder, final int position) {
                holder.textName.setText(list.get(position));
                holder.buttonDirections.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + list.get(position) + "&mode=d");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initList() {
        list = new ArrayList<>();
        list.add("Providence Rhode Island");
        list.add("Dorchester");
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_predictions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                prefs.setLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.changeLocation:
                startActivity(new Intent(this, MapsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
