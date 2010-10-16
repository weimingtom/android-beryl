package org.beryl.web.search.bing;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

class SearchWorker implements Runnable
{
	private final BingSearchRequest _request;
	private final IBingSearchResponseHandler _handler;
	
	protected SearchWorker(BingSearchRequest request, IBingSearchResponseHandler handler)
	{
		_request = request;
		_handler = handler;
	}
	
	public void run()
	{
		String uri = _request.getSearchUri();
		String response = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet getter = new HttpGet(uri);
		try
		{
			ResponseHandler<String> handler = new BasicResponseHandler();
			response = client.execute(getter, handler);
			BingSearchResponse bing_response = BingSearchResponse.getResponse(response);
			handleResult(bing_response);
		}
		catch(Exception e)
		{
			handleError(e);
		}
	}
	
	private void handleError(Exception e)
	{
		_handler.onSearchError(e);
	}
	
	private void handleResult(BingSearchResponse response)
	{
		_handler.onSearchCompleted(response);
	}
}