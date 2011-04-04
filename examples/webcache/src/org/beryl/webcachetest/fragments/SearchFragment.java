package org.beryl.webcachetest.fragments;

import org.beryl.webcachetest.R;
import org.beryl.widget.ViewBinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {

	Button SearchButton;
	EditText SearchQueryEditText;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_search, container, false);
		ViewBinder.bind(view, this, R.id.class);
		return view;
	}
	
	public void SearchButton_onClick(View v) {
		
	}
}
