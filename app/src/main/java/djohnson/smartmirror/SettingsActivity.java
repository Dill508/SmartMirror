package djohnson.smartmirror;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import djohnson.smartmirror.modules.Configuration;

/**
 * Created by Dillon on 5/18/2016.
 *
 */
public class SettingsActivity extends Activity {

    private static final long ONE_HOUR = 60 * 60000;
    private static final long MIN_METERS = 500;

    @NonNull
    private Configuration sconfiguration;

    private LocationManager slocationManager;

    @Nullable
    private LocationListener slocationListener;
    private Location slocation;
    private View slocationView;
    private EditText slatitude;
    private EditText slongitude;
    private RadioGroup stempChoice;
    private View slocationalert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sconfiguration = new Configuration(this);

        stempChoice = (RadioGroup) findViewById(R.id.temperature_group);
        stempChoice.check(sconfiguration.getIsCelsius() ? R.id.celsius : R.id.farenheit);

        slatitude = (EditText) findViewById(R.id.latitude);
        slongitude = (EditText) findViewById(R.id.longitude);
        slatitude.setText(String.valueOf(sconfiguration.getLatitude()));
        slongitude.setText(String.valueOf(sconfiguration.getLongitude()));
        slocationView = findViewById(R.id.location_view);
        slocationalert = findViewById(R.id.location_alert);
        startFindLocation();

        findViewById(R.id.launch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();

                Intent intent = new Intent(SettingsActivity.this, MirrorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (slocationManager != null && slocationListener != null) {
            try {
                slocationManager.removeUpdates(slocationListener);
            } catch (SecurityException e) {
                slocationalert.setVisibility(View.VISIBLE);
            }
        }
    }

    private void startFindLocation() {
        slocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String provider = slocationManager.getBestProvider(criteria, true);

        try {
            try {
                slocation = slocationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                slocationalert.setVisibility(View.VISIBLE);
            }

            if (slocation == null) {
                slocationView.setVisibility(View.VISIBLE);
                slocationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            Toast.makeText(SettingsActivity.this, R.string.location_found, Toast.LENGTH_SHORT).show();
                            slocation = location;
                            sconfiguration.setLatLong(String.valueOf(slocation.getLatitude()), String.valueOf(slocation.getLongitude()));
                            try {
                                slocationManager.removeUpdates(this);
                            } catch (SecurityException e) {
                                    slocationalert.setVisibility(View.VISIBLE);
                            }
                            if (slocationView != null) {
                                slocationView.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                try {
                    slocationManager.requestLocationUpdates(provider, ONE_HOUR, MIN_METERS, slocationListener);
                } catch (SecurityException e) {
                    slocationalert.setVisibility(View.VISIBLE);
                }
            } else {
                slocationView.setVisibility(View.GONE);
            }
        } catch (IllegalArgumentException e) {
            Log.e("SetUpActivity", "Location manager could not use provider", e);
        }
    }

    private void saveSettings() {
        sconfiguration.setIsCelsius(stempChoice.getCheckedRadioButtonId() == R.id.celsius);

        if (slocation == null) {
            sconfiguration.setLatLong(slatitude.getText().toString(), slongitude.getText().toString());
        } else {
            sconfiguration.setLatLong(String.valueOf(slocation.getLatitude()), String.valueOf(slocation.getLongitude()));
        }
    }

}
