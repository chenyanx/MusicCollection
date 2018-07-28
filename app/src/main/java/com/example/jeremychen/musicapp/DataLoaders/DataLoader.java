package com.example.jeremychen.musicapp.DataLoaders;

import android.content.Context;
import android.os.AsyncTask;
import com.example.jeremychen.musicapp.utils.Music;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremychen on 2018/7/25.
 */

public class DataLoader extends AsyncTask<String, Void, String> {

    private Context m_context;
    private DataResponse dataResponse;
    private InputStream inputStream;

    public DataLoader(Context context, DataResponse response) {
        this.m_context = context;
        this.dataResponse = response;
    }

    @Override
    protected String doInBackground(String... params) {
            try {
                inputStream = m_context.getResources().getAssets().open("JsonData.txt");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                String content = new String(buffer, "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream != null)
                {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String content = s;
        // TODO: 2018/7/25  data has been captured, next step needs to analysis and pass to callback interface to deliver data out!
        DataAnalysis(content);
    }


    private List<Music> DataAnalysis(String content)
    {
        List<Music> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = (JSONObject) jsonArray.get(i);
                Music music = new Music();
                String musicName = object.getString("trackCensoredName");
                String artistName = object.getString("artistName");
                String picURL = object.getString("artworkUrl100");
                String CollectionPrice = object.getString("collectionPrice");
                String trackPrice = object.getString("trackPrice");
                String releaseDate = object.getString("releaseDate");
                String onlineMusicUrl = object.getString("previewUrl");
                String country = object.getString("country");
                String currency = object.getString("currency");
                String musicCategory = object.getString("primaryGenreName");
                String trackViewUrl = object.getString("trackViewUrl");
                int trackTime = object.getInt("trackTimeMillis");
                music.setMusicName(musicName);
                music.setArtistName(artistName);
                music.setCollectionPrice(CollectionPrice);
                music.setPicUrl(picURL);
                music.setCountry(country);
                music.setCurrency(currency);
                music.setTrackPrice(trackPrice);
                music.setReleaseDate(releaseDate);
                music.setOnlineMusicUrl(onlineMusicUrl);
                music.setKindMusic(musicCategory);
                music.setTrackViewUrl(trackViewUrl);
                music.setTrackTime(trackTime);
                list.add(music);
            }
            dataResponse.dataResponse(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
