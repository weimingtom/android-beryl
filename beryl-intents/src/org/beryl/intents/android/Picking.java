package org.beryl.intents.android;

import org.beryl.intents.IntentHelper;

import android.content.Intent;

public class Picking {

	public static final Intent getImage() {
		return getImage("image/*");
	}
	
	public static final Intent getImage(String imageType) {
		return IntentHelper.getContentByType(imageType);
	}
}
