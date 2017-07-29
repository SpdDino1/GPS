package com.example.vikramkumaresan.gps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    LocationManager manage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);

        manage = (LocationManager) getSystemService(LOCATION_SERVICE);  //Location Manager - - Will use this later
    }

    public void fire_gps(View view) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 102);       //Runtime perm. only required >API 23. This checks it.
        }

        LocationListener loc = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {  //If location gets updated by manager
                if (txt.getText().toString() == null) {
                    txt.setText(location.getLatitude() +" "+location.getLongitude()+"\n");

                } else {
                    txt.setText(txt.getText().toString() + "\n" + location.getLatitude() +" "+location.getLongitude()+"\n");
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {       //If location settings off
                Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); //Take user to settings page
                startActivity(settings);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling   //Useless code. Put it to avoid error
            return;
        }
        manage.requestLocationUpdates("gps", 1000, 0, loc); // manager(Provider, RefreshInterval, ToRefreshIfDistDisplacedGreater than, listener)
        //Here you Refresh on the basis of dist is 0 as it is overriden by refresh interval time


    }

}
