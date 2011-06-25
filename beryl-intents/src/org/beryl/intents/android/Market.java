package org.beryl.intents.android;

import org.beryl.intents.IIntentBuilder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

// http://developer.android.com/guide/publishing/publishing.html
public class Market {

	public static final String MARKET_Prefix = "http://market.android.com/";
	public static final String MARKET_Scheme = "market://";
	
	public static class ViewDetails implements IIntentBuilder {
		public String packageName;
		
		public void prepareIntent(Context context) {}

		public Intent getIntent() {
			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(MARKET_Scheme + "details?id=" + packageName));
			return intent;
		}
	}
	
	public static class Search implements IIntentBuilder {
		public String queryString;
		
		public void prepareIntent(Context context) {}

		public Intent getIntent() {
			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(MARKET_Scheme + "search?q=" + queryString));
			return intent;
		}
	}
	
	public static class SearchPublisher implements IIntentBuilder {
		public String publisherName;
		
		public void prepareIntent(Context context) {}

		public Intent getIntent() {
			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(MARKET_Scheme + "search?q=pub:" + publisherName));
			return intent;
		}
	}

	public static final Intent viewDetails(final String packageName) {
		final ViewDetails builder = new ViewDetails();
		builder.packageName = packageName;
		return builder.getIntent();
	}
	
	public static final Intent search(final String queryString) {
		final Search builder = new Search();
		builder.queryString = queryString;
		return builder.getIntent();
	}
	
	public static final Intent searchPublisher(final String publisherName) {
		final SearchPublisher builder = new SearchPublisher();
		builder.publisherName = publisherName;
		return builder.getIntent();
	}
}
