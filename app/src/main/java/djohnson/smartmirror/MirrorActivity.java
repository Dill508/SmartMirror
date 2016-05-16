package djohnson.smartmirror;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.johnhiott.darkskyandroidlib.ForecastApi;

import djohnson.smartmirror.modules.Date;
import djohnson.smartmirror.modules.ForecastIO;

public class MirrorActivity extends AppCompatActivity {

    private TextView currentDate;
    private TextView currentWeather;
    private TextView highlowTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

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

            int forecastAPIKey = getResources().getIdentifier("forecastio_api_key", "string", getPackageName());
            ForecastApi.create(getString(forecastAPIKey));

            updateMirrorInfo();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateMirrorInfo();
    }

    private void updateMirrorInfo() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                currentDate.setText(Date.getDate());
//                ForecastIO forecast = new ForecastIO();
//                forecast.getCurrentWeather(currentWeather);
//                highlowTemp.setText("Today: 73° /  53°");
//            }
//        }, 5000);
        currentDate.setText(Date.getDate());
        ForecastIO forecast = new ForecastIO();
        forecast.getCurrentWeather(currentWeather);
        highlowTemp.setText("Today: 73° /  53°");

    }
}
