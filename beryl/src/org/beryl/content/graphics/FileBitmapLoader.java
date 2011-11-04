package org.beryl.content.graphics;

import org.beryl.content.SimpleLoaderBase;
import org.beryl.graphics.BitmapLoader;

import android.content.Context;
import android.graphics.Bitmap;

public class FileBitmapLoader extends SimpleLoaderBase<Bitmap> {

	private String filePath = null;
	
	public FileBitmapLoader(Context context) {
		super(context);
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	protected void loadData() {
		Bitmap result = null;
		
		if(filePath != null) {
			result = BitmapLoader.tryDecodeBitmapFileConsideringInstances(filePath);
		}
		
		if(result == null) {
			finishWithError(new Exception("Cannot load " + filePath));
		} else {
			finishWithResult(result);
		}
	}
}
