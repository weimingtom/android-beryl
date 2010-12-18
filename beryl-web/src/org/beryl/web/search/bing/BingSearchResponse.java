package org.beryl.web.search.bing;

import java.util.ArrayList;
import java.util.Date;

import org.beryl.web.search.AbstractSearchResponseSegment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BingSearchResponse extends AbstractSearchResponseSegment
{
	public static BingSearchResponse getResponse(String response)
	{
		BingSearchResponse result = new BingSearchResponse();
		result.inflateResponse(response);
		return result;
	}

	protected BingSearchResponse()
	{

	}
	
	private String Version;
	private ArrayList<Error> Errors = new ArrayList<Error>();
	private Ad Ad = null;
	private Image Image = null;
	private InstantAnswer InstantAnswer = null;
	private MobileWeb MobileWeb = null;
	private News News = null;
	private Phonebook Phonebook = null;
	private Query Query = null;
	private RelatedSearch RelatedSearch = null;
	private Spell Spell = null;
	private Translation Translation = null;
	private Video Video = null;
	private Web Web = null;
	
	
	public boolean hasErrors()
	{
		return this.Errors.size() > 0;
	}

	public String getVersion()
	{
		return Version;
	}

	public Ad getAd()
	{
		return Ad;
	}
	public Image getImage()
	{
		return Image;
	}
	public InstantAnswer getInstantAnswer()
	{
		return InstantAnswer;
	}

	public MobileWeb getMobileWeb()
	{
		return MobileWeb;
	}
	
	public News getNews()
	{
		return News;
	}
	
	public Phonebook getPhonebook()
	{
		return Phonebook;
	}
	
	public Query getQuery()
	{
		return Query;
	}
	
	public RelatedSearch getRelatedSearch()
	{
		return RelatedSearch;
	}
	
	public Spell getSpell()
	{
		return Spell;
	}

	public Translation getTranslation()
	{
		return Translation;
	}
	
	public Video getVideo()
	{
		return Video;
	}
	
	public Web getWeb()
	{
		return Web;
	}
	
	@Override
	public void inflateResponse(JSONObject jo) throws JSONException
	{
		_response = jo;

		JSONObject sr = _response.getJSONObject("SearchResponse");
		this.Version = jsonString("Version", sr);

		JSONArray array = jsonArray("Errors", sr);
		if (array != null)
		{
			int i;
			int len = array.length();
			for (i = 0; i < len; i++)
			{
				Error item = new Error();
				item.inflateResponse(array.getJSONObject(i));
				this.Errors.add(item);
			}
		}

		if (sr.has("Ad"))
		{
			this.Ad = new Ad();
			this.Ad.inflateResponse(sr.getJSONObject("Ad"));
		}
		
		if (sr.has("Image"))
		{
			this.Image = new Image();
			this.Image.inflateResponse(sr.getJSONObject("Image"));
		}
		
		if (sr.has("InstantAnswer"))
		{
			this.InstantAnswer = new InstantAnswer();
			this.InstantAnswer.inflateResponse(sr.getJSONObject("InstantAnswer"));
		}
		
		if(sr.has("MobileWeb"))
		{
			MobileWeb = new MobileWeb();
			MobileWeb.inflateResponse(sr.getJSONObject("MobileWeb"));
		}
		
		if(sr.has("News"))
		{
			News = new News();
			News.inflateResponse(sr.getJSONObject("News"));
		}
		
		if(sr.has("Phonebook"))
		{
			Phonebook = new Phonebook();
			Phonebook.inflateResponse(sr.getJSONObject("Phonebook"));
		}
		
		if(sr.has("RelatedSearch"))
		{
			RelatedSearch = new RelatedSearch();
			RelatedSearch.inflateResponse(sr.getJSONObject("RelatedSearch"));
		}
		
		if(sr.has("Spell"))
		{
			Spell = new Spell();
			Spell.inflateResponse(sr.getJSONObject("Spell"));
		}
		
		if(sr.has("Translation"))
		{
			Translation = new Translation();
			Translation.inflateResponse(sr.getJSONObject("Translation"));
		}
		
		if(sr.has("Query"))
		{
			Query = new Query();
			Query.inflateResponse(sr.getJSONObject("Query"));
		}
		
		if(sr.has("Video"))
		{
			Video = new Video();
			Video.inflateResponse(sr.getJSONObject("Video"));
		}
		
		if(sr.has("Web"))
		{
			Web = new Web();
			Web.inflateResponse(sr.getJSONObject("Web"));
		}
	}

	public class Error extends AbstractSearchResponseSegment
	{
		public int Code;
		public String Message;
		public String HelpUrl;
		public String Parameter;
		public String SourceType;
		public int SourceTypeErrorCode;
		public String Value;

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Code = jsonInt("Code", jo);
			this.Message = jsonString("Message", jo);
			this.HelpUrl = jsonString("HelpUrl", jo);
			this.Parameter = jsonString("Parameter", jo);
			this.SourceType = jsonString("SourceType", jo);
			this.SourceTypeErrorCode = jsonInt("SourceTypeErrorCode", jo);
			this.Value = jsonString("Value", jo);
		}
	}

	public class Ad
	{
		public int Total;
		public int PageNumber;
		public String AdAPIVersion;
		public ArrayList<AdResult> Results = new ArrayList<AdResult>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;
			
			this.Total = jsonInt("Total", jo);
			this.PageNumber = jsonInt("PageNumber", jo);
			this.AdAPIVersion = jsonString("AdAPIVersion", jo);
			if(jo.has("Results"))
			{
				array = jo.getJSONArray("Results");
				len = array.length();
				AdResult ar;
				for(i = 0; i < len; i++)
				{
					ar = new AdResult();
					ar.inflateResponse(array.getJSONObject(i));
					this.Results.add(ar);
				}
			}
		}
	}
	
	public class AdResult
	{
		public String AdlinkURL;
		public int Rank;
		public String Position;
		public String Title;
		public String Description;
		public String DisplayUrl;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.AdlinkURL = jsonString("AdlinkURL", jo);
			this.Rank = jsonInt("Rank", jo);
			this.Position = jsonString("Position", jo);
			this.Title = jsonString("Title", jo);
			this.Description = jsonString("Description", jo);
			this.DisplayUrl = jsonString("DisplayUrl", jo);
		}
	}
	
	public class Image
	{
		public int Offset;
		public int Total;
		public ArrayList<ImageResult> Results = new ArrayList<ImageResult>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Offset = jsonInt("Offset", jo);
			this.Total = jsonInt("Total", jo);
			if(jo.has("Results"))
			{
				JSONArray array = jo.getJSONArray("Results");
				int len = array.length();
				ImageResult ir;
				for(int i = 0; i < len; i++)
				{
					ir = new ImageResult();
					ir.inflateResponse(array.getJSONObject(i));
					this.Results.add(ir);
				}
			}
		}
	}
	
	public class ImageResult
	{
		public int FileSize;
		public String Title;
		public String MediaUrl;
		public String Url;
		public String DisplayUrl;
		public int Width;
		public int Height;
		public String ContentType;
		public Thumbnail Thumbnail;

		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.FileSize = jsonInt("FileSize", jo);
			this.Title = jsonString("Title", jo);
			this.MediaUrl = jsonString("MediaUrl", jo);
			this.Url = jsonString("Url", jo);
			this.DisplayUrl = jsonString("DisplayUrl", jo);
			this.Width = jsonInt("Width", jo);
			this.Height = jsonInt("Height", jo);
			this.ContentType = jsonString("ContentType", jo);

			if(jo.has("Thumbnail"))
			{
				this.Thumbnail = new Thumbnail();
				this.Thumbnail.inflateResponse(jo.getJSONObject("Thumbnail"));
			}
		}
	}
	
	public class InstantAnswer extends AbstractSearchResponseSegment
	{
		public final ArrayList<InstantAnswerResult> Results = new ArrayList<InstantAnswerResult>();

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;

			if (jo.has("Results"))
			{
				array = jo.getJSONArray("Results");
				len = array.length();
				InstantAnswerResult iar;
				for (i = 0; i < len; i++)
				{
					iar = new InstantAnswerResult();
					iar.inflateResponse(array.getJSONObject(i));
					this.Results.add(iar);
				}
			}
		}
	}

	public class InstantAnswerResult extends AbstractSearchResponseSegment
	{
		public String ContentType = null;
		public String Title = null;
		public String Url = null;
		public String ClickThroughUrl = null;
		public String Attribution = null;
		public InstantAnswerEncarta Encarta = null;
		public InstantAnswerFlightStatus FlightStatus = null;
		
		public boolean hasEncartaResult()
		{
			return Encarta != null;
		}
		
		public boolean hasFlightStatus()
		{
			return FlightStatus != null;
		}

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			JSONObject InstantAnswerSpecificData = null;
			
			this.ContentType = jsonString("ContentType", jo);
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);
			this.ClickThroughUrl = jsonString("ClickThroughUrl", jo);
			this.Attribution = jsonString("Attribution", jo);

			InstantAnswerSpecificData = jsonObject("InstantAnswerSpecificData", jo);
			if (InstantAnswerSpecificData.has("Encarta"))
			{
				this.Encarta = new InstantAnswerEncarta();
				this.Encarta.inflateResponse(jsonObject("Encarta", InstantAnswerSpecificData));
			}
			if (InstantAnswerSpecificData.has("FlightStatus"))
			{
				this.FlightStatus = new InstantAnswerFlightStatus();
				this.FlightStatus.inflateResponse(jsonObject("FlightStatus", InstantAnswerSpecificData));
			}
		}
	}

	public class InstantAnswerFlightStatus extends AbstractSearchResponseSegment
	{
		public String AirlineCode;
		public String AirlineName;
		public String FlightNumber;
		public String FlightName;
		public int FlightHistoryId;
		public String StatusString;
		public int StatusCode;
		public String OnTimeString;
		public Date ScheduledDeparture;
		public Date UpdatedDeparture;
		public Date ScheduledArrival;
		public Date UpdatedArrival;
		public String OriginAirportCode;
		public String OriginAirportName;
		public int OriginAirportTimezoneOffset;
		public String DestinationAirportCode;
		public String DestinationAirportName;
		public int DestinationAirportTimezoneOffset;
		public String DepartureGate;
		public String DepartureTerminal;
		public String ArrivalGate;
		public String ArrivalTerminal;
		public int DataFreshness;

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			JSONObject origin_airport = jsonObject("OriginAirport", jo);
			JSONObject destination_airport = jsonObject("DestinationAirport", jo);
			
			if(origin_airport != null)
			{
				this.OriginAirportName = jsonString("Name", origin_airport);
				this.OriginAirportCode = jsonString("Code", origin_airport);
				this.OriginAirportTimezoneOffset = jsonInt("TimeZoneOffset", origin_airport);
			}
			
			if(destination_airport != null)
			{
				this.DestinationAirportName = jsonString("Name", destination_airport);
				this.DestinationAirportCode = jsonString("Code", destination_airport);
				this.DestinationAirportTimezoneOffset = jsonInt("TimeZoneOffset", destination_airport);
			}

			this.AirlineCode = jsonString("AirlineCode", jo);
			this.AirlineName = jsonString("AirlineName", jo);
			this.FlightNumber = jsonString("FlightNumber", jo);
			this.FlightHistoryId = jsonInt("FlightHistoryId", jo);
			this.StatusCode = jsonInt("StatusCode", jo);
			this.StatusString = jsonString("StatusString", jo);
			this.FlightName = jsonString("FlightName", jo);
			this.OnTimeString = jsonString("OnTimeString", jo);

			this.ScheduledDeparture = jsonDate("ScheduledDeparture", jo);
			this.UpdatedDeparture = jsonDate("UpdatedDeparture", jo);
			this.ScheduledArrival = jsonDate("ScheduledArrival", jo);
			this.UpdatedArrival = jsonDate("UpdatedArrival", jo);
			this.DepartureGate = jsonString("DepartureGate", jo);
			this.DepartureTerminal = jsonString("DepartureTerminal", jo);
			this.ArrivalGate = jsonString("ArrivalGate", jo);
			this.ArrivalTerminal = jsonString("ArrivalTerminal", jo);
			this.DataFreshness = jsonInt("DataFreshness", jo);
		}
	}

	public class InstantAnswerEncarta extends AbstractSearchResponseSegment
	{
		public String Value;

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Value = jsonString("Value", jo);
		}
	}
	
	public class MobileWeb extends AbstractSearchResponseSegment
	{
		public int Total;
		public int Offset;
		public final ArrayList<MobileWebResult> Results = new ArrayList<MobileWebResult>();

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			
			this.Total = jsonInt("Total", jo);
			this.Offset = jsonInt("Offset", jo);
			
			int i;
			int len;
			JSONArray array;

			if (jo.has("Results"))
			{
				array = jo.getJSONArray("Results");
				len = array.length();
				MobileWebResult mwr;
				for (i = 0; i < len; i++)
				{
					mwr = new MobileWebResult();
					mwr.inflateResponse(array.getJSONObject(i));
					this.Results.add(mwr);
				}
			}
		}
	}
	
	public class MobileWebResult extends AbstractSearchResponseSegment
	{
		public String Title;
		public String Description;
		public String Url;
		public String DisplayUrl;
		public String DateTime;

		@Override
		public void inflateResponse(JSONObject jo) throws JSONException
		{
			
			this.Title = jsonString("Title", jo);
			this.Description = jsonString("Description", jo);
			this.Url = jsonString("Url", jo);
			this.DisplayUrl = jsonString("DisplayUrl", jo);
			this.DateTime = jsonString("DateTime", jo);
		}
	}
	
	public class News
	{
		public int Total;
		public int Offset;
		public ArrayList<NewsResult> Results = new ArrayList<NewsResult>();
		public ArrayList<NewsRelatedSearch> RelatedSearches = new ArrayList<NewsRelatedSearch>();
		
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;
			
			this.Total = jsonInt("Total", jo);
			this.Offset = jsonInt("Offset", jo);
			if(jo.has("Results"))
			{
				array = jo.getJSONArray("Results");
				len = array.length();
				NewsResult nr;
				for(i = 0; i < len; i++)
				{
					nr = new NewsResult();
					nr.inflateResponse(array.getJSONObject(i));
					this.Results.add(nr);
				}
			}
			
			if(jo.has("RelatedSearches"))
			{
				array = jo.getJSONArray("RelatedSearches");
				len = array.length();
				NewsRelatedSearch nrs;
				for(i = 0; i < len; i++)
				{
					nrs = new NewsRelatedSearch();
					nrs.inflateResponse(array.getJSONObject(i));
					this.RelatedSearches.add(nrs);
				}
			}
		}
	}
	
	public class NewsRelatedSearch
	{
		public String Title;
		public String Url;

		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);			
		}
	}
	
	public class NewsResult
	{
		public boolean BreakingNews;
		public Date Date;
		public ArrayList<NewsCollection> NewsCollections = new ArrayList<NewsCollection>();
		public String Snippet;
		public String Source;
		public String Title;
		public String Url;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;
			
			this.BreakingNews = (jsonInt("BreakingNews", jo) == 0) ? false : true;
			this.Date = jsonDate("Date", jo);
			this.Snippet = jsonString("Snippet", jo);
			this.Source = jsonString("Source", jo);
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);
			
			if(jo.has("NewsCollections"))
			{
				array = jo.getJSONArray("NewsCollections");
				len = array.length();
				NewsCollection nc;
				for(i = 0; i < len; i++)
				{
					nc = new NewsCollection();
					nc.inflateResponse(array.getJSONObject(i));
					this.NewsCollections.add(nc);
				}
			}
		}
	}
	
	public class NewsCollection
	{
		public String Name;
		public ArrayList<NewsArticle> NewsArticles = new ArrayList<NewsArticle>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;

			this.Name = jsonString("Name", jo);
			
			if(jo.has("NewsArticles"))
			{
				array = jo.getJSONArray("NewsArticles");
				len = array.length();
				NewsArticle na;
				for(i = 0; i < len; i++)
				{
					na = new NewsArticle();
					na.inflateResponse(array.getJSONObject(i));
					this.NewsArticles.add(na);
				}
			}
		}
	}
	
	public class NewsArticle
	{
		public Date Date;
		public String Snippet;
		public String Source;
		public String Title;
		public String Url;

		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Date = jsonDate("Date", jo);
			this.Snippet = jsonString("Snippet", jo);
			this.Source = jsonString("Source", jo);
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);			
		}
	}
	
	public class Phonebook
	{
		public int Total;
		public int Offset;
		public String LocalSerpUrl;
		public String Title;
		public ArrayList<PhonebookResult> Results = new ArrayList<PhonebookResult>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;
			
			this.Total = jsonInt("Total", jo);
			this.Offset = jsonInt("Offset", jo);
			this.LocalSerpUrl = jsonString("LocalSerpUrl", jo);
			this.Title = jsonString("Title", jo);
			if(jo.has("Results"))
			{
				array = jo.getJSONArray("Results");
				len = array.length();
				PhonebookResult pr;
				for(i = 0; i < len; i++)
				{
					pr = new PhonebookResult();
					pr.inflateResponse(array.getJSONObject(i));
					this.Results.add(pr);
				}
			}
		}
	}
	
	public class PhonebookResult
	{
		public String Title;
		public String Url;
		public String BusinessUrl;
		public String City;
		public String CountryOrRegion;
		public String DisplayUrl;
		public double Latitude;
		public double Longitude;
		public String PhoneNumber;
		public String PostalCode;
		public int ReviewCount;
		public String StateOrProvince;
		public String UniqueId;
		public double UserRating;
		public String Business;
		public String Address;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);
			this.BusinessUrl = jsonString("BusinessUrl", jo);
			this.City = jsonString("City", jo);
			this.CountryOrRegion = jsonString("CountryOrRegion", jo);
			this.DisplayUrl = jsonString("DisplayUrl", jo);
			this.Latitude = jsonDouble("Latitude", jo);
			this.Longitude = jsonDouble("Longitude", jo);
			this.PhoneNumber = jsonString("PhoneNumber", jo);
			this.PostalCode = jsonString("PostalCode", jo);
			this.ReviewCount = jsonInt("ReviewCount", jo);
			this.StateOrProvince = jsonString("StateOrProvince", jo);
			this.UniqueId = jsonString("UniqueId", jo);
			this.Business = jsonString("Business", jo);
			this.Address = jsonString("Address", jo);
		}
	}
	
	public class Query
	{
		public String SearchTerms;
		public String AlterationOverrideQuery;
		public String AlteredQuery;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.SearchTerms = jsonString("SearchTerms", jo);
			this.AlterationOverrideQuery = jsonString("AlterationOverrideQuery", jo);
			this.AlteredQuery = jsonString("AlteredQuery", jo);
		}
	}
	
	public class RelatedSearch
	{
		public ArrayList<RelatedSearchResult> Results = new ArrayList<RelatedSearchResult>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;

			if(jo.has("Results"))
			{
				array = jo.getJSONArray("Results");
				len = array.length();
				RelatedSearchResult rsr;
				for(i = 0; i < len; i++)
				{
					rsr = new RelatedSearchResult();
					rsr.inflateResponse(array.getJSONObject(i));
					this.Results.add(rsr);
				}
			}
		}
	}
	
	public class RelatedSearchResult
	{
		public String Title;
		public String Url;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);
		}
	}

	public class Spell
	{
		public int Total;
		public ArrayList<String> Results = new ArrayList<String>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Total = jsonInt("Total", jo);
			if(jo.has("Results"))
			{
				JSONArray array = jo.getJSONArray("Results");
				JSONObject obj;
				int len = array.length();
				for(int i = 0; i < len; i++)
				{
					obj = array.getJSONObject(i);
					if(obj.has("Value"))
					{
						this.Results.add(obj.getString("Value"));
					}
				}
			}
		}
	}
	
	public class Translation
	{
		public ArrayList<String> TranslatedTerms = new ArrayList<String>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			if(jo.has("Results"))
			{
				JSONArray array = jo.getJSONArray("Results");
				JSONObject obj;
				int len = array.length();
				for(int i = 0; i < len; i++)
				{
					obj = array.getJSONObject(i);
					if(obj.has("TranslatedTerm"))
					{
						this.TranslatedTerms.add(obj.getString("TranslatedTerm"));
					}
				}
			}
		}
	}
	
	public class Video
	{
		public int Offset;
		public int Total;
		public ArrayList<VideoResult> Results = new ArrayList<VideoResult>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Offset = jsonInt("Offset", jo);
			this.Total = jsonInt("Total", jo);
			if(jo.has("Results"))
			{
				JSONArray array = jo.getJSONArray("Results");
				int len = array.length();
				VideoResult vr;
				for(int i = 0; i < len; i++)
				{
					vr = new VideoResult();
					vr.inflateResponse(array.getJSONObject(i));
					this.Results.add(vr);
				}
			}
		}
	}
	
	public class VideoResult
	{
		public String Title;
		public String SourceTitle;
		public String RunTime;
		public String PlayUrl;
		public String ClickThroughPageUrl;
		public Thumbnail StaticThumbnail;

		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Title = jsonString("Title", jo);
			this.SourceTitle = jsonString("SourceTitle", jo);
			this.RunTime = jsonString("RunTime", jo);
			this.PlayUrl = jsonString("PlayUrl", jo);
			this.ClickThroughPageUrl = jsonString("ClickThroughPageUrl", jo);
			
			if(jo.has("StaticThumbnail"))
			{
				this.StaticThumbnail = new Thumbnail();
				this.StaticThumbnail.inflateResponse(jo.getJSONObject("StaticThumbnail"));
			}
		}
	}
	
	public class Thumbnail
	{
		public String Url;
		public String ContentType;
		public int Width;
		public int Height;
		public int FileSize;

		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Url = jsonString("Url", jo);
			this.ContentType = jsonString("ContentType", jo);
			this.Width = jsonInt("Width", jo);
			this.Height = jsonInt("Height", jo);
			this.FileSize = jsonInt("FileSize", jo);
		}
	}
	
	public class Web
	{
		public int Offset;
		public int Total;
		public ArrayList<WebResult> Results = new ArrayList<WebResult>();
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Offset = jsonInt("Offset", jo);
			this.Total = jsonInt("Total", jo);
			if(jo.has("Results"))
			{
				JSONArray array = jo.getJSONArray("Results");
				int len = array.length();
				WebResult wr;
				for(int i = 0; i < len; i++)
				{
					wr = new WebResult();
					wr.inflateResponse(array.getJSONObject(i));
					this.Results.add(wr);
				}
			}
		}
	}
	public class WebResult
	{
		public String DateTime;
		public String Description;
		public ArrayList<DeepLink> DeepLinks = new ArrayList<DeepLink>();
		public String DisplayUrl;
		public ArrayList<WebSearchTag> SearchTags = new ArrayList<WebSearchTag>();
		public String Title;
		public String Url;
		public String CacheUrl;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			int i;
			int len;
			JSONArray array;
			
			this.DateTime = jsonString("DateTime", jo);
			this.Description = jsonString("Description", jo);
			if(jo.has("DeepLinks"))
			{
				array = jo.getJSONArray("DeepLinks");
				len = array.length();
				DeepLink dl;
				for(i = 0; i < len; i++)
				{
					dl = new DeepLink();
					dl.inflateResponse(array.getJSONObject(i));
					this.DeepLinks.add(dl);
				}
			}
			this.DisplayUrl = jsonString("DisplayUrl", jo);
			if(jo.has("SearchTags"))
			{
				array = jo.getJSONArray("SearchTags");
				len = array.length();
				WebSearchTag wst;
				for(i = 0; i < len; i++)
				{
					wst = new WebSearchTag();
					wst.inflateResponse(array.getJSONObject(i));
					this.SearchTags.add(wst);
				}
			}
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);
			this.CacheUrl = jsonString("CacheUrl", jo);
		}
	}
	
	public class DeepLink
	{
		public String Title;
		public String Url;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Title = jsonString("Title", jo);
			this.Url = jsonString("Url", jo);
		}
	}
	
	public class WebSearchTag
	{
		public String Name;
		public String Value;
		
		protected void inflateResponse(JSONObject jo) throws JSONException
		{
			this.Name = jsonString("Name", jo);
			this.Value = jsonString("Value", jo);
		}
	}
}
