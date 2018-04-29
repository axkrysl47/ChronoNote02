package edu.illinois.cs.cs125.chrononote02;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends ArrayAdapter<String> {

    private MainActivity activity;

    public CardAdapter(MainActivity context, List<String> list) {
        super(context, 0, list);
        activity = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        String cardContent = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cardlayout, parent, false);
        }

        final EditText cardText = convertView.findViewById(R.id.content);

        cardText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                activity.getList().set(position, cardText.getText().toString());
                activity.saveArray();
                return false;
            }
        });

        cardText.setText(cardContent);

        return convertView;
    }
}
