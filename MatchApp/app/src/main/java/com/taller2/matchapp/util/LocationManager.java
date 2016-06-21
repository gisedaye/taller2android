package com.taller2.matchapp.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import java.util.List;

/**
 * Created by Fede on 21/06/16.
 */
public class LocationManager {

    public static final int MIN_TIME = 60000 * 5;//5 minute
    private static final float ACCURACY_TOLERANCE = 100.0f;

    private boolean looperCreated;
    private Location lastLocation;
    private boolean waitingLocation = false;
    private static LocationManager instance;

    private final android.location.LocationManager locationManager;

    public static LocationManager getInstance(Context context) {
        // double checked locking implementation to prevent synchronization blocking
        if (instance == null) {
            synchronized (LocationManager.class) {
                if (instance == null) {
                    instance = new LocationManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private LocationManager(Context context) {
        locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private void requestUpdate() {

        List<String> providers = locationManager.getProviders(true);

        String networkProvider = android.location.LocationManager.NETWORK_PROVIDER;
        String gpsProvider = android.location.LocationManager.GPS_PROVIDER;

        if (providers.contains(networkProvider)) {
            requestLocationWithProvider(networkProvider);
        } else if (providers.contains(gpsProvider)) {
            requestLocationWithProvider(gpsProvider);
        }
    }

    private void requestLocationWithProvider(final String provider) {

        Looper looper = Looper.myLooper();
        if (looper == null) {
            Runnable looperRunnable = new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        looperCreated = true;
                        waitingLocation = true;
                        Looper.prepare();
                        locationManager.requestSingleUpdate(provider, new BestLocationListener(), Looper.myLooper());
                        Looper.loop();
                    }
                }
            };
            new Thread(looperRunnable).start();
        } else {
            locationManager.requestSingleUpdate(provider, new BestLocationListener(), null);
            waitingLocation = true;
        }
    }

    public synchronized Location fetchLastLocation() {

        String networkProvider = android.location.LocationManager.NETWORK_PROVIDER;
        String gpsProvider = android.location.LocationManager.GPS_PROVIDER;

        //It may take a while to receive the first location update, so If an immediate location is required we use getLastKnownLocation.
        if (isNotLocated())
            lastLocation = locationManager.getLastKnownLocation(networkProvider);

        if (lastLocation == null)
            lastLocation = locationManager.getLastKnownLocation(gpsProvider);

        if (isLocationExpired() && !waitingLocation)
            requestUpdate();

        return lastLocation;
    }


    private final class BestLocationListener implements LocationListener {


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            synchronized (this) {
                waitingLocation = false;
                Log.d(getClass().getSimpleName(), "Status: Got location!");
                if (isNotLocated() || isLocationExpired() || shouldReplaceCurrentLocation(location)) {
                    LocationManager.this.lastLocation = location;
                    //Stop background requests after first one.
                    locationManager.removeUpdates(this);
                    if (looperCreated) {
                        Looper.myLooper().quit();
                    }
                    looperCreated = false;
                }
            }
        }

        private boolean shouldReplaceCurrentLocation(Location location) {
            return !this.areTheSameLocation(location, LocationManager.this.lastLocation)
                    && this.isBetterLocation(location, LocationManager.this.lastLocation);
        }

        private boolean isBetterLocation(Location location, Location lastLocation) {
            return location.getAccuracy() < lastLocation.getAccuracy() + ACCURACY_TOLERANCE;
        }


        private boolean areTheSameLocation(Location location, Location lastLocation) {
            return location.getLatitude() == lastLocation.getLatitude()
                    && location.getLongitude() == lastLocation.getLongitude();
        }
    }

    private boolean isLocationExpired() {
        return lastLocation == null || System.currentTimeMillis() - LocationManager.this.lastLocation.getTime() > MIN_TIME;
    }

    private boolean isNotLocated() {
        return lastLocation == null;
    }
}
