package com.example.sarahli.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.in;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private ProgressBar progressBar;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView windSpeed;
    private ImageView weatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImage = (ImageView)findViewById(R.id.weatherImage) ;
        currentTemp = (TextView)findViewById(R.id.currentTemp);
        minTemp = (TextView)findViewById(R.id.minTemp);
        maxTemp = (TextView)findViewById(R.id.maxTemp);
        windSpeed = (TextView)findViewById(R.id.windSpeed);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        ForecastQuery query = new ForecastQuery();
        query.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

        progressBar.setVisibility(View.VISIBLE);
    }

    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }



    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String current;
        String min;
        String max;
        String speed;
        Bitmap weather;

        @Override
        protected String doInBackground(String... args) {
            try {
                //connect to Server:
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                // Starts the query
                urlConnection.connect();

                //Read the XML:
                InputStream response = urlConnection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(response, null);

                while(parser.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    switch(parser.getEventType())
                    {

                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("temperature")) {
                                current = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            }

                            if(parser.getName().equals("speed"))
                            {
                                speed = parser.getAttributeValue(null, "value");
                            }

                            if(parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String iconFile = iconName + ".png";

                                if(fileExistance(iconFile)){
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(iconFile);
                                    }
                                    catch (FileNotFoundException e)
                                    {    e.printStackTrace();  }
                                    weather = BitmapFactory.decodeStream(fis);
                                }else{
                                    URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconFile);
                                    weather = getImage(iconUrl);
                                    FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                                    weather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }

                    }
                    parser.next();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        public void onProgressUpdate(Integer ... value) //update your GUI
        {
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }


        public void onPostExecute(String result)  {
            Log.i(ACTIVITY_NAME, "In onPostExecute");
            String degree = Character.toString((char) 0x00B0);
            currentTemp.setText(currentTemp.getText()+current+degree+"C");
            minTemp.setText(minTemp.getText()+min+degree+"C");
            maxTemp.setText(maxTemp.getText()+max+degree+"C");
            windSpeed.setText(windSpeed.getText()+speed+"m/s");
            weatherImage.setImageBitmap(weather);
            progressBar.setVisibility(View.INVISIBLE );
        }
    }

}

