package org.beryl.web.search.bing;

import java.util.ArrayList;
import java.util.Set;

import android.net.Uri;
import android.os.Bundle;

public class BingSearchRequest {
	private Bundle _request = new Bundle();

	private static final String SearchRequestBaseUri_Json = "http://api.bing.net/json.aspx";

	protected BingSearchRequest(String query) {
		setString("AppId", BingSearchService._api_key);
		setString("Version", "2.2");
		setString("Market", "en-US");
		setString("Query", query);
		setString("JsonType", "raw");
		setString("Web.Count", "1");
		setString("Adult", Adult__Strict);
	}

	public void setQuery(String query) {
		_request.putString("Query", query);
	}

	public void setAdRequest(int PageNumber, int MlAdCount, int SbAdCount,
			int AdUnitId, String ChannelId, int PropertyId) {
		attachSearchSource("Ad");
		setUInt("Ad.PageNumber", PageNumber);
		setUInt("Ad.MlAdCount", MlAdCount);
		setUInt("Ad.SbAdCount", SbAdCount);
		setUInt("Ad.AdUnitId", AdUnitId);
		setString("Ad.ChannelId", ChannelId);
		setUInt("Ad.PropertyId", PropertyId);
	}

	public static final String Image_Filter__SizeSmall = "Size:Small";
	public static final String Image_Filter__SizeMedium = "Size:Medium";
	public static final String Image_Filter__SizeLarge = "Size:Large";
	public static final String Image_Filter__SizeHeightX = "Size:Height:";
	public static final String Image_Filter__SizeWidthX = "Size:Width:";
	public static final String Image_Filter__AspectSquare = "Aspect:Square";
	public static final String Image_Filter__AspectWide = "Aspect:Wide";
	public static final String Image_Filter__AspectTall = "Aspect:Tall";
	public static final String Image_Filter__ColorColor = "Color:Color";
	public static final String Image_Filter__ColorMonochrome = "Color:Monochrome";

	public void setImageRequest(int Count) {
		setImageRequest(Count, 0, null);
	}

	public void setImageRequest(int Count, int Offset) {
		setImageRequest(Count, Offset, null);
	}

	public void setImageRequest(int Count, int Offset, ArrayList<String> Filters) {
		attachSearchSource("Image");
		setUInt("Image.Count", Count);
		setUInt("Image.Offset", Offset);
		appendStringArrayList("Image.Filter", Filters);
	}

	public void addImageRequestFilter(String Filter) {
		appendString("Image.Filter", Filter);
	}

	public void setInstantAnswerRequest() {
		attachSearchSource("InstantAnswer");
	}

	public static final String MobileWeb_Option__DisableHostCollapsing = "DisableHostCollapsing";
	public static final String MobileWeb_Option__DisableQueryAlterations = "DisableQueryAlterations";

	public void setMobileWebRequest(int Count) {
		setMobileWebRequest(Count, 0, null);
	}

	public void setMobileWebRequest(int Count, int Offset) {
		setMobileWebRequest(Count, Offset, null);
	}

	public void setMobileWebRequest(int Count, int Offset,
			ArrayList<String> Options) {
		attachSearchSource("MobileWeb");
		setUInt("MobileWeb.Count", Count);
		setUInt("MobileWeb.Offset", Offset);
		appendStringArrayList("MobileWeb.Options", Options);
	}

	public void addMobileWebRequestOptions(String Option) {
		appendString("MobileWeb.Options", Option);
	}

	public static final String News_Category__Business = "rt_Business";
	public static final String News_Category__Entertainment = "rt_Entertainment";
	public static final String News_Category__Health = "rt_Health";
	public static final String News_Category__Political = "rt_Political";
	public static final String News_Category__Scientific = "rt_Scientific";
	public static final String News_Category__Sports = "rt_Sports";
	public static final String News_Category__US = "rt_US";
	public static final String News_Category__World = "rt_World";
	public static final String News_Category_Local = "rt_Local";
	public static final String News_Category__ScienceAndTechnology = "rt_ScienceAndTechnology";

	public static final String News_SortBy__Date = "Date";
	public static final String News_SortBy__Relevance = "Relevance";

	public void setNewsRequest(int Count, String Category) {
		setNewsRequest(Count, 0, Category);
	}

	public void setNewsRequest(int Count, int Offset, String Category) {
		setNewsRequest(Count, Offset, Category, null, null);
	}

	public void setNewsRequest(int Count, int Offset, String Category,
			String LocationOverride, String SortBy) {
		attachSearchSource("News");
		setString("News.Category", Category);
		setString("News.LocationOverride", LocationOverride);

		setUInt("News.Count", Count);
		setUInt("News.Offset", Offset);
		setString("News.SortBy", SortBy);
	}

	public static final String Phonebook_FileType__YellowPages = "YP";
	public static final String Phonebook_FileType__WhitePages = "WP";

	public static final String Phonebook_SortBy__Default = "Date";
	public static final String Phonebook_SortBy__Distance = "Distance";
	public static final String Phonebook_SortBy__Relevance = "Relevance";

	public void setPhonebookRequest(int Count) {
		setPhonebookRequest(Count, 0);
	}

	public void setPhonebookRequest(int Count, int Offset) {
		setPhonebookRequest(Count, Offset, Phonebook_FileType__YellowPages,
				null, null);
	}

	public void setPhonebookRequest(int Count, int Offset, String FileType) {
		setPhonebookRequest(Count, Offset, FileType, null, null);
	}

	public void setPhonebookRequest(int Count, int Offset, String FileType,
			String SortBy, String LocId) {
		attachSearchSource("Phonebook");
		setUInt("Phonebook.Count", Count);
		setUInt("Phonebook.Offset", Offset);
		setString("Phonebook.FileType", FileType);
		setString("Phonebook.SortBy", SortBy);
		setString("Phonebook.LocId", LocId);
	}

	public void setRelatedSearchRequest() {
		attachSearchSource("RelatedSearch");
	}

	public static final String Market__Arabic_Arabia = "ar-XA";
	public static final String Market__Bulgarian_Bulgaria = "bg-BG";
	public static final String Market__Czech_CzechRepublic = "cs-CZ";
	public static final String Market__Danish_Denmark = "da-DK";
	public static final String Market__German_Austria = "de-AT";
	public static final String Market__German_Switzerland = "de-CH";
	public static final String Market__German_Germany = "de-DE";
	public static final String Market__Greek_Greece = "el-GR";
	public static final String Market__English_Australia = "en-AU";
	public static final String Market__English_Canada = "en-CA";
	public static final String Market__English_UnitedKingdom = "en-GB";
	public static final String Market__English_Indonesia = "en-ID";
	public static final String Market__English_Ireland = "en-IE";
	public static final String Market__English_India = "en-IN";
	public static final String Market__English_Malaysia = "en-MY";
	public static final String Market__English_NewZealand = "en-NZ";
	public static final String Market__English_Philippines = "en-PH";
	public static final String Market__English_Singapore = "en-SG";
	public static final String Market__English_UnitedStates = "en-US";
	public static final String Market__English_SouthAfrica = "en-ZA";
	public static final String Market__Spanish_Argentina = "es-AR";
	public static final String Market__Spanish_Chile = "es-CL";
	public static final String Market__Spanish_Spain = "es-ES";
	public static final String Market__Spanish_Mexico = "es-MX";
	public static final String Market__Spanish_UnitedStates = "es-US";
	public static final String Market__Spanish_LatinAmerica = "es-XL";
	public static final String Market__Estonian_Estonia = "et-EE";
	public static final String Market__Finnish_Finland = "fi-FI";
	public static final String Market__French_Belgium = "fr-BE";
	public static final String Market__French_Canada = "fr-CA";
	public static final String Market__French_Switzerland = "fr-CH";
	public static final String Market__French_France = "fr-FR";
	public static final String Market__Hebrew_Israel = "he-IL";
	public static final String Market__Croatian_Croatia = "hr-HR";
	public static final String Market__Hungarian_Hungary = "hu-HU";
	public static final String Market__Italian_Italy = "it-IT";
	public static final String Market__Japanese_Japan = "ja-JP";
	public static final String Market__Korean_Korea = "ko-KR";
	public static final String Market__Lithuanian_Lithuania = "lt-LT";
	public static final String Market__Latvian_Latvia = "lv-LV";
	public static final String Market__Norwegian_Norway = "nb-NO";
	public static final String Market__Dutch_Belgium = "nl-BE";
	public static final String Market__Dutch_Netherlands = "nl-NL";
	public static final String Market__Portuguese_Brazil = "pt-BR";
	public static final String Market__Portuguese_Portugal = "pt-PT";
	public static final String Market__Romanian_Romania = "ro-RO";
	public static final String Market__Russian_Russia = "ru-RU";
	public static final String Market__Slovak_SlovakRepublic = "sk-SK";
	public static final String Market__Slovenian_Slovenia = "sl-SL";
	public static final String Market__Swedish_Sweden = "sv-SE";
	public static final String Market__Thai_Thailand = "th-TH";
	public static final String Market__Ukrainian_Ukraine = "uk-UA";
	public static final String Market__Chinese_China = "zh-CN";
	public static final String Market__Chinese_HongKongSAR = "zh-HK";
	public static final String Market__Chinese_Taiwan = "zh-TW";

	public static final String Adult__Off = "Off";
	public static final String Adult__Moderate = "Moderate";
	public static final String Adult__Strict = "Strict";

	public static final String SearchOption__DisableLocationDetection = "DisableLocationDetection";
	public static final String SearchOption__EnableHighlighting = "EnableHighlighting";

	public void setSearchRequestParameters(String Market, String Adult,
			String UILanguage, double Latitude, double Longitude,
			double Radius, ArrayList<String> Options) {
		setString("Market", Market);
		setString("Adult", Adult);
		setString("UILanguage", UILanguage);
		setDouble("Latitude", Latitude);
		setDouble("Longitude", Longitude);
		setDouble("Radius", Radius);
		appendStringArrayList("Options", Options);
	}

	public void addSearchRequestOptions(String Option) {
		appendString("Options", Option);
	}

	public void setSpellRequest() {
		attachSearchSource("Spell");
	}

	public static final String Translation_Language__Arabic = "Ar";
	public static final String Translation_Language__SimplifiedChinese = "zh-CHS";
	public static final String Translation_Language__TraditionalChinese = "zh-CHT";
	public static final String Translation_Language__Dutch = "Nl";
	public static final String Translation_Language__English = "En";
	public static final String Translation_Language__French = "Fr";
	public static final String Translation_Language__German = "De";
	public static final String Translation_Language__Italian = "It";
	public static final String Translation_Language__Japanese = "Ja";
	public static final String Translation_Language__Korean = "Ko";
	public static final String Translation_Language__Polish = "Pl";
	public static final String Translation_Language__Portuguese = "Pt";
	public static final String Translation_Language__Russian = "Ru";
	public static final String Translation_Language__Spanish = "Es";

	public void setTranslationRequest(String SourceLanguage,
			String TargetLanguage) {
		attachSearchSource("Translation");
		setString("Translation.SourceLanguage", SourceLanguage);
		setString("Translation.TargetLanguage", TargetLanguage);
	}

	public static final String Video_Filter__DurationShort = "Duration:Short";
	public static final String Video_Filter__DurationMedium = "Duration:Medium";
	public static final String Video_Filter__DurationLong = "Duration:Long";
	public static final String Video_Filter__AspectStandard = "Aspect:Standard";
	public static final String Video_Filter__AspectWidescreen = "Aspect:Widescreen";
	public static final String Video_Filter__ResolutionLow = "Resolution:Low";
	public static final String Video_Filter__ResolutionMedium = "Resolution:Medium";
	public static final String Video_Filter__ResolutionHigh = "Resolution:High";

	public static final String Video_SortBy__Date = "Date";
	public static final String Video_SortBy__Relevance = "Relevance";

	public void setVideoRequest(int Count) {
		setVideoRequest(Count, 0);
	}

	public void setVideoRequest(int Count, int Offset) {
		setVideoRequest(Count, Offset, null, null);
	}

	public void setVideoRequest(int Count, int Offset,
			ArrayList<String> Filters, String SortBy) {
		attachSearchSource("Video");
		setUInt("Video.Count", Count);
		setUInt("Video.Offset", Offset);
		setStringArrayList("Video.Filters", Filters);
		setString("Video.Offset", SortBy);
	}

	public void addVideoRequestFilters(String Filter) {
		appendString("Video.Filters", Filter);
	}

	public static final String Web_FileType__WordDocument = "DOC";
	public static final String Web_FileType__AutodeskDrawing = "DWF";
	public static final String Web_FileType__RSS = "FEED";
	public static final String Web_FileType__HTM = "HTM";
	public static final String Web_FileType__HTML = "HTML";
	public static final String Web_FileType__PDF = "PDF";
	public static final String Web_FileType__PowerPointPresentation = "PPT";
	public static final String Web_FileType__PostScript = "PS";
	public static final String Web_FileType__RichTextDocument = "RTF";
	public static final String Web_FileType__Text = "TEXT";
	public static final String Web_FileType__TXT = "TXT";
	public static final String Web_FileType__ExcelWorkbook = "XLS";

	public static final String Web_Option__DisableHostCollapsing = "DisableHostCollapsing";
	public static final String Web_Option__DisableQueryAlterations = "DisableQueryAlterations";

	public void setWebRequest(int Count) {
		setWebRequest(Count, 0);
	}

	public void setWebRequest(int Count, int Offset) {
		setWebRequest(Count, Offset, null, null, null);
	}

	public void setWebRequest(int Count, int Offset, String FileType,
			ArrayList<String> Options, ArrayList<String> SearchTags) {
		attachSearchSource("Web");
		setUInt("Web.Count", Count);
		setUInt("Web.Offset", Offset);
		setString("Web.FileType", FileType);
		setStringArrayList("Web.Options", Options);
		setStringArrayList("Web.SearchTags", SearchTags);
	}

	private void setStringArrayList(String parameter, ArrayList<String> value) {
		if (_request.containsKey(parameter)) {
			_request.remove(parameter);
		}

		appendStringArrayList(parameter, value);
	}

	private void setString(String parameter, String value) {
		if (value != null) {
			_request.putString(parameter, value);
		}
	}

	private void setUInt(String parameter, int value) {
		if (value >= 0) {
			_request.putInt(parameter, value);
		}
	}

	private void setDouble(String parameter, double value) {
		_request.putDouble(parameter, value);
	}

	private void attachSearchSource(String source_name) {
		appendString("Sources", source_name);
	}

	private void appendString(String parameter, String value) {
		
		if(value != null) {
			ArrayList<String> strings;
			if (_request.containsKey(parameter)) {
				strings = _request.getStringArrayList(parameter);
				strings.add(value);
				_request.putStringArrayList(parameter, strings);
			} else {
				strings = new ArrayList<String>();
				strings.add(value);
				_request.putStringArrayList(parameter, strings);
			}
		}
	}

	private void appendStringArrayList(String parameter, ArrayList<String> value) {
		if (value != null) {
			ArrayList<String> strings;
			if (_request.containsKey(parameter)) {
				strings = _request.getStringArrayList(parameter);
				strings.addAll(value);
				_request.putStringArrayList(parameter, strings);
			} else {
				_request.putStringArrayList(parameter, value);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String getSearchUri() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbi;
		sb.append(SearchRequestBaseUri_Json);
		sb.append("?");
		Set<String> keys = _request.keySet();
		Object data_handle;
		boolean first_element = true;
		ArrayList<Object> object_list;

		cleanupBundle();

		if (_request.containsKey("Sources") == false) {
			sb.append("Sources");
			sb.append("=");
			sb.append("Web");
			first_element = false;
		}

		for (String key : keys) {
			data_handle = _request.get(key);

			if (first_element) {
				first_element = false;
			} else {
				sb.append("&");
			}

			sb.append(key);
			sb.append("=");

			if (data_handle instanceof String || data_handle instanceof Integer
					|| data_handle instanceof Double) {
				sb.append(SanitizeParameter(data_handle.toString()));
			} else if (data_handle instanceof ArrayList<?>) {
				object_list = (ArrayList<Object>) data_handle;
				sbi = new StringBuilder();
				int sli;
				int sllen = object_list.size();
				for (sli = 0; sli < sllen; sli++) {
					if (sli > 0) {
						sbi.append("+");
					}
					sbi.append(SanitizeParameter(object_list.get(sli)
							.toString()));
				}
				sb.append(sbi.toString());
			} else {
				android.util.Log.i("Request", data_handle.toString());
			}
		}

		return sb.toString();
	}

	private void cleanupBundle() {
		Set<String> keys = _request.keySet();
		Object data;
		ArrayList<String> del_targets = new ArrayList<String>();
		for (String key : keys) {
			if (_request.get(key) == null) {
				del_targets.add(key);
			} else {
				data = _request.get(key);
				if (data instanceof String || data instanceof Integer
						|| data instanceof Double) {
					if (data.toString().length() == 0) {
						del_targets.add(key);
					}
				}
			}
		}

		int len = del_targets.size();
		for (int i = 0; i < len; i++) {
			_request.remove(del_targets.get(i));
		}
	}

	private String SanitizeParameter(String param) {
		return Uri.encode(param);
	}
}
