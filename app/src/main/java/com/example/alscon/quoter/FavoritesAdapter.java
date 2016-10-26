package com.example.alscon.quoter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alscon.readtxt.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Alscon on 15.10.2016.
 */

public class FavoritesAdapter extends BaseAdapter {
    ArrayList<TextFavoriteQuoter> favoriteQoter;
    LayoutInflater layoutInflater;
    Context context;
    Typeface typeface4;


    public FavoritesAdapter(Context context, ArrayList<TextFavoriteQuoter> favoriteQoter) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.favoriteQoter = favoriteQoter;
    }

    @Override
    public int getCount() {
        return favoriteQoter.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteQoter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        final TextFavoriteQuoter favoriteQuoter = getFavoriteAdapter(position);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.favotite_news, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) view.findViewById(R.id.favorite_news_text);
            viewHolder.delete = (ImageView) view.findViewById(R.id.favorite_news_image_delete);
            fonts(viewHolder.mTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(favoriteQuoter.favoriteQuoter);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.favorite_news_image_delete:
                        String remove = favoriteQuoter.favoriteQuoter;
                        removeLineFromFile("/sdcard/Favorites.txt", remove);
                        refreshEvents(favoriteQoter, remove);
                        favoriteQoter.remove(favoriteQuoter);
                        break;
                }
            }
        });

        return view;
    }

    TextFavoriteQuoter getFavoriteAdapter(int position) {
        return ((TextFavoriteQuoter) getItem(position));
    }

    private void fonts(TextView favoriteText) {
        typeface4 = Typeface.createFromAsset(context.getAssets(), "open-sans.light.ttf");
        favoriteText.setTypeface(typeface4);
    }

    public void refreshEvents(ArrayList<TextFavoriteQuoter> favoriteQoter, String element) {
        this.favoriteQoter.remove(element);
        notifyDataSetChanged();
    }

    public void removeLineFromFile(String file, String lineToRemove) {

        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TextFavoriteQuoter item);
    }

    static class ViewHolder {
        TextView mTextView;
        ImageView delete;

    }

}
