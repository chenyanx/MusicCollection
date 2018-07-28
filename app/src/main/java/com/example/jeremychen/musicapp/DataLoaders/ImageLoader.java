package com.example.jeremychen.musicapp.DataLoaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.example.jeremychen.musicapp.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jeremychen on 2018/7/26.
 */

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private String m_url;
    private ImageResponse response;
    private Context m_context;
    private FileOutputStream fileOutputStream;
    private File bitmapFile;
    private int fileTag;
    private String permission;

    public ImageLoader(String url, ImageResponse response, Context context, int position, String permission) {
        this.m_url = url;
        this.response = response;
        this.m_context = context;
        this.fileTag = position;
        this.permission = permission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected Bitmap doInBackground(String... params) {

        File fileDir = new File(m_context.getExternalCacheDir() + File.separator + m_context.getString(R.string.FileName));
        if(!fileDir.exists())
        {
            fileDir.mkdir();
        }
        String fileName = fileTag + ".jpg";
        bitmapFile = new File(fileDir + "/" + fileName);
        if(bitmapFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
            return bitmap;
        }
        else {
            InputStream inputStream = null;
            try {
                URL url = new URL(m_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                int code = connection.getResponseCode();
                inputStream = null;
                Bitmap bitmap = null;
                if(code == 200)
                {
                    inputStream = connection.getInputStream();
                    byte[] b = new byte[1024];
                    int len;
                    if(m_context.checkSelfPermission(permission) != m_context.getPackageManager().PERMISSION_GRANTED)
                    {
                        //Log.e("TAG", "no permission，using internet!");
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    }
                    else {
                        //Log.e("TAG", "had permission，local read!");
                        fileOutputStream = new FileOutputStream(bitmapFile);
                        while ((len = inputStream.read(b)) != -1) {
                            fileOutputStream.write(b,0,len);
                        }
                        bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
                    }
                }
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        response.response(bitmap);
    }
}
