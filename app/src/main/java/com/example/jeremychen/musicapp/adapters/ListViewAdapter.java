package com.example.jeremychen.musicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.jeremychen.musicapp.activities.DetailActivity;
import com.example.jeremychen.musicapp.utils.Music;
import com.example.jeremychen.musicapp.R;

import java.util.List;

/**
 * Created by jeremychen on 2018/7/25.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context m_context;
    private List<Music> dataList;
    private static final String PASS_SIGNAL = "Position";

    public ListViewAdapter(Context context, List<Music> list) {
        this.m_context = context;
        this.dataList = list;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final viewHolder holder;
        if(convertView == null)
        {
            holder = new viewHolder();
            LayoutInflater inflater = LayoutInflater.from(m_context);
            convertView = inflater.inflate(R.layout.item_layout,null);
            holder.textView_name_singer = (TextView) convertView.findViewById(R.id.text_View_singerName);
            holder.textView_name_song = (TextView) convertView.findViewById(R.id.text_View_songName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            holder.textView_generic = (TextView) convertView.findViewById(R.id.text_view_generic);
            convertView.setTag(holder);
        }
        else
        {
            holder = (viewHolder) convertView.getTag();
        }
        holder.textView_name_singer.setText(dataList.get(position).getArtistName());
        holder.textView_name_song.setText(dataList.get(position).getMusicName());
        holder.textView_generic.setText(dataList.get(position).getKindMusic() + " . " + dataList.get(position).getReleaseDate().subSequence(0,4) );
        Glide.with(m_context).load(dataList.get(position).getPicUrl()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(m_context, DetailActivity.class);
                intent.putExtra(PASS_SIGNAL, position);
                m_context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class viewHolder
    {
        TextView textView_name_singer;
        TextView textView_name_song;
        TextView textView_generic;
        ImageView imageView;
    }
}
