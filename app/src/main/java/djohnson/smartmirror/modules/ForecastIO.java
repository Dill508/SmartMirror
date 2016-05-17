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

    public void getCurrentWeather(final TextView currentWeather, final TextView highlowtemp) {
        RequestBuilder weather = new RequestBuilder();
        Request request = new Request();
        request.setLat("41.533");
        request.setLng("-90.655");
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.ENGLISH);
        //request.addExcludeBlock(Request.Block.CURRENTLY);
        //request.removeExcludeBlock(Request.Block.CURRENTLY);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
//                Log.d(TAG, "data0: " + weatherResponse.getDaily().getData().get(1).getTemperatureMax());
                Log.d(TAG, "data0: " + weatherResponse.getDaily().getData().get(0).getPrecipProbability());

                //setting current weather (temperature and summary)
                String currentTemp = String.valueOf((int)(float)Math.round(weatherResponse.getCurrently().getTemperature()));
                String displayedCurrentWeather = currentTemp + "° " + weatherResponse.getCurrently().getSummary();
                currentWeather.setText(displayedCurrentWeather);

                //setting today's high/low temperatures
                String highTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMax()));
                String lowTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMin()));
                String displayedhighlowtemp = "Today: " + highTemp + "° /  " + lowTemp + "° ";
                highlowtemp.setText(displayedhighlowtemp);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.d(TAG, retrofitError.toString());
            }
        });
    }

}
