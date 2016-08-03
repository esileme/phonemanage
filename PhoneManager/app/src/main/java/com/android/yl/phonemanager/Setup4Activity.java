package com.android.yl.phonemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Setup4Activity extends BaseSetupActivity {
    SharedPreferences preferences;
    private TextView tvLocation;
    private Button button;
    private String j;
    private String w;
    private String accuracy;
    private String altitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        preferences = getSharedPreferences("cfg", MODE_PRIVATE);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        button = (Button) findViewById(R.id.getGPS);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvLocation.setText("132");
                tvLocation.setText(j + "\n" + w + "\n" + accuracy + "\n" + altitude);
            }
        });

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        // List<String> allProviders = lm.getAllProviders();
        MyLocationListener listener = new MyLocationListener();
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);


    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            j = "经度" + location.getLongitude();
            w = "纬度" + location.getLatitude();
            accuracy = "精度" + location.getAccuracy();
            altitude = "海拔" + location.getAltitude();
            tvLocation.setText(j + "\n" + w + "\n" + accuracy + "\n" + altitude);

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
    }

    @Override
    public void showNextPage() {
        preferences.edit().putBoolean("configed", true).commit();
        startActivity(new Intent(this, LostAndFoundActivity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);

    }

}

