package org.beryl.diagnostics.reporting;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.Html;
import android.widget.Toast;

public class CrashReporter implements UncaughtExceptionHandler
{
	public static final String ReportMode_Silent = "silent";
	public static final String ReportMode_Activity = "activity";
	
	private static String InternalPreferenceFallback_ReporterMode = ReportMode_Activity;
	private static boolean InternalPreferenceFallback_ReporterEnabled = false;
	
	private static Application ApplicationContext = null;
	private static String MetaData_ApplicationName = null;
	private static String MetaData_ApplicationPackageName = null;
	private static String MetaData_ApplicationVersionCode = null;
	private static String MetaData_ApplicationVersionName = null;
	private static String MetaData_UserKey = null;
	
	private static String CrashReporter_ServiceEndpointUri = "http://192.168.1.102:8888/api/crashreporter/submit";
	private static String PreferenceString_ReporterMode = null;
	private static String PreferenceString_ReporterEnabled = null;

	public static final String MetaDataTag_PreferenceString_UserKey = "BerylCrashReporter_UserKey";
	public static final String MetaDataTag_PreferenceString__ReporterMode = "BerylCrashReporter_ReporterMode";
	public static final String MetaDataTag_PreferenceString__ReporterEnabled = "BerylCrashReporter_Enabled";
	public static final String MetaDataTag_PreferenceString__ServiceEndpoint = "BerylCrashReporter_ServiceEndpoint";
	
	public static void InitializeReporter(final Application appcontext)
	{
		if(CrashReporter.ApplicationContext == null)
		{
			CrashReporter.ApplicationContext = appcontext;
			final PackageManager pm = appcontext.getPackageManager();
			try
			{
				final String package_name = appcontext.getPackageName();
				String metadata_userkey = "";
				String app_name = "";
				String version_code = "";
				String version_name = "";
				String service_endpoint = CrashReporter.CrashReporter_ServiceEndpointUri;
				String prefname_mode = null;
				String prefname_enabled = null;
				final PackageInfo pkginfo = pm.getPackageInfo(package_name, PackageManager.GET_META_DATA);
				if(pkginfo != null)
				{
					version_code = Integer.toString(pkginfo.versionCode);
					version_name = pkginfo.versionName;
									
					final ApplicationInfo appinfo = pm.getApplicationInfo(package_name, PackageManager.GET_META_DATA);
					if(appinfo != null)
					{
						app_name = pm.getApplicationLabel(appinfo).toString();
						
						final Bundle metadata = appinfo.metaData;
						if(metadata != null)
						{
							prefname_mode = metadata.getString(CrashReporter.MetaDataTag_PreferenceString__ReporterMode);
							prefname_enabled = metadata.getString(CrashReporter.MetaDataTag_PreferenceString__ReporterEnabled);
							metadata_userkey = metadata.getString(CrashReporter.MetaDataTag_PreferenceString_UserKey);
							
							if(metadata.containsKey(CrashReporter.MetaDataTag_PreferenceString__ServiceEndpoint))
							{
								service_endpoint = metadata.getString(CrashReporter.MetaDataTag_PreferenceString__ServiceEndpoint);
							}
						}
					}
				}
	
				CrashReporter.MetaData_UserKey = metadata_userkey;
				CrashReporter.MetaData_ApplicationName = app_name;
				CrashReporter.MetaData_ApplicationPackageName = package_name;
				CrashReporter.MetaData_ApplicationVersionCode = version_code;
				CrashReporter.MetaData_ApplicationVersionName = version_name;
				CrashReporter.CrashReporter_ServiceEndpointUri = service_endpoint;
				CrashReporter.PreferenceString_ReporterMode = prefname_mode;
				CrashReporter.PreferenceString_ReporterEnabled = prefname_enabled;
			}
			catch (Exception e)
			{
			}
		}
	}
	
	public static void BindReporter(final Thread thread)
	{
		try
		{
			boolean createnew = true;
			int i;
			Thread t;
			int len;
			Thread[] threads;
			UncaughtExceptionHandler ueh;
			
			ThreadGroup parent = thread.getThreadGroup();
			while(parent.getParent() != null && parent.getParent() != parent)
			{
				parent = parent.getParent();
			}
			
			threads = new Thread[parent.activeCount()];
			len = parent.enumerate(threads);
			
			for(i = 0; i < len; i++)
			{
				t = threads[i];
				ueh = t.getUncaughtExceptionHandler();
				if(ueh != null)
				{
					if(ueh.getClass().equals(CrashReporter.class))
					{
						createnew = false;
					}
				}
				if(createnew)
					t.setUncaughtExceptionHandler(new CrashReporter(t.getUncaughtExceptionHandler()));
			}
		}
		catch(Exception e)
		{
			
		}
	}

	private final UncaughtExceptionHandler _handler;
	public CrashReporter(UncaughtExceptionHandler superhandler)
	{
		_handler = superhandler;
	}
	
	public void uncaughtException(Thread thread, Throwable ex)
	{
		try
		{
			final CrashReportParcel parcel = new CrashReportParcel(thread, ex);
			final ICrashParachute parachute = CrashReporter.CreateParachute (parcel);
			
			if (parachute != null)
			{
				parachute.deploy();
			}
		}
		catch(Exception e)
		{
			
		}
		_handler.uncaughtException(thread, ex);
	}
	
	private static ICrashParachute CreateParachute(CrashReportParcel parcel)
	{
		final Context context = CrashReporter.ApplicationContext;
		ICrashParachute result = null;
		boolean isenabled = InternalPreferenceFallback_ReporterEnabled;
		String mode = CrashReporter.InternalPreferenceFallback_ReporterMode;
		
		if(CrashReporter.ApplicationContext != null)
		{
			if(CrashReporter.PreferenceString_ReporterMode != null ||
				CrashReporter.PreferenceString_ReporterEnabled != null)
			{
				final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
				if (prefs != null)
				{
					if(CrashReporter.PreferenceString_ReporterMode != null)
					{
						mode = prefs.getString(CrashReporter.PreferenceString_ReporterMode, CrashReporter.ReportMode_Activity);
					}
					if(CrashReporter.PreferenceString_ReporterEnabled != null)
					{
						isenabled = prefs.getBoolean(CrashReporter.PreferenceString_ReporterEnabled, false);
					}
				}
			}
		}
		
		// If the reporter is enabled then create the parachute.
		if(isenabled)
		{
			if(mode.compareToIgnoreCase(CrashReporter.ReportMode_Activity) == 0)
			{
				result = new PendingActivityCrashParachute(parcel, context);
			}
			else if (mode.compareToIgnoreCase(CrashReporter.ReportMode_Silent) == 0)
			{
				result = new SilentCrashParachute(parcel);
			}
		}
		
		return result;
	}

	private static final int NotificationId_DisplayActivity = 2312321;
	
	static class PendingActivityCrashParachute implements ICrashParachute
	{
		private final CrashReportParcel _parcel;
		private final Context _context;
		
		public PendingActivityCrashParachute(final CrashReportParcel parcel, final Context context)
		{
			_parcel = parcel;
			_context = context;
		}

		
		public void deploy()
		{
			try
			{
				final NotificationManager nm = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
				PendingIntent pi;
				Intent i = new Intent(_context, CrashReportActivity.class);
				i.putExtra("CrashParcel", _parcel);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				
				pi = PendingIntent.getActivity(_context, 0, i, 0);
				
				Notification notification = new Notification();
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				notification.icon = android.R.drawable.stat_notify_error;
				notification.setLatestEventInfo(_context, _parcel.ApplicationName, Html.fromHtml("has crashed <b>click</b> to send bug report."), pi);
				nm.notify(CrashReporter.NotificationId_DisplayActivity, notification);
			}
			catch(Exception e)
			{
				Toast.makeText(_context, "Crash Report Activity is not registered.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	static class SilentCrashParachute implements ICrashParachute
	{
		private final CrashReportParcel _parcel;
		
		public SilentCrashParachute(CrashReportParcel parcel)
		{
			_parcel = parcel;
		}
		
		public void deploy()
		{
			CrashReportClient client = new CrashReportClient(_parcel);
			Thread t = new Thread (client);
			t.setName("Beryl Crash Reporter");
			t.run();
		}
	}
	
	static class CrashReportClient implements Runnable
	{
		final CrashReportParcel _parcel;
		public CrashReportClient(CrashReportParcel parcel)
		{
			_parcel = parcel;
		}
		public void run()
		{
			try
			{
				final DefaultHttpClient client = new DefaultHttpClient();
				HttpParams params = client.getParams();
				HttpConnectionParams.setConnectionTimeout(params, 1500);
				HttpConnectionParams.setSoTimeout(params, 1500);
				client.setParams(params);

				final HttpPost postmessage = new HttpPost(_parcel.ServiceEndpointUri);
				
				// Populate the message
				ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
				
				nvp.add(new BasicNameValuePair("type", "android"));
				
				nvp.add(new BasicNameValuePair("UserKey", _parcel.UserKey));
				nvp.add(new BasicNameValuePair("ApplicationId", _parcel.ApplicationPackageName));
				nvp.add(new BasicNameValuePair("ErrorKey", _parcel.ErrorKey));
				nvp.add(new BasicNameValuePair("UserComment", _parcel.UserComment));
				
				nvp.add(new BasicNameValuePair("ApplicationInfo", _parcel.ApplicationInfo.toString()));
				nvp.add(new BasicNameValuePair("ThreadInfo", _parcel.ThreadInfo.toString()));
				nvp.add(new BasicNameValuePair("ExceptionInfo", _parcel.ExceptionInfo.toString()));
				nvp.add(new BasicNameValuePair("DeviceInfo", _parcel.DeviceInfo.toString()));
				postmessage.setEntity(new UrlEncodedFormEntity(nvp));				
				
				client.execute(postmessage);
			}
			catch(Exception e)
			{
				android.util.Log.e("Beryl", "error", e);
			}
		}
		
	}
	static interface ICrashParachute
	{
		void deploy();
	}
	
	static class CrashReportParcel implements Parcelable
	{
		public String UserKey;
		public String ApplicationName;
		public String ApplicationPackageName;
		public String ApplicationVersionCode;
		public String ApplicationVersionName;
		public String ServiceEndpointUri;
		public String ErrorKey;
		public String UserComment;
		
		public JSONObject ThreadInfo;
		public JSONObject ExceptionInfo;
		public JSONObject DeviceInfo;
		public JSONObject ApplicationInfo;
		
		public static final Parcelable.Creator<CrashReportParcel> CREATOR = new Parcelable.Creator<CrashReportParcel>() {
	        public CrashReportParcel createFromParcel(Parcel in) {
	            return new CrashReportParcel(in);
	        }

	        public CrashReportParcel[] newArray(int size) {
	            return new CrashReportParcel[size];
	        }
	    };
	    
		public CrashReportParcel(final Thread thread, final Throwable ex)
		{
			UserKey = CrashReporter.MetaData_UserKey;
			ApplicationName = CrashReporter.MetaData_ApplicationName;
			ApplicationPackageName = CrashReporter.MetaData_ApplicationPackageName;
			ApplicationVersionCode = CrashReporter.MetaData_ApplicationVersionCode;
			ApplicationVersionName = CrashReporter.MetaData_ApplicationVersionName;
			ServiceEndpointUri = CrashReporter.CrashReporter_ServiceEndpointUri;
			UserComment = "";
			
			try
			{
				ThreadInfo = new JSONObject();
				ThreadInfo.put("name", thread.getName());
				ThreadInfo.put("state", thread.getState().name());
				ThreadInfo.put("priority", thread.getPriority());
				
				ExceptionInfo = new JSONObject();
				
				ExceptionInfo.put("localizedmessage", ex.getLocalizedMessage());
				ExceptionInfo.put("message", ex.getMessage());
	
				JSONArray jsonstacktrace = new JSONArray();
				
				StackTraceElement[] stacktracedata = ex.getStackTrace();
				int i;
				final int len = stacktracedata.length;
				
				for(i = 0; i < len; i++)
				{
					if(i == 0)
					{
						ErrorKey = stacktracedata[i].toString();
					}
					jsonstacktrace.put(stacktracedata[i].toString());
				}
				
				ExceptionInfo.put("stacktrace", jsonstacktrace);
				
				DeviceInfo = new JSONObject();
				DeviceInfo.put("board", Build.BOARD);
				DeviceInfo.put("brand", Build.BRAND);
				DeviceInfo.put("device", Build.DEVICE);
				DeviceInfo.put("model", Build.MODEL);
				DeviceInfo.put("product", Build.PRODUCT);
				DeviceInfo.put("androidrelease", Build.VERSION.RELEASE);
				DeviceInfo.put("apilevel", Build.VERSION.SDK);
				
				ApplicationInfo = new JSONObject();
				ApplicationInfo.put("name", ApplicationName);
				ApplicationInfo.put("packagename", ApplicationPackageName);
				ApplicationInfo.put("versionname", ApplicationVersionName);
				ApplicationInfo.put("versioncode", ApplicationVersionCode);
			}
			catch(Exception e)
			{
				
			}
		}
		
		private CrashReportParcel(Parcel in)
		{
	        readFromParcel(in);
	    }

		public int describeContents()
		{
			return 0;
		}

		public void readFromParcel(Parcel in)
		{
			String buffer;
			
			UserKey = in.readString();
			ApplicationName = in.readString();
			ApplicationPackageName = in.readString();
			ApplicationVersionCode = in.readString();
			ApplicationVersionName = in.readString();
			ServiceEndpointUri = in.readString();			
			ErrorKey = in.readString();
			UserComment = in.readString();
			
	        buffer = in.readString();
	        try
	        {
	        	ThreadInfo = new JSONObject(buffer);
	        } catch(Exception e) {}
	        
	        buffer = in.readString();
	        try
	        {
	        	ExceptionInfo = new JSONObject(buffer);
	        } catch(Exception e) {}
	        
	        buffer = in.readString();
	        try
	        {
	        	DeviceInfo = new JSONObject(buffer);
	        } catch(Exception e) {}
	        
	        buffer = in.readString();
	        try
	        {
	        	ApplicationInfo = new JSONObject(buffer);
	        } catch(Exception e) {}
	    }
		
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeString(UserKey);
			dest.writeString(ApplicationName);
			dest.writeString(ApplicationPackageName);
			dest.writeString(ApplicationVersionCode);
			dest.writeString(ApplicationVersionName);
			dest.writeString(ServiceEndpointUri);
			dest.writeString(ErrorKey);
			dest.writeString(UserComment);
			
			dest.writeString(ThreadInfo.toString());
			dest.writeString(ExceptionInfo.toString());
			dest.writeString(DeviceInfo.toString());
			dest.writeString(ApplicationInfo.toString());
		}
	}
}
