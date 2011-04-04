package org.beryl.web.cache;

import org.beryl.schedule.AbstractTask;

public class DownloadFileTask extends AbstractTask<byte[]> {

	private final String url;
	
	public DownloadFileTask(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
