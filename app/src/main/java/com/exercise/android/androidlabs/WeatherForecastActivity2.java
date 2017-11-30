package com.exercise.android.androidlabs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecastActivity2 extends AppCompatActivity {

    protected static final String Activity_Name = "WeatherForecastActivity";
//    ProgressBar progressBar;
//    TextView txtCurrTemp;
//    TextView txtMinTemp;
//    TextView txtMaxTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather_forecast);
//        progressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
//        progressBar.setVisibility(View.VISIBLE);
//
//        txtCurrTemp = (TextView) findViewById(R.id.weather_txtCurrTem);
//        txtMinTemp = (TextView) findViewById(R.id.weather_txtMinTem);
//        txtMaxTemp = (TextView) findViewById(R.id.weather_txtMaxTem);
        Log.i(Activity_Name, "In onCreate()");

    }

    public class ForecastQuery extends AsyncTask<String,String,String> {

        HttpURLConnection connection = null;
        URL url;
        InputStream is = null;
        Bitmap bitmap = null;
        String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        /**
         * 쓰레드 작업
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {

            try {
                url= new URL(urlStr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                StringBuffer json = new StringBuffer(1024);
                String tmp ="";

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((tmp= reader.readLine())!=null)
                    json.append(tmp).append("\n");
                reader.close();
                JSONObject data = new JSONObject(json.toString());
                if (data.getInt("cod") !=200) {
                    return null;
                }
                Log.i("data.toString()==>",data.toString());
                return data.toString();

            } catch (Exception e) {
                return null;
            }

         }


        protected void publishProgress() {
//            Bitmap image = HTTPUtils.getImage(ImageURL));
//            FileOutputStream outputStream= openFileOutput (iconName + ".png", Context.MODE_PRIVATE);
//            image.compress(Bitmap.CompressFormat.PNG,80,outputStream);
//            outputStream.flush();
//            outputStream.close();

        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }


        public void setBitmap(String fname){
            FileInputStream fis = null;
//            if (!fileExistance(fname)) {
//                try {
//                    fis = openFileInput(imagefile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                Bitmap bm = BitmapFactory.decodeStream(fis);
//  //              if (image is locally) || (download needs)
//                Log.i("filename==>",fname);
//
//
//
//            }
        }


        protected void onProgressUpdate (Integer ... values) {
//            super.onProgressUpdate(String.valueOf(values));
//            progressBar.setProgress(values[0]);
        }


        protected void onPostExecute () {

//            super.onPreExecute();
//            txtCurrTemp.setText("current temp");
//            txtMinTemp.setText("Min temp");
//            txtMaxTemp.setText("Max temp");
//            progressBar.setVisibility(View.VISIBLE);
        }


    }

}
