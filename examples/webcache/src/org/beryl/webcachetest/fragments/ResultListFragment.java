package org.beryl.webcachetest.fragments;

import org.beryl.webcachetest.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResultListFragment extends ListFragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_resultlist, container, false);
		return view;
	}
}
