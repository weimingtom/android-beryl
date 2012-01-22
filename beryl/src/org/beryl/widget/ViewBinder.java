package org.beryl.widget;

import org.beryl.diagnostics.ExceptionReporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** Methods that auto-wireup member variables that are of type {@link android.view.View} or a subclass of it.
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
@deprecated Use Android Annotations instead. http://androidannotations.org/
 */
@Deprecated
public class ViewBinder {

	/**
	 * Bind all the views defined in the {@link android.app.Fragment} object to the layout defined.
	 * @param root The root layout. Used to make the call, root.findViewById(R.id.*);
	 * @param object The object that contains member variables of type View or subclasses that bill be bound.
	 * @param packageName The package name where the R.java file is located in.
	 */
	public static void bind(View root, Object object, String packageName) {
		IViewBindable bindable = new GenericViewBinder(object, root, packageName);
		bindable.bindViews();
	}
	public static void bind(View root, Object object, Class<?> rDotId) {
		bind(root, object, rDotId.getPackage().getName());
	}

	/**
	 * Helper method to assist in BaseAdapter.getView method. Ensures that convertView is populated and that the associated ViewHolder class is tagged to the
	 * view if it is created.
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param viewHolderClass
	 * @param rDotId
	 * @return convertView
<i>Suppose you had a BaseAdapter for listing articles from an RSS Feed. Your class may look like something below.</i>
<h2>Example Class</h2>
<pre class="code"><code class="java">
class ArticleAdapter extends BaseAdapter {
	static class ViewHolder {
		Button OpenWebsiteButton;
		ImageView ArticlePicture;
		TextView ArticleCaption;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Need to do something here.
	}
}
</code></pre>
<h2>New View Binder Method</h2>
<pre class="code"><code class="java">
public View getView(int position, View convertView, ViewGroup parent) {
	convertView = ViewBinder.bind(convertView, parent, R.id.lineitem_article, ViewHolder.class, R.id.class);
	final ViewHolder holder = (ViewHolder)convertView.getTag();
		// Mess with the state of the view to fit the list item.
	return convertView;
}
</code></pre>
<h2>Old Method</h2>
<pre class="code"><code class="java">
public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	if(convertView == null) {
		final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(layoutId, parent, false);
		holder = new ViewHolder();
		convertView.setTag(holder);
		holder.OpenWebsiteButton = (Button)convertView.findViewById(R.id.OpenWebsiteButton);
		holder.ArticlePicture = (ImageView)convertView.findViewById(R.id.ArticlePicture);
		holder.ArticleCaption = (TextView)convertView.findViewById(R.id.ArticleCaption);
	} else {
		holder = (ViewHolder)convertView.getTag();
	}
		// Mess with the state of the view to fit the list item.
	return convertView;
}
</code></pre>
	 */
	public static View bind(View convertView, final ViewGroup parent, final int layoutId, final Class<?> viewHolderClass, final Class<?> rDotId) {
		if(convertView == null) {
			final LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutId, parent, false);
			try {
				final Object tag = viewHolderClass.newInstance();
				ViewBinder.bind(convertView, tag, rDotId);
				convertView.setTag(tag);
			} catch(Exception e) {
				ExceptionReporter.report(e);
			}
		}

		return convertView;
	}
}
