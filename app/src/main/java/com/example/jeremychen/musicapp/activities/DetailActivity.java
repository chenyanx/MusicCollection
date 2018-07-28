package com.example.jeremychen.musicapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.jeremychen.musicapp.DataLoaders.DataLoader;
import com.example.jeremychen.musicapp.DataLoaders.DataResponse;
import com.example.jeremychen.musicapp.DataLoaders.ImageLoader;
import com.example.jeremychen.musicapp.DataLoaders.ImageResponse;
import com.example.jeremychen.musicapp.R;
import com.example.jeremychen.musicapp.tools.TimeTransfer;
import com.example.jeremychen.musicapp.utils.Music;
import com.example.jeremychen.musicapp.utils.Player;

import java.util.List;
import java.util.Timer;

public class DetailActivity extends AppCompatActivity {

    private static final String CONTENT_PROCESS = "Loading....";
    private static final int PLAYING = 1001;
    private static final int NoPLAYING = 1002;
    private static final int SEEKBARUPDATE = 1003;
    private TextView textView_SongName;
    private TextView textView_Collection;
    private TextView textView_Artist;
    private TextView textView_ReleaseDate;
    private TextView textView_Region;
    private TextView textView_generic;
    private TextView textView_price;
    private TextView textView_collectionPrice;
    private TextView textView_TrackTime;
    private ImageButton ImageButton;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private Button PlayButton;
    private SeekBar seekBar;
    private Player player;
    private Thread mThread;
    private Handler handler;
    private int duration;
    private int position;


    class TimerTask extends java.util.TimerTask{

        @Override
        public void run() {
            if(player == null) {
                return;
            }
            if(player != null && seekBar.isPressed() == false) {
                if(handler != null) {
                    Message msg = new Message();
                    msg.what = SEEKBARUPDATE;
                    handler.sendMessage(msg);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(CONTENT_PROCESS);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

        // TODO: 2018/7/25 get the previous key postion
        Intent intent = getIntent();
        position = intent.getIntExtra("Position", 0);
        //Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        initUI();


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case PLAYING:
                        PlayButton.setText(R.string.Music_Pause);
                        PlayButton.setClickable(true);
                        seekBar.setVisibility(View.VISIBLE);
                        break;
                    case NoPLAYING:
                        PlayButton.setText(R.string.Music_Trailer);
                        PlayButton.setClickable(true);
                        seekBar.setProgress(0);
                        seekBar.setVisibility(View.INVISIBLE);
                        break;
                    case SEEKBARUPDATE:
                        int position = player.getCurrentPosition();
                        if(duration > 0){
                            long pos = seekBar.getMax() * position / duration;
                            seekBar.setProgress((int) pos);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        new DataLoader(this, new DataResponse() {
            @Override
            public void dataResponse(List<Music> content) {
                Music music = content.get(position);
                BindView(music);
            }
        }).execute();
    }

    private void initUI() {
        textView_SongName = (TextView) findViewById(R.id.text_view_SongName);
        textView_ReleaseDate = (TextView) findViewById(R.id.text_view_ReleaseDate);
        textView_Collection = (TextView) findViewById(R.id.text_view_SongCollection);
        textView_Artist = (TextView) findViewById(R.id.text_view_Artist);
        textView_Region = (TextView) findViewById(R.id.text_view_Region);
        textView_generic = (TextView) findViewById(R.id.text_view_Generic);
        textView_price = (TextView) findViewById(R.id.text_view_Price);
        textView_collectionPrice = (TextView) findViewById(R.id.text_view_collectionPrice);
        textView_TrackTime = (TextView) findViewById(R.id.text_view_TrackTime);
        imageView = (ImageView) findViewById(R.id.image_view_detail);
        PlayButton = (Button) findViewById(R.id.PlayButton);
        ImageButton = (ImageButton) findViewById(R.id.IamgeButton_AppStore);
        seekBar = (SeekBar) findViewById(R.id.music_progress);
    }

    private void BindView(final Music music)
    {
        textView_SongName.setText(getString(R.string.SongName) + "  " + music.getMusicName());
        textView_Artist.setText(getString(R.string.Artist) + "  " + music.getArtistName());
        textView_ReleaseDate.setText(getString(R.string.Release_Date) + "  " + music.getReleaseDate().substring(0,4));
        textView_Region.setText(getString(R.string.Region) + "  " + music.getCountry());
        textView_price.setText(getString(R.string.Price) + "  " + music.getTrackPrice() + " " + music.getCurrency());
        textView_collectionPrice.setText(getString(R.string.Collection_Price) + "  " + music.getCollectionPrice() + music.getCurrency());
        textView_generic.setText(getString(R.string.Generic) + "  " + music.getKindMusic());
        textView_Collection.setText(getString(R.string.Collection) + "  " + getString(R.string.Default_collection));
        TimeTransfer timeTransfer = new TimeTransfer(music.getTrackTime());
        textView_TrackTime.setText(getString(R.string.TrackTime) + "  " + timeTransfer.getTime());
        new ImageLoader(music.getPicUrl(), new ImageResponse() {
            @Override
            public void response(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                progressDialog.dismiss();
            }
        }, this,position, Manifest.permission.WRITE_EXTERNAL_STORAGE).execute();

        PlayButton.setOnClickListener(new View.OnClickListener() {
            // TODO: 2018/7/25 new a thread to play music
            @Override
            public void onClick(View v) {
                player = Player.getInstance();
                boolean indicator = player.inPlaying;
                final String url = music.getOnlineMusicUrl();
                final Timer timer = new Timer();
                if(indicator == false) {
                    PlayButton.setClickable(false);
                    player.inPlaying = true;
                    mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            player.play(url);
                            duration = player.getDuration();
                            if(handler != null) {
                                Message msg = new Message();
                                msg.what = PLAYING;
                                handler.sendMessage(msg);
                            }
                            timer.schedule(new TimerTask(),0,1000);
                        }
                    });
                    if(!mThread.isAlive()) {
                        mThread.start();
                    }
                }
                else {
                    PlayButton.setClickable(false);
                    player.inPlaying = false;
                    player.stop();
                    if(handler != null) {
                        Message msg = new Message();
                        msg.what = NoPLAYING;
                        handler.sendMessage(msg);
                    }
                    try {
                        mThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timer.cancel();
                }
            }
        });

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(music.getTrackViewUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null)
        {
            player.inPlaying = false;
            player.stop();
            player = null;
        }
        if(mThread != null && mThread.isAlive())
        {
            mThread.interrupt();
            mThread = null;
        }
    }
}
