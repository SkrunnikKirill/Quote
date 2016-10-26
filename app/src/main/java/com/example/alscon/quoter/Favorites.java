package com.example.alscon.quoter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alscon.readtxt.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Alscon on 14.10.2016.
 */

public class Favorites extends Activity {
    ArrayList<String> citationFavorite;
    TextView toolbarname;
    ListView listViewQuoter;
    ArrayAdapter<String> adapter;
    ArrayList<TextFavoriteQuoter> favoritesQuter;
    Typeface mTypefacetext;
    Context context;
    private FavoritesAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_activity);
        listViewQuoter = (ListView) findViewById(R.id.favorite_list_view);
        toolbarname = (TextView) findViewById(R.id.toolbar_title2);
        citationFavorite = new ArrayList<>();
        context = Favorites.this;
        toolbar();
        readFavoriteFileFromCdCard();
        adapter();
        font();
    }

    private void font() {
        mTypefacetext = Typeface.createFromAsset(getAssets(), "open-sans.light.ttf");
        toolbarname.setTypeface(mTypefacetext);
    }

    private void adapter() {
        favoritesQuter = new ArrayList<>();
        favoriteAdapter = new FavoritesAdapter(context, favoritesQuter);
        for (int i = 0; i < citationFavorite.size(); i++) {
            favoritesQuter.add(new TextFavoriteQuoter(citationFavorite.get(i)));
        }
        listViewQuoter.setAdapter(favoriteAdapter);
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fay);
//        toolbar.setTitle(R.string.info2);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void readFavoriteFileFromCdCard() {
        File sdcard = Environment.getExternalStorageDirectory();

        //Get the text file
        File file = new File("/sdcard/Favorites.txt");

        if (file.exists()) {

            //Read text from file
            StringBuilder text = new StringBuilder();

            try {
                FileInputStream fileSdCard = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fileSdCard));
                String line = "";


                while ((line = br.readLine()) != null) {
                    text.append(line);
                    citationFavorite.add(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }

//Set the text

        } else {
            Toast.makeText(Favorites.this, "Sorry file doesn't exist", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}