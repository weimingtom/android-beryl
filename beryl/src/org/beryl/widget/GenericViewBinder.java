package org.beryl.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.beryl.diagnostics.ExceptionReporter;

import android.content.res.Resources;
import android.view.View;

class GenericViewBinder implements IViewBindable {

	final Class<?> objectClass;
	final Object object;
	final View rootView;
	final Resources res;
	final String packageName;

	GenericViewBinder(Object object, View rootView, String packageName) {
		this.object = object;
		objectClass = this.object.getClass();
		this.rootView = rootView;
		res = rootView.getResources();
		this.packageName = packageName;
	}

	protected View getRootView() {
		return rootView;
	}

	private int getRdotId(String name) {
		return res.getIdentifier(name, "id", packageName);
	}

	protected Object getObject() {
		return object;
	}

	protected Class<?> getType() {
		return objectClass;
	}

	static class ViewData {
		final Field field;
		public ViewData(Field field) {
			this.field = field;
			this.field.setAccessible(true);
		}
	}

	public List<ViewData> getViews() {
		final Class<?> myType = getType();

		final ArrayList<ViewData> views = new ArrayList<ViewData>();
		final Field[] fields = myType.getDeclaredFields();

		for(final Field field : fields) {
			field.setAccessible(true);
			final Class<?> fieldType = field.getType();
			if(View.class.isAssignableFrom(fieldType)) {
				try {
					views.add(new ViewData(field));
				} catch(final Exception e){
					ExceptionReporter.report("Failed to assign field= " + field.getName(), e);
				}
			}
		}

		return views;
	}

	public void bindViews() {
		final View root = getRootView();
		final List<ViewData> viewDatas = getViews();

		for(final ViewData viewData : viewDatas) {
			String viewName = "<Unknown>";
			try {

				viewName = viewData.field.getName();
				final int rId = getRdotId(viewName);
				bindView(root, viewData, rId);
			} catch(final Exception e) {
				ExceptionReporter.report("Failed to bind on View " + viewName, e);
			}
		}
	}

	private void bindView(View root, ViewData viewData, int rId) throws IllegalArgumentException, IllegalAccessException {
		final Object parent = getObject();
		final View foundView = root.findViewById(rId);
		viewData.field.set(parent, foundView);
	}
}
