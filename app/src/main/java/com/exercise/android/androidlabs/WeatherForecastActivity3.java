package com.exercise.android.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

public class WeatherForecastActivity3 extends Activity {

    protected static final String Activity_Name = "WeatherForecastActivity";
    private ProgressBar progressBar;
    private int progressStatus = 0;
    TextView txtCurrTemp;
    TextView txtMinTemp;
    TextView txtMaxTemp;
    String max="";
    String min="";
    String value="";
    String icon = "";

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        progressBar.setVisibility(View.VISIBLE);

        txtCurrTemp = (TextView) findViewById(R.id.weather_txtCurrTem);
        txtMinTemp = (TextView) findViewById(R.id.weather_txtMinTem);
        txtMaxTemp = (TextView) findViewById(R.id.weather_txtMaxTem);
        Log.i(Activity_Name, "In onCreate()");

//        ForecastQuery forecastQuery = new ForecastQuery(WeatherForecastActivity.this);
//        forecastQuery.execute(fileURL);

        new ForecastQuery().execute("AAA","BBB");
    }

    private class ForecastQuery extends AsyncTask<String,String,String> {

        private final String ns = null;
        URL url;
        InputStream is = null;
        Bitmap bitmap = null;
        String urlStr = "http://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1";
//        String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

//        @Override
//        protected void onPreExecute() {
//            new Thread(new Runnable() {
//                public void run() {
//                    while (progressStatus < 100) {
//                        progressStatus += 1;
//                        // Update the progress bar and display the
//                        //current value in the text view
//                        handler.post(new Runnable() {
//                            public void run() {
//                                progressBar.setProgress(progressStatus);
//                                txtCurrTemp.setText(progressStatus+"/"+progressBar.getMax());
//                            }
//                        });
//                        try {
//                            // Sleep for 200 milliseconds.
//                            Thread.sleep(200);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//        }

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

//                InputStream in = urlConnection.getInputStream();
//                Log.i(Activity_Name,"BBBBBBBBBBB");
//
//                factory = XmlPullParserFactory.newInstance();
//                factory.setNamespaceAware(true);
//                XmlPullParser parser = factory.newPullParser();
//
//                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//                parser.setInput(in, null);
//                parser.nextTag();

                urlConnection.setReadTimeout(10000 /* milliseconds */);
                Log.i(Activity_Name,"11");
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                Log.i(Activity_Name,"22");
                urlConnection.setRequestMethod("GET");
                Log.i(Activity_Name,"33");
                urlConnection.setDoInput(true);
                Log.i(Activity_Name,"44");
                // Starts the query
                urlConnection.connect();
                Log.i(Activity_Name,"55");
                urlConnection.getInputStream();
                Log.i(Activity_Name,"BBBBBBBBBBB");


                factory = XmlPullParserFactory.newInstance();
//                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

//                InputStreamReader isw = new InputStreamReader(in);
//                Log.i(Activity_Name,"InputStreamReader:"+isw);
//                BufferedReader reader = null;

//                reader = new BufferedReader(isw);
//
//                String line;
//
//                StringBuffer buffer = new StringBuffer();
//
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n");
//
//                    Log.i(Activity_Name,"line==>"+line);
//
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                forecastJsonStr = buffer.toString();
//                return forecastJsonStr;

//                parser.setInput(in,null);
//
                Log.i(Activity_Name,"parser==>"+parser);

//                parser.require(XmlPullParser.START_TAG, ns, "current");
//                while (parser.next() != XmlPullParser.END_TAG) {
//                    if (parser.getEventType() != XmlPullParser.START_TAG) {
//                        continue;
//                    }
//                    String name = parser.getName();
//                    // Starts by looking for the entry tag
//                    if (name.equals("country")) {
//                        Log.i(Activity_Name,"aaaa==>"+name);
//                    } else {
//                        Log.i(Activity_Name,"bbb==>");
//                    }
//                }

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name;
  //                  Log.i(Activity_Name,"name==>"+parser.getName());
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equals("temperature")) {
                                max = parser.getAttributeValue(null,"max");
//                                publishProgress("25");
                                min = parser.getAttributeValue(null,"min");
//                                publishProgress("50");
                                value = parser.getAttributeValue(null,"value");
//                                publishProgress("75");
                                Log.i(Activity_Name,"max==>"+max);
//                                publishProgress();
 //                               publishProgress("25","50","75");
                            }

//                            if (name.equals("weather")) {
//                                icon = parser.getAttributeValue(null,"icon");
//                                Log.i(Activity_Name,"icon==>"+icon);
//                            }

                            if (fileExistance(icon)) {
                                FileInputStream fis = null;
                                try {    fis = openFileInput(icon);   }
                                catch (FileNotFoundException e) {    e.printStackTrace();  }
                                Bitmap bm = BitmapFactory.decodeStream(fis);

                            } else {

                                if (name.equals("weather")) {
                                    icon = parser.getAttributeValue(null,"icon");
                                    Log.i(Activity_Name,"icon==>"+icon);
                                }

                            }
                            break;

//                        case XmlPullParser.TEXT:
//                            Log.i(Activity_Name,"text==>"+parser.getText());
//                            break;
                    }
                    eventType = parser.next();
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

//        protected void publishProgress() {
//            Bitmap image = HTTPUtils.getImage(ImageURL));
//            FileOutputStream outputStream= openFileOutput (iconName + ".png", Context.MODE_PRIVATE);
//            image.compress(Bitmap.CompressFormat.PNG,80,outputStream);
//            outputStream.flush();
//            outputStream.close();

  //      }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }

        public void Bitmap(String imageURL){
            publishProgress("100");
            Bitmap image  = HttpUtils.getImage(imageURL);
            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            progressBar.setVisibility(View.VISIBLE);
        }


    }

}
