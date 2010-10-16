package org.beryl;

import org.beryl.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BerylPreferenceActivity extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		addPreferencesFromResource(R.xml.test_prefs);
	}
}
