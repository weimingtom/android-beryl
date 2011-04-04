package org.beryl.location;

import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * Represents a coordinate on a geographic mapping surface.
 */
public class GeoCoord
{
	private double _lat = 0.0;
	private double _long = 0.0;
	private boolean _hasLocation = false;
	private double _accuracy = -1.0;
	
	// TODO: Accuracy Levels may not be reasonable.
	public static final int ACCURACY_UNKNOWN = -1;
	public static final int ACCURACY_STREETLEVEL = 8; // 8 meters.
	public static final int ACCURACY_CITYLEVEL = 150; // 150 meters.
	public static final int ACCURACY_REASONABLE = ACCURACY_CITYLEVEL; // 50 meters.
	
	public GeoCoord()
	{
		clearLocation();
	}
	
	public GeoCoord(double latitude, double longitude)
	{
		setCoordinates(latitude, longitude);
	}
	
	public void clearLocation()
	{
		_lat = 0.0;
		_long = 0.0;
		_hasLocation = false;
		_accuracy = ACCURACY_UNKNOWN;
	}
	
	public double getAccuracy()
	{
		return _accuracy;
	}
	
	public boolean hasAccuracy()
	{
		return _accuracy != ACCURACY_UNKNOWN;
	}
	
	public boolean hasStreetLevelAccuracy()
	{
		return hasAccuracy() && _accuracy < ACCURACY_STREETLEVEL;
	}
	
	public boolean hasCityLevelAccuracy()
	{
		return hasAccuracy() && _accuracy < ACCURACY_CITYLEVEL;
	}
	
	public boolean hasReasonableAccuracy()
	{
		return hasAccuracy() && _accuracy < ACCURACY_REASONABLE;
	}
	
	public boolean hasLocation()
	{
		return _hasLocation;
	}
	
	public GeoCoord(GeoPoint point)
	{
		fromGeoPoint(point);
	}
	
	public GeoPoint getGeoPoint()
	{
		return new GeoPoint((int)(_lat * 1E6), (int)(_long * 1E6));
	}
	
	public void setCoordinates(double latitude, double longitude)
	{
		_lat = latitude;
		_long = longitude;
		_hasLocation = true;
		_accuracy = ACCURACY_UNKNOWN;
	}
	
	public void setCoordinates(double latitude, double longitude, double accuracy)
	{
		_lat = latitude;
		_long = longitude;
		_hasLocation = true;
		_accuracy = accuracy;
	}
	
	public void fromGeoPoint(final GeoPoint point)
	{
		_lat = point.getLatitudeE6() / (double)1E6;
		_long = point.getLongitudeE6() / (double)1E6;
		_hasLocation = true;
		_accuracy = ACCURACY_UNKNOWN;
	}
	
	public double [] getDoubleArray()
	{
		return new double [] { _lat, _long };
	}
	
	public void fromLocation(final Location location)
	{
		_lat = location.getLatitude();
		_long = location.getLongitude();
		
		if(location.hasAccuracy())
		{
			_accuracy = location.getAccuracy();
		}
		else
		{
			_accuracy = ACCURACY_UNKNOWN;
		}
	}
	
	public double getLatitude()
	{
		return _lat;
	}
	
	public double getLongitude()
	{
		return _long;
	}
}
