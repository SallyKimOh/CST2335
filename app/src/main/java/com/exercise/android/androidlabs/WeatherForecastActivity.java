package com.exercise.android.androidlabs;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherForecastActivity extends AppCompatActivity {
    protected static final String Activity_Name = "WeatherForecastActivity";
    private ProgressBar progressBar;
    private int progressStatus = 0;
    TextView txtCurrTemp;
    TextView txtMinTemp;
    TextView txtMaxTemp;
    ImageView imgWeather;
    String max="";
    String min="";
    String value="";
    String icon = "";

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        txtCurrTemp = (TextView) findViewById(R.id.weather_txtCurrTem);
        txtMinTemp = (TextView) findViewById(R.id.weather_txtMinTem);
        txtMaxTemp = (TextView) findViewById(R.id.weather_txtMaxTem);
        imgWeather = (ImageView) findViewById(R.id.weather_img);

        Log.i(Activity_Name, "In onCreate()");

        progressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        progressBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();








        new ForecastQuery().execute();

    }

    private class ForecastQuery extends AsyncTask<String,String,String> {

        private final String ns = null;
//        URL url;
//        InputStream is = null;
//        Bitmap bitmap = null;
//        String urlStr = "http://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1";
        String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        String imageUrl = "";
        Bitmap bm;


        /**
         * 쓰레드 작업
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            Log.i(Activity_Name, "In doInBackground()");
            //           Log.i(Activity_Name,strings[0]+":"+strings[1]);

            URL url;
            HttpURLConnection urlConnection = null;
            XmlPullParserFactory factory;

            try {
                url = new URL(urlStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                Log.i(Activity_Name,"AAAAAAA");

                InputStream in = urlConnection.getInputStream();
                Log.i(Activity_Name,"BBBBBBBBBBB");

                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();

                Log.i(Activity_Name,"parser==>"+parser);

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name;
                    //                  Log.i(Activity_Name,"name==>"+parser.getName());
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equals("temperature")) {
                                max = parser.getAttributeValue(null,"max");
                                publishProgress("25");
                                min = parser.getAttributeValue(null,"min");
                                publishProgress("50");
                                value = parser.getAttributeValue(null,"value");
                                publishProgress("75");
                                Log.i(Activity_Name,"max==>"+max);
//                                publishProgress();
                            }

                            if (name.equals("weather")) {
                                icon = parser.getAttributeValue(null,"icon");
                                Log.i(Activity_Name,"icon==>"+icon);
                            }

                            break;
                    }
                    eventType = parser.next();
                }

                imageUrl = "http://openweathermap.org/img/w/"+icon+".png";

                Log.i(Activity_Name,"exist:"+fileExistance(icon+".png"));

                if (fileExistance(icon+".png")) {

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(icon + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bm = BitmapFactory.decodeStream(fis);
                    Log.i(Activity_Name,"Saved local:"+icon+".png");

                } else {
                    Bitmap image  = HttpUtils.getImage(imageUrl);
                    publishProgress("100");
                    FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    bm = image;
                    Log.i(Activity_Name,"Download Image:"+icon+".png");

                }

                return max;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }

        protected void onProgressUpdate (Integer ... values) {
            super.onProgressUpdate(String.valueOf(values));
            progressBar.setProgress(values[0]);
        }


        protected void onPostExecute (String s) {

            super.onPostExecute(s);
            Log.i("xml",s);
            txtCurrTemp.setText(value);
            txtMinTemp.setText(min);
            txtMaxTemp.setText(max);

            imgWeather.setImageBitmap(bm);

            progressBar.setVisibility(View.INVISIBLE);

        }


    }



}