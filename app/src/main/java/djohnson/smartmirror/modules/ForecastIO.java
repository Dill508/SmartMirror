package djohnson.smartmirror.modules;

import android.util.Log;
//import android.zetterstrom.com.forecast.ForecastClient;
//import android.zetterstrom.com.forecast.ForecastConfiguration;
//import android.zetterstrom.com.forecast.ForecastClient;
//import android.zetterstrom.com.forecast.models.Forecast;

import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dillon on 5/15/2016.
 *
 */
public class ForecastIO {

    private static final String TAG = "ForecastIO class:";


    public static String getCurrentWeather() {
        final String currentWeather = "asdf";

        RequestBuilder weather = new RequestBuilder();

        Request request = new Request();
        request.setLat("32.00");
        request.setLng("-81.00");
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.ENGLISH);
        request.addExcludeBlock(Request.Block.CURRENTLY);
        request.removeExcludeBlock(Request.Block.CURRENTLY);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                Log.d(TAG, "Temp: " + weatherResponse.getCurrently().getTemperature());
                Log.d(TAG, "Summary: " + weatherResponse.getCurrently().getSummary());
                Log.d(TAG, "Hourly Sum: " + weatherResponse.getHourly().getSummary());

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.d(TAG, retrofitError.toString());
            }
        });

        return currentWeather;
    }

}
