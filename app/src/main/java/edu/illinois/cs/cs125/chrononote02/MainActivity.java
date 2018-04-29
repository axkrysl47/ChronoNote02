package edu.illinois.cs.cs125.chrononote02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<String>();

    CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadArray(this);

        cardAdapter = new CardAdapter(this, list);

        ListView listView = findViewById(R.id.cardList);

        listView.setAdapter(cardAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createAndShowAlertDialog(position);
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.addNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Note added!", Toast.LENGTH_LONG).show();
                list.add("New Note");
                cardAdapter.notifyDataSetChanged();
                saveArray();
                onWindowFocusChanged(true);
            }
        });





//        list.add("Created by Alexander Krysl & Rithika Matta \n \n" +
//                 "Tap any note to start editing it, \n" +
//                 "Tap the plus button & tap enter to add a new note, \n" +
//                 "and long press a note to delete it. That's it!");
//
//        list.add("Tap here to edit your first note! ");
    }

    public List<String> getList() {
        return list;
    }

    private void createAndShowAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove note?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                list.remove(position);
                cardAdapter.notifyDataSetChanged();
                saveArray();
                Toast.makeText(MainActivity.this, "Note removed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();

        mEdit1.putInt("Status_size", list.size());

        for(int i=0;i<list.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }

        return mEdit1.commit();
    }

    public void loadArray(Context mContext) {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            list.add(mSharedPreference1.getString("Status_" + i, null));
        }

        if (size == 0) {
            list.add("Created by Alexander Krysl & Rithika Matta \n \n" +
                     "Tap any note to start editing it, \n" +
                     "Tap the plus button to add a new note, \n" +
                     "and long press a note to delete it. That's it!");

            list.add("Tap here to edit your first note! ");
        }
    }
}
