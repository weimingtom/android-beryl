package org.beryl.webcachetest.fragments;

import org.beryl.web.cache.WebCacheOpenHelper;
import org.beryl.webcachetest.R;
import org.beryl.widget.ViewBinder;

import android.app.Fragment;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TestPanelFragment extends Fragment {

	Button TestCreateButton;
	Button TestUpdateButton;
	Button TestDeleteButton;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_testpanel, container, false);		
		ViewBinder.bind(view, this, R.id.class);
		return view;
	}
	
    public void startRunnable(Runnable r) {
    	Thread t = new Thread(r);
    	t.start();
    }
    
    public void TestCreateButton_onClick(View view) {
    	startRunnable(new Runnable() {
			@Override
			public void run() {
				ContextWrapper wrapper = new ContextWrapper(TestPanelFragment.this.getActivity());
				wrapper.deleteDatabase("webcache");
				WebCacheOpenHelper webcache = new WebCacheOpenHelper(TestPanelFragment.this.getActivity());
				webcache.getReadableDatabase();
				webcache.close();
			}
    	});
    }
    
	public void TestUpdateButton_onClick(View view) {
		startRunnable(new Runnable() {
			@Override
			public void run() {
				
				WebCacheOpenHelper webcache = new WebCacheOpenHelper(TestPanelFragment.this.getActivity(), 2);
				webcache.getReadableDatabase();
				webcache.close();
			}
    	});
    }
	
	public void TestDeleteButton_onClick(View view) {
		startRunnable(new Runnable() {
			@Override
			public void run() {
				ContextWrapper wrapper = new ContextWrapper(TestPanelFragment.this.getActivity());
				wrapper.deleteDatabase("webcache");
			}
    	});
	}
}
