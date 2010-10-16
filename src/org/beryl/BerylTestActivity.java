package org.beryl;

import org.beryl.R;
import org.beryl.diagnostics.reporting.CrashReporter;
import org.beryl.web.search.bing.ActivityBingSearchResponseHandler;
import org.beryl.web.search.bing.BingSearchRequest;
import org.beryl.web.search.bing.BingSearchResponse;
import org.beryl.web.search.bing.BingSearchService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BerylTestActivity extends Activity implements OnClickListener
{
	EditText editQuery;
	Button runQuery;
	Button btnCrashMe;
	Button btnPrefs;
	TextView viewQuery;
	TextView targetResult;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CrashReporter.InitializeReporter(this.getApplication());
        CrashReporter.BindReporter(Thread.currentThread());
        CrashReporter.BindReporter(Thread.currentThread());
        setContentView(R.layout.layout_test);
        editQuery = (EditText) findViewById(R.id.txtQuery);
        runQuery = (Button) findViewById(R.id.btnSearch);
        btnCrashMe = (Button) findViewById(R.id.btnCrashMe);
        viewQuery = (TextView) findViewById(R.id.txtData);
        targetResult = (TextView) findViewById(R.id.txtResult);
        btnPrefs = (Button) findViewById(R.id.btnPrefs);
        runQuery.setOnClickListener(this);
        
        btnPrefs.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v)
			{
				Intent launcher = new Intent();
				launcher.setClass(BerylTestActivity.this, BerylPreferenceActivity.class);
				BerylTestActivity.this.startActivity(launcher);
			}
        });
        
		
        btnCrashMe.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v)
			{
				LinearLayout test = (LinearLayout)v;
				test.setOnClickListener(this);
			}
        	
        });
        
        BingSearchService.initializeService("400DEFE4AB195D9A509DA6109042113D20E459F3");
    }
    
    public void onClick(View v)
    {
		String query_string = editQuery.getText().toString();
		
		BingSearchRequest request = BingSearchService.createSearchRequest(query_string);
		//request.setAdRequest(10);
		request.setImageRequest(10);
		request.setInstantAnswerRequest();
		request.setMobileWebRequest(10);
		request.setNewsRequest(10, BingSearchRequest.News_Category__US);
		request.setPhonebookRequest(10);
		request.setRelatedSearchRequest();
		request.setSpellRequest();
		request.setTranslationRequest(BingSearchRequest.Translation_Language__English, BingSearchRequest.Translation_Language__French);
		request.setVideoRequest(10);
		request.setWebRequest(10);
		
		viewQuery.setText("Searching... " + request.getSearchUri());
		BingSearchService.beginSearchRequest(request, new SearchHandler(this));
	}

    public class SearchHandler extends ActivityBingSearchResponseHandler
    {
		public SearchHandler(Activity activity)
		{
			super(activity);
		}

		@Override
		public void handleSearchCompleted(BingSearchResponse response)
		{
			viewQuery.setText(response.toString());
			try
			{
				targetResult.setText("[Nothing]");
				targetResult.setText(response.getInstantAnswer().Results.get(0).Encarta.Value);
			}
			catch(Exception e)
			{
				
			}
		}

		@Override
		public void handleSearchError(Exception error)
		{
			viewQuery.setText(error.getMessage());
		}
    }
}