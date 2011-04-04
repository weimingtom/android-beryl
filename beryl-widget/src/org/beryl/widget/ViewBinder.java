package org.beryl.widget;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

/** Methods that automatically assign member variables that are of type {@link android.view.View} or a subclass of it.
 * Running ViewBinder against a Fragment or Activity replaces the manual <code>view.findViewById()</code> calls.
 * This method can also be used for container objects that have Views inside of them.
<h2>Requirements</h2>
<ul>
	<li>The View's field name in the Fragment or Activity class must match the R.id.* entry defined in the layout.</li>
	<li>Listeners must have the format FieldName_onEvent.
	For instance TestCreateButton's onClickListener will be defined as <code>public void TestCreateButton_onClick(View v)</code>
	This is similar to Visual C#'s naming standards.</li>
</ul>
<h2>Layout XML for Example</h2>
<pre class="code"><code class="xml">
&lt;LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"&gt;
	&lt;Button android:id="@+id/TestCreateButton" android:text="Create" /&gt;
	&lt;Button android:id="@+id/TestUpdateButton" android:text="Update" /&gt;
	&lt;Button android:id="@+id/TestDeleteButton" android:text="Delete" /&gt;
&lt;/LinearLayout&gt;
</code></pre>
<h2>Old way of binding layout views.</h2>
<pre class="code"><code class="java">
class MyFragment extends Fragment {
	Button TestCreateButton;
	Button TestUpdateButton;
	Button TestDeleteButton;
	// And other View/Widgets...

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_testpanel, container, false);
	
		// Bind Views
		TestCreateButton = (Button) view.findViewById(R.id.TestCreateButton);
		TestCreateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { android.util.Log.i("Test", "Clicked TestCreateButton"); }
		});
		TestUpdateButton = (Button) view.findViewById(R.id.TestUpdateButton);
		TestUpdateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { android.util.Log.i("Test", "Clicked TestUpdateButton"); }
		});
		TestDeleteButton = (Button) view.findViewById(R.id.TestDeleteButton);
		TestDeleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { android.util.Log.i("Test", "Clicked TestDeleteButton"); }
		});
		// And so on...
		
		return view;
	}
}
</code></pre>

<h2>New View Binder Method</h2>
<pre class="code"><code class="java">
class MyFragment extends Fragment {
	Button TestCreateButton;
	Button TestUpdateButton;
	Button TestDeleteButton;
	// And other View/Widgets...
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_testpanel, container, false);
		// Bind Views
		ViewBinder.bind(view, this, R.id.class);	// Yeah, that's it.
		return view;
	}
	
	// Look ma no explicit binding!
	public void TestCreateButton_onClick(View view) { android.util.Log.i("Test", "Clicked TestCreateButton"); }
	public void TestUpdateButton_onClick(View view) { android.util.Log.i("Test", "Clicked TestUpdateButton"); }
	public void TestDeleteButton_onClick(View view) { android.util.Log.i("Test", "Clicked TestDeleteButton"); }
}
</code></pre>
 */
public class ViewBinder {

	/**
	 * Bind all the views defined in the {@link android.app.Fragment} object to the layout defined.
	 * @param root The root of the layout. root.findViewById(R.id.*); is called.
	 * @param fragment The fragment that contains all the 
	 * @param rDotId
	 */
	public static void bind(View root, Fragment fragment, Class<?> rDotId) {
		ViewBindable bindable = new GenericViewBinder(fragment, root, rDotId);
		bindable.bindViews();
	}
	
	public static void bind(View root, Activity activity, Class<?> rDotId) {
		ViewBindable bindable = new GenericViewBinder(activity, root, rDotId);
		bindable.bindViews();
	}
	
	public static void bind(View root, Object object, Class<?> rDotId) {
		ViewBindable bindable = new GenericViewBinder(object, root, rDotId);
		bindable.bindViews();
	}
}
