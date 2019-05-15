package com.example.crosswordchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private char[] chars;

    public GridAdapter(Context context, char[] grid){
        this.mContext = context;
        this.chars = grid;
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
        return chars.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return chars[position];

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_char, null);
        }

        final TextView charTextView = (TextView)convertView.findViewById(R.id.textview_char);
        charTextView.setText(String.valueOf(chars[position]));
        return convertView;
    }

}
