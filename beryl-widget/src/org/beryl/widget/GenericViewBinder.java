package org.beryl.widget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.beryl.diagnostics.Log;
import org.beryl.diagnostics.Logger;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;

class GenericViewBinder implements ViewBindable {

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
		return getViews(Logger.newInstance("ViewBinder"));
	}
	
	public List<ViewData> getViews(final Log log) {
		final Class<?> myType = getType();

		ArrayList<ViewData> views = new ArrayList<ViewData>();
		Field[] fields = myType.getDeclaredFields();
		
		for(Field field : fields) {
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			if(View.class.isAssignableFrom(fieldType)) {
				try {
					views.add(new ViewData(field));
				} catch(Exception e){
					log.e("Failed to assign field=" + field.getName());
					log.e(e);
				}
			}
		}
		
		return views;
	}
	
	public void bindViews() {
		bindViews(Logger.newInstance("ViewBinder"));
	}
	
	public void bindViews(final Log log) {
		final View root = getRootView();
		final List<ViewData> viewDatas = getViews();

		for(ViewData viewData : viewDatas) {
			String viewName = "<Unknown>";
			try {
				
				viewName = viewData.field.getName();
				final int rId = getRdotId(viewName);
				bindView(root, viewData, rId);
			} catch(Exception e) {
				log.e("Failed to bind on View " + viewName);
				log.e(e);
			}
		}
	}
	
	private void bindView(View root, ViewData viewData, int rId) throws IllegalArgumentException, IllegalAccessException {
		final Object parent = getObject();
		final View foundView = root.findViewById(rId);
		viewData.field.set(parent, foundView);
		
		attemptListenerBind(parent, foundView, viewData, "onClick", GenericOnClickListener.class);
	}

	private void attemptListenerBind(Object parent, View foundView, ViewData viewData, String methodSuffix, Class<GenericOnClickListener> listenerClass) {
		final Log log = Logger.newInstance("attemptListenerBind");
		log.i(String.format("FoundView= %s, MethodSuffix= %s, ListenerClass= %s", foundView.toString(), methodSuffix, listenerClass.getName()));
		try {
			final StringBuilder sb = new StringBuilder();
			sb.append(viewData.field.getName());
			sb.append("_");
			sb.append(methodSuffix);
			
			final String methodName = sb.toString();
			log.i("Attempt MethodName = " + methodName);
			final Method attachListenerMethod = listenerClass.getMethod("attachListener", Object.class, View.class, String.class);
			log.probe(attachListenerMethod);
			attachListenerMethod.setAccessible(true);
			attachListenerMethod.invoke(null, parent, foundView, methodName);
		} catch(NoSuchMethodException e) {
			log.e(e);
			// This was an attempt so whatever.
		} catch(Exception e) {
			log.e(e);
			// Logit.
		}
	}

	static abstract class GenericListener {
		final Object parent;
		final Method method;
		
		GenericListener(Object parent, Method method) {
			this.parent = parent;
			this.method = method;
		}
		
		Class<?> getParentClass() {
			return parent.getClass();
		}
	}
	
	static class GenericOnClickListener 
		extends GenericListener
		implements OnClickListener {
		
		public static void attachListener(Object parent, View target, String methodName) {
			try {
				final Method handlerMethod = parent.getClass().getDeclaredMethod(methodName, View.class);
				target.setOnClickListener(new GenericOnClickListener(parent, handlerMethod));
			} catch(Exception e) {
				//Logger.probe(parent);
				//Logger.inspectClass(parent.getClass());
				//Logger.e(e);
			}
		}
		
		GenericOnClickListener(Object parent, Method method) {
			super(parent, method);
		}
		
		public void onClick(View v) {
			try {
				method.invoke(parent, v);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
