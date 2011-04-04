package org.beryl.webcachetest.fragments;

import org.beryl.webcachetest.R;
import org.beryl.widget.ViewBinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageDisplayFragment extends Fragment {

	ImageView DisplayImageView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_imagedisplay, container, false);
		ViewBinder.bind(view, this, R.id.class);
		return view;
	}
}
