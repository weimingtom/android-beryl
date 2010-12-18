package org.beryl.web.search.bing;

public interface IBingSearchResponseHandler
{
	void onSearchCompleted(final BingSearchResponse response);
	void onSearchError(final Exception error);
}
