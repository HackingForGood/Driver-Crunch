package com.daksh.drivercrunch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by daksh on 17-Jun-17.
 */

public class EntryViewHolder extends RecyclerView.ViewHolder {

    TextView textName;
    Button buttonDirections;

    EntryViewHolder(final View view) {
        super(view);
        textName = (TextView) view.findViewById(R.id.textName);
        buttonDirections = (Button) view.findViewById(R.id.buttonDirections);
    }
}
