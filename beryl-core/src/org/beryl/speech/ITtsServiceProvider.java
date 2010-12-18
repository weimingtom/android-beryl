package org.beryl.speech;

public interface ITtsServiceProvider
{
	boolean isReady();
	boolean isSpeaking();
	void speak(String message);
	void stopSpeaking();
	void setStopOnUtteranceCompleted(boolean shouldStop);
}
