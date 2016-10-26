package com.example.alscon.quoter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alscon.readtxt.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 112;
    TextView tvCitation, tb;
    Button btnNext, btnPrevious, addCitation;
    ArrayList<String> citationList;
    ArrayList<Integer> historyStack;
    ArrayList<String> quoter;
    ArrayList<String> favoriteQuoter;
    int currentPosition;
    Typeface mtypeface, buttonTypeface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        check();

        tvCitation = (TextView) findViewById(R.id.citation);
        btnNext = (Button) findViewById(R.id.next);
        btnPrevious = (Button) findViewById(R.id.previous);
        addCitation = (Button) findViewById(R.id.add_citation);
        tb = (TextView) findViewById(R.id.toolbar_title);
        citationList = new ArrayList<>();

        fonts();

        historyStack = new ArrayList<>();
        favoriteQuoter = new ArrayList<>();
        File myFile = new File("/sdcard/Quoter.txt");
        if (!myFile.exists()) {
            addCitation();
            newFile();
        }

        readTxtFileFromCdCard();
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        addCitation.setOnClickListener(this);
        btnNext.performClick();
        toolbar();
    }


    private void fonts() {
        mtypeface = Typeface.createFromAsset(getAssets(), "open-sans.light.ttf");
        buttonTypeface = Typeface.createFromAsset(getAssets(), "open-sans.extrabold.ttf");
        tvCitation.setTypeface(mtypeface);
        btnNext.setTypeface(buttonTypeface);
        btnPrevious.setTypeface(buttonTypeface);
        addCitation.setTypeface(buttonTypeface);
        tb.setTypeface(mtypeface);

    }

    private void toolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    private void addCitation() {
        quoter = new ArrayList<>();
        quoter.add("After all is said and done, more is said than done");
        quoter.add("\n");
        quoter.add("Persuasion is often more effectual than force.");
        quoter.add("\n");
        quoter.add("United we stand, divided we fall");
        quoter.add("\n");
        quoter.add("When I was born, I was so surprised I didn't talk for a year and a half.");
        quoter.add("\n");
        quoter.add("If you're not failing every now and again, it's a sign you're not doing anything very innovative.");
        quoter.add("\n");
        quoter.add("I'm not afraid to die.  I just don't want to be there when it happens.");
        quoter.add("\n");
        quoter.add("Time is nature's way of keeping everything from happening at once.");
        quoter.add("\n");
        quoter.add("The secret of life is not to do what you like, but to like what you do.");
        quoter.add("\n");
        quoter.add("Love is not about who you live with. It's about who you can't live without.");
        quoter.add("\n");
        quoter.add("A real friend is someone who walks in when the rest of the world walks out");
        quoter.add("\n");
        quoter.add("Opportunity may knock only once, but temptation leans on the doorbell.");
        quoter.add("\n");
    }


    private Integer randomChitation() {
        int random = (int) (Math.random() * citationList.size());
        if (random == currentPosition) {
            random = (int) (Math.random() * citationList.size());
        }
        return random;
    }

    private void addFileOnSdCard() {
        try {
            File myFile = new File("/sdcard/Favorites.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(tvCitation.getText());
            myOutWriter.append("\n");
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(),
                    "quote added to favorites",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void newFile() {
        try {
            File myFile = new File("/sdcard/Quoter.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            for (int i = 0; i < quoter.size(); i++) {
                myOutWriter.append(quoter.get(i));
            }
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkQuoter() {
        for (String quoter : favoriteQuoter) {
            if (quoter.equals(tvCitation.getText())) {
                Toast.makeText(MainActivity.this, "This quotation is already in your list", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;

    }

    private void readFavoriteFile() {
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File("/sdcard/Favorites.txt");

        if (file.exists()) {


            try {
                FileInputStream fileSdCard = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fileSdCard));
                String line = "";

                StringBuilder text = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    favoriteQuoter.add(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {

            }


        } else {
            return;
        }
    }


    private void readTxtFileFromCdCard() {
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File("/sdcard/Quoter.txt");

        if (file.exists()) {


            try {
                FileInputStream fileSdCard = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fileSdCard));
                String line = "";

                StringBuilder text = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    citationList.add(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {

            }


        } else {
            tvCitation.setText("Sorry file doesn't exist");
        }
    }
//    private void check(){
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//            } else {
//
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
//                );
//            }
//        }
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                if (historyStack.size() > 1) {
                    historyStack.remove(historyStack.size() - 1);
                    currentPosition = historyStack.get(historyStack.size() - 1);
                }
                break;
            case R.id.next:
                currentPosition = randomChitation();
                historyStack.add(currentPosition);
                break;
            case R.id.add_citation:
                readFavoriteFile();
                if (checkQuoter()) {
                    addFileOnSdCard();
                }
                break;
        }
        tvCitation.setText(citationList.get(currentPosition));

    }
}