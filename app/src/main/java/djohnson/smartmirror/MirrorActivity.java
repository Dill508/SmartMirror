package djohnson.smartmirror;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnhiott.darkskyandroidlib.ForecastApi;

import java.util.Timer;
import java.util.TimerTask;

import djohnson.smartmirror.modules.Configuration;
import djohnson.smartmirror.modules.Date;
import djohnson.smartmirror.modules.ForecastIO;
import djohnson.smartmirror.modules.MapsBackground;
import djohnson.smartmirror.modules.WeatherUndergroundRadar;

public class MirrorActivity extends AppCompatActivity {

    @NonNull
    private Configuration configSettings;

    private TextView currentDate;
    private TextView currentWeather;
    private TextView highlowTemp;
    private TextView feelslikeTemp;
    private TextView todayForecast;
    private ImageView weatherRadar;
    private ImageView googleMap;

    private String weatherundergroundAPIKEY;
    private String googlemapsAPIKEY;
    private Timer timer = new Timer();
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        configSettings = new Configuration(this);

        //Sets to fullscreen and removes notification and action bar
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            currentDate = (TextView) findViewById(R.id.date_text);
            currentWeather = (TextView) findViewById(R.id.weather_text);
            highlowTemp = (TextView) findViewById(R.id.highlowtemp_text);
            feelslikeTemp = (TextView) findViewById(R.id.feelslike_text);
            weatherRadar = (ImageView) findViewById(R.id.weather_radar);
            todayForecast = (TextView) findViewById(R.id.todayforecast_text);
            googleMap = (ImageView) findViewById(R.id.map);

            int forecastAPIKey = getResources().getIdentifier("forecastio_api_key", "string", getPackageName());
            ForecastApi.create(getString(forecastAPIKey));

            weatherundergroundAPIKEY = getString(getResources().getIdentifier("weatherunderground_api_key", "string", getPackageName()));
            googlemapsAPIKEY = getString(getResources().getIdentifier("googlemaps_static_api_key", "string", getPackageName()));
            updateMirrorInfo();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateMirrorInfo();
    }

    private void updateMirrorInfo() {

//        currentDate.setText(Date.getDate());
//        ForecastIO forecast = new ForecastIO();
//        forecast.getCurrentWeather(configSettings.getLatitude(), configSettings.getLongitude(), configSettings.getForecastUnits(), currentWeather, highlowTemp, feelslikeTemp, todayForecast);
//        MapsBackground map = new MapsBackground();
//        map.getMapBackground(googlemapsAPIKEY, configSettings.getLatitude(), configSettings.getLongitude(), googleMap);
//        WeatherUndergroundRadar radar = new WeatherUndergroundRadar();
//        radar.getWeatherRadar(weatherundergroundAPIKEY, configSettings.getLatitude(), configSettings.getLongitude(), weatherRadar);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentDate.setText(Date.getDate());
                        ForecastIO forecast = new ForecastIO();
                        forecast.getCurrentWeather(configSettings.getLatitude(), configSettings.getLongitude(), configSettings.getForecastUnits(), currentWeather, highlowTemp, feelslikeTemp, todayForecast);
                        MapsBackground map = new MapsBackground();
                        map.getMapBackground(googlemapsAPIKEY, configSettings.getLatitude(), configSettings.getLongitude(), googleMap);
                        WeatherUndergroundRadar radar = new WeatherUndergroundRadar();
                        radar.getWeatherRadar(weatherundergroundAPIKEY, configSettings.getLatitude(), configSettings.getLongitude(), weatherRadar);
                    }
                });

            }
        };
        timer.schedule(timerTask, 0, 5 * 60 * 1000);


//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    currentDate.setText(Date.getDate());
//                    ForecastIO forecast = new ForecastIO();
//                    forecast.getCurrentWeather(currentWeather, highlowTemp, feelslikeTemp, todayForecast);
//                    //WeatherRadar radar = new WeatherRadar();
//                    //radar.getWeatherRadar(weatherRadar);
//                }
//            }, 5000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //AlarmReceiver.stopMirrorUpdates(this);
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
