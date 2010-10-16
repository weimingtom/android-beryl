package org.beryl.web.search.bing;

import android.app.Activity;

public abstract class ActivityBingSearchResponseHandler
	implements IBingSearchResponseHandler
{
	private final Activity _activity;
	
	public ActivityBingSearchResponseHandler(Activity activity)
	{
		_activity = activity;
	}
	
	public abstract void handleSearchCompleted(final BingSearchResponse response);
	public abstract void handleSearchError(final Exception error);

	public final void onSearchCompleted(final BingSearchResponse response)
	{
		_activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				handleSearchCompleted(response);
			}
		});
	}

	public final void onSearchError(final Exception error)
	{
		_activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				handleSearchError(error);
			}
		});
	}

}
