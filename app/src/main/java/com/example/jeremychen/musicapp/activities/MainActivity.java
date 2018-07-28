package com.example.jeremychen.musicapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.jeremychen.musicapp.DataLoaders.DataLoader;
import com.example.jeremychen.musicapp.DataLoaders.DataResponse;
import com.example.jeremychen.musicapp.R;
import com.example.jeremychen.musicapp.adapters.ListViewAdapter;
import com.example.jeremychen.musicapp.utils.Music;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

        initUI();
        new DataLoader(this, new DataResponse() {
            @Override
            public void dataResponse(List<Music> content) {
                listView.setAdapter(new ListViewAdapter(MainActivity.this, content));
            }
        }).execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initUI() {
        listView = (ListView) findViewById(R.id.list_view);
    }
}
