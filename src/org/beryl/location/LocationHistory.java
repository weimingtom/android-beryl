package org.beryl.location;

import java.util.ArrayList;

import android.location.Location;

/**
 * Holds a history of locations recorded by a location provider.
 * There is a limit as to how many locations can be remembered.
 * @author jeremyje
 *
 */
public class LocationHistory
{
	private final static int DEFAULT_CAPACITY = 100;
	private final static float MIN_DISTANCE = 1.0F;
	
	
	/**
	 * Holds the location coordinates for each sample.
	 * Order is from oldest to newest.
	 */
	public final ArrayList<Location> Locations;
	private final int _capacity;
	private final float _minDistance;
	
	public LocationHistory()
	{
		_capacity = DEFAULT_CAPACITY;
		Locations = new ArrayList<Location>(_capacity);
		_minDistance = MIN_DISTANCE;
	}
	
	public LocationHistory(int coords_to_save)
	{
		_capacity = coords_to_save;
		Locations = new ArrayList<Location>(_capacity);
		_minDistance = MIN_DISTANCE;
	}
	
	public LocationHistory(int coords_to_save, float minDistance)
	{
		_capacity = coords_to_save;
		Locations = new ArrayList<Location>(_capacity);
		_minDistance = minDistance;
	}
	
	public void add(final Location location)
	{
		if(isFull())
		{
			Locations.remove(0);
		}
		
		final int index = Locations.size() - 1;
		if(index == -1 || (index >= 0 && Locations.get(index).distanceTo(location) > _minDistance))
		{
			Locations.add(location);
		}
	}
	
	public boolean isFull()
	{
		return _capacity == Locations.size();
	}
}
