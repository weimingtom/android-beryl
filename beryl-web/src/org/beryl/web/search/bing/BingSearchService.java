package org.beryl.web.search.bing;

public class BingSearchService
{
	protected static String _api_key = null;
	
	public static void initializeService(String api_key)
	{
		BingSearchService._api_key = api_key;
	}
	
	public static BingSearchRequest createSearchRequest(String query)
	{
		BingSearchService.validateServiceState();
		
		BingSearchRequest result = new BingSearchRequest(query);

		return result;
	}

	public static void beginSearchRequest(BingSearchRequest request, IBingSearchResponseHandler handler)
	{
		SearchWorker worker = new SearchWorker(request, handler);
		Thread t = new Thread(worker);
		t.run();
	}
	
	public static void blockingSearchRequest(BingSearchRequest request, IBingSearchResponseHandler handler)
	{
		SearchWorker worker = new SearchWorker(request, handler);
		worker.run();
	}
	
	private static void validateServiceState() throws IllegalStateException
	{
		if(BingSearchService._api_key == null)
		{
			throw new IllegalStateException("BingSearchService was not initialized. Call BingSearchService.initializeService() before using the API.");
		}
	}
}
