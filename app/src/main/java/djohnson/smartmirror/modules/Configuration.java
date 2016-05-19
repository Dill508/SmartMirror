package djohnson.smartmirror.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by Dillon on 5/18/2016.
 *
 */
public class Configuration {

    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String FORECAST_UNIT = "forecast_unit";
    private static final String MIRROR_PREFS = "mirror_Prefs";

    @NonNull
    private SharedPreferences sharedPrefs;
    private String forecastUnit;
    private String latitude;
    private String longitude;

    public Configuration(Context context) {
        sharedPrefs = context.getSharedPreferences(MIRROR_PREFS, Context.MODE_PRIVATE);
        readPrefs();
    }

    private void readPrefs() {
        forecastUnit = sharedPrefs.getString(FORECAST_UNIT, "US");
        latitude = sharedPrefs.getString(LATITUDE, "");
        longitude = sharedPrefs.getString(LONGITUDE, "");
    }

    public void setIsCelsius(boolean isCelsius) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(FORECAST_UNIT, isCelsius ? "SI" : "US");
        editor.apply();
    }

    public void setLatLong(String lat, String lon) {
        latitude = lat.trim();
        longitude = lon.trim();

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(LATITUDE, latitude);
        editor.putString(LONGITUDE, longitude);
        editor.apply();
    }

    public boolean getIsCelsius() {
        if (forecastUnit.equals("SI")) {
            return true;
        } else {
            return false;
        }
    }

    public String getForecastUnits() {
        return forecastUnit;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
