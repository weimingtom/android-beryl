package org.beryl.speech;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class TtsServiceConnection implements ServiceConnection
{
	public ITtsServiceProvider TtsService = null;

	public boolean isAvailable()
	{
		return (TtsService != null);
	}
	
	public void onServiceConnected(ComponentName name, IBinder service)
	{
		TtsService = (ITtsServiceProvider)service;
	}

	public void onServiceDisconnected(ComponentName name)
	{
		TtsService.stopSpeaking();
	}
};
