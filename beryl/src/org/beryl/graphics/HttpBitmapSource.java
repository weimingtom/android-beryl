package org.beryl.graphics;

import java.io.File;
import java.io.InputStream;

import org.beryl.net.SimpleFileDownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class HttpBitmapSource extends AbstractBitmapSource {

	private Uri remoteUri;
	private Uri localUri = null;
	
	public HttpBitmapSource(final Uri uri) {
		this.remoteUri = uri;
	}
	
	public HttpBitmapSource(Parcel in) {
		readFromParcel(in);
	}
	
	public boolean load(Context context, Options options) {
		boolean success = false;
		if(localUri == null) {
			downloadFile(context);
		}
		
		if(! isAvailable() || options.inJustDecodeBounds) {
			try {
				final InputStream is = context.getContentResolver().openInputStream(this.localUri);
				final Bitmap tempBitmap = BitmapFactory.decodeStream(is, null, options);
				
				if(! options.inJustDecodeBounds) {
					dispose();
					this.bitmap = tempBitmap;
				}
				
				is.close();
				success = true;
			} catch(Exception e) {
				dispose();
				success = false;
			}
		}
		
		return success;
	}

	private void downloadFile(final Context context) {
		SimpleFileDownloader downloader = new SimpleFileDownloader();
		File dest = downloader.download(context, remoteUri.getPath());
		localUri = Uri.parse(dest.getAbsolutePath());
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeParcelable(remoteUri, flags);
    	dest.writeParcelable(localUri, flags);
    	dest.writeParcelable(bitmap, flags);
    }

    public void readFromParcel(Parcel in) {
    	remoteUri = in.readParcelable(null);
    	localUri = in.readParcelable(null);
    	bitmap = in.readParcelable(null);
    }

    public static final Parcelable.Creator<HttpBitmapSource> CREATOR = new Parcelable.Creator<HttpBitmapSource>() {
        public HttpBitmapSource createFromParcel(Parcel in) {
            return new HttpBitmapSource(in);
        }

        public HttpBitmapSource[] newArray(int size) {
            return new HttpBitmapSource[size];
        }
    };
}
