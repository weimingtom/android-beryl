package org.beryl.speech;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;

public class TtsService extends Service implements OnInitListener, OnUtteranceCompletedListener
{
	TextToSpeech _tts = null;
	private boolean _isAvailable = false;
	private TtsServiceBinder _binder = new TtsServiceBinder();
	private boolean _stopOnUtteranceCompleted = false;
	final HashMap<String, String> _speakHashMap = new HashMap<String, String>();
	
	public class TtsServiceBinder extends Binder implements ITtsServiceProvider
	{
		public void speak(String message)
		{
			TtsService.this.speak(message);
		}

		public void stopSpeaking()
		{
			TtsService.this.stopSpeaking();
		}

		public boolean isReady()
		{
			return TtsService.this._isAvailable;
		}

		public boolean isSpeaking()
		{
			return TtsService.this.isSpeaking();
		}
		
		public void setStopOnUtteranceCompleted(boolean shouldStop)
		{
			TtsService.this.setStopOnUtteranceCompleted(shouldStop);
		}
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		_speakHashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "nothing");
		
		_tts = new TextToSpeech(this, this);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		this._isAvailable = false;
		_tts.shutdown();
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return this._binder;
	}

	public void onInit(int status)
	{
		if(status == TextToSpeech.SUCCESS)
		{
			_isAvailable = true;
			_tts.setOnUtteranceCompletedListener(this);
			flushSpeech();
		}
		else
		{
			_isAvailable = false;
			_tts.shutdown();
		}
	}

	private final LinkedList<String> _utterables = new LinkedList<String>();
	
	public void stopSpeaking()
	{
		synchronized(_utterables)
		{
			_utterables.clear();
		}
		
		if(_tts != null && _tts.isSpeaking())
		{
			_tts.stop();
		}
	}
	
	public void speak(String message)
	{
		synchronized(_utterables)
		{
			_utterables.add(message);
		}
		flushSpeech();
	}

	public void setStopOnUtteranceCompleted(boolean shouldStop)
	{
		_stopOnUtteranceCompleted = shouldStop;
	}
	
	private void flushSpeech()
	{
		if(this._isAvailable)
		{
			final LinkedList<String> temp = new LinkedList<String>();
			synchronized(_utterables)
			{
				temp.addAll(_utterables);
				_utterables.clear();
			}
			
			if(temp.size() > 0)
			{
					
				for(String message : temp)
				{
					_tts.speak(message, TextToSpeech.QUEUE_ADD, _speakHashMap);
				}
			}
			else if(_stopOnUtteranceCompleted)
			{
				this.stopSelf();
			}
		}
	}
	
	public void onUtteranceCompleted(String utteranceId)
	{
		boolean stopping = false;
		
		synchronized(_utterables)
		{
			if(_utterables.size() == 0 && _stopOnUtteranceCompleted)
			{
				stopping = true;
			}
		}
		
		if(! stopping)
		{
			// Make sure that there are no more requests in the queue.
			flushSpeech();
		}
		else
		{
			this.stopSelf();
		}
	}
	
	public boolean isSpeaking()
	{
		return (_tts != null && _tts.isSpeaking());
	}
}
