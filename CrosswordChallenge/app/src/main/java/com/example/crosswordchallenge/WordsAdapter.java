package com.example.crosswordchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WordsAdapter extends BaseAdapter {

    private final Context mContext;
    private String[] words;

    public WordsAdapter(Context context, String[] grid){
        this.mContext = context;
        this.words = grid;
        //convertGridToArray(grid);
    }

    /*private void convertGridToArray(char[] grid){
        int count = 0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                chars[count] = grid[i][j];
                count++;
            }
        }
    }*/
    @Override
    public int getCount() {
        return words.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return words[position];

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_word, null);
        }

        final TextView wordTextView = (TextView)convertView.findViewById(R.id.textview_word);
        wordTextView.setText(words[position]);
        if (Words.foundWords.contains(Words.words[position])){
            //wordTextView.setTextColor(mContext.getResources().getColor(R.color.wordFound));
            wordTextView.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            wordTextView.setTextAppearance(mContext, R.style.boldText);
        }

        return convertView;
    }

}