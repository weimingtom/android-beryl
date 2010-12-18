package org.beryl.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class GpsSignalStrengthMonitor implements LocationListener {

	private int _numSatellites = 0;
	
	public void onLocationChanged(Location location) {
	}

	public void onProviderDisabled(String provider) {
		if(provider.equals(LocationManager.GPS_PROVIDER)) {
			_numSatellites = 0;
		}
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		boolean is_available = LocationProvider.AVAILABLE == status;

		if(provider.equals(LocationManager.GPS_PROVIDER)) {
			if(is_available){
				_numSatellites = extras.getInt("satellites");
			}
			else {
				_numSatellites = 0;
			}
		}
	}

	public boolean isAvailable() {
		return _numSatellites != 0;
	}
	
	public int getSatellites() {
		return _numSatellites;
	}
}
