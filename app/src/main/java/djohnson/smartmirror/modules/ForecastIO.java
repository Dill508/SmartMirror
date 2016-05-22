package djohnson.smartmirror.modules;

import android.util.Log;
import android.widget.TextView;

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

    private static final String TAG = "ForecastIO class";

    public void getCurrentWeather(String lat, String lon, String units, final TextView currentWeather, final TextView highlowtemp, final TextView feelsliketemp, final TextView todaysforecast) {
        RequestBuilder weather = new RequestBuilder();
        Request request = new Request();
        request.setLat(lat);
        request.setLng(lon);
        if (units.equals("SI")) {
            request.setUnits(Request.Units.SI);
        } else {
            request.setUnits(Request.Units.US);
        }
        request.setLanguage(Request.Language.ENGLISH);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                //Log.d(TAG, "data0: " + weatherResponse.getDaily().getData().get(0).getTemperatureMaxTime());

                //setting current weather (temperature and summary)
                String currentTemp = String.valueOf((int)(float)Math.round(weatherResponse.getCurrently().getTemperature()));
                String displayedCurrentWeather = currentTemp + "° " + weatherResponse.getCurrently().getSummary();
                currentWeather.setText(displayedCurrentWeather);

                //setting today's high/low temperatures
                String highTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMax()));
                String lowTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMin()));
                String displayedhighlowtemp = "Today: " + highTemp + "° /  " + lowTemp + "° ";
                highlowtemp.setText(displayedhighlowtemp);

                //setting current feels like temperature
                String feelslike = String.valueOf((int)(float)Math.round(weatherResponse.getCurrently().getApparentTemperature()));
                String displayedfeelsliketemp = "Feels Like: " + feelslike + "° ";
                feelsliketemp.setText(displayedfeelsliketemp);

                //setting today's forecast
                String daysforecast = weatherResponse.getHourly().getSummary();
                todaysforecast.setText(daysforecast);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.d(TAG, retrofitError.toString());
            }
        });
    }

}
