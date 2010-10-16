package org.beryl.diagnostics.reporting;

import org.beryl.R;
import org.beryl.diagnostics.reporting.CrashReporter.CrashReportParcel;
import org.beryl.diagnostics.reporting.CrashReporter.SilentCrashParachute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CrashReportActivity extends Activity
{
	Button btnSendReport;
	Button btnCancel;
	EditText txtComment;
	CrashReportParcel _parcel = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_crashreporter);
		
		final Intent params = this.getIntent();
		final Bundle extras = params.getExtras();
		if (extras != null)
		{
			_parcel = extras.getParcelable("CrashParcel");
		}
		
		btnSendReport = (Button)findViewById(R.id.btnSubmitReport);
		btnCancel = (Button)findViewById(R.id.btnCloseActivity);
		txtComment = (EditText)findViewById(R.id.txtComment);
		btnSendReport.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				final String comment = CrashReportActivity.this.txtComment.getText().toString();
				
				if(comment.length() > 0)
				{
					_parcel.UserComment = comment;
				}

				SilentCrashParachute parachute = new SilentCrashParachute(_parcel);
				parachute.deploy();
				
				CrashReportActivity.this.finish();
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				CrashReportActivity.this.finish();
			}
		});
	}
}
