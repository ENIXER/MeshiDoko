/**
 * 
 */
package com.indecisive.meshidoko.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.indecisive.meshidoko.models.Genre;

/**
 * @author KOJISUKE
 *
 */
public class GenreAdapter extends ArrayAdapter<Genre> {

    private Context context;

    public GenreAdapter(Context context, int textViewResourceId, ArrayList<Genre> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        Genre item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            TextView itemView = (TextView) view.findViewById(android.R.id.text1);
            if (itemView != null) {
                // do whatever you want with your string and long
                itemView.setText(String.format("%s", item.getName()));
            }
         }

        return view;
    }
}
