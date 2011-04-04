package org.beryl.widget;

import android.view.View;

/** Methods that automatically assign member variables that are of type {@link android.view.View} or a subclass of it.
 * Running ViewBinder.bind() against any object that contains View objects eliminates the need to do manual <code>view.findViewById()</code> calls.
 * This method will also attempt to attach View's listener methods if the parent object's methods are defined properly.
<h2>Notes</h2>
<ul>
	<li>The View's field name in the parent class must match the R.id.* entry defined in the layout.</li>
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
	 * @param root The root layout. Used to make the call, root.findViewById(R.id.*);
	 * @param object The object that contains member variables of type View or subclasses that bill be bound.
	 * @param rDotId The R.id class that is defined for resources in your project.
	 */
	public static void bind(View root, Object object, Class<?> rDotId) {
		ViewBindable bindable = new GenericViewBinder(object, root, rDotId);
		bindable.bindViews();
	}
}
