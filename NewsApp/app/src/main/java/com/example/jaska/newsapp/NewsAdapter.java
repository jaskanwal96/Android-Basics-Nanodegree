package com.example.jaska.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by jaska on 22-Dec-17.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final News current = getItem(position);
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView sectionName = (TextView)listItemView.findViewById(R.id.section);
        sectionName.setText(current.getSectionName());

        TextView newsTitle = (TextView)listItemView.findViewById(R.id.title);
        newsTitle.setText(current.getNewsTitle());

        String dateTime = current.getDate();
        StringBuffer sB = new StringBuffer(dateTime);
        TextView date = (TextView)listItemView.findViewById(R.id.date);
        date.setText(sB.substring(0, 10));

        TextView time = (TextView)listItemView.findViewById(R.id.author);
        time.setText(current.getAuthor());


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(current.getUrl()));
                getContext().startActivity(i);
            }
        });
        return listItemView;
    }
}
